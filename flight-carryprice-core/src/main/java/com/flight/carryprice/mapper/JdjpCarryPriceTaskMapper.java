package com.flight.carryprice.mapper;

import com.flight.carryprice.entity.JdjpCarryPriceTask;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价任务表
 * @date 2019/3/6 16:00
 * @updateTime
 */
public interface JdjpCarryPriceTaskMapper  extends Mapper<JdjpCarryPriceTask> {

    /**
     * 功能描述: 查询
     * @param:
     * @return:
     */
    List<JdjpCarryPriceTask> queryList(JdjpCarryPriceTask queryBean);

    /**
     * 功能描述: 删除
     * @param:
     * @return:
     */
    int deleteByQueryCondition(JdjpCarryPriceTask condition);

}
