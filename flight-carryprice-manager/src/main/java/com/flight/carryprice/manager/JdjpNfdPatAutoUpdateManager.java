package com.flight.carryprice.manager;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/4 11:36.
 */
public interface JdjpNfdPatAutoUpdateManager extends BaseService<JdjpNfdPatAutoUpdate> {
    List<JdjpNfdPatAutoUpdate> queryByPopularAndAirType(JdjpNfdPatAutoUpdate ccsNfdAutoUpdate);
    int updateExecuteQuartzTimeBatch(List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList);
}
