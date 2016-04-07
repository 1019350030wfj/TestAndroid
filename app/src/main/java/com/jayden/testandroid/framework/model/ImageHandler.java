package com.jayden.testandroid.framework.model;

import android.content.Context;
import android.widget.ImageView;

import com.jayden.testandroid.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Jayden on 2016/4/6.
 * Email : 1570713698@qq.com
 */
public class ImageHandler {

    public static void displayImage(Context context, ImageView imageView,String url) {
        Picasso.with(context).load(url).placeholder(R.drawable.bg_default_banner).error(R.drawable.bg_default_banner).into(imageView);
    }
}
