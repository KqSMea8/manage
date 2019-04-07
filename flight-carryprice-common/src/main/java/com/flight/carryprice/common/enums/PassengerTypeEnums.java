package com.flight.carryprice.common.enums;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 乘机人类型
 * @date 2019/3/21 11:13
 * @updateTime
 */
public enum PassengerTypeEnums {

    //0--成人; 	1-无人陪伴儿童;可不提供 2-儿童; 3--婴儿;可不提供
    ADULT("0", "成人"),
    CHILD_ADULT("1", "无人陪伴儿童"),
    CHILD("2", "儿童"),
    INFANT("3", "婴儿");

    private String code;

    private String description;

    PassengerTypeEnums(String code, String description) {
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
