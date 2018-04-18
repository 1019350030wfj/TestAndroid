package com.jayden.officialandroiddemo.opengles.part1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Administrator on 2018/4/18.
 */

public class Square {
    private FloatBuffer vertexBuffer;//顶点缓冲区
    private ShortBuffer drawListBuffer;//绘图顺序顶点缓冲区

    //每个顶点的坐标
    private static final int COORDS_PER_VERTEX = 3;

    //正方形四个顶点的坐标
    private static float squareCoords[] = {-0.5f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f,
                    0.5f, 0.5f, 0.0f
        };

    private short drawOrder[] = {0, 1, 2, 0, 2, 3};//顶点的绘制顺序

    //设置图形的RGB值和透明度
    float color[] = {0.63671875f, 0.76953125f, 0.2265625f, 1.0f};

    public Square(){
        //initialize vertex byte buffer for shape coordinates 坐标数 * 4
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        //为绘制列表初始化字节缓冲 （对应顺序的坐标树 * 2） short是2字节
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    public void draw(){
        new Util().draw(COORDS_PER_VERTEX, vertexBuffer, color, drawOrder, drawListBuffer);
    }
}
