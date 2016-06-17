package com.test.hosta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BundleClassLoaderManager.install(getApplicationContext());
        TextView invokeTv = (TextView) findViewById(R.id.invoke_tv);
        invokeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BundlerResourceLoader.getBundleResources(getApplicationContext());
                startActivity(new Intent(MainActivity3.this,BundleActivity.class));
            }
        });
    }
}
