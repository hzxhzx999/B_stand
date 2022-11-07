package com.yellow.b.api;

import com.yellow.b.domain.Result;
import com.yellow.b.domain.UserMoments;
import com.yellow.b.service.impl.UserMomentsService;
import com.yellow.b.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserMomentsApi {

    @Autowired
    private UserMomentsService userMomentsService;
    @Autowired
    private UserSupport userSupport;

    @PostMapping("/user-moments")
    public Result addUserMoments(@RequestBody UserMoments userMoments, HttpServletRequest request){
        Long userId = userSupport.getCurrentUserId();
        userMoments.setUserId(userId);
        userMomentsService.addUserMoments(userMoments,request);
        return Result.ok();
    }
}
