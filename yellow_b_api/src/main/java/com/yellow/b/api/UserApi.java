package com.yellow.b.api;

import com.yellow.b.domain.Result;
import com.yellow.b.domain.User;
import com.yellow.b.service.UserService;
import com.yellow.b.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
public class UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSupport userSupport;

    @GetMapping("/users")
    public Result getUserId(){
        Long currentUserId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(currentUserId);
        return Result.ok(user);
    }

    @PostMapping("/users")
    public Result addUser(@RequestBody User user, HttpServletRequest request){
        userService.addUser(user,request);
        return Result.ok();
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = userService.login(user);
        return Result.ok(token);
    }
}
