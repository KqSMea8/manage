package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

public interface JdjpRunparametersService extends BaseService<JdjpRunparameters> {

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param queryBean
	 * @return
	 */
	public PageInfo<JdjpRunparameters> pagination(Integer pageNo, Integer pageSize, JdjpRunparameters queryBean);


	/**
	 * 获取更新频率
	 * 
	 * @param
	 */
	List<Map<String, String>> queryUpdateDay();

	/**
	 * 获取基础数据-舱位类型
	 * 
	 * @return
	 */
	public Map<String, String> getSeatTypeMap();



	List<Map<String, String>> queryParamByName(String name);
}
