package com.yellow.b.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import com.yellow.b.dao.UserDao;
import com.yellow.b.domain.Constant;
import com.yellow.b.domain.PageResult;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import com.yellow.b.exception.ConditionException;
import com.yellow.b.service.UserService;
import com.yellow.b.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void addUser(User user, HttpServletRequest request) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空!");
        }
        User userByPhone = getUserByPhone(phone);
        if (userByPhone != null) {
            throw new ConditionException("手机号已注册!");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8)));
        user.setSalt(salt);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setUpdateIp(request.getRemoteAddr());
        userDao.addUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(Constant.GENDER_NICK + IdUtil.randomUUID().replaceAll("-", ""));
        userInfo.setGender(Constant.GENDER_MALE);
        userInfo.setBirth(Constant.GENDER_BIRTH);
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);
        userInfo.setUpdateIp(request.getRemoteAddr());
        userDao.addUserInfo(userInfo);
    }

    @Override
    public String login(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空!");
        }
        User userByPhone = getUserByPhone(phone);
        if (userByPhone == null) {
            throw new ConditionException("用户不存在!");
        }
        String pwd = DigestUtils.md5DigestAsHex((user.getPassword() + userByPhone.getSalt()).getBytes(StandardCharsets.UTF_8));
        if (!pwd.equals(userByPhone.getPassword())) {
            throw new ConditionException("密码错误!");
        }
        return TokenUtils.getToken(userByPhone.getId());
    }

    @Override
    public User getUserInfo(Long currentUserId) {
        User user = userDao.getUserById(currentUserId);
        UserInfo userInfo = userDao.getUserInfoByUserInfoId(currentUserId);
        user.setUserInfo(userInfo);
        return user;
    }

    @Override
    @Transactional
    public void updateUserInfos(UserInfo userInfo, HttpServletRequest request) {
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateIp(request.getRemoteAddr());
        userDao.updateUserInfos(userInfo);
    }

    @Override
    @Transactional
    public void updateUsers(User user, HttpServletRequest request) {
        user.setUpdateTime(new Date());
        user.setUpdateIp(request.getRemoteAddr());
        userDao.updateUsers(user);
    }

    @Override
    public User getUserByid(Long followingId) {
        return userDao.getUserById(followingId);
    }

    @Override
    public List<UserInfo> getUserInfoByUserIds(Set<Long> collect) {
        return userDao.getUserInfoByUserIds(collect);
    }

    @Override
    public PageResult<UserInfo> pageListUserInfo(JSONObject params) {
        Integer currentPage = params.getInteger("currentPage");
        Integer pageSize = params.getInteger("pageSize");
        PageHelper.startPage(currentPage, pageSize);
        List<UserInfo> list = userDao.pageListUserInfos(params);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        return new PageResult<UserInfo>(pageInfo.getTotal(),list);
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
}
