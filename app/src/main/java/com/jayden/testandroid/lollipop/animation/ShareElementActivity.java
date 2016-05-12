package com.jayden.testandroid.lollipop.animation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class ShareElementActivity extends AppCompatActivity {

    private int pos;
    private int drawableId;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        Intent intent = getIntent();
        if (intent != null) {
            pos = intent.getExtras().getInt("pos");
            drawableId = intent.getExtras().getInt("drawableId");
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setTransitionName(pos+"pic");
        imageView.setImageResource(drawableId);

    }
}
