package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价类型
 * @date 2019/3/21 11:33
 * @updateTime
 */
public enum FDorNFDEnum {

    FD("0", "FD运价"),
    NFD("1", "NFD运价");

    private String code;

    private String description;

    FDorNFDEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
