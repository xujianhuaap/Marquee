package io.xjh.marqueeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.xjh.marqueeview.bean.Student;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private MarqueeView<Student> view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (MarqueeView)findViewById(R.id.view);
        View addView=findViewById(R.id.tv_button_add);
        View deleteView=findViewById(R.id.tv_button_delete);
        students = new ArrayList<>();
        for(int i=0;i<3;i++){
            Student s=new Student("河出伏流，一泻汪洋。鹰隼肆翼，喷薄吸张。旭日东升，其道大光。",23+i);
            students.add(s);
        }

        try {
            view.setOnItemClickListener(new MarqueeView.MarqueesItemClickListener<Student>() {
                @Override
                public void onItemClick(Student o) {
                    if(o!=null) Toast.makeText(MainActivity.this,o.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            view.setNews(students);
            view.startScroll();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    view.setNews(students);
                    view.startScroll();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
