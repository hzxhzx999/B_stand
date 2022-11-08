package com.yellow.b.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.yellow.b.domain.UserFollowing;
import com.yellow.b.domain.UserMoments;
import com.yellow.b.domain.UserMomentsConstant;
import com.yellow.b.service.UserFollowingService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class RocketMqConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddress;
    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Bean(value = "momentProducer")
    public DefaultMQProducer momentsProducer(){ //生产者
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddress);
        try {
            producer.start();
            return producer;
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(value = "momentConsumer")
    public DefaultMQPushConsumer momentsPushConsumer(){//消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddress);
        try {
            consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS,"*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    MessageExt messageExt = list.get(0);
                    if(messageExt==null){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    String s = new String(messageExt.getBody());
                    UserMoments userMoments = JSONObject.toJavaObject(JSONObject.parseObject(s), UserMoments.class);
                    Long userId = userMoments.getUserId();
                    List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
                    for (UserFollowing userFan : userFans) {
                        String key = "sub"+userFan.getUserId();
                        String subList = redisTemplate.opsForValue().get(key);
                        List<UserMoments> subMomentList;
                        if(StringUtils.isNullOrEmpty(subList)){
                                subMomentList = new ArrayList<>();
                        }else{
                            subMomentList = JSONArray.parseArray(subList,UserMoments.class);
                        }
                        subMomentList.add(userMoments);
                        redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subMomentList),2, TimeUnit.DAYS);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });//监听器
            consumer.start();
            return consumer;
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }

}
