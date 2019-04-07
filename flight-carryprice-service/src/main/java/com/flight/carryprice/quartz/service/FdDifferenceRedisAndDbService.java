package com.flight.carryprice.quartz.service;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价比较数据库和缓存中不同，做记录
 * @date 2019/4/2 10:44
 * @updateTime
 */
public interface FdDifferenceRedisAndDbService {

    /**
     * 功能描述: 比较FD运价数据库和redis中不同
     * @param:
     * @return: 
     */
    void compareFDRedisAndDB();
}
