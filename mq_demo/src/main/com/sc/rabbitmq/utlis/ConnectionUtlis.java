package com.sc.rabbitmq.utlis;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtlis {

    public static Connection getConnection() throws IOException, TimeoutException, IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.128");
        factory.setPort(5672);
        factory.setVirtualHost("/sc");
        factory.setUsername("sc");
        factory.setPassword("sc");


        //创建连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
