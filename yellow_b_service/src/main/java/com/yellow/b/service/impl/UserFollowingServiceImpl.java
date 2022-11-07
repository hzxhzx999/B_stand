package com.yellow.b.service.impl;

import com.yellow.b.dao.FollowingGroupDao;
import com.yellow.b.dao.UserFollowDao;
import com.yellow.b.domain.FollowingGroup;
import com.yellow.b.domain.UserFollowing;
import com.yellow.b.service.FollowingGroupService;
import com.yellow.b.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowingServiceImpl implements UserFollowingService {

    @Autowired
    private UserFollowDao userFollowDao;
    @Autowired
    private FollowingGroupService followingGroupService

    @Override
    public void addUserFollowing(UserFollowing userFollowing) {
        Long group  = userFollowing.getGroupId();
        if(group==null){
            FollowingGroup byType = followingGroupService.getByType();
        }
    }
}
