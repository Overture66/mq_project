package com.sc.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

public class TopicListenerWell2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String msg = new String(message.getBody(), "UTF-8");
            System.out.printf("通配符#监听器2:%s，路由:key%s，队列:%s,消息:%s\n",
                    message.getMessageProperties().getReceivedExchange(),
                    message.getMessageProperties().getReceivedRoutingKey(),
                    message.getMessageProperties().getConsumerQueue(),
                    msg);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
