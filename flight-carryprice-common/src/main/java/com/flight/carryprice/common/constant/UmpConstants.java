package com.flight.carryprice.common.constant;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description UMP监控
 * @date 2019/3/8 11:21
 * @updateTime
 */
public class UmpConstants {

	/********** 系统应用名称 start **************************/
	/**
	 * 运价api接口系统
	 */
	public static final String CARRYPRICE_API_NAME = "carryprice.api";

	/**
	 * 运价api对外接口系统
	 */
	public static final String CARRYPRICE_OUT_API_NAME = "carryprice.out.api";

	/**
	 * 运价web系统
	 */
	public static final String CARRYPRICE_WEB_NAME = "carryprice.web";

	/**
	 * 运价quartz任务系统
	 */
	public static final String CARRYPRICE_QUARTZ_NAME = "carryprice.quartz";

	/********** 系统应用名称  end **************************/




	/********** ump监控方法  start **************************/
	/**
	 * FD运价查询接口
	 */
	public static final String QUERY_FD_PRICE_LIST_API = "queryFdPriceList.api";

	/**
	 * FD查询舱位-运价接口
	 */
	public static final String QUERY_FD_PRICE_BY_SEATS_LIST_API = "queryFdPriceBySeatsList.api";
	/**
	 * NFD运价查询接口
	 */
	public static final String QUERY_NFD_PRICE_LIST_API = "queryNfdPriceList.api";
	/**
	 * 插入计划任务接口
	 */
	public static final String ADD_SCHEDULED_TASK = "addScheduledTask.api";
	/**
	 * 插入运价监控接口
	 */
	public static final String ADD_CARRY_PRICE_MONITOR = "addCarryPriceMonitor.api";

	/**
	 * 自动插入FD运价缓存更新任务
	 */
	public static final String ADD_CARRY_PRICE_TASK = "addCarryPriceTask.quartz";

	/**
	 * 自动刷新FD运价缓存信息
	 */
	public static final String AUTO_FLUSH_FD_TO_REDIS = "autoFlushFdToRedis.quartz";

	/**
	 * 手动刷新FD运价缓存信息
	 */
	public static final String MANUAL_FLUSH_FD_TO_REDIS = "manualFlushFdToRedis.quartz";

	/**
	 * 自动插入FD运价更新策略
	 */
	public static final String AUTO_ADD_FD_UPDATE_POLICY = "autoAddFdUpdatePolicy.quartz";

	/**
	 * FD运价维护更新
	 */
	public static final String UPDATE_FD_CARRY_PRICE_BY_POLICY = "updateFdCarryPriceByPolicy.quartz";

	/**
	 * 查询航司出票类型
	 */
	public static final String QUERY_AIRWAYS_TICKET_TYPE = "queryAirwaysTiketType";

	/**
	 * 查询限制航班
	 */
	public static final String QUERY_LIMIT_FLIGHT = "queryLimitFlight";

	/********** ump监控方法  end **************************/










































	/********** ump监控定时任务start **************************/

	/**
	 * FD运价更新任务
	 */
	public static final String FD_PRICE_UPDATE_JOB_QUARTZ = "fdPriceUpdateJob.quartz";

	/**
	 * 改签成功回调job
	 */
	public static final String CHANGE_TICKET_NOTIFY_JOB_QUARTZ = "changeTicketNotifyJob.quartz";

	/**
	 * 在线退票结果通知job
	 */
	public static final String ONLINE_REFUND_RESULT_NOTIFY_QUARTZ = "onlineRefundResuleNoticeJob.quartz";

	/**
	 * 机票状态同步job
	 */
	public static final String REFUND_STATUS_NOTIFY_QUARTZ = "refundStatusSyncNoticeJob.quartz";

	/**
	 * 下载配送信息到Excel，并发送邮件job
	 * 定时任务，自动配送任务job
	 */
	public static final String DOWNLOAD_DIS_TO_EMAIL_QUARTZ = "downloadDisToEmail.quartz";
	public static final String AUTO_DISTRIBUTION_QUARTZ = "autoDistribution.quartz";

	/**
	 * 改签后的反向下单接口任务job
	 */
	public static final String ENDORSE_BOOKING_QUARTZ = "endorseBooking.quartz";

	/**
	 * 自动录入票号任务job
	 */
	public static final String AUTO_ENTRY_TICKET_QUARTZ = "autoEntryTicketJob.quartz";

	/**
	 * 出票通知job
	 */
	public static final String ISSUE_TICKET_NOTIFY_QUARTZ = "issueTicketNotify.quartz";

	/**
	 * 航司退票审核job
	 */
	public static final String AIRLINE_RETICKET_AUDIT_QUARTZ = "airlineReticketAudit.quartz";
	/**
	 * 航司退票审核job
	 */
	public static final String AIRLINE_RETICKET_REFUNDDEAL_QUARTZ = "airlineReticketReFundDeal.quartz";

	/**
	 * fd 更新策略job
	 */
	public static final String FD_PRICE_UPDATE_STRATEGY_JOB_QUARTZ = "fdPriceUpdateStrategyJob.quartz";

	/**
	 * fd 运价维护job
	 */
	public static final String FD_PRICE_UPDATE_QUARTZ = "fdPriceUpdate.quartz";

	/**
	 * 航变更新job
	 */
	public static final String QT_AIR_CHANGE_QUARTZ = "qtAirChange.quartz";

	/**
	 * 航变通知表
	 */
	public static final String CHANGE_NOTIFY_QUARTZ = "changeNotify.quartz";

	/**
	 * 更新短信（改人工）jbo
	 */
	public static final String INSERT_SMS_NOTIFY_QUARTZ = "insertsmsNotify.quartz";

	/**
	 * nfd 从ftp下载job
	 */
	public static final String NFD_PRICE_FTP_JOB_QUARTZ = "nfdpriceFtpJob.quartz";

	/**
	 * 配送通知job
	 */
	public static final String DISTRIBUTION_NOTIFY_JOB_QUARTZ = "distributionNotifyJob.quartz";

	/**
	 * 改期完成通知job
	 */
	public static final String CHANGE_TICKET_NOTIFY_QUARTZ = "changeTicketNotify.quartz";

	/**
	 * nfd更新job
	 */
	public static final String NFD_RUN_JON_QUARTZ = "nfdRunJob.quartz";

	/**
	 * 行程单退配送费通知job
	 */
	public static final String REFUND_DISPATCH_FEE_NOTIFY_JOB_QUARTZ = "refundDispatchFeeNotifyJob.quartz";

	/**
	 * 扫描NFD PAT自动更新配置表任务job
	 */
	public static final String NFD_PAT_AUTO_UPDATE_JOB_QUARTZ = "nfdPatAutoUpdateJob.quartz";

	/**
	 * 扫描NFD运价更新策略任务
	 */
	public static final String NFD_ADD_UPDATE_POLICY_JOB_QUARTZ = "nfdAddUpdatePolicyJob.quartz";

	/**
	 * NFD定时推送NFDRunAll接口任务
	 */
	public static final String NFD_SCHEDULE_TASK_QUARTZ = "nfdScheduleTask.quartz";

	/**
	 * NFD运价全量更新策略表job
	 */
	public static final String NFD_TOTAL_UPDATE_STRATEGY_JOB_QUARTZ = "nfdTotalUpdateStrategyJob.quartz";

	/**
	 * 屏蔽虚假舱位job
	 */
	public static final String HIDE_MENDACITY_SEAT_JOB_QUARTZ = "hideMendacitySeatJob.quartz";

	/**
	 * 自动取消pnrjob
	 */
	public static final String AUTO_XEPNR_JOB_QUARTZ = "autoXepnrJob.quartz";

	/**
	 * 更新私有运价
	 */
	public static final String UPDATE_SSDCARRYPRICE_JOB_QUARTZ = "updateSsdCarryPriceJob.quartz";

	/**
	 * 退款结果通知job
	 */
	public static final String REFUND_TICKET_NOTICE_QUARTZ = "refundTicketNotice.quartz";

	/**
	 * 在线申请退票商家处理结果通知
	 */
	public static final String ONLINE_REFUND_RESULE_NOTICE_QUARTZ = "onlineRefundResuleNotice.quartz";

	/**
	 * 机票退票状态同步通知job
	 */
	public static final String REFUND_STATUS_SYNC_NOTICE_QUARTZ = "refundStatusSyncNotice.quartz";

	/**
	 * 自动出牌任务
	 */
	public static final String AUTO_TICKET_QUARTZ = "getScheduledAndUpdate.quartz";

	/**
	 * 查询订单详情job
	 */
	public static final String SEARCH_ORDER_DETAIL_QUARTZ = "getScheduledAndUpdateOrder.quartz";

	/**
	 * 发送短信通知job
	 */
	public static final String SENN_SMS_QUARTZ = "sendSms.quartz";

	/**
	 * 基础数据更新job
	 */
	public static final String BASE_DATA_REFUESH_QUARTZ = "refresh.quartz";

	/**************** ump监控接口start *******************/

	/**
	 * 改签成功回调接口
	 */
	public static final String CHANGE_TICKET_NOTIFY_INTERFACE = "changeTicketNotify.interface";

	/**
	 * 在线退票结果通知接口
	 */
	public static final String ONLINE_REFUND_RESULT_NOTIFY_INTERFACE = "onlineRefundResuleNoticeJob.interface";

	/**
	 * BSP在线申请退票接口
	 */
	public static final String ONLINE_APPLY_REFUND_INTERFAC = "applyOnlineRefund.interface";

	/**
	 * 机票状态同步接口
	 */
	public static final String REFUND_STATUS_NOTIFY_INTERFACE = "refundStatusSyncNoticeJob.interface";

	/**
	 * 机票预订下单接口
	 */
	public static final String BOOK_PNR_INTERFACE = "bookPNR.interface";

	/**
	 * 查询订单详情接口
	 */
	public static final String SEARCH_ORDER_DETAIL_INTERFACE = "searchOrderDetail.interface";

	/**
	 * 退票结果通知接口
	 */
	public static final String REFUND_TICKET_APPLY_INTERFACE = "refundTicketApply.interface";

	/**
	 * 行程单退配送费通知接口
	 */
	public static final String REFUND_DISPATCH_FEE_INTERFACE = "refundDispatchFee.interface";

	/**
	 * 发送短信通知接口
	 */
	public static final String SEND_SMS_INTERFACE = "sendSms.interface";

	/**
	 * 配送通知接口
	 */
	public static final String DISPATCH_NOTIFY_INTERFACE = "dispatchNotify.interface";

	/**
	 * 出票通知接口
	 */
	public static final String TICKET_NOTIFY_INTERFACE = "issueTicket.interface";

	/**
	 * 价格校验接口
	 */
	public static final String CHECK_PRICE_INTERFACE = "checkPrice.interface";

	/**
	 * 支付通知接口
	 */
	public static final String PAY_ORDER_INTERFACE = "payOrder.interface";

	/**
	 * 取消PNR接口
	 */
	public static final String CANCEL_PNR_INTERFACE = "cancelPnr.interface";

	/**
	 * 指定航班查询接口
	 */
	public static final String SPECIFIC_FLIGHT_QUERY_INTERFACE = "specificFlightQuery.interface";

	/**
	 * 退改签查询接口
	 */
	public static final String QUERY_PROVISION_INTERFACE = "queryProvision.interface";

	/**
	 * 经停查询
	 */
	public static final String QUERY_STOP_INTERFACE = "queryStop.interface";

	/**
	 * 是否京配
	 */
	public static final String IS_JD_DISTRIBUTION_INTERFACE = "isJdDistribution.interface";

	/**
	 * 航班异步查询
	 */
	public static final String QUERY_FLIGHT_INTERFACE = "queryFlight.interface";

	/**
	 * 航班查询FD运价处理时间
	 */
	public static final String QUERY_FD_PRICE = "queryFlight.fd.price";
	/**
	 * 航班同步查询
	 */
	public static final String QUERY_FLIGHT_INTERFACE_SYN = "queryFlight.interface.syn";

	/**
	 * 获取舱位接口
	 */
	public static final String GET_CABIN_INTERFACE = "getCabin.interface";

	/**
	 * 获取航站楼接口
	 */
	public static final String GET_TERMINAL_INTERFACE = "getTerminal.interface";

	/**
	 * 获取行李额接口
	 */
	public static final String GET_LUGGAGE_INTERFACE = "getLuggage.interface";

	/**
	 * 获取里程积分接口
	 */
	public static final String GET_MILEAGE_INTERFACE = "getMileage.interface";

	/**
	 * 获取舱位接口
	 */
	public static final String GET_CABININFO_INTERFACE = "getCabinInfo.interface";

	/**
	 * 获取航站楼接口
	 */
	public static final String GET_TERMINALINFO_INTERFACE = "getTerminalInfo.interface";

	/**
	 * 获取行李额接口
	 */
	public static final String GET_LUGGAGEINFO_INTERFACE = "getLuggageInfo.interface";

	/**
	 * 获取里程积分接口
	 */
	public static final String GET_MILEAGEINFO_INTERFACE = "getMileageInfo.interface";

	/**
	 * 获取退改签接口
	 */
	public static final String GET_REFUNDCHANGERULEINFO_INTERFACE = "getRefundChangeRuleInfo.interface";

	/******************* 网关系统start ******************************/

	/**
	 * 网关系统nfd运价更新
	 */
	public static final String GWDATA_NFD_RUN_ALL_INTERFACE = "gwdata.nfd.run.all.interface";

	/******************* 网关系统end ******************************/

	/**
	 * 航班查询
	 */
	public static final String QUERY_FLIGHT_FLAGSHIP = "queryFlight.flagship";

	/**
	 * 出保状态查询
	 */
	public static final String QUERY_INSURANCE_FLAGSHIP = "queryInsurance.flagship";

	/**
	 * 经停查询
	 */
	public static final String QUERY_STOP_FLAGSHIP = "queryStop.flagship";

	/**
	 * 退改签查询接口
	 */
	public static final String QUERY_PROVISION_FLAGSHIP = "queryProvision.flagship";

	/**
	 * 新退改签查询接口
	 */
	public static final String QUERY_REFUND_CHANGE_RULE_FLAGSHIP = "queryRefundChangeRule.flagship";

	/**
	 * 指定航班查询接口
	 */
	public static final String SPECIFIC_FLIGHT_QUERY_FLAGSHIP = "specificFlightQuery.flagship";

	/**
	 * 机票预订下单接口
	 */
	public static final String BOOK_PNR_FLAGSHIP = "bookPNR.flagship";

	/**
	 * 价格校验接口
	 */
	public static final String CHECK_PRICE_FLAGSHIP = "checkPrice.flagship";

	/**
	 * 支付通知接口
	 */
	public static final String PAY_ORDER_FLAGSHIP = "payOrder.flagship";

	/**
	 * 查询订单出票状态接口
	 */
	public static final String QUERY_TICKET_STATUS_FLAGSHIP = "queryTicketStatus.flagship";

	/**
	 * 取消PNR接口
	 */
	public static final String CANCEL_PNR_FLAGSHIP = "cancelPnr.flagship";

	/**
	 * 支付消息处理结果
	 */
	public static final String PAY_PROCESS_RESULT_FLAGSHIP = "payProcessResult.flagship";

	/**
	 * 退款结果通知
	 */
	public static final String REFUND_AMOUNT_NOTIFY_FLAGSHIP = "refundAmountNotify.flagship";

	/**
	 * 在线申请退票接口
	 */
	public static final String ONLINE_APPLY_REFUND_FLAGSHIP = "applyOnlineRefund.flagship";

	/**
	 * 退票审核结果接口
	 */
	public static final String REFUND_AUDIT_RESULT_FLAGSHIP = "refundAuditResult.flagship";

	/**
	 * 查询退票明细接口
	 */
	public static final String QUERY_REFUND_TICKET_STATUS_FLAGSHIP = "queryRefundTicketStatus.flagship";

	/**
	 * 航司产品预定销售控制规则
	 */
	public static final String QUERY_SALE_CONTROL_FLAGSHIP = "querySaleControl.flagship";
	
	/**
	 * TTS政策查询
	 */
	public static final String TTS_QUERY_POLICY_API = "tts.query.policy.api";

	/**
	 * TTS政策有效性校验
	 */
	public static final String TTS_POLICY_EFFECT_CHECK_API = "tts.policy.effect.check.api";

	/**
	 * TTS下单
	 */
	public static final String TTS_CREATE_CUSTOM_ORDER_API = "tts.create.custom.order.api";

	/**
	 * TTS下单支付前验价
	 */
	public static final String TTS_CHECK_ORDER_PRICE_API = "tts.check.order.price.api";

	/**
	 * TTS请求出票
	 */
	public static final String TTS_PAY_ORDER_API = "tts.pay.order.api";

	/**
	 * TTS订单详情
	 */
	public static final String TTS_QUERY_TICKET_STATUS_API = "tts.query.ticket.status.api";

	/**
	 * TTS取消订单
	 */
	public static final String TTS_CANCEL_ORDER_API = "tts.cacel.order.api";

	/**
	 * TTS应用名称
	 */
	public static final String TTS_NAME = "tts.api";

	/**
	 * TTS退票信息
	 */
	public static final String TTS_REFUND_TICKET_NAME = "tts.refund.ticked";

	/**
	 * 线上改签申请
	 */
	public static final String APPLY_CHANGE_TICKET = "applyChangeTicket";

	/**
	 * 线上改签航班查询
	 */
	public static final String QUERY_CHANGE_FLIGHT = "queryChangeFlight";

	/**
	 * 查询订单出票状态job
	 */
	public static final String QUERY_TICKET_STATUS_QUARTZ = "queryTicketStatus.quartz";

	/**
	 * TTS退票申请
	 */
	public static final String TTS_APPLY_ONLINE_REFUND_API = "tts.apply.online.refund.api";

	/**
	 * 查询保险产品接口
	 */
	public static final String QUERY_INSURANCE_PRODUCT_FLAGSHIP = "queryInsuranceProduct.flagship";

	/**
	 * 查询行李额产品
	 */
	public static final String QUERY_LUGGALLOW_PRODUCT_FLAGSHIP = "queryLuggallowProduct.flagship";

	/**
	 * 手动定时全量更新运价缓存
	 */
	public static final String MANUAL_CARRY_PRICE_REDIS_QUARTZ = "manualCarryPriceRedis.quartz";

	/**
	 * 定时插入更新运价缓存的任务
	 */
	public static final String INSERT_CARRY_PRICE_TASK_QUARTZ = "insertCarryPriceTask.quartz";

	/**
	 * FD运价比较，数据库数据和缓存不同
	 */
	public static final String FD_PPRICE_DIFFERENCE_IN_REDIS_AND_DATABASE_QUARTZ = "fdPriceDifferenceInRedisAndDatabase.quartz";

	/**
	 * NFD运价比较，数据库数据和缓存不同
	 */
	public static final String NFD_PPRICE_DIFFERENCE_IN_REDIS_AND_DATABASE_QUARTZ = "NFdPriceDifferenceInRedisAndDatabase.quartz";

	/**
	 * 定时更新FD运价缓存
	 */
	public static final String UPDATE_CARRY_PRICE_FD_QUARTZ = "updateCarryPriceFD.quartz";

	/**
	 * 定时更新NFD运价缓存
	 */
	public static final String UPDATE_CARRY_PRICE_NFD_QUARTZ = "updateCarryPriceNFD.quartz";

	/**
	 * 定时更新SSD运价缓存
	 */
	public static final String UPDATE_CARRY_PRICE_SSD_QUARTZ = "updateCarryPriceSSD.quartz";

	/**
	 * 自动上票号Job
	 */
	public static final String AUTO_UPDATE_TICKETNO_JOB_QUARTZ = "autoUpdateTicketNoJob.quartz";

	/**
	 * 航线清除缓存
	 */
	public static final String CLEAN_NO_ROUTING_DAY_JOB_QUARTZ = "cleanNoRoutingDay.quartz";
	
	/**
	 * 自动更新大客户政策缓存
	 */
	public static final String AUTO_UPDATE_BIG_CUSTOMER_POLICY_CACHE_QUARTZ = "autoUpdateBigBustomerPolicyCache.quartz";
	
	/**
	 * 大客户定时推送更新接口任务
	 */
	public static final String AUTO_UPDATE_BIG_CUSTOMER_POLICY_JOB = "autoUpdateBigCustomerPolicy.quartz";
	
	
	/**
	 * 订单同步到IBE+任务
	 */
	public static final String ORDER_SYNCHRO_TO_IBE_JOB = "autoUpdateBigCustomerPolicy.quartz";



	/**
	 * 配送地址更新任务
	 */
	public static final String DISTRIBUTION_ADDRESS_UPDATE_QUARTZ = "distributionAddressUpdate.quartz";

	/**
	 * 订单同步任务
	 */
	public static final String SYNCHRO_ORDER_QUARTZ = "distributionAddressUpdate.quartz";

	/**
	 * 配送状态同步任务
	 */
	public static final String PUSH_DISTRIBUTION_STATE_QUARTZ = "pushDistributionState.quartz";

	/**
	 * 配送行程单配送费同步
	 */
	public static final String DISTRI_BUTION_CHARGE_QUARTZ = "distriButionCharge.quartz";

	/**
	 * 商家配送行程单状态同步
	 */
	public static final String SYNCHRONIZATION_DISTRIBUTION_TRAVEL_QUARTZ = "synchronizationDistributionTravel.quartz";

	public static final String QUERY_FLIGHT_INTERFACE_POLICY = "queryFlight.policyFlagship";


	/**
	 * AV缓存命中率，变价率统计
	 */
	public static final String CACHE_SHOOT_PRICE_STATITICS_JOB = "cacheShootPriceStatiticsJob.quartz";


	/**
	 * AV缓存主动更新缓存
	 */
	public static final String ACTIVE_UPDATE_AVCACHE_JOB = "activeUpdateAvCacheJob.quartz";



	/**
	 * 供货政策有效性校验
	 */
	public static final String GH_POLICY_EFFECT_CHECK_API = "gh.policy.effect.check.api";

	/**
	 * 供货下单
	 */
	public static final String GH_CREATE_CUSTOM_ORDER_API = "gh.create.custom.order.api";

	/**
	 * TTS下单支付前验价
	 */
	public static final String GH_CHECK_ORDER_PRICE_API = "gh.check.order.price.api";

	/**
	 * 供货请求出票
	 */
	public static final String GH_PAY_ORDER_API = "gh.pay.order.api";



	/**
	 * 供货取消订单
	 */
	public static final String GH_CANCEL_ORDER_API = "gh.cacel.order.api";


	/**
	 * 供货退票申请
	 */
	public static final String GH_APPLY_ONLINE_REFUND_API = "gh.apply.online.refund.api";

	/**
	 * 供货配送信息状态同步
	 */
	public static final String GH_SYN_CHRO_DELIVERY_INFO_API = "gh.syn.chro.delivery.info.api";


	/**
	 * 供货出票信息状态同步
	 */
	public static final String GH_SYN_OUTTICKET_INFO_API = "gh.syn.outticket.info.api";


	/**
	 * 供货退票信息状态同步
	 */
	public static final String GH_SYN_RETTICKET_INFO_API = "gh.syn.retticket.info.api";


	/**
	 * 供货商家退票处理结果
	 */
	public static final String GH_REFUND_TICKET_PROCESS_RESULT_API = "gh.refund.ticket.process.result.api";

	/**
	 * 供货商家出票通知
	 */
	public static final String GH_ISSUE_TICKET_API = "gh.issue.ticket.api";

	/**
	 * 供货商家配送结果通知
	 */
	public static final String GH_DISPATCH_NOTIFY_API = "gh.dispatch.notify.api";


	/**
	 * 供货平台ump报警
	 */
	public static final String GH_UMP_WARNING_API = "gh.ump.warning.api";


	/**
	 * 通知供货平台航变通知
	 */
	public static final String GH_NOTIFY_FLIGHT_CHANGE_INFO_API = "gh.notify.flight.change.info.api";

	/**
	 * 通知航变处理结果通知
	 */
	public static final String GH_NOTIFY_FLIGHT_CHANGE_RESULT_API = "gh.notify.flight.change.result.api";

	/**
	 * 供货同步出票信息监控
	 */
	public static final String GH_SYN_OUT_TICKET_QUARTZ = "gh.syn.out.ticket.quartz";

	/**
	 * 供货同步配送信息配送状态监控
	 */
	public static final String GH_SYN_DISTRIBUTION_QUARTZ = "gh.syn.distribution.quartz";

	/**
	 * xePnr worker监控关键词
	 */
	public static final String GH_XE_PNR_QUARTZ = "gh.xe.pnr.quartz";

	/**
	 * 授权pnr authorizePnr worker监控关键词
	 */
	public static final String GH_AUTHORIZE_PNR_QUARTZ = "gh.authorize.pnr.quartz";

}

