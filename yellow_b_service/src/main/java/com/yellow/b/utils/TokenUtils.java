package com.yellow.b.utils;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yellow.b.exception.ConditionException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

public class TokenUtils {
    public static final String ISSUER = "签发者";
    private static RSA rsa = null;
    public static RSA getRsa(){
        if(rsa==null){
            rsa = new RSA();
        }
        return rsa;
    }
    public static String getToken(Long userId){
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) getRsa().getPublicKey(), (RSAPrivateKey) getRsa().getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());//当前系统时间
        calendar.add(Calendar.SECOND,30);//过期时间
        return JWT.create() //生成jwt
                .withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }
    public static Long verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) getRsa().getPublicKey(), (RSAPrivateKey) getRsa().getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT verify = verifier.verify(token);//验证解密
            String keyId = verify.getKeyId();//获取解密前的userId
            return Long.valueOf(keyId);
        }catch (TokenExpiredException e){
            throw new ConditionException("555","token过期");
        }catch (Exception e){
            throw new ConditionException("非法用户token");
        }
    }
}
