package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlPopedomTblrolepopedom;
import tk.mybatis.mapper.common.Mapper;

public interface AlPopedomTblrolepopedomMapper extends Mapper<AlPopedomTblrolepopedom> {

	/**
	 * 根据roleCode删除角色-权限关联表
	 * 
	 * @param roleCode
	 */
	public void deleteByRoleCode(Integer roleCode);
}