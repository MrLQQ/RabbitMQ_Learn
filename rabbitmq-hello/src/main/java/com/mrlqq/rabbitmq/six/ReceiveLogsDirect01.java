package com.mrlqq.rabbitmq.six;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LQQ
 */
public class ReceiveLogsDirect01 {

    public static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个队列
        channel.queueDeclare("console",false,false,false,null);

        channel.queueBind("console",EXCHANGE_NAME,"info");
        channel.queueBind("console",EXCHANGE_NAME,"warning");

        // 消息接收成功
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect01控制台打印接受的消息：" + new String(message.getBody(),"UTF-8"));
        };

        // 消息接收失败
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息接受失败");
        };

        channel.basicConsume("console",true,deliverCallback,cancelCallback);

    }
}
