/*
package com.flight.carryprice.api.service.impl;

import com.flight.carryprice.api.service.LimitFlightInfoApiServcie;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.jdmodel.QueryLimitFlightRequest;
import com.flight.carryprice.jdmodel.QueryLimitFlightResponse;
import com.flight.carryprice.service.JdCacheService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;

*/
/**
 * @Description 限制航班查询API service层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/19
 *//*

@Service
public class LimitFlightInfoApiServcieImpl implements LimitFlightInfoApiServcie {

    private static final Logger LOGGER = Logger.getLogger(LimitFlightInfoApiServcieImpl.class);
    */
/**
     * 限制航班缓存key前缀
     *//*

    private static final String LIMITFLIGHTM_REDISKEY_BUSSINESSCODE = "LIMITF_";

    // 缓存服务
    @Resource
    private JdCacheService jdCacheService;

    @Override
    public QueryLimitFlightResponse queryLimitFlight(QueryLimitFlightRequest request) {

        // 入参校验
        QueryLimitFlightResponse response = checkParams(request);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(response.getResponseCode())) {
            return response;
        }
        // 获取缓存的key
        final String key = new StringBuilder(LIMITFLIGHTM_REDISKEY_BUSSINESSCODE)
                .append(request.getDepCode()).append("_").append(request.getArrCode()).toString();
        LOGGER.info("catch query limitFlight key = " + key);
        // 缓存中查询限制航班数据
        final Map<String, String> limitFlightMap = jdCacheService.getValue(key);
        if (!CollectionUtils.isEmpty(limitFlightMap)) {
            response.setDataStr(JacksonUtil.obj2json(limitFlightMap));
        } else {
            response.setResponseCode(ResponseCodeEnum.NO_DATA.getCode());
            response.setResponseMessage(ResponseCodeEnum.NO_DATA.getDesc());
            LOGGER.info("catch query limitFlight return null!");
        }
        return response;
    }

    */
/**
     * 入参校验
     *//*

    private QueryLimitFlightResponse checkParams(QueryLimitFlightRequest request) {
        QueryLimitFlightResponse response = new QueryLimitFlightResponse();
        response.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodeEnum.SUCCESS.getDesc());
        try {
            Preconditions.checkNotNull(request, "请求参数为空！request = " + request);

            final String depCode = request.getDepCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(depCode) && RegularUtil.regexCode(depCode),
                    "出发机场三字码错误！depCode = " + depCode);

            final String arrCode = request.getArrCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(arrCode) && RegularUtil.regexCode(arrCode),
                    "到达机场三字码错误！arrCode = " + arrCode);

            // 完善备用参数校验逻辑
            final String param1 = request.getParam1();
            if (StringUtils.isNotEmpty(param1)) {
                Preconditions.checkArgument(
                        true, "备用参数1错误！param1 = " + param1);
            }
            final String param2 = request.getParam2();
            if (StringUtils.isNotEmpty(param2)) {
                Preconditions.checkArgument(
                        true, "备用参数2错误！param2 = " + param2);
            }
            final String param3 = request.getParam3();
            if (StringUtils.isNotEmpty(param3)) {
                Preconditions.checkArgument(
                        true, "备用参数3错误！param3 = " + param3);
            }
        } catch (Exception e) {
            response.setResponseCode(ResponseCodeEnum.PARAM_ERROR.getCode());
            response.setResponseMessage(e.getMessage());
            LOGGER.error("request param check fail！errorMsg = " + e.getMessage());
        }
        return response;
    }

}
*/
