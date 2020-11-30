package com.personal.monsterapi.test;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        List<Long> memberIds=new ArrayList<>();
        for (long i=0;i<3600;i++){
            memberIds.add(i);
        }
        boolean hasleft=true;
        int index=0;
        int pageSize=450;
        while(hasleft){
            int endIndex=Math.min((index+1)*pageSize,memberIds.size());
            if (endIndex>=memberIds.size()){
                hasleft=false;
            }
            List<Long> executeMemberIds=memberIds.subList(index++*pageSize,endIndex);
            ExecutorService executorService=  Executors.newCachedThreadPool();

            if (CollectionUtils.isNotEmpty(executeMemberIds)){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(executeMemberIds);
                    }
                });
            }
            //TODO 很关键 一定要shutdown！否则，不仅核心线程不会回收，业务线程也不会结束，很危险
            try {
                Thread.sleep(20000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            executorService.shutdown();
        }


        }
    }
