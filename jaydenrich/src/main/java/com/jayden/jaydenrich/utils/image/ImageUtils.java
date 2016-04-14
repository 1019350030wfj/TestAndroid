package com.jayden.jaydenrich.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jayden.jaydenrich.R;
import com.jayden.jaydenrich.utils.BitmapUtils;
import com.jayden.jaydenrich.utils.LogUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

/**
 * Picasso图片加载和处理图片缓存
 *          工具类
 *
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class ImageUtils {

    private static Picasso sInstance;
    private static LruCache memoryCache;
    private static OkHttp3Downloader downloader;

    public static Picasso getPicasso(Context context) {
        if (sInstance == null) {
            memoryCache = new LruCache(context);
            downloader = new OkHttp3Downloader(context);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.memoryCache(memoryCache);
            builder.downloader(downloader);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    LogUtils.e("onImageLoadFailed uri:" + uri.getPath() + ", exception:" + exception.getMessage());
                    exception.printStackTrace();
                }
            });
            builder.loggingEnabled(true);
            sInstance = builder.build();
            Picasso.setSingletonInstance(sInstance);
        }
        return sInstance;
    }

    public static void onPause(Activity activity) {
        getPicasso(activity).pauseTag(activity);
    }

    public static void onResume(Activity activity) {
        getPicasso(activity).resumeTag(activity);
    }

    /**
     * 清空本地缓存
     */
    public static void clearDiskCache() {
        if (downloader == null) {
            LogUtils.e("清空本地缓存失败");
            return;
        }
        try {
            downloader.getCache().evictAll();
            LogUtils.e("清空本地缓存成功");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("清空本地缓存失败");
        }

    }

    /**
     * 清空内存缓存
     */
    public static void clearMemoryCache(Context context) {
        getPicasso(context).cancelTag(context);
        if (memoryCache != null) {
            memoryCache.clear();
            LogUtils.e(getPicasso(context).getSnapshot().toString());
        }
    }

    /**
     * 从url加载用户头像
     */
    public static void getAvater(Context context, ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_img));
            return;
        }
        Point p = BitmapUtils.getSize(imageView);
        getPicasso(context).load(url)
                .placeholder(R.drawable.bg_default_img).error(R.drawable.bg_default_img)
                .resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(imageView);
    }

    /**
     * 从url加载banner大图
     */
    public static void getBannerImage(Context context, final ImageView mImageView, String url) {
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (TextUtils.isEmpty(url)) {
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_banner));
            return;
        }
        Point p = BitmapUtils.getSize(mImageView);
        getPicasso(context).load(url)
                .placeholder(R.drawable.bg_default_banner).error(R.drawable.bg_default_banner).
                resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            @Override
            public void onError() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }

    /**
     * 从url记载普通图片
     */
    public static void getBigImage(Context context, final ImageView mImageView, String url) {
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        getPicasso(context).load(url).error(R.drawable.bg_default_banner)
                .skipMemoryCache().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView);
    }

    /**
     * 从file记载普通图片
     */
    public static void getBigFileImage(Context context, final ImageView mImageView, String path) {
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        getPicasso(context).load(new File(path)).error(R.drawable.bg_default_img)
                .skipMemoryCache().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView);
    }

    /**
     * 加载圆角图片
     */
    public static void getRoundImage(Context context, final ImageView mImageView, String url, final View tag) {
        if (TextUtils.isEmpty(url)) {
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_img));
            return;
        }
        Point p = BitmapUtils.getSize(mImageView);
        getPicasso(context).load(url)
                .placeholder(R.drawable.bg_default_img).error(R.drawable.bg_default_img)
                .resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                tag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                tag.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取圆行图，只能设置为centerCrop
     * @param context
     * @param mImageView
     * @param url
     */
    public static void getCircleImage(Context context, final ImageView mImageView, String url) {
        if (TextUtils.isEmpty(url)) {
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_img));
            return;
        }
        Point p = BitmapUtils.getSize(mImageView);
        getPicasso(context).load(url)
                .placeholder(R.drawable.bg_default_img).error(R.drawable.bg_default_img)
                .resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView);
    }

    /**
     * 从url记载普通图片
     */
    public static void getImage(Context context, final ImageView mImageView, String url) {
        if (TextUtils.isEmpty(url)) {
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_img));
            return;
        }
        Point p = BitmapUtils.getSize(mImageView);
        getPicasso(context).load(url)
                .placeholder(R.drawable.bg_default_img).error(R.drawable.bg_default_img)
                .resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            @Override
            public void onError() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }

    /**
     * 从file记载普通图片
     */
    public static void getFileImage(Context context, final ImageView mImageView, String path) {
        if (TextUtils.isEmpty(path)) {
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_default_img));
            return;
        }
        Point p = BitmapUtils.getSize(mImageView);
        getPicasso(context).load(new File(path))
                .placeholder(R.drawable.bg_default_img).error(R.drawable.bg_default_img)
                .resize(p.x, p.y).centerCrop().config(Bitmap.Config.RGB_565)
                .tag(context).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onError() {
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }

    public static void getImageFile(Context context, ImageView imageView, String path, int errorResId, int placeHolderId) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(path)) {
            imageView.setImageDrawable(context.getResources().getDrawable(placeHolderId));
            return;
        }
        Point p = BitmapUtils.getSize(imageView);
        getPicasso(context).load(new File(path))
                .placeholder(placeHolderId).error(errorResId)
                .resize(p.x, p.y).centerCrop()
                .tag(context).into(imageView);
    }

    public static void getImageUrl(Context context, ImageView imageView, String url, int errorResId, int placeHolderId) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(context.getResources().getDrawable(placeHolderId));
            return;
        }
        getPicasso(context).load(url).placeholder(placeHolderId).error(errorResId).fit().tag(context).into(imageView);
    }

    public static void getImage2B1(Context context, ImageView imageView, String url) {
        getImageUrl(context, imageView, url, R.drawable.bg_default_banner, R.drawable.bg_default_banner);
    }
}
