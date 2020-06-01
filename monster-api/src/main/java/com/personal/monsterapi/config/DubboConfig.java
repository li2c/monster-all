package com.personal.monsterapi.config;

import com.alibaba.dubbo.config.*;
import com.personal.service.ICallbackService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DubboConfig {

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


}
