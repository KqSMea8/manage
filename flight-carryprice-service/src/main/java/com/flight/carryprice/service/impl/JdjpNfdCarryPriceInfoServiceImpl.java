package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.entity.JdjpNfdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpNfdCarryPriceInfoManager;
import com.flight.carryprice.service.JdjpNfdCarryPriceInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author wanghaiyuan
 * Date 2019/3/14 10:39.
 */
@Service
public class JdjpNfdCarryPriceInfoServiceImpl extends BaseServiceImpl<JdjpNfdCarryPriceInfo> implements JdjpNfdCarryPriceInfoService {
    private static Logger LOGGER = Logger.getLogger(JdjpNfdCarryPriceInfoServiceImpl.class);

    @Resource
    private JdjpNfdCarryPriceInfoManager jdjpNfdCarryPriceInfoManager;
    @Override
    public PageInfo<JdjpNfdCarryPriceInfo> pagination(Integer pageIndex, Integer pageSize, JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpNfdCarryPriceInfo> list = jdjpNfdCarryPriceInfoManager.queryList(jdjpNfdCarryPriceInfo);
        return new PageInfo<>(list);
    }

    @Override
    public int insetbatchNfdCarryPrice(JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo) {
        int insertResult = 0;
        String[] seatList = jdjpNfdCarryPriceInfo.getSeat().split(",");
        String airWays = jdjpNfdCarryPriceInfo.getAirWays();
        //根据仓位逐条插入
        for (String seat : seatList) {
            Cabin cabin = new Cabin();
            cabin.setAirCorpCode(airWays);
            cabin.setAdultCode(seat);
            List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, cabin);
            if (CollectionUtils.isEmpty(cabinList)){
                continue;
            }
            cabin = cabinList.get(0);
            Double discount = cabin.getDiscount();
            if (null == discount){
                continue;
            }
            jdjpNfdCarryPriceInfo.setDiscount(BigDecimal.valueOf(discount));
            jdjpNfdCarryPriceInfo.setSeat(seat);
            //seatLevel暂时以页面填的为准
            //jdjpNfdCarryPriceInfo.setSeatLevel(cabin.getLevel());
            jdjpNfdCarryPriceInfo.setSeatType(cabin.getType().byteValue());
            String priceLevel = jdjpNfdCarryPriceInfo.getPriceLevel();
            jdjpNfdCarryPriceInfo.setPriceLevelSeat(getPriceLevelSeat(seat, priceLevel));
            jdjpNfdCarryPriceInfo.setId(null);
            int result = super.insertSelective(jdjpNfdCarryPriceInfo);
            insertResult+=result;
        }
        LOGGER.info("NFD运价插入成功条数：" + insertResult);
        return insertResult;
    }

    @Override
    public int updateNfdCarryPrice(JdjpNfdCarryPriceInfo jdjpNfdCarryPriceInfo) {
        Cabin cabin = new Cabin();
        cabin.setAirCorpCode(jdjpNfdCarryPriceInfo.getAirWays());
        cabin.setAdultCode(jdjpNfdCarryPriceInfo.getSeat());
        List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, cabin);
        if (CollectionUtils.isEmpty(cabinList)){
            return -1;
        }
        cabin = cabinList.get(0);
        jdjpNfdCarryPriceInfo.setDiscount(BigDecimal.valueOf(cabin.getDiscount()));
        //jdjpNfdCarryPriceInfo.setSeatLevel(cabin.getLevel());
        jdjpNfdCarryPriceInfo.setSeatType(cabin.getType().byteValue());
        String priceLevel = jdjpNfdCarryPriceInfo.getPriceLevel();
        jdjpNfdCarryPriceInfo.setPriceLevelSeat(getPriceLevelSeat(jdjpNfdCarryPriceInfo.getSeat(), priceLevel));
        return super.updateByPrimaryKeySelective(jdjpNfdCarryPriceInfo);
    }

    @Override
    public int updateBatch(String operator, Byte state, Integer[] ids) {
        return jdjpNfdCarryPriceInfoManager.updateBatch(operator, state, ids);
    }

    /**
     * @Author hYuan 机票供应链研发部
     * @Description 转换pricelevelseat
     * @Date 9:08 2019/3/19
     * @Param [seat, priceLevel]
     * @return java.lang.String
     **/
    private String getPriceLevelSeat(String seat, String priceLevel){
        if (StringUtils.isNotBlank(priceLevel)) {
            if (priceLevel.length() == 2) {
                final Pattern p = Pattern.compile("^" + seat + "[0-9]$");
                Matcher m = p.matcher(priceLevel);
                if (m.matches()) {
                    return priceLevel;
                }
            }
        }
        return seat;
    }
}
