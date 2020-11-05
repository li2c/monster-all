package com.personal.practice.jdk;

import java.math.BigDecimal;

public class AddTest {
    public static void main(String[] args) {
//        int a=3;
////        int b=a++ + ++a;
////        System.out.println("a:"+a);
////        System.out.println("b:"+b);
//        double d=1d/10;
//        int i=10;
//
//        double s=i;
//        System.out.println(d);
        System.out.println("7*0.1=" + (0.1f+0.1f+0.1f+0.1f+0.1f+0.1f+0.1f));
        System.out.println("6*0.1=" + (0.1f+0.1f+0.1f+0.1f+0.1f+0.1f));
        BigDecimal bigDecimal=new BigDecimal("7.22");
        BigDecimal bigDecimal1=new BigDecimal("7.12");
        BigDecimal diff=bigDecimal.subtract(bigDecimal1);
        System.out.println(diff);


    }
}
