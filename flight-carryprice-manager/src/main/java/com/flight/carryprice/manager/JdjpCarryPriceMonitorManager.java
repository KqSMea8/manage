package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpCarryPriceMonitor;

/**
 * @Description 运价监控 manager层
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface JdjpCarryPriceMonitorManager {

    /**
     * 插入运价监控信息
     *
     * @param jdjpCarryPriceMonitor
     * @return
     */
    Long insertCarryPriceMonitor(JdjpCarryPriceMonitor jdjpCarryPriceMonitor);
}
