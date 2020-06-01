package com.personal.practice.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.personal.service.IDubboTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@Service(register=false,registry="myRegistryConfig",group = "test",cluster = "failover")
public class DubboProvider implements IDubboTest {
    @Resource(name = "redisTemplate2")
    private RedisTemplate redisTemplate;


    public void dubboTest(){
        JedisConnectionFactory jedisConnectionFactory=(JedisConnectionFactory)redisTemplate.getConnectionFactory();
        System.out.println(jedisConnectionFactory.getDatabase());
        System.out.println(jedisConnectionFactory.getPassword());
        System.out.println(jedisConnectionFactory.getHostName());
        System.out.println(jedisConnectionFactory.getPort());
        System.out.println(jedisConnectionFactory.getPoolConfig().toString());

        redisTemplate.opsForValue().set("name","lcc");
        System.out.println("666666");
        System.out.println(RpcContext.getContext().getAttachment("lcc"));
    }

    @Override
    public String loadBanlance() {
        return "111111111";
    }
}
