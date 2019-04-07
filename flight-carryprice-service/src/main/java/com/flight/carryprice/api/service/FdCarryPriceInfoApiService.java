package com.flight.carryprice.api.service;

import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceBySeatsResponse;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceRequest;
import com.flight.carryprice.jdmodel.QueryFdCarryPriceResponse;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价API  Service层
 * @date 2019/3/7 16:00
 * @updateTime
 */
public interface FdCarryPriceInfoApiService {

    /**
     * 功能描述: 查询FD运价信息
     *
     * @param: request FD运价请求实体类
     * @return: FD运价响应实体类
     */
    QueryFdCarryPriceResponse queryFdCarryPriceInfoList(QueryFdCarryPriceRequest request);

    /**
     * 功能描述: 根据舱位集合 查询舱位-运价信息
     *
     * @param: request Fd运价根据舱位列表取运价请求
     * @return: Fd运价根据舱位列表取运价返回
     */
    QueryFdCarryPriceBySeatsResponse queryFdPriceBySeatsList(QueryFdCarryPriceBySeatsRequest request);

}
