package com.personal.practice;

import com.personal.practice.spring.aop.target.AopTargetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopTest {
    @Autowired
    private AopTargetService aopTargetService;

    @Test
    public void aopTest(){
        aopTargetService.aopTest(3);
    }
}
