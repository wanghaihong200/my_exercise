package com.example.springbootlearn.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 王海虹
 */
public class DateUtil {
    public static final String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_MIN = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_CHINESE = "yyyy年MM月dd日";

    public static final String FORMAT_ALL_M = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FORMAT_DEFAULT_SMALL = "yyyy-MM-dd";

    /**
    * 将 Date转换为特定的 日期字符串
    * @param:
    * @return:
    * @date: 2022/11/16
    */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 将 时间字符串 转换为 date对象
     *
     * @param:
     */
    public static Date getDate(String dateStr, String pattern) {
        Date date = new Date();
        try {
            date = DateUtils.parseDate(dateStr, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 转换字符串为long型毫秒
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static long date2Long(String dateString, String pattern) throws ParseException {
        Long timeStamp = null;
        try {
            Date date = DateUtils.parseDate(dateString, pattern);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 对特定日期 ，进行增减
     *
     * @param: calendarField: 如 Calendar.DAY_OF_YEAR
     * @return: Date
     * @date: 2022/7/18
     */
    public static Date addDateTime(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static void main(String[] args) {
        Date nowDate = new Date();
        //System.out.println(getDate("2015-09-28", FORMAT_DEFAULT_SMALL));
        System.out.println(format(nowDate, FORMAT_ALL));
        Date afterDate = addDateTime(nowDate, Calendar.DAY_OF_YEAR, 2);

        System.out.println(format(afterDate, FORMAT_ALL));
    }
}
