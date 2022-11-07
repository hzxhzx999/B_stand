package com.yellow.b.api;

import com.yellow.b.domain.Result;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import com.yellow.b.service.UserService;
import com.yellow.b.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/token-login")
    public Result login(@RequestBody User user){
        String token = userService.login(user);
        return Result.ok(token);
    }
    @PutMapping("/users")
    public Result updateUsers(@RequestBody User user,HttpServletRequest request){
        Long currentUserId = userSupport.getCurrentUserId();
        user.setId(currentUserId);
        userService.updateUsers(user,request);
        return Result.ok();
    }
    @PutMapping("/user-infos")
    public Result UpdateUserInfo(@RequestBody UserInfo userInfo,HttpServletRequest request){
        Long currentUserId = userSupport.getCurrentUserId();
        userInfo.setUserId(currentUserId);
        userService.updateUserInfos(userInfo,request);
        return Result.ok();
    }
}
