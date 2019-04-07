package com.flight.carryprice.common.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class Config {

	private final static Logger LOGGER = Logger.getLogger(Config.class);

	private static Config config = new Config();

	private VariableExpander expander = new VariableExpander(this, "${", "}");

	/**
	 * 单例，不允许实例化
	 */
	private Config() {
	}

	/**
	 * 配置
	 */
	private Properties cofig = new Properties();

	// /**
	// * 系统URL配置
	// */
	// private Properties urls = new Properties();

	static {
		loadConfig();
	}

	private static void loadConfig() {
		InputStream in = null;
		try {
			in = Config.class.getResourceAsStream("/config.properties");
			config.cofig.load(in);

			// in = Config.class.getResourceAsStream("/url.properties");
			// config.urls.load(in);
		} catch (Exception e) {
			LOGGER.error("", e);
		} finally {
			if (in == null) {
				return;
			}
			try {
				in.close();
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
	}

	/**
	 * 获取配置文件的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return config.getConfig(key, ConfigType.config);
	}

	/**
	 * 获取配置文件的值，并转换为int
	 * 
	 * @param key
	 * @return
	 */
	public static Integer getConfig2Int(String key) {
		String s = config.getConfig(key, ConfigType.config);
		if (StringUtils.isBlank(s)) {
			return null;
		}
		return Integer.parseInt(s);
	}

	/**
	 * 获取配置文件的值，并转换为long
	 * 
	 * @param key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static long getConfig2Long(String key, long defaultValue) {
		String s = config.getConfig(key, ConfigType.config);
		if (StringUtils.isBlank(s)) {
			return defaultValue;
		}
		return Long.parseLong(s);
	}

	// public static String getUrlConfig(String key) {
	// return config.getConfig(key, ConfigType.url);
	// }

	public String getConfig(String key, ConfigType type) {
		String preExpansion = null;
		switch (type) {
		case config: {
			preExpansion = config.cofig.getProperty(key);
			if (preExpansion != null) {
				break;
			}
			return null;
		}
		// case url:{
		// preExpansion = config.urls.getProperty(key);
		// if (preExpansion == null) {
		// return "";
		// }
		// }
		}
		return expander.expandVariables(preExpansion, type);
	}

}
