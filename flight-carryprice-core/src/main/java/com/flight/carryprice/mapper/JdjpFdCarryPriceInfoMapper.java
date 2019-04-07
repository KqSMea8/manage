package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JdjpFdCarryPriceInfoMapper extends Mapper<JdjpFdCarryPriceInfo> {

	/**
	 * 功能描述: 列表查询
	 * @param:
	 * @return:
	 */
	List<JdjpFdCarryPriceInfo> queryList(JdjpFdCarryPriceInfo queryBean);

	/**
	 * 功能描述: 批量新增
	 * @param:
	 * @return:
	 */
	int insertBatch(List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList);

	/**
	 * 功能描述: 把来源于NFD的运价置为无效状态
	 * @param:
	 * @return:
	 */
	int updateBatchToInvaildByParams(JdjpFdCarryPriceInfo jdjpFdCarryPriceInfo);

	/**
	 * 功能描述: 根据ids更新运价状态
	 * @param:
	 * @return:
	 */
	int updateBatch(@Param("operator") String operator, @Param("ids")Integer[] ids, @Param("state")Byte state);

	/**
	 * 功能描述: 根据ids查询运价信息
	 * @param:
	 * @return:
	 */
	List<JdjpFdCarryPriceInfo> selectByIds( @Param("ids")Integer[] ids);

	/**
	 * 功能描述: 根据航司、出发、到达、舱位集合查询运价信息
	 * @param:
	 * @return:
	 */
	List<JdjpFdCarryPriceInfo> queryListBySeats(@Param("airWays") String airWays, @Param("depCode")String depCode, @Param("arrCode")String arrCode, @Param("seats")List<String> seats);

	/**
	 * 功能描述: 分页查询运价，用于数据库运价和缓存运价比较时，分批查询
	 * @param:
	 * @return:
	 */
	List<JdjpFdCarryPriceInfo> queryListByLimit(Integer pageIndex, Integer pageSize);
}