package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpCarryPriceTask;
import com.flight.carryprice.entity.JdjpFdDifference;
import com.flight.carryprice.manager.JdjpCarryPriceTaskManager;
import com.flight.carryprice.manager.JdjpFdDifferenceManager;
import com.flight.carryprice.mapper.JdjpCarryPriceTaskMapper;
import com.flight.carryprice.mapper.JdjpFdDifferenceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价Manager
 * @date 2019/3/7 11:19
 * @updateTime
 */
@Service
public class JdjpFdDifferenceManagerImpl implements JdjpFdDifferenceManager{

    @Resource
    private JdjpFdDifferenceMapper jdjpFdDifferenceMapper;

    /**
     * 功能描述: 查询列表
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdDifference> queryList(JdjpFdDifference queryBean) {
        return jdjpFdDifferenceMapper.queryList(queryBean);
    }

    /**
     * 获取版本号列表
     * @param
     * @return
     */
    @Override
    public List<String> getVersionNumList(){
        return jdjpFdDifferenceMapper.getVersionNumList();
    }


}
