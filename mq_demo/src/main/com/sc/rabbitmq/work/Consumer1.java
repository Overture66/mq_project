package com.sc.rabbitmq.work;

import com.rabbitmq.client.*;
import com.sc.rabbitmq.utlis.ConnectionUtlis;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtlis.getConnection();
        //创建channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(Producer.queueName, true, false, false, null);

        //接受消息
        DefaultConsumer consumer=new DefaultConsumer(channel){
            /**
             * 接收到消息，处理消息的回调函数
             * @param consumerTag 消费者标签，在channel.basicConsume()去指定
             * @param envelope 消息包的信息，可以从中获取消息id，消息routingkey，交换机
             * @param properties 消息的属性信息
             * @param body 消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key:"+envelope.getRoutingKey());
                System.out.println("交换机:"+envelope.getExchange());
                System.out.println("消息ID:"+envelope.getDeliveryTag());
                System.out.println("消息:"+new String(body,"utf-8"));
            }
        };
        //消费消息
        /**
         * 参数：
         * 1.队列名称
         * 2.是否确认，true表示接收到消息后，告诉mq消息已经接收，false表示消息接收到，告诉mq消息接收到
         * 3.指定消费对象，消息函数的回调函数
         */
        channel.basicConsume(Producer.queueName,true,consumer);


//        //释放资源
//        channel.close();
//        connection.close();


    }
}
