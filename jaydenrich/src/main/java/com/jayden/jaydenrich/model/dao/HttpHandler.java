package com.jayden.jaydenrich.model.dao;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.internal.$Gson$Types;
import com.jayden.jaydenrich.R;
import com.jayden.jaydenrich.model.test.ApiRequest;
import com.jayden.jaydenrich.utils.BitmapUtils;
import com.jayden.jaydenrich.utils.LogUtils;
import com.jayden.jaydenrich.utils.NetworkUtils;
import com.jayden.jaydenrich.utils.SecurityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp工具类
 * <p/>
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class HttpHandler {

    private static final int TIME_OUT = 60;

    private static final MyComparator mPairComparator = new MyComparator();
    private static volatile HttpHandler sInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private HttpHandler(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(CookiesManager.getInstance(context));//支持cookie
        mOkHttpClient = builder.build();

        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static HttpHandler getInstance(Context context) {
        if (sInstance == null) {
            synchronized (HttpHandler.class) {
                if (sInstance == null) {
                    sInstance = new HttpHandler(context);
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 按键名升序排序
     * 在参数后加一个加密密钥
     * 最后对于MD5加密
     */
    private static String getSign(List<Param> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            Collections.sort(list, mPairComparator);
            for (Param item : list) {
                if (TextUtils.isEmpty(item.value)) {
                    continue;
                }
                if (sb.length() != 0) {
                    sb.append("&");
                }
                sb.append(item.key);
                sb.append("=");
                sb.append(item.value);
            }
        }
        sb.append(ApiRequest.KEY);
        LogUtils.e("getSign:" + sb.toString());
        return SecurityUtils.encodeMD5(sb.toString());
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    /**
     * 异步get获取数据
     */
    public void getAsync(Context context, String url, ResultCallback callback) {
        Request request = new Request.Builder().url(url).tag(context).build();
        deliveryResult(context, callback, request);
    }

    /**
     * post异步提交数据
     */
    public void postAsync(Context context, String url, List<Param> params, ResultCallback callback) {
        if (!NetworkUtils.isNetConnected(context)) {
            callback.onError(null, new Exception(context.getResources().getString(R.string.loading_no_network)));
            return;
        }

        //表单形式上传数据
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (Param param : params) {
                LogUtils.e("param:" + param.toString());
                if (param.key != null && param.value != null) {
                    bodyBuilder.add(param.key, param.value);
                }
            }
        }
        bodyBuilder.add("sign", getSign(params));

        //TODO 请求头
        Headers.Builder builder = new Headers.Builder();
//        if (CommonUtils.isCurrentUserExist()) {
//            builder.add("TOKEN", BaseApp.mCurrentUser.token);
//        }
        RequestBody body = bodyBuilder.build();
        Request.Builder rb = new Request.Builder().url(url).post(body).tag(context);
        //OkHttp header 中不能传中文的坑
        Headers header = builder.build();
        rb.headers(header);
        Request request = rb.build();
        deliveryResult(context, callback, request);
    }

    /**
     * post异步文件上传
     */
    public void postAsync(Context context, String url, List<Param> params, List<FileParam> fileParams, final ResultCallback callback) {
        if (!NetworkUtils.isNetConnected(context)) {
            callback.onError(null, new Exception(context.getResources().getString(R.string.loading_no_network)));
            return;
        }
        Request request = buildMultipartFormRequest(context, url, params, fileParams);
        deliveryResult(context, callback, request);
    }

    /**
     * download异步文件下载
     */
    public void downloadAsync(final Context context, final String url, final String saveDir, final String fileName, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(saveDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(context, file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
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

        });
    }


    private Request buildMultipartFormRequest(Context context, String url, List<Param> params, List<FileParam> fileParams) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM); //表单形式上传数据

        //TODO 请求头
        Headers header = null;
//        if (CommonUtils.isCurrentUserExist()) {
//            header = new Headers.Builder().add("token", BaseApp.mCurrentUser.token).build();
//        } else {
//            header = new Headers.Builder().build();
//        }
        if (false) {
//            header = new Headers.Builder().add("token", BaseApp.mCurrentUser.token).build();
        } else {
            header = new Headers.Builder().build();
        }

        //请求参数
        if (params != null && params.size() > 0) {
            for (Param param : params) {
                if (param.value != null) {
                    LogUtils.e("param:" + param.toString());
                    bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                            RequestBody.create(null, param.value));
                }
            }
        }

        //加一个签名信息
        bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"sign\""),
                RequestBody.create(null, getSign(params)));

        //文件上传，这边是传图片,
        //这边还有对上传的图片进行压缩
        if (fileParams != null && fileParams.size() > 0) {
            for (FileParam param : fileParams) {
                LogUtils.e("file:" + param.toString());
                bodyBuilder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + param.key + "\"; filename=\"" + param.file.getName() + "\""),
                        RequestBody.create(MediaType.parse("image/*"), BitmapUtils.resizeBitmap(context, param.file)));
            }
        }
        RequestBody body = bodyBuilder.build();
        return new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .headers(header)
                .build();
    }

    private void deliveryResult(final Context context, final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("http request onFailure, msg:" + e.getMessage());
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.e("http request onResponse" + response.toString());
                if (response.code() == 500 || response.code() == 502) {
                    sendFailedStringCallback(response.request(), new Exception("获取数据失败"), callback);
                    return;
                }
                try {
                    final String string = response.body().string();
                    LogUtils.e("onResponse：data is " + string);
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(context, string, callback);
                    } else {
                        Object o = GsonHandler.fromJson(string, callback.mType);
                        sendSuccessResultCallback(context, o, callback);
                    }
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }

        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Context context, final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    public static abstract class ResultCallback<T> {

        public Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        public static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }


    /**
     * 参数名升序排列
     */
    private static class MyComparator implements Comparator {

        @Override
        public int compare(Object o, Object t1) {
            Param first = (Param) o;
            Param last = (Param) t1;
            return first.key.compareTo(last.key);
        }
    }

    public static class Param {
        public String key;
        public String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[key:" + key + ",value:" + value + "]";
        }
    }

    public static class FileParam {
        public String key;
        public File file;

        public FileParam(String key, File file) {
            this.key = key;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileParam{" +
                    "key='" + key + '\'' +
                    ", file=" + file.getPath() +
                    '}';
        }
    }
}
