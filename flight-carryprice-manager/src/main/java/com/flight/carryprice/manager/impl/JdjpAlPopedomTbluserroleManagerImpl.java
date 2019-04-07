package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlPopedomTbluserrole;
import com.flight.carryprice.manager.JdjpAlPopedomTbluserroleManager;
import com.flight.carryprice.mapper.AlPopedomTbluserroleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:18.
 */
@Service
public class JdjpAlPopedomTbluserroleManagerImpl extends BaseServiceImpl<AlPopedomTbluserrole> implements JdjpAlPopedomTbluserroleManager {
    @Resource
    private AlPopedomTbluserroleMapper alPopedomTbluserroleMapper;
    @Override
    public void deleteByUserCode(Integer userCode) {
        alPopedomTbluserroleMapper.deleteByUserCode(userCode);
    }

    /*@Override
    public List<AlPopedomTbluserrole> select(AlPopedomTbluserrole alPopedomTbluserrole) {
        return alPopedomTbluserroleMapper.select(alPopedomTbluserrole);
    }*/
}
