package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface JdjpNfdUpdatePolicyMapper extends Mapper<JdjpNfdUpdatePolicy> {

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
