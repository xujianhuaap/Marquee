package io.xjh.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.xjh.marquee.MarqueeView;
import io.xjh.app.bean.Student;
import io.xjh.tablelayout.views.TableLayout;

public class MainActivity extends AppCompatActivity {


    private MarqueeView<Student> view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        io.xjh.tablelayout.views.TableLayout tableLayout=(TableLayout) findViewById(R.id.tab);
        List<Student> datas=new ArrayList<>();
        datas.add(new Student("借款问题",2));
        datas.add(new Student("还款问题",2));
//        datas.add(new Student("其问题",2));
//        datas.add(new Student("问题",2));
        tableLayout.setData(datas);
        tableLayout.setCallBack(new TableLayout.CallBack() {
            @Override
            public void onClick(Object o, int index) {
                Toast.makeText(MainActivity.this,"index"+index,Toast.LENGTH_SHORT).show();
            }
        });
        View deleteView=findViewById(R.id.tv_button_delete);

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new MainFragment()).commit();
            }
        });
    }
    //git flow release
    //git flow v4.0.1
    //git flow v4.0.2

    //git flow v4.0.3
}
