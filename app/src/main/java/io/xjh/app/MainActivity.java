package io.xjh.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        List<String> datas=new ArrayList<>();
        datas.add("111111");
        datas.add("2222");
        datas.add("333333");
        datas.add("444444");
        datas.add("666666");
        datas.add("55555");
        tableLayout.setData(datas);
        View deleteView=findViewById(R.id.tv_button_delete);

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new MainFragment()).commit();
            }
        });
    }
}
