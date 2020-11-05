package com.personal.monsterapi.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.personal.monsterapi.consumer.DubboConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class ExtendController {
    int i=0;
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

    @RequestMapping("resolveTest")
    public  Object resolveTest(Number user){
        return "success";
    }

    @RequestMapping("sentinel-test")
    public Object sentinelTest(){
        Entry entry=null;
        try {
            entry=SphU.entry("first-sentinel-test");
            return "healthy response";
        } catch (BlockException e) {
            e.printStackTrace();
            return "response after block";
        }finally {
            // SphU.entry(xxx) 需要与 entry.exit() 成对出现,否则会导致调用链记录异常
            if (entry!=null){
                entry.exit();
            }
        }
    }

    @SentinelResource(value = "first-sentinel-test",blockHandler = "handleFlowQpsException",fallback = "fallback")
    @RequestMapping("sentinel-aspect")
    public Object aspectSentinel(){
        return "healthy response";

    }

    public Object handleFlowQpsException(BlockException e){
        e.printStackTrace();
        return "after block response";
    }

    public Object fallback(Throwable e){
        e.printStackTrace();
        return "fall back response";
    }

    @SentinelResource(value="degradeTest",fallback = "fallback")
    @RequestMapping("sentinel-degrade")
    public Object degradeSentinel(){
        System.out.println(i);
        if (i/2==0){
            throw new RuntimeException();
        }
        return "healthy response";
    }

}


//class  User{
//    private String name;
//    private String age;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//}
