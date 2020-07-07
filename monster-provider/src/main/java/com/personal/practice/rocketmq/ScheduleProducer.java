package com.personal.practice.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class ScheduleProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer=new DefaultMQProducer("monster-schedule");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i=0;i<100;i++){
            Message message=new Message("monster-schedule",("第"+i+"条message").getBytes());
            message.setDelayTimeLevel(3);
            System.out.println(producer.send(message));
        }
        producer.shutdown();
    }
}
