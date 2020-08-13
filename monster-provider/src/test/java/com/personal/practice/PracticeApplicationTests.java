package com.personal.practice;

import com.personal.service.RedisOpsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
class PracticeApplicationTests {
    @Resource
   private  RedisOpsService redisOpsService;


    @Test
    void contextLoads() {
    }

    @Test
    void luaTest(){
        System.out.println(redisOpsService.lua("lua",Long.class, Arrays.asList("mykey","someKey"),new Integer[]{1,2}));
    }

}
