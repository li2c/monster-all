package com.personal.practice.jvm;

import com.personal.practice.nio.FileChannelControl;

public class JvmTest extends FileChannelControl implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        testGC();

    }
    public Object instance=null;
    private static final int _1MB=1024*1024;

    private byte[] bigSize=new byte[2*_1MB];

    public static void testGC() throws InterruptedException {
        JvmTest a=new JvmTest();
        JvmTest b=new JvmTest();
        a.instance=b;
        b.instance=a;
        a=null;
        b=null;
        System.gc();

    }

    @Override
    public void run() {

    }
}
