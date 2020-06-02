package com.personal.monsterapi.consumer;

import com.personal.service.StubService;
import org.springframework.stereotype.Component;

//TODO 不能注入 否则循环依赖
//@Component("sub")
public class Stub  implements StubService{
    private StubService stubService;

    public Stub(StubService stubService){
        this.stubService=stubService;
    }

//    @Override
    public void stubTest() {
        System.out.println("this is stub");
        stubService.stubTest();
    }
}