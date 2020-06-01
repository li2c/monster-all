package com.personal.monsterapi.consumer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.personal.service.CallbackListener;
import com.personal.service.ICallbackService;
import com.personal.service.IDubboTest;
import org.springframework.stereotype.Service;



@Service
public class DubboConsumer {
    //分组+cache
    @Reference(group = "test",cache="lru")
    private IDubboTest dubboTest;

    @Reference
    private ICallbackService callbackService;

    public Object  testEchoService(String s){
//        EchoService echoService=(EchoService)dubboTest;
//        return echoService.$echo(s);
//        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(20,30, 5,TimeUnit.SECONDS,new LinkedBlockingDeque());
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

    public Object callback(){
        callbackService.addListener("test", new CallbackListener() {
            @Override
            public void doSomething() {
                System.out.println("this is a callback test");
            }
        });
        return "success";
    }

}