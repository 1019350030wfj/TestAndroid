package com.jayden.testandroid.customview.waveprogress;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/1/22.
 */

public class TestWaveActivity extends AppCompatActivity {

    private WaveView waveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wave);

        waveView = (WaveView) findViewById(R.id.wave_view);
        waveView.setStyle(Paint.Style.FILL);
        waveView.setColor(Color.RED);
        waveView.setDuration(5000);
        waveView.setInterpolator(new LinearOutSlowInInterpolator());
    }

    public void start(View view) {
        waveView.start();
    }

    public void stop(View view) {
        waveView.stop();
    }
}
