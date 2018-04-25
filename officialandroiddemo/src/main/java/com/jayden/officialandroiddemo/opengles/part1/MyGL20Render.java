package com.jayden.officialandroiddemo.opengles.part1;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MyGL20Render implements GLSurfaceView.Renderer {

    //声明一个三角形对象
    Triangle triangle = null;
    //声明一个正方形对象
    Square square = null;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景颜色
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        triangle = new Triangle();
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //重绘背景色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //绘制三角形
//        triangle.draw();
//        //绘制正方形
        square.draw();
    }
}
