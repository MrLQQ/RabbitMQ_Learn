package com.mrlqq.rabbitmq.six;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author LQQ
 */
public class ReceiveLogsDirect02 {

    public static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个队列
        channel.queueDeclare("disk",false,false,false,null);

        channel.queueBind("disk",EXCHANGE_NAME,"error");

        // 消息接收成功
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect02控制台打印接受的消息：" + new String(message.getBody(),"UTF-8"));
        };

        // 消息接收失败
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息接受失败");
        };

        channel.basicConsume("disk",true,deliverCallback,cancelCallback);

    }
}
