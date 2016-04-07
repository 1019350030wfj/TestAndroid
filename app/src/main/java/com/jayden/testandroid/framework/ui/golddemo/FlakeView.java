package com.jayden.testandroid.framework.ui.golddemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jayden.testandroid.R;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class FlakeView extends View {

    private Bitmap droid;//金币图片
    private Paint textPaint;

    long startTime,preTime;//记录动画的时间流逝
    int numFlakes = 0;  // Current number of flakes 金币的数量
    ArrayList<Flake> flakes = new ArrayList<Flake>(); // List of current flakes金币列表

    //通过动画，来改变金币的平移位置，旋转角度，
    public ValueAnimator animator = ValueAnimator.ofFloat(0,1);

    public FlakeView(Context context) {
        this(context,null);
    }

    public FlakeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        droid = BitmapFactory.decodeResource(getResources(), R.drawable.b);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long nowTime = System.currentTimeMillis();
                float secs = (float) (nowTime - preTime) / 100f;//时间流逝的百分比
                preTime = nowTime;
                for (int i=0; i< numFlakes; i++) {
                    //获得每个金币，然后改变其位置和旋转角度
                    Flake flake = flakes.get(i);
                    flake.y += (flake.speed * secs);
                    if (flake.y > getHeight()) {
                        flake.y = 0 - flake.height;
                    }
                    flake.rotation = flake.rotation + (flake.rotationSpeed * secs);
                }
                //然后重新绘制
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
    }

    public int getNumFlakes() {
        return numFlakes;
    }

    public void setNumFlakes(int numFlakes) {
        this.numFlakes = numFlakes;
    }

    public void addFlakes(int quantity) {
        for (int i = 0; i< quantity;i++) {
            flakes.add(Flake.createFlake(getWidth(),droid,getContext()));
        }
        setNumFlakes(numFlakes + quantity);
    }

    int frames = 0;     // Used to track frames per second

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flakes.clear();
        numFlakes = 0;
        addFlakes(16);
        animator.cancel();
        startTime = System.currentTimeMillis();
        preTime = startTime;
        frames = 0;
        animator.start();
    }

    //矩阵，用来平移和旋转，金币
    Matrix m = new Matrix(); // Matrix used to translate/rotate each flake during rendering
    float fps = 0;      // frames per second

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i =0; i< numFlakes; i++) {
            Flake flake = flakes.get(i);
            m.setTranslate(-flake.width / 2,-flake.height/2);
            m.postRotate(flake.rotation);
            m.postTranslate(flake.width/2 + flake.x,flake.height/2 + flake.y);
            canvas.drawBitmap(flake.bitmap,m,null);
        }

        ++frames;
        long nowTime = System.currentTimeMillis();
        long delayTime = nowTime - startTime;
        if (delayTime > 1000) {
            float secs = delayTime / 1000f;
            fps = frames / secs;
            startTime = nowTime;
            frames = 0;
        }
    }

    public void pause() {
        animator.cancel();
    }

    public void resume() {
        animator.start();
    }
}
