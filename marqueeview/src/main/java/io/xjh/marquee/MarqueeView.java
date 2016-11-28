package io.xjh.marquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.xjh.marquee.annotation.MsgField;

/**
 * Created by xujianhua on 2016/11/18.
 */

public class MarqueeView<T> extends LinearLayout{
    private int newsCount;
    private ArrayList<String> newsArr=new ArrayList<>();
    private ArrayList<T> datas=new ArrayList<>();
    private float density;
    private float textLeftPadding,textRightPadding;
    private int textSize=8;
    private int textColor;
    private boolean isStop=false;
    private boolean initWidth=true;
    private int translateRate=5;
    private MarqueesItemClickListener<T> clickListener;
    ViewHolder viewHolder=new ViewHolder();
    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x12){
                if(!isStop){
                    translate();
                }
            }
        }
    };
    private int time=0;
    private Timer timer;

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs) {

        TypedArray typedArray=context.getResources().obtainAttributes(attrs,R.styleable.marquee);
        density=context.getResources().getDisplayMetrics().density;
        textLeftPadding=5;
        textRightPadding=5;
        translateRate=typedArray.getInt(R.styleable.marquee_view_translate_rate,6);
        textSize=typedArray.getDimensionPixelSize(R.styleable.marquee_view_text_size,14);
        textColor=typedArray.getColor(R.styleable.marquee_view_text_color,Color.WHITE);
        typedArray.recycle();
    }
    public void setNews(List<T> news) throws IllegalAccessException {
        newsArr.clear();
        datas.clear();
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer=null;
        }
        isStop=true;
        initWidth=true;
        if(news!=null){
            newsCount=news.size();
            datas.addAll(news);
            for(int i=0;i<news.size();i++){
                T t=news.get(i);
                Class clazz=t.getClass();
                if(clazz!=null){
                    boolean isMsg=false;
                    for(Field f:clazz.getDeclaredFields()){
                        for(Annotation annotation:f.getDeclaredAnnotations()){
                            if(annotation.annotationType()==MsgField.class){
                                isMsg=true;
                                break;
                            }
                        }
                        if(isMsg){
                            newsArr.add(i,String.valueOf(f.get(t)));
                            break;
                        }

                    }

                }

            }
        }else {
            newsCount=0;
        }
        removeAllViews();
        addTextView();
        isStop=false;
    }

    public void setOnItemClickListener(MarqueesItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private void addTextView(){
        for (int i=0;i<newsCount;i++){
            int width=computeTextViewWidth(newsArr.get(i));
            TextView textView= viewHolder.getTextViewFromHolder(width);
            textView.setTextColor(textColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            int left=computeTotalWidth(i);
            textView.setLeft(left);
            textView.setTag(left);
            addView(textView,i);
//            Log.d(MarqueeView.class.getName(),"\twidth\t"+getChildAt(i).getWidth());
        }
    }

    public void startScroll(){
//        Toast.makeText(getContext(),""+newsCount+"\t\t"+newsArr.get(0),Toast.LENGTH_SHORT).show();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {

                    handler.sendEmptyMessage(0x12);


            }
        };
        if(timer==null){
            timer = new Timer();
        }
        timer.schedule(timerTask,100,100);
    }
    public void translate(){
        if(time==0||time==-getWidth()/translateRate){
            for(int i=0;i<getChildCount();i++){
                TextView tv=(TextView) getChildAt(i);
                tv.setText(newsArr.get(i));
            }
        }

        for (int i=0;i<getChildCount();i++){
            View v=getChildAt(i);
//            Log.d(MarqueeView.class.getName(),i+"\t v is null"+(v==null));
            if (i==(getChildCount()-1)&&amend(v)) break;
            if(v!=null){
                v.setTranslationX(-translateRate* time);
            }
//            Log.d(MarqueeView.class.getName(),"v width\t"+v.getWidth()+"\tleft\t"+v.getLeft());
        }
        time++;


    }

    private boolean amend(View v) {
        if(v==null){
             Log.d(MarqueeView.class.getName(),"v is null"+(v==null));
            return false;
        }
        boolean isAmend=false;
//        Log.d(MarqueeView.class.getName(),"v width\t"+v.getWidth()+"\tleft\t"+v.getLeft()+"\t translateX"+v.getTranslationX()+"parent width"+getWidth());
        isAmend=-v.getTranslationX()>v.getWidth()+v.getLeft();
        if(isAmend){
            handler.removeMessages(0x12);
            time=-getWidth()/translateRate;
            for (int i=0;i<getChildCount();i++){
                View child=getChildAt(i);
                if(child!=null){
                    TextView view=(TextView)child;
                    if(view!=null){
                        view.setTranslationX(getWidth());
//                        Log.d(MarqueeView.class.getName(),"i"+i+"\t  tranlateX\t"+view.getTranslationX()+"\t left\t"+view.getLeft());
                    }
                }

            }
            return true;
        }
        return false;
    }

    public int computeTotalWidth(int to){
        int sum=0;
        if(to>newsArr.size()) to=newsArr.size();
        for(int i=0;i<to;i++ ){
            sum+=computeTextViewWidth(newsArr.get(i));
        }
        return sum;
    }
    public int  computeTextViewWidth(CharSequence s){
        Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
        Pattern p1=Pattern.compile("[。，？（！）]");
        int cnt=0;
        for (int i=0;i<s.length();i++){
            Matcher matcher=p.matcher(s.subSequence(i,i+1));
            if(matcher.matches()){
                cnt++;
            }
            Matcher matcher1=p1.matcher(s.subSequence(i,i+1));
            if(matcher1.matches()){
                cnt++;
            }
        }
        if(!TextUtils.isEmpty(s)){
            float v=(cnt+s.length())/2*textSize+(textLeftPadding+textRightPadding)*density;
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
            view.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
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
            textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
            LayoutParams layoutParams=new LayoutParams(width,LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(layoutParams);
            textView.setPadding((int)(textLeftPadding*density),10,(int)(textRightPadding*density),10);
            textView.setMaxLines(1);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        int index=getTFromMsg(textView.getText().toString());
                        clickListener.onItemClick(datas.get(index));
                    }
                }
            });
            return textView;
        }
    }
    private int getTFromMsg(String content){
        for(int i=0;i<newsArr.size();i++){
            if(newsArr.get(i).equals(content)){
                return i;
            }
        }
        return -1;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(initWidth){
            time=-getWidth()/translateRate;
            initWidth=false;
        }

        Log.d(MarqueeView.class.getName(),"time\t:"+time+"\t childCount"+getChildCount());
    }

    public interface MarqueesItemClickListener<T>{
        void onItemClick(T t);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void cancle(){
        if(timer!=null){
            timer.cancel();
            timer.purge();
        }
    }
}
