package io.xjh.marqueeview.bean;

import io.xjh.marqueeview.annotation.MsgField;

/**
 * Created by xujianhua on 2016/11/22.
 */

public class Student {
    @MsgField("Student")
    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "name\t"+name+"\tage\t"+age;
    }
}
