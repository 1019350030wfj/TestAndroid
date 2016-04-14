package com.jayden.jaydenrich.utils.upload_download_progress.listener;

/**
 * 进度回调接口，比如用于文件上传与下载
 * Created by Jayden on 2016/3/22.
 */
public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
