package com.flight.carryprice.service.impl;

import com.flight.carryprice.service.JdCacheUtils;
import com.jd.jim.cli.Cluster;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @see
 * @author juln 商旅事业部
 * @version 1.0.0
 * @date 2016年9月21日 下午7:11:48
 * @updateTime
 */
@Service
public class JdCacheImplUtils implements JdCacheUtils {

	@Autowired(required = false)
	private Cluster jimdbClient;

	private static Logger logger = Logger.getLogger(JdCacheImplUtils.class);

	/**
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @param exist
	 * @return
	 */
	@Override
	public boolean set(String key, String value, long timeout, TimeUnit unit, boolean exist) {
		boolean result = false;
		try {
			result = jimdbClient.set(key,value,timeout, TimeUnit.SECONDS, exist);
			return true;
		} catch (Exception ex) {
			String errorMsg = "jimdb-->setEx 设置key失败 key:" + key + ",value:" + value;
			logger.error(errorMsg, ex);
		}
		return result;
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean del(String key, String value) {
		boolean result = true;
		try {
			if(value.equals(jimdbClient.get(key))){
				Long del = jimdbClient.del(key);
			}
		} catch (Exception ex) {
			result = false;
			String errorMsg = "jimdb-->setEx 删除key失败 key:" + key + ",value:" + value;
			logger.error(errorMsg, ex);
		}
		return result;
	}

	/*
		 * （非 Javadoc）
		 *
		 * @see com.flight.system.service.JdCacheUtils#set(java.lang.String,
		 * java.lang.String)
		 */
	@Override
	public boolean set(String key, String value) {
		jimdbClient.set(key, value);
		return true;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#set(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public boolean set(String key, Object value) {
		return true;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#setEx(java.lang.String,
	 * java.lang.String, long)
	 */
	@Override
	public boolean setEx(String key, String value, long seconds) {
		if (StringUtils.isBlank(key) || 0 > seconds)
			return false;
		try {
			jimdbClient.setEx(key, value, seconds, TimeUnit.SECONDS);
			return true;
		} catch (Exception ex) {
			String errorMsg = "jimdb-->setEx 设置key失败 key:" + key + ",value:" + value;
			logger.error(errorMsg, ex);
		}
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#setEx(java.lang.String,
	 * java.lang.Object, long)
	 */
	@Override
	public boolean setEx(String key, Object value, long seconds) {
		/*
		 * String str = JacksonUtil.obj2json(value); ShardedJedis jedis =
		 * shardedJedisPool.getResource(); jedis.setex(key, (int) seconds, str);
		 * jedis.close();
		 */
		return true;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#setNX(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean setNX(String key, String value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#setNX(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public boolean setNX(String key, Object value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#setObject(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public boolean setObject(String key, Object value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hSet(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hSet(String key, String field, String value) {
		if (StringUtils.isBlank(key)||StringUtils.isBlank(field)||StringUtils.isBlank(value)){
			return false;
		}
		try {
			return jimdbClient.hSet(key, field, value);
		}catch (Exception e){
			logger.error("Hset value error", e);
		}
		return false;
	}
	/**
	 * @Description reidsKey Map<key, value>获取mapkey集合
	 * @Date 10:30 2019/3/26
	 * @Param [key]
	 * @return java.util.Set<java.lang.String>
	 **/
	public Set<String> hkeys(String key){
		return jimdbClient.hKeys(key);
	}
	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hSet(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hSet(String key, String field, Object value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hSetNX(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hSetNX(String key, String field, String value) {
        if (StringUtils.isBlank(key)||StringUtils.isBlank(field)||StringUtils.isBlank(value)){
            return false;
        }
        try {
            return jimdbClient.hSetNX(key, field, value);
        }catch (Exception e){
            logger.error("Hset value error", e);
        }
        return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hSetNX(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hSetNX(String key, String field, Object value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#mSet(java.util.Map)
	 */
	@Override
	public boolean mSet(Map<String, String> values) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hMSet(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public boolean hMSet(String key, Map<String, String> values) {
		if (StringUtils.isBlank(key))
			return false;
		try {
			jimdbClient.hMSet(key, values);
			return true;
		} catch (Exception ex) {
			String errorMsg = "jimdb-->hMSet 设置key失败 key:" + key + ",value:" + values;
			logger.error(errorMsg, ex);
		}
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#append(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long append(String key, String value) {
		return 0;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#sAdd(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Long sAdd(String key, String... values) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#lPush(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Long lPush(String key, String... values) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		String data = null;
		if (StringUtils.isBlank(key))
			return data;
		try {
			data = jimdbClient.get(key);
		} catch (Exception ex) {
			String errorMsg = "jimdb-->get key:" + key;
			logger.error(errorMsg, ex);
		}
		return data;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#get(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T get(String key, Class<T> clazz) {
		/*
		 * ShardedJedis jedis = shardedJedisPool.getResource(); String str =
		 * jedis.get(key); jedis.close(); return JacksonUtil.json2Obj(str,
		 * clazz);
		 */
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hGet(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String hGet(String key, String field) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hGet(java.lang.String,
	 * java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T hGet(String key, String field, Class<T> clazz) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hGetAll(java.lang.String)
	 */
	@Override
	public Map<String, String> hGetAll(String key) {
		if (StringUtils.isBlank(key))
			return new HashMap<String, String>();
		try {
			return jimdbClient.hGetAll(key);
		} catch (Exception ex) {
			String errorMsg = "jimdb-->hGetAll 设置key失败 key:" + key;
			logger.error(errorMsg, ex);
		}
		return new HashMap<String, String>();
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hGetAll(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#mGet(java.lang.String[])
	 */
	@Override
	public List<String> mGet(String... keys) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#sMembers(java.lang.String)
	 */
	@Override
	public Set<String> sMembers(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#sCard(java.lang.String)
	 */
	@Override
	public Long sCard(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#lLen(java.lang.String)
	 */
	@Override
	public Long lLen(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) {
		if (StringUtils.isBlank(key))
			return false;
		try {
			return jimdbClient.exists(key);
		} catch (Exception ex) {
			String errorMsg = "jimdb-->exists 设置key失败 key:" + key;
			logger.error(errorMsg, ex);
		}

		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#sIsMember(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean sIsMember(String key, String value) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#incr(java.lang.String)
	 */
	@Override
	public Long incr(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#incrBy(java.lang.String,
	 * long)
	 */
	@Override
	public Long incrBy(String key, long step) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hIncrBy(java.lang.String,
	 * java.lang.String, long)
	 */
	@Override
	public Long hIncrBy(String key, String field, long step) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#decr(java.lang.String)
	 */
	@Override
	public Long decr(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#sRem(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Long sRem(String key, String... values) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#lPop(java.lang.String)
	 */
	@Override
	public String lPop(String key) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#hDel(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Long hDel(String key, String... fields) {
		return jimdbClient.hDel(key, fields);
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#expire(java.lang.String,
	 * long)
	 */
	@Override
	public boolean expire(String key, long seconds) {
		if (StringUtils.isBlank(key) || 0 > seconds)
			return false;
		try {
			jimdbClient.expire(key, seconds, TimeUnit.SECONDS);
			return true;
		} catch (Exception ex) {
			String errorMsg = "jimdb-->expire 更新" + key + "的过期时间失败";
			logger.error(errorMsg, ex);
		}
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#expireAt(java.lang.String,
	 * java.util.Date)
	 */
	@Override
	public boolean expireAt(String key, Date time) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#del(java.lang.String)
	 */
	@Override
	public boolean del(String key) {

		try {
			Long del = jimdbClient.del(key);
			return true;
		} catch (Exception e) {
			String errorMsg = "jimdb-->del key:" + key;
			logger.error(errorMsg, e);
		}
		return false;
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#lTrim(java.lang.String, long,
	 * long)
	 */
	@Override
	public void lTrim(String key, long start, long end) {

	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.flight.system.service.JdCacheUtils#lIndex(java.lang.String,
	 * long)
	 */
	@Override
	public String lIndex(String key, long index) {
		return null;
	}

	public Cluster getJimdbClient() {
		return jimdbClient;
	}

	public void setJimdbClient(Cluster jimdbClient) throws Exception {
		this.jimdbClient = jimdbClient;
	}

	@Override
	public Set<String> keys(String pattern) {
		return jimdbClient.keys(pattern);
	}
}
