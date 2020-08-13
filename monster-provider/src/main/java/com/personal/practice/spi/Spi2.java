package com.personal.practice.spi;

import com.personal.practice.spi.inter.SpiInterface;

public class Spi2 implements SpiInterface {
    @Override
    public boolean spiTest() {
        System.out.println("this is spi2");
        return true;
    }
}
