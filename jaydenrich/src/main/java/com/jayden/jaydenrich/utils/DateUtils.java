package com.jayden.jaydenrich.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class DateUtils {

    public static StringBuilder toDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new StringBuilder(year + "/" + month + "/" + day);
    }

    /**
     * @param date 2016-04-13T13:49:02.190Z
     * @return 2014年4月13日
     */
    public static String toDateTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }
}
