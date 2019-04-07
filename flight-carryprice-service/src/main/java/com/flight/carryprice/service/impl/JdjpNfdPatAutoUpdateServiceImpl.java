package com.flight.carryprice.service.impl;

import IceInternal.Ex;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.DateUtil;
import com.flight.carryprice.entity.JdjpNfdPatAutoUpdate;
import com.flight.carryprice.manager.JdjpNfdPatAutoUpdateManager;
import com.flight.carryprice.service.JdjpNfdPatAutoUpdateService;
import com.flight.carryprice.service.JdjpNfdUpdatePolicyService;
import com.flight.carryprice.service.JdjpPatUpdatePolicyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * @Author hYuan 机票供应链研发部
 * @Date 18:03 2019/3/5
 **/
@Service
public class JdjpNfdPatAutoUpdateServiceImpl extends BaseServiceImpl<JdjpNfdPatAutoUpdate> implements JdjpNfdPatAutoUpdateService {
    private static final Logger LOGGER = Logger.getLogger(JdjpNfdPatAutoUpdateServiceImpl.class);
    private static final String SPLIT = ",";
    @Resource
    private JdjpNfdPatAutoUpdateManager jdjpNfdPatAutoUpdateManager;
    @Resource
    private JdjpPatUpdatePolicyService jdjpPatUpdatePolicyService;
    @Resource
    private JdjpNfdUpdatePolicyService jdjpNfdUpdatePolicyService;

    /**
     * @Author hYuan
     * @Description 根据热门类型和航线类型查找JdjpNfdPatAutoUpdate
     * @Date 17:42 2019/3/5
     * @Param [jdjpNfdPatAutoUpdate]
     * @return java.util.List<com.flight.carryprice.entity.JdjpNfdPatAutoUpdate>
     **/
    @Override
    public List<JdjpNfdPatAutoUpdate> queryByPopularAndAirType(JdjpNfdPatAutoUpdate jdjpNfdPatAutoUpdate) {
        return jdjpNfdPatAutoUpdateManager.queryByPopularAndAirType(jdjpNfdPatAutoUpdate);
    }

    @Override
    public void nfdPatAutoUpdateJob() {
        JdjpNfdPatAutoUpdate jdjpNfdPatAutoUpdate = new JdjpNfdPatAutoUpdate();
        Date currentDay = new Date();
        jdjpNfdPatAutoUpdate.setExecuteQuartzTime(currentDay);
        List<JdjpNfdPatAutoUpdate> jdjpNfdPatAutoUpdateList = jdjpNfdPatAutoUpdateManager.queryByPopularAndAirType(jdjpNfdPatAutoUpdate);
        for (JdjpNfdPatAutoUpdate nfdPatAutoUpdate : jdjpNfdPatAutoUpdateList){
            String deptDate = nfdPatAutoUpdate.getDeptDate();
            String[] deptDateArray = deptDate.split(SPLIT);
            byte cpType = nfdPatAutoUpdate.getAirlinePopularType();
            byte airlineType = nfdPatAutoUpdate.getAirlineType();
            StringBuffer deptDateSb = new StringBuffer();
            //nfd
            if (cpType == (byte)0){
                if (deptDateArray.length == 1){
                    deptDate = DateUtil.dateToString(
                            DateUtil.dateAddDays(currentDay, Integer.valueOf(deptDateArray[0].substring(2))),
                            DateUtil.DATE_FMT1);
                }else {
                    for (String dept : deptDateArray){
                        deptDateSb.append(dept.substring(2)).append(SPLIT);
                    }
                    deptDate = deptDateSb.deleteCharAt(deptDateSb.length() - 1).toString();
                }
                try {
                    jdjpNfdUpdatePolicyService.nfdAutoUpdatePolicy(airlineType, deptDate);
                }catch (Exception e){
                    LOGGER.error("执行nfd自动更新生成策略异常", e);
                }

            //pat
            }else if (cpType == (byte)1){
                for (String dept : deptDateArray){
                    deptDateSb.append(DateUtil.dateToString(
                            DateUtil.dateAddDays(currentDay, Integer.valueOf(dept.substring(2))),
                            DateUtil.DATE_FMT1)).append(SPLIT);
                }
                deptDate = deptDateSb.deleteCharAt(deptDateSb.length() - 1).toString();
                try {
                    jdjpPatUpdatePolicyService.patAutoUpdatePolicy(airlineType, deptDate);
                }catch (Exception e){
                    LOGGER.error("执行pat自动更新生成策略异常", e);
                }
            }
        }
        int result = jdjpNfdPatAutoUpdateManager.updateExecuteQuartzTimeBatch(jdjpNfdPatAutoUpdateList);
        LOGGER.info(String.format("更新nfd与pat自动更新配置 共%条", result));
    }
}
