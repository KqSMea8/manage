package com.flight.carryprice.common.enums;

/**
 * @author gxd 商旅事业部
 * @version 1.0.0
 * @Description 枚举类型的父类
 * @date 2017年2月24日 下午5:52:15
 * @updateTime
 * @see
 */
public enum PopConfigurationEnum implements BaseEnum<String> {

    AGENCY_CODE_GROUP("agency_code_group", "自营，商家及航司旗舰店agencyCode配置"),
    AVCACHE_QUERY_BEFORE_TIME("AVCACHE_QUERY_BEFORE_TIME", "AV缓存统计变价率/缓存命中率时清除当前时间前n小时明细表记录"),
    GH_AGENCY_CODE("GH_AGENCY_CODE", "供货平台商家agencyCode配置"),
    GH_TASK_WORK_MAX_TIMES("GH_TASK_WORK_MAX_TIMES", "通用worker执行最大次数默认10次"),
    GH_TASK_WORK_LIMIT_COUNT("GH_TASK_WORK_LIMIT_COUNT", "通用worker执行时取出条数"),
    GH_POLICY_CHECK_TEST_ID("GH_POLICY_CHECK_TEST_ID", "测试政策校验的的Id,模拟的时候用线上不需要"),
    GH_INTERFACE_URL("GH_INTERFACE_URL", "与供货交互的供货url地址"),
    GH_PRE_INTERFACE_URL("GH_PRE_INTERFACE_URL", "与供货交互的供货预发url地址"),
    GH_DEFAULT_XEPNR_OFFICENO("GH_DEFAULT_XEPNR_OFFICENO","供货商家授权京东officeNo为空时默认xepnr所传参数"),
    HUB2B_NOTIFY_IS_JSF("HUB2B_NOTIFY_IS_JSF", "预发环境下海航b2b是否jsf接口调用"),
    QUERY_FLIGHT_USE_DB_FLAG("QUERY_FLIGHT_USE_DB_FLAG", "航班查询运价是否使用数据库 0-查询不到缓存运价，不使用数据库 1-查询不到缓存运价，运价使用数据库"),
    QUERY_FLIGHT_NO_USE_DB("0","查询不到缓存运价，不使用数据库"),
    QUERY_FLIGHT_USE_DB("1","查询不到缓存运价，运价使用数据库"),
    PRE_IP_LIST("PRE_IP_LIST","预发机器的IP列表"),
    WORK_OPREATE_USER("WORK_OPREATE_USER","工单允许操作work的人员"),
    MISMAN_PRE_PUBLISH_IP("MISMAN_PRE_PUBLISH_IP", "工单man端预发机器ip"),
    MISMAN_BACK_PUSH_USER("MISMAN_BACK_PUSH_USER","工单中backdoor允许使用推送操作（不通过页面进行操作）的人员");

    private String code;

    private String description;

    PopConfigurationEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getDescription() {
        return description;
    }
}
