package com.jayden.versionopo.api.opengl.tutor;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

/**
 * OpenGl ES    GL10方法的介绍：      http://www.fx114.net/qa-261-89327.aspx
 * Created by Jayden on 2017/5/16.
 */

public class TutorialActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new OpenGlRender());
        setContentView(glSurfaceView);
    }
}
