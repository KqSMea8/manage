package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 航司类型管理中的出票类型及开关
 * @date 2019/3/25 15:05
 * @updateTime
 */
public enum SwitchEnum {

    //获取开关 1.BSP 2.B2B 3.关闭
    BSP("1","停用,无效"),
    B2B("2","停用,无效"),
    CLOSE("3","启用,有效");

    private String code;
    private String desc;

    SwitchEnum(String code, String desc){
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
