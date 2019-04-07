package com.flight.carryprice.common.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自定义日期工具类
 * 
 * @author wanghx 2015年3月13日下午3:07:00
 */
public class CustomDateUtils extends org.apache.commons.lang3.time.DateUtils {

	protected final static Logger LOGGER = Logger.getLogger(CustomDateUtils.class);

	public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String FORMAT_TIME = "HH:mm:ss";

	public static final String FORMAT_SHORT_DATE_TIME = "MM-dd HH:mm";

	public static final String FORMAT_DATE_TIME = FORMAT_DEFAULT;

	public static final String FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";

	public static final String FORMAT_JAPAN = "MM.dd(EEE) HH";

	public static final String FORMAT_CHINESE_NO_SECOND = "yyyy年MM月dd日 HH:mm";

	public static final String FORMAT_CHINESE_NO_SECOND_1 = "yyyy年MM月dd日HH:mm";

	public static final String FORMAT_CHINESE = "yyyy年MM月dd日 HH点mm分";

	public static final int TYPE_HTML_SPACE = 2;

	public static final int TYPE_DECREASE_SYMBOL = 3;

	public static final int TYPE_SPACE = 4;

	public static final int TYPE_NULL = 5;

	public static Map<String, SimpleDateFormat> getFormaters() {
		return formaters;
	}

	private static Map<String, SimpleDateFormat> formaters = new HashMap<String, SimpleDateFormat>();

	static {
		SimpleDateFormat defaultFormater = new SimpleDateFormat(FORMAT_DEFAULT, Locale.CHINA);
		formaters.put(FORMAT_DEFAULT, defaultFormater);
		formaters.put(FORMAT_DATE, new SimpleDateFormat(FORMAT_DATE, Locale.CHINA));
		formaters.put(FORMAT_TIME, new SimpleDateFormat(FORMAT_TIME, Locale.CHINA));
		formaters.put(FORMAT_SHORT_DATE_TIME, new SimpleDateFormat(FORMAT_SHORT_DATE_TIME, Locale.CHINA));
		formaters.put(FORMAT_CHINESE_NO_SECOND, new SimpleDateFormat(FORMAT_CHINESE_NO_SECOND, Locale.CHINA));
		formaters.put(FORMAT_CHINESE, new SimpleDateFormat(FORMAT_CHINESE, Locale.CHINA));
		formaters.put(FORMAT_DATE_TIME, defaultFormater);
		formaters.put(FORMAT_NO_SECOND, new SimpleDateFormat(FORMAT_NO_SECOND, Locale.CHINA));
		formaters.put(FORMAT_JAPAN, new SimpleDateFormat(FORMAT_JAPAN, Locale.JAPAN));
		formaters.put(FORMAT_CHINESE_NO_SECOND_1, new SimpleDateFormat(FORMAT_CHINESE_NO_SECOND_1, Locale.CHINA));

	}

	/**
	 * 使用给定的 pattern 对日期进格式化为字符串
	 * 
	 * @param date
	 *            待格式化的日期
	 * @param pattern
	 *            格式字符串
	 * @return 格式化后的日期字符串
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat dateFormat;
		if (formaters.containsKey(pattern)) {
			dateFormat = formaters.get(pattern);
		} else {
			dateFormat = new SimpleDateFormat(pattern);
		}
		return dateFormat.format(date);
	}

	/**
	 * 以默认日期格式(yyyy-MM-dd HH:mm:ss)对日期进行格式化
	 *
	 * @param date
	 *            待格式化的日期
	 * @return 格式化后的日期字符串
	 */
	public static String format(Date date) {
		return formaters.get(FORMAT_DEFAULT).format(date);
	}

	/**
	 * 格式化日期，
	 *
	 * @param date
	 *            要格式化的日期对象
	 * @param format
	 *            格式
	 * @param type
	 *            如果日期为空，定义返回的类型
	 * @return 返回值，如果date为空，则type定义返回类型，如果格式化失败。则返回空串
	 */
	public static String format(Date date, String format, int type) {
		if (date != null) {
			// ---------------------------------
			// 日期不为空时才格式
			// ---------------------------------
			try {
				// ---------------------------------
				// 调用SimpleDateFormat来格式化
				// ---------------------------------
				return new SimpleDateFormat(format).format(date);
			} catch (Exception e) {
				LOGGER.error("", e);
				// ---------------------------------
				// 格式化失败后，返回一个空串
				// ---------------------------------
				return "";
			}
		} else {
			// ---------------------------------
			// 如果传入日期为空，则根据类型返回结果
			// ---------------------------------
			switch (type) {
			case TYPE_HTML_SPACE: // '\002'
				return "&nbsp;";

			case TYPE_DECREASE_SYMBOL: // '\003'
				return "-";

			case TYPE_SPACE: // '\004'
				return " ";

			case TYPE_NULL:
				return null;

			default:
				// ---------------------------------
				// 默认为空串
				// ---------------------------------
				return "";
			}
		}
	}

	/**
	 * 将给定字符串解析为对应格式的日期,循环尝试使用预定义的日期格式进行解析
	 *
	 * @param str
	 *            待解析的日期字符串
	 * @return 解析成功的日期，解析失败返回null
	 */
	public static Date parse(String str) {
		Date date = null;
		for (String _pattern : formaters.keySet()) {
			if (_pattern.getBytes().length == str.getBytes().length) {
				try {
					date = formaters.get(_pattern).parse(str);
					// 格式化成功则退出
					break;
				} catch (ParseException e) {
					// 格式化失败，继续尝试下一个
					LOGGER.error("尝试将日期:" + str + "以“" + _pattern + "”格式化--失败=.=!", e);
				}
			} else if (_pattern.equals(FORMAT_JAPAN)) {
				try {
					date = formaters.get(_pattern).parse(str);
					// 格式化成功则退出
					break;
				} catch (ParseException e) {
					LOGGER.error("", e);
				}
			}
		}
		return date;
	}

	public static Date parse(String str, String pattern) {
		SimpleDateFormat dateFormat;
		if (formaters.containsKey(pattern)) {
			dateFormat = formaters.get(pattern);
		} else {
			dateFormat = new SimpleDateFormat(pattern);
		}
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			// 是格式失败
			LOGGER.error("尝试将日期:" + str + "以“" + pattern + "”格式化--失败=.=!", e);
		}
		return date;
	}

	/**
	 * date2 是否在date1之后
	 * 
	 * @param date1
	 *            参照日期
	 * @param date2
	 *            比较日期
	 * @return true:是 false:否
	 */
	public static boolean isAfter(Date date1, Date date2) {
		Calendar calendar2 = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		calendar2.setTime(date2);
		calendar1.setTime(date1);
		return calendar2.after(calendar1);
	}

	/**
	 * 当前时间+指定的分钟以后的时间
	 */
	public static Date getTimeByMinis(int minis) {
		Calendar calendar = Calendar.getInstance();
		int min = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, min + minis);
		Date cc = calendar.getTime();
		return cc;
	}

	/**
	 * 
	 * 获得当前时间的毫秒数
	 * 
	 * @return
	 */
	public static Long getMillisecond() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DEFAULT);
		Date beginTime = null;
		try {
			beginTime = sdf.parse(CustomDateUtils.format(new Date(), FORMAT_DEFAULT));

		} catch (ParseException e) {
			LOGGER.error("", e);
		}
		return beginTime.getTime();
	}

	/**
	 * 获得当前时间的毫秒数
	 * 
	 * @param pattern
	 * @return
	 */
	public static Long getMillisecond(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date beginTime = null;
		try {
			beginTime = sdf.parse(CustomDateUtils.format(new Date(), pattern));

		} catch (ParseException e) {
			LOGGER.error("", e);
		}
		return beginTime.getTime();
	}

	/**
	 * 获得指定时间的毫秒数
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Long getMillisecond(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date beginTime = null;
		try {
			beginTime = sdf.parse(date);

		} catch (ParseException e) {
			LOGGER.error("", e);
		}
		return beginTime.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @param locale
	 * @return
	 */
	public static Long getMillisecond(String date, String pattern, Locale locale) {
		SimpleDateFormat sdf = null;
		if (locale == null) {
			sdf = new SimpleDateFormat(pattern, locale);
		} else {
			sdf = new SimpleDateFormat(pattern, locale);
		}
		Date beginTime = null;
		try {
			beginTime = sdf.parse(date);
		} catch (ParseException e) {
			LOGGER.error("", e);
		}
		return beginTime.getTime();
	}

	public static void main(String[] args) {
		String str = "11/Sep/2018:12:00:01 +0800";
		System.out.println(CustomDateUtils.parse(str));
		System.out.println(CustomDateUtils.format(CustomDateUtils.parse(str)));

		// logger.debug("------------------------------------------------------");
		// String sdate1 = DateUtils.format(new Date());
		// logger.debug("sdate1 = " + sdate1);
		// String sdate2 = DateUtils.format(new Date(),DateUtils.FORMAT_DATE);
		// logger.debug("sdate2 = " + sdate2);
		// String sdate3 = DateUtils.format(new
		// Date(),DateUtils.FORMAT_NO_SECOND);
		// logger.debug("sdate3 = " + sdate3);
		// String sdate4 = DateUtils.format(new Date(),DateUtils.FORMAT_JAPAN);
		// logger.debug("sdate4 = " + sdate4);
		// logger.debug("------------------------------------------------------");
		//
		// Date date1 = DateUtils.parse(sdate1);
		// Date date2 = DateUtils.parse(sdate2);
		// Date date3 = DateUtils.parse(sdate3);
		// Date date4 = DateUtils.parse(sdate4);
		// logger.debug(DateUtils.format(date3));
		// logger.debug(DateUtils.format(date4));
		// Date date5 = DateUtils.parse("2008-05-05",DateUtils.FORMAT_DATE);
		// logger.debug(DateUtils.format(date5));
		//
		// logger.debug(DateUtils.isAfter(date5,date1));

	}

	public static String getNowTime() {
		return CustomDateUtils.format(new Date());
	}

}
