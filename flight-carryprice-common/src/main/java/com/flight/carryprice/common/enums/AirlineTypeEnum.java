package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 航线类型枚举类
 * @date 2019/3/11 11:36
 * @updateTime
 */
public enum AirlineTypeEnum {

    ORDINARY ("0","普通航线"),
    POPULAR ("1","热门航线");

    private String code;
    private String desc;

    AirlineTypeEnum(String code, String desc){
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
