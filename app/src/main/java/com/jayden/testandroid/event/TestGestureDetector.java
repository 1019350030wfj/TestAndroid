package com.jayden.testandroid.event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * 如果是处理滑动事件就在onTouchEvent中实现，如果是双击这种行为的话，就可以用GestureDetector
 * Created by Jayden on 2016/2/11.
 */
public class TestGestureDetector extends Activity implements View.OnTouchListener {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesturedetector);

        mGestureDetector = new GestureDetector(new GestureListener()); //使用派生自OnGestureListener

        Button tv = (Button)findViewById(R.id.button);
        tv.setOnTouchListener(this);
        tv.setFocusable(true);
        tv.setClickable(true);
        tv.setLongClickable(true);
    }


    /*  
     * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，将捕捉到的MotionEvent交给GestureDetector  
     * 来分析是否有合适的callback函数来处理用户的手势  
     */
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class GestureListener implements GestureDetector.OnGestureListener{

        // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发     
        public boolean onDown(MotionEvent e) {
            Log.i("MyGesture", "onDown");
            Toast.makeText(TestGestureDetector.this, "onDown", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*   
         * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发   
         * 注意和onDown()的区别，强调的是没有松开或者拖动的状态   
         *  
         * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制， 
         * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行， 
         * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间 
         * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。 
         */
        public void onShowPress(MotionEvent e) {
            Log.i("MyGesture", "onShowPress");
            Toast.makeText(TestGestureDetector.this, "onShowPress", Toast.LENGTH_SHORT).show();
        }

        // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发     
        ///轻击一下屏幕，立刻抬起来，才会有这个触发  
        //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应  
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("MyGesture", "onSingleTapUp");
            Toast.makeText(TestGestureDetector.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
            return true;
        }

        // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发     
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.i("MyGesture22", "onScroll:"+(e2.getX()-e1.getX()) +"   "+distanceX);
            Toast.makeText(TestGestureDetector.this, "onScroll", Toast.LENGTH_LONG).show();

            return true;
        }

        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发     
        public void onLongPress(MotionEvent e) {
            Log.i("MyGesture", "onLongPress");
            Toast.makeText(TestGestureDetector.this, "onLongPress", Toast.LENGTH_LONG).show();
        }

        // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发     
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.i("MyGesture", "onFling");
            Toast.makeText(TestGestureDetector.this, "onFling", Toast.LENGTH_LONG).show();
            return true;
        }
    };


}  