package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlPopedomTbluserrole;
import tk.mybatis.mapper.common.Mapper;

public interface AlPopedomTbluserroleMapper extends Mapper<AlPopedomTbluserrole> {

	/**
	 * 删除某个用户的所有角色
	 * 
	 * @param userCode
	 *            用户code
	 */
	public void deleteByUserCode(Integer userCode);
}