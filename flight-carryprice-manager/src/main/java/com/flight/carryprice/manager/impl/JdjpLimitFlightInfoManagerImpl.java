package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import com.flight.carryprice.manager.JdjpLimitFlightInfoManager;
import com.flight.carryprice.mapper.JdjpLimitFlightInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/21 18:22.
 */
@Service
public class JdjpLimitFlightInfoManagerImpl implements JdjpLimitFlightInfoManager {
    @Resource
    private JdjpLimitFlightInfoMapper jdjpLimitFlightInfoMapper;

    @Override
    public List<JdjpLimitFlightInfo> queryList(JdjpLimitFlightInfo queryBean) {
        return jdjpLimitFlightInfoMapper.queryList(queryBean);
    }

    @Override
    public List<JdjpLimitFlightInfo> queryEffectList(JdjpLimitFlightInfo queryBean) {
        return jdjpLimitFlightInfoMapper.queryEffectList(queryBean);
    }

    @Override
    public List<JdjpLimitFlightInfo> queryFields() {
        return jdjpLimitFlightInfoMapper.queryFields();
    }
}
