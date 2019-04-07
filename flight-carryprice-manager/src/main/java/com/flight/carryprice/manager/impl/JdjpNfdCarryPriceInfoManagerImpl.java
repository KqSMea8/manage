package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpNfdCarryPriceInfoManager;
import com.flight.carryprice.mapper.JdjpNfdCarryPriceInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/14 10:44.
 */
@Service
public class JdjpNfdCarryPriceInfoManagerImpl implements JdjpNfdCarryPriceInfoManager {

    @Resource
    private JdjpNfdCarryPriceInfoMapper jdjpNfdCarryPriceInfoMapper;

    @Override
    public List<JdjpNfdCarryPriceInfo> queryList(JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo) {
        return jdjpNfdCarryPriceInfoMapper.queryList(jdjpNfdCarryPriceInfo);
    }

    @Override
    public int updateBatch(String operator, Byte state, Integer[] ids) {
        return jdjpNfdCarryPriceInfoMapper.updateBatch(operator, state, ids);
    }

}
