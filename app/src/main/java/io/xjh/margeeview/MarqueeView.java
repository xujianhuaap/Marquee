package io.xjh.margeeview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xujianhua on 2016/11/18.
 */

public class MarqueeView extends LinearLayout{
    private final int newsCount;
    private ArrayList<String> newsArr=new ArrayList<>();
    private int time=0;
    private float density;
    private float textLeftPadding,textRightPadding;
    private int textSize=10;
    private int tranlateXSum;
    ViewHolder viewHolder=new ViewHolder();
    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x12){
                translate();
                time++;
            }
        }
    };

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        newsCount=3;
        init(context);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        newsCount=3;
        init(context);
    }
    private void init(Context context) {
        density=context.getResources().getDisplayMetrics().density;
        textLeftPadding=5;
        textRightPadding=5;
        textSize=10;
    }
    public void setNews(){
        newsArr.clear();
        newsArr.add("1111111111111");
        newsArr.add("22222222222  22");
        newsArr.add("333333333  3");
        addTextView();
    }
    private void addTextView(){
        for (int i=0;i<newsCount;i++){
            int width=computeTextViewWidth(newsArr.get(i));
            TextView textView= viewHolder.getTextViewFromHolder(width);
            textView.setText(newsArr.get(i));
            addView(textView);
        }
    }

    public void startScroll(){
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x12);
            }
        };
        new Timer().schedule(timerTask,1000,100);
    }
    public void translate(){
        for (int i=0;i<getChildCount();i++){
            View v=getChildAt(i);
            if(i==0&&Math.abs(tranlateXSum)>v.getWidth()){
                Log.d(MarqueeView.class.getName(),"left view"+tranlateXSum+"\t child"+getChildCount());
                View textView=getChildAt(0);
                viewHolder.recyleTextView((TextView) textView);
                removeView(textView);

                TextView tv=viewHolder.getTextViewFromHolder(computeTextViewWidth("sssssssssssssssssss"));
                tv.setText("sssssssssssssssssss");
                tranlateXSum=0;
                addView(tv,getChildCount());
            }

            v.getLeft();
            tranlateXSum=+(-10*time);
            v.setTranslationX(-10*time);
        }


    }


    public int  computeTextViewWidth(String s){
        if(!TextUtils.isEmpty(s)){
            float v=(s.length()*textSize+textLeftPadding+textRightPadding)*density;
            return (int)v ;
        }
        return 0;
    }

    class ViewHolder{
        List<TextView> views=new ArrayList<>();
        public void recyleTextView(TextView view){
            view.setOnClickListener(null);
            view.setText("");
            view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
            views.add(views.size(),view);
        }

        public  TextView getTextViewFromHolder(int width){
            final TextView textView;
            if(views.size()>0){
                textView=views.get(0);
                views.remove(textView);
            }else {
                textView=new TextView(getContext());
            }

            textView.setGravity(Gravity.CENTER_VERTICAL);
            LayoutParams layoutParams=new LinearLayout.LayoutParams(width,LayoutParams.MATCH_PARENT);

            textView.setLayoutParams(layoutParams);
            textView.setPadding((int)(textLeftPadding*density),10,(int)(textRightPadding*density),10);
            textView.setMaxLines(1);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),textView.getText(),Toast.LENGTH_SHORT).show();
                }
            });
            return textView;
        }
    }

}
