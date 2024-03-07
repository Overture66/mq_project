package com.sc.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sc.rabbitmq.utlis.ConnectionUtlis;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    //queue的名字
    static final String queueName = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtlis.getConnection();

        //声明队列
        Channel channel = connection.createChannel();
        //参数：1队列名 2.是否持久化 3.是否独占连接 4.是否不用时自动删除 5.队列其他参数
        channel.queueDeclare(queueName, true, false, false, null);

        String message;
        for (int i = 0; i < 10; i++) {
            message = "work queue模式" + i;
            /*参数：交换机名字，默认使用defalut exchange
         2.routingkey，在简单模式可以写queuename
         3.消息的其他属性
         4.消息数据 */
            channel.basicPublish("", queueName, null, message.getBytes());

            System.out.println("send message: " + message);
        }


        //释放资源
        channel.close();
        connection.close();

    }
}
