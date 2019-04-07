package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpRunparameters;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/1 15:27.
 */
public interface JdjpRunparametersManager extends BaseService<JdjpRunparameters> {
    /**
     * @Author hYuan
     * @Description 分页查询总控参数
     * @Date  2019/3/1 15:29
     * @Param null
     * @return 
     **/
    List<JdjpRunparameters> queryList(JdjpRunparameters queryBean);

    /**
     * 功能描述: 根据名称查询单个总控参数
     * @param:
     * @return:
     */
    JdjpRunparameters queryByName(String name);
}
