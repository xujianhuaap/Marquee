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

/***
 * 对于 FLAG_CLEAR_TOP 只有TargetActivity 在task中已经存在才能清除，其从栈顶到targetActivity 之间的所有Activity
 * 否则 只会创建新的Activity 置于栈顶。
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_button_add)
    TextView btnAdd;
    @Bind(R.id.tv_button_delete)
    TextView btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnAdd.setText("to SecondActivity");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                finish();
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
