package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlPopedomTblrole;
import com.flight.carryprice.manager.JdjpAlPopedomTblroleManager;
import com.flight.carryprice.mapper.AlPopedomTblroleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:05.
 */
@Service
public class JdjpAlPopedomTblroleManagerImpl extends BaseServiceImpl<AlPopedomTblrole> implements JdjpAlPopedomTblroleManager {
    @Resource
    private AlPopedomTblroleMapper alPopedomTblroleMapper;
    @Override
    public List<AlPopedomTblrole> queryRoleList(AlPopedomTblrole queryRole) {
        return alPopedomTblroleMapper.queryRoleList(queryRole);
    }
}
