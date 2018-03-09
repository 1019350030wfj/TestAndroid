package com.jayden.versionopo.customview.image.largeimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jayden.versionopo.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/1/13.
 */

public class LargeImageViewSimpleBak  extends AppCompatActivity
{
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

        mImageView = (ImageView) findViewById(R.id.id_largetImageview);
        try
        {
            InputStream inputStream = getAssets().open("hxswdt.jpg");

            //获得图片的宽、高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            int width = tmpOptions.outWidth;
            int height = tmpOptions.outHeight;

            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
            mImageView.setImageBitmap(bitmap);


        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

}
