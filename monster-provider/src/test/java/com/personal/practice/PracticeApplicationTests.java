package com.personal.practice;

import com.personal.practice.entity.Student;
import com.personal.practice.mapper.StudentMapper;
import com.personal.practice.shardingsphere.TransactionService;
import com.personal.service.RedisOpsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import sun.nio.ch.ThreadPool;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class PracticeApplicationTests implements ApplicationContextAware {
    @Resource
   private  RedisOpsService redisOpsService;

    @Resource
    private RedisTemplate redisTemplate;

//    @Resource
//    private StudentMapper studentMapper;

    @Resource
    private TransactionService transactionService;

    private ApplicationContext applicationContext;


    @Test
    void contextLoads() {
    }

    @Test
    void luaTest(){
        System.out.println(redisOpsService.lua("lua",Long.class, Arrays.asList("mykey","someKey"),new Integer[]{1,2}));
    }

    @Test
    void mysqlTest(){
        for (int i=1;i<2;i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    Random random=new Random();
                    while (true){
                        StudentMapper studentMapper=applicationContext.getBean(StudentMapper.class);
                        System.out.println(studentMapper.toString());
                        Student student = new Student();
                        student.setAge(new BigDecimal(1));
                        student.setName("dasd");
                        student.setMoney(random.nextInt(100));
                        student.setScore(i);
                        studentMapper.insert(student);
                    }

                }
            });
            t.start();
        }
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void decimalTest(){
        StudentMapper studentMapper=applicationContext.getBean(StudentMapper.class);
        System.out.println(studentMapper.toString());
        List<Student>  students= studentMapper.listStudent();
        System.out.println(students);
    }


    @Test
    void transactionTest(){
        transactionService.transactionTest();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext=applicationContext;
    }

    @Test
    public void redisDecrTest(){
        for(int i=0;i<50;i++){
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    long begin=System.currentTimeMillis();
                    while(true){
                        List<Object> res=(List<Object>)redisTemplate.execute(new SessionCallback<List<Object>>() {
                            @Override
                            public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                                redisTemplate.watch("decr:key");
                                redisOperations.multi();
                                redisTemplate.opsForValue().decrement("decr:key");
//                                redisOperations.unwatch();
                                List<Object> res = redisOperations.exec();

                                return res;
                            }
                        });
                        if (!res.isEmpty()){
                            System.out.println("damn,it takes me "+(System.currentTimeMillis()-begin)+"ms");
                            break;
                        }

                    }
                }
            });
            t.start();
        }
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
