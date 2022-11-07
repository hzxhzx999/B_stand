package com.yellow.b.service;

import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void addUser(User user, HttpServletRequest request);

    String login(User user);

    User getUserInfo(Long currentUserId);

    void updateUserInfos(UserInfo userInfo,HttpServletRequest request);

    void updateUsers(User user, HttpServletRequest request);
}
