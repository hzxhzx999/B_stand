package com.yellow.b.service.impl;

import com.yellow.b.dao.FollowingGroupDao;
import com.yellow.b.domain.FollowingGroup;
import com.yellow.b.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {
    @Autowired
    private FollowingGroupDao followingGroupDao;
    @Override
    public FollowingGroup getByType(String type) {
        return followingGroupDao.getByType(type);
    }
    @Override
    public FollowingGroup getById(Long id) {
        return followingGroupDao.getById(id);
    }
    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }
    @Override
    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    @Override
    public List<FollowingGroup> getFollowingGroups(Long userId) {
        return followingGroupDao.getFollowingGroups(userId);
    }
}
