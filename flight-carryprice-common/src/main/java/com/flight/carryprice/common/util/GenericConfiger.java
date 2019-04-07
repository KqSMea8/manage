package com.flight.carryprice.common.util;

import com.flight.carryprice.common.enums.PopConfigurationEnum;
import com.jd.pop.configcenter.client.ConfigCenterClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * pop统一配置管理工具
 * <p>
 * <pre>
 * 提供统一配置管理工具的获取不同类型值方法
 * </pre>
 * <p>
 * User : xuyang5 Date : 15-7-7 Time : 上午11:43
 */
public class GenericConfiger {

	private GenericConfiger() {
		super();
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(GenericConfiger.class);

	private static ConfigCenterClient configCenterClient;

	/**
	 * 初始化方法
	 * <p>
	 * <pre>
	 * 初始化配置管理器客户端
	 * </pre>
	 *
	 * @param configCenterClient
	 */
	public static void init(ConfigCenterClient configCenterClient) {
		GenericConfiger.configCenterClient = configCenterClient;
	}

	/**
	 * 获取开关值
	 * <p>
	 * <pre>
	 * 获取开关管理对应的值
	 * </pre>
	 *
	 * @param popConfigEnum 开关key对应的枚举
	 * @return
	 */
	public static boolean getSwitch(PopConfigurationEnum popConfigEnum) {
		String res = configCenterClient.get(popConfigEnum.getCode());
		if (StringUtils.isBlank(res)) {
			LOGGER.info("【POP configcenter】switch CONFIG value could not be found, switch key :"
					+ popConfigEnum.getCode());
			return false;
		}
		return Boolean.valueOf(res);
	}

	/**
	 * 获取配置管理值（通过枚举）
	 *
	 * @param popConfigEnum 配置管理key对应的枚举
	 * @return
	 */
	public static String getConfigContent(PopConfigurationEnum popConfigEnum) {
		return configCenterClient.get(popConfigEnum.getCode());
	}

	/**
	 * 获取字典管理值
	 *
	 * @param popConfigEnum 字典管理key对应的枚举
	 * @return
	 */
	public static String getDictionary(PopConfigurationEnum popConfigEnum) {
		return configCenterClient.get(popConfigEnum.getCode());
	}

}
