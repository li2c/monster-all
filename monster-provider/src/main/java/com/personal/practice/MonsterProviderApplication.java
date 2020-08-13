package com.personal.practice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@EnableDubbo
@SpringBootApplication
public class MonsterProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonsterProviderApplication.class, args);

        ClassPathXmlApplicationContext c=new ClassPathXmlApplicationContext("");
        c.getBean("");
    }

}
