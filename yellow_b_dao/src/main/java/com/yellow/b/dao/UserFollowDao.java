package com.yellow.b.dao;

import com.yellow.b.domain.UserFollowing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowDao {
    void deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);
    void addUserFollowing(UserFollowing userFollowing);
    List<UserFollowing> getUserFollowings(@Param("userId") Long userId);
    List<UserFollowing> getUserFans(Long userId);
}
