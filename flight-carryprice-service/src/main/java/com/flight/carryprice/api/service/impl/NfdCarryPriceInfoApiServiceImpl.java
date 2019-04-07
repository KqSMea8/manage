package com.flight.carryprice.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.flight.carryprice.api.service.NfdCarryPriceInfoApiService;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.RegularUtil;
import com.flight.carryprice.constant.ResponseCodeEnum;
import com.flight.carryprice.constant.SourceTypeEnum;
import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import com.flight.carryprice.jdmodel.NfdCarryPriceInfo;
import com.flight.carryprice.jdmodel.QueryNfdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryNfdCarryPriceResponse;
import com.flight.carryprice.manager.JdjpNfdCarryPriceInfoManager;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description NFD运价API  Service层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/11
 */
@Service
public class NfdCarryPriceInfoApiServiceImpl implements NfdCarryPriceInfoApiService {

    private static final Logger LOGGER = Logger.getLogger(NfdCarryPriceInfoApiServiceImpl.class);

    @Resource
    private JdjpNfdCarryPriceInfoManager jdjpNfdCarryPriceInfoManager;

    @Override
    public QueryNfdCarryPriceResponse queryNfdCarryPriceInfoList(QueryNfdCarryPriceRequest request) {

        // 参数校验
        QueryNfdCarryPriceResponse response = checkParams(request);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(response.getResponseCode())) {
            return response;
        }
        // 查询NFD运价数据
        List<JdjpNfdCarryPriceInfo> priceInfos = new ArrayList<>();
        if (SourceTypeEnum.CACHE.getCode() == request.getSourceType()) {
            priceInfos = getNfdCarryPriceFromCache(request);
        } else if (SourceTypeEnum.CACHE_DB.getCode() == request.getSourceType()) {
            priceInfos = getNfdCarryPriceFromCache(request);
            if (CollectionUtils.isEmpty(priceInfos)) {
                priceInfos = getNfdCarryPriceFromDB(request);
                // 增加同步缓存逻辑

            }
        } else if (SourceTypeEnum.DB.getCode() == request.getSourceType()) {
            priceInfos = getNfdCarryPriceFromDB(request);
        }
        // 封装返回的nfd运价信息
        if (CollectionUtils.isNotEmpty(priceInfos)) {
            List<NfdCarryPriceInfo> infos = new ArrayList<>();
            for (JdjpNfdCarryPriceInfo priceInfo : priceInfos) {
                NfdCarryPriceInfo info = new NfdCarryPriceInfo();
                BeanUtils.copyProperties(priceInfo, info);
                infos.add(info);
            }
            response.setNfdPriceInfoList(infos);
        } else {
            LOGGER.info("query NFD carryPrice return null!");
            response.setResponseCode(ResponseCodeEnum.NO_DATA.getCode());
            response.setResponseMessage(ResponseCodeEnum.NO_DATA.getDesc());
        }
        return response;
    }

    /**
     * 入参校验
     */
    private QueryNfdCarryPriceResponse checkParams(QueryNfdCarryPriceRequest request) {
        QueryNfdCarryPriceResponse response = new QueryNfdCarryPriceResponse();
        response.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setResponseMessage(ResponseCodeEnum.SUCCESS.getDesc());
        try {
            Preconditions.checkNotNull(request, "请求参数为空！request = " + request);

            String airWays = request.getAirWays();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(airWays) && RegularUtil.regexAirways(airWays),
                    "航司二字码错误！airWays = " + airWays);

            String depCode = request.getDepCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(depCode) && RegularUtil.regexCode(depCode),
                    "出发机场三字码错误！depCode = " + depCode);

            String arrCode = request.getArrCode();
            Preconditions.checkArgument(
                    StringUtils.isNotEmpty(arrCode) && RegularUtil.regexCode(arrCode),
                    "到达机场三字码错误！arrCode = " + arrCode);

            String depDate = request.getDepDate();
            Preconditions.checkArgument(DateUtil.validDate(depDate, DateUtil.DATE_FMT1),
                    "出发日期格式错误！depDate = " + depDate);

            Integer sourceType = request.getSourceType();
            Preconditions.checkArgument(null != SourceTypeEnum.getEnumByKey(sourceType),
                    "查询运价方式非法！sourceType = " + sourceType);

            String seat = request.getSeat();
            if (StringUtils.isNotEmpty(seat)) {
                Preconditions.checkArgument(RegularUtil.regexSeat(seat),
                        "舱位错误！seat = " + seat);
            }

            String priceLevelSeat = request.getPriceLevelSeat();
            if (StringUtils.isNotEmpty(priceLevelSeat)) {
                Preconditions.checkArgument(RegularUtil.regexSeat(priceLevelSeat),
                        "运价级别对应舱位错误！priceLevelSeat = " + priceLevelSeat);
            }
        } catch (Exception e) {
            LOGGER.error("request param check fail！errorMsg = " + e.getMessage());
            response.setResponseCode(ResponseCodeEnum.PARAM_ERROR.getCode());
            response.setResponseMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 缓存中获取NFD运价数据
     */
    private List<JdjpNfdCarryPriceInfo> getNfdCarryPriceFromCache(QueryNfdCarryPriceRequest request) {
        // 增加缓存获取NFD运价数据逻辑

        return null;
    }

    /**
     * 数据库中获取NFD运价数据
     */
    private List<JdjpNfdCarryPriceInfo> getNfdCarryPriceFromDB(QueryNfdCarryPriceRequest request) {
        // 封装查询请求
        JdjpNfdCarryPriceInfo queryBean = new JdjpNfdCarryPriceInfo();
        BeanUtils.copyProperties(request, queryBean);
        LOGGER.info("DB query NFD carryPrice request:" + JSON.toJSONString(queryBean));
        List<JdjpNfdCarryPriceInfo> priceInfos = jdjpNfdCarryPriceInfoManager.queryList(queryBean);
        return priceInfos;
    }

}
