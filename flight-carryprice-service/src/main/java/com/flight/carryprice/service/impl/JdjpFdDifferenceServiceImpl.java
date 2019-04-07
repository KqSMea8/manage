package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.ExcelUtil;
import com.flight.carryprice.entity.JdjpFdDifference;
import com.flight.carryprice.manager.JdjpFdDifferenceManager;
import com.flight.carryprice.service.JdjpFdDifferenceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价缓存和数据库差异service层
 * @date 2019/3/07 14:15
 * @updateTime
 */
@Service
public class JdjpFdDifferenceServiceImpl extends BaseServiceImpl<JdjpFdDifference> implements JdjpFdDifferenceService {

    private final static Logger LOGGER = Logger.getLogger(JdjpFdDifferenceServiceImpl.class);

    @Resource
    private JdjpFdDifferenceManager jdjpFdDifferenceManager;

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @Override
    public PageInfo<JdjpFdDifference> pagination(Integer pageIndex, Integer pageSize, JdjpFdDifference queryBean) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpFdDifference> list = jdjpFdDifferenceManager.queryList(queryBean);
        return new PageInfo<JdjpFdDifference>(list);
    }

    /**
     * 获取版本号列表
     * @param
     * @return
     */
    @Override
    public List<String> getVersionNumList(){
        return jdjpFdDifferenceManager.getVersionNumList();
    }

    /**
     * 功能描述: 下载数据
     * @param:
     * @return:
     */
    @Override
    public void downLoadExcel(HttpServletRequest req, HttpServletResponse res, JdjpFdDifference queryBean) {
        List<JdjpFdDifference> fdDifferenceList = jdjpFdDifferenceManager.queryList(queryBean);
        String title = "FD运价库数据差异报表";
        String filename = title + DateUtil.dateToString(new Date(), DateUtil.DATE_TIME5) + ".xls";
        // 附件信息
        HSSFWorkbook book = this.getDownExcell(fdDifferenceList, title);
        ExcelUtil.export(filename, book, req, res);
    }

    /**
     *
     * @param fdDifferenceList
     * @param title
     * @return
     */
    private HSSFWorkbook getDownExcell(List<JdjpFdDifference> fdDifferenceList, String title) {
        List<LinkedList<String>> databaseList = new ArrayList<>();
        List<LinkedList<String>> redisList = new ArrayList<>();
        List<LinkedList<Boolean>> booleanList = new ArrayList<>();
        //拆分数据库信息，放置在不同的集合
        for (JdjpFdDifference difference : fdDifferenceList) {
            //数据库信息集合
            String airWays = fieldToString(difference.getAirWays());
            String depCode = fieldToString(difference.getDepCode());
            String arrCode = fieldToString(difference.getArrCode());
            String seat = fieldToString(difference.getSeat());
            String carryPrice = fieldToString(difference.getCarryPrice());
            String distance = fieldToString(difference.getDistance());
            String source = fieldToString(difference.getSource());
            String takeOffEffectDate = dateToString(difference.getTakeOffEffectDate(),DateUtil.DATE_FMT1);
            String takeOffExpireDate = dateToString(difference.getTakeOffExpireDate(),DateUtil.DATE_FMT1);
            String airlineType = fieldToString(difference.getAirlineType());
            String seatType = fieldToString(difference.getSeatType());
            String seatLevel = fieldToString(difference.getSeatLevel());
            String discount = fieldToString(difference.getDiscount());
            String versionNum = fieldToString(difference.getVersionNum());

            String airWaysRedis = fieldToString(difference.getAirWaysRedis());
            String depCodeRedis = fieldToString(difference.getDepCodeRedis());
            String arrCodeRedis = fieldToString(difference.getArrCodeRedis());
            String seatRedis = fieldToString(difference.getSeatRedis());
            String carryPriceRedis = fieldToString(difference.getCarryPriceRedis());
            String distanceRedis = fieldToString(difference.getDistanceRedis());
            String sourceRedis = fieldToString(difference.getSourceRedis());
            String takeOffEffectDateRedis = dateToString(difference.getTakeOffEffectDateRedis(),DateUtil.DATE_FMT1);
            String takeOffExpireDateRedis = dateToString(difference.getTakeOffExpireDateRedis(),DateUtil.DATE_FMT1);
            String airlineTypeRedis = fieldToString(difference.getAirlineTypeRedis());
            String seatTypeRedis = fieldToString(difference.getSeatTypeRedis());
            String seatLevelRedis = fieldToString(difference.getSeatLevelRedis());
            String discountRedis = fieldToString(difference.getDiscountRedis());

            LinkedList<String> linkedListDatabase = new LinkedList<>();
            linkedListDatabase.add(airWays);
            linkedListDatabase.add(depCode);
            linkedListDatabase.add(arrCode);
            linkedListDatabase.add(seat);
            linkedListDatabase.add(carryPrice);
            linkedListDatabase.add(distance);
            linkedListDatabase.add(source);
            linkedListDatabase.add(takeOffEffectDate);
            linkedListDatabase.add(takeOffExpireDate);
            linkedListDatabase.add(airlineType);
            linkedListDatabase.add(seatType);
            linkedListDatabase.add(seatLevel);
            linkedListDatabase.add(discount);
            linkedListDatabase.add(versionNum);
            databaseList.add(linkedListDatabase);

            //Redis信息集合
            LinkedList<String> linkedListRedis = new LinkedList<>();
            linkedListRedis.add(airWaysRedis);
            linkedListRedis.add(depCodeRedis);
            linkedListRedis.add(arrCodeRedis);
            linkedListRedis.add(seatRedis);
            linkedListRedis.add(carryPriceRedis);
            linkedListRedis.add(distanceRedis);
            linkedListRedis.add(sourceRedis);
            linkedListRedis.add(takeOffEffectDateRedis);
            linkedListRedis.add(takeOffExpireDateRedis);
            linkedListRedis.add(airlineTypeRedis);
            linkedListRedis.add(seatTypeRedis);
            linkedListRedis.add(seatLevelRedis);
            linkedListRedis.add(discountRedis);
            linkedListRedis.add(versionNum);
            redisList.add(linkedListRedis);

            //放置比较结果信息
            LinkedList<Boolean> linkedListBoolean = new LinkedList<>();
            linkedListBoolean.add(airWays.equals(airWaysRedis));
            linkedListBoolean.add(depCode.equals(depCodeRedis));
            linkedListBoolean.add(arrCode.equals(arrCodeRedis));
            linkedListBoolean.add(seat.equals(seatRedis));
            linkedListBoolean.add(carryPrice.equals(carryPriceRedis));
            linkedListBoolean.add(distance.equals(distanceRedis));
            linkedListBoolean.add(source.equals(sourceRedis));
            linkedListBoolean.add(takeOffEffectDate.equals(takeOffEffectDateRedis));
            linkedListBoolean.add(takeOffExpireDate.equals(takeOffExpireDateRedis));
            linkedListBoolean.add(airlineType.equals(airlineTypeRedis));
            linkedListBoolean.add(seatType.equals(seatTypeRedis));
            linkedListBoolean.add(seatLevel.equals(seatLevelRedis));
            linkedListBoolean.add(discount.equals(discountRedis));
            linkedListBoolean.add(true);
            booleanList.add(linkedListBoolean);

        }

        String[] secondTitles = { "序号", "来源 ", "航空公司 ", "出发机场 ", "到达机场 ", "舱位 ", "价格", "里程", "数据来源", "开始日期",
                "结束日期", "航线类型","舱位类型", "舱位等级", "折扣 ", "扫描版本 "};
        HSSFWorkbook book = ExcelUtil.createFdDifferenceExcel(title, secondTitles, databaseList, redisList, booleanList);
        return book;
    }

    /**
     * 功能描述: 字段转化为String
     * @param:
     * @return:
     */
    private String fieldToString(Object field){
        return String.valueOf(field == null? "" : field);
    }

    /**
     * 功能描述: 日期转String
     * @param:
     * @return:
     */
    private String dateToString(Date field, String dateFmt){
        return field == null ? "" : DateUtil.dateToString(field, dateFmt);

    }




}
