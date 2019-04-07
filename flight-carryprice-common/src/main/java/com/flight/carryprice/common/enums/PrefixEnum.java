package com.flight.carryprice.common.enums;

/**
 * Author wanghaiyuan
 * Date 2018/11/17 15:31.
 * 可以用来作为 存储一些自定义的 字符串常量 比如前缀等
 */
public enum PrefixEnum implements BaseEnum<String>{
    PREFIX_NFD_CARRYPRICE("air", "nfd运价策略入ES前缀"),
    PREFIX_PAT_CARRYPRICE("pairl", "pat运价策略入ES前缀"),
    LIMITFLIGHTM2REDIS("LIMITF", "限制航班缓存key前缀");
    private String code;
    private String description;

    PrefixEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
