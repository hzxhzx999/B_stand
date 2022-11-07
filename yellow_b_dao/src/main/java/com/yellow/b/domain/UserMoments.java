package com.yellow.b.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMoments {
    private Long id;
    private Long userId;
    private String type;
    private Long contentId;
    private Date createTime;
    private Date updateTime;
    private String updateIp;
}
