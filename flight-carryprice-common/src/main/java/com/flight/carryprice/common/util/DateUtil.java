package com.flight.carryprice.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.common.joda.time.LocalDate;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理工具类
 * 
 * @author 肖有标
 *
 */
public class DateUtil {

	private static Logger logger = Logger.getLogger(DateUtil.class);

	private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();

	public static final String DATE_FMT1 = "yyyy-MM-dd";

	public static final String DATE_TIME2 = "HH:mm";

	public static final String DATE_FMT3 = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME4 = "yyyyMMdd";

	public static final String DATE_TIME5 = "yyMMddHHmmssSSS";

	public static final String DATE_TIME6 = "yyyy-MM-dd HH:mm";

	public static final String DATE_TIME7 = "yyyyMMddHH";

	public static final String DATE_TIME8 = "HHmm";

	public static final String DATE_TIME9 = "MM-dd";


	/**
	 * 线程安全的SimpleDateFormat
	 * 
	 * @param formatStr
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String formatStr) {
		SimpleDateFormat sf = tl.get();
		if (sf == null) {
			sf = new SimpleDateFormat(formatStr);
			tl.set(sf);
		}
		return sf;
	}

	/**
	 * 验证日期格式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean validDate(String dateStr, String formatStr) {
		if (StringUtils.isBlank(dateStr)) {
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			format.setLenient(false);
			format.parse(dateStr);
			return true;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	/**
	 * 把时间转换成字符串
	 *
	 * @param date
	 *            时间
	 * @param formatStr
	 *            要转换的格式
	 * @param encode
	 *            显示的语言
	 * @return
	 */
	public static String dateToString(Date date, String formatStr,Locale encode) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr,encode);
		return sdf.format(date);
	}

	/**
	 * 英文字符串时间转时间格式
	 * @param dateStr
	 * @param formatStr
	 * @param encode
	 * @return
	 */
	public static Date stringToDate(String dateStr, String formatStr,Locale encode) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr,encode);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("", e);
			return null;
		}
		return date;
	}

	/**
	 * 把时间转换成字符串
	 * 
	 * @param date
	 *            时间
	 * @param formatStr
	 *            要转换的格式
	 * @return
	 */
	public static String dateToString(Date date, String formatStr) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		sdf.format(date);
		return sdf.format(date);
	}

	/**
	 * 计算2个时间之间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String spacingTime(Date date1, Date date2) {
		return spacingTime(date1.getTime(), date2.getTime());
	}

	/**
	 * 计算2个时间之间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String spacingTime(long date1, long date2) {
		long diff = date1 - date2;
		return spacingTime(diff);
	}

	/**
	 * 计算2个时间之间的间隔
	 * 
	 * @param diff
	 * @return
	 */
	public static String spacingTime(long diff) {
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		long ss = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);

		String str = "";
		// if (diff >= 900000 && diff < 1200000) { // 15分之20分之间显示红色
		// str += "<span style='color:red'>";
		// }
		if (days > 0) {
			str += days + "天";
		}
		if (days > 0 || hours > 0) {
			str += hours + "小时";
		}
		if (days > 0 || hours > 0 || minutes > 0) {
			str += minutes + "分";
		}
		if (days > 0 || hours > 0 || minutes > 0 || ss >= 0) {
			str += ss + "秒";
		}
		// if (diff >= 900000 && diff < 1200000) {
		// str += "</span>";
		// }
		return str;
	}

	public static Date stringToDate(String dateStr, String formatStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("", e);
			return null;
		}
		return date;
	}

	/**
	 * 格式转换
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static String stringToString(String dateStr, String formatStr) {
		return dateToString(stringToDate(dateStr, formatStr), formatStr);
	}

	/**
	 * 格式转换
	 *
	 * @param dateStr
	 * @param orgFormat
	 * @param toFormat
	 * @return
	 */
	public static String stringToString(String dateStr, String orgFormat, String toFormat) {
		return dateToString(stringToDate(dateStr, orgFormat), toFormat);
	}


	public static Date dateAddDays(Date date, int addDays) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, addDays);
		return cal.getTime();
	}

	public static String dateAddDays(String date, int addDays) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date,DATE_TIME6));
		cal.add(Calendar.DAY_OF_YEAR, addDays);
		return dateToString(cal.getTime(), DATE_TIME6);
	}

	public static Date dateAddMinToDate(Date date, int mins) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, mins);
		return cal.getTime();
	}

	public static String dateAddHours(Date date, int addHour) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, addHour);
		return dateToString(cal.getTime(), DATE_FMT3);
	}

	public static String dateAddMin(Date date, int addDays) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, addDays);
		return dateToString(cal.getTime(), DATE_FMT3);
	}

	public static int dateOfDay(Date date) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // 获取东八区时间
		int year = c.get(Calendar.YEAR); // 获取年
		int month = c.get(Calendar.MONTH) + 1; // 获取月份，0表示1月份
		int day = c.get(Calendar.DAY_OF_MONTH); // 获取当前天数
		int first = c.getActualMinimum(c.DAY_OF_MONTH); // 获取本月最小天数
		int last = c.getActualMaximum(c.DAY_OF_MONTH); // 获取本月最大天数
		int time = c.get(Calendar.HOUR_OF_DAY); // 获取当前小时
		int min = c.get(Calendar.MINUTE); // 获取当前分钟
		int xx = c.get(Calendar.SECOND); // 获取当前秒
		return day;
	}

	/**
	 * 计算2个时间相差的小时数，保留2为小数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static BigDecimal diffHour(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		BigDecimal hours = new BigDecimal(diff).divide(new BigDecimal(1000 * 60 * 60), 2, BigDecimal.ROUND_UP);
		// System.err.println(hours.doubleValue());
		return hours;
	}

	/**
	 * 计算2个时间相差的分钟数，保留2为小数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static BigDecimal diffMinutes(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		BigDecimal minutes = new BigDecimal(diff).divide(new BigDecimal(1000 * 60), 2, BigDecimal.ROUND_UP);
		// System.err.println(hours.doubleValue());
		return minutes;
	}

	/**
	 * 获取当前时间间隔多少秒的时间
	 * 
	 * @param second
	 * @return
	 */
	public static Date getCalulationDate(Date date, Integer second) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);// 设置参数时间
		c.add(Calendar.SECOND, second);// 把日期往前推SECOND 秒.整数往后推,负数往前移动
		date = c.getTime();
		return date;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 获取当前日期，不带时分秒
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar c = Calendar.getInstance();
		// 设置当前时刻的时钟为0
		c.set(Calendar.HOUR_OF_DAY, 0);
		// 设置当前时刻的分钟为0
		c.set(Calendar.MINUTE, 0);
		// 设置当前时刻的秒钟为0
		c.set(Calendar.SECOND, 0);
		// 设置当前的毫秒钟为0
		c.set(Calendar.MILLISECOND, 0);

		Date date = c.getTime();
		return date;
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @return
	 */
	public static String getCurrentStrDateTime(String formatStr) {
		Date date = new Date();
		return dateToString(date, formatStr);
	}

	/**
	 * 比较两个日期大小
	 *
	 * @param dt1
	 * @param dt2
	 * @return 1：dt1>dt2
	 */
	public static int compare_date(Date dt1, Date dt2) {
		if(dt1 == null || dt2 == null){
			logger.error("比较日期时间,传入的参数为空了,dt1="+dt1+",dt2="+dt2);
			return 0;
		}
		try {
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			logger.error("比较日期时间大小异常了,dt1="+dt1+",dt2="+dt2+ exception.getMessage(),exception);
		}
		return 0;
	}

	/**
	 * 判断是否是闰年 true:是;false:否
	 * @param year
	 * @return
	 */
	public static boolean is_leapYear(int year) {
		try {
			if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * 比较两个字符串日期大小
	 * @param dstr1
	 * @param dstr2
	 * @param dateFmt
	 * @return
	 */
	public static int compareStrDate(String dstr1, String dstr2, String dateFmt) {
		try {
			Date dt1 = DateUtil.stringToDate(dstr1, dateFmt);
			Date dt2 = DateUtil.stringToDate(dstr2, dateFmt);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将两个时间设置为同一年,对比两个时间的月,日大小,
	 * @param paramDate
	 * @param fdEndDate
	 * @return
	 */
	public static int compare_mon(Date paramDate,Date fdEndDate){
		try {
			String paramMon=dateToString(paramDate,DATE_FMT1);
			DateTimeFormatter formatP= DateTimeFormat.forPattern("yyyy-MM-dd");
			LocalDate ls_p=formatP.parseLocalDate(paramMon);
        	/*取月*/
			int m_p=ls_p.getMonthOfYear();
       		 /*取天*/
			int day_p=ls_p.getDayOfMonth();
			DateFormat df_p = new SimpleDateFormat("MM-dd");
			Date monDay_p= df_p.parse(m_p+"-"+day_p);

			String fdEndMon=dateToString(fdEndDate,DATE_FMT1);
			DateTimeFormatter format_fd= DateTimeFormat.forPattern("yyyy-MM-dd");
			LocalDate ls_fd=format_fd.parseLocalDate(fdEndMon);
        	/*取月*/
			int m_fd=ls_fd.getMonthOfYear();
       		 /*取天*/
			int day_fd=ls_fd.getDayOfMonth();
			DateFormat df1 = new SimpleDateFormat("MM-dd");
			Date dt_fd= df1.parse(m_fd+"-"+day_fd);
			return compare_date(dt_fd,monDay_p);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 对指定时间小时进行
	 * 加减
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHoursToDate(Date date, int hours) {
		if (null == date) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}


	/**
	 * 获取当前日期，不带时分秒
	 *
	 * @return
	 */
	public static int getHourOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 指定日期转换成指定格式的日期
	 *
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Date dateToDate(Date date, String formatStr) {
		if (date == null || StringUtils.isEmpty(formatStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String dateStr = sdf.format(date);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	public static Date dateToDate_Nw(Date date, String formatStr) {
		if (date == null || StringUtils.isEmpty(formatStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String dateStr = sdf.format(date);
		return new Date(dateStr);
	}

	public static String getSysYear() {
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		return year;
	}

	public static void main(String[] args) {
		Date date1 = stringToDate("2016-10-13 17:59:00", DATE_FMT3);
		Date date2 = stringToDate("2016-10-13 18:00:00", DATE_FMT3);
		Integer i =1;
		System.out.println(diffHour(date1, date2).setScale(2, RoundingMode.UP).compareTo(new BigDecimal(i)));
		System.out.println(diffHour(date1, date2).setScale(2, RoundingMode.UP).abs());
		System.out.println(spacingTime(date1, date2));
		System.out.println((date1.getTime() - date2.getTime()) / 60 / 1000);
	}

}

