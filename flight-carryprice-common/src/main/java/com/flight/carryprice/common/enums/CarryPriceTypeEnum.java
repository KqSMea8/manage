package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价类型
 * @date 2019/3/15 10:31
 * @updateTime
 */
public enum CarryPriceTypeEnum {

    FD("0","FD运价"),
    NFD ("1","NFD运价"),
    PAT ("2","PAT运价");

    private String code;
    private String desc;

    CarryPriceTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
