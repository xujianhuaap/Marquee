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
    private float density;
    private float textLeftPadding,textRightPadding;
    private int textSize=10;
    ViewHolder viewHolder=new ViewHolder();
    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x12){
                translate();
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
        newsArr.add("1111111111");
        newsArr.add("22222ssss2222222222222222");
        newsArr.add("33333eee2222222222222222222222222");
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
        new Timer().schedule(timerTask,200,100);
    }
    public void translate(){
        for (int i=0;i<getChildCount();i++){
            View v=getChildAt(i);
            int time=(int)v.getTag();
            time++;
            v.setTag(time);
            Log.d(MarqueeView.class.getName(),"i"+i+"\t child"+((TextView)v).getText()+"\t text"+v.getTranslationX());
            if (amend(v)) continue;
            v.setTranslationX(-3*time);


        }


    }

    private boolean amend(View v) {
        if(Math.abs(v.getTranslationX())>(v.getWidth()+v.getLeft())){
            CharSequence s=((TextView) v).getText();
            viewHolder.recyleTextView((TextView) v);
            removeView(v);
            TextView tv=viewHolder.getTextViewFromHolder(computeTextViewWidth(s.toString()));
            tv.setText(s);
            tv.setTranslationX(0);
            tv.setLeft(computeTotalWidth()-tv.getWidth());
            tv.setTag(0);
            addView(tv);

            return true;
        }
        return false;
    }

    public int computeTotalWidth(){
        int sum=0;
        for(String s:newsArr){
            sum=+computeTextViewWidth(s);
        }
        return sum;
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
            view.setTag(0);
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
            textView.setTag(0);
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
