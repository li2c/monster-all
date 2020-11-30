package com.personal.monsterapi.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.personal.monsterapi.consumer.DubboConsumer;
import com.personal.monsterapi.service.IResourceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

@RestController
@RequestMapping("test")
public class ExtendController {
    private static final String RESOURCE_NAME = "testABC";
    int i=0;
    @Autowired
    private DubboConsumer dubboConsumer;

    @Resource
    private IResourceTest resource1;

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


    @RequestMapping("sentinel-auth")
    public Object sentinelAuth(String origin){
        System.out.println("========Testing for black list========");
        initBlackRules();
        testFor(RESOURCE_NAME, "appA");
        testFor(RESOURCE_NAME, "appB");
        testFor(RESOURCE_NAME, "appC");
        testFor(RESOURCE_NAME, "appE");

        System.out.println("========Testing for white list========");
        initWhiteRules();
        testFor(RESOURCE_NAME, "appA");
        testFor(RESOURCE_NAME, "appB");
        testFor(RESOURCE_NAME, "appC");
        testFor(RESOURCE_NAME, "appE");
        return "";


//        initBlackRules();
//        System.out.println(origin);
//        String resource="testABC";
//        ContextUtil.enter(resource, origin);
//        Entry entry = null;
//        try {
//            entry = SphU.entry(resource);
//            System.out.println(String.format("Passed for resource %s, origin is %s", resource, origin));
//        } catch (BlockException ex) {
//            System.err.println(String.format("Blocked for resource %s, origin is %s", resource, origin));
//        } finally {
//            if (entry != null) {
//                entry.exit();
//            }
//            ContextUtil.exit();
//            return "";
//        }

    }

    public Object authback(String origin,Throwable e){
        e.printStackTrace();
        return "authback result";
    }

    private static void initBlackRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }
    private static void initWhiteRules() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(RESOURCE_NAME);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appE");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void testFor(/*@NonNull*/ String resource, /*@NonNull*/ String origin) {
        ContextUtil.enter(resource, origin);
        Entry entry = null;
        try {
            entry = SphU.entry(resource);
            System.out.println(String.format("Passed for resource %s, origin is %s", resource, origin));
        } catch (BlockException ex) {
            System.err.println(String.format("Blocked for resource %s, origin is %s", resource, origin));
        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
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
