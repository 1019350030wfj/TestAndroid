package com.jayden.versionopo.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Jayden on 2017/4/13.
 */

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        //如果异常是在Asynctash 里面的后台线程抛出
        //那么实际的异常任然可以通过getCause获得
        Throwable cause = e;
        while (cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        //stacktraceAsString 就是获取的crash堆栈信息
        final String stacktraceAsString = result.toString();
        printWriter.close();
    }
}
