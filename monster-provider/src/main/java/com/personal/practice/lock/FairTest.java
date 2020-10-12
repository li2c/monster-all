package com.personal.practice.lock;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FairTest {

    public static void main(String[] args) throws InterruptedException {
        FairRunner runner=new FairRunner();
        for (int i=0;i<10;i++){
            Thread thread=new Thread(runner);
            thread.start();
        }
    }
    static class FairRunner implements Runnable{
        ReentrantLock lock=new ReentrantLock(true);
        @Override
        public void run() {
            ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
            lock.readLock();
            System.out.println(Thread.currentThread().getName()+"请求锁");;
            lock.readLock();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"获得了锁");
            lock.readLock();
        }
    }
}
