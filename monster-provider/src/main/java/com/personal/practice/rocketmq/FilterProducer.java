package com.personal.practice.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class FilterProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer=new DefaultMQProducer("monster-filter");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.start();

        for (int i=0;i<10;i++){
            Message message=new Message("monster-filter","filter-test".getBytes());
            message.putUserProperty("lcc","filter");
            System.out.println(defaultMQProducer.send(message));
        }
        for (int i=0;i<10;i++){
            Message message=new Message("monster-filter","do filter".getBytes());
            message.putUserProperty("ccl","test");
            System.out.println(defaultMQProducer.send(message));;
        }
        defaultMQProducer.shutdown();
    }
}
