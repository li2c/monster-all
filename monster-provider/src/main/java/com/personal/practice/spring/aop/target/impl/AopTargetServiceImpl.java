package com.personal.practice.spring.aop.target.impl;

import com.personal.practice.spring.aop.target.AopTargetService;
import org.springframework.stereotype.Component;

@Component
public class AopTargetServiceImpl implements AopTargetService {
    @Override
    public void aopTest(int test) {
        System.out.println("aopTest service"+test);
    }
}
