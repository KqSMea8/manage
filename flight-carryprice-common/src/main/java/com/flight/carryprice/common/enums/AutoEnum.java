package com.flight.carryprice.common.enums;

/**
 * @Author wangyanwei 机票供应链研发部
 * @Description 自动或手动 枚举类
 * @Date 18:49 2019/3/11
 * @Param
 * @return
 **/
public enum AutoEnum {

    AUTO("0","自动"),
    MANUAL ("1","手动");

    private String code;
    private String desc;

    AutoEnum(String code, String desc){
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
