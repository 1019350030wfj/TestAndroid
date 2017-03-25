package com.jayden.testandroid.animation.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/3/8.
 */

public class SceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void changeBounds(View view) {
        startActivity(new Intent(this, SceneChangeBoundsActivity.class));
    }

    public void changeTransform(View view) {
        startActivity(new Intent(this, BeginDelayedActivity.class));
    }

    public void changeClipBounds(View view) {
        startActivity(new Intent(this, SceneChangeClipBoundsActivity.class));
    }

    public void changeImageTransform(View view) {
        startActivity(new Intent(this, SceneChangeImageTransformActivity.class));
    }

    public void fade(View view) {
        startActivity(new Intent(this, SceneFadeSlideExplodeActivity.class));
    }

}
