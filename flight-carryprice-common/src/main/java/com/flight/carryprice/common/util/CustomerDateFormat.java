package com.flight.carryprice.common.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义日期转换
 * 
 * @author wanghx 2015年3月13日下午3:03:17
 */
public class CustomerDateFormat extends SimpleDateFormat {
	private static final long serialVersionUID = 5279801482134026788L;

	public CustomerDateFormat() {
		super();
	}

	public CustomerDateFormat(String pattern) {
		super(pattern);
	}

	public CustomerDateFormat(String pattern, Locale locale) {
		super(pattern, locale);
	}

	public CustomerDateFormat(String pattern, DateFormatSymbols formatSymbols) {
		super(pattern, formatSymbols);
	}

	@Override
	public Date parse(String source) throws ParseException {
		return CustomDateUtils.parse(source);
	}
}
