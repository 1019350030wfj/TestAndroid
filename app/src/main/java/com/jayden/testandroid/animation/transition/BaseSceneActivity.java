package com.jayden.testandroid.animation.transition;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/3/8.
 */
public abstract class BaseSceneActivity extends AppCompatActivity {

    protected Scene mScene1;
    protected Scene mScene2;
    protected boolean isScene2;

    public abstract Transition getTransition();

    public void switchScene(Transition transition) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.go(isScene2 ? mScene1 : mScene2, transition);
        }
        isScene2 = !isScene2;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//app的名称
    }

    public void initScene(@IdRes int root, @LayoutRes int sceneId1, @LayoutRes int sceneId2){
        ViewGroup viewGroup = (ViewGroup) findViewById(root);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mScene1 = Scene.getSceneForLayout(viewGroup,sceneId1,this);
            mScene2 = Scene.getSceneForLayout(viewGroup,sceneId2,this);
            TransitionManager.go(mScene1);
        }
    }

    public void change(View view){
        switchScene(getTransition());
    }
}
