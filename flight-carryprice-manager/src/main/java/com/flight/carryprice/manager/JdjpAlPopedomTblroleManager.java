package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlPopedomTblrole;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:03.
 */
public interface JdjpAlPopedomTblroleManager extends BaseService<AlPopedomTblrole> {
    /**
     * @Author hYuan
     * @Description 根据 queryRole 查询queryRole列表
     * @Date  2019/2/28 16:04
     * @Param queryRole
     * @return 
     **/
    List<AlPopedomTblrole> queryRoleList(AlPopedomTblrole queryRole);
}
