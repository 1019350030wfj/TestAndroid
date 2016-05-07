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
 * Created by Jayden on 2016/4/23.
 * Email : 1570713698@qq.com
 */
public class FirstView extends View {

    public FirstView(Context context) {
        this(context,null);

    }

    public FirstView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    private int viewWidth;
    private int viewHeight;

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.BLUE);//画笔颜色
        paint.setStyle(Paint.Style.STROKE);//空心
        paint.setStrokeWidth(4);//边框的宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //整张画布，画成绿色
        canvas.drawColor(Color.GREEN);

        //获取到view的宽度
        viewWidth = getWidth();
        viewHeight = getHeight();

        paint.setColor(Color.BLUE);

        //画圆形
        canvas.drawCircle(viewWidth / 10 + 60,viewHeight / 10 + 10,viewWidth / 10 + 10,paint);

        //绘制矩形
        canvas.drawRect(100, viewHeight / 5 + 20, viewWidth / 5 + 10, viewHeight * 2 / 5 + 20, paint);

        /*
         *绘制圆角矩形
         * 1、实心
         * 2、颜色改为红色
         */
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        RectF rectF = new RectF(10,viewHeight/2 +40,viewWidth/5+10,viewHeight*3/5+40);

        //40为x椭圆半径,10为y椭圆半径
        canvas.drawRoundRect(rectF,40,40,paint);

        /*
        * 画文字
        * 1、文字大小为48
         */
        paint.setTextSize(48);

        paint.setColor(Color.WHITE);
        canvas.drawText("Jayden",viewWidth / 2, viewHeight / 2,paint);

        /*
         * 画路径
         * 画三角形
         */
        Path path = new Path();
        path.moveTo(300,400);
        path.lineTo(500,100);
        path.lineTo(600,200);
        path.lineTo(500,300);
        path.close();

        canvas.drawPath(path,paint);
    }
}
