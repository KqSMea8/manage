package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.flight.carryprice.manager.JdjpNfdUpdatePolicyManager;
import com.flight.carryprice.mapper.JdjpNfdUpdatePolicyMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/8 14:22.
 */
@Service
public class JdjpNfdUpdatePolicyManagerImpl extends BaseServiceImpl<JdjpNfdUpdatePolicy> implements JdjpNfdUpdatePolicyManager {
    @Resource
    private JdjpNfdUpdatePolicyMapper jdjpNfdUpdatePolicyMapper;

    @Override
    public List<JdjpNfdUpdatePolicy> queryList(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy) {
        return jdjpNfdUpdatePolicyMapper.queryList(jdjpNfdUpdatePolicy);
    }

    @Override
    public void save(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy) {
        jdjpNfdUpdatePolicyMapper.save(jdjpNfdUpdatePolicy);
    }

    @Override
    public List<JdjpNfdUpdatePolicy> queryNfdUpdatePolicys() {
        return jdjpNfdUpdatePolicyMapper.queryNfdUpdatePolicys();
    }


}
