package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlTmp;
import com.flight.carryprice.manager.JdjpAlTmpManager;
import com.flight.carryprice.mapper.AlTmpMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:24.
 */
@Service
public class JdjpAlTmpManagerImpl extends BaseServiceImpl<AlTmp> implements JdjpAlTmpManager {
    @Resource
    private AlTmpMapper alTmpMapper;
    @Override
    public void execSql(String sql) {
        alTmpMapper.execSql(sql);
    }
}
