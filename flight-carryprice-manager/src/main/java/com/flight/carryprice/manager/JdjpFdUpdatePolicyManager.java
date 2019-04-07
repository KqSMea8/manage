package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpFdUpdatePolicy;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价更新策略
 * @date 2019/2/28 13:50
 * @updateTime
 */
public interface JdjpFdUpdatePolicyManager {

    /**
     * 功能描述: 查询单条记录（未同步，并且计划执行时间小于当前时间）
     * @param:
     * @return:
     */
    JdjpFdUpdatePolicy selectFdPolicyOne();

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    List<JdjpFdUpdatePolicy> queryList(JdjpFdUpdatePolicy queryBean);

    /**
     * 批量插入-fn运价更新策略
     *
     * @param list
     */
    int insertBatch(List<JdjpFdUpdatePolicy> list);
}
