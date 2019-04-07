package com.flight.carryprice.common.enums;

/**
 * @Description 实体JdjpScheduledTask中scanStatus字段枚举值
 * @Author: qinhaoran1
 * @Date: 2019/3/25
 */
public enum ScanStatusEnum {
    WAIT_SCAN(0, "待扫描"),
    SCANNING(1, "扫描处理中"),
    SUCCESS(2, "处理成功"),
    FAIL(3, "处理失败");

    private int status;

    private String desc;

    ScanStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
