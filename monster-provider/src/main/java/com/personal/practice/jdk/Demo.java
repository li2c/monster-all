package com.personal.practice.jdk;


import java.util.concurrent.TimeUnit;


public class Demo {

    // 定义一个信号量
    private volatile int signal;

    public static void main(String[] args) {

        Demo demo = new Demo();
        SetTarget set = new SetTarget(demo);
        GetTarget get = new GetTarget(demo);

        // 开启线程，
        new Thread(get).start();
        new Thread(get).start();
        new Thread(get).start();
        new Thread(get).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(set).start();
    }

    // set方法唤醒线程
    public synchronized void set() {
        signal = 1;
        // notify方法会随机叫醒一个处于wait状态的线程
        notify();
        // notifyAll叫醒所有的处于wait线程，争夺到时间片的线程只有一个
        //notifyAll();
        System.out.println("叫醒线程叫醒之后休眠开始...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // get方法使线程进入等待状态
    public synchronized int get() {
        System.out.println(Thread.currentThread().getName() + " 方法执行了...");
        if (signal != 1) {
            try {
                wait();
                System.out.println("叫醒之后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 方法执行完毕...");
        return signal;
    }
}

class GetTarget implements Runnable {
    private Demo demo;
    public GetTarget(Demo demo) {
        this.demo = demo;
    }
    @Override
    public void run() {
        demo.get();
    }
}

class SetTarget implements Runnable{
    private Demo demo;
    public SetTarget(Demo demo) {
        this.demo = demo;
    }
    @Override
    public void run() {
        demo.set();
    }
}
