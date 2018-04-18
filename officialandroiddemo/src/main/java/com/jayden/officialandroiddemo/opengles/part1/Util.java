package com.jayden.officialandroiddemo.opengles.part1;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Administrator on 2018/4/18.
 */

public class Util {
    private static final int LENGTH = 4;//float 类型大小为4字节

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "   gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "   gl_FragColor = vColor;" +
                    "}";

    public int loadShader(int type, String shaderCode) {
        //创建一个vertex shader 类型（GLES20.GL_VERTEX_SHADER）
        //或一个fragment shader类型（GLES20.GL_FRAGMENT_SHADER）
        int shader = GLES20.glCreateShader(type);

        //将源码添加到shader并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * 顶点着色器（Vertex Shader） ： 用来渲染形状定点的OpenGL ES代码
     * @return 顶点着色器
     */
    public int getVertexShader(){
        return loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    }

    /**
     * 片段着色器（Fragment Shader）：使用颜色或纹理渲染形状表面的OpenGL ES代码
     * @return 片段着色器
     */
    public int getFragmentShader(){
        return loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
    }

    /**
     * 程式（Program）： 一个OpenGL ES对象， 包含了你希望用来绘制一个或更多图形所要用到的着色器
     * 程式（Program）是用来装配着色器的（个人理解）
     * @return
     */
    public int getProgram() {
        //获取顶点着色器
        int vertexShader = getVertexShader();
        //获取片段着色器
        int fragmentShader = getFragmentShader();

        int program = GLES20.glCreateProgram();//创建空的OpenGL ES Program
        GLES20.glAttachShader(program, vertexShader);// 将vertex shader添加到program
        GLES20.glAttachShader(program, fragmentShader);// 将fragment shader添加到program
        GLES20.glLinkProgram(program);// 创建可执行的OpenGL ES program

        //添加program到OpenGL ES环境中
        GLES20.glUseProgram(program);
        return program;
    }

    /**
     *
     * @param coords_per_vertex 每个顶点的坐标
     * @param vertexBuffer 浮点缓冲区
     * @param color 颜色数组， 数组的四个数分别为图形的RGB值和透明度
     */
    public void draw(int coords_per_vertex, FloatBuffer vertexBuffer, float color[]){
        //获取程式
        int program = getProgram();

        //得到处理到顶点着色器的vPosition成员
        int vPositionHandler = GLES20.glGetAttribLocation(program, "vPosition");

        //启用一个指向图形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(vPositionHandler);

        //准备坐标数据
        GLES20.glVertexAttribPointer(vPositionHandler, coords_per_vertex,
                GLES20.GL_FLOAT, false,
                LENGTH * coords_per_vertex, vertexBuffer);

        //得到处理到片段着色器的vPosition成员
        int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");

        //设置颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        //绘制三角形比较简单， 这里才有glDrawArrays方法（默认是逆时针方向）
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0 , 3);

        //禁用指向图形的顶点数据
        GLES20.glDisableVertexAttribArray(vPositionHandler);
    }

    /**
     *
     * @param coords_per_vertex 每个顶点的坐标数
     * @param vertexBuffer 浮点缓冲区
     * @param color 颜色数组，数组的四个数分别为图形的RGB值和透明度
     * @param drawOrder  绘制顶点的顺序（逆时针方向）
     * @param drawListBuffer 绘图顺序顶点的缓冲区
     */
    public void draw(int coords_per_vertex, FloatBuffer vertexBuffer, float color[], short drawOrder[], ShortBuffer drawListBuffer){
        //获取程式
        int program = getProgram();
        //得到处理到顶点着色器的vPosition
        int vPositionHandler = GLES20.glGetAttribLocation(program, "vPosition");
        //启用一个指向图形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(vPositionHandler);

        //准备坐标数据
        GLES20.glVertexAttribPointer(vPositionHandler, coords_per_vertex,
                GLES20.GL_FLOAT, false,
                LENGTH * coords_per_vertex, vertexBuffer);

        // 得到处理片段着色器的vPosition成员
        int mColorhandle = GLES20.glGetUniformLocation(program, "vColor");

        //设置颜色
        GLES20.glUniform4fv(mColorhandle, 1, color, 0);

        //绘制图形
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT,drawListBuffer);

        //禁用指向图形的顶点数据
        GLES20.glDisableVertexAttribArray(vPositionHandler);
    }
}
