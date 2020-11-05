package com.personal.practice.jvm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Jstacktest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(10,20,30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(30));
        while(true){
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

        }
    }

}
