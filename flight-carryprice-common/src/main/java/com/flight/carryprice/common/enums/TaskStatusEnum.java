package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 任务状态
 * @date 2019/3/11 12:09
 * @updateTime
 */
public enum TaskStatusEnum {

    WAITING ("0","待执行"),
    SYNCING ("1","执行中"),
    INTERRUPT("2","中断"),
    OVER ("3","执行完成");

    private String code;
    private String desc;

    TaskStatusEnum(String code, String desc){
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
