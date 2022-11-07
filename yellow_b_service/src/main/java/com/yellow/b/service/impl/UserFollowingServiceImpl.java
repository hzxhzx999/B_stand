package com.yellow.b.service.impl;

import com.yellow.b.dao.UserFollowDao;
import com.yellow.b.domain.*;
import com.yellow.b.exception.ConditionException;
import com.yellow.b.service.FollowingGroupService;
import com.yellow.b.service.UserFollowingService;
import com.yellow.b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.CodecException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (userById == null) {//如果用户不存在
            throw new CodecException("关注的用户不存在!");
        }//如果存在将原有的分组删除
        userFollowDao.deleteUserFollowing(userFollowing.getUserId(), followingId);
        userFollowing.setCreateTime(new Date());
        userFollowing.setUpdateTime(new Date());
        userFollowing.setUpdateIp(request.getRemoteAddr());
        userFollowDao.addUserFollowing(userFollowing);//关注用户
    }

    @Override
    public List<FollowingGroup> getUserFollowings(Long userId) {
        List<UserFollowing> userFollowings = userFollowDao.getUserFollowings(userId);
        Set<Long> collect = userFollowings.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (collect.size() > 0) {
            userInfoList = userService.getUserInfoByUserIds(collect);
        }
        for (UserFollowing userFollowing : userFollowings) {
            for (UserInfo userInfo : userInfoList) {
                if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(Constant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for (FollowingGroup followingGroup : groupList) {
            List<UserInfo> userInfos = new ArrayList<>();
            for (UserFollowing userFollowing : userFollowings) {
                if (followingGroup.getId().equals(userFollowing.getGroupId())) {
                    userInfos.add(userFollowing.getUserInfo());
                }
            }
            followingGroup.setFollowingUserInfoList(userInfos);
            result.add(followingGroup);
        }
        return result;
    }

    @Override
    public List<UserFollowing> getUserFans(Long userId) {
        List<UserFollowing> fanList = userFollowDao.getUserFans(userId);
        Set<Long> fanIsSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if (fanIsSet.size() > 0) {
            userInfoList = userService.getUserInfoByUserIds(fanIsSet);
        }
        List<UserFollowing> followingList = userFollowDao.getUserFollowings(userId);
        for (UserFollowing fan : fanList) {
            for (UserInfo userInfo : userInfoList) {
                if (fan.getUserId().equals(userInfo.getUserId())) {
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following : followingList) {
                if (following.getFollowingId().equals(fan.getUserId())) {
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    @Override
    public Long addFollowingGroups(FollowingGroup followingGroup, HttpServletRequest request) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setUpdateIp(request.getRemoteAddr());
        followingGroup.setUpdateTime(new Date());
        followingGroup.setType(Constant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    @Override
    public List<FollowingGroup> getFollowingGroups(Long userId) {
        return followingGroupService.getFollowingGroups(userId);
    }

    @Override
    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        List<UserFollowing> userFollowingList = userFollowDao.getUserFollowings(userId);
        for (UserInfo userInfo : userInfoList) {
            userInfo.setFollowed(false);
            for (UserFollowing following : userFollowingList) {
                if (following.getFollowingId().equals(userInfo.getUserId())) {
                    userInfo.setFollowed(true);
                }
            }
        }
        return userInfoList;
    }
}
