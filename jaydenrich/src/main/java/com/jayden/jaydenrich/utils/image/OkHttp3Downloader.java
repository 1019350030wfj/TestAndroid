package com.jayden.jaydenrich.utils.image;

import android.content.Context;
import android.net.Uri;
import android.os.StatFs;

import com.jayden.jaydenrich.utils.FileUtils;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * 让Picasso使用okhttp作网络请求
 *
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class OkHttp3Downloader implements Downloader {

    private static final int MB = 1024 * 1024;
    private static final int MIN_DISK_CACHE_SIZE = 20 * MB;
    private static final int MAX_DISK_CACHE_SIZE = 100 * MB;

    private final Call.Factory client;
    private final Cache cache;

    private static File createDefaultCacheDir(Context context) {
        File cache = new File(FileUtils.getCacheImgDir(context));
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

    /**
     * 计算缓存磁盘的大小
     * @param dir
     * @return
     */
    private static long calculateDiskCacheSize(File dir) {
        long size = MIN_DISK_CACHE_SIZE;

        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }

        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
    }

    private static OkHttpClient defaultOkHttpClient(File cacheDir, long maxSize) {
        return new OkHttpClient.Builder()
                .cache(new okhttp3.Cache(cacheDir, maxSize))
                .build();
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     */
    public OkHttp3Downloader(Context context) {
        this(createDefaultCacheDir(context));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     */
    public OkHttp3Downloader(File cacheDir) {
        this(cacheDir, calculateDiskCacheSize(cacheDir));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     *
     * @param maxSize The size limit for the cache.
     */
    public OkHttp3Downloader(final Context context, final long maxSize) {
        this(createDefaultCacheDir(context), maxSize);
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     * @param maxSize  The size limit for the cache.
     */
    public OkHttp3Downloader(File cacheDir, long maxSize) {
        this(defaultOkHttpClient(cacheDir, maxSize));
    }

    public OkHttp3Downloader(OkHttpClient client) {
        this.client = client;
        this.cache = client.cache();
    }

    public OkHttp3Downloader(Call.Factory client) {
        this.client = client;
        this.cache = null;
    }

    public Cache getCache() {
        return cache;
    }

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                CacheControl.Builder builder = new CacheControl.Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }

        Request.Builder builder = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }

        okhttp3.Response response = client.newCall(builder.build()).execute();
        int responseCode = response.code();
        if (responseCode >= 300) {
            response.body().close();
            throw new ResponseException( response.message(),networkPolicy,responseCode);
        }

        boolean fromCache = response.cacheResponse() != null;

        ResponseBody responseBody = response.body();
        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
    }

//    @Override
//    public Response load(Uri uri, boolean b) throws IOException {
//        CacheControl cacheControl = null;
//        if (b) {
//            cacheControl = CacheControl.FORCE_CACHE;
//        } else {
//            cacheControl = CacheControl.FORCE_NETWORK;
//        }
//        Request.Builder builder = new Request.Builder().url(uri.toString());
//        if (cacheControl != null) {
//            builder.cacheControl(cacheControl);
//        }
//
//        okhttp3.Response response = client.newCall(builder.build()).execute();
//        int responseCode = response.code();
//        if (responseCode >= 300) {
//            response.body().close();
//            throw new ResponseException(responseCode + " " + response.message());
//        }
//
//        boolean fromCache = response.cacheResponse() != null;
//
//        ResponseBody responseBody = response.body();
//        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
//    }

    @Override
    public void shutdown() {
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }
}
