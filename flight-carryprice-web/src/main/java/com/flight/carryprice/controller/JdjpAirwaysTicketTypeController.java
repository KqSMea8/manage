package com.flight.carryprice.controller;

import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpAirwaysTicketType;
import com.flight.carryprice.service.JdjpAirwaysTicketTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @Description 航司出票类型管理
 * @Author: qinhaoran1
 * @Date: 2019/3/19
 */
@Controller
@RequestMapping("/basedata/airwaysTicketType")
public class JdjpAirwaysTicketTypeController extends BaseController {

    private static Logger LOGGER = Logger.getLogger(JdjpAirwaysTicketTypeController.class);

    @Resource
    private JdjpAirwaysTicketTypeService jdjpAirwaysTicketTypeService;


    /**
     * 航司出票类型列表
     */
    @RequestMapping("/airwaysTicketTypeList")
    public String airwaysTicketTypeList(HttpServletRequest req, ModelMap model, JdjpAirwaysTicketType queryBean) {
        // 查询菜单
        Integer pageIndex = getPageIndex(req);
        Integer pageSize = getPageSize(req);
        PageInfo<JdjpAirwaysTicketType> pageInfo = new PageInfo<>();
        List<AirCorp> airCorpList = Collections.EMPTY_LIST;
        try {
            PageHelper.startPage(pageIndex, pageSize);
            if (StringUtils.isEmpty(queryBean.getAirways())) {
                queryBean.setAirways(null);
            }
            // 分页查询航司出票类型
            List<JdjpAirwaysTicketType> ticketTypes = jdjpAirwaysTicketTypeService.select(queryBean);
            pageInfo = new PageInfo<>(ticketTypes);
            // 基础数据获取所有航司信息
            airCorpList = BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp());
        } catch (Exception e) {
            LOGGER.error("query airwaysTicketType error!", e);
        }
        model.put("aircompanyList", airCorpList);
        model.put("pageInfo", pageInfo);
        model.put("queryBean", queryBean);
        model.put("pageIndex", pageIndex);
        model.put("pageSize", pageSize);
        return "basedata/airwaysTicketType/airwaysTicketTypeList";
    }

    /**
     * 进入添加航司出票类型页面
     */
    @RequestMapping("/toAddAirwaysTicketType")
    public String toAddAirwaysTicketType(ModelMap model) {
        List<AirCorp> airCorpList = Collections.EMPTY_LIST;
        try {
            // 获取可配置的航司信息
            airCorpList = jdjpAirwaysTicketTypeService.getAllowAddAirways();
        } catch (Exception e) {
            LOGGER.error("to addAirwaysTicketType error!", e);
        }
        model.put("aircompanyList", airCorpList);
        return "basedata/airwaysTicketType/addAirwaysTicketType";
    }

    /**
     * 执行增加航司出票类型
     */
    @RequestMapping("/doAddAirwaysTicketType")
    @ResponseBody
    public Object doAddAirwaysTicketType(String jsonStr, HttpSession session) {
        int flag = 0;
        try {
            final JdjpAirwaysTicketType airwaysTicketType = JacksonUtil.json2Obj(jsonStr, JdjpAirwaysTicketType.class);
            airwaysTicketType.setOperator(getCurrentUser(session).getUsername());
            flag = jdjpAirwaysTicketTypeService.insertSelective(airwaysTicketType);
        } catch (Exception e) {
            LOGGER.error("do addAirwaysTicketType error!", e);
        }
        return flag;
    }

    /**
     * 执行修改航司出票类型
     */
    @RequestMapping("/doUpdateAirwaysTicketType")
    @ResponseBody
    public Object doUpdateAirwaysTicketType(
            ModelMap model, HttpSession session, HttpServletRequest req, JdjpAirwaysTicketType queryBean,
            Integer airwaysTicketTypeId, Byte swith, Byte isSpeedTicket) {
        int flag = 0;
        try {
            JdjpAirwaysTicketType airwaysTicketType = new JdjpAirwaysTicketType();
            airwaysTicketType.setId(airwaysTicketTypeId);
            airwaysTicketType.setSwith(swith);
            airwaysTicketType.setIsSpeedTicket(isSpeedTicket);
            airwaysTicketType.setOperator(getCurrentUser(session).getUsername());
            flag = jdjpAirwaysTicketTypeService.updateByPrimaryKeySelective(airwaysTicketType);
        } catch (Exception e) {
            LOGGER.error("do updateAirwaysTicketType error!", e);
        }
        return flag;
    }
}
