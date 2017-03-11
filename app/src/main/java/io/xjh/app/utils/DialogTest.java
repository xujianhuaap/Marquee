package io.xjh.app.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import io.xjh.app.R;

/**
 * Created by xujianhua on 2017/3/10.
 */

public class DialogTest extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,null);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Log.d(">>>","begin dismiss");
                dismiss();
                Log.d(">>>","end dismiss");
            }
        };
        Timer timer=new Timer();
        timer.schedule(timerTask,5000);
    }
}
