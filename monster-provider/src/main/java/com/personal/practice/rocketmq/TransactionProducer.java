package com.personal.practice.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionProducer {

    public static void main(String[] args) throws MQClientException {
        AtomicInteger transactionIndex=new AtomicInteger(0);
        ConcurrentHashMap<String,Integer> localTrans=new ConcurrentHashMap<>();
        TransactionListener transactionListener=new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                System.out.println("executeLocalTransaction");
                int value=transactionIndex.getAndIncrement();
                int status=value%3;
                localTrans.put(message.getTransactionId(),status);
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("checkLocalTransaction");
                Integer status=localTrans.get(messageExt.getTransactionId());
                if (null!=status){
                    switch (status){
                        case 0:
                            return LocalTransactionState.UNKNOW;
                        case 1:
                            return LocalTransactionState.COMMIT_MESSAGE;
                        case 2:
                            return LocalTransactionState.ROLLBACK_MESSAGE;
                    }
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        };
        TransactionMQProducer producer=new TransactionMQProducer("monster-transaction");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setTransactionListener(transactionListener);
        producer.start();
        for (int i=0;i<1000;i++){
            Message msg=new Message("monster-transaction",("第"+i+"条消息").getBytes());
            System.out.println(producer.sendMessageInTransaction(msg,null));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
