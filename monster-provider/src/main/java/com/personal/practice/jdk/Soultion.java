package com.personal.practice.jdk;

import java.util.ArrayList;
import java.util.Arrays;

public class Soultion{
    public Object lock=new Object();
    public Integer countOut=1;
    public Integer index=0;
    public static void main(String args[]){
//        int n=Integer.valueOf(args[0]);
        Soultion soultion=new Soultion();
        soultion.soult(9);
    }

    public void soult(int n){
        //获取数列
        int[] sequence=getSequence(n);
        //交替输出1+2+3+4
        Run run=new Run(sequence,this.index,this.countOut,lock);
        Thread t1=new Thread(run);
        Thread t2=new Thread(run);
        t1.start();
        t2.start();
    }


    public int[] getSequence(int n){
        int[] dp=new int[n];
        dp[0]=0;
        dp[1]=1;
        for(int i=2;i<n;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp;
    }
}

class Run implements Runnable{
    public Object lock;
    public int[] sequence;
    public Integer index;
    public Integer countOut;

    Run(int[] sequence,Integer index,Integer countOut,Object lock){
        this.sequence=sequence;
        this.index=index;
        this.countOut=countOut;
        this.lock=lock;
    }


    @Override
    public void run(){
        synchronized(lock){
            while(true){
                System.out.println("线程被唤醒："+Thread.currentThread().getName());
//                System.out.println("index"+index);
                int end =this.countOut+index;
                if (index>=sequence.length-1){
                    break;
                }
                for(int i=index;i<end&&index<sequence.length;i++){
                    if(i==end-1){
                        System.out.println(sequence[i]);
                        this.index++;
                    }else{
                        System.out.print(sequence[i]+",");
                        this.index++;
                    }
                }

                countOut++;
//                在这notify？
                lock.notifyAll();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lock.wait();
//                    System.out.println("发生了啥"+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    System.out.println("被唤醒了");
                    e.printStackTrace();
                }
            }
        }
    }
}
