package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD更新策略
 * @date 2019/2/28 13:35
 * @updateTime
 */
public interface JdjpFdUpdatePolicyMapper extends Mapper<JdjpFdUpdatePolicy> {

    /**
     *
     * @Description 查询更新时刻<=当前时间&&状态=未同步，如果为是，则按更新时间升序排列取第一条
	 * @return
	 */
    JdjpFdUpdatePolicy selectFdPolicyOne();

    /**
     * 查询fd运价更新策略列表
     *
     * @param fdUpdatePolicy
     * @return
     */
    List<JdjpFdUpdatePolicy> queryList(JdjpFdUpdatePolicy fdUpdatePolicy);

    /**
     * 批量插入-fn运价更新策略
     *
     * @param list
     * @return
     */
    int insertBatch(List<JdjpFdUpdatePolicy> list);

}
