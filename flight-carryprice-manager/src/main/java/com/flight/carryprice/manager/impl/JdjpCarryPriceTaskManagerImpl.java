package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpCarryPriceTaskManager;
import com.flight.carryprice.mapper.JdjpCarryPriceTaskMapper;
import com.flight.carryprice.mapper.JdjpFdCarryPriceInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价任务表
 * @date 2019/3/6 16:00
 * @updateTime
 */
@Service
public class JdjpCarryPriceTaskManagerImpl implements JdjpCarryPriceTaskManager{

    @Resource
    private JdjpCarryPriceTaskMapper jdjpCarryPriceTaskMapper;

    /**
     * 功能描述: 查询
     * @param:
     * @return:
     */
    @Override
    public List<JdjpCarryPriceTask> queryList(JdjpCarryPriceTask queryBean) {
        return jdjpCarryPriceTaskMapper.queryList(queryBean);
    }

    /**
     * 功能描述: 删除
     * @param:
     * @return:
     */
    @Override
    public int deleteByQueryCondition(JdjpCarryPriceTask condition){
        return jdjpCarryPriceTaskMapper.deleteByQueryCondition(condition);
    }

}
