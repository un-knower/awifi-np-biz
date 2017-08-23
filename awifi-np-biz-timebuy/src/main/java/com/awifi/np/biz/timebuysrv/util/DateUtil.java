package com.awifi.np.biz.timebuysrv.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期操作工具类
 * @ClassName DateUtil
 * @CreateDate 2014-3-19
 * @Version V1.0
 */
public final class DateUtil {

	private DateUtil() {
	}

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_DASH = "yyyy-MM-dd_HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 根据指定格式转换成日期字符串
	 * */
	public static String formatToString(Date date, String format) {
		if(date==null){
			return "";
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 获取今天日期yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getTodayDate() {
		return formatToString(new Date(), YYYY_MM_DD);
	}
	
	/**
	 * 
	 * 说明:得到当前日期 年月日 不含时分秒
	 * @return
	 * @author dozen.zhang
	 * @date 2015年6月4日上午11:56:13
	 */
	public static Date getTodayDateDate() {
		return parseToDate(formatToString(new Date(), YYYY_MM_DD), YYYY_MM_DD);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getNow() {
		return formatToString(new Date(), YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * 说明: 得到当前时间 去掉 毫秒
	 * @return
	 */
	public static Date getNowDate() {
		return parseToDate(formatToString(new Date(), YYYY_MM_DD_HH_MM_SS),
				YYYY_MM_DD_HH_MM_SS);
	}
	/**
	 * 
	 * 说明: 字符串转日期
	 * @param s
	 * @param format
	 * @return
	 */
	public static Date parseToDate(String s, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(s);
		} catch (ParseException e) {
			// e.printStackTrace();
			//System.err.println(s + "日期格式不对");
			return null;
		}
	}

	/**
	 * 
	 * 说明:自动匹配日期格式 把字符串转成日期
	 * @param s
	 * @return Date
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
	 * 
	 * @param time
	 *            毫秒数
	 * @return HH:MM:ss
	 */
	public static String formatMillisecond(long time) {
		time = time / 1000;
		long hh = time / 60 / 60;
		long mm = time / 60 % 60;
		long ss = time % 60;
		return (hh < 10 ? "0" + hh : hh) + ":" + (mm < 10 ? "0" + mm : mm)
				+ ":" + (ss < 10 ? "0" + ss : ss);
	}
	public static String toString(Date date,String format){
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			return df.format(date);

		} catch (Exception e) {
			// 如果不能转换,肯定是错误格式
			return null;
		}


	}
	// 判断日期格式是否正确
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

	// 判断字符串是否是合法日期格式
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
	 * */
	public static Date convertDate4JXL(Date jxlDate) {
		if (jxlDate == null) {
			return null;
		}
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
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
	 * */
	public static String strDate4JXL(Date jxlDate) {
		if (jxlDate == null) {
			return "";
		}
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		dateFormat.setTimeZone(gmt);
		return dateFormat.format(jxlDate);
	}
	
	/**
	 * 返回一个月份的最后一天
	 * 
	 * @param time
	 * @return
	 */
	public static String getMonthLastDay(String time) {
		int yy = Integer.parseInt(time.substring(0, 4));
		int mm = Integer.parseInt(time.substring(5));

		String endTime = "";
		boolean r = yy % 4 == 0 && yy % 100 != 0 || yy % 400 == 0;
		if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10
				|| mm == 12) {
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
	
	@SuppressWarnings("unchecked")
	public static <T extends Date> T parse(String dateString,String dateFormat,Class<T> targetResultType) {
		if(StringUtil.isBlank(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			Date t = targetResultType.getConstructor(long.class).newInstance(time);
			return (T)t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:"+dateFormat+" parse datestring:"+dateString;
			throw new IllegalArgumentException(errorInfo,e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"+targetResultType.getName(),e);
		}
	}
	/**
	 * 
	 * 说明:日期增加
	 * @param dateStr
	 * @param plus
	 * @return
	 */
	public static String dayAdd(String dateStr, int plus){
		Date date = parseToDate(dateStr, YYYY_MM_DD);
		Date resultDate = new Date(date.getTime() + plus*24*60*60*1000);
		return formatToString(resultDate, YYYY_MM_DD);
		
//		Calendar calendar = new GregorianCalendar();
//		Date date = parseToDate(dateStr, DateUtil.YYYY_MM_DD);
//        calendar.setTime(date);
//		calendar.add(Calendar.DATE, plus);
//		return formatToString(calendar.getTime(), YYYY_MM_DD);
	}
	
	/**
	 * 
	 * 说明:根据timestamp 精确到分钟的精度
	 * @param datetime
	 * @return void
	 * @author dozen.zhang
	 * @date 2015年7月2日上午10:05:34
	 */
	public static void printTimestampby60(long datetime){
		Date date =new Date();
		date.setTime(datetime*60000);
		System.out.println(DateUtil.formatToString(date, DateUtil.YYYY_MM_DD_HH_MM));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		smdate=sdf.parse(sdf.format(smdate));
		bdate=sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 *字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate,String bdate) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));

	}
	public static String toDateStr(long vlaue){
		Date date =new Date(vlaue);
		return DateUtil.toDateStr(date,"yyyy-MM-dd HH:mm");
	}
	public static String toDateStr(Date date ,String format){
		return DateUtil.formatToString(date,format);

	}
	public static void main(String[] args) {
		System.out.println(DateUtil.toDateStr(1491038114l*1000));
		System.out.println(DateUtil.toDateStr(1490862198l*1000));
		DateUtil.printTimestampby60(24355980);
		System.out.println(24355980/24/60);
		System.out.println(24355980/24/60);
		System.out.println(24355980/24/60);
		System.out.println(1260/60);
		System.out.println(System.currentTimeMillis() );
		TimeZone tz = TimeZone.getTimeZone("GMT-8:00");
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(tz);
		System.out.println(ca.getTimeInMillis() );
		System.out.println(new Date().getTime() );
		System.out.println(DateUtil.formatToString(new Date(1480695398105l),YYYY_MM_DD_HH_MM_SS));
	}

	public final static int DATE_INTERVAL_DAY = 1; // 日
	public final static int DATE_INTERVAL_WEEK = 2; // 周
	public final static int DATE_INTERVAL_MONTH = 3; // 月
	public final static int DATE_INTERVAL_YEAR = 4; // 年
	public final static int DATE_INTERVAL_HOUR = 5; // 小时
	public final static int DATE_INTERVAL_MINUTE = 6; // 分钟
	public final static int DATE_INTERVAL_SECOND = 7; // 秒
	/**
	 * 加时间
	 * @author sunju
	 * @creationDate. 2010-8-27 下午05:28:06
	 * @param interval 增加项，可以是天数、月份、年数、时间、分钟、秒
	 * @param date 时间
	 * @param num 加的数目
	 * @return 时间加后的时间
	 */
	public static Date dateAdd(int interval, Date date, int num) {
		if (date == null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (interval) {
			case DATE_INTERVAL_DAY:
				calendar.add(Calendar.DATE, num);
				break;
			case DATE_INTERVAL_WEEK:
				calendar.add(Calendar.WEEK_OF_MONTH, num);
				break;
			case DATE_INTERVAL_MONTH:
				calendar.add(Calendar.MONTH, num);
				break;
			case DATE_INTERVAL_YEAR:
				calendar.add(Calendar.YEAR, num);
				break;
			case DATE_INTERVAL_HOUR:
				calendar.add(Calendar.HOUR, num);
				break;
			case DATE_INTERVAL_MINUTE:
				calendar.add(Calendar.MINUTE, num);
				break;
			case DATE_INTERVAL_SECOND:
				calendar.add(Calendar.SECOND, num);
				break;
			default:
		}
		return calendar.getTime();
	}


	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}
	/**
	 * 获取当前时间字符串(年月日)
	 * @author sunju
	 * @creationDate. 2011-5-4 下午08:22:34
	 * @return 时间字符串
	 */
	public static String getNowStringDate() {
		return DateUtil.formatToString(new Date(), "yyyy-MM-dd");
	}
	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


}



