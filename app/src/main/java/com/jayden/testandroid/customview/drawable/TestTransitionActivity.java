package com.jayden.testandroid.customview.drawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/21.
 * Email : 1570713698@qq.com
 */
public class TestTransitionActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_drawable);

        imageView = (ImageView) findViewById(R.id.image);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_beauty_diary);

        final TransitionDrawable td = new TransitionDrawable(new Drawable[] { new ColorDrawable(0xfffcfcfc),
                new BitmapDrawable(getResources(), bitmap) });
        imageView.setImageDrawable(td);
        td.startTransition(300);
    }
}
