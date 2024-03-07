package com.sc.rabbitmq.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-rabbitmq.xml")
public class ConsumerTest {

    @Test
    public void test() {
        while (true) {

        }
    }

}