package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 接口响应码
 * @date 2019/3/8 11:12
 * @updateTime
 */
public enum CodeEnums {

    SUCCESS("0000", "成功"),
    NOT_DATA("0001", "无数据返回"),
    PARAM_EXCEPTION("0009", "参数异常"),
    INTERFACE_EXCEPTION("0010", "接口异常");

    private String code;

    private String description;

    CodeEnums(String code, String description) {
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
