package com.yellow.b.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInfo {
    private Long id;
    private Long userId;
    private String nickname;
    private String avatar;
    private String sign;
    private String gender;
    private String birth;
    private Date createTime;
    private Date updateTime;
    private String updateIp;
}
