package com.personal.practice.shardingsphere;

import com.personal.practice.entity.Student;
import com.personal.practice.mapper.StudentMapper;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class TransactionServiceImpl implements TransactionService, ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.BASE)
    public void transactionTest() {
        Random random=new Random();
        long begin=System.currentTimeMillis();
        int i=0;
        while (true){
            insert(random);
            insert2(random);
            System.out.println("i:+"+ ++i);
//            if (System.currentTimeMillis()-begin>5000){
//                int j=1/0;
//            }
        }

    }


    @Transactional(propagation= Propagation.REQUIRES_NEW)
//    @ShardingTransactionType(TransactionType.LOCAL)
    public void insert(Random random){
        StudentMapper studentMapper=applicationContext.getBean(StudentMapper.class);
        System.out.println(studentMapper.toString());
        Student student = new Student();
        student.setAge(1);
        student.setName("dasd");

        student.setMoney(random.nextInt(100));
        student.setScore(100);
        studentMapper.insert(student);
        if(random.nextInt(100)>98){
            int i=1/0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert2(Random random){
        StudentMapper studentMapper=applicationContext.getBean(StudentMapper.class);
        System.out.println(studentMapper.toString());
        Student student = new Student();
        student.setAge(1);
        student.setName("dasd");

        student.setMoney(random.nextInt(100));
        student.setScore(100);
        studentMapper.insert(student);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
