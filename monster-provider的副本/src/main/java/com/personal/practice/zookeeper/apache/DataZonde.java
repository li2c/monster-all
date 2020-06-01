package com.personal.practice.zookeeper.apache;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class DataZonde {
    public static ZooKeeper zooKeeper=null;
    public static Stat stat=new Stat();
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
         zooKeeper=ZookeeperSession.getSesson();
        System.out.println(new String(zooKeeper.getData("/zk-kex-",new Watch(),stat)));

        Stat stat1=zooKeeper.setData("/zk-kex-","123".getBytes(),-1);
        System.out.println(stat);

        Stat  stat2=zooKeeper.setData("/zk-kex-","456".getBytes(),stat1.getVersion());
        System.out.println(stat2);


        Stat  stat3=zooKeeper.setData("/zk-kex-","789".getBytes(),stat1.getVersion());
        System.out.println(stat3);



        Thread.sleep(Integer.MAX_VALUE);


    }
    public static class Watch implements Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {

            if (watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    System.out.println(new String(zooKeeper.getData("/zk-kex-",new Watch(),stat)));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
