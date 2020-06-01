package com.personal.practice.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.personal.service.IDubboTest;


@Service(register=false,registry="myRegistryConfig",group = "test",retries = 5,protocol={"rmi","dubbo"})
public class DubboProvider implements IDubboTest {


    public void dubboTest()  {
        System.out.println("2323123");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String loadBanlance() {
        return "222222";
    }
}
