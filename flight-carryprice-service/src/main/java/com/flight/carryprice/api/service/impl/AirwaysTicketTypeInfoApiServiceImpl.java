package com.flight.carryprice.api.service.impl;

import com.flight.carryprice.api.service.AirwaysTicketTypeInfoApiService;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.jdmodel.AirWaysTicketType;
import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeRequest;
import com.flight.carryprice.jdmodel.QueryAirWaysTicketTypeResponse;
import com.flight.carryprice.manager.JdjpAirwaysTicketTypeManager;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 航司出票类型API service层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/19
 */
@Service
public class AirwaysTicketTypeInfoApiServiceImpl implements AirwaysTicketTypeInfoApiService {

    private static final Logger LOGGER = Logger.getLogger(AirwaysTicketTypeInfoApiServiceImpl.class);

    @Resource
    private JdjpAirwaysTicketTypeManager jdjpAirwaysTicketTypeManager;

    @Override
    public QueryAirWaysTicketTypeResponse queryAirwaysTicketType(QueryAirWaysTicketTypeRequest request) {

        // 参数校验
        QueryAirWaysTicketTypeResponse response = checkParams(request);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(response.getResponseCode())) {
            return response;
        }
        // 查询航司出票类型
        JdjpAirwaysTicketType ticketType = jdjpAirwaysTicketTypeManager.queryTicketTypeByAirways(request.getAirWays());
        if (null != ticketType) {
            AirWaysTicketType ticketTypeApi = new AirWaysTicketType();
            BeanUtils.copyProperties(ticketType, ticketTypeApi);
            ticketTypeApi.setAirWays(ticketType.getAirways());
            response.setAirWaysTicketType(ticketTypeApi);
        } else {
            LOGGER.info("query airwaysTicketType return null!");
            response.setResponseCode(ResponseCodeEnum.NO_DATA.getCode());
            response.setResponseMessage(ResponseCodeEnum.NO_DATA.getDesc());
        }
        return response;
    }

    /**
     * 入参校验
     */
    private QueryAirWaysTicketTypeResponse checkParams(QueryAirWaysTicketTypeRequest request) {
        QueryAirWaysTicketTypeResponse response = new QueryAirWaysTicketTypeResponse();
        response.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodeEnum.SUCCESS.getDesc());
        try {
            Preconditions.checkNotNull(request, "请求参数为空！request = " + request);

            final String airWays = request.getAirWays();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(airWays) && RegularUtil.regexAirways(airWays),
                    "航司二字码错误！airWays = " + airWays);

            // 完善备用参数校验逻辑
            final String param1 = request.getParam1();
            if (StringUtils.isNotEmpty(param1)) {
                Preconditions.checkArgument(true, "备用参数1错误！param1 = " + param1);
            }
            final String param2 = request.getParam2();
            if (StringUtils.isNotEmpty(param2)) {
                Preconditions.checkArgument(true, "备用参数2错误！param2 = " + param2);
            }
            final String param3 = request.getParam3();
            if (StringUtils.isNotEmpty(param3)) {
                Preconditions.checkArgument(true, "备用参数3错误！param3 = " + param3);
            }
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.PARAM_ERROR.getCode());
            response.setResponseMessage(e.getMessage());
            LOGGER.error("request param check fail！errorMsg = " + e.getMessage());
        }
        return response;
    }

}
