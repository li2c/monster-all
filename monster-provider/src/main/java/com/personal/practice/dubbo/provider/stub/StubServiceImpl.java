package com.personal.practice.dubbo.provider.stub;

import com.alibaba.dubbo.config.annotation.Service;
import com.personal.service.StubService;

@Service
public class StubServiceImpl implements StubService {
    @Override
    public void stubTest() {
        System.out.println("this is stub test");
    }
}
