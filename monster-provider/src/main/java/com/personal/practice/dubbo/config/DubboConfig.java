package com.personal.practice.dubbo.config;

import com.alibaba.dubbo.config.*;
import com.personal.service.ICallbackService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Configurable
public class DubboConfig implements InitializingBean {
    @Autowired
    private ICallbackService iCallbackService;

    @Bean("myRegistryConfig")
    public RegistryConfig getRegistryConfig(){
        RegistryConfig registryConfig=new RegistryConfig();
        registryConfig.setAddress("localhost");
        registryConfig.setPort(2181);
        registryConfig.setProtocol("zookeeper");
        return registryConfig;
    }

    @Bean
    public ApplicationConfig  getApplicationConfig(){
        ApplicationConfig applicationConfig=new ApplicationConfig();
        applicationConfig.setName("api-test");
        return applicationConfig;
    }

    @Bean
    public ProtocolConfig getProtocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setPort(20880);
        protocolConfig.setName("dubbo");
        return protocolConfig;
    }

//    @Bean
//    public ICallbackService referenceConfig(){
//        System.out.println("ReferenceConfig init");
//        ServiceConfig<ICallbackService> service = new ServiceConfig<ICallbackService>();// 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
//        MethodConfig method = new MethodConfig();
//        ArgumentConfig argumentConfig=new ArgumentConfig();
//        argumentConfig.setType("com.personal.service.CallbackListener");
//        argumentConfig.setCallback(true);
//        method.setName("addListener");
//        method.setArguments(Arrays.asList(argumentConfig));
//        service.setMethods(Arrays.asList(method)); // 设置方法级配置
//        service.setInterface(ICallbackService.class);
//        service.setApplication(getApplicationConfig());
//        service.setRegistry(getRegistryConfig());
//        service.
//    }

    @Override
    public void afterPropertiesSet() throws Exception {

        ServiceConfig<ICallbackService> service = new ServiceConfig<ICallbackService>();// 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        MethodConfig method = new MethodConfig();
        ArgumentConfig argumentConfig=new ArgumentConfig();
        argumentConfig.setType("com.personal.service.CallbackListener");
        argumentConfig.setCallback(true);
        method.setName("addListener");
        method.setArguments(Arrays.asList(argumentConfig));
        service.setMethods(Arrays.asList(method)); // 设置方法级配置
        service.setInterface(ICallbackService.class);
        service.setApplication(getApplicationConfig());
        service.setRegistry(getRegistryConfig());
        service.setRef(iCallbackService);
        service.export();
    }
}
