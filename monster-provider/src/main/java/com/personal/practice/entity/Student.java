package com.personal.practice.entity;

import java.math.BigDecimal;

public class Student {
    private String name;
    private BigDecimal age;
    private int score;
    private int money;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", money=" + money +
                '}';
    }
}
