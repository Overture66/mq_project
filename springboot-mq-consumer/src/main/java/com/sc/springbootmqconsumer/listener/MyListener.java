package com.sc.springbootmqconsumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @RabbitListener(queues = "springboot_item_queue")
    public void myListener1(String message) {
        System.out.println("Message: " + message);
    }
}
