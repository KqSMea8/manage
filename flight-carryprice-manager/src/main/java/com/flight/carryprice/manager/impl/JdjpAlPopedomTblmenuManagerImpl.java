package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlPopedomTblmenu;
import com.flight.carryprice.manager.JdjpAlPopedomTblmenuManager;
import com.flight.carryprice.mapper.AlPopedomTblmenuMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 15:57.
 */
@Service
public class JdjpAlPopedomTblmenuManagerImpl extends BaseServiceImpl<AlPopedomTblmenu> implements JdjpAlPopedomTblmenuManager {
    @Resource
    private AlPopedomTblmenuMapper alPopedomTblmenuMapper;
    @Override
    public List<AlPopedomTblmenu> selectLeftMenu(Integer appCode, String userCode) {
        return alPopedomTblmenuMapper.selectLeftMenu(appCode, userCode);
    }
}
