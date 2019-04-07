package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import com.flight.carryprice.manager.JdjpNfdPatAutoUpdateManager;
import com.flight.carryprice.mapper.JdjpNfdPatAutoUpdateMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/4 11:36.
 */
@Service
public class JdjpNfdPatAutoUpdateManagerImpl extends BaseServiceImpl<JdjpNfdPatAutoUpdate> implements JdjpNfdPatAutoUpdateManager {
    @Resource
    private JdjpNfdPatAutoUpdateMapper jdjpNfdPatAutoUpdateMapper;

    @Override
    public List<JdjpNfdPatAutoUpdate> queryByPopularAndAirType(JdjpNfdPatAutoUpdate jdjpNfdPatAutoUpdate) {
        return jdjpNfdPatAutoUpdateMapper.queryByPopularAndAirType(jdjpNfdPatAutoUpdate);
    }

    @Override
    public int updateExecuteQuartzTimeBatch(List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList) {
        return jdjpNfdPatAutoUpdateMapper.updateExecuteQuartzTimeBatch(jdjpNfdPatAutoUpdateList);
    }
}
