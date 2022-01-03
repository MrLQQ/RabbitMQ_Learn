package com.mrlqq.rabbitmq.three;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @author LQQ
 *
 * 消息在手动应答时不丢失，放回队列中重新消费
 */
public class Task2 {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 声明队列
        boolean durable = true; // 需要让Queue进行持久化
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        // 从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
           // 设置生产者发送消息未持久化消息（要求保存在磁盘上）
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println("生产者发出消息：" + message);
        }
    }
}
