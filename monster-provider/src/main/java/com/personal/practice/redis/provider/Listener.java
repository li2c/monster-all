package com.personal.practice.redis.provider;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class Listener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
//        System.out.println("=========consumer==========");
//        System.out.println(new String(message.getBody()));
//        System.out.println(new String(message.getChannel()));
//        System.out.println(new String(bytes));
        System.out.println(new String(message.getBody()));

    }
}

