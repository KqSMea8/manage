package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlPopedomTblrole;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface AlPopedomTblroleMapper extends Mapper<AlPopedomTblrole> {

	/**
	 * 查询角色列表
	 * 
	 * @param queryRole
	 *            查询条件
	 * @return
	 */
	public List<AlPopedomTblrole> queryRoleList(AlPopedomTblrole queryRole);
}