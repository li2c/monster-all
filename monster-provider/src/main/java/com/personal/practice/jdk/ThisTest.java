package com.personal.practice.jdk;

public class ThisTest {
    public static void main(String[] args) {
        ThisTest thisTest=new ThisTest();
        thisTest.test1();
    }

    public void test1(){
        new C().m();
    }
    public class A{
        void p(){
            System.out.println("a.p");
        }
    }
    public class B extends A {
        void p(){
            System.out.println("b.p");
        }
        void m(){
            System.out.println("b.m");
            this.p();
            System.out.println(this.getClass().getName());
            super.p();
            System.out.println(super.getClass().getSuperclass().getName());
        }
    }
    public class C extends B {
        void p(){
            System.out.println("c.p");
        }
    }
}
