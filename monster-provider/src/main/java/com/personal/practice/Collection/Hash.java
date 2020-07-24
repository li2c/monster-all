package com.personal.practice.Collection;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.Lock;

public class Hash {
    public static void main(String[] args) {
        ArrayList<Integer>  list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4); list.add(5); list.add(6); list.add(7);


//        for (int i=0;i<list.size();i++){
//            list.remove(i);
//            System.out.println(list);
//        }


//        for(Integer i:list){
//            list.remove(i);
//
//        }


//        Iterator it=list.iterator();
//        while (it.hasNext()){
//            it.next();
//            it.remove();
//        }
        TreeSet set=new TreeSet();
        set.add(3);
        set.add(5);
        set.add(1);
        set.add(4);
        Iterator it=set.iterator();
        while (it.hasNext()){
            System.out.println(it.next());

        }
//        DelayQueue
        ConcurrentHashMap
        CopyOnWriteArrayList;






    }
}
