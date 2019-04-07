package com.flight.carryprice.common.config;

import IceInternal.Ex;
import com.flight.carryprice.common.constant.GDSConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 
 * @Description (加载配置里的参数)
 * @see
 * @author 余贤辉 商旅事业部
 * @version 1.0.0
 * @date 2016年10月12日 上午11:06:32
 * @updateTime
 */
public class GDSConfig {

	private static final Logger LOGGER = Logger.getLogger(GDSConfig.class);

	/** 调用GDS接口OfficeNo */
	public static String OFFICENO = Config.getConfig(GDSConstants.GDS_OFFICENO);

	/**
	 * 功能描述: 获取配置中officeNo对应的商家
	 * @param:
	 * @return:
	 */
	public static String getGdsSalesysId(String officeNo){
		String salesysId = null;
		try{
			/** 调用GDS的 gds.office.salesysid=PEK898|621906;PEK332|668976   (officeNo|商家ID;officeNo|商家ID) */
			String gdsOfficeSalesysId = Config.getConfig(GDSConstants.GDS_OFFICE_SALESYSID);
			LOGGER.info("获取gdsOfficeSalesysId配置："+gdsOfficeSalesysId);
			String[] gdsOfficeSalesysIds = gdsOfficeSalesysId.split(";");
			for (int i = 0; i < gdsOfficeSalesysIds.length; i++) {
				String[] officeSalesysIds = gdsOfficeSalesysIds[i].split("\\|");
				if(officeSalesysIds[0].equals(officeNo)){
					salesysId = officeSalesysIds[1];
				}
			}
		}catch (Exception e){
			LOGGER.info("获取officeNo对应的商家出错",e);
		}
		return salesysId;
	}


}
