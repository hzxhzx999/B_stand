package com.yellow.b.service;

import com.yellow.b.domain.FollowingGroup;
import com.yellow.b.domain.UserFollowing;
import com.yellow.b.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserFollowingService {
    void addUserFollowing(UserFollowing userFollowing, HttpServletRequest request);
    List<FollowingGroup> getUserFollowings(Long userId);
    List<UserFollowing> getUserFans(Long userId);
    Long addFollowingGroups(FollowingGroup followingGroup, HttpServletRequest request);
    List<FollowingGroup> getFollowingGroups(Long userId);
    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);
}
