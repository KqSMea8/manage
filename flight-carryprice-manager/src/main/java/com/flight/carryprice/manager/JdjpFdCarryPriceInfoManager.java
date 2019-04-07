package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价Manager
 * @date 2019/3/4 11:09
 * @updateTime
 */
public interface JdjpFdCarryPriceInfoManager {

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
     * 功能描述: 根据条件把运价置为无效状态
     * @param:
     * @return:
     */
    int updateBatchToInvaildByParams(JdjpFdCarryPriceInfo jdjpFdCarryPriceInfo);

    /**
     * 功能描述: 根据ids更新运价状态
     * @param:
     * @return:
     */
    boolean updateBatch(String operator, Integer[] ids, Byte state);

    /**
     * 功能描述: 根据ids查询运价信息
     * @param:
     * @return:
     */
    List<JdjpFdCarryPriceInfo> selectByIds(Integer[] ids);

    /**
     * 功能描述: 根据航司、出发、到达、舱位集合查询运价信息
     * @param:
     * @return:
     */
    List<JdjpFdCarryPriceInfo> queryListBySeats(String airWays, String depCode, String arrCode, List<String> seats);

    /**
     * 功能描述: 分页查询运价，用于数据库运价和缓存运价比较时，分批查询
     * @param:
     * @return:
     */
    List<JdjpFdCarryPriceInfo> queryListByLimit(Integer pageNo, Integer pageSize);


}
