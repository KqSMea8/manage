package com.flight.carryprice.api.service;

import com.flight.carryprice.jdmodel.QueryNfdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryNfdCarryPriceResponse;

/**
 * @Description NFD运价API  Service层
 * @Author: qinhaoran1
 * @Date: 2019/3/11
 */
public interface NfdCarryPriceInfoApiService {

    /**
     * NFD运价信息服务
     *
     * @param request
     * @return
     */
    QueryNfdCarryPriceResponse queryNfdCarryPriceInfoList(QueryNfdCarryPriceRequest request);

}
