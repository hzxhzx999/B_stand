package com.yellow.b.service;

import com.yellow.b.domain.FollowingGroup;

import java.util.List;

public interface FollowingGroupService {

    FollowingGroup getByType(String type);
    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(Long userId);
}
