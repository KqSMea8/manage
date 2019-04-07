package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.mapper.JdjpFdCarryPriceInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description  FD运价Manager
 * @date 2019/3/4 11:10
 * @updateTime
 */
@Service
public class JdjpFdCarryPriceInfoManagerImpl implements JdjpFdCarryPriceInfoManager{

    @Resource
    private JdjpFdCarryPriceInfoMapper jdjpFdCarryPriceInfoMapper;

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdCarryPriceInfo> queryList(JdjpFdCarryPriceInfo queryBean) {
        return jdjpFdCarryPriceInfoMapper.queryList(queryBean);
    }

    /**
     * 功能描述: 批量新增
     * @param:
     * @return:
     */
    @Override
    public int insertBatch(List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList){
        return jdjpFdCarryPriceInfoMapper.insertBatch(fdCarryPriceInfoList);
    }

    /**
     * 功能描述: 根据条件把运价置为无效状态
     * @param:
     * @return:
     */
    @Override
    public int updateBatchToInvaildByParams(JdjpFdCarryPriceInfo jdjpFdCarryPriceInfo){
        return jdjpFdCarryPriceInfoMapper.updateBatchToInvaildByParams(jdjpFdCarryPriceInfo);
    }

    /**
     * 功能描述: 根据ids更新运价状态
     * @param:
     * @return:
     */
    @Override
    public boolean updateBatch(String operator, Integer[] ids, Byte state){
        int result = jdjpFdCarryPriceInfoMapper.updateBatch(operator, ids, state);
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 功能描述: 根据ids查询运价信息
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdCarryPriceInfo> selectByIds(Integer[] ids){
        return jdjpFdCarryPriceInfoMapper.selectByIds( ids);
    }

    /**
     * 功能描述: 根据航司、出发、到达、舱位集合查询运价信息
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdCarryPriceInfo> queryListBySeats(String airWays, String depCode, String arrCode, List<String> seats){
        return jdjpFdCarryPriceInfoMapper.queryListBySeats(airWays, depCode, arrCode, seats);
    }

    /**
     * 功能描述: 分页查询运价，用于数据库运价和缓存运价比较时，分批查询
     * @param:
     * @return:
     */
    @Override
    public List<JdjpFdCarryPriceInfo> queryListByLimit(Integer pageNo, Integer pageSize){
        return jdjpFdCarryPriceInfoMapper.queryListByLimit((pageNo - 1) * pageSize , pageSize);
    }


}
