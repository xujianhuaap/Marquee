package io.xjh.app.bean;


import io.xjh.marquee.annotation.MsgField;

/**
 * Created by xujianhua on 2016/11/22.
 */

public class Student {
    @MsgField("Student")
    public String name;
    public int age;

    public Student() {
    }

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
