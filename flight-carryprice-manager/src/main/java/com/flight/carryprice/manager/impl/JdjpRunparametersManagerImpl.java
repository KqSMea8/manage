package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.JdjpRunparameters;
import com.flight.carryprice.manager.JdjpRunparametersManager;
import com.flight.carryprice.mapper.JdjpRunparametersMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/1 15:29.
 */
@Service
public class JdjpRunparametersManagerImpl extends BaseServiceImpl<JdjpRunparameters> implements JdjpRunparametersManager {
    @Resource
    private JdjpRunparametersMapper jdjpRunparametersMapper;
    @Override
    public List<JdjpRunparameters> queryList(JdjpRunparameters queryBean) {
        return jdjpRunparametersMapper.queryList(queryBean);
    }

    /**
     * 功能描述: 根据名称查询单个总控参数
     * @param:
     * @return:
     */
    @Override
    public JdjpRunparameters queryByName(String name){
        JdjpRunparameters runparameters = new JdjpRunparameters();
        runparameters.setName(name);
        return jdjpRunparametersMapper.selectOne(runparameters);
    }
}
