package com.flight.carryprice.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 正则表达式判断
 * @date 2019-03-08 17:21
 * @updateTime
 * @see
 */
public class RegularUtil {

	// 机场或城市三字码校验
	public static final String REGEX_CITY = "^[A-Za-z]{3}$";
	// 航司二字码校验
	public static final String REGEX_AIRWAYS = "^[A-Za-z]{2}$|^\\d{1}[A-Za-z]{1}$|^[A-Za-z]{1}\\d{1}$";
	// 舱位校验
	public static final String REGEX_SEAT = "^([a-zA-Z]{1}[0-9]?)$";
	// 出发日期校验
	public static final String REGEX_DEP_DATE = "^[2-9]{1}[0-9]{3}-(([0]?[1-9])|([1][0,1,2]))-(([0]?[1-9]{1})|([1,2][0-9]{1})|([3][0,1]))$";
	//数字以逗号分隔格式的验证
	public static final String REGEX_NUMBER_SPLIT = "^[0-9]{1,2}(,[0-9]{1,2}){0,30}$";
	//数字验证
	public static final String REGEX_NUMBER = "^[0-9]*$";


	/**
	 * 校验机场三字码
	 * @param code
	 * @return
	 */
	public static boolean regexCode(String code) {
		Pattern pattern = Pattern.compile(REGEX_CITY);
		Matcher matcher = pattern.matcher(code);
		return matcher.matches();
	}

	/**
	 * 校验航司二字码
	 *
	 * @param airWays
	 * @return
	 */
	public static boolean regexAirways(String airWays) {
		Pattern pattern = Pattern.compile(REGEX_AIRWAYS);
		Matcher matcher = pattern.matcher(airWays);
		return matcher.matches();
	}

    /**
     * 校验舱位
     * @param seat
     * @return
     */
    public static boolean regexSeat(String seat) {
        Pattern pattern = Pattern.compile(REGEX_SEAT);
        Matcher matcher = pattern.matcher(seat);
        return matcher.matches();
    }

	/**
	 * 校验出发日期 yyyy-MM-dd
	 * @param depDate
	 * @return
	 */
	public static boolean regexDepDate(String depDate) {
		Pattern pattern = Pattern.compile(REGEX_DEP_DATE);
		Matcher matcher = pattern.matcher(depDate);
		return matcher.matches();
	}

	/**
	 * 校验数字以逗号分隔格式的验证
	 * @param numberSplit
	 * @return
	 */
	public static boolean regexNumberSplit(String numberSplit) {
		Pattern pattern = Pattern.compile(REGEX_NUMBER_SPLIT);
		Matcher matcher = pattern.matcher(numberSplit);
		return matcher.matches();
	}

	/**
	 * 校验数字
	 * @param number
	 * @return
	 */
	public static boolean regexNumber(String number) {
		Pattern pattern = Pattern.compile(REGEX_NUMBER);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}


}