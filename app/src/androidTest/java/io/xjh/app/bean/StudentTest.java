package io.xjh.app.bean;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xujianhua on 2017/3/11.
 */
public class StudentTest {
    Student student=new Student("xu",27);
    @Test
    public void getName() throws Exception {

        assertEquals(student.getName(),"xu");
        assertEquals(student.getName(),"xu1");
    }

    @Test
    public void getAge() throws Exception {
        assertEquals(student.getAge(),28);
    }

}