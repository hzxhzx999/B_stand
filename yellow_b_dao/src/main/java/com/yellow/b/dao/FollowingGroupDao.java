package com.yellow.b.dao;

import com.yellow.b.domain.FollowingGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowingGroupDao {
    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(@Param("userId") Long userId);

    void addFollowingGroup(FollowingGroup followingGroup);
}
