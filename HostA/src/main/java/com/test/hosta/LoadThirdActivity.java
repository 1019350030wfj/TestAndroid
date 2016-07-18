package com.test.hosta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class LoadThirdActivity extends AppCompatActivity {
    private TextView invokeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        invokeTv = (TextView) findViewById(R.id.invoke_tv);
        invokeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //加载插件中的activity
                try {
                    startActivity(new Intent(LoadThirdActivity.this,Class.forName("com.test.plugina.BundleActivity")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
