package com.personal.practice.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class FilterConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("filter-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("monster-filter",
                MessageSelector.bySql("lcc is not null"));

       consumer.registerMessageListener(new MessageListenerConcurrently() {
           @Override
           public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
               for (MessageExt messageExt:list){
                   try {
                       System.out.println("receive message ID"+messageExt.getMsgId()+",body"+new String(messageExt.getBody(),"utf-8"));
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }
               }
               return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
           }

       });
       consumer.start();
    }
}
