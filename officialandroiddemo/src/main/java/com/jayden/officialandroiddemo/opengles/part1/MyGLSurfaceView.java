package com.jayden.officialandroiddemo.opengles.part1;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);
        //创建一个OpenGL ES 2.0 context 非常重要
        setEGLContextClientVersion(2);;
        //设置Render到GLSurfaceView
        setRenderer(new MyGL20Render());
        //只有在绘制数据改变时才绘制view
        //此设置会阻止绘制GLSurfaceView的帧，直到你调用了requestRender()，这样会非常高效
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
