package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价更新策略service层
 * @date 2019/2/28 14:15
 * @updateTime
 */
public interface JdjpFdUpdatePolicyService extends BaseService<JdjpFdUpdatePolicy> {

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
    PageInfo<JdjpFdUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpFdUpdatePolicy queryBean);

    /**
     * fn运价更新策略-新增
     * @param jdjpFdUpdatePolicy
     */
    int insertFdUpdatePolicy(JdjpFdUpdatePolicy jdjpFdUpdatePolicy);
}
