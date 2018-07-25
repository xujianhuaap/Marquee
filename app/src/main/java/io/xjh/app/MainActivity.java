package io.xjh.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.xjh.app.bean.Student;
import io.xjh.app.utils.FileUtil;
import io.xjh.marquee.MarqueeView;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private MarqueeView marqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileUtil.getExternalStorage();
        FileUtil.getAppExternalStorage(this);
        FileUtil.getFilesDir(this);
        marqueeView = (MarqueeView)findViewById(R.id.view);
        students = new ArrayList<>();
        for(int i=0;i<2;i++){
            Student s;
            if (i%2==0){
                s=new Student("到账提醒，你有一笔资金到账银行卡尾号（0395）也是",23+i);
            }else {
                s=new Student("到账提醒，你有一笔资金到账银行卡尾号（0395）",23+i);
            }
            // jenkins test
            students.add(s);
        }
        try {
            marqueeView.setNews(students);
            marqueeView.startScroll();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
