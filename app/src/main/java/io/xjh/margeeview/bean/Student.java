package io.xjh.margeeview.bean;

import io.xjh.margeeview.annotation.MsgField;

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
}
