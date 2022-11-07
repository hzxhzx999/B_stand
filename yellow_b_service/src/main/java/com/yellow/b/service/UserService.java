package com.yellow.b.service;

import com.alibaba.fastjson.JSONObject;
import com.yellow.b.domain.PageResult;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface UserService {
    void addUser(User user, HttpServletRequest request);

    String login(User user);

    User getUserInfo(Long currentUserId);

    void updateUserInfos(UserInfo userInfo,HttpServletRequest request);

    void updateUsers(User user, HttpServletRequest request);

    User getUserByid(Long followingId);

    List<UserInfo> getUserInfoByUserIds(Set<Long> collect);

    PageResult<UserInfo> pageListUserInfo(JSONObject params);
}
