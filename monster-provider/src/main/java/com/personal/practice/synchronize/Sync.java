package com.personal.practice.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Sync {
    public void SyncTest(){
        synchronized (this){
            System.out.println("sync");
        }
    }

    public synchronized void methodSyncTest(){
        System.out.println("method sync");
    }

    public static void main(String[] args) {
//        Lock
//        ReentrantLock

    }

}
