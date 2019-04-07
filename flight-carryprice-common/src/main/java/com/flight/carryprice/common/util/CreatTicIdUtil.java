package com.flight.carryprice.common.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 票号生成器
 * 
 * @author qinsg
 * 
 */
public class CreatTicIdUtil {

	public static int no = 1;// 序号

	public static final Object LOCK = new Object();

	private final static Logger LOGGER = Logger.getLogger(CreatTicIdUtil.class);

	/**
	 * 生成单号
	 * 
	 * @param pk
	 *            生成的单号的前缀
	 * @return
	 */
	public static String getPk(String pk) {
		return getPk(pk, "");
	}

	public static String getPk(String pk, String dbk) {
		int current = 10000;
		synchronized (LOCK) {
			if (no > 89999) {
				no = 1;
			}
			current += no++;
		}

		pk += dbk + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		LOGGER.info(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
		return pk + current;
	}

	public static int getRandom(int n) {
		Random r = new Random();
		int num = r.nextInt();
		num = Math.abs(num);
		num = num % n;
		return num;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 8999; i++) {
			System.out.println((i + 1) + "===========" + CreatTicIdUtil.getPk("OR", ""));
			// System.out.println(getRandom(8999));
		}

	}
}
