package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.AlUsers;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface AlUsersMapper extends Mapper<AlUsers> {
	
	/**
	 * 根据名称和系统编号查询用户，如果此用户不是该系统的用户，则返回null
	 * @param userName
	 * @param appCode
	 * @return
	 */
	public AlUsers findUserByUserNameAndAppCode(@Param("userName") String userName, @Param("appCode") Integer appCode);
	
	/**
	 * 根据条件查询user
	 * @param queryUser
	 * @return
	 */
	public List<AlUsers> queryUserList(AlUsers queryUser);
}