package com.flight.carryprice.quartz.service.impl;

import com.flight.carryprice.common.constant.CarryPriceConstants;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpFdCarryPriceInfo;
import com.flight.carryprice.entity.JdjpFdDifference;
import com.flight.carryprice.manager.JdjpFdCarryPriceInfoManager;
import com.flight.carryprice.quartz.service.FdDifferenceRedisAndDbService;
import com.flight.carryprice.service.JdCacheService;
import com.flight.carryprice.service.JdjpFdCarryPriceInfoRedisService;
import com.flight.carryprice.service.JdjpFdDifferenceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价比较数据库和缓存中不同，做记录
 * @date 2019/4/2 10:46
 * @updateTime
 */
@Service
public class FdDifferenceRedisAndDbServiceImpl implements FdDifferenceRedisAndDbService {

    private static Logger LOGGER = Logger.getLogger(FdDifferenceRedisAndDbServiceImpl.class);

    @Resource
    private JdjpFdCarryPriceInfoManager jdjpFdCarryPriceInfoManager;

    @Resource
    private JdjpFdCarryPriceInfoRedisService jdjpFdCarryPriceInfoRedisService;

    @Resource
    private JdjpFdDifferenceService jdjpFdDifferenceService;

    @Resource
    private JdCacheService jdCacheService;

    /**
     * 功能描述: 比较FD运价数据库和redis中不同
     * @param:
     * @return:
     */
    @Override
    public void compareFDRedisAndDB(){
        //判断是否正在执行
        if(getRunningFlag()){
            LOGGER.info("运价比较正在执行，直接返回！");
            return;
        }
        /** 存储运价执行状态 **/
        setRunningFlag(true);

        /** 生成本次比较的版本号 **/
        String versionNum = DateUtil.dateToString(new Date(), DateUtil.DATE_TIME6);

        while(true){

            Integer pageNo = 1;
            Integer pageSize = 5000;
            List<JdjpFdCarryPriceInfo> carryPriceInfoList = jdjpFdCarryPriceInfoManager.queryListByLimit(pageNo, pageSize);
            if(CollectionUtils.isEmpty(carryPriceInfoList)){
                break;
            }
            for (JdjpFdCarryPriceInfo carryPriceInfoDb : carryPriceInfoList){
                JdjpFdCarryPriceInfo carryPriceInfoRedis = jdjpFdCarryPriceInfoRedisService.getRedisFdCarryPriceInfo(carryPriceInfoDb);
                //比较相同，则继续
                if(comareDifference(carryPriceInfoDb, carryPriceInfoRedis)){
                    continue;
                }
                saveFdDifference(carryPriceInfoDb, carryPriceInfoRedis, versionNum);

            }
            pageNo++;
        }
        /** 存储运价完成状态 **/
        setRunningFlag(false);

        /** 刪除历史数据 **/
        //setRunningFlag(true);
    }

    /**
     * 功能描述: 比较是否相同，全部相同，返回true，有不同的字段，返回false
     * @param:
     * @return:
     */
    private boolean comareDifference(JdjpFdCarryPriceInfo carryPriceInfoDb, JdjpFdCarryPriceInfo carryPriceInfoRedis){
        if(carryPriceInfoRedis == null ){
            LOGGER.info("缓存中的运价为空！数据库中的运价信息为：" + JacksonUtil.obj2json(carryPriceInfoDb));
            return false;
        }
        if(StringUtils.isBlank(carryPriceInfoRedis.getAirWays()) ||
                !carryPriceInfoDb.getAirWays().equals(carryPriceInfoRedis.getAirWays())){
            LOGGER.info("航司不同：缓存-航司：" + carryPriceInfoRedis.getAirWays() + " 数据库-航司：" + carryPriceInfoDb.getAirWays());
            return false;
        }

        if(StringUtils.isBlank(carryPriceInfoRedis.getDepCode()) ||
                !carryPriceInfoDb.getDepCode().equals(carryPriceInfoRedis.getDepCode())){
            LOGGER.info("航司不同：缓存-出发：" + carryPriceInfoRedis.getDepCode() + " 数据库-出发：" + carryPriceInfoDb.getDepCode());
            return false;
        }

        if(StringUtils.isBlank(carryPriceInfoRedis.getArrCode()) ||
                !carryPriceInfoDb.getArrCode().equals(carryPriceInfoRedis.getArrCode())){
            LOGGER.info("航司不同：缓存-到达" + carryPriceInfoRedis.getArrCode() + " 数据库-到达：" + carryPriceInfoDb.getArrCode());
            return false;
        }

        if(carryPriceInfoRedis.getDistance() == null ||
                carryPriceInfoDb.getDistance() != carryPriceInfoRedis.getDistance()){
            LOGGER.info("航司不同：缓存-里程" + carryPriceInfoRedis.getDistance() + " 数据库-里程：" + carryPriceInfoDb.getDistance());
            return false;
        }

        if(carryPriceInfoRedis.getSeat() == null ||
                !carryPriceInfoDb.getSeat().equals(carryPriceInfoRedis.getSeat())){
            LOGGER.info("航司不同：缓存-舱位" + carryPriceInfoRedis.getSeat() + " 数据库-舱位：" + carryPriceInfoDb.getSeat());
            return false;
        }

        if(carryPriceInfoRedis.getCarryPrice() == null ||
                carryPriceInfoDb.getCarryPrice().compareTo(carryPriceInfoRedis.getCarryPrice()) != 0){
            LOGGER.info("航司不同：缓存-价格" + carryPriceInfoRedis.getCarryPrice() + " 数据库-价格：" + carryPriceInfoDb.getCarryPrice());
            return false;
        }

        if(carryPriceInfoRedis.getAirlineType() == null ||
                carryPriceInfoDb.getAirlineType() != carryPriceInfoRedis.getAirlineType()){
            LOGGER.info("航司不同：缓存-航线类型" + carryPriceInfoRedis.getAirlineType() + " 数据库-航线类型：" + carryPriceInfoDb.getAirlineType());
            return false;
        }

        if(carryPriceInfoRedis.getAirlineType() == null ||
                carryPriceInfoDb.getAirlineType() != carryPriceInfoRedis.getAirlineType()){
            LOGGER.info("航司不同：缓存-航线类型" + carryPriceInfoRedis.getAirlineType() + " 数据库-航线类型：" + carryPriceInfoDb.getAirlineType());
            return false;
        }

        if(carryPriceInfoRedis.getTakeOffEffectDate() == null ||
                DateUtil.compare_date(carryPriceInfoRedis.getTakeOffEffectDate(), carryPriceInfoRedis.getTakeOffEffectDate()) != 0){
            LOGGER.info("航司不同：缓存-旅行有效期起：" + carryPriceInfoRedis.getTakeOffEffectDate() + " 数据库-旅行有效期起：" + carryPriceInfoDb.getTakeOffEffectDate());
            return false;
        }

        if(carryPriceInfoRedis.getTakeOffExpireDate() == null ||
                DateUtil.compare_date(carryPriceInfoRedis.getTakeOffExpireDate(), carryPriceInfoRedis.getTakeOffExpireDate()) != 0){
            LOGGER.info("航司不同：缓存-旅行有效期止：" + carryPriceInfoRedis.getTakeOffExpireDate() + " 数据库-旅行有效期止：" + carryPriceInfoDb.getTakeOffExpireDate());
            return false;
        }

        if(carryPriceInfoDb.getSeatType() != carryPriceInfoRedis.getSeatType()){
            LOGGER.info("航司不同：缓存-舱位类型：" + carryPriceInfoRedis.getSeatType() + " 数据库-舱位类型：" + carryPriceInfoDb.getSeatType());
            return false;
        }

        if(carryPriceInfoDb.getSeatLevel() != carryPriceInfoRedis.getSeatLevel()){
            LOGGER.info("航司不同：缓存-舱位等级：" + carryPriceInfoRedis.getSeatLevel() + " 数据库-舱位等级：" + carryPriceInfoDb.getSeatLevel());
            return false;
        }

        if(carryPriceInfoDb.getDiscount().compareTo(carryPriceInfoRedis.getDiscount()) != 0){
            LOGGER.info("航司不同：缓存-折扣：" + carryPriceInfoRedis.getSeatLevel() + " 数据库-折扣：" + carryPriceInfoDb.getSeatLevel());
            return false;
        }

        return true;
    }


    /**
     * 获取运价比较是否正在执行,如果是，返回true，否，返回false
     * @return
     */
    private boolean getRunningFlag() {
        //放置信息，表示正在执行
        String flag = jdCacheService.get(CarryPriceConstants.FD_COMPARE_IS_RUNNING_FLAG);
        return StringUtils.isNotBlank(flag) && flag.equals("1") ? true : false;
    }

    /**
     * 功能描述: 存储运价比较执行状态
     * @param:
     * @return: 
     */
    private void setRunningFlag(boolean value) {
        if (value) {
            //放置信息，表示正在执行,保存两个小时
            jdCacheService.set(CarryPriceConstants.FD_COMPARE_IS_RUNNING_FLAG, "1", (long) 60 * 60 * 2);
        } else {
            //删除信息，表示执行完毕
            jdCacheService.delete(CarryPriceConstants.FD_COMPARE_IS_RUNNING_FLAG);
        }
    }

    private void saveFdDifference(JdjpFdCarryPriceInfo carryPriceInfoDb, JdjpFdCarryPriceInfo carryPriceInfoRedis, String versionNum) {
        JdjpFdDifference fdDifference = new JdjpFdDifference();
        BeanUtils.copyProperties(carryPriceInfoDb, fdDifference);
        fdDifference.setDatabaseId(carryPriceInfoDb.getId());
        fdDifference.setVersionNum(versionNum);
        if(carryPriceInfoRedis != null){
            fdDifference.setDepCodeRedis(carryPriceInfoRedis.getDepCode());
            fdDifference.setArrCodeRedis(carryPriceInfoRedis.getArrCode());
            fdDifference.setAirWaysRedis(carryPriceInfoRedis.getAirWays());
            fdDifference.setDistanceRedis(carryPriceInfoRedis.getDistance());
            fdDifference.setSeatRedis(carryPriceInfoRedis.getSeat());
            fdDifference.setCarryPriceRedis(carryPriceInfoRedis.getCarryPrice());
            fdDifference.setAirlineTypeRedis(carryPriceInfoRedis.getAirlineType());
            fdDifference.setTakeOffEffectDateRedis(carryPriceInfoRedis.getTakeOffEffectDate());
            fdDifference.setTakeOffExpireDateRedis(carryPriceInfoRedis.getTakeOffExpireDate());
            fdDifference.setSeatTypeRedis(carryPriceInfoRedis.getSeatType());
            fdDifference.setSeatLevelRedis(carryPriceInfoRedis.getSeatLevel());
            fdDifference.setDiscountRedis(carryPriceInfoRedis.getDiscount());
            fdDifference.setSourceRedis(carryPriceInfoRedis.getSource());
        }
        fdDifference.setCreateTime(new Date());
        fdDifference.setUpdateTime(new Date());
        fdDifference.setOperator(CarryPriceConstants.QUARTZ_OPERATOR_SYSTEM);
        jdjpFdDifferenceService.insertSelective(fdDifference);
    }


}
