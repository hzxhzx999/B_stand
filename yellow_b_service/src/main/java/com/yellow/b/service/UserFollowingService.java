package com.yellow.b.service;

import com.yellow.b.domain.UserFollowing;

import javax.servlet.http.HttpServletRequest;

public interface UserFollowingService {
    void addUserFollowing(UserFollowing userFollowing, HttpServletRequest request);
}
