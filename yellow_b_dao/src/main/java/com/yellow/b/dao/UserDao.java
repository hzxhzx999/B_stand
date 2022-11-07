package com.yellow.b.dao;

import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User getUserByPhone(@Param("phone") String phone);

    void addUser(User user);

    void addUserInfo(UserInfo userInfo);

    User getUserByUserUd(@Param("userId") Long currentUserId);

    UserInfo getUserInfoByUserInfoId(@Param("userId") Long currentUserId);

    void updateUserInfos(UserInfo userInfo);

    void updateUsers(User user);
}
