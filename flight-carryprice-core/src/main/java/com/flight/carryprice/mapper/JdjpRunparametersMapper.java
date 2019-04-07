package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpRunparameters;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface JdjpRunparametersMapper extends Mapper<JdjpRunparameters> {

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	List<JdjpRunparameters> queryList(JdjpRunparameters queryBean);
}