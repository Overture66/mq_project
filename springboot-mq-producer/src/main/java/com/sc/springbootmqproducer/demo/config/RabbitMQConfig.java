package com.sc.springbootmqproducer.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ITEM_TOPIC_EXCHANGE = "springboot_item_topic_exchange";
    public static final String ITEM_QUEUE = "springboot_item_queue";

    @Bean("itemTopicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean("itemQueue")
    public Queue itemQueue() {
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    @Bean
    public Binding itemQueueBinding(@Qualifier("itemQueue") Queue itemQueue,
                                    @Qualifier("itemTopicExchange")Exchange itemTopicExchange) {
        return BindingBuilder.bind(itemQueue).to(itemTopicExchange).with("item.#").noargs();
    }

}
