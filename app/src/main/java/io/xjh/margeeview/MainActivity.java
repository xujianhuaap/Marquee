package io.xjh.margeeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MarqueeView view=(MarqueeView)findViewById(R.id.view);
        view.setNews();
        view.startScroll();
    }
}
