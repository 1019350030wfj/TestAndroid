package com.jayden.jaydenrich.utils.upload_download_progress;

import android.content.Context;

import com.jayden.jaydenrich.model.dao.HttpHandler;
import com.jayden.jaydenrich.utils.LogUtils;
import com.jayden.jaydenrich.utils.upload_download_progress.helper.ProgressHelper;
import com.jayden.jaydenrich.utils.upload_download_progress.listener.impl.UIProgressListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class SampleActivity {

    /**
     *
     * @param context
     * @param url
     * @param saveDir 存储路径
     * @param saveFileName 文件名
     * @param listener
     */
    public void download(Context context, String url,
                         final String saveDir,final String saveFileName,
                         final ILoadingListener listener) {
        //构造请求
        final Request request1 = new Request.Builder()
                .url(url)
                .build();

        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(HttpHandler.getInstance(context).getOkHttpClient(),
                new DownloadProgressListener(context)).newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("jayden","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.d("jayden","onResponse");
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(saveDir, saveFileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    listener.onSuccess();
                } catch (IOException e) {
                    listener.onFail("");
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            public void onFailure(Request request, IOException e) {
                LogUtils.d("jayden", "error ", e);
            }

            public void onResponse(Response response) throws IOException {
                LogUtils.d("jayden", response.body().string());
            }
        });
    }

    //这个是ui线程回调，可直接操作UI
    class DownloadProgressListener extends UIProgressListener {
        private WeakReference<Context> weakReference;

        public DownloadProgressListener(Context context) {
            this.weakReference = new WeakReference<Context>(context);
        }

        @Override
        public void onUIProgress(long bytesRead, long contentLength, boolean done) {
            LogUtils.d("jayden", "bytesRead:" + bytesRead);
            LogUtils.d("jayden", "contentLength:" + contentLength);
            LogUtils.d("jayden", "done:" + done);
            if (contentLength != -1) {
                //长度未知的情况下回返回-1
                LogUtils.d("jayden",  (100 * bytesRead) / contentLength+ "% done");
            }
        }

        @Override
        public void onUIStart(long bytesRead, long contentLength, boolean done) {
            super.onUIStart(bytesRead, contentLength, done);
        }

        @Override
        public void onUIFinish(long bytesRead, long contentLength, boolean done) {
            super.onUIFinish(bytesRead, contentLength, done);
        }
    }


    public static interface ILoadingListener {
        void onSuccess();

        void onFail(String failMsg);
    }
}
