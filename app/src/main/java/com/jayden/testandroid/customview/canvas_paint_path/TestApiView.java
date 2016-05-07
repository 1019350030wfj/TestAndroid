package com.jayden.testandroid.customview.canvas_paint_path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jayden on 2016/4/26.
 * Email : 1570713698@qq.com
 */
public class TestApiView extends View {

    public TestApiView(Context context) {
        this(context, null);
    }

    public TestApiView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestApiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画梯形
//        Path path2 = new Path();
//        path2.reset();
//        path2.moveTo(30, 400); //左顶点 也即起始点
//        path2.lineTo(90, 400); //左顶点
//        path2.lineTo(120, 440); //右底部
//        path2.lineTo(0, 440); // 左底部
//        canvas.drawPath(path2, paint);

        Path path = new Path();
        path.moveTo(400,200);
        path.lineTo(400,600);
        path.lineTo(150,600);

        RectF rect = new RectF();
        rect.left = 100;
        rect.top = 500;
        rect.right = 200;
        rect.bottom = 600;

        path.addArc(rect,90,180);

        path.lineTo(150,500);
        path.lineTo(300,500);
        path.lineTo(300,200);
        path.lineTo(400,200);

        paint.setColor(Color.BLUE);
        canvas.drawPath(path,paint);

        rect.left = 50;
        rect.top = 590;
        rect.right = 450;
        rect.bottom = 630;
        paint.setColor(Color.GRAY);
        canvas.drawOval(rect,paint);
    }
}
