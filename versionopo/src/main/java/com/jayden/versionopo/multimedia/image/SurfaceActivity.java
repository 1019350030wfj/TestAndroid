package com.jayden.versionopo.multimedia.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**1、源码中出现的AtomicBoolean 是多线程环境下保证数据的原子性操作；
 * 2、多线程下利用SurfaceView生成炫丽的Fractal图形，了解lockCanvas（）作用。当需要高质量和刷新频率高的图形时，就使用这种方法，因为有一条专门线程是专门用于画图的。
 * 3、Canvas的本质规定绘制的是哪些内容和规则，而内容实际是绘制在屏幕上。内容的位置是由坐标决定，而坐标是相对画布而言的。
 * Created by Administrator on 2017/11/29.
 */

public class SurfaceActivity extends BaseActivity {

    SurfaceHolder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holder.addCallback(mCallback);
    }

    @Override
    protected View initContentView() {
        SurfaceView surfaceView = new SurfaceView(this);
        holder = surfaceView.getHolder();
        return surfaceView;
    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isAvailable.set(true);

            //draw background
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            holder.unlockCanvasAndPost(canvas);

            canvas = holder.lockCanvas(new Rect(0, 0, 100, 100));
            canvas.drawColor(Color.RED);
            holder.unlockCanvasAndPost(canvas);
            go();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            isAvailable.set(false);
        }
    };

    protected void go() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ArrayList<Rect> list = new ArrayList<>(xCount * yCount);

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                Rect r = new Rect(i * xSize, j * ySize, (i + 1) * xSize, (j + 1) * ySize);
                list.add(r);

            }
        }

        //打乱排序
        Collections.shuffle(list);

        for (final Rect r : list) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = calculateBitmap(r);
                    syncDraw(r, bitmap);
                }
            });
        }
    }

    synchronized void syncDraw(Rect r, Bitmap bitmap){
        drawBitmap(holder, r, bitmap);
    }
}
