package com.awifi.np.biz.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 11, 2017 9:01:03 AM
 * 创建作者：亢燕翔
 * 文件名称：DateUtil.java
 * 版本：  v1.0
 * 功能：  日期操作工具类
 * 修改记录：
 */
public final class DateUtil {

    /** */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /** */
    public static final String YYYYMMDD = "yyyyMMdd";
    /** */
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    /** */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /** */
    public static final String YYYY_MM_DD_HH_MM_DASH = "yyyy-MM-dd_HH:mm";
    /** */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /** */
    public static final String YYMMDDHHMMSS = "yyMMddHHmmss";

    /**
     * 根据指定格式转换成日期字符串
     * @param date 日期
     * @param format 表达式
     * @return 格式化后的日期
     */
    public static String formatToString(Date date, String format) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取今天日期yyyy-MM-dd
     * @return 格式化后的日期
     */
    public static String getTodayDate() {
        return formatToString(new Date(), YYYY_MM_DD);
    }
    
    /**
     * @return 日期
     */
    public static Date getTodayDateDate() {
        return parseToDate(formatToString(new Date(), YYYY_MM_DD), YYYY_MM_DD);
    }
    
    /**
     * 获取今天日期yyyyMMddHH
     * @return 格式化后的日期
     */
    public static String getTodayDateHour() {
        return formatToString(new Date(), YYYYMMDDHH);
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     * @return 当前时间
     */
    public static String getNow() {
        return formatToString(new Date(), YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * @return 当前时间
     */
    public static Date getNowDate() {
        return parseToDate(formatToString(new Date(), YYYY_MM_DD_HH_MM_SS), YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * @param s 日期
     * @param format 表达式
     * @return 日期
     */
    public static Date parseToDate(String s, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param s 日期str
     * @return 日期
     */
    public static Date parseToDateTry(String s) {
        Date v = null;
        if (s.length() == DateUtil.YYYY_MM_DD.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYY_MM_DD);
        } else if (s.length() == DateUtil.YYYY_MM_DD_HH_MM_SS.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYY_MM_DD_HH_MM_SS);
        } else if (s.length() == DateUtil.YYYYMMDDHHMMSS.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYYMMDDHHMMSS);
        } else if (s.length() == DateUtil.YYYYMMDD.length()) {
            v = DateUtil.parseToDate(s, DateUtil.YYYYMMDD);
        } else if (s.length() == "yyyy/MM/dd HH:mm".length()) {
            v = DateUtil.parseToDate(s, "yyyy/MM/dd HH:mm");
        } else {
            System.err.println("Unsupported date string format: " + s);
            return v;
        }
        return v;
    }

    /**
     * 格式化毫秒数
     * @param time 毫秒数
     * @return HH:MM:ss
     */
    public static String formatMillisecond(long time) {
        time = time / 1000;
        long hh = time / 60 / 60;
        long mm = time / 60 % 60;
        long ss = time % 60;
        return (hh < 10 ? "0" + hh : hh) + ":" + (mm < 10 ? "0" + mm : mm) + ":" + (ss < 10 ? "0" + ss : ss);
    }

    /**
     * 判断日期格式是否正确
     * @param date 日期
     * @param format 表达式
     * @return true/false
     */
    public static boolean checkDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (Exception e) {
            // 如果不能转换,肯定是错误格式
            return false;
        }
        String s1 = df.format(d);
        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
        // 逻辑上不对.
        return date.equals(s1);
    }

    /**
     * 判断字符串是否是合法日期格式
     * @param format 表达式
     * @return true/false
     */
    public static boolean checkStrDate(String format) {
        try {
            parseToDateTry(format);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * excel日期解析 返回Date
     * @param jxlDate 日期
     * @return 日期
     */
    public static Date convertDate4JXL(Date jxlDate) {
        if (jxlDate == null) {
            return null;
        }
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(gmt);
        String str = dateFormat.format(jxlDate);
        TimeZone local = TimeZone.getDefault();
        dateFormat.setTimeZone(local);
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * excel日期解析 返回String
     * @param jxlDate 日期
     * @return 日期
     */
    public static String strDate4JXL(Date jxlDate) {
        if (jxlDate == null) {
            return StringUtils.EMPTY;
        }
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(gmt);
        return dateFormat.format(jxlDate);
    }

    /**
     * 返回一个月份的最后一天
     * @param time 日期
     * @return 一个月份的最后一天
     */
    public static String getMonthLastDay(String time) {
        int yy = Integer.parseInt(time.substring(0, 4));
        int mm = Integer.parseInt(time.substring(5));

        String endTime = StringUtils.EMPTY;
        boolean r = yy % 4 == 0 && yy % 100 != 0 || yy % 400 == 0;
        if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) {
            endTime = time + "-31";
        } else if (mm != 2) {
            endTime = time + "-30";
        } else {
            if (r) {
                endTime = time + "-29";
            } else {
                endTime = time + "-28";
            }
        }
        return endTime;
    }

    /**
     * 日期增加
     * @param dateStr dateStr
     * @param plus plus
     * @return 日期
     * @author wmfyt325
     * @date 2015年4月30日 下午5:09:23
     */
    public static String dayAdd(String dateStr, int plus) {
        Date date = parseToDate(dateStr, YYYY_MM_DD);
        Date resultDate = new Date(date.getTime() + (long) plus * 24 * 60 * 60 * 1000);
        return formatToString(resultDate, YYYY_MM_DD);
    }

    /**
     * 月份相加
     * @param dateStr dateStr
     * @param plus plus
     * @return 日期
     * @author wmfyt325
     * @date 2015年4月30日 下午5:24:20
     */
    public static String monthAdd(String dateStr, int plus) {
        Date date = DateUtil.parseToDate(dateStr, YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, plus);
        return DateUtil.formatToString(c.getTime(), DateUtil.YYYY_MM_DD);
    }
    
    /**
     * 年份相加
     * @param dateStr dateStr
     * @param plus plus
     * @return 日期
     * @author 范立松
     * @date 2017年05月08日 上午9:33:20
     */
    public static String yearAdd(String dateStr, int plus) {
        Date date = DateUtil.parseToDate(dateStr, YYYY_MM_DD_HH_MM_SS);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 50);
        return DateUtil.formatToString(c.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 日期相减
     * @param startDay startDay
     * @param endDay endDay
     * @return long
     * @author wmfyt325
     * @date 2015年4月30日 下午5:09:34
     */
    public static long daySubDay(String startDay, String endDay) {
        Date startDate = parseToDate(startDay, YYYY_MM_DD);
        Date endDate = parseToDate(endDay, YYYY_MM_DD);
        return (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 月份相减
     * @param startDay startDay
     * @param endDay endDay
     * @return 月份
     * @throws Exception 异常
     * @author wmfyt325
     * @date 2015年4月30日 下午5:17:53
     */
    public static int monthSubMonth(String startDay, String endDay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(endDay));
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);

        c.setTime(sdf.parse(startDay));
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);

        int result;
        if (year1 == year2) {
            result = month1 - month2;
        } else {
            result = 12 * (year1 - year2) + month1 - month2;
        }
        return result;
    }
    
    /**
     * 日期转星期
     * @param day 日期 格式yyyy-MM-dd
     * @return 星期 1 周一
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月27日 上午10:35:58
     */
    public static int dayForWeek(String day) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(day));
        int week = c.get(Calendar.DAY_OF_WEEK);
        if(week == 1){  
            return 7;  
        }else{  
            return week - 1;  
        }  
    }

    /**
     * 转换为 yyyy-MM-dd 格式的日期数据
     * @param time 时间戳
     * @return 1900-01-01
     * @author 许小满
     * @date 2015年6月9日 下午5:21:07
     */
    public static String formatDate(long time) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        return df.format(new Date(time));
    }

    /**
     * 转换为 yyyy-MM-dd HH:mm:ss 格式的日期数据
     * 
     * @param time
     *            时间戳
     * @return 1900-01-01 01:01:01
     * @author 许小满
     * @date 2015年7月27日 下午4:57:45
     */
    public static String formatTimestamp(long time) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return df.format(new Date(time));
    }

    /**
     * 日期 转 时间戳
     * @param date 日期
     * @return  时间戳
     * @throws Exception 异常
     */
    public static long getDateMills(String date) throws Exception {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        Date d = df.parse(date);
        return d.getTime() / 1000;
    }
    
    /**
     * 日期 转 时间戳
     * @param date 日期
     * @return  时间戳
     * @throws Exception 异常
     */
    public static long getWithMiuMills(String date) throws Exception {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        Date d = df.parse(date);
        return d.getTime() / 1000;
    }
    
    /**
     * 日期 转 时间戳
     * @param date 日期
     * @return  时间戳
     * @throws Exception 异常
     */
    public static long getTimestampMills(String date) throws Exception {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date d = df.parse(date);
        return d.getTime() / 1000;
    }

    /**
     * 获取时间差
     * @param starDate 开始日期
     * @param endDate 结束日期
     * @return 时间差
     * @throws Exception 异常
     * @author ZhouYing
     * @date 2015年10月24日 下午5:51:05
     */
    public static String diffTime(String starDate, String endDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date authDate = df.parse(starDate);
        Date offlineDate = df.parse(endDate);
        Long diff = offlineDate.getTime() - authDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        String diffTime = days + "天" + hours + "小时" + minutes + "分";
        return diffTime;
    }
    
    /***
     * 日期差 秒
     * @param starDate 开始时间
     * @param endDate 结束时间
     * @return 日期差
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月11日 上午10:47:35
     */
    public static Long minusDate(String starDate, String endDate) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd = df.parse(starDate);
        Date ed = df.parse(endDate);
        Long interval = ed.getTime() - sd.getTime();
        return interval / 1000;
    }
    
    /***
     * 日期差 秒
     * @param starDate 开始时间
     * @param endDate 结束时间
     * @param pattern 日期格式
     * @return 日期差
     * @throws Exception 异常
     * @author 许尚敏 
     * @date 2017年7月27日 上午3:45:54
     */
    public static Long minusDate(String starDate, String endDate, String pattern) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd = df.parse(starDate);
        Date ed = df.parse(endDate);
        Long interval = ed.getTime() - sd.getTime();
        return interval / 1000;
    }

    /**
     * 将分钟转换为 天时分
     * @param minutes 分
     * @return 时间差
     * @throws Exception
     * @author 许小满
     * @date 2015年11月04日 上午9:42:05
     */
    public static String getTimeByMimute(Integer minutes) {
        if (minutes == null){
            return StringUtils.EMPTY;
        }
        if (minutes <= 0){
            return "0分";
        }
        StringBuffer time = new StringBuffer();
        if (minutes >= 525600) { // 365*24*60
            time.append(minutes / 525600).append("年");
        }
        int leftDay = minutes % 525600;// 一年内剩余的天
        if (minutes >= 1440) { // 24*60
            time.append(leftDay / 1440).append("天");
        }
        int leftMinute = minutes % 1440;// 一天内剩余的分钟
        if (minutes >= 60) {
            int hour = leftMinute / 60;
            time.append(hour).append("时");
        }
        int minute = leftMinute % 60;
        time.append(minute).append("分");
        return time.toString();
    }

    /**
     *将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String strToDateFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }
}