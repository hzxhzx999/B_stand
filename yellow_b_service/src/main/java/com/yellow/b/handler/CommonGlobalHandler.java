package com.yellow.b.handler;

import com.yellow.b.domain.Result;
import com.yellow.b.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) //优先级最高
public class CommonGlobalHandler {

    @ExceptionHandler(value = Exception.class)
    public Result commonExceptionHandler(HttpServletRequest request, Exception e) {
        String errorMsg = e.getMessage();
        if(e instanceof ConditionException){
            String errorCode = ((ConditionException) e).getCode();
            return Result.fail(errorCode,errorMsg);
        }
        return Result.fail("500",errorMsg);
    }
}
