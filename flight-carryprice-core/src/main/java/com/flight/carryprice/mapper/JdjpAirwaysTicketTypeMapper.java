package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface JdjpAirwaysTicketTypeMapper extends Mapper<JdjpAirwaysTicketType> {

	/**
	 * @Author hYuan 机票供应链研发部
	 * @Description 查询swith为3的记录
	 * @Date 16:40 2019/3/13
	 * @Param []
	 * @return java.util.List<com.flight.carryprice.entity.JdjpAirwaysTicketType>
	 **/
	List<JdjpAirwaysTicketType> queryCloseSwitchTypeList();

	/**
	 * 根据航司二字码查询航司出票类型
	 * @param airways 航司二字码
	 * @return 航司出票类型
	 */
	JdjpAirwaysTicketType queryTicketTypeByAirways(String airways);

}