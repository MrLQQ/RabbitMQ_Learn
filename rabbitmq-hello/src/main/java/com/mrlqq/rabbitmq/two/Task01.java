package com.mrlqq.rabbitmq.two;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author LQQ
 * 生产者 发送大量的消息
 */
public class Task01 {
    public static final String QUEUE_NAME = "hello";

    // 发送大量消息
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        // 队列的声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 从控制台当中接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            /**
             * 生成一个队列
             * 1.队列名称
             * 2.队列里面的消息是否持久化，默认情况消息存储在内存中
             * 3.该队列是否只供一个消费者进行消费，是否进行消息共享，true可以多个消费者消费，false只能一个消费者消费，默认false
             * 4.是否自动删除，最后一个消费者断开连接后，该队列是否自动删除，true自动删除，false不自动删除
             * 5.其他参数
             */
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成："+message);
        }
    }
}
