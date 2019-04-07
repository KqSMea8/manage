package com.flight.carryprice.service.impl;

import com.flight.carryprice.service.JdCacheService;
import com.flight.carryprice.service.JdCacheUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author juln 商旅事业部
 * @version 1.0.0
 * @Description 处理JDCache的服务
 * @date 2017年1月3日 下午7:12:16
 * @updateTime
 * @see
 */
@Service
public class JdCacheServiceImpl implements JdCacheService {

	@Resource
	JdCacheUtils jdCacheUtils;

	private static final long SECONDS = 60 * 60 * 24 * 20;// 保存20天

	private final static Logger LOGGER = Logger.getLogger(JdCacheServiceImpl.class);


	public boolean delete(String key){
		return jdCacheUtils.del(key);
	}

	public boolean set(String key, String value,Long seconds) {
		// 清空
		if (jdCacheUtils.exists(key)) {// KEY是否存在
			jdCacheUtils.del(key);
		}
		//为空时放置统一时间
		if(null == seconds){
			jdCacheUtils.expire(key, SECONDS);
		}else {
			jdCacheUtils.expire(key, seconds);
		}
		return jdCacheUtils.set(key, value);
	}


	public String get(String key) {
		return jdCacheUtils.get(key);
	}


	@Override
	@Transactional
	public boolean refreshAll(String key, Map<String, String> map) {
		// 清空
		if (jdCacheUtils.exists(key)) {// KEY是否存在
			jdCacheUtils.del(key);
		}
		// set到radis缓存
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean addOne(String key, String mapKey, String mapValue) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		map.put(mapKey, mapValue);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean delOne(String key, String mapKey) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		map.remove(mapKey);
		jdCacheUtils.del(key);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean updateOne(String key, String mapKey_old, String mapKey, String mapValue) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		map.remove(mapKey_old);
		map.put(mapKey, mapValue);
		jdCacheUtils.del(key);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean addMap(String key, Map<String, String> addmap) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		map.putAll(addmap);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean delMap(String key, Map<String, String> delmap) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		for (Entry<String, String> entry : delmap.entrySet()) {
			map.remove(entry.getKey());
		}
		jdCacheUtils.del(key);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	@Transactional
	public boolean updateMap(String key, Map<String, String> delmap, Map<String, String> addmap) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		for (Entry<String, String> entry : delmap.entrySet()) {
			map.remove(entry.getKey());
		}
		map.putAll(addmap);
		jdCacheUtils.del(key);
		jdCacheUtils.expire(key, SECONDS);
		return jdCacheUtils.hMSet(key, map);
	}

	@Override
	public String getMapValue(String key, String mapKey) {
		Map<String, String> map = jdCacheUtils.hGetAll(key);
		String mapValue = map.get(mapKey);
		if (mapValue == null) {
			mapValue = "";
		}
		return mapValue;
	}

	@Override
	public Map<String, String> getValue(String kEY) {
		Map<String, String> map = jdCacheUtils.hGetAll(kEY);
		return map;
	}


}


