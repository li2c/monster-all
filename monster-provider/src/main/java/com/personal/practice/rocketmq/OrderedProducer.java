package com.personal.practice.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class OrderedProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer  = new DefaultMQProducer("order-product");
        producer.setNamesrvAddr("127.0.0.1:9876");

        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        //Launch the instance.
        try {
            producer.start();
            for (int i=0;i<1000;i++){
                int orderId=1000;
                Message  message=new Message("order-monster",tags[i%tags.length],"KEY"+i,("第"+i+"条顺序消息").getBytes());
                SendResult sendResult=producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        Integer id = (Integer) o;
                        System.out.println("id为"+id);
                        System.out.println("list.size"+list.size());
                        int index = id % list.size();
                        return list.get(index);
                    }
                },orderId);
                System.out.println(sendResult);
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

}
