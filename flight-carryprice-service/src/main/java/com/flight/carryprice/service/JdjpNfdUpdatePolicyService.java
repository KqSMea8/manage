package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.entity.JdjpNfdUpdatePolicy;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;

import java.util.List;

public interface JdjpNfdUpdatePolicyService extends BaseService<JdjpNfdUpdatePolicy> {
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 插入jdjpNfdUpdatePolicy入库 对航线数据入es处理
	 * @Date 14:56 2019/3/12
	 **/
	int save(JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy);
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 分页查询
	 * @Date 18:07 2019/3/13
	 **/

	PageInfo<JdjpNfdUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpNfdUpdatePolicy jdjpNfdUpdatePolicy);
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 查询airmis获取航线类型
	 * 查询jdjpAirwaysTicketType 剔除关闭航司
	 * @Date 18:07 2019/3/13
	 **/
	List<Airline> getOpenAirlineList(Airline record, List<JdjpAirwaysTicketType> jdjpAirwaysTicketTypes);
	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description nfd自动更新策略
	 * @Date 10:34 2019/3/28
	 * @Param [airLineType, deptDate]
	 * @return void
	 **/
	void nfdAutoUpdatePolicy(byte airLineType, String deptDate);

}
