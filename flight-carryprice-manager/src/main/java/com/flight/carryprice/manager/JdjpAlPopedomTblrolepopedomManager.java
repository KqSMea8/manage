package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlPopedomTblrolepopedom;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:13.
 */
public interface JdjpAlPopedomTblrolepopedomManager extends BaseService<AlPopedomTblrolepopedom> {
    /**
     * @Author hYuan
     * @Description 根据rolecode删除AlPopedomTblrolepopedom
     * @Date  2019/2/28 16:13
     * @Param roleCode
     * @return 
     **/
    void deleteByRoleCode(Integer roleCode);
}
