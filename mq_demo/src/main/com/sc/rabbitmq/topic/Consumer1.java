package com.sc.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.sc.rabbitmq.utlis.ConnectionUtlis;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtlis.getConnection();
        //创建channel
        Channel channel = connection.createChannel();
        //声明交换机
        /*
        参数：1.交换机名称
        2.交换机类型,fanout、direct(定向)、topic(通配符)
        * */
        channel.exchangeDeclare(Producer.exchange_Name, BuiltinExchangeType.TOPIC);
        //参数：1队列名 2.是否持久化 3.是否独占连接 4.是否不用时自动删除 5.队列其他参数
        channel.queueDeclare(Producer.queueName1, true, false, false, null);
        channel.queueDeclare(Producer.queueName2, true, false, false, null);

        //队列绑定交换机
        channel.queueBind(Producer.queueName1 ,Producer.exchange_Name,"item.*");
        channel.queueBind(Producer.queueName2,Producer.exchange_Name , "item.insert");
        channel.queueBind(Producer.queueName2,Producer.exchange_Name , "item.update");
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
        channel.basicConsume(Producer.queueName1,true,consumer);


//        //释放资源
//        channel.close();
//        connection.close();


    }
}
