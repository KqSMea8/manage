package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.AirlineTypeEnum;
import com.flight.carryprice.common.enums.StateEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoRedisService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.cabin.Cabin;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价Service
 * @date 2019/3/4 11:07
 * @updateTime
 */
@Service
public class JdjpFdCarryPriceInfoServiceImpl extends BaseServiceImpl<JdjpFdCarryPriceInfo> implements JdjpFdCarryPriceInfoService{

    private static Logger LOGGER = Logger.getLogger(JdjpFdCarryPriceInfoServiceImpl.class);

    @Resource
    private JdjpFdCarryPriceInfoManager jdjpFdCarryPriceInfoManager;

    @Resource
    private JdjpFdCarryPriceInfoRedisService jdjpFdCarryPriceInfoRedisService;

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @Override
    public PageInfo<JdjpFdCarryPriceInfo> pagination(Integer pageIndex, Integer pageSize, JdjpFdCarryPriceInfo queryBean) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpFdCarryPriceInfo> list = jdjpFdCarryPriceInfoManager.queryList(queryBean);
        return new PageInfo<JdjpFdCarryPriceInfo>(list);
    }

    /**
     * 新增运价
     * @param fdCarryPriceInfo
     * @param seatesArr 舱位数组
     * @param carryPriceArr  价格数组
     */
    @Transient
    @Override
    public int addFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo, String[] seatesArr, String[] carryPriceArr){

        List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList = new ArrayList<JdjpFdCarryPriceInfo>();
        List<JdjpFdCarryPriceInfo> fdCarryPriceInfoRedisList = new ArrayList<JdjpFdCarryPriceInfo>();

        // 舱位信息与价格信息是对等的
        for (int i = 0, count = seatesArr.length; i < count; i++) {
            String seat = seatesArr[i];
            String carryPrice = carryPriceArr[i];

            // 把carrypriceA的属性值拷贝到temp中,忽略carryprice属性
            JdjpFdCarryPriceInfo temp = new JdjpFdCarryPriceInfo();
            BeanUtils.copyProperties(fdCarryPriceInfo, temp, "carryPrice");
            temp.setSource((byte) 0);//0-来源FD运价 1-来源NFD运价
            temp.setSeat(seat.toUpperCase());
            temp.setCarryPrice(new BigDecimal(carryPrice));
            temp.setAirlineType(new Byte(AirlineTypeEnum.ORDINARY.getCode()));//默认航线类型 0-普通，1-热门
            temp.setCreateTime(new Date());
            temp.setUpdateTime(new Date());

            //查询出对应仓位的类型、等级、折扣
            Cabin record = new Cabin();
            record.setAirCorpCode(temp.getAirWays());
            record.setAdultCode(seat.toUpperCase());
            List<Cabin> cabinList = BaseDataUtils.getCabinList(QueryTypeEnum.UNION_KEY, record);
            if(CollectionUtils.isNotEmpty(cabinList)){
                Cabin cabin = cabinList.get(0);
                temp.setSeatType(new Byte(cabin.getType().toString()));//舱位类型
                temp.setSeatLevel(cabin.getLevel());
                temp.setDiscount(new BigDecimal(cabin.getDiscount()));
            }
            fdCarryPriceInfoList.add(temp);
            //启用状态更新缓存
            if (temp.getState() == Byte.parseByte(StateEnum.VALID.getCode())) {
                fdCarryPriceInfoRedisList.add(temp);
            }
        }
        int insertCount = jdjpFdCarryPriceInfoManager.insertBatch(fdCarryPriceInfoList); // 批量插入
        if (fdCarryPriceInfoRedisList.size() > 0) {
            jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(fdCarryPriceInfoRedisList);
        }
        return insertCount;
    }

    /**
     * 获取缓存中的FD运价
     * @param fdCarryPriceInfo
     */
    public JdjpFdCarryPriceInfo getRedisFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo){
        return jdjpFdCarryPriceInfoRedisService.getRedisFdCarryPriceInfo(fdCarryPriceInfo);
    }

    /**
     * 更新FD运价，并更新缓存
     * @param fdCarryPriceInfo
     */
    @Transient
    @Override
    public int updateFdCarryPriceInfo(JdjpFdCarryPriceInfo fdCarryPriceInfo){
        int result = 0;
        boolean successFlag = false;
        result = this.updateByPrimaryKeySelective(fdCarryPriceInfo);
        Byte state = fdCarryPriceInfo.getState();
        if(state != null && state == 1){
            //使用状态
            successFlag = jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(fdCarryPriceInfo);
        }else{
            successFlag = jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(fdCarryPriceInfo);
        }
        if(!successFlag){
            LOGGER.info("更新缓存失败");
            result = 0;
        }
        return result;
    }

    /**
     * 功能描述: 批量启用
     * @param:
     * @return:
     */
    @Override
    public boolean isShow(String operator, String carrypriceIds){
        try {
            Integer[] ids = changeIntegerArray(carrypriceIds);
            //启用更新运价状态, state: 0-禁用，1-使用
            boolean flag = jdjpFdCarryPriceInfoManager.updateBatch(operator, ids, new Byte(StateEnum.VALID.getCode()));
            boolean successFlag = false;
            if(flag){
                //根据ids查询运价
                List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList = jdjpFdCarryPriceInfoManager.selectByIds(ids);
                //更新缓存运价
                successFlag = jdjpFdCarryPriceInfoRedisService.updateOrAddCarryPriceRedis(fdCarryPriceInfoList);
            }
            if(!successFlag){
                LOGGER.info("更新缓存失败");
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("批量启用运价数据出错", e);
            throw new RuntimeException("批量启用运价数据出错");
        }

    }

    /**
     * 功能描述: 批量停用
     * @param:
     * @return:
     */
    @Override
    public boolean isHide(String operator, String carrypriceIds){
        try {
            Integer[] ids = changeIntegerArray(carrypriceIds);
            //停用更新运价状态, state: 0-禁用，1-使用
            boolean flag = jdjpFdCarryPriceInfoManager.updateBatch(operator, ids, new Byte(StateEnum.INVALID.getCode()));
            boolean successFlag = false;
            if(flag){
                //根据ids查询运价
                List<JdjpFdCarryPriceInfo> fdCarryPriceInfoList = jdjpFdCarryPriceInfoManager.selectByIds(ids);
                //更新缓存运价
                successFlag = jdjpFdCarryPriceInfoRedisService.delCarryPriceRedis(fdCarryPriceInfoList);
            }
            if(!successFlag){
                LOGGER.info("更新缓存失败");
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("批量停用运价数据出错", e);
            throw new RuntimeException("批量停用运价数据出错");
        }
    }

    /**
     * 功能描述: 清除缓存
     * @param:
     * @return:
     */
    @Override
    public void cleanRedisForCarryPrice(String airWays, String depCode, String arrCode, String seat, String carryPriceType){
        jdjpFdCarryPriceInfoRedisService.cleanRedisForCarryPrice(airWays, depCode, arrCode, seat, carryPriceType);
    }


    /**
     * 功能描述: 字符串转化为数组
     * @param:
     * @return:
     */
    private Integer[] changeIntegerArray(String carrypriceIds){
        LOGGER.info("需要转换的字符串："+carrypriceIds);
        String[] carrypriceIdsArr = carrypriceIds.split(",");
        Integer[] ids = new Integer[carrypriceIdsArr.length];
        for(int i=0; i<carrypriceIdsArr.length; i++){
            ids[i] = Integer.valueOf(carrypriceIdsArr[i]);
        }
        return ids;
    }

}
