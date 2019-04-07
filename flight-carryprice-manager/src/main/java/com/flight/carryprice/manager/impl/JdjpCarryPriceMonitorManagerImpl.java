package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpCarryPriceMonitor;
import com.flight.carryprice.manager.JdjpCarryPriceMonitorManager;
import com.flight.carryprice.mapper.JdjpCarryPriceMonitorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 运价监控 manager层实现
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Service
public class JdjpCarryPriceMonitorManagerImpl implements JdjpCarryPriceMonitorManager {

    @Resource
    private JdjpCarryPriceMonitorMapper jdjpCarryPriceMonitorMapper;

    @Override
    public Long insertCarryPriceMonitor(JdjpCarryPriceMonitor jdjpCarryPriceMonitor) {
        return jdjpCarryPriceMonitorMapper.insertCarryPriceMonitor(jdjpCarryPriceMonitor);
    }
}
