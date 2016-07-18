package com.jayden.testandroid.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/6/25.
 * Email : 1570713698@qq.com
 */
public class TestRadarActivity extends AppCompatActivity {

    private RadarView mRadarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_radar);
        mRadarView = (RadarView) findViewById(R.id.radar_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRadarView.setmImageUrl("http://p5.qhimg.com/t01ba4f7909f15de5fc.jpg");
    }
}
