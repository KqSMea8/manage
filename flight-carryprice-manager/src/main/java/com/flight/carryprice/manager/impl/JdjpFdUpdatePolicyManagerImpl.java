package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.flight.carryprice.manager.JdjpFdUpdatePolicyManager;
import com.flight.carryprice.mapper.JdjpFdUpdatePolicyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价更新策略
 * @date 2019/2/28 13:51
 * @updateTime
 */
@Service
public class JdjpFdUpdatePolicyManagerImpl implements JdjpFdUpdatePolicyManager{

    @Resource
    private JdjpFdUpdatePolicyMapper jdjpFdUpdatePolicyMapper;

    /**
     * 功能描述: 查询单条记录（未同步，并且计划执行时间小于当前时间）
     * @param:
     * @return:
     */
    @Override
    public JdjpFdUpdatePolicy selectFdPolicyOne() {
        return jdjpFdUpdatePolicyMapper.selectFdPolicyOne();
    }

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdUpdatePolicy> queryList(JdjpFdUpdatePolicy queryBean) {
        return jdjpFdUpdatePolicyMapper.queryList(queryBean);
    }

    /**
     * 批量插入-fn运价更新策略，热门需要批量插入
     *
     * @param list
     */
    @Override
    @Transactional
    public int insertBatch(List<JdjpFdUpdatePolicy> list) {
        return jdjpFdUpdatePolicyMapper.insertBatch(list);
    }
}
