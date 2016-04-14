package com.jayden.jaydenrich.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jayden.jaydenrich.app.BaseAppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class BitmapUtils {

    /**
     * 对选中的上传的图片重新设置宽高
     */
    public static File resizeBitmap(Context context, File file) {
        if (file == null) {
            return null;
        }
        BitmapFactory.Options options = null;
        InputStream is = null;
        Bitmap bm = null;
        try {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new FileInputStream(file.getPath());
            BitmapFactory.decodeStream(is, null, options);
            int w = options.outWidth;
            int h = options.outHeight;
            LogUtils.e("Source , width:" + w + ",height:" + h);
            float scale = getSampleSize(w, h);
            int sscale = (int) scale;
            LogUtils.e("inSampleSize:" + sscale);
            options = new BitmapFactory.Options();
            options.inSampleSize = sscale;
            options.inJustDecodeBounds = false;
            is = new FileInputStream(file.getPath());
            bm = BitmapFactory.decodeStream(is, null, options);
            LogUtils.e("Options , width:" + bm.getWidth() + ",height:" + bm.getHeight());
            bm = scaleImg(bm, 1 / getSampleSize(bm.getWidth(), bm.getHeight()));
            return saveBitmapToFile(bm, FileUtils.mCacheCropDir, file.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
            }
        }
        return null;
    }

    /**
     * 保存图片至本地文件
     */
    public static File saveBitmapToFile(Bitmap bmp, String dir, String name) {
        File file = new File(dir);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        File f = new File(dir, name);
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
            fOut.flush();
            fOut.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    /**
     * 获取压缩比例
     */
    public static float getSampleSize(int cWidth, int cHeight) {
        float sampleSize = 1;
        int min = Math.min(cWidth, cHeight);
        int max = Math.max(cWidth, cHeight);
        if (max <= BaseAppConfig.UPLOAD_IMG_MIN) {

        } else if (min > BaseAppConfig.UPLOAD_IMG_MIN) {
            boolean isWidth = min == cWidth ? true : false;
            if (isWidth) {
                sampleSize = ((float) cWidth) / BaseAppConfig.UPLOAD_IMG_MIN;
            } else {
                sampleSize = ((float) cHeight) / BaseAppConfig.UPLOAD_IMG_MIN;
            }
            max = (int) (max / sampleSize);
            if (max > BaseAppConfig.UPLOAD_IMG_MAX) {
                sampleSize = sampleSize * ((float) max) / BaseAppConfig.UPLOAD_IMG_MAX;
            }
        } else if (min <= BaseAppConfig.UPLOAD_IMG_MIN && max > BaseAppConfig.UPLOAD_IMG_MAX) {
            boolean isWidth = max == cWidth ? true : false;
            if (isWidth) {
                sampleSize = ((float) cWidth) / BaseAppConfig.UPLOAD_IMG_MAX;
            } else {
                sampleSize = ((float) cHeight) / BaseAppConfig.UPLOAD_IMG_MAX;
            }
            max = (int) (max / sampleSize);
            if (max > BaseAppConfig.UPLOAD_IMG_MAX) {
                sampleSize = sampleSize * ((float) max) / BaseAppConfig.UPLOAD_IMG_MAX;
            }
        }
        LogUtils.e("图片压缩比例：" + sampleSize);
        return sampleSize;
    }

    public static Bitmap scaleImg(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        LogUtils.e("new , width:" + bm.getWidth() * scale + ",height:" + bm.getHeight() * scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix,
                true);
        return newbm;
    }

    /**
     * 获取要显示的Img的宽高
     */
    public static Point getSize(ImageView imageView) {
        Point p = new Point();
        DisplayMetrics displayMetrics = imageView.getContext().getResources()
                .getDisplayMetrics();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int width = imageView.getWidth();
        if (width <= 0) {
            width = lp.width;
        }
        if (width <= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                width = imageView.getMaxWidth();
            }
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        int height = imageView.getHeight();
        if (height <= 0) {
            height = lp.height;
        }
        if (height <= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                height = imageView.getMaxHeight();
            }
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        p.set(width, height);
        return p;
    }

}
