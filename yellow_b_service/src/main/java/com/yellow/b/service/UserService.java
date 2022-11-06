package com.yellow.b.service;

import com.yellow.b.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void addUser(User user, HttpServletRequest request);

    String login(User user);
}
