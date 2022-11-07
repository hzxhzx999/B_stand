package com.yellow.b.api;

import com.yellow.b.domain.FollowingGroup;
import com.yellow.b.domain.Result;
import com.yellow.b.domain.UserFollowing;
import com.yellow.b.service.UserFollowingService;
import com.yellow.b.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;
    @Autowired
    private UserSupport userSupport;

    @PostMapping("/user-followings")
    public Result addUserFollowings(@RequestBody UserFollowing userFollowing, HttpServletRequest request){
        Long currentUserId = userSupport.getCurrentUserId();
        userFollowing.setUserId(currentUserId);
        userFollowingService.addUserFollowing(userFollowing,request);
        return Result.ok();
    }
    @GetMapping("/user-followings")
    public Result getUserFollowings(){
        Long currentUserId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowings(currentUserId);
        return Result.ok(result);
    }
    @GetMapping("user-fans")
    public Result getUserFans(){
        Long currentUserId = userSupport.getCurrentUserId();
        List<UserFollowing> userFans = userFollowingService.getUserFans(currentUserId);
        return Result.ok(userFans);
    }
    @PostMapping("/user-following-group")
    public Result addUserFollowingGroups(@RequestBody FollowingGroup followingGroup, HttpServletRequest request){
        Long currentUserId = userSupport.getCurrentUserId();
        followingGroup.setUserId(currentUserId);
        Long groupId = userFollowingService.addFollowingGroups(followingGroup,request);
        return Result.ok(groupId);
    }
}
