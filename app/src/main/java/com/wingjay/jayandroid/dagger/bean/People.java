package com.wingjay.jayandroid.dagger.bean;

/**
 * Created by Jay on 4/19/16.
 */
public class People {

    private int age;
    private String name;

    public People(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
