package com.jayden.testandroid.framework.ui.canvas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public class TestCanvasApi extends Activity{

    private ImageView mImg1;
    private ImageView mImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        mImg1 = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);

        clipRect();
    }

    private void clipRect() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_beauty_diary);

        //画布
        Bitmap newBitmap = Bitmap.createBitmap(300,200, Bitmap.Config.ARGB_4444);

        //画家
        Canvas canvas = new Canvas(newBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);

        //画家画了一个颜色区域
        canvas.drawColor(Color.RED);

        //画家写字
        canvas.drawText("原先的画图区域,为红色",100,100,paint);

        //画家将bitmap画在画布上
        canvas.drawBitmap(bitmap,30,30,paint);

        mImg1.setImageBitmap(newBitmap);
    }
}
