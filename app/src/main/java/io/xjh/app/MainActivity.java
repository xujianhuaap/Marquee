package io.xjh.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.xjh.app.data.HomeService;
import io.xjh.app.utils.DialogTest;
import io.xjh.app.utils.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private DialogTest dialogTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        TextView btnClick=(TextView)findViewById(R.id.btn_click) ;
        TextView btnClickJump=(TextView)findViewById(R.id.btn_click_right) ;
        btnClickJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTest= new DialogTest();
                getSupportFragmentManager().beginTransaction().replace(R.id.holder,dialogTest).commit();
            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(">>>","onSaveInstanceState");
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(dialogTest!=null){
//            Log.d(">>>","OnPause before");
//            dialogTest.dismissAllowingStateLoss();
//            Log.d(">>>","OnPause after");
//        }
    }
}
