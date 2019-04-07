package com.flight.carryprice.controller;

import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.common.util.PatCarryPriceUtil;
import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import com.flight.carryprice.service.JdjpPatUpdatePolicyService;
import com.flight.carryprice.service.JdjpRunparametersService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
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
 * Date 2018/11/14 13:57.
 */
@Controller
@RequestMapping("/pat/patUpdatePolicy")
public class JdjpPatUpdatePolicyController extends BaseController{
    @Resource
    private JdjpRunparametersService jdjpRunparametersService;
    @Resource
    private JdjpPatUpdatePolicyService jdjpPatUpdatePolicyService;


    /**
     * @Author hYuan 机票供应链研发部
     * @Description 查询pat更新策略列表
     * @Date 9:17 2019/3/21
     * @Param [req, model, jdjpPatUpdatePolicy]
     * @return java.lang.String
     **/
    @RequestMapping("/patUpdatePolicyList")
    public String patUpdatePolicyList(HttpServletRequest req, ModelMap model,
                                     JdjpPatUpdatePolicy queryBean) {
        PageInfo<JdjpPatUpdatePolicy> pageInfo = jdjpPatUpdatePolicyService
                .pagination(getPageIndex(req), getPageSize(req), queryBean);
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        model.put("pageInfo", pageInfo);
        model.put("queryBean", queryBean);
        return "pat/patUpdatePolicy/patUpdatePolicyList";
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 跳转到新增页面
     * @Date 10:11 2019/3/21
     * @Param [req, model]
     * @return java.lang.String
     **/
    @RequestMapping("/toAddPatUpdatePolicy")
    public String toAddPatUpdatePolicy(ModelMap model) {
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        return "pat/patUpdatePolicy/addPatUpdatePolicy";
    }

    /**
     * @Author hYuan 机票供应链研发部
     * @Description 新增pat更新策略
     * @Date 10:18 2019/3/21
     * @Param [session, jsonStr]
     * @return java.lang.String
     **/
    @RequestMapping("/doAddPatUpdatePolicy")
    @ResponseBody
    public Object doAddPatUpdatePolicy(HttpSession session, String jsonStr) {
        JdjpPatUpdatePolicy policy = JacksonUtil.json2Obj(jsonStr, JdjpPatUpdatePolicy.class);
        if (policy == null){
            return -1;
        }
        Map<String, Object> map = JacksonUtil.json2Obj(jsonStr, Map.class);
        if (map == null){
            return -1;
        }
        policy.setDeptDate(PatCarryPriceUtil.parseDept(map));
        policy.setReserveTimeDuration(PatCarryPriceUtil.parseReserve(map));
        String userName = getCurrentUser(session).getUsername();
        policy.setOperator(userName);
        return jdjpPatUpdatePolicyService.addUpdatePolicy(policy);
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 跳转到更新页面
     * @Date 15:10 2019/3/21
     * @Param [model, id]
     * @return java.lang.String
     **/
    @RequestMapping("/toUpdatePatUpdatePolicy")
    public String toUpdatePatCarryPricePolicy(ModelMap model, Long id) {
        JdjpPatUpdatePolicy jdjpPatUpdatePolicy = jdjpPatUpdatePolicyService.selectByPrimaryKey(id);
        model.put("jdjpPatUpdatePolicy", jdjpPatUpdatePolicy);
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("airlineType", jdjpRunparametersService.queryParamByName("AIRLINE_TYPE"));
        String reserveTimeDuration = jdjpPatUpdatePolicy.getReserveTimeDuration();
        String deptDate = jdjpPatUpdatePolicy.getDeptDate();
        //reserveTimeDuration 格式 1~2
        model.put("reserveTimeDayFrom", reserveTimeDuration.split("~")[0]);
        model.put("reserveTimeDayTo", reserveTimeDuration.split("~")[1]);
        //deptDate 格式 T+1~T+2
        model.put("deptDateFrom",deptDate.split("~")[0].substring(2));
        model.put("deptDateTo",deptDate.split("~")[1].substring(2));
        return "pat/patUpdatePolicy/updatePatPolicy";
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 更新入库
     * @Date 13:47 2019/3/21
     * @Param [session, jsonStr]
     * @return java.lang.String
     **/
    @RequestMapping("/doUpdatePatUpdatePolicy")
    @ResponseBody
    public Object doUpdatePatUpdatePolicy(HttpSession session, String jsonStr) {
        JdjpPatUpdatePolicy policy = JacksonUtil.json2Obj(jsonStr, JdjpPatUpdatePolicy.class);
        if (policy == null){
            return -1;
        }
        String userName = getCurrentUser(session).getUsername();
        policy.setPlanQuartzTime(DateUtil.stringToDate(policy.getPlanTime(), DateUtil.DATE_FMT3));
        policy.setOperator(userName);
        return jdjpPatUpdatePolicyService.updateByPrimaryKeySelective(policy);
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 删除pat运价更新策略
     * @Date 15:27 2019/3/21
     * @Param [id]
     * @return int
     **/
    @RequestMapping("/deletePatUpdatePolicy")
    @ResponseBody
    public int deletePatUpdateConfig(long id) {
        return jdjpPatUpdatePolicyService.deleteByPrimaryKey(id);
    }

}
