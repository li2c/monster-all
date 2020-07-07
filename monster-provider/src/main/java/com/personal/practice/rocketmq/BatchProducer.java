package com.personal.practice.rocketmq;

//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import com.alibaba.rocketmq.common.message.Message;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

public class BatchProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer mqProducer=new DefaultMQProducer("monster-batch");
        mqProducer.setNamesrvAddr("127.0.0.1:9876");
        mqProducer.start();
        List<Message>  messages=new ArrayList<>();
        for (int i=0;i<10;i++){
            Thread.sleep(5000);
            messages.add(new Message("monster-batch",("第"+i+"批消息").getBytes()));
            messages.add(new Message("monster-batch",("第"+i+"批消息").getBytes()));
            messages.add(new Message("monster-batch",("第"+i+"批消息").getBytes()));
            messages.add(new Message("monster-batch",("第"+i+"批消息").getBytes()));
            messages.add(new Message("monster-batch",("第"+i+"批消息").getBytes()));
            System.out.println(mqProducer.send(messages));

        }
        mqProducer.shutdown();
    }
}
