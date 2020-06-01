package com.personal.practice.zookeeper.apache;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateZnode {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper=ZookeeperSession.getSesson();
        if (zooKeeper==null){
            System.out.println("connect failed");
            return;
        }
        String path1=zooKeeper.create("/zk-test-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: "+path1);
        String path2=zooKeeper.create("/zk-test-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: "+path2);

        zooKeeper.create("/zk-kex-","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL,new CallBack(),"I am context");

        Thread.sleep(Integer.MAX_VALUE);

    }

    public static class CallBack implements AsyncCallback.StringCallback{

        @Override
        public void processResult(int rc, String path, Object o, String s1) {
            System.out.println("create result:"+rc+","+path+","+o+","+s1);
        }
    }
}
