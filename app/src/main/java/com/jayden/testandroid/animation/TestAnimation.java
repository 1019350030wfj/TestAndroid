package com.jayden.testandroid.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/2/15.
 */
public class TestAnimation extends Activity implements View.OnClickListener{

    private ImageView mImage;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);
        mImage = (ImageView) findViewById(R.id.image);
        mBtn1 = (Button) findViewById(R.id.button1);
        mBtn2 = (Button) findViewById(R.id.button2);
        mBtn3 = (Button) findViewById(R.id.button3);
        mBtn4 = (Button) findViewById(R.id.button4);
        mBtn5 = (Button) findViewById(R.id.button5);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image: {
                Toast.makeText(this,"Imageview onClick",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.button1: {
                //translate
                playTranslation();
                break;
            }
            case R.id.button2: {
                //alpha

                break;
            }
            case R.id.button3: {
                //rotate

                break;
            }
            case R.id.button4: {
                //scale

                break;
            }
            case R.id.button5: {
                //property
//                propertyAlpah();

                ObjectAnimator objectAnimtor = ObjectAnimator.ofFloat(mImage, "rotation", 0.0f,200f);
                objectAnimtor.setDuration(5000);
                objectAnimtor.setRepeatCount(1);
                objectAnimtor.setRepeatMode(ValueAnimator.REVERSE);
                objectAnimtor.start();
                break;
            }
        }
    }

    /**
     * 透明度
     */
    private void propertyAlpah() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImage,"alpha",0.0f,1.0f);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.start();
    }

    private void playTranslation(){
        Animation translationAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.translate_anim);
        mImage.startAnimation(translationAnimation);
    }
}
