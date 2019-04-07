package com.flight.carryprice.controller;

import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.PatCarryPriceUtil;
import com.flight.carryprice.entity.JdjpPatFlightSeatConfig;
import com.flight.carryprice.service.JdjpPatFlightSeatService;
import com.flight.carryprice.service.JdjpRunparametersService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Author wanghaiyuan
 * Date 2019/03/19 14:42.
 * pat运价航班舱位配置
 */
@Controller
@RequestMapping("/pat/patSeatConfig")
public class JdjpPatFlightSeatController extends BaseController{
    @Resource
    private JdjpPatFlightSeatService jdjpPatFlightSeatService;
    @Resource
    private JdjpRunparametersService jdjpRunparametersService;

    /**
     * @Author hYuan 机票供应链研发部
     * @Description pat运价配置列表
     * @Date 9:52 2019/3/20
     * @Param [req, model, queryBean]
     * @return java.lang.String
     **/
    @RequestMapping("/patFlightSeatList")
    public String nfdCarryPriceAList(HttpServletRequest req, ModelMap model,
                                     JdjpPatFlightSeatConfig queryBean) {
        PageInfo<JdjpPatFlightSeatConfig> pageInfo = jdjpPatFlightSeatService
                .pagination(getPageIndex(req), getPageSize(req), queryBean);
        model.put("pageInfo", pageInfo);
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("queryBean", queryBean);
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        return "pat/patSeatConfig/patFlightSeatList";
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description pat航班舱位配置 跳转到新增页面；
     * @Date 9:52 2019/3/20
     * @Param [req, model]
     * @return java.lang.String
     **/
    @RequestMapping("/toAddPatFlightSeat")
    public String toAddPatCarryPrice(ModelMap model) {
        // 航班类型
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        return "pat/patSeatConfig/addPatFlightSeat";
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 新增航班舱位配置
     * @Date 9:51 2019/3/20
     * @Param [session, jsonStr]
     * @return java.lang.String
     **/
    @RequestMapping("/doAddPatSeatFlight")
    @ResponseBody
    public Object doAddPatCarryPriceConfig(HttpSession session, String jsonStr) {
        LOGGER.info("新增pat航班舱位，接受到jsonStr = " + jsonStr);
        JdjpPatFlightSeatConfig jdjpPatconfig = JacksonUtil.json2Obj(jsonStr, JdjpPatFlightSeatConfig.class);
        if (jdjpPatconfig == null){
            return 0;
        }
        Map<String, Object> map = JacksonUtil.json2Obj(jsonStr, Map.class);
        if (map == null){
            return 0;
        }
        String userName = getCurrentUser(session).getUsername();
        jdjpPatconfig.setDeptDate(PatCarryPriceUtil.parseDept(map));
        jdjpPatconfig.setReserveTimeDuration(PatCarryPriceUtil.parseReserve(map));
        jdjpPatconfig.setOperator(userName);
        try {
            return jdjpPatFlightSeatService.addFlightSeatConfig(jdjpPatconfig);
        }catch (DuplicateKeyException e1){
            LOGGER.error("插入PAT航班舱位配置错误", e1);
            return "重复的 航司-出发-到达-航班-舱位 数据";
        }catch (Exception e){
            LOGGER.error("插入PAT航班舱位配置错误", e);
            return 0;
        }
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 跳转到更新页面
     * @Date 10:55 2019/3/20
     * @Param [model, id]
     * @return java.lang.String
     **/
    @RequestMapping("/toUpdatePatFlightSeat")
    public String toUpdatePatCarryPriceConfig(ModelMap model, Long id) {
        JdjpPatFlightSeatConfig queryBean =  jdjpPatFlightSeatService.selectByPrimaryKey(id);
        model.put("queryBean", queryBean);
        // pat航班类型
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        String reserveTimeDuration = queryBean.getReserveTimeDuration();
        String deptDate = queryBean.getDeptDate();
        //reserveTimeDuration 格式 1~2
        model.put("reserveTimeDayFrom", reserveTimeDuration.split("~")[0]);
        model.put("reserveTimeDayTo", reserveTimeDuration.split("~")[1]);
        //deptDate 格式 T+1~T+2
        model.put("deptDateFrom",deptDate.split("~")[0].substring(2));
        model.put("deptDateTo",deptDate.split("~")[1].substring(2));
        return "pat/patSeatConfig/updatePatFlightSeat";
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 更新pat运价航班舱位配置到数据库
     * @Date 10:56 2019/3/20
     * @Param [session, jsonStr]
     * @return java.lang.String
     **/
    @RequestMapping("/doUpdatePatFlightSeat")
    @ResponseBody
    public Object doUpdatePatCarryPriceConfig(HttpSession session, String jsonStr) {
        LOGGER.info("更新pat航班舱位，接受到jsonStr = " + jsonStr);
        JdjpPatFlightSeatConfig jdjpSeatConfig = JacksonUtil.json2Obj(jsonStr, JdjpPatFlightSeatConfig.class);
        if (jdjpSeatConfig == null){
            return 0;
        }
        Map<String, Object> map = JacksonUtil.json2Obj(jsonStr, Map.class);
        if (map == null){
            return 0;
        }
        String userName = getCurrentUser(session).getUsername();
        jdjpSeatConfig.setOperator(userName);
        jdjpSeatConfig.setDeptDate(PatCarryPriceUtil.parseDept(map));
        jdjpSeatConfig.setReserveTimeDuration(PatCarryPriceUtil.parseReserve(map));
        return jdjpPatFlightSeatService.updateByPrimaryKeySelective(jdjpSeatConfig);
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 根据id 单条删除PAT航班舱位配置信息
     * @Date 15:17 2019/3/20
     * @Param [id]
     * @return int
     **/
    @RequestMapping("/deletePatFlightSeat")
    @ResponseBody
    public int deletePatCarryPriceConfig(long id) {
        return jdjpPatFlightSeatService.deleteByPrimaryKey(id);
    }
}
