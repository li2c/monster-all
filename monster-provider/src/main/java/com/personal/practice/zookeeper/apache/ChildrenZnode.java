package com.personal.practice.zookeeper.apache;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class ChildrenZnode {
    public static ZooKeeper zooKeeper;
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        getChildren();

    }

    public  static  void getChildren() throws IOException, KeeperException, InterruptedException {
         zooKeeper=ZookeeperSession.getSesson();
        List<String>  childrens=zooKeeper.getChildren("/",new Watcher());
        System.out.println(childrens);

        Thread.sleep(Integer.MAX_VALUE);


    }

    public static class Watcher implements org.apache.zookeeper.Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                try {
                    //TODO 注意watch是一次性的需要反复注册
                    System.out.println("ReGet child:"+zooKeeper.getChildren("/",this));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
