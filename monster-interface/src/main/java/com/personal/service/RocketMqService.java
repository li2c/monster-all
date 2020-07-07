package com.personal.service;

public interface RocketMqService {
   /**
    * 同步发送
    */
   void sendmqMessage();

   /**
    * 异步发送
    */
   void sendAsynMessage();

   /**
    * 单向发送
    */
   void sendOneWay();
}
