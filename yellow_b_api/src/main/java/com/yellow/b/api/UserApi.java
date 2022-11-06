package com.yellow.b.api;

import com.yellow.b.domain.Result;
import com.yellow.b.domain.User;
import com.yellow.b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public Result addUser(@RequestBody User user, HttpServletRequest request){
        userService.addUser(user,request);
        return Result.ok();
    }
}
