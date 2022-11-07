package com.yellow.b.utils;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

public class RocketMqUtil {

//    同步发送
    public static void syncSendMessage(DefaultMQProducer producer, Message message){
        try {
            SendResult send = producer.send(message);
            System.out.println(send);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
//    异步发送
    public static void asyncSendMessage(DefaultMQProducer producer, Message message){
        int messageCount = 2;
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch2.countDown();
                        System.out.println(sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch2.countDown();
                        System.out.println("发生了异常"+throwable);
                        throwable.printStackTrace();
                    }
                });
                countDownLatch2.await(5, TimeUnit.SECONDS);
            } catch (MQClientException | RemotingException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
