package com.personal.monsterapi.consumer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.alibaba.dubbo.rpc.RpcContext;
import com.personal.service.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service
public class DubboConsumer {
    //分组+cache
    @Reference(group = "test",cache="lru")
    private IDubboTest dubboTest;

    @Reference
    private ICallbackService callbackService;

    @Reference(stub = "com.personal.monsterapi.consumer.Stub")
    private StubService stubService;

    @Reference
//            (mock="com.personal.monsterapi.consumer.Mock")
    private MockService mockService;

    @Reference(cluster = "failfast",retries = 0)
    private RedisOpsService redisOpsService;

    public Object  testEchoService(String s){
        //线程池测试：
//        EchoService echoService=(EchoService)dubboTest;
//        return echoService.$echo(s);
//        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(20,30, 5, TimeUnit.SECONDS,new LinkedBlockingDeque());
//        for (int i=0;i<20;i++){
//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    dubboTest.dubboTest();
//                }
//            });
//        }
        //
        RpcContext.getContext().setAttachment("lcc", "enigne");
        dubboTest.dubboTest();
        //rpc context 注意：每发起RPC调用，上下文状态会变化
        boolean isConsumerSide = RpcContext.getContext().isConsumerSide();
        System.out.println(isConsumerSide);
        // 获取最后一次调用的提供方IP地址
        String serverIP = RpcContext.getContext().getRemoteHost();
        System.out.println(serverIP);
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getContext().getUrl().getParameter("application");
        System.out.println(application);
        URL url = RpcContext.getContext().getUrl();
        System.out.println(url);
        return dubboTest.loadBanlance();
    }

    //参数回调
    public Object callback(){
        callbackService.addListener("test", new CallbackListener() {
            @Override
            public void doSomething() {
                System.out.println("this is a callback test");
            }
        });
        return "success";
    }

    //本地存根
    public Object stub(){
        stubService.stubTest();
        return "success";
    }

    public Object mock(){
       return mockService.mockTest();
    }

    public Object dynamicMock(){
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://localhost:2181"));
        registry.register(URL.valueOf("override://192.168.1.103/com.personal.service.MockService?application=api-test&check=false&dubbo=2.6.0&interface=com.personal.service.MockService&methods=mockTest&pid=47349&side=consumer&timestamp=1591113907771&mock=force:return+null&category=configurators&dynamic=false"));
        return "success";
    }

    public Object redis(){
       redisOpsService.subscribe();
        return "success";
    }

    public  Object transcation(){
        redisOpsService.transaction();
        return "success";
    }

}