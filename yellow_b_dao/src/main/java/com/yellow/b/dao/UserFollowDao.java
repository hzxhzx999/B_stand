package com.yellow.b.dao;

import com.yellow.b.domain.UserFollowing;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao {
    void deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    void addUserFollowing(UserFollowing userFollowing);
}
