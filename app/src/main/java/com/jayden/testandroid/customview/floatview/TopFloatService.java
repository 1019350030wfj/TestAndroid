package com.jayden.testandroid.customview.floatview;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/10.
 * Email : 1570713698@qq.com
 */
public class TopFloatService extends Service{

    WindowManager wm = null;
    WindowManager.LayoutParams ballWmParams = null;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private View ballView;//球状太view
    private Button floatImage;
    private boolean isMoving = false;

    //通过bind机制进行通信


    @Override
    public void onCreate() {
        super.onCreate();
        //加载辅助球布局
        ballView = View.inflate(this, R.layout.floatball,null);
        floatImage = (Button) ballView.findViewById(R.id.float_image);
    }

    private void createView() {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        ballWmParams = new WindowManager.LayoutParams();
        ballWmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        ballWmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        ballWmParams.gravity = Gravity.LEFT | Gravity.TOP;
        ballWmParams.x = 0;
        ballWmParams.y = 0;
        ballWmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        ballWmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ballWmParams.format = PixelFormat.RGBA_8888;

        //添加显示
        wm.addView(ballView,ballWmParams);

        //注册触摸事件监听
        floatImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = event.getRawX();
                y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        isMoving = false;
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        isMoving = true;
                        updatePosition();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mTouchStartX = mTouchStartY = 0;
                        break;
                    }
                }

                //如果拖动则返回true，否则返回false
                if (isMoving == false) {
                    return false;
                } else {
                    return true;
                }
            }
        });

        //注册点击事件监听
        floatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TopFloatService.this,"click",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePosition() {
        Log.d("jayden","mTouchStartX = " + mTouchStartX);
        Log.d("jayden","mTouchStartY = " + mTouchStartY);
        Log.d("jayden","X = " + x);
        Log.d("jayden","Y = " + y);
        ballWmParams.x = (int) (x - mTouchStartX);
        ballWmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(ballView, ballWmParams);
    }

    private MyBinder myBinder = new MyBinder();

    public void show(){
        Log.i("jayden", "BindService-->show()");
        createView();
    }

    public void hide(){
        Log.i("jayden", "BindService-->hide()");
        onHide();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("jayden", "BindService-->onBind()");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {

        public TopFloatService getService(){
            return TopFloatService.this;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ballView.isAttachedToWindow()) {
            onHide();
        }
    }

    public void onHide() {
        wm.removeViewImmediate(ballView);
    }
}
