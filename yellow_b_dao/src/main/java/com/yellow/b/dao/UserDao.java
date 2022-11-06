package com.yellow.b.dao;

import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;

public interface UserDao {
    User getUserByPhone(String phone);

    void addUser(User user);

    void addUserInfo(UserInfo userInfo);
}
