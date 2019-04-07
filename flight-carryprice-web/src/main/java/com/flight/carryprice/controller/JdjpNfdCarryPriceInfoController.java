package com.flight.carryprice.controller;

import com.alibaba.fastjson.JSONObject;
import com.flight.carryprice.common.enums.SeatLevelEnum;
import com.flight.carryprice.common.enums.SeatTypeEnum;
import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import com.flight.carryprice.service.JdjpNfdCarryPriceInfoService;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nfd/nfdCarryPriceInfo")
public class JdjpNfdCarryPriceInfoController extends BaseController {

    @Resource
    private JdjpNfdCarryPriceInfoService jdjpNfdCarryPriceInfoService;

    /**
     * nfd运价列表
     *
     * @return
     */
    @RequestMapping("/nfdCarryPriceInfoList")
    public String nfdCarryPriceAList(HttpServletRequest req, ModelMap model,
                                     JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo) {
        PageInfo<JdjpNfdCarryPriceInfo> pageInfo = jdjpNfdCarryPriceInfoService
                .pagination(getPageIndex(req), getPageSize(req), jdjpNfdCarryPriceInfo);
        model.put("pageInfo", pageInfo);
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("queryBean", jdjpNfdCarryPriceInfo);
        return "nfd/nfdCarryPriceInfo/nfdCarryPriceInfoList";
    }

    /**
     * 转到添加nfd运价页面
     *
     * @return
     */
    @RequestMapping("/toAddNfdCarryPriceInfo")
    public String toAddNfdCarryPriceA(HttpServletRequest req, ModelMap model) {
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        return "nfd/nfdCarryPriceInfo/addNfdCarryPriceInfo";
    }

    /**
     * 执行添加nfd运价页面
     * 0 -1添加失败 1 添加成功
     * @return
     */

    @RequestMapping("/doAddNfdCarryPriceInfo")
    @ResponseBody
    public Object doAddNfdCarryPriceA(HttpSession session, String jsonStr) {
        int flag = 0;
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo = parseJsonTObj(jsonObject);
            if (jdjpNfdCarryPriceInfo == null){
                return -1;
            }
            jdjpNfdCarryPriceInfo.setOperator(getCurrentUser(session).getUsername());
            if (jdjpNfdCarryPriceInfoService.insetbatchNfdCarryPrice(jdjpNfdCarryPriceInfo) > 0){
                flag = 1;
            }
        } catch (Exception e) {
            LOGGER.error("新增nfd运价信息异常", e);
            throw new SystemException("新增nfd运价信息异常");
        }
        return flag;
    }
    /**
     * 转到编辑运价
     *
     * @return
     */
    @RequestMapping("/toUpdateNfdCarryPriceInfo")
    public String toUpdateFdUpdatePolicy(HttpServletRequest req,
                                         ModelMap model, @RequestParam Long id) {
        JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo = jdjpNfdCarryPriceInfoService.selectByPrimaryKey(id);
        List<String> scheduleList = new ArrayList<>();
        if (jdjpNfdCarryPriceInfo.getSchedules() != null) {
            String[] schedules = jdjpNfdCarryPriceInfo.getSchedules().split(",");
            for (int i = 0; i < schedules.length; i++) {
                scheduleList.add(schedules[i]);
            }
        }
        //截取限制航班时间，前台展示
        List<String> limit1 = new ArrayList<String>();
        List<String> limit2 = new ArrayList<String>();

        if (jdjpNfdCarryPriceInfo.getFlightTimeLimit() != null) {
            String[] splitLimit = jdjpNfdCarryPriceInfo.getFlightTimeLimit().split(",");
            for (String limit : splitLimit) {
                String[] split = limit.split("-");
                limit1.add(split[0]);
                limit2.add(split[1]);
            }
        }
        model.put("limit1", limit1);
        model.put("limit2", limit2);
        model.put("company", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airport", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("scheduleList", scheduleList);
        model.addAttribute("queryBean", jdjpNfdCarryPriceInfo);
        return "nfd/nfdCarryPriceInfo/updateNfdCarryPrice";
    }
    /**
     * 新增nfd运价
     *
     * @return
     */
    @RequestMapping("/doUpdateNfdCarryPrice")
    @ResponseBody
    public Object doUpdateNfdCarryPriceA(HttpSession session,
                                         String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo = parseJsonTObj(jsonObject);
            if (jdjpNfdCarryPriceInfo == null){
                return -1;
            }
            jdjpNfdCarryPriceInfo.setOperator(getCurrentUser(session).getUsername());
            return jdjpNfdCarryPriceInfoService.updateNfdCarryPrice(jdjpNfdCarryPriceInfo);
        } catch (Exception e) {
            LOGGER.error("修改NFD运价信息异常", e);
            throw new SystemException("修改NFD运价信息异常");
        }
    }
    /**
     * 批量启用
     *
     * @return
     */
    @RequestMapping("/isShow")
    @ResponseBody
    public Object isShow(HttpSession session, Integer[] nfdIds) {
        if (jdjpNfdCarryPriceInfoService.updateBatch(getCurrentUser(session).getUsername(), Byte.valueOf(StateEnum.VALID.getCode()), nfdIds) > 0){
            return 1;
        }
        return -1;
    }
    /**
     * 批量停用
     *
     * @return
     */
    @RequestMapping("/isHide")
    @ResponseBody
    public Object isHide(HttpSession session, Integer[] nfdIds) {
        if (jdjpNfdCarryPriceInfoService.updateBatch(getCurrentUser(session).getUsername(), Byte.valueOf(StateEnum.INVALID.getCode()), nfdIds) > 0){
            return 1;
        }
        return -1;
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 处理页面传的·jsonobj为JdjpNfdCarryPriceInfo
     * @Date 21:55 2019/3/17
     * @Param [jsonObject]
     **/
    private JdjpNfdCarryPriceInfo parseJsonTObj(JSONObject jsonObject){
        //转换前台来的限制时间
        String[] limit1 = getLimitTime("flightTimeLimit1", jsonObject);
        String[] limit2 = getLimitTime("flightTimeLimit2", jsonObject);
        String limitTimeResult = copyLimitTimeString(limit1, limit2);
        //去除多余字段，方便转换实体
        JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo = JacksonUtil.json2Obj(jsonObject.toJSONString(), JdjpNfdCarryPriceInfo.class);
        if (jdjpNfdCarryPriceInfo == null) {
            return null;
        }
        //放置限制时间
        jdjpNfdCarryPriceInfo.setFlightTimeLimit(limitTimeResult);
        return jdjpNfdCarryPriceInfo;
    }

    /**
     * 获取该航空公司的舱位列表
     *
     * @param airways
     * @return
     */
    @RequestMapping("/getSeatList")
    @ResponseBody
    public String getSeatList(@RequestParam String airways) {
        try {
            Cabin cabin = new Cabin();
            cabin.setAirCorpCode(airways);
            List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, cabin);
            JSONObject result = new JSONObject();
            result.put("list", cabinList);
            return result.toString();
        } catch (Exception e) {
            LOGGER.error("获取舱位信息异常", e);
        }
        return null;
    }

    /**
     * 前端的字符数组进行格式化处理
     * @param name
     * @param jsonObject
     * @return
     */
    private String[] getLimitTime(String name, JSONObject jsonObject){
        Object flightTimeLimit = jsonObject.get(name);
        String string1 = flightTimeLimit.toString().replace(" ", "");
        string1 = string1.replace("[", "");
        string1 = string1.replace("]", "");
        string1 = string1.replace("\"", "");
        return string1.split(",");
    }

    /**
     * 去程时刻限制时间区间拼接为2301-2309，2312-2359的字符串
     * @param limit1
     * @param limit2
     * @return
     */
    private String copyLimitTimeString(String[] limit1, String[] limit2){
        StringBuffer limitTimeSb = new StringBuffer();
        for (int i = 0; i < limit1.length; i++) {
            limitTimeSb.append(limit1[i]);
            limitTimeSb.append("-");
            limitTimeSb.append(limit2[i]);
            limitTimeSb.append(",");
        }
        return limitTimeSb.deleteCharAt(limitTimeSb.length() - 1).toString();
    }


}
