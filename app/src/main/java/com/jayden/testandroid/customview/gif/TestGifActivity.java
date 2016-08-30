package com.jayden.testandroid.customview.gif;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/7/25.
 */
public class TestGifActivity extends Activity {

    private GifView mGifView;
    private GifView mGifView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gif);
        mGifView = (GifView) findViewById(R.id.gifview);
        mGifView.setGifImage(R.drawable.onesoft_loading_gif1, PlayMode.CONTINUOUS);
        mGifView2 = (GifView) findViewById(R.id.gifview2);
        mGifView2.setGifImage(R.drawable.onesoft_render_gif1, PlayMode.CONTINUOUS);
        mGifView.setVisibility(View.GONE);
        mGifView2.setVisibility(View.GONE);
        mGifView.showCover();
        mGifView2.showCover();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGifView.setVisibility(View.VISIBLE);
                mGifView.showAnimation();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGifView2.setVisibility(View.VISIBLE);
            }
        });
    }
}
