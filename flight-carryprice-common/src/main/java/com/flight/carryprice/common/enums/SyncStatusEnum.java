package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 同步状态
 * @date 2019/3/11 12:09
 * @updateTime
 */
public enum SyncStatusEnum {

    WAITING ("0","未同步"),
    SYNCING ("1","同步中"),
    OVER ("2","同步完成");

    private String code;
    private String desc;

    SyncStatusEnum(String code, String desc){
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
