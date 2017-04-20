package io.xjh.marquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
    private boolean isBootFirst=true;
    private boolean isBootSecond=false;
    private boolean isLoop=false;
    private int screenWidth;
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
    private int time1=0;
    private Timer timer;
    private int firstGroupStatus;
    private int secondGroupStatus;
    private int firstLastGroupStatus=-1;
    private int secondLastGroupStatus=-1;

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
        screenWidth=context.getResources().getDisplayMetrics().widthPixels;
        textLeftPadding=5;
        textRightPadding=5;
        translateRate=typedArray.getInt(R.styleable.marquee_view_translate_rate,6);
        textSize=typedArray.getDimensionPixelSize(R.styleable.marquee_view_text_size,14);
        textColor=typedArray.getColor(R.styleable.marquee_view_text_color,Color.WHITE);
        typedArray.recycle();
    }
    public void setNews(List<T> news) throws IllegalAccessException {
        time=0;
        time1=0;
        isBootFirst=true;
        isBootSecond=false;
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
        //添加两组以备做循环操作
        int width=screenWidth*8/10;
        isLoop=computeTotalWidth(newsCount)>width;
        if(isLoop){
            addTextView(1);
            addTextView(2);
        }else {
            addTextView(1);
        }
        isStop=false;
    }

    public void setOnItemClickListener(MarqueesItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /***
     *
     * @param groupIndex >1
     */
    private void addTextView(int groupIndex){
        for (int i=0;i<newsCount;i++){
            int width=computeTextViewWidth(newsArr.get(i));
            TextView textView= viewHolder.getTextViewFromHolder(width);
            textView.setTextColor(textColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            addView(textView,i+(groupIndex-1)*newsCount);
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
                if(newsCount!=0){
                    tv.setText(newsArr.get(i%newsCount));
                }
            }
        }

        if(isLoop){
            firstGroupStatus = getViewStailStatus(1);
            secondGroupStatus = getViewStailStatus(2);
            if(firstGroupStatus ==1){
                //第一组尾部已经可见 启动第二组
                if(firstLastGroupStatus==0){
                    isBootSecond=true;
                    time1=(-getWidth()+computeTotalWidth(newsCount))/translateRate;
                }

            }else if(firstGroupStatus ==2){
                //第一组尾部已经右不可见  初始化第一组的状态
                if(firstLastGroupStatus==1){
                    isBootFirst=false;
                    time=-getWidth()/translateRate;
                    for(int i=0;i<newsCount;i++){
                        TextView tv=(TextView) getChildAt(i);
                        tv.setTranslationX(getWidth());
                    }
                }

            }

            if(secondGroupStatus ==1){
                //启动第一组
                if(secondLastGroupStatus==0){
                    isBootFirst=true;
                    time=-getWidth()/translateRate;
                }

            }else if(secondGroupStatus ==2){
                //初始化第二组的状态
                if(secondLastGroupStatus==1){
                    isBootSecond=false;
                    time1=(-getWidth()+computeTotalWidth(newsCount))/translateRate;
                    for(int i=newsCount;i<newsCount*2;i++){
                        TextView tv=(TextView) getChildAt(i);
                        tv.setTranslationX(getWidth()-computeTotalWidth(newsCount));
                    }
                }

            }
            if(isBootFirst){
                time++;
            }
            if(isBootSecond){
                time1++;
            }
            for (int i=0;i<getChildCount();i++){
                View v=getChildAt(i);
                if(v!=null){
                    if(i>=newsCount){
                        v.setTranslationX(-time1*translateRate);
                    }else {
                        v.setTranslationX(-time*translateRate);
                    }
                }
            }
            firstLastGroupStatus=firstGroupStatus;
            secondLastGroupStatus=secondGroupStatus;
//            Log.d(MarqueeView.class.getName()," isBootFirst\t"+isBootFirst+"\tisBootSecond\t"+isBootSecond+"\ttime1\t"+time1+"\t time\t"+time);
        }


    }

    /***
     *
     * @param groupIndex  组的编号
     * @return 0 : 头右不可见 1 : 头可见   2:头左不可见 不可见 3:头可见 4: 尾可见
     */
    public int getViewHeadStatus(int groupIndex){
        View view=getChildAt(newsCount*groupIndex-1);
        if(view!=null){
            float translateX=view.getTranslationX();
            if(translateX+view.getLeft()>getWidth()){
                return 0;
            }else {
                if(translateX+view.getLeft()>0){
                    return 1;
                }else {
                    return 2;
                }
            }
        }else {
            return 0;
        }
    }
    /***
     *
     * @param groupIndex  组的编号
     * @return 0 : 尾右不可见 1 : 尾可见   2:尾左不可见
     */
    public int getViewStailStatus(int groupIndex){
        View view=getChildAt(newsCount*groupIndex-1);

        if(view!=null){
//            Log.d(MarqueeView.class.getName(),"groupIndex\t:"+groupIndex+"\t left-getWidth"+(view.getLeft()-getWidth()));
            float translateX=view.getTranslationX();
            if(translateX+view.getLeft()+view.getWidth()>getWidth()){
                return 0;
            }else {
                if(translateX+view.getLeft()+view.getWidth()>0){
                    return 1;
                }else {
                    return 2;
                }
            }
        }else {
            return 0;
        }
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
            float v=s.length()*textSize+(textLeftPadding+textRightPadding)*density;
            Log.d("MarqueeView","digit count"+cnt+"length"+s.length());
            Log.d("MarqueeView","textSize"+textSize+"length\t"+v);
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
            if(isLoop){
                time=-getWidth()/translateRate;
            }else {
                time=0;
            }

            initWidth=false;
        }

//        Log.d(MarqueeView.class.getName(),"time\t:"+time+"\t childCount"+getChildCount());
//        Log.d(MarqueeView.class.getName(),"left 0\t:" +getChildAt(0).getLeft()+"\t"+newsCount+"\t"+getChildAt(newsCount).getLeft()+"\t parent getWidth\t"+getWidth());
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMeasuredWidth();
        Log.d("MarqueeView","getMeasuredWidth()\t"+ getMeasuredWidth()+"getWidth()\t"+getWidth());
    }
}
