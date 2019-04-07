package com.flight.carryprice.common.enums;

/**
 * @Description JdjpScheduledTask类中taskType字段枚举值
 * @Author: qinhaoran1
 * @Date: 2019/3/21
 */
public enum TaskTypeEnum {

    SHIELDING_INVENTED_CABIN(1, "屏蔽虚假舱位"),
    PUSH_NFDRUNALL(2, "NFDRunALL推送");

    private int code;
    private String desc;

    TaskTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举对象
     */
    public static TaskTypeEnum getTaskTypeEnum(Integer key) {
        for (TaskTypeEnum bean : TaskTypeEnum.values()) {
            if (bean.getCode() == key) {
                return bean;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
