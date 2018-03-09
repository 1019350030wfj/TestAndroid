package com.jayden.versionopo.multimedia.opengl;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLSurfaceView;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRender mRender;

    public MyGLSurfaceView(Context context) {
        super(context);
        //创建OpenGL ES 2.0的上下文
    }
}
