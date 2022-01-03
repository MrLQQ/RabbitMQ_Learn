package com.mrlqq.rabbitmq.three;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.mrlqq.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class work04 {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    // 接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接收消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            // 沉睡1秒
            SleepUtils.sleep(30);
            System.out.println("接收到的消息：" + new String(message.getBody(),"UTF-8"));
            // 手动应答
            /**
             * 1.消息的标记tag
             * 2.是否批量应答 true:批量应答 false:不批量应答信道中的消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag+"消费者取消消费接口回调逻辑");
        };
        // 设置不公平分发
        //int prefetchCount = 1;
        // 预取值
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);
        // 采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,cancelCallback);
    }
}