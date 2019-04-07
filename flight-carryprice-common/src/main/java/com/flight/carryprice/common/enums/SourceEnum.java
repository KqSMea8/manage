package com.flight.carryprice.common.enums;

/**
 * @Author hYuan 机票供应链研发部
 * @Description 来源枚举类
 * @Date 18:49 2019/3/11
 * @Param
 * @return
 **/
public enum SourceEnum {

    AUTO((byte)0,"自动"),
    MANUAL ((byte)1,"手动");

    private Byte code;
    private String desc;

    SourceEnum(Byte code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
