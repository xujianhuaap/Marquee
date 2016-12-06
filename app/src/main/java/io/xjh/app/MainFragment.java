package io.xjh.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.xjh.app.bean.Student;
import io.xjh.marquee.MarqueeView;

/**
 * Created by xujianhua on 2016/12/6.
 */

public class MainFragment extends Fragment {
    private int index=0;
    private List<Student> students;
    private MarqueeView marqueeView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MainFragment.class.getName(),"onAttach index"+index++);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainFragment.class.getName(),"onCreate index"+index++);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainFragment.class.getName(),"onStart index"+index++);
        students = new ArrayList<>();
        for(int i=0;i<2;i++){
            Student s;
            if (i%2==0){
                s=new Student("河出伏流",23+i);
            }else {
                s=new Student("明知不可。",23+i);
            }

            students.add(s);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainFragment.class.getName(),"onResume index"+index++);
        try {
            marqueeView.setNews(students);
            marqueeView.startScroll();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainFragment.class.getName(),"onCreateView index"+index++);
        View view=inflater.inflate(R.layout.activity_main,null);
        marqueeView = (MarqueeView)view.findViewById(R.id.view);
        View addView=view.findViewById(R.id.tv_button_add);

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    marqueeView.setNews(students);
                    marqueeView.startScroll();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainFragment.class.getName(),"onActivityCreated index"+index++);
    }
}
