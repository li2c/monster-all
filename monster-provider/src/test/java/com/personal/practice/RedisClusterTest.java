package com.personal.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisClusterTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisTest(){
        for (int i=0;i<100;i++){
            String keys="lcc"+i;
            redisTemplate.opsForValue().set(keys,i);
//            redisTemplate.delete(keys);
            System.out.println("success");
        }

    }
}
