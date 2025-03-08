package com.dw3c.infocollecttool.utils;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Clayton
 * @date 2024/12/07
 */
public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * LocalDateTime转String
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime,String formart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formart);
        return dateTime.format(formatter);
    }

    /**
     * String转LocalDateTime
     *
     * @param dateStr 日期字符串
     * @return {@link LocalDateTime }
     */
    public static LocalDateTime parseDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DEFAULT_FORMATTER);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date 日期
     * @return {@link LocalDateTime }
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime 日期
     * @return {@link Date }
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间字符串
     *
     * @return {@link String }
     */
    public static String getCurrentDateTimeStr() {
        return formatDateTime(LocalDateTime.now());
    }
}