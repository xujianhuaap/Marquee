package io.xjh.tablelayout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import io.xjh.marquee.annotation.MsgField;
import io.xjh.tablelayout.R;


/**
 * Created by xujianhua on 2016/12/31.
 */

public class TableLayout<D> extends HorizontalScrollView {
    List<D> datas=new ArrayList<>();
    private Context context;
    private LinearLayout rootView;
    private CallBack<D> callBack;
    private int colorDividerSelected=Color.parseColor("#31a3EE");
    private int colorDivider=Color.parseColor("#E7E6E6");
    private int colorTitle=Color.parseColor("#908f94");
    private int colorTilteSelected=Color.parseColor("#31a3EE");
    private int screenWidth;
    public TableLayout(Context context) {
        super(context);
        init(context);
    }
    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.getResources().obtainAttributes(attrs,R.styleable.table_layout);
        typedArray.recycle();
        init(context);
    }


    public void setCallBack(CallBack<D> callBack) {
        this.callBack = callBack;
    }

    private void init(Context context) {
        this.context=context;
        screenWidth=context.getResources().getDisplayMetrics().widthPixels;
        View view=inflate(context, R.layout.layout_tab,null);
        addView(view);
        this.rootView =(LinearLayout)view.findViewById(R.id.tab);

    }
    public void initViews(){

    }
    public void setData(final List<D>datas){
        if(datas!=null){
            rootView.removeAllViews();
            this.datas.clear();
            this.datas.addAll(datas);
            for (int i=0;i<datas.size();i++){
                View view =inflate(context, R.layout.layout_item,null);
                view.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, ViewGroup.LayoutParams.MATCH_PARENT));
                TextView tvTitle=(TextView) view.findViewById(R.id.item_title);
                D data=datas.get(i);
                if(data!=null){
                    Class clazz=data.getClass();
                    Field[] fields=clazz.getDeclaredFields();
                    for(Field f:fields){
                        Annotation[] annotations=f.getAnnotations();
                        boolean isTitle=false;
                        for(Annotation a:annotations){
                            if(a.annotationType()== MsgField.class){
                              isTitle=true;
                                break;
                            }
                        }
                        if(isTitle){
                            try {
                                tvTitle.setText(String.valueOf(f.get(data)));
                                break;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }


                view.setTag(i);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index=(int)v.getTag();
                        refreshStatus(index);
                        if(callBack!=null){
                            callBack.onClick(datas.get(index),index);
                        }
                    }
                });
                rootView.addView(view,i);
            }
            refreshStatus(0);
        }

    }

    private void refreshStatus(int selectedIndex){
        for (int i=0;i<datas.size();i++){
            TextView tvDivider=(TextView) (rootView.getChildAt(i).findViewById(R.id.item_divider));
            TextView tvTitle=(TextView) (rootView.getChildAt(i).findViewById(R.id.item_title));
            if(selectedIndex==i){
                tvDivider.setBackgroundColor(colorDividerSelected);
                tvTitle.setTextColor(colorTilteSelected);
            }else {
                tvDivider.setBackgroundColor(Color.TRANSPARENT);
                tvTitle.setTextColor(colorTitle);
            }
        }
    }

    public interface CallBack<D>{
        void onClick(D d,int index);
    }
}
