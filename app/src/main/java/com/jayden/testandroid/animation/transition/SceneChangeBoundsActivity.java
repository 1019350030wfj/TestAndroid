package com.jayden.testandroid.animation.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.Transition;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/3/8.
 */
public class SceneChangeBoundsActivity extends BaseSceneActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change_bounds);
        initToolbar();
        initScene(R.id.scene_root,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds);
    }

    @Override
    public Transition getTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ChangeBounds();
        }
        return null;
    }
}
