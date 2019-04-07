package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.PrefixEnum;
import com.flight.carryprice.common.exception.SystemException;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import com.flight.carryprice.manager.JdjpLimitFlightInfoManager;
import com.flight.carryprice.service.JdCacheUtils;
import com.flight.carryprice.service.JdjpLimitFlightInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author wanghaiyuan
 * Date 2019/3/21 18:40.
 */
@Service
public class JdjpLimitFlightInfoServiceImpl extends BaseServiceImpl<JdjpLimitFlightInfo> implements JdjpLimitFlightInfoService {
    private final static Logger LOGGER = Logger.getLogger(JdjpLimitFlightInfoServiceImpl.class);
    @Resource
    private JdjpLimitFlightInfoManager jdjpLimitFlightInfoManager;
    @Resource
    private JdCacheUtils jdCacheUtils;
    private static Long SECONDS_OF_TWO_DAYS = 2 * 24 * 3600L;
    @Override
    public PageInfo<JdjpLimitFlightInfo> pagination(Integer pageIndex, Integer pageSize, JdjpLimitFlightInfo jdjpLimitFlightInfo){
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpLimitFlightInfo> limitFlightInfos = jdjpLimitFlightInfoManager.queryList(jdjpLimitFlightInfo);
        return new PageInfo<>(limitFlightInfos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addLimitFlights(JdjpLimitFlightInfo jdjpLimitFlightInfo) {
        Date limitTimeEnd = jdjpLimitFlightInfo.getLimitTimeEnd();
        jdjpLimitFlightInfo.setLimitTimeEnd(parseDate(limitTimeEnd));
        int result = super.insertSelective(jdjpLimitFlightInfo);
        if (result > 0){
            if (!this.flushFlightInfoToJimdb(jdjpLimitFlightInfo)){
                throw new SystemException("更新限制航班信息到缓存失败");
            }
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLimitFlights(JdjpLimitFlightInfo jdjpLimitFlightInfo) {
        Date limitTimeEnd = jdjpLimitFlightInfo.getLimitTimeEnd();
        jdjpLimitFlightInfo.setLimitTimeEnd(parseDate(limitTimeEnd));
        JdjpLimitFlightInfo oldJdjpLimitFlightInfo = super.selectByPrimaryKey(jdjpLimitFlightInfo.getId());
        int result = super.updateByPrimaryKeySelective(jdjpLimitFlightInfo);
        if (result > 0){
            if (this.flushFlightInfoToJimdb(jdjpLimitFlightInfo) && this.flushFlightInfoToJimdb(oldJdjpLimitFlightInfo)){
            }else{
                throw new SystemException("更新限制航班信息到缓存失败");
            }
        }
        return result;
    }

    @Override
    public boolean flushFlightInfoToJimdb(JdjpLimitFlightInfo jdjpLimitFlightInfo) {
        boolean result;
        List<JdjpLimitFlightInfo> limitFlightList = jdjpLimitFlightInfoManager.queryEffectList(jdjpLimitFlightInfo);
        jdCacheUtils.hDel(getRedisKey(jdjpLimitFlightInfo), getMapkey(jdjpLimitFlightInfo));
        if (CollectionUtils.isEmpty(limitFlightList)){
            return true;
        }
        result = jdCacheUtils.hSet(getRedisKey(jdjpLimitFlightInfo), getMapkey(jdjpLimitFlightInfo), JacksonUtil.obj2json(limitFlightList));
        jdCacheUtils.expire(getRedisKey(jdjpLimitFlightInfo), SECONDS_OF_TWO_DAYS);
        return result;
    }

    @Override
    public int flushAllFlightInfoToJimdb() {
        List<JdjpLimitFlightInfo> fields = jdjpLimitFlightInfoManager.queryFields();
        AtomicInteger count = new AtomicInteger(0);
        for (JdjpLimitFlightInfo jdjpLimitFlightInfo : fields){
            this.flushFlightInfoToJimdb(jdjpLimitFlightInfo);
            count.incrementAndGet();
        }
        return count.get();
    }

    @Override
    public int delFlightInfo(long id) {
        JdjpLimitFlightInfo jdjpLimitFlightInfo = super.selectByPrimaryKey(id);
        if (jdjpLimitFlightInfo == null){
            return -1;
        }
        int result = super.deleteByPrimaryKey(id);
        if (result > 0){
            this.flushFlightInfoToJimdb(jdjpLimitFlightInfo);
        }
        return result;
    }

    /**
     * @Author hYuan 机票供应链研发部
     * @Description 组装redis key ：LIMITF_出发城市三字码_到达城市三字码
     * @Date 18:00 2019/3/22
     * @Param [jdjpLimitFlightInfo]
     * @return java.lang.String
     **/
    private String getRedisKey(JdjpLimitFlightInfo jdjpLimitFlightInfo){
        StringBuffer stringBuffer = new StringBuffer(PrefixEnum.LIMITFLIGHTM2REDIS.getCode());
        if (jdjpLimitFlightInfo.getDepCode() != null){
            stringBuffer.append('_').append(jdjpLimitFlightInfo.getDepCode());
        }
        if (jdjpLimitFlightInfo.getArrCode() != null){
            stringBuffer.append('_').append(jdjpLimitFlightInfo.getArrCode());
        }
        return stringBuffer.toString();
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 组装map 的key ；航司_航班号_是否虚假舱位_舱位码
     * @Date 18:00 2019/3/22
     * @Param [flightInfo]
     * @return java.lang.String
     **/
    private String getMapkey(JdjpLimitFlightInfo flightInfo){
        StringBuffer stringBuffer = new StringBuffer();
        if (flightInfo.getAirWays() != null){
            stringBuffer.append(flightInfo.getAirWays());
        }
        if (flightInfo.getFlightNo() != null){
            stringBuffer.append('_').append(flightInfo.getFlightNo());
        }
        if (flightInfo.getIsFalseSeat() != null){
            stringBuffer.append('_').append(flightInfo.getIsFalseSeat());
        }
        if (flightInfo.getSeat() != null && !"".equals(flightInfo.getSeat())){
            stringBuffer.append('_').append(flightInfo.getSeat());
        }
        return stringBuffer.toString();
    }
    /**
     * @Author hYuan 机票供应链研发部
     * @Description limitTimeEnd字段，在页面显示为yyyy-MM-dd<br>
     *              对于用户来说应该包含当天全天 故取时间至23：59：59<br>
     * @Date 14:46 2019/3/26
     * @Param [date]
     * @return java.util.Date
     **/
    private Date parseDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
}
