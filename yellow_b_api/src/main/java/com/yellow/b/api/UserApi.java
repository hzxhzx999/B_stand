package com.yellow.b.api;

import cn.hutool.crypto.asymmetric.RSA;
import com.yellow.b.domain.Result;
import com.yellow.b.domain.User;
import com.yellow.b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;
    @Resource
    private RSA rsa;

    @GetMapping("/rsa-pks")
    public Result getRsaKey(){
        PublicKey publicKey = rsa.getPublicKey();
        String algorithm = publicKey.getAlgorithm();
        return Result.ok(algorithm);
    }
    @PostMapping("/users")
    public Result addUser(@RequestBody User user, HttpServletRequest request){
        userService.addUser(user,request);
        return Result.ok();
    }
}
