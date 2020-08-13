package com.personal.practice.zookeeper.apache;


import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperSession {
    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper=getSesson();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ZooKeeper getSesson() throws IOException {
        ZooKeeper zookeeper=new ZooKeeper("localhost:2182",5000,new Watcher(1));
        System.out.println(zookeeper.getState());
        try {
            Watcher.countDownLatch.await();
            long sessionID=zookeeper.getSessionId();
            byte[] passwd=zookeeper.getSessionPasswd();
            System.out.println("Zookeeper sessoin established");

            //error passwod
//            ZooKeeper zookeeperError=new ZooKeeper("localhost:2181",5000,new Watcher(2),1l,"test".getBytes());
//            System.out.println(zookeeperError.getState());
//
//            ZooKeeper zookeeperCorrect=new ZooKeeper("localhost:2181",5000,new Watcher(3),sessionID,passwd);
//            System.out.println(zookeeperCorrect.getState());
//
//            //TODO 不sleep的话 主进程结束无法接受zk的事件
            Thread.sleep(Integer.MAX_VALUE);

        } catch (InterruptedException e) {
            System.out.println("Zookeeper sessoin established");
            e.printStackTrace();
        }
        return zookeeper;
    }

    public static class Watcher implements org.apache.zookeeper.Watcher{
        int id;
        Watcher(int id){
            this.id=id;
        }
        public static CountDownLatch countDownLatch=new CountDownLatch(1);

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("Receive watched event:"+watchedEvent+this.id);
            if (Event.KeeperState.SyncConnected==watchedEvent.getState()){
                countDownLatch.countDown();
            }
        }
    }
}
