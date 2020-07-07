package com.personal.practice.rocketmq;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.personal.service.RocketMqService;

@Service
public class SynchronousSender  implements RocketMqService {

    public static void main(String[] args) {
        SynchronousSender synchronousSender=new SynchronousSender();
        synchronousSender.sendmqMessage();
    }
    public void sendmqMessage() {
        DefaultMQProducer producer = new DefaultMQProducer(System.currentTimeMillis()+"");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();
            for (int i = 0; i < 1000; i++) {
                Thread.sleep(5000);
                Message message = new Message();
                message.setTopic("monster");
                message.setBody(("第" + i + "个message").getBytes());
                message.setTags("monster-a");
                SendResult result = producer.send(message);
//                System.out.println(message);
                System.out.println(result);
            }
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAsynMessage() {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        try {
            producer.start();
            producer.setRetryTimesWhenSendFailed(0);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        for (int i=0;i<1000;i++){
            Message message = new Message();
            message.setTopic("monster");
            message.setBody(("第" + i + "个Asynmessage").getBytes());
            message.setTags("monster-a");
            try {
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println(sendResult);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void sendOneWay() {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        try {
            producer.start();
            producer.setRetryTimesWhenSendFailed(0);
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = new Message();
            message.setTopic("oneWay");
            message.setBody(("第" + i + "个message").getBytes());
            message.setTags("monster-a");
            try {
            producer.sendOneway(message);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
