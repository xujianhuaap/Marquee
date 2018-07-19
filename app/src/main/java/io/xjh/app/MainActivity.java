package io.xjh.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.xjh.app.bean.Student;
import io.xjh.marquee.MarqueeView;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private MarqueeView marqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @Override
    protected void onResume() {
        super.onResume();
        checkHijacking(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkHijacking(this);
    }

    public static void checkHijacking(Activity activity) {
        if (checkActivity(activity)) return;
        Toast.makeText(activity, "------danger------", Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测当前Activity是否安全
     */
    private static boolean checkActivity(Activity context) {
        boolean safe = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List appList = am.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo app;

        for (int i = 0; i < appList.size(); i++) {
            //ActivityManager.RunningAppProcessInfo app : appList
            app = (ActivityManager.RunningAppProcessInfo) appList.get(i);
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && context.getPackageName().equals(app.processName)) {//表示前台运行进程.
                safe = true;
            }
        }
        return safe;
    }
}
