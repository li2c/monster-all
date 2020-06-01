package com.personal.practice.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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

    @Bean("dubbo")
    public ProtocolConfig getProtocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setPort(20881);
        protocolConfig.setName("dubbo");
        return protocolConfig;
    }
    @Bean("rmi")
    public ProtocolConfig rmi(){
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setPort(20882);
        protocolConfig.setName("rmi");
        return protocolConfig;
    }
}
