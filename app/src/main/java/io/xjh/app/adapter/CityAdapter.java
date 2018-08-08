package io.xjh.app.adapter;

import android.content.Context;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.xjh.app.R;

/**
 * Created by xujianhua on 8/8/18.
 */

public class CityAdapter extends SimpleAdapter{
    public static String KEY_CITY_NUM = "city_number";
    public CityAdapter(Context context,List<Map<String,String>>data) {
        super(context, data, R.layout.item_city,
                new String[]{KEY_CITY_NUM}, new int[]{R.id.tv_city});
    }
}
