package com.changyue.blogserver.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 时间工具类
 * @date : 2020-03-26 10:30
 */
public class DateUtils {

    /**
     * 字符串转时间
     *
     * @param dateStr    时间字符串
     * @param dateFormat 时间格式
     * @return 时间
     */
    public static Date stringToDate(String dateStr, String dateFormat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 在时间上添加小时
     *
     * @param date  传入时间
     * @param hours 小时
     * @return 时间
     */
    public static Date addHours(Date date, int hours) {
        Calendar cd = Calendar.getInstance();

        cd.setTime(date);

        cd.add(Calendar.HOUR_OF_DAY, hours);

        return cd.getTime();
    }

}
