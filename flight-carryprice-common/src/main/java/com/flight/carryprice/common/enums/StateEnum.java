package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 状态枚举类
 * @date 2019/3/11 11:29
 * @updateTime
 */
public enum StateEnum {

    INVALID("0","停用,无效"),
    VALID("1","启用,有效");

    private String code;
    private String desc;

    StateEnum(String code, String desc){
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
