package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.entity.JdjpFdDifference;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价Manager
 * @date 2019/3/7 11:09
 * @updateTime
 */
public interface JdjpFdDifferenceManager {

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    List<JdjpFdDifference> queryList(JdjpFdDifference queryBean);

    /**
     * 获取版本号列表
     * @param
     * @return
     */
    List<String> getVersionNumList();


}
