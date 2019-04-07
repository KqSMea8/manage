package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.AlTmp;
import org.apache.ibatis.annotations.Param;

/**
 * Author wanghaiyuan
 * Date 2019/2/28 16:23.
 */
public interface JdjpAlTmpManager extends BaseService<AlTmp> {
    void execSql(@Param("sql") String sql);
}
