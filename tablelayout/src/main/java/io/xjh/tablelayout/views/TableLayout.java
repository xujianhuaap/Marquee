package io.xjh.tablelayout.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
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

public class TableLayout extends ScrollView {
    List<String> datas=new ArrayList<>();
    private Context context;
    private LinearLayout rootView;

    public TableLayout(Context context) {
        super(context);
        init(context);
    }
    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                tvDivider.setBackgroundColor(Color.parseColor("#31a3EE"));
                tvTitle.setTextColor(Color.parseColor("#31a3EE"));
            }else {
                tvDivider.setBackgroundColor(Color.TRANSPARENT);
                tvTitle.setTextColor(Color.parseColor("#908f94"));
            }
        }
    }
}
