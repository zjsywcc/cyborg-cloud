package com.ese.cloud.client.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengchenyun on 2017/4/5.
 */
public class DateTimeUtil {
    static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Date转换成String
     * @param date
     * @return
     */
    public static String convert(Date date) {
        if (date != null) {
            return sdf.format(date);
        } else {
            return sdf.format(new Date());
        }
    }

    /**
     * Date转换为MMM DD, YYYY格式的String
     * @param date
     * @return
     */
    public static String convertMDY(Date date) {
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int month = date.getMonth();
        int day = date.getDate();
        int year = date.getYear() + 1900;
        if (date != null) {
            return months[month] + " " + day + ", " + year;
        } else {
            return sdf.format(new Date());
        }
    }

    /**
     * String转换成Date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parse(String date) throws Exception {
        return sdf.parse(date);
    }

    /**
     * 取得两个date间的分钟差值
     * @param date1
     * @param date2
     * @return
     */
    public static int diffMinutes(Date date1, Date date2) {
        return Math.abs((int) ((date1.getTime() - date2.getTime()) / (1000 * 60)));
    }

    /**
     * 日期转换为毫秒
     * @param millseconds
     * @return
     */
    public static String convertMillSecondToDateStr(long millseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millseconds);
        return sdf.format(cal.getTime());
    }

    public static long convert2long(String date) {
        try {
            if (StringUtils.isNotBlank(date)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            logger.error("data string conver2long error:",e);
        }
        return 0l;
    }

    public static Date parseDay(String date) {
        try {
            if (StringUtils.isNotBlank(date)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                return sf.parse(date);
            }
        } catch (ParseException e) {
            logger.error("data string parse error:",e);
        }
        return null;
    }

    /**
     * 获取指定date的后n天
     * @param date
     * @param day
     * @return
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }
}
