package io.xjh.margeeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.xjh.margeeview.bean.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MarqueeView<Student> view=(MarqueeView)findViewById(R.id.view);
        List<Student> students=new ArrayList<>();
        for(int i=0;i<3;i++){
            Student s=new Student("stu"+i,23+i);
            students.add(s);
        }

        try {
            view.setNews(students);
            view.startScroll();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
