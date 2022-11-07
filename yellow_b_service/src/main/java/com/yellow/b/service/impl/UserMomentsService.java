package com.yellow.b.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yellow.b.dao.UserMomentsDao;
import com.yellow.b.domain.UserMoments;
import com.yellow.b.domain.UserMomentsConstant;
import com.yellow.b.utils.RocketMqUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;
    @Autowired
    private ApplicationContext applicationContext;

    public void addUserMoments(UserMoments userMoments, HttpServletRequest request) {
        userMoments.setCreateTime(new Date());
        userMoments.setUpdateTime(new Date());
        userMoments.setUpdateIp(request.getRemoteAddr());
        userMomentsDao.addUserMoments(userMoments);
        DefaultMQProducer momentProducer = (DefaultMQProducer)applicationContext.getBean("momentProducer");
        Message message = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONBytes(userMoments));
        RocketMqUtil.syncSendMessage(momentProducer,message);
    }
}
