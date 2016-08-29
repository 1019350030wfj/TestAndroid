package com.jayden.testandroid.customview.floatview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/10.
 * Email : 1570713698@qq.com
 */
public class TestFloatViewActivity extends AppCompatActivity {

    private FloatView floatView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_view);
        floatView = new FloatView(this,0,0,R.layout.floatview_layotu);
        floatView.setFloatViewClickListener(new FloatView.IFloatViewClick() {
            @Override
            public void onFloatViewClick() {
                Toast.makeText(TestFloatViewActivity.this,"floatview is clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        floatView.addToWindow();
        super.onStart();
    }

    @Override
    protected void onStop() {
        floatView.removeFromWindow();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
