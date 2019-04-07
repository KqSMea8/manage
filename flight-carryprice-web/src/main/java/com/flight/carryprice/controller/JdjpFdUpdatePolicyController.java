package com.flight.carryprice.controller;

import com.flight.carryprice.common.enums.SyncStatusEnum;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.flight.carryprice.service.JdjpFdUpdatePolicyService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.client.service.BaseDataServiceClient;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.aircorp.AirCorpQueryRequest;
import com.jd.airplane.flight.base.vo.aircorp.AirCorpQueryResponse;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.airline.AirlineQueryRequest;
import com.jd.airplane.flight.base.vo.airline.AirlineQueryResponse;
import com.jd.airplane.flight.base.vo.airport.Airport;
import com.jd.airplane.flight.base.vo.airport.AirportQueryRequest;
import com.jd.airplane.flight.base.vo.airport.AirportQueryResponse;
import com.jd.airplane.infra.response.AirResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description Fd运价更新策略
 * @date 2019/2/28 14:03
 * @updateTime
 */
@Controller
@RequestMapping("/fd/fdUpdatePolicy")
public class JdjpFdUpdatePolicyController extends BaseController {

    @Resource
    private JdjpFdUpdatePolicyService jdjpFdUpdatePolicyService;

    /**
     * 运价跟新策略列表
     *
     * @return
     */
    @RequestMapping("/getFdUpdatePolicyList")
    public String getFdUpdatePolicyList(HttpServletRequest req, ModelMap model, JdjpFdUpdatePolicy queryBean) {
        PageInfo<JdjpFdUpdatePolicy> pageInfo = jdjpFdUpdatePolicyService.pagination(getPageIndex(req), getPageSize(req),
                queryBean);

        //查询机场信息和航司信息
        model.put("pageInfo", pageInfo);
        model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("queryBean", queryBean);
        return "fd/fdUpdatePolicy/fdUpdatePolicyList";
    }

    /**
     * 转到添加fd运价更新策略
     *
     * @return
     */
    @RequestMapping("/toAddFdUpdatePolicy")
    public String toAddFdUpdatePolicy(HttpServletRequest req, ModelMap model) {
        model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        return "fd/fdUpdatePolicy/addFdUpdatePolicy";
    }

    /**
     * 执行添加fd运价更新策略
     * @return 1-成功，0-失败
     */
    @RequestMapping("/doAddFdUpdatePolicy")
    @ResponseBody
    public Object doAddFdUpdatePolicy(HttpSession session, String jsonStr) {
        Integer flag = 1;
        try{
            JdjpFdUpdatePolicy jdjpFdUpdatePolicy = JacksonUtil.json2Obj(jsonStr, JdjpFdUpdatePolicy.class); // json转对象
            if (jdjpFdUpdatePolicy == null) { // 转对象失败
                return flag;
            }
            String operator = getCurrentUser(session).getUsername();
            jdjpFdUpdatePolicy.setSyncStatus(new Byte(SyncStatusEnum.WAITING.getCode()));
            jdjpFdUpdatePolicy.setPlanQuartzTime(DateUtil.stringToDate(jdjpFdUpdatePolicy.getPlanTime(), DateUtil.DATE_FMT3));
            jdjpFdUpdatePolicy.setRemark(SyncStatusEnum.WAITING.getDesc());
            jdjpFdUpdatePolicy.setOperator(operator);

            flag = jdjpFdUpdatePolicyService.insertFdUpdatePolicy(jdjpFdUpdatePolicy);
        }catch (Exception e){
            LOGGER.error("新增数据出错", e);
            throw new RuntimeException("新增数据出错");
        }
        return flag;
    }

    @RequestMapping("/deletefdUpdatePolicy")
    @ResponseBody
    public Object deletefdUpdatePolicy( Integer id) {
        return jdjpFdUpdatePolicyService.deleteByPrimaryKey(id);
    }

}
