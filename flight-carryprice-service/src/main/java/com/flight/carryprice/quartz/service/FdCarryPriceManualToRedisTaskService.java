package com.flight.carryprice.quartz.service;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 手动刷新FD运价缓存信息
 * @date 2019/3/19 18:07
 * @updateTime
 */
public interface FdCarryPriceManualToRedisTaskService {

    /**
     * 功能描述: 手动刷新FD运价缓存信息
     * @param:
     * @return:
     */
    void manualFlushFdToRedis();

}
