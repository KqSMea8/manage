package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import com.flight.carryprice.manager.JdjpPatFlightSeatManager;
import com.flight.carryprice.mapper.JdjpPatFlightSeatMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/19 17:02.
 */
@Service
public class JdjpPatFlightSeatManagerImpl implements JdjpPatFlightSeatManager {
    @Resource
    private JdjpPatFlightSeatMapper jdjpPatFlightSeatMapper;
    @Override
    public List<JdjpPatFlightSeatConfig> queryList(JdjpPatFlightSeatConfig queryBean) {
        return jdjpPatFlightSeatMapper.queryList(queryBean);
    }
}
