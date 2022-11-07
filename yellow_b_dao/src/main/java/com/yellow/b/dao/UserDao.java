package com.yellow.b.dao;

import com.alibaba.fastjson.JSONObject;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User getUserByPhone(@Param("phone") String phone);

    void addUser(User user);

    void addUserInfo(UserInfo userInfo);

    User getUserById(@Param("id") Long currentUserId);

    UserInfo getUserInfoByUserInfoId(@Param("userId") Long currentUserId);

    void updateUserInfos(UserInfo userInfo);

    void updateUsers(User user);

    List<UserInfo> getUserInfoByUserIds(Set<Long> collect);

    List<UserInfo> pageListUserInfos(JSONObject params);
}
