package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlPopedomTblmodule;
import com.flight.carryprice.manager.JdjpAlPopedomTblmoduleManager;
import com.flight.carryprice.mapper.AlPopedomTblmoduleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:01.
 */
@Service
public class JdjpAlPopedomTblmoduleManagerImpl extends BaseServiceImpl<AlPopedomTblmodule> implements JdjpAlPopedomTblmoduleManager {
    @Resource
    private AlPopedomTblmoduleMapper alPopedomTblmoduleMapper;
    @Override
    public List<AlPopedomTblmodule> selectLeftModule(Integer appCode, String userCode) {
        return alPopedomTblmoduleMapper.selectLeftModule(appCode, userCode);
    }
}
