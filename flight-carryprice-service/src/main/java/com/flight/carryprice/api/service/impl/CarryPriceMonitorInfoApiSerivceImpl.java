package com.flight.carryprice.api.service.impl;

import com.flight.carryprice.api.service.CarryPriceMonitorInfoApiService;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.entity.JdjpCarryPriceMonitor;
import com.flight.carryprice.jdmodel.AddCarryPriceMonitorRequest;
import com.flight.carryprice.jdmodel.AddCarryPriceMonitorResponse;
import com.flight.carryprice.manager.JdjpCarryPriceMonitorManager;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 运价监控API service层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Service
public class CarryPriceMonitorInfoApiSerivceImpl implements CarryPriceMonitorInfoApiService {

    private static final Logger LOGGER = Logger.getLogger(CarryPriceMonitorInfoApiSerivceImpl.class);

    @Resource
    private JdjpCarryPriceMonitorManager jdjpCarryPriceMonitorManager;

    @Override
    public AddCarryPriceMonitorResponse addCarryPriceMonitor(AddCarryPriceMonitorRequest request) {
        AddCarryPriceMonitorResponse response = checkParams(request);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(response.getResponseCode())) {
            return response;
        }
        JdjpCarryPriceMonitor carryPriceMonitor = new JdjpCarryPriceMonitor();
        // 封装待插入的运价监控信息
        BeanUtils.copyProperties(request, carryPriceMonitor);
        Date depTime = DateUtil.stringToDate(request.getDepTime(), DateUtil.DATE_TIME6);
        carryPriceMonitor.setDepTime(depTime);
        LOGGER.info("insert carryPriceMonitor: " + JacksonUtil.obj2json(carryPriceMonitor));
        // 插入封装好的运价监控信息
        final Long count = jdjpCarryPriceMonitorManager.insertCarryPriceMonitor(carryPriceMonitor);
        if (1 != count) {
            response.setResponseCode(ResponseCodeEnum.EXCEPTION.getCode());
            response.setResponseMessage(ResponseCodeEnum.EXCEPTION.getDesc());
            LOGGER.error("insert carryPriceMonitor fail! carryPriceMonitor: " + JacksonUtil.obj2json(carryPriceMonitor));
        }
        return response;
    }

    /**
     * 请求参数校验
     */
    private AddCarryPriceMonitorResponse checkParams(AddCarryPriceMonitorRequest request) {

        AddCarryPriceMonitorResponse response = new AddCarryPriceMonitorResponse();
        response.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodeEnum.SUCCESS.getDesc());
        try {
            Preconditions.checkNotNull(request, "请求参数为空！request = " + request);

            String orderId = request.getOrderId();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(orderId), "订单号错误！orderId = " + orderId);

            String orderPnr = request.getOrderPnr();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(orderPnr), "PNR编码错误！pnr = " + orderPnr);

            String depCode = request.getDepCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(depCode) && RegularUtil.regexCode(depCode),
                    "出发机场三字码错误！depCode = " + depCode);

            String arrCode = request.getArrCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(arrCode) && RegularUtil.regexCode(arrCode),
                    "到达机场三字码错误！arrCode = " + arrCode);

            String airWays = request.getAirWays();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(airWays) && RegularUtil.regexAirways(airWays),
                    "航司错误！airWays = " + airWays);

            String flightNo = request.getFlightNo();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(flightNo), "航班号错误！flightNo = " + flightNo);

            String seat = request.getSeat();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(seat) && RegularUtil.regexSeat(seat),
                    "舱位错误！seat = " + seat);

            BigDecimal errorCarryPrice = request.getErrorCarryPrice();
            Preconditions.checkArgument(
                    null != errorCarryPrice, "错误运价为空！errorCarryPrice = " + errorCarryPrice);

            BigDecimal realCarryPrice = request.getRealCarryPrice();
            Preconditions.checkArgument(
                    null != realCarryPrice, "真实运价为空！realCarryPrice = " + realCarryPrice);

            Byte operateStatus = request.getOperateStatus();
            Preconditions.checkArgument(
                    null != operateStatus, "操作状态错误！operateStatus = " + operateStatus);

            String depTime = request.getDepTime();
            if (StringUtils.isNotEmpty(depTime)) {
                Preconditions.checkArgument(DateUtil.validDate(depTime, DateUtil.DATE_TIME6),
                        "起飞时间格式错误！depTime = " + depTime);
            }

            String operator = request.getOperator();
            if (StringUtils.isNotEmpty(operator)) {
                Preconditions.checkArgument(true, "操作人错误！operator = " + operator);
            }

            // 备用参数校验逻辑根据实际使用情况完善
            String param1 = request.getParam1();
            if (StringUtils.isNotEmpty(param1)) {
                Preconditions.checkArgument(true, "备用参数错误！param1 = " + param1);
            }
            String param2 = request.getParam2();
            if (StringUtils.isNotEmpty(param2)) {
                Preconditions.checkArgument(true, "备用参数错误！param2 = " + param2);
            }
            String param3 = request.getParam3();
            if (StringUtils.isNotEmpty(param3)) {
                Preconditions.checkArgument(true, "备用参数错误！param3 = " + param3);
            }

        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.PARAM_ERROR.getCode());
            response.setResponseMessage(e.getMessage());
            LOGGER.error("request param check fail! errorMsg = " + e.getMessage());
        }
        return response;
    }
}
