package com.jayden.testandroid.framework.ui.shareelementatfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 */
public class ShareElementActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_element_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new GridFragment()).commit();
        }
    }
}
