package com.jayden.versionopo.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jayden on 2017/5/5.
 */

public class RegularPolygonView extends View {

    private Paint mPaint;

    public RegularPolygonView(Context context) {
        super(context);
    }

    public RegularPolygonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegularPolygonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStar(canvas, getHeight() / 12, 5, false);
    }

    /**
     * 绘制彩色多边形或星形
     *
     * @param canvas Canvas画布
     * @param radius 外接圆半径
     * @param count  外顶点数
     * @param isStar 是否为星形
     */
    private void drawStar(Canvas canvas, float radius, int count, boolean isStar) {
        canvas.save();
        canvas.translate(radius, radius);
        if ((!isStar) && count < 3) {
            canvas.translate(-radius, -radius);
            return;
        }
        if (isStar && count < 5) {
            canvas.translate(-radius, -radius);
            return;
        }
        canvas.rotate(-90);
        Path path = new Path();
        float inerRadius = count % 2 == 0 ?
                (radius * (cos(360 / count / 2) - sin(360 / count / 2) * sin(90 - 360 / count) / cos(90 - 360 / count)))
                : (radius * sin(360 / count / 4) / sin(180 - 360 / count / 2 - 360 / count / 4));
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                path.moveTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
            } else {
                path.lineTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
            }
            if (isStar) {
                path.lineTo(inerRadius * cos(360 / count * i + 360 / count / 2), inerRadius * sin(360 / count * i + 360 / count / 2));
            }
        }
        path.close();
        canvas.drawPath(path, mPaint);
        canvas.rotate(90);
        canvas.translate(-radius, -radius);
        canvas.restore();
    }


    /**
     * Math.sin的参数为弧度，使用起来不方便，重新封装一个根据角度求sin的方法
     *
     * @param num 角度
     * @return
     */
    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    /**
     * 与sin同理
     */
    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }
}
