package io.xjh.app.bean;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xujianhua on 2016/12/1.
 */
public class StudentTest {
    @Test
    public void getName() throws Exception {
        Student student=new Student("xu",27);
        System.out.print(student.getName().equals("xu"));
    }

}