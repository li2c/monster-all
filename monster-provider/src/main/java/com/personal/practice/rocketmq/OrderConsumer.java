package com.personal.practice.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class OrderConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderconsumer");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe("order-monster", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            AtomicLong consumeTimes = new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                this.consumeTimes.incrementAndGet();
                System.out.println("consumeTime:"+consumeTimes.get());
                if ((this.consumeTimes.get() % 2) == 0) {
                    System.out.println("消息消费成功");
                    return ConsumeOrderlyStatus.SUCCESS;
                } else if ((this.consumeTimes.get() % 3) == 0) {
                    System.out.println("消息消费回滚");
                    return ConsumeOrderlyStatus.ROLLBACK;
                } else if ((this.consumeTimes.get() % 4) == 0) {
                    System.out.println("消息消费提交");
                    return ConsumeOrderlyStatus.COMMIT;
                } else if ((this.consumeTimes.get() % 5) == 0) {
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    System.out.println("消息消费挂起");
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                System.out.println("消息消费成功");
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }

    public static class OrderConsumer2 {
        public static void main(String[] args) throws Exception {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderconsumer");

            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setNamesrvAddr("127.0.0.1:9876");

            consumer.subscribe("order-monster", "TagA || TagC || TagD");

            consumer.registerMessageListener(new MessageListenerOrderly() {

                AtomicLong consumeTimes = new AtomicLong(0);
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                           ConsumeOrderlyContext context) {
                    context.setAutoCommit(true);
                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                    this.consumeTimes.incrementAndGet();
                    System.out.println("consumeTime:"+consumeTimes.get());
                    if ((this.consumeTimes.get() % 2) == 0) {
                        System.out.println("消息消费成功");
                        return ConsumeOrderlyStatus.SUCCESS;
                    } else if ((this.consumeTimes.get() % 3) == 0) {
                        System.out.println("消息消费回滚");
                        return ConsumeOrderlyStatus.ROLLBACK;
                    } else if ((this.consumeTimes.get() % 4) == 0) {
                        System.out.println("消息消费提交");
                        return ConsumeOrderlyStatus.COMMIT;
                    } else if ((this.consumeTimes.get() % 5) == 0) {
                        context.setSuspendCurrentQueueTimeMillis(3000);
                        System.out.println("消息消费挂起");
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    System.out.println("消息消费成功");
                    return ConsumeOrderlyStatus.SUCCESS;

                }
            });

            consumer.start();

            System.out.printf("Consumer Started.%n");
        }
    }
}
