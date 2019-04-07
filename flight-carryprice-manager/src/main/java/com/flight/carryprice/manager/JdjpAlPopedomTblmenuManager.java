package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlPopedomTblmenu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 15:55.
 */
public interface JdjpAlPopedomTblmenuManager extends BaseService<AlPopedomTblmenu> {
    /**
     * @Author hYuan
     * @Description 根据appcode 和 usercode查询tblmenu
     * @Date  2019/2/28 15:56
     * @Param appCode userCode
     * @return
     **/
    List<AlPopedomTblmenu> selectLeftMenu(@Param("appCode") Integer appCode, @Param("userCode") String userCode);
}
