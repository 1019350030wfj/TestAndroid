package com.jayden.testandroid.framework.ui.golddemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.jayden.testandroid.utils.DvAppUtil;

import java.util.HashMap;

/**
 * 封装了每个金币实体的一些信息，
 * 大小，旋转角度，位置，和下滑的速度
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class Flake {

    float x,y;
    float rotation;
    float speed;
    float rotationSpeed;
    int width,height;
    Bitmap bitmap;

    //缓存已经存在此大小的bitmap
    static HashMap<Integer,Bitmap> bitmapMap = new HashMap<>();

    static Flake createFlake(float xRange, Bitmap originalBitmap, Context context) {
        Flake flake = new Flake();
        DisplayMetrics metrics = DvAppUtil.getDisplayMetrics(context);
        if (metrics.widthPixels >= 1080) {
            //如果屏幕的宽度大于1080，每个金币的宽度5《 width 《 85
            flake.width = (int) (5 + (float) Math.random() * 80);
            float hwRadio = originalBitmap.getHeight() / originalBitmap.getWidth();
            flake.height = (int) (flake.width * hwRadio + 60);
        } else {
            //如果屏幕的宽度小于1080，每个金币的宽度5《 width 《 55
            flake.width = (int) (5 + (float) Math.random() * 50);
            float hwRadio = originalBitmap.getHeight() / originalBitmap.getWidth();
            flake.height = (int) (flake.width * hwRadio + 40);
        }
        //金币的位置，在xRange的左边到右边的任意位置
        flake.x = (float) (Math.random() * (xRange - flake.width));
        flake.y = 0-(flake.height + (float)Math.random() * flake.height);

        //金币平移的速度50 -- 200
        flake.speed = 50 + (float)Math.random() * 150;

        //旋转的角度-90 -- 90， 旋转的速度 -45 --45
        flake.rotation = (float)Math.random() * 180 - 90;
        flake.rotationSpeed = (float) Math.random() * 90 - 45;

        flake.bitmap = bitmapMap.get(flake.width);
        if (flake.bitmap == null) {
            flake.bitmap = Bitmap.createScaledBitmap(originalBitmap,
                    (int)flake.width,
                    (int)flake.height,
                    true);
            bitmapMap.put(flake.width,flake.bitmap);
        }
        return flake;
    }
}
