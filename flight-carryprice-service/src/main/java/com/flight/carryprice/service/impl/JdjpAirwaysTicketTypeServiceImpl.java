package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.manager.JdjpAirwaysTicketTypeManager;
import com.flight.carryprice.service.JdjpAirwaysTicketTypeService;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/13 16:36.
 */
@Service
public class JdjpAirwaysTicketTypeServiceImpl extends BaseServiceImpl<JdjpAirwaysTicketType> implements JdjpAirwaysTicketTypeService {
    @Resource
    private JdjpAirwaysTicketTypeManager jdjpAirwaysTicketTypeManager;

    @Override
    public List<JdjpAirwaysTicketType> queryCloseSwitchTypeList() {
        return jdjpAirwaysTicketTypeManager.queryCloseSwitchTypeList();
    }

    @Override
    public List<AirCorp> getAllowAddAirways() {
        // 基础数据获取所有航司信息
        final List<AirCorp> airCorpList = BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp());
        // 获取已配置航司出票类型的航司
        final List<JdjpAirwaysTicketType> ticketTypes = selectAll();
        // 需要去掉的航司
        final ArrayList<Object> subAirCorpList = new ArrayList<>();
        OUT:
        for (AirCorp airCorp : airCorpList) {
            for (JdjpAirwaysTicketType type : ticketTypes) {
                if (airCorp.getCode().equalsIgnoreCase(type.getAirways())) {
                    subAirCorpList.add(airCorp);
                    continue OUT;
                }
            }
        }
        // 将已配置的航司去掉
        airCorpList.removeAll(subAirCorpList);
        return airCorpList;
    }
}
