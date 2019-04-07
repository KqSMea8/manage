package com.flight.carryprice.config;

import com.flight.carryprice.common.config.Config;

/**
 * 公共常量参数类
 * 
 * @author wanghx
 * @date (2014-6-26 上午11:00:55)
 */
public class ParamConfig {

	/** 系统应用码 **/
	public static final Integer APP_CODE = 33;
	// /**前台系统应用码*/
	// public static final Integer PROVIDER_APP_CODE = 33;

	/** session中验证码对象的key **/
	public static final String CAPTCHA = "captcha";

	/** session中user对象的key **/
	public static final String SESSION_USER = "sessionUser";

	/** session中menu集合的key **/
	public static final String SESSION_MEMUS = "sessionMenuList";

	/** session中module url集合的key **/
	public static final String SESSION_URLS = "sessionUrlList";

	/** session中业务信息集合的key **/
	public static final String SESSION_BUSINESS = "sessionBusiness";
	/** session中公告弹窗是否显示的key **/
	// public static final String SESSION_NOTICE_DIALOG_FLAG =
	// "sessionNoticeDialogFlag";
	/** session中header中显示已关闭航司的key **/
	// public static final String SESSION_CLOSED_AIRWAYS =
	// "sessionClosedAirways";

	/** cookie中username的key **/
	public static final String COOKIE_USERNAME = "cookieUsername";

	/** cookie中password的key **/
	public static final String COOKIE_PASSWORD = "cookiePassword";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表出票平台
	 */
	public static final String BILLSYS_NAME = "BILLSYS";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表出票支付账户
	 */
	public static final String BILL_ACCOUNT_NAME = "BILL_ACCOUNT";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表出票失败原因
	 */
	public static final String TICKET_FAIL_REASON_NAME = "TICKET_FAIL_REASON";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表转专家库原因
	 */
	public static final String TO_EXPERT_REASON_NAME = "TO_EXPERT_REASON";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表航变处理状态
	 */
	public static final String AIR_CHANGE_RESULT_NAME = "AIR_CHANGE_RESULT";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表配送方式
	 */
	public static final String DISTRIBUTION_WAY_NAME = "DISTRIBUTION_WAY";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表取消退票原因
	 */
	public static final String CANCEL_RETTICKET_REASON_NAME = "CANCEL_RETTICKET_REASON";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表取消改签原因
	 */
	public static final String CANCEL_CHANGE_REASON_NAME = "CANCEL_CHANGE_REASON";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表fd热门运价更新时间
	 */
	public static final String FDHOT_PLAN_QUARTZ_TIME = "FDHOT_PLAN_QUARTZ_TIME";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表fd运价更新时间
	 */
	public static final String NFD_PLAN_QUARTZ_TIME = "NFD_PLAN_QUARTZ_TIME";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表nfd热门运价更新时间
	 */
	public static final String NFDHOT_PLAN_QUARTZ_TIME = "NFDHOT_PLAN_QUARTZ_TIME";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表nfd运价更新时间
	 */
	public static final String FD_PLAN_QUARTZ_TIME = "FD_PLAN_QUARTZ_TIME";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表请求清Q时，传的工作号参数
	 */
	public static final String QT_SI = "QT_SI";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表商家名称
	 */
	public static final String SALESYS_CONFIG = "SALESYS_CONFIG";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表NFD失败后调用参数
	 */
	public static final String NFD_FLIGHT_GODATE = "NFD_FLIGHT_GODATE";

	/**
	 * 配置在ccs_runparamenter表中的参数，代表退改签规则儿童免费航司调用参数
	 */
	public static final String REFUND_CHANGE_FREE = "REFUND_CHANGE_FREE";

	/**
	 * 总控参数CLEAN_NO_ROUTING_DAY的key值
	 */
	public static final String CLEAN_NO_ROUTING_DAY = "CLEAN_NO_ROUTING_DAY";


	/**
	 * 定时任务的quartz操作人
	 */
	public static final String QUARTZ_OPERATOR = Config.getConfig("quartz.operator");

	/**
	 * 敬众的ftp的nfd数据的操作人：jingzhong
	 */
	public static final String FTP_OPERATOR = Config.getConfig("ftp.operator");

	/**
	 * 敬众的ftp的nfd数据的host
	 */
	public static final String FTP_HOSTNAME = Config.getConfig("ftp.hostname");

	/**
	 * 敬众的ftp的nfd数据的端口
	 */
	public static final String FTP_PORT = Config.getConfig("ftp.port");

	/**
	 * 敬众的ftp的nfd数据的ftp用户名
	 */
	public static final String FTP_USERNAME = Config.getConfig("ftp.username");

	/**
	 * 敬众的ftp的nfd数据的ftp密码
	 */
	public static final String FTP_PASSWORD = Config.getConfig("ftp.password");

	/**
	 * 敬众的ftp的nfd数据的ftp的路径
	 */
	public static final String FTP_PATHNAME = Config.getConfig("ftp.pathname");

	/**
	 * 敬众的ftp的nfd数据的ftp的文件名
	 */
	public static final String FTP_FILENAME = Config.getConfig("ftp.filename");

	/**
	 * 敬众的ftp的nfd数据的ftp本地路径
	 */
	public static final String FTP_LOCALPATH = Config.getConfig("ftp.localpath");

	/**
	 * 总控参数OPERATOR_SECURITY的key值
	 */
	public static final String OPERATOR_SECURITY_KEY = Config.getConfig("operator.security.key");




	/**
	 * 供应链商家名称配置
	 */
	public static final String GH_MERCHANT_NAME = "GH_MERCHANT_NAME";

}
