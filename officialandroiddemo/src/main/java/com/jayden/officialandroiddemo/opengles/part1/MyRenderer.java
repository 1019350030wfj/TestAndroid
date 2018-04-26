package com.jayden.officialandroiddemo.opengles.part1;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;

    /**
     * 加载制定的shader的方法
     *
     * @param shaderType shader的类型， GLES20.GL_VERTEX_SHADER GLES20.GL_FRAGMENT_SHADER
     * @param sourceCode shader的脚本
     * @return shader索引
     */
    private int loadShader(int shaderType, String sourceCode) {
        //创建一个新shader
        int shader = GLES20.glCreateShader(shaderType);
        //若创建成功则加载shader
        if (shader != 0) {
            // 加载shader的源码
            GLES20.glShaderSource(shader, sourceCode);
            //编译shader
            GLES20.glCompileShader(shader);
            //存放编译成功shader数量的数组
            int[] compiled = new int[1];
            //获取shader的编译情况
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        //加载顶点着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        //加载片元着色器
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0) {
            return 0;
        }

        //创建程序
        int program = GLES20.glCreateProgram();
        //若程序创建成功则向程序加入顶点着色器与片元着色器
        if (program != 0) {
            //向程序中加入顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            //向程序中加入片元着色器
            GLES20.glAttachShader(program, fragmentShader);
            //链接程序
            GLES20.glLinkProgram(program);
            //存放链接成功program数量的数组
            int[] linkStatus = new int[1];
            //获取program的链接情况
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            //若链接失败则报错并删除程序
            if (linkStatus[0] != GLES20.GL_TRUE) {
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    /**
     * 获取图形的顶点
     * 特别提示： 由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer转换，
     * 关键是要通过ByteOrder设置nativeOrder（），否则有可能会出问题
     *
     * @return
     */
    private FloatBuffer getVertices() {
        float vertices[] = {
                0.0f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f,
        };

        //创建顶点坐标数据缓冲
        // vertices.length*4 是因为一个float占四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        //设置字节顺序
        vbb.order(ByteOrder.nativeOrder());
        //转换为Float型缓冲
        FloatBuffer vertexBuffer = vbb.asFloatBuffer();
        // 向缓冲区中放入顶点坐标数据
        vertexBuffer.put(vertices);
        // 设置缓冲区起始位置
        vertexBuffer.position(0);

        return vertexBuffer;
    }

    /*===================================着色器语言======================================*/
    /**
     *  限定符：
     *  attribute 一般用于每个顶点都各不相同的量，如顶点位置、颜色等
     *  uniform 一般用于对同一组顶点组成的单个3D物体中所有顶点都相同的量
     */
    //顶点着色器的脚本
    private static final String VERTICES_SHADER
            = "attribute vec2 vPosition;                \n" //顶点位置属性vPosition
            + "void main(){                             \n"
            + "      gl_Position = vec4(vPosition, 0, 1);\n" //确定顶点位置
            + "}";

    //片元着色器的脚本
    private static final String FRAGMENT_SHADER
            = "precision mediump float;                 \n"     //声明float类型的精度为中等（精确度越高越好资源）
            + "uniform vec4 uColor;                     \n"
            + "void main(){                             \n"
            + "    gl_FragColor = uColor;               \n" //确定顶点位置
            + "}";

    /*===================================着色器语言======================================*/

    /**
     * 当GLSurfaceView中的Surface被创建的时候（界面显示）回调此方法，一般在这里做一些初始化
     *
     * @param gl     1.0版本的OpenGL对象，这里用于兼容老版本，用处不大
     * @param config egl的配置信息（GLSurfaceView会自动创建egl， 这里可以先忽略）
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 初始化着色器
        // 基于顶点着色器与片元着色器创建程序
        program = createProgram(VERTICES_SHADER, FRAGMENT_SHADER);
        // 获取着色器中的属性引用id（传入的字符串就是我们着色器脚本中的属性名）
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        uColor = GLES20.glGetUniformLocation(program, "uColor");

        //设置clear color 颜色RGBA（这里仅仅是设置清屏时GLES20.glClear（）用的颜色值而不是执行清屏）
        GLES20.glClearColor(1.0f, 0, 0, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置绘图的窗口（可以理解成在画布上划出一块区域来画图）
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 获取图形的顶点坐标
        FloatBuffer vertices = getVertices();

        //清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        //使用某套shader程序
        GLES20.glUseProgram(program);
        // 为画笔指定顶点位置数据（vPosition）
        //GLES20.glVertexAttribPointer(属性索引,单顶点大小,数据类型,归一化,顶点间偏移量,顶点Buffer)
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0 , vertices);
        //允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(vPosition);
        //设置属性uColor（颜色 索引 R，G，B，A）
        GLES20.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
        //绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0 ,3);//GLES20.glDrawArrays(绘制方式, 起始偏移, 顶点数量)
    }
}
