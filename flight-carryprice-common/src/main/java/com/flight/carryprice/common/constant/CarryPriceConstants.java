package com.flight.carryprice.common.constant;

/**
 * @Description 运价相关配置
 * @see
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @date 2019年3月6日 下午11:11:32
 * @updateTime
 */
public class CarryPriceConstants {

	/*************策略更新*************/
	public static final String AIRWAYS = "航司";

	public static final String DEPCODE = "出发";

	public static final String ARRCODE = "到达";

	public static final String SEAT = "舱位";

	public static final String CONNECTPARAM1 = "-";

	public static final String CONNECTPARAM2 = ";";

	public static final String CONNECTPARAM3 = "_";

	/*************运价更新配置  开始***************************************/
	//开始条数
	public static final int NEXTDATA = 0;
	//FD自动每次扫描的条数
	public static final int OPERATENO_AUTO = 50;
	//FD手工每次扫描的条数
	public static final int OPERATENO_MANUAL = 1000;
	//查询基础数据航线，翻页数据每页最大条数500条！
	public static final int PAGE_SIZE_DEFALUT = 500;
	/*************运价更新配置  结束***************************************/


	/******************运价缓存key标识    开始  ****************************/
	public static final String FD_REDIS_KEY_TAIL = "FD";
	public static final String NFD_REDIS_KEY_TAIL = "NFD";
	public static final String SSD_REDIS_KEY_TAIL = "SSD";
	/******************运价缓存key标识    结束  ****************************/

	/****************fd比较缓存和数据库是否正在比较中  开始*******************************/
	public static final String FD_COMPARE_IS_RUNNING_FLAG = "FD_COMPARE_IS_RUNNING_FLAG";
	/****************fd比较缓存和数据库是否正在比较中  结束*******************************/

	/********************FD总控参数配置  开始***********************************************/
	//自动插入FD更新策略 - 代表fd普通更新策略时间
	public static final String FD_PLAN_QUARTZ_TIME = "FD_PLAN_QUARTZ_TIME";

	//自动插入FD更新策略 - 代表fd热门更新策略时间
	public static final String FD_HOT_PLAN_QUARTZ_TIME = "FD_HOT_PLAN_QUARTZ_TIME";

	//fd运价维护更新 - fd运价更新航程类型 往返/单程(1-往返,2-单程)
	public static final String FD_UPDATE_ROUTE_TYPE = "FD_UPDATE_ROUTE_TYPE";

	//fd运价维护更新 - fd运价更新航程类型 往返/单程(1-往返,2-单程) 默认值 1-往返
	public static final String FD_UPDATE_ROUTE_TYPE_DEFAULT = "1";

	//fd运价维护更新 - fd运价更新office号
	public static final String FD_UPDATE_OFFICE_NO = "FD_OFFICE_NO";

	//fd运价维护更新 - fd运价更新office号，默认值 PEK898
	public static final String FD_UPDATE_OFFICE_NO_DEFALUT = "PEK898";

	//fd运价维护更新 - fd运价更新office号
	public static final String FD_UPDATE_DAY = "FD_UPDATE_DATE";

	//fd运价维护更新 - fd运价更新office号,默认值180天
	public static final Integer FD_UPDATE_DAY_DEFALUT = 90;

	//fd运价维护更新 - FD运价更新特殊航线;例如:DR#2.1-2.28;7.1-8.31,W01-D01
	public static final String FD_UPDATE_SPECIAL_AIRLINE = "FD_UPDATE_SPECIAL_AIRLINE";


	/********************FD总控参数配置  开始***********************************************/

	/********************系统操作人 开始***********************************************/
	//系统操作人
	public static final String QUARTZ_OPERATOR_SYSTEM= "SYSTEM";
	//敬众操作人
	public static final String QUARTZ_OPERATOR_JINGZHONG = "JINGZHONG";
	/********************系统操作人 开始***********************************************/


	public static final String CARRY_PRICE_SEAT_Y = "Y";

	/******更新FD运价涉及到的闰年**************/
	public static final String IS_LEAP_YEAR = "2-28";
}

