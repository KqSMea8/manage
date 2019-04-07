package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价缓存处理
 * @date 2019/3/4 17:36
 * @updateTime
 */
public interface JdjpFdCarryPriceInfoRedisService extends BaseService<JdjpFdCarryPriceInfo> {

    /**
     * 根据运价集合，新增或修改FD基础运价缓存
     *
     * @param fdCarryPriceInfos
     * @return
     */
    Boolean updateOrAddCarryPriceRedis(List<JdjpFdCarryPriceInfo> fdCarryPriceInfos);

    /**
     * 根据运价实体，新增或修改FD基础运价缓存
     *
     * @param fdCarryPriceInfo
     * @return
     */
    Boolean updateOrAddCarryPriceRedis(JdjpFdCarryPriceInfo fdCarryPriceInfo);

    /**
     * 根据运价集合，批量删除FD基础运价缓存
     * @param fdCarryPriceInfos
     * @return
     */
    Boolean delCarryPriceRedis(List<JdjpFdCarryPriceInfo> fdCarryPriceInfos);

    /**
     * 根据运价实体，单条删除FD基础运价缓存
     * @param fdCarryPriceInfo
     * @return
     */
    Boolean delCarryPriceRedis(JdjpFdCarryPriceInfo fdCarryPriceInfo);

    /**
     * 获取缓存中的FD运价
     * @param fdCarryPriceInfo
     */
    JdjpFdCarryPriceInfo getRedisFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo);

    /**
     * 功能描述: 清除缓存
     * @param:
     * @return:
     */
    void cleanRedisForCarryPrice(String airWays, String depCode, String arrCode, String seat, String carryPriceType);
}
