package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlPopedomTblmodule;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 15:59.
 */
public interface JdjpAlPopedomTblmoduleManager extends BaseService<AlPopedomTblmodule> {
    /**
     * @Author hYuan
     * @Description 根据appcode 和 usercode查询tblmodule
     * @Date  2019/2/28 16:00
     * @Param appcode usercode
     * @return
     **/
    List<AlPopedomTblmodule> selectLeftModule(@Param("appCode") Integer appCode, @Param("userCode") String userCode);
}
