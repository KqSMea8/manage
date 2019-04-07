package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlPopedomTblmodule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface AlPopedomTblmoduleMapper extends Mapper<AlPopedomTblmodule> {
	
	/**
	 * 根据系统编号和用户代码查询
	 * @param appCode
	 * @param userCode
	 * @return
	 */
	public List<AlPopedomTblmodule> selectLeftModule(@Param("appCode") Integer appCode, @Param("userCode") String userCode);
}