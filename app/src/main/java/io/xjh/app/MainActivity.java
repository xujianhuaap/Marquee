package io.xjh.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.xjh.marquee.MarqueeView;
import io.xjh.app.bean.Student;

public class MainActivity extends AppCompatActivity {


    private MarqueeView<Student> view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        View deleteView=findViewById(R.id.tv_button_delete);

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new MainFragment()).commit();
            }
        });
    }
}
