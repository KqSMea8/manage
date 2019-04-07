package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlPopedomTbluserrole;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:17.
 */
public interface JdjpAlPopedomTbluserroleManager extends BaseService<AlPopedomTbluserrole> {
    /**
     * @Author hYuan
     * @Description 删除某个用户的角色
     * @Date  2019/2/28 16:18
     * @Param userCode
     * @return 
     **/
    void deleteByUserCode(Integer userCode);

    //List<AlPopedomTbluserrole> select(AlPopedomTbluserrole alPopedomTbluserrole);
}
