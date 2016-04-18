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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    /**
     * 对图片进行压缩
     * @param bitmap
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 32) {    //循环判断如果压缩后图片是否大于32kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            if (options < 0) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap targetBitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return targetBitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据Bitmap图片压缩）：
     * @return
     */
    public static Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressBitmap(bitmap);//压缩好比例大小后再进行质量压缩
    }
}
