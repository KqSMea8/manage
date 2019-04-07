package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpCarryPriceMonitor;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Description 运价监控表
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
public interface JdjpCarryPriceMonitorMapper extends Mapper<JdjpCarryPriceMonitor> {

    /**
     * 插入运价监控信息
     *
     * @param jdjpCarryPriceMonitor
     * @return
     */
    Long insertCarryPriceMonitor(JdjpCarryPriceMonitor jdjpCarryPriceMonitor);
}
