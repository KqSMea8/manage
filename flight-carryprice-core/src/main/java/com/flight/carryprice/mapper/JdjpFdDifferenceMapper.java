package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.entity.JdjpFdDifference;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价缓存和数据库差异表
 * @date 2019/3/7 16:00
 * @updateTime
 */
public interface JdjpFdDifferenceMapper extends Mapper<JdjpFdDifference> {

    /**
     * 查询列表
     * @param fdDifference
     * @return
     */
    List<JdjpFdDifference> queryList(JdjpFdDifference fdDifference);

    /**
     * 获取版本号列表
     * @param
     * @return
     */
    List<String> getVersionNumList();

}
