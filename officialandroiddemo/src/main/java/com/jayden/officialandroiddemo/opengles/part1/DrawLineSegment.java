package com.jayden.officialandroiddemo.opengles.part1;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2018/4/19.
 */

public class DrawLineSegment extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new MyRender());
        setContentView(glSurfaceView);
    }

    float vertexArray[] = {
            -0.8f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f, 0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f,
    };

    int index;

    public class MyRender implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
            vbb.order(ByteOrder.nativeOrder());
            FloatBuffer vertex = vbb.asFloatBuffer();
            vertex.put(vertexArray);
            vertex.position(0);
            gl.glLoadIdentity();//矩阵初始化， 把当前矩阵恢复成一个单位矩阵 等同于glLoadMatrix（）， 但在一些情况下，glLoadIdentity()更加效率
            gl.glTranslatef(0, 0, -4);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(3, GLES20.GL_FLOAT, 0, vertex);
            index++;
            index %= 10;
            switch (index) {
                case 0:
                case 1:
                case 2:
                    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                    gl.glDrawArrays(GL10.GL_LINES, 0, 4);
                    break;
                case 3:
                case 4:
                case 5:
                    gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                    gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                    gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                    gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
                    break;
            }
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        }
    }
}
