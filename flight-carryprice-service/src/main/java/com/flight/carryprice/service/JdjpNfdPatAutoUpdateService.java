package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import org.springframework.ui.ModelMap;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/4 11:47.
 */
public interface JdjpNfdPatAutoUpdateService extends BaseService<JdjpNfdPatAutoUpdate> {
    List<JdjpNfdPatAutoUpdate> queryByPopularAndAirType(JdjpNfdPatAutoUpdate jdjpNfdPatAutoUpdate);
    void nfdPatAutoUpdateJob();
}
