package com.keernuo.preprocessor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fioman on 2016/12/8 0008.
 * Description: 处理时间的工具类
 */

public class TimeFormat {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd    HH:mm:ss");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    static Date date = new Date();

    /**
     * 获取系统的时间,格式是1999-8-1  13:25:32
     * @param millions
     * @return
     */
    public static String getNowDateAndTime(long millions) {
        date.setTime(millions);
        return sdf.format(date);
    }

    /**
     * 获取系统的时间,格式是时分秒,不显示日期
     * @param millions
     * @return
     */
    public static String getNowTime(long millions) {
        date.setTime(millions);
        return sdf2.format(date);
    }

}
