package com.sc.demo.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-rabbitmq.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *发送简单队列消息
     * 使用默认的交换机
     * routingKey=""
     */
    @Test
    public void queueTest() {
        rabbitTemplate.convertAndSend("spring_queue","发送简单队列消息");
    }
    @Test
    public void fanoutTest() {
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","发送到spring_fanout_exchange交换机");
    }
    @Test
    public void topicTest() {
        rabbitTemplate.convertAndSend("spring_topic_exchange","sc.nj","发送到spring_topic_exchange交换机sc.nj的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange","sc.nj.1","发送到spring_topic_exchange交换机sc.nj.1的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange","sc.nj.2","发送到spring_topic_exchange交换机sc.nj.2的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange","hh.sc.1","发送到spring_topic_exchange交换机hh.sc.1的消息");
    }

}
