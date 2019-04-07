package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/8 14:21.
 */
public interface JdjpNfdUpdatePolicyManager extends BaseService<JdjpNfdUpdatePolicy> {
    /**
     * 查询nfd运价更新策略列表
     *
     * @param jdjpNfdUpdatePolicy
     * @return
     */
    List<JdjpNfdUpdatePolicy> queryList(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy);


    /**
     * 保存nfd运价更新策略
     *
     * @param jdjpNfdUpdatePolicy
     * @return
     */
    void save(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy);

    /**
     * 查询未处理的NFD运价更新策略id
     * @return
     */
    List<JdjpNfdUpdatePolicy> queryNfdUpdatePolicys();

}
