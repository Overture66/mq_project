package com.sc.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sc.rabbitmq.utlis.ConnectionUtlis;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    static final String exchange_Name = "topic_exchange";
    //queue的名字
    static final String queueName1 = "topic_queue1";
    static final String queueName2 = "topic_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtlis.getConnection();

        //声明队列
        Channel channel = connection.createChannel();
        //声明交换机
        /*
        参数：1.交换机名称
        2.交换机类型,fanout、direct(定向)、topic(通配符)
        * */
        channel.exchangeDeclare(exchange_Name, BuiltinExchangeType.TOPIC);
        //参数：1队列名 2.是否持久化 3.是否独占连接 4.是否不用时自动删除 5.队列其他参数
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueDeclare(queueName2, true, false, false, null);

        //队列绑定交换机
        channel.queueBind(queueName1 ,exchange_Name,"item.*");
        channel.queueBind(queueName2,exchange_Name , "item.insert");
        channel.queueBind(queueName2,exchange_Name , "item.update");

        /*参数：交换机名字，默认使用defalut exchange
         2.routingkey，在简单模式可以写queuename
         3.消息的其他属性
         4.消息数据 */
        String message="通配符模式-新增订单:routingKey=item.insert";

        channel.basicPublish(exchange_Name, "item.insert", null, message.getBytes());

        System.out.println("send message: " + message);

        message="通配符模式-修改订单:routingKey=item.update";

        channel.basicPublish(exchange_Name, "item.update", null, message.getBytes());

        System.out.println("send message: " + message);

        message="通配符模式-删除订单:routingKey=item.delete";

        channel.basicPublish(exchange_Name, "item.delete", null, message.getBytes());

        System.out.println("send message: " + message);

        //释放资源
        channel.close();
        connection.close();

    }
}
