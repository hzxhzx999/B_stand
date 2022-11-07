package com.yellow.b.service;

import com.yellow.b.domain.FollowingGroup;

public interface FollowingGroupService {

    FollowingGroup getByType(String type);
    FollowingGroup getById(Long id);

}
