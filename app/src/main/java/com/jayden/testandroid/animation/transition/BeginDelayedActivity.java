package com.jayden.testandroid.animation.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.testandroid.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 研究android Transition动画，这是在Android 4.4引入的但在5.0才真正被实现。
 * Transition Framework 核心有三种动画方式：
 * 1、TransitionManager.go()，这个主要是通过场景Scene加载动画前后的布局，然后通过具体Transition的实现类来比较和实现动画；
 * 2、beginDelayedTransition，这个主要是通过代码来改变view属性，然后还是通过Transition具体实现子类分析前后scene来创建动画；
 * 3、界面切换，主要包括共享元素和不共享元素
 */

public class BeginDelayedActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView cuteboy,cutegirl,hxy,lly;
    private boolean isImageBigger;
    private ViewGroup sceneRoot;
    private int primarySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_delayed);
        initToolBar();
        initView();
    }

    @Override
    public void onClick(View v) {
        //start scene 是当前的scene
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(this).inflateTransition(R.transition.explode_and_changebounds));
            //next scene 此时通过代码已改变了scene statue
            changeScene(v);
        }
    }

    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(cuteboy,cutegirl,hxy,lly);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * view的宽高1.5倍和原尺寸大小切换
     * 配合ChangeBounds实现缩放效果
     * @param view
     */
    private void changeSize(View view) {
        isImageBigger=!isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(isImageBigger){
            layoutParams.width=(int)(1.5*primarySize);
            layoutParams.height=(int)(1.5*primarySize);
        }else {
            layoutParams.width=primarySize;
            layoutParams.height=primarySize;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     * @param views
     */
    private void changeVisibility(View ...views){
        for (View view:views){
            view.setVisibility(view.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
        }
    }

    private void initView() {
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        cuteboy= (CircleImageView) findViewById(R.id.cuteboy);
        cutegirl= (CircleImageView) findViewById(R.id.cutegirl);
        hxy= (CircleImageView) findViewById(R.id.hxy);
        lly= (CircleImageView) findViewById(R.id.lly);
        primarySize=cuteboy.getLayoutParams().width;
        cuteboy.setOnClickListener(this);
        cutegirl.setOnClickListener(this);
        hxy.setOnClickListener(this);
        lly.setOnClickListener(this);
    }


    private void initToolBar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
