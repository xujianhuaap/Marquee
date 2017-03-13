package io.xjh.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThirdActivity extends AppCompatActivity {
    @Bind(R.id.tv_button_add)
    TextView btnAdd;
    @Bind(R.id.tv_button_delete)
    TextView btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnAdd.setText("to FourthActivity");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this,FourthActivity.class));
            }
        });

    }
}
