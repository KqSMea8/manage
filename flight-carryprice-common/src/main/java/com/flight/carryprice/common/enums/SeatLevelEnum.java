package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 舱位等级枚举类
 * @date 2019/3/11 11:40
 * @updateTime
 */
public enum SeatLevelEnum {
    Y("Y","经济舱"),
    C("C","商务舱"),
    F("F","头等舱");

    private String code;
    private String desc;

    SeatLevelEnum(String code, String desc){
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
