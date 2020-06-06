package com.personal.practice.redis.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.personal.service.RedisOpsService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service(cluster = "failfast")
public class RedisOpsServiceImpl implements RedisOpsService {
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public void subscribe() {
        for (int i=0;i<Integer.MAX_VALUE;i++){
            redisTemplate.convertAndSend("subTest","第"+i+"条message");
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void transaction() {
        //没有事务
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(20,30, 5, TimeUnit.SECONDS,new LinkedBlockingDeque());
//        for (int i=0;i<3;i++){
//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(redisTemplate.opsForValue().increment("transaction"));
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(redisTemplate.opsForValue().increment("transaction",-1));
//                }
//            });
//        }
        //事务
        for (int i=0;i<3;i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    List<Object>  result=redisTemplate.executePipelined(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            redisConnection.closePipeline();
                            System.out.println(redisConnection.getClass().getName());
                            System.out.println(redisConnection.set("transaction".getBytes(),"2".getBytes()));
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(redisConnection.incrBy("transaction".getBytes(),-1));
                            return null;
                        }
                    });
                    System.out.println(result);

                }
            });
        }



    }
}
