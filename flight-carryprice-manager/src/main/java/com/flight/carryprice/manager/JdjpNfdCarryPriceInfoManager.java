package com.flight.carryprice.manager;

import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import java.util.List;
/**
 * Author wanghaiyuan
 * Date 2019/3/14 10:44.
 */
public interface JdjpNfdCarryPriceInfoManager {
    /**
     * 条件查询NFD运价
     * @param queryBean 查询条件bean
     * @return
     */
    List<JdjpNfdCarryPriceInfo> queryList(JdjpNfdCarryPriceInfo queryBean);
    int updateBatch(String operator, Byte state, Integer[] ids);
}
