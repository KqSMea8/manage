package com.flight.carryprice.common.enums;


/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description GDS对外提供接口枚举
 * @date 2019-03-23 17:49
 * @updateTime
 * @see
 */
public enum GDSEnums {
	GDS_INTERFACE_AV("AV", "航班查询"),
	GDS_INTERFACE_AVHF("AVHF", "查询航班"),
	GDS_INTERFACE_FF("FF", "经停查询"),
	GDS_INTERFACE_FD("FD", "票价查询"),
	GDS_INTERFACE_NFD("NFD", "特价查询"),
	GDS_INTERFACE_RT("RT", "获取pnr信息"),
	GDS_INTERFACE_RT_CMD("RTCMD", "补充RT信息"),
	GDS_INTERFACE_RTSFC("RTSFC", "生成PNR后穿入子舱位价格"),
	GDS_INTERFACE_PATA("PATA", "获取PNR及价格信息"),
	GDS_INTERFACE_DETRCN("DETRCN", "根据编码获取票号"),
	GDS_INTERFACE_DETRTN("DETRTN", "根据票号查询数据"),
	GDS_INTERFACE_DETRTN_JZ("DETRTN-JZ", "根据票号查询数据(敬众)"),
	GDS_INTERFACE_SS("SS", "下单生成编码"),
	GDS_INTERFACE_XEPNR("XEPNR", "取消整个PNR"),
	GDS_INTERFACE_XEPSG("XEPSG", "取消PNR中个别乘机人"),
	GDS_INTERFACE_ETDZ("ETDZ", "自动出票"),
	GDS_INTERFACE_COVERSEAT("COVERSEAT", "编码补位"),
	GDS_INTERFACE_LINKAUD("LinkAUD", "儿童PNR关联到成人PNR"),
	GDS_INTERFACE_QT("QT", "获取航班动态"),
	GDS_INTERFACE_PAT("PAT", "虚拟订座以获取价格"),
	GDS_INTERFACE_NFDRUN("NFDRun", "自有配置跑nfd数据"),
	GDS_INTERFACE_NFDRUNALL("NFDrunALL", "自用配置跑nfd数据的注册接口"),
	GDS_INTERFACE_TRI("TRI", "查看改签费的注册接口"),
	GDS_INTERFACE_SK("SK", "查询航班周期的注册接口"),
	GDS_INTERFACE_PIDUSED("PIDUSED", "获取PID使用情况接口"),
	GDS_INTERFACE_ADDTKT("ADDTKT", "自动上票号接口"),
	GDS_INTERFACE_TRR("TRR", "自动退票接口"),
	GDS_INTERFACE_SYNCHRO_ORDER("SYNCHORDER", "订单同步"),
	GDS_INTERFACE_AUTH_PNR("AUTHPNR", "授权pnr");

	private String code;

	private String description;

	GDSEnums(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 根据code获取枚举
	 *
	 * @param code
	 * @return
	 */
	public static GDSEnums getGDSEnums(String code) {
		for (GDSEnums bean : GDSEnums.values()) {
			if (code.equals(bean.getCode())) {
				return bean;
			}
		}
		return null;
	}
}
