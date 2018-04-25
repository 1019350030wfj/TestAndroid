package com.jayden.versionopo.customview.image.largeimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jayden.versionopo.R;
import com.jayden.versionopo.customview.image.largeimage.view.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/1/13.
 */

public class LargeImageViewActivity extends AppCompatActivity {
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

        mLargeImageView = (LargeImageView) findViewById(R.id.id_largetImageview);
        try {
            InputStream inputStream = getAssets().open("hxswdt.jpg");
            mLargeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}