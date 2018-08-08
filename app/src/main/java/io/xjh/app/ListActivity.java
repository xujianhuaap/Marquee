package io.xjh.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.xjh.app.adapter.CityAdapter;

import static io.xjh.app.adapter.CityAdapter.KEY_CITY_NUM;

/**
 * Created by xujianhua on 8/8/18.
 */

public class ListActivity extends Activity {
    public static final String EXTRA_CITY = "extra_city";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listView = (ListView) findViewById(R.id.list);
        final List<Map<String,String>> data = new ArrayList<>();
        for(int i=1;i<15;i++){
            HashMap map = new HashMap();
            map.put(KEY_CITY_NUM,""+i);
            data.add(map);
        }
        CityAdapter cityAdapter = new CityAdapter(this,data);
        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this,
                        ""+data.get(position).get(KEY_CITY_NUM),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
