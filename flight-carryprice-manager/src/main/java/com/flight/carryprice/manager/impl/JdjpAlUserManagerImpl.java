package com.flight.carryprice.manager.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.entity.AlUsers;
import com.flight.carryprice.manager.JdjpAlUserManager;
import com.flight.carryprice.mapper.AlUsersMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 15:48.
 */
@Service
public class JdjpAlUserManagerImpl extends BaseServiceImpl<AlUsers> implements JdjpAlUserManager {
    @Resource
    private AlUsersMapper alUsersMapper;
    @Override
    public AlUsers findUserByUserNameAndAppCode(String userName, Integer appCode) {
        return alUsersMapper.findUserByUserNameAndAppCode(userName, appCode);
    }

    @Override
    public List<AlUsers> queryUserList(AlUsers queryUser) {
        return alUsersMapper.queryUserList(queryUser);
    }

    @Override
    public AlUsers selectByPrimaryKey(Integer userId) {
        return alUsersMapper.selectByPrimaryKey(userId);
    }

}
