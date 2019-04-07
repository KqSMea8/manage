package com.flight.carryprice.manager.impl;

import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.manager.JdjpAirwaysTicketTypeManager;
import com.flight.carryprice.mapper.JdjpAirwaysTicketTypeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author wanghaiyuan
 * Date 2019/3/13 16:32.
 */
@Service
public class JdjpAirwaysTicketTypeManagerImpl implements JdjpAirwaysTicketTypeManager {

    @Resource
    private JdjpAirwaysTicketTypeMapper jdjpAirwaysTicketTypeMapper;

    @Override
    public List<JdjpAirwaysTicketType> queryCloseSwitchTypeList() {
        return jdjpAirwaysTicketTypeMapper.queryCloseSwitchTypeList();
    }

    @Override
    public JdjpAirwaysTicketType queryTicketTypeByAirways(String airways) {
        return jdjpAirwaysTicketTypeMapper.queryTicketTypeByAirways(airways);
    }
}
