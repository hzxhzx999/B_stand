package com.yellow.b.api;

import com.alibaba.fastjson.JSONObject;
import com.yellow.b.domain.PageResult;
import com.yellow.b.domain.Result;
import com.yellow.b.domain.User;
import com.yellow.b.domain.UserInfo;
import com.yellow.b.service.UserFollowingService;
import com.yellow.b.service.UserService;
import com.yellow.b.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserFollowingService userFollowingService;

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
    @GetMapping("/user-infos")
    public Result getUserInfo(@RequestParam Integer currentPage,@RequestParam Integer pageSize,String nickname){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("currentPage",currentPage);
        params.put("pageSize",pageSize);
        params.put("nickname",pageSize);
        params.put("userId",userId);
        PageResult<UserInfo> result =userService.pageListUserInfo(params);
        if(result.getTotal()>0){
            List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(),userId);
            result.setList(checkedUserInfoList);
        }
        return Result.ok(result);
    }
}
