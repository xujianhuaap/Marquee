package io.xjh.tablelayout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import io.xjh.tablelayout.R;

/**
 * Created by xujianhua on 2016/12/31.
 */

public class TableLayout extends HorizontalScrollView {
    List<String> datas=new ArrayList<>();
    private Context context;
    private LinearLayout rootView;
    private int colorDividerSelected=Color.parseColor("#31a3EE");
    private int colorDivider=Color.parseColor("#E7E6E6");
    private int colorTitle=Color.parseColor("#908f94");
    private int colorTilteSelected=Color.parseColor("#31a3EE");
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




    private void init(Context context) {
        this.context=context;
        View view=inflate(context, R.layout.layout_tab,null);
        addView(view);
        this.rootView =(LinearLayout)view.findViewById(R.id.tab);

    }
    public void initViews(){

    }
    public void setData(List<String>datas){
        if(datas!=null){
            rootView.removeAllViews();
            this.datas.clear();
            this.datas.addAll(datas);
            for (int i=0;i<datas.size();i++){
                View view =inflate(context, R.layout.layout_item,null);
                TextView tvTitle=(TextView) view.findViewById(R.id.item_title);
                tvTitle.setText(datas.get(i));
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshStatus(2);
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


}
