package com.flight.carryprice.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Author wanghaiyuan
 * Date 9:54 2019/3/20
 */
public class PatCarryPriceUtil {
    /**
     * @Author hYuan
     * @Description PAT p价失败 进行按 舱位 清除运价得 response message
     * @Date  2019/03/19 17:25
     **/
    public static final String PAT_RESP_MESSAGE_NOPNR = "NO PNR";
    public static final String PAT_RESP_MESSAGE_WITHOUT_SUITABLE_PRICE= "没有符合条件的运价";
    public static final int PAT_AIRLINE_TYPE_ORDINARY = 20;
    /**
     * @Author hYuan
     * @Description 拼接航班舱位配置字符串
     * @Date  2019/03/19 17:25
     * @Param
     * @return 
     **/
    public static String pasAirLines(String airWays, String depCode, String arrCode, String flightNo, String seatNo, String deptDate, String reserveTimeDuration, Integer distance, Byte seatType, BigDecimal discount, String seatLevel) {

        StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(airWays).append(':');
            stringBuffer.append(depCode).append('/').append(arrCode).append(':');
            stringBuffer.append(flightNo).append(':');
            stringBuffer.append(seatNo).append(':');
            stringBuffer.append(deptDate).append(':');
            stringBuffer.append(reserveTimeDuration).append(':');
            stringBuffer.append(distance == null ? "" : distance).append(':');
            stringBuffer.append(seatType == null ? "" : seatType).append(':');
            stringBuffer.append(discount == null ? "" : discount).append(':');
            stringBuffer.append(seatLevel == null ? "" : seatLevel);
        return stringBuffer.toString();
    }
    /**
     * @Author hYuan
     * @Description deptDay 从前端的数组 转为 单个字段
     * @Date  2019/03/19 17:25
     **/
    public static String parseDept(Map<String, Object> map){
        ArrayList dept_days = (ArrayList) map.get("deptDay");
        if (dept_days == null || dept_days.isEmpty()){
            return "";
        }
        String deptDate = "";
        for (int i = 0; i < dept_days.size(); i++) {
            if (!"".equals(dept_days.get(i))) {
                deptDate = deptDate + "T+" + dept_days.get(i) + "~";
            }
        }
        return deptDate.substring(0, deptDate.length() - 1);

    }
    /**
     * @Author hYuan
     * @Description reserveTimeDay 从前端的数组 转为 单个字段
     * @Date  2019/03/19 17:25
     **/
    public static String parseReserve(Map<String, Object> map){
        ArrayList reserveTimeDays = (ArrayList) map.get("reserveTimeDay");
        if (reserveTimeDays == null || reserveTimeDays.isEmpty()){
            return "";
        }
        String reserveTimeDuration = "";
        for (int i = 0; i < reserveTimeDays.size(); i++) {
            if (!"".equals(reserveTimeDays.get(i))) {
                reserveTimeDuration = reserveTimeDuration + reserveTimeDays.get(i) + "~";
            }
        }
        return reserveTimeDuration.substring(0, reserveTimeDuration.length() - 1);
    }
    public static String pasAirLines(String airWays, String depCode, String arrCode, String flightNo, String seatNo, String deptDate, String reserveTimeDuration, Integer distance, String seatType, Float discount, String seatLevel) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(airWays).append(':');
        stringBuffer.append(depCode).append('/').append(arrCode).append(':');
        stringBuffer.append(flightNo).append(':');
        stringBuffer.append(seatNo).append(':');
        stringBuffer.append(deptDate).append(':');
        stringBuffer.append(reserveTimeDuration).append(':');
        stringBuffer.append(distance == null ? "" : distance).append(':');
        stringBuffer.append(seatType == null ? "" : seatType).append(':');
        stringBuffer.append(discount == null ? "" : discount).append(':');
        stringBuffer.append(seatLevel == null ? "" : seatLevel);

        return stringBuffer.toString();
    }
}
