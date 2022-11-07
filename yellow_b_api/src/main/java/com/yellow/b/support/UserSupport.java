package com.yellow.b.support;

import com.yellow.b.exception.ConditionException;
import com.yellow.b.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class UserSupport {
    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Long token = TokenUtils.verifyToken(Objects.requireNonNull(requestAttributes).getRequest().getHeader("token"));
        if(token<0){
            throw new ConditionException("非法用户");
        }
        return token;
    }
}
