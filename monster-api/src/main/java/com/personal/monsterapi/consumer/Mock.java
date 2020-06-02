package com.personal.monsterapi.consumer;

import com.personal.service.MockService;

public class Mock implements MockService {
    @Override
    public String mockTest() {
        return "provider 异常";
    }
}
