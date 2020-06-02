package com.personal.practice.dubbo.provider.mock;

import com.alibaba.dubbo.config.annotation.Service;
import com.personal.service.MockService;

@Service
public class MockServiceImpl implements MockService {
    @Override
    public String mockTest() {
        String s="this is mock test";
        System.out.println(s);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return s;
    }
}
