package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import com.flight.carryprice.manager.JdjpPatUpdatePolicyManager;
import com.flight.carryprice.mapper.JdjpPatUpdatePolicyMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/20 18:47.
 */
@Service
public class JdjpPatUpdatePolicyManagerImpl implements JdjpPatUpdatePolicyManager {
    @Resource
    private JdjpPatUpdatePolicyMapper jdjpPatUpdatePolicyMapper;
    @Override
    public List<JdjpPatUpdatePolicy> queryList(JdjpPatUpdatePolicy jdjpPatUpdatePolicy) {
        return jdjpPatUpdatePolicyMapper.queryList(jdjpPatUpdatePolicy);
    }
}
