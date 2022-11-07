package com.yellow.b.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Result {
    private String code;
    private String msg;
    private Object result;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Result(String code, String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.result = data;
    }

    public static Result ok(){
        return new Result("200","成功");
    }
    public static Result ok(String code,String msg){
        return new Result(code,msg);
    }
    public static Result ok(Object data){
        return new Result("200","成功",data);
    }
    public static Result ok(String code,String msg,Object data){
        return new Result(code,msg,data);
    }
    public static Result fail(){
        return new Result("201","失败");
    }
    public static Result fail(String code,String msg){
        return new Result(code,msg);
    }
}
