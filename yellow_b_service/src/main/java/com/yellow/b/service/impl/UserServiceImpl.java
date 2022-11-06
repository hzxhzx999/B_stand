package com.yellow.b.service.impl;

import cn.hutool.core.util.IdUtil;
import com.mysql.cj.util.StringUtils;
import com.yellow.b.dao.UserDao;
import com.yellow.b.domain.Constant;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import com.yellow.b.exception.ConditionException;
import com.yellow.b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public void addUser(User user, HttpServletRequest request) {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空!");
        }
        User userByPhone = getUserByPhone(phone);
        if(userByPhone !=null){
            throw new ConditionException("改手机号已注册!");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex((password+salt).getBytes(StandardCharsets.UTF_8)));
        user.setSalt(salt);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setUpdateIp(request.getRemoteAddr());
        userDao.addUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(Constant.GENDER_NICK +IdUtil.randomUUID().replaceAll("-",""));
        userInfo.setGender(Constant.GENDER_MALE);
        userInfo.setBirth(Constant.GENDER_BIRTH);
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);
        userInfo.setUpdateIp(request.getRemoteAddr());
        userDao.addUserInfo(userInfo);
    }
    public User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }
}