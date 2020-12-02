package com.personal.practice.redis.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.personal.service.RedisOpsService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.transaction.annotation.Transactional;

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
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(500,1000, 5, TimeUnit.SECONDS,new LinkedBlockingDeque());
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

        for (int i=0;i<1;i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    List<Object>  result=redisTemplate.executePipelined(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                            redisConnection.closePipeline();
//                            System.out.println(redisConnection.getClass().getName());
                            System.out.println("pipeline start");
                            for (int j=0;j<1000;j++){
                                redisConnection.incrBy("transaction".getBytes(),1);
                            }
//                            try {
//                                Thread.sleep(10000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println(redisConnection.incrBy("transaction".getBytes(),-1));
                            return null;
                        }
                    });
                    System.out.println(result);

                }
            });
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    Object result=redisTemplate.execute(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                            redisConnection.closePipeline();
//                            System.out.println(redisConnection.getClass().getName
//                           ());
                            System.out.println("dddd start");
                            for (int j=0;j<1000;j++){
                                System.out.println(redisConnection.incrBy("transaction".getBytes(),-1));
                            }
//                            try {
//                                Thread.sleep(10000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println(redisConnection.incrBy("transaction".getBytes(),-1));
                            return null;
                        }
                    });
                    System.out.println(result);

                }
            });
        }




    }

    @Override
    @Transactional
    public void watch() {
        for (int i=0;i<1;i++) {
            ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(500,1000, 5, TimeUnit.SECONDS,new LinkedBlockingDeque());
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {

//                    //TODO 这种自己处理事务的使用方式 不会释放reids链接
//                    redisTemplate.setEnableTransactionSupport(true);
//                    redisTemplate.multi();
//                    System.out.println(redisTemplate.opsForValue().increment("transaction",2));
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(redisTemplate.opsForValue().increment("transaction",-1));
//                    TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
//                    List<Object>  res=redisTemplate.exec();
//                    System.out.println(res);

                    redisTemplate.execute(new SessionCallback() {
                        @Override
                        public Object execute(RedisOperations redisOperations) throws DataAccessException {
                            System.out.println("transaction start");
                            redisOperations.watch("watch");
                            redisOperations.multi();

                            for (int i=0;i<10;i++){
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(redisOperations.opsForValue().increment("transaction", 1));
                            }
                            List<Object> res = redisOperations.exec();
                            System.out.println(res);
                            return null;
                        }
                    });
                }
            });

//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    redisTemplate.execute(new SessionCallback() {
//                        @Override
//                        public Object execute(RedisOperations redisOperations) throws DataAccessException {
//                            System.out.println("dddd start");
//                            for (int i=0;i<1000;i++){
//                                System.out.println(redisOperations.opsForValue().increment("transaction", -1));
//                            }
//                            return null;
//                        }
//                    });
//                }
//            });
        }

    }

    @Override
    public void sentinel() {
        System.out.println("sentinel start");
        for (int i=0;i<1000;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sentinel incr");
            System.out.println(redisTemplate.opsForValue().increment("sentinel"));
        }
    }

    public <T> T lua(String fileClasspath, Class<T> returnType, List<String> keys, Object ... values){
        DefaultRedisScript<T> redisScript=new DefaultRedisScript();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(fileClasspath)));
        redisScript.setResultType(returnType);
        return (T) redisTemplate.execute(redisScript,keys,values);
    }


}
