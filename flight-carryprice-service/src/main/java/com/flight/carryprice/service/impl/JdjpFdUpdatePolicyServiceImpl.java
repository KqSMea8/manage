package com.flight.carryprice.service.impl;

import com.flight.carryprice.common.enums.AirlineTypeEnum;
import com.flight.carryprice.common.service.impl.BaseServiceImpl;
import com.flight.carryprice.common.util.BaseDataUtils;
import com.flight.carryprice.common.util.JacksonUtil;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.flight.carryprice.manager.JdjpFdUpdatePolicyManager;
import com.flight.carryprice.service.JdjpFdUpdatePolicyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.model.enmus.QueryTypeEnum;
import com.jd.airplane.flight.base.vo.airline.Airline;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description FD运价更新策略service层
 * @date 2019/2/28 14:15
 * @updateTime
 */
@Service
public class JdjpFdUpdatePolicyServiceImpl extends BaseServiceImpl<JdjpFdUpdatePolicy> implements JdjpFdUpdatePolicyService {

    private final static Logger LOGGER = Logger.getLogger(JdjpFdUpdatePolicyServiceImpl.class);

    @Resource
    private JdjpFdUpdatePolicyManager jdjpFdUpdatePolicyManager;

    /**
     * 功能描述: 查询单条记录（未同步，并且计划执行时间小于当前时间）
     * @param:
     * @return:
     */
    @Override
    public JdjpFdUpdatePolicy selectFdPolicyOne() {
        return jdjpFdUpdatePolicyManager.selectFdPolicyOne();
    }

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    @Override
    public PageInfo<JdjpFdUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpFdUpdatePolicy queryBean) {
        PageHelper.startPage(pageIndex, pageSize);
        List<JdjpFdUpdatePolicy> list = jdjpFdUpdatePolicyManager.queryList(queryBean);
        return new PageInfo<JdjpFdUpdatePolicy>(list);
    }

    /**
     * fn运价更新策略-新增
     * @param jdjpFdUpdatePolicy
     * @return 0-失败，1-成功
     */
    @Override
    public int insertFdUpdatePolicy(JdjpFdUpdatePolicy jdjpFdUpdatePolicy){
        int flag = 0;
        //判断航线类型，为普通或者热门
        Byte airlineType = jdjpFdUpdatePolicy.getAirlineType();
        if(airlineType == null){
            return flag;
        }

        if (airlineType == 0) {// 全量-添加一条
            int count = this.insertSelective(jdjpFdUpdatePolicy);
            flag = count > 0 ? 1 : 0;
            LOGGER.info("批量插入FD运价更新策略：" + count + "条");
        } else {// 热门--添加多条
            // 返查热门数据
            Airline record = new Airline();
            record.setFdType(Integer.valueOf(AirlineTypeEnum.POPULAR.getCode()));//0-普通航线  1-热门航线
            String airways = jdjpFdUpdatePolicy.getAirWays();
            //航司如果不为全部，加条件
            if (StringUtils.isNotBlank(airways) && !airways.equals("***")) {
                record.setAirCorpCode(jdjpFdUpdatePolicy.getAirWays());
            }
            List<Airline> airlineList = BaseDataUtils.getAirlineListWithCondition(record);

            if (CollectionUtils.isNotEmpty(airlineList)) {
                LOGGER.info("航线信息" + JacksonUtil.obj2json(airlineList));
                // 批量插入
                List<JdjpFdUpdatePolicy> list = new ArrayList<>();
                for (int i = 0; i < airlineList.size(); i++) {
                    Airline airline = airlineList.get(i);
                    jdjpFdUpdatePolicy.setDepCode(airline.getDepAirportCode());
                    jdjpFdUpdatePolicy.setArrCode(airline.getArrAirportCode());
                    jdjpFdUpdatePolicy.setAirWays(airline.getAirCorpCode());
                    list.add(JacksonUtil.obj2obj(jdjpFdUpdatePolicy, JdjpFdUpdatePolicy.class));
                }
                LOGGER.info("待更新的热门航线信息" + JacksonUtil.obj2json(list));
                int count = jdjpFdUpdatePolicyManager.insertBatch(list);
                flag = count > 0 ? 1 : 0;
                LOGGER.info("批量插入FD运价更新策略：" + count + "条");
            } else {
                LOGGER.info("没有符合条件的航线");
                return flag;
            }
        }
        return flag;
    }

}
