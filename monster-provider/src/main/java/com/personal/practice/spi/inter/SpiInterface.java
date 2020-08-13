package com.personal.practice.spi.inter;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface SpiInterface {
    boolean spiTest();
}
