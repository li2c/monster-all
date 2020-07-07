package com.personal.monsterapi.controller;

import com.personal.monsterapi.consumer.DubboConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class ExtendController {
    @Autowired
    private DubboConsumer dubboConsumer;

    @RequestMapping("echoService")
    public Object echoService(String echo){
        return dubboConsumer.testEchoService(echo);
    }

    @RequestMapping("callback")
    public Object callback(){
        return dubboConsumer.callback();
    }

    @RequestMapping("stub")
    public Object stub(){
        return dubboConsumer.stub();
    }

    @RequestMapping("mock")
    public Object mock(){
        return dubboConsumer.mock();
    }

    @RequestMapping("dynamicMock")
    public Object dynamicMock(){
        return dubboConsumer.dynamicMock();
    }

    @RequestMapping("redis")
    public Object redis(){
        return dubboConsumer.redis();
    }

    @RequestMapping("transcation")
    public Object transcation(){
        return dubboConsumer.transcation();
    }

    @RequestMapping("watch")
    public Object watch(){
        return dubboConsumer.watch();
    }

    @RequestMapping("sentinel")
    public Object sentinel(){
        return dubboConsumer.sentinel();
    }

    @RequestMapping("mq")
    public Object mq(){
        return dubboConsumer.mq();
    }
    @RequestMapping("asynmq")
    public Object asynmq(){
        return dubboConsumer.asynmq();
    }
    @RequestMapping("oneWaySend")
    public Object oneWaySend(){
        return dubboConsumer.oneWaySend();
    }


}
