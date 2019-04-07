package com.flight.carryprice.quartz.service;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 自动刷新FD运价缓存信息
 * @date 2019/3/19 18:05
 * @updateTime
 */
public interface FdCarryPriceAutoToRedisTaskService {

    /**
     * 功能描述: 自动刷新FD运价缓存信息
     * @param:
     * @return:
     */
    void autoFlushFdToRedis();
}
