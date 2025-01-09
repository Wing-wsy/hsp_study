package com.yz.common.util;

import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.constant.Times;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * Java8 LocalDate 日期时间 工具类
 */
public class LocalDateUtils {


    /**
     * 【功能描述】获取当前日期和时间字符串.
     * @return String 日期时间字符串，例如 2015-08-11 09:51:53
     */
    public static String getLocalDateTimeStr() {
        return format(LocalDateTime.now(), Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 【功能描述】获取当前日期字符串.
     * @return String 日期字符串，例如2023-12-25
     */
    public static String getLocalDateStr() {
        return format(LocalDate.now(), Times.DATE_PATTERN_YYYY_MM_DD);
    }

    /**
     * 【功能描述】获取当前时间字符串.
     * @return String 时间字符串，例如 09:51:53
     */
    public static String getLocalTimeStr() {
        return format(LocalTime.now(), Times.TIME_PATTERN_HH_MM_SS);
    }

    /**
     * 获取当前星期字符串.
     *
     * @return String 当前星期字符串，例如：周二
     */
    public static String getDayOfWeekShortStr() {
        return format(LocalDate.now(), Basic.E);
    }

    /**
     * 获取当前星期字符串.
     *
     * @return String 当前星期字符串，例如：星期二
     */
    public static String getDayOfWeekLongStr() {
        return format(LocalDate.now(), Basic.strJointByNum(Basic.E, 4));
    }

    /**
     * 获取指定日期是星期几
     *
     * @param localDate 日期
     * @return String 星期几
     */
    public static String getDayOfWeekStr(LocalDate localDate) {
        String[] weekOfDays = {
                Times.MONDAY,
                Times.TUESDAY,
                Times.WEDNESDAY,
                Times.THURSDAY,
                Times.FRIDAY,
                Times.SATURDAY,
                Times.SUNDAY };
        int dayOfWeek = localDate.getDayOfWeek().getValue() - 1;
        return weekOfDays[dayOfWeek];
    }

    /**
     * 格式化日期时间字符串
     *
     * @param temporal 需要转化的日期时间
     * @param pattern  时间格式
     * @return String 日期时间字符串，例如 2015-08-11 09:51:53
     */
    public static String format(TemporalAccessor temporal, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(temporal);
    }

    /**
     * 日期时间字符串转换为日期时间(java.time.LocalDateTime)
     *
     * @param localDateTimeStr 日期时间字符串
     * @param pattern          日期时间格式 例如DATETIME_PATTERN
     * @return LocalDateTime 日期时间
     */
    public static LocalDateTime parseLocalDateTime(String localDateTimeStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(localDateTimeStr, dateTimeFormatter);
    }

    /**
     * 日期字符串转换为日期(java.time.LocalDate)
     *
     * @param localDateStr 日期字符串
     * @param pattern      日期格式 例如DATE_PATTERN
     * @return LocalDate 日期
     */
    public static LocalDate parseLocalDate(String localDateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(localDateStr, dateTimeFormatter);
    }

    /**
     * 获取指定日期时间加上指定数量日期时间单位之后的日期时间.
     *
     * @param localDateTime 日期时间
     * @param num           数量
     * @param chronoUnit    日期时间单位
     * @return LocalDateTime 新的日期时间
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, int num, ChronoUnit chronoUnit) {
        return localDateTime.plus(num, chronoUnit);
    }

    public static LocalDate plus(LocalDate localDate, int num, ChronoUnit chronoUnit) {
        return localDate.plus(num, chronoUnit);
    }

    /**
     * 获取指定日期时间减去指定数量日期时间单位之后的日期时间.
     *
     * @param localDateTime 日期时间
     * @param num           数量
     * @param chronoUnit    日期时间单位
     * @return LocalDateTime 新的日期时间
     */
    public static LocalDateTime minus(LocalDateTime localDateTime, int num, ChronoUnit chronoUnit) {
        return localDateTime.minus(num, chronoUnit);
    }

    /**
     * 根据ChronoUnit计算两个日期时间之间相隔日期时间
     *
     * @param start      开始日期时间
     * @param end        结束日期时间
     * @param chronoUnit 日期时间单位
     * @return long 相隔日期时间
     */
    public static long getChronoUnitBetween(LocalDateTime start, LocalDateTime end, ChronoUnit chronoUnit, boolean needABS) {
        long times = start.until(end, chronoUnit);
//        return Math.abs(times);
        return needABS ? Math.abs(times) : times;
    }

    /**
     * 根据ChronoUnit计算两个日期之间相隔年数或月数或天数
     *
     * @param start      开始日期
     * @param end        结束日期
     * @param chronoUnit 日期时间单位,(ChronoUnit.YEARS,ChronoUnit.MONTHS,ChronoUnit.WEEKS,ChronoUnit.DAYS)
     * @return long 相隔年数或月数或天数
     */
    public static long getChronoUnitBetween(LocalDate start, LocalDate end, ChronoUnit chronoUnit, boolean needABS) {
//        return Math.abs(start.until(end, chronoUnit));
        long days = start.until(end, chronoUnit);
        return needABS ? Math.abs(days) : days;
    }

    /**
     * 获取本年第一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr() {
        return getFirstDayOfYearStr(LocalDateTime.now());
    }

    /**
     * 获取本年最后一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr() {
        return getLastDayOfYearStr(LocalDateTime.now());
    }

    /**
     * 获得今天的日期
     * @return
     */
    public static String getTodayStr() {
        return format(LocalDate.now(), Times.DATE_PATTERN_YYYY_MM_DD);
    }

    public static String getTodayStr(String pattern) {
        return format(LocalDate.now(), pattern);
    }

    /**
     * 获得明天的日期
     * @return
     */
    public static LocalDate getTomorrow() {
        return LocalDate.now().plusDays(1); //获取后一天日期
    }

    /**
     * 获得昨天的日期
     * @return
     */
    public static LocalDate getYesterday() {
        return LocalDate.now().minusDays(1); //获取前一天日期
    }

    /**
     * 根据时间单位来获得未来的日期时间
     * @param times
     * @param chronoUnit
     * @return
     */
    public static LocalDateTime getFuture(LocalDateTime dateTime, long times, ChronoUnit chronoUnit) {
        return dateTime.plus(times, chronoUnit);
    }

    /**
     * 获取指定日期当年第一天的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr(LocalDateTime localDateTime) {
        return getFirstDayOfYearStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当年最后一天的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr(LocalDateTime localDateTime) {
        return getLastDayOfYearStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当年第一天的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfYearStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0), pattern);
    }

    /**
     * 获取指定日期当年最后一天的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfYearStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59), pattern);
    }

    /**
     * 获取本月第一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfMonthStr() {
        return getFirstDayOfMonthStr(LocalDateTime.now());
    }

    /**
     * 获取本月最后一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfMonthStr() {
        return getLastDayOfMonthStr(LocalDateTime.now());
    }

    /**
     * 获取指定日期当月第一天的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getFirstDayOfMonthStr(LocalDateTime localDateTime) {
        return getFirstDayOfMonthStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当月最后一天的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfMonthStr(LocalDateTime localDateTime) {
        return getLastDayOfMonthStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当月第一天的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfMonthStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0), pattern);
    }

    /**
     * 获取指定日期当月最后一天的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfMonthStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59), pattern);
    }

    /**
     * 获取本周第一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfWeekStr() {
        return getFirstDayOfWeekStr(LocalDateTime.now());
    }

    /**
     * 获取本周最后一天的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr() {
        return getLastDayOfWeekStr(LocalDateTime.now());
    }

    /**
     * 获取指定日期当周第一天的日期字符串,这里第一天为周一
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfWeekStr(LocalDateTime localDateTime) {
        return getFirstDayOfWeekStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当周最后一天的日期字符串,这里最后一天为周日
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr(LocalDateTime localDateTime) {
        return getLastDayOfWeekStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期当周第一天的日期字符串,这里第一天为周一,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayOfWeekStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0), pattern);
    }

    /**
     * 获取指定日期当周最后一天的日期字符串,这里最后一天为周日,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getLastDayOfWeekStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59), pattern);
    }

    /**
     * 获取今天开始时间的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getStartTimeOfDayStr() {
        return getStartTimeOfDayStr(LocalDateTime.now());
    }

    /**
     * 获取今天结束时间的日期字符串
     *
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getEndTimeOfDayStr() {
        return getEndTimeOfDayStr(LocalDateTime.now());
    }

    /**
     * 获取指定日期开始时间的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 00:00:00
     */
    public static String getStartTimeOfDayStr(LocalDateTime localDateTime) {
        return getStartTimeOfDayStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期结束时间的日期字符串
     *
     * @param localDateTime 指定日期时间
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getEndTimeOfDayStr(LocalDateTime localDateTime) {
        return getEndTimeOfDayStr(localDateTime, Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期开始时间的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getStartTimeOfDayStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withHour(0).withMinute(0).withSecond(0), pattern);
    }

    /**
     * 获取指定日期结束时间的日期字符串,带日期格式化参数
     *
     * @param localDateTime 指定日期时间
     * @param pattern       日期时间格式
     * @return String 格式：yyyy-MM-dd 23:59:59
     */
    public static String getEndTimeOfDayStr(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime.withHour(23).withMinute(59).withSecond(59), pattern);
    }



    /**
     * 切割日期。按照周期切割成小段日期段。例如： <br>
     *
     * @param startDate 开始日期（yyyy-MM-dd）
     * @param endDate   结束日期（yyyy-MM-dd）
     * @param period    周期（天，周，月，年）
     * @return 切割之后的日期集合
     * <li>startDate="2019-02-28",endDate="2019-03-05",period="day"</li>
     * <li>结果为：[2019-02-28, 2019-03-01, 2019-03-02, 2019-03-03, 2019-03-04, 2019-03-05]</li><br>
     * <li>startDate="2019-02-28",endDate="2019-03-25",period="week"</li>
     * <li>结果为：[2019-02-28,2019-03-06, 2019-03-07,2019-03-13, 2019-03-14,2019-03-20,
     * 2019-03-21,2019-03-25]</li><br>
     * <li>startDate="2019-02-28",endDate="2019-05-25",period="month"</li>
     * <li>结果为：[2019-02-28,2019-02-28, 2019-03-01,2019-03-31, 2019-04-01,2019-04-30,
     * 2019-05-01,2019-05-25]</li><br>
     * <li>startDate="2019-02-28",endDate="2020-05-25",period="year"</li>
     * <li>结果为：[2019-02-28,2019-12-31, 2020-01-01,2020-05-25]</li><br>
     */
    public static List<String> listDateStrs(String startDate, String endDate, String period) {
        List<String> result = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Times.DATE_PATTERN_YYYY_MM_DD);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate tmp = start;
        switch (period) {
            case Times.DAY:
                while (start.isBefore(end) || start.isEqual(end)) {
                    result.add(start.toString());
                    start = start.plusDays(1);
                }
                break;
            case Times.WEEK:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    if (tmp.plusDays(6).isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + tmp.plusDays(6));
                    }
                    tmp = tmp.plusDays(7);
                }
                break;
            case Times.MONTH:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    LocalDate lastDayOfMonth = tmp.with(TemporalAdjusters.lastDayOfMonth());
                    if (lastDayOfMonth.isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + lastDayOfMonth);
                    }
                    tmp = lastDayOfMonth.plusDays(1);
                }
                break;
            case Times.YEAR:
                while (tmp.isBefore(end) || tmp.isEqual(end)) {
                    LocalDate lastDayOfYear = tmp.with(TemporalAdjusters.lastDayOfYear());
                    if (lastDayOfYear.isAfter(end)) {
                        result.add(tmp.toString() + "," + end);
                    } else {
                        result.add(tmp.toString() + "," + lastDayOfYear);
                    }
                    tmp = lastDayOfYear.plusDays(1);
                }
                break;
            default:
                break;
        }
        return result;
    }



    /**
     * 2021-11-11 17:49:57
     * 2021-11-11
     * 17:49:57
     * 星期四
     * 星期四
     * ========
     * 20211111
     * ========
     * 2020-12-13T11:14:12
     * 2020-12-13
     * ========
     * 2021-11-11T20:49:57.533
     * 2021-11-07T17:49:57.533
     * ========
     * 481306
     * 4
     * ========
     * 2021-01-01 00:00:00
     * 2021-01-01 00:00:00
     * 20210101000000
     * 2021-12-31 23:59:59
     * 2021-12-31 23:59:59
     * 20211231235959
     * ========
     * 2021-11-01 00:00:00
     * 2021-12-01 00:00:00
     * 20211201000000
     * 2021-11-30 23:59:59
     * 2021-12-31 23:59:59
     * 20211231235959
     * ========
     * 2021-11-08 00:00:00
     * 2021-12-06 00:00:00
     * 20211206000000
     * 2021-11-14 23:59:59
     * 2021-12-12 23:59:59
     * 20211212235959
     * ========
     * 2021-11-11 00:00:00
     * 2021-12-12 00:00:00
     * 20211212000000
     * 2021-11-11 23:59:59
     * 2021-12-12 23:59:59
     * 20211212235959
     * ========
     * 2019-01-30,2019-12-31
     * 2020-01-01,2020-12-13
     * ========
     * 2019-01-30,2019-01-31
     * 2019-02-01,2019-02-28
     * 2019-03-01,2019-03-31
     * 2019-04-01,2019-04-30
     * 2019-05-01,2019-05-31
     * 2019-06-01,2019-06-30
     * 2019-07-01,2019-07-31
     * 2019-08-01,2019-08-31
     * 2019-09-01,2019-09-30
     * 2019-10-01,2019-10-31
     * 2019-11-01,2019-11-30
     * 2019-12-01,2019-12-31
     * 2020-01-01,2020-01-31
     * 2020-02-01,2020-02-29
     * 2020-03-01,2020-03-31
     * 2020-04-01,2020-04-30
     * 2020-05-01,2020-05-31
     * 2020-06-01,2020-06-30
     * 2020-07-01,2020-07-31
     * 2020-08-01,2020-08-31
     * 2020-09-01,2020-09-30
     * 2020-10-01,2020-10-31
     * 2020-11-01,2020-11-30
     * 2020-12-01,2020-12-13
     * ========
     * 2020-12-01
     * 2020-12-02
     * 2020-12-03
     * 2020-12-04
     * 2020-12-05
     * 2020-12-06
     * 2020-12-07
     * 2020-12-08
     * 2020-12-09
     * 2020-12-10
     * 2020-12-11
     * 2020-12-12
     * 2020-12-13
     *
     */

    /**
     * 格式打印，支持动态传参
     * 例如 2015-08-11 09:51:53 > content
     * @param content
     * @param args
     */
    public static void print(String content, Object... args) {
        content = content.replaceAll("\\{}","%s");
        Formatter formatter = new Formatter();
        String result = formatter.format(content, args).toString();
        System.out.println(LocalDateUtils.getLocalDateTimeStr()
                + Strings.SPACE
                + Strings.GREATER_THAN
                + Strings.SPACE
                + result);
        formatter.close();
    }

    /**
     * 带日期格式打印
     * 例如 2015-08-11 09:51:53 > content
     *
     * @param content 打印内容
     * @return
     */
    public static void printByDatetimePattern(String content) {
        System.out.println(LocalDateUtils.getLocalDateTimeStr()
                + Strings.SPACE
                + Strings.GREATER_THAN
                + Strings.SPACE
                + content);
    }

    public static void main(String[] args) {

//        AtomicInteger atomicInteger = new AtomicInteger(0);
//        int newValue = atomicInteger.incrementAndGet();
//        System.out.println("New Value: " + newValue);
//        System.out.println("当前 Value: " + atomicInteger.get());
//
//        newValue = atomicInteger.decrementAndGet();
//        System.out.println("New Value: " + newValue);
//        System.out.println("当前 Value: " + atomicInteger.get());

//        String s = "你好：{},你叫什么名字：{}".replaceAll(Strings.LEFT_BRACE + Strings.RIGHT_BRACE,"%s");
//        String s = "你好：{},你叫什么名字：{}".replaceAll("\\{}","%s");
//        System.out.println(s);


//        print("你好：{},你叫什么名字：{}{}=={}","小明","小白","小红",9);
//        print("你叫什么名字");

//        LocalDateTime nowTime = LocalDateTime.now();
//
//        System.out.println(getTomorrow().toString());
//        System.out.println(getYesterday().toString());
//        System.out.println(parseLocalDateTime(getTomorrow() + " 03:00:00", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS));
//
//        // 计算凌晨3点到现在的时间
//        LocalDateTime futureTime = LocalDateUtils.parseLocalDateTime(
//                LocalDateUtils.getTomorrow() + " 03:00:00",
//                LocalDateUtils.Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
//        long publishTimes = LocalDateUtils.getChronoUnitBetween(LocalDateTime.now(), futureTime, ChronoUnit.MILLIS, true);
//
//        System.out.println(publishTimes);

//        System.out.println(getChronoUnitBetween(
//                nowTime,
//                parseLocalDateTime("2022-07-19 09:12:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS),
//                ChronoUnit.MILLIS));
//        System.out.println(getChronoUnitBetween(
//                nowTime,
//                parseLocalDateTime("2022-07-19 09:12:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS),
//                ChronoUnit.SECONDS));

//        System.out.println(getChronoUnitBetween(
//                                                LocalDate.now(),
//                                                parseLocalDate("2022-07-19", Times.DATE_PATTERN_YYYY_MM_DD),
//                                                ChronoUnit.DAYS,
//                                        false));

//        System.out.println(getChronoUnitBetween(
//                                                parseLocalDate("2022-07-20", Times.DATE_PATTERN_YYYY_MM_DD),
//                                                LocalDate.now(),
//                                                ChronoUnit.DAYS,
//                                        false));
    }

//    public static void main(String[] args) {
//        System.out.println(getLocalDateTimeStr());
//        System.out.println(getLocalDateStr());
//        System.out.println(getLocalTimeStr());
//        System.out.println(getDayOfWeekStr());
//        System.out.println(getDayOfWeekStr(LocalDate.now()));
//
//        System.out.println("========时间转换为字符串");
//        System.out.println(format(LocalDate.now(), UNSIGNED_DATE_PATTERN));
//
//        System.out.println("========字符串年月日时分秒转换为时间");
//        System.out.println(parseLocalDateTime("2020-12-13 11:14:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS));
//        System.out.println("========字符串年月日转换为时间");
//        System.out.println(parseLocalDate("2020-12-13", Times.DATE_PATTERN_YYYY_MM_DD));
//
//        System.out.println("========对你时间增加一段时间返回时间对象");
//        System.out.println(plus(LocalDateTime.now(), 2, ChronoUnit.HOURS));
//        System.out.println("========对你时间减少一段时间返回时间对象");
//        System.out.println(minus(LocalDateTime.now(), 4, ChronoUnit.DAYS));
//
//        System.out.println("========二个时间的间隔时间（天、秒、周、日等等）");
//        System.out.println(getChronoUnitBetween(LocalDateTime.now(), parseLocalDateTime("2021-11-13 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), ChronoUnit.HALF_DAYS));
//        System.out.println(getChronoUnitBetween(LocalDate.now(), parseLocalDate("2021-11-13", Times.DATE_PATTERN_YYYY_MM_DD), ChronoUnit.WEEKS));
//
//        System.out.println("========选择时间的第一天，（可以设置格式、定制时间）");
//        System.out.println(getFirstDayOfYearStr());
//        System.out.println(getFirstDayOfYearStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getFirstDayOfYearStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========选择时间的最后一天，（可以设置格式、定制时间）");
//        System.out.println(getLastDayOfYearStr());
//        System.out.println(getLastDayOfYearStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getLastDayOfYearStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========获取本月第一天的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getFirstDayOfMonthStr());
//        System.out.println(getFirstDayOfMonthStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getFirstDayOfMonthStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========获取本月最后一天的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getLastDayOfMonthStr());
//        System.out.println(getLastDayOfMonthStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getLastDayOfMonthStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("======== 获取本周第一天的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getFirstDayOfWeekStr());
//        System.out.println(getFirstDayOfWeekStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getFirstDayOfWeekStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("======== 获取本周最后一天的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getLastDayOfWeekStr());
//        System.out.println(getLastDayOfWeekStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getLastDayOfWeekStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========获取今天开始时间的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getStartTimeOfDayStr());
//        System.out.println(getStartTimeOfDayStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getStartTimeOfDayStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========获取今天结束时间的日期字符串（可以设置格式、定制时间）");
//        System.out.println(getEndTimeOfDayStr());
//        System.out.println(getEndTimeOfDayStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS)));
//        System.out.println(getEndTimeOfDayStr(parseLocalDateTime("2021-12-12 12:03:12", Times.DATE_PATTERN_YYYY_MM_DD_HH_MM_SS), UNSIGNED_DATETIME_PATTERN));
//
//        System.out.println("========切割日期。按照周期切割成小段日期段 ");
//        List<String> dateStrs = listDateStrs("2019-01-30", "2020-12-13", YEAR);
//        for (String dateStr : dateStrs) {
//            System.out.println(dateStr);
//        }
//
//        System.out.println("========切割日期。按照周期切割成小段日期段 ");
//        List<String> dateStrs1 = listDateStrs("2019-01-30", "2020-12-13", MONTH);
//        for (String dateStr : dateStrs1) {
//            System.out.println(dateStr);
//        }
//
//        System.out.println("========切割日期。按照周期切割成小段日期段 ");
//        List<String> dateStrs2 = listDateStrs("2020-12-01", "2020-12-13", DAY);
//        for (String dateStr : dateStrs2) {
//            System.out.println(dateStr);
//        }
//    }

}



