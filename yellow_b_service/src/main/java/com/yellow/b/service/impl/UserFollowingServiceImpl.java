package com.yellow.b.service.impl;

import com.yellow.b.dao.UserFollowDao;
import com.yellow.b.domain.Constant;
import com.yellow.b.domain.FollowingGroup;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserFollowing;
import com.yellow.b.exception.ConditionException;
import com.yellow.b.service.FollowingGroupService;
import com.yellow.b.service.UserFollowingService;
import com.yellow.b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.CodecException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserFollowingServiceImpl implements UserFollowingService {

    @Autowired
    private UserFollowDao userFollowDao;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void addUserFollowing(UserFollowing userFollowing, HttpServletRequest request) {
        Long group = userFollowing.getGroupId(); //获取分组id
        if (group == null) { //如果分组为空
            FollowingGroup byType = followingGroupService.getByType(Constant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);//从数据库获取默认的分组id
            userFollowing.setGroupId(byType.getId());//给传入的参数设置为默认id
        }//如果不为空
        FollowingGroup byId = followingGroupService.getById(group);//通过分组id获取参数
        if (byId == null) {//如果传入的分组不存在
            throw new ConditionException("分组不存在");
        }
        Long followingId = userFollowing.getFollowingId();//获取关注id
        User userById = userService.getUserByid(followingId);//获取用户
        if(userById==null){//如果用户不存在
            throw new CodecException("关注的用户不存在!");
        }//如果存在将原有的分组删除
        userFollowDao.deleteUserFollowing(userFollowing.getUserId(),followingId);
        userFollowing.setCreateTime(new Date());
        userFollowing.setUpdateTime(new Date());
        userFollowing.setUpdateIp(request.getRemoteAddr());
        userFollowDao.addUserFollowing(userFollowing);//关注用户
    }
}
