package com.flight.carryprice.controller;

import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoService;
import com.flight.carryprice.service.JdjpRunparametersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.aircorp.AirCorp;
import com.jd.airplane.flight.base.vo.airport.Airport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价管理
 * @date 2019/3/4 11:05
 * @updateTime
 */
@Controller
@RequestMapping("/fd/fdCarryPriceInfo")
public class JdjpFdCarryPriceInfoController extends BaseController {

    private static Logger LOGGER = Logger.getLogger(JdjpFdCarryPriceInfoController.class);

    @Resource
    private JdjpFdCarryPriceInfoService jdjpFdCarryPriceInfoService;

    @Resource
    private JdjpRunparametersService jdjpRunparametersService;


    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @RequestMapping("/getFdCarryPriceInfoList")
    public String getFdCarryPriceInfoList(HttpServletRequest req, ModelMap model, JdjpFdCarryPriceInfo queryBean) {
        // 分页查询
        PageInfo<JdjpFdCarryPriceInfo> pageInfo = jdjpFdCarryPriceInfoService
                .pagination(getPageIndex(req), getPageSize(req), queryBean);

        // 查询所有机场
        model.put("pageInfo", pageInfo);
        model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));
        model.put("queryBean", queryBean);

        return "fd/fdCarryPriceInfo/fdCarryPriceInfoList";
    }

    /**
     * 进入添加运价页面
     * @return
     */
    @RequestMapping("/toAddFdCarryPriceInfo")
    public String toAddFdCarryPriceInfo(ModelMap model) {
        // 查询航司和机场
        model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));

        return "fd/fdCarryPriceInfo/addFdCarryPriceInfo";
    }

    /**
     * 执行添加运价信息
     * @param jsonStr
     * @param seates 舱位，多个用逗号隔开
     * @param carryPrices  价格，多个用逗号隔开
     * @return
     */
    @RequestMapping("/doAddFdCarryPriceInfo")
    @ResponseBody
    public Object doAddFdCarryPriceInfo(HttpSession session, String jsonStr, String seates, String carryPrices) {
        LOGGER.info("FD新增==seates："+seates + "  carryPrices："+carryPrices + " jsonStr:"+jsonStr);
        int flag = 0;
        try {
            JdjpFdCarryPriceInfo carryPriceInfo = JacksonUtil.json2Obj(jsonStr, JdjpFdCarryPriceInfo.class); // json转对象
            if (carryPriceInfo == null) { // 转对象失败
                return "-1";
            }
            carryPriceInfo.setOperator(getCurrentUser(session).getUsername());

            String[] seatesArr = seates.split(",");
            String[] carryPriceArr = carryPrices.split(",");
            // 没有舱位信息或没有价格信息或舱位信息与价格信息不对等
            if (seatesArr.length == 0 || carryPriceArr.length == 0 || seatesArr.length != carryPriceArr.length) {
                return "-2";
            }
            flag = jdjpFdCarryPriceInfoService.addFdCarryPriceInfo(carryPriceInfo, seatesArr, carryPriceArr); // 插入
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException("新增FD运价信息异常");
        }
        return flag;
    }

    /**
     * 进入修改全价信息的页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateFdCarryPriceInfo")
    public String toUpdateFdCarryPriceInfo(ModelMap model, Integer id) {
        // 查询要修改的全价信息
        JdjpFdCarryPriceInfo fdCarryPriceInfo = jdjpFdCarryPriceInfoService.selectByPrimaryKey(id);

        model.put("fdCarryPriceInfo", fdCarryPriceInfo);
        model.put("aircorpList", BaseDataUtils.getAirCorpList(QueryTypeEnum.ALL, new AirCorp()));
        model.put("airportList", BaseDataUtils.getAirportList(QueryTypeEnum.ALL, new Airport()));

        return "fd/fdCarryPriceInfo/updateFdCarryPriceInfo";
    }

    /**
     * 执行修改全价信息
     * @return
     */
    @RequestMapping("/doUpdateFdCarryPriceInfo")
    @ResponseBody
    public Object doUpdateFdCarryPriceInfo(HttpSession session, String jsonStr) {
        int flag = 0;
        try {
            JdjpFdCarryPriceInfo carryPriceInfo = JacksonUtil.json2Obj(jsonStr, JdjpFdCarryPriceInfo.class); // json转对象
            //大写转换
            carryPriceInfo.setSeat(carryPriceInfo.getSeat().toUpperCase());
            carryPriceInfo.setOperator(getCurrentUser(session).getUsername());
            carryPriceInfo.setUpdateTime(new Date());
            flag = jdjpFdCarryPriceInfoService.updateFdCarryPriceInfo(carryPriceInfo);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException("修改运价信息异常");
        }
        return flag;
    }

    /**
     * 进入比较运价信息的页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/toCompareFdCarryPriceInfo")
    public String toCompareFdCarryPriceInfo(ModelMap model, Integer id) {
        // 查询数据库中的运价信息
        JdjpFdCarryPriceInfo fdCarryPriceInfo = jdjpFdCarryPriceInfoService.selectByPrimaryKey(id);
        JdjpFdCarryPriceInfo redisFdCarryPriceInfo = null;
        if(fdCarryPriceInfo != null){
            //查询缓存中的FD运价
            redisFdCarryPriceInfo = jdjpFdCarryPriceInfoService.getRedisFdCarryPriceInfo(fdCarryPriceInfo);
        }
        Map<String, String> seatTypeMap = jdjpRunparametersService.getSeatTypeMap();
        model.put("seatTypeMap", seatTypeMap);
        model.put("fdCarryPriceInfo", fdCarryPriceInfo);
        model.put("redisFdCarryPriceInfo", redisFdCarryPriceInfo);
        return "fd/fdCarryPriceInfo/compareFdCarryPriceInfo";
    }

    /**
     * 批量启用
     * @return
     */
    @RequestMapping("/isShow")
    @ResponseBody
    public Object isShow(HttpSession session, String carrypriceIds) {
        int flag = 1;
        try {
            String operator = getCurrentUser(session).getUsername();
            boolean result = jdjpFdCarryPriceInfoService.isShow(operator, carrypriceIds);
            if (!result) {
                LOGGER.error("批量启用运价管理失败");
                flag = 0;
            }
        } catch (Exception e) {
            LOGGER.error("批量启用运价管理异常", e);
            throw new RuntimeException("批量启用运价管理异常");
        }
        return flag;
    }

    /**
     * 批量停用
     * @return
     */
    @RequestMapping("/isHide")
    @ResponseBody
    public Object isHide(HttpSession session, String carrypriceIds) {
        int flag = 1;
        try {
            String operator = getCurrentUser(session).getUsername();
            boolean result = jdjpFdCarryPriceInfoService.isHide(operator, carrypriceIds);
            if (!result) {
                LOGGER.error("批量停用运价管理失败");
                flag = 0;
            }
        } catch (Exception e) {
            LOGGER.error("批量停用运价管理异常", e);
            throw new RuntimeException("批量停用运价管理异常");
        }
        return flag;
    }

    /**
     * 功能描述: 清除FD运价缓存
     * @param: carryPriceType 运价类型(0:FD，1:NFD，2:SSD)
     * @return:
     */
    @RequestMapping("/cleanRedisForCarryPrice")
    @ResponseBody
    public String cleanRedisForCarryPrice(String airWays, String depCode, String arrCode, String seat, String carryPriceType) {
        try {
            jdjpFdCarryPriceInfoService.cleanRedisForCarryPrice(airWays, depCode, arrCode, seat, carryPriceType);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }


}
