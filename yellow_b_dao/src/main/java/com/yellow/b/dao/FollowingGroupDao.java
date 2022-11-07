package com.yellow.b.dao;

import com.yellow.b.domain.FollowingGroup;

public interface FollowingGroupDao {
    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);
}
