package com.mrlqq.rabbitmq.eight;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LQQ
 *
 * 死信队列 实战
 *
 * 消费者2
 */
public class Consumer02 {

    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("等待接收消息……");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02接收到消息：" + new String(message.getBody(),"UTF-8"));

        };
        CancelCallback cancelCallback = consumerTag -> {};
        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,cancelCallback);
    }
}
