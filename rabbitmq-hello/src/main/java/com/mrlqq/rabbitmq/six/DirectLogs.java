package com.mrlqq.rabbitmq.six;

import com.mrlqq.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class DirectLogs {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.DIRECT);

        //创建多个 bindingKey
        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("info","普通 info 信息");
        bindingKeyMap.put("warning","警告 warning 信息");
        bindingKeyMap.put("error","错误 error 信息");

        //debug 没有消费这接收这个消息 所有就丢失了
        bindingKeyMap.put("debug","调试 debug 信息");

        // 发送消息
        for (Map.Entry<String, String> bindingKeyEntry: bindingKeyMap.entrySet()){
            String bindingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME,bindingKey, null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息:" + message);
        }
    }
}
