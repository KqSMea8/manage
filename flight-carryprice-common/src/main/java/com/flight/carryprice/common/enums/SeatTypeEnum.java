package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 舱位类型枚举类
 * @date 2019/3/11 11:40
 * @updateTime
 */
public enum SeatTypeEnum {
    ECONOMY("1","经济舱"),
    BUSINESS("2","商务舱"),
    FIRSTCABIN("3","头等舱"),
    SUPERECONOMY("4","超级经济舱"),
    SPECIAL ("5","特价舱");

    private String code;
    private String desc;

    SeatTypeEnum(String code, String desc){
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
