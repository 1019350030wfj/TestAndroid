package com.jayden.testandroid.customview.scroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/8/18.
 */
public class TestScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);
        JaydenScrollView jaydenScrollView = (JaydenScrollView) findViewById(R.id.jayden);

//        jaydenScrollView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.translate_anim));

//        ObjectAnimator.ofFloat(jaydenScrollView,"translationX",0,300).setDuration(1000).start();

        //使用Scroller来进行平滑移动
        jaydenScrollView.smoothScrollTo(-400,0);

        jaydenScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestScrollActivity.this,"OnClick",Toast.LENGTH_LONG).show();
            }
        });
    }
}
