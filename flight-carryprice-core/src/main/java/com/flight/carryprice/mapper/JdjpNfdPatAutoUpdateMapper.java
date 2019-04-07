package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface JdjpNfdPatAutoUpdateMapper extends Mapper<JdjpNfdPatAutoUpdate> {

	/**
	 * @Author hYuan
	 * @Description 根据热门类型和 运价类型查询
	 * @Date  2019/3/4 11:48
	 * @Param null
	 * @return 
	 **/
	List<JdjpNfdPatAutoUpdate> queryByPopularAndAirType(JdjpNfdPatAutoUpdate ccsNfdAutoUpdate);
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 根据更新频率 executeIntervalDay 更新自动更新配置表的更新时间 executeQuartzTime
	 * @Date 11:53 2019/3/28
	 * @Param [jdjpNfdPatAutoUpdateList]
	 * @return void
	 **/
	int updateExecuteQuartzTimeBatch(List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList);
}