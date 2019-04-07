package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlPopedomTblrolepopedom;
import com.flight.carryprice.manager.JdjpAlPopedomTblrolepopedomManager;
import com.flight.carryprice.mapper.AlPopedomTblrolepopedomMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:14.
 */
@Service
public class JdjpAlPopedomTblrolepopedomManagerImpl extends BaseServiceImpl<AlPopedomTblrolepopedom> implements JdjpAlPopedomTblrolepopedomManager {
    @Resource
    private AlPopedomTblrolepopedomMapper alPopedomTblrolepopedomMapper;
    @Override
    public void deleteByRoleCode(Integer roleCode) {
        alPopedomTblrolepopedomMapper.deleteByRoleCode(roleCode);
    }
}
