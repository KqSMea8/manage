package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.github.pagehelper.PageInfo;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价Service
 * @date 2019/3/4 11:07
 * @updateTime
 */
public interface JdjpFdCarryPriceInfoService extends BaseService<JdjpFdCarryPriceInfo> {

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    PageInfo<JdjpFdCarryPriceInfo> pagination(Integer pageIndex, Integer pageSize, JdjpFdCarryPriceInfo queryBean);

    /**
     * 新增运价
     * @param fdCarryPriceInfo
     * @param seatesArr 舱位数组
     * @param carryPriceArr  价格数组
     */
    int addFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo, String[] seatesArr, String[] carryPriceArr);

    /**
     * 获取缓存中的FD运价
     * @param fdCarryPriceInfo
     */
    JdjpFdCarryPriceInfo getRedisFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo);

    /**
     * 更新FD运价，并更新缓存
     * @param fdCarryPriceInfo
     */
    int updateFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo);

    /**
     * 功能描述: 批量启用
     * @param:
     * @return:
     */
    boolean isShow(String operator, String carrypriceIds);

    /**
     * 功能描述: 批量停用
     * @param:
     * @return:
     */
    boolean isHide(String operator, String carrypriceIds);

    /**
     * 功能描述: 清除缓存
     * @param:
     * @return:
     */
    void cleanRedisForCarryPrice(String airWays, String depCode, String arrCode, String seat, String carryPriceType);

}
