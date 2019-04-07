package com.flight.carryprice.api.service;

import com.flight.carryprice.jdmodel.AddCarryPriceMonitorRequest;
import com.flight.carryprice.jdmodel.AddCarryPriceMonitorResponse;

/**
 * @Description 运价监控API service层
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface CarryPriceMonitorInfoApiService {

    /**
     * 插入运价监控
     *
     * @param request 插入运价监控请求实体
     * @return 插入运价监控响应实体
     */
    AddCarryPriceMonitorResponse addCarryPriceMonitor(AddCarryPriceMonitorRequest request);
}
