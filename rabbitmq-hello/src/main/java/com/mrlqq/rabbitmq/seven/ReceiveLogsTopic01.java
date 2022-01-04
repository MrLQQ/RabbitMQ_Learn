package com.mrlqq.rabbitmq.seven;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LQQ
 *
 * 声明主题交换机 及相关队列
 *
 * 消费者C1
 */
public class ReceiveLogsTopic01 {

    // 交换机名称
    public static final String EXCHANGE_NAME = "topic_logs";

    // 接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列
        String queueName = "Q1";
        channel.queueDeclare(queueName,false,false,false,null   );
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");
        System.out.println("ReceiveLogsTopic01等待接收消息……");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody(),"UTF-8"));
            System.out.println("接收队列：" + queueName + " 绑定键：" + message.getEnvelope().getRoutingKey());
        };

        CancelCallback cancelCallback = consumerTag -> {

        };
        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}
