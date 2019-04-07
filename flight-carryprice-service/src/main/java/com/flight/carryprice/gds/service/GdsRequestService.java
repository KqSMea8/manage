package com.flight.carryprice.gds.service;

import com.flight.carryprice.common.enums.GDSEnums;

/**
 * @author juln 商旅事业部
 * @version 1.0.0
 * @Description 通过JSF请求GDS的服务
 * @date 2018/3/20 17:40
 * @updateTime
 * @see
 */
public interface GdsRequestService {

	/**
	 * JSF请求统一总线方法（不指定数据渠道）
	 *
	 * @param request 请求实体类
	 * @param officeID office号
	 * @param gdsEnums 调用的gds接口
	 * @param respClazz 返回实体类class
	 * @return <T>
	 */
	public <T> T doService(Object request, String officeID, GDSEnums gdsEnums, Class<T> respClazz);

	/**
	 * JSF请求统一总线方法（指定数据渠道）
	 * 
	 * @param request 请求实体类
	 * @param officeID office号
	 * @param gdsEnums 调用的gds接口
	 * @param respClazz 返回实体类class
	 * @param agencyCode 数据来源渠道 vibe、saas
	 * @return <T>
	 */
	public <T> T doService(Object request, String officeID, GDSEnums gdsEnums,  Class<T> respClazz, String agencyCode);

}
