package com.flight.carryprice.gds.service;

import com.jd.gds.dto.*;

import java.math.BigDecimal;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 请求GDS接口的封装类
 * @date 2019/3/21 14:45
 * @updateTime
 * @see
 */
public interface GdsInterfaceService {

	/**
	 * 获取运价FD（ 乘客类型默认0-成人；officeID默认配置PEK898）
	 *
	 * @param depCode 出发机场
	 * @param arrCode 到达机场
	 * @param depDate 起飞时间
	 * @param airways 航司
	 * @return
	 */
	FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways);

	/**
	 * 获取运价FD（officeID默认配置PEK898）
	 *
	 * @param depCode 出发机场
	 * @param arrCode 到达机场
	 * @param depDate 起飞时间
	 * @param airways 航司
	 * @param passType 乘客类型 0--成人; 	1-无人陪伴儿童;可不提供 2-儿童; 3--婴儿;可不提供
	 * @return
	 */
	FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways, String passType);

	/**
	 * 获取运价FD
	 *
	 * @param depCode 出发机场
	 * @param arrCode 到达机场
	 * @param depDate 起飞时间
	 * @param airways 航司
	 * @param passType 乘客类型 0--成人; 	1-无人陪伴儿童;可不提供 2-儿童; 3--婴儿;可不提供
	 * @param officeNo office号
	 * @return
	 */
	FDResponseDTO fdprice(String depCode, String arrCode, String depDate, String airways, String passType, String officeNo);



}
