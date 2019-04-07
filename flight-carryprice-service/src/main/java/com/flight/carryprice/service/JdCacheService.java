package com.flight.carryprice.service;

import java.util.Map;

/**
 * @Description 处理JDCache的服务
 * @see
 * @author juln 商旅事业部
 * @version 1.0.0
 * @date 2017年1月3日 下午7:11:33
 * @updateTime
 */
public interface JdCacheService {

	boolean delete(String key);

	String get(String key);

	boolean set(String key, String value, Long seconds) ;

	/**
	 * 根据KEY，全量更新MAP-value值
	 *
	 * @param key
	 * @param map
	 * @return true:成功，false:失败
	 */
	boolean refreshAll(String key, Map<String, String> map);

	/**
	 * 根据KEY，向radis添加/更新一条
	 *
	 * @param key
	 * @param mapKey
	 * @param mapValue
	 * @return
	 */
	boolean addOne(String key, String mapKey, String mapValue);

	/**
	 * 根据KEY，删除radis中一条
	 *
	 * @param key
	 * @param mapKey
	 * @return
	 */
	boolean delOne(String key, String mapKey);

	/**
	 * 根据KEY，删除radis中一条旧数据，添加一条新数据
	 *
	 * @param key
	 * @param mapKey_old
	 * @param mapKey
	 * @param mapValue
	 * @return
	 */
	boolean updateOne(String key, String mapKey_old, String mapKey, String mapValue);

	/**
	 * 根据KEY，向radis添加/更新MAP
	 *
	 * @param key
	 * @param addmap
	 * @return
	 */
	boolean addMap(String key, Map<String, String> addmap);

	/**
	 * 根据KEY，从radis删除MAP
	 *
	 * @param key
	 * @param delmap
	 * @return
	 */
	boolean delMap(String key, Map<String, String> delmap);

	/**
	 * 根据KEY，从radis删除MAP，再增加新MAP
	 *
	 * @param key
	 * @param delmap
	 * @param addmap
	 * @return
	 */
	boolean updateMap(String key, Map<String, String> delmap, Map<String, String> addmap);

	/**
	 * 根据KEY，获取MAP中的value值
	 *
	 * @param key
	 * @param mapKey
	 * @return
	 */
	String getMapValue(String key, String mapKey);

	/**
	 * @param kEY
	 * @return
	 */
	Map<String, String> getValue(String kEY);
/*
	*//**
	 * @param key
	 * @param voSet
	 * @return
	 *//*
	Map<String, String> getAirwaysSeatValue(String key, Set<AirwaysSeatVo> voSet);

	*//**
	 * @param key
	 * @param voSet
	 * @return
	 *//*
	Map<String, String> getAirportAirwayVoyageValue(String key, Set<AirportAirwayVoyageVo> voSet);

	*//**
	 * @param key
	 * @param voSet
	 * @return
	 *//*
	Map<String, String> getRefundChangeRuleValue(String key, Set<RefundChangeRuleCondition> voSet);

	Map<String, String> getMileageInfo(String key, Set<AirwaysSeatVo> voSet);

	public Map<String, String> getLuggageInfo(String key, Set<AirwaysSeatVo> voSet);*/

}
