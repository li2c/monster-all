package com.personal.practice.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.personal.practice.spi.inter.SpiInterface;


import java.sql.DriverManager;
import java.util.ServiceLoader;

public class Spi   extends ClassLoader  implements SpiInterface{

    @Override
    public boolean spiTest() {
        System.out.println("spi impl");
        return true;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("未找到类");
        return super.findClass(name);
    }

    public static void main(String[] args) {
//        ServiceLoader<SpiInterface> loaders = ServiceLoader.load(SpiInterface.class);
//        for (SpiInterface spi : loaders) {
//            spi.spiTest();
//        }
//        Spi spi=new Spi();
//        try {
//            spi.loadClass("test.class");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        Thread t=new Thread();
//        System.out.println(Thread.currentThread().getContextClassLoader());
//        DriverManager.getConnection();
//        new Thread();
//        spi.getClass().getClassLoader();

        //dubbo spi
        ExtensionLoader<SpiInterface> extensionLoader =
                ExtensionLoader.getExtensionLoader(SpiInterface.class);
        SpiInterface test = extensionLoader.getExtension("test2");
        test.spiTest();
    }
}
