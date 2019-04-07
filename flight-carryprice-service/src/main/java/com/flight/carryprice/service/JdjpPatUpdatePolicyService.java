package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpPatUpdatePolicy;
import com.github.pagehelper.PageInfo;
import com.jd.airplane.flight.base.vo.airline.Airline;
import com.jd.airplane.flight.base.vo.cabin.Cabin;

/**
 * Author wanghaiyuan
 * Date 2019/3/20 18:50.
 */
public interface JdjpPatUpdatePolicyService extends BaseService<JdjpPatUpdatePolicy> {
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 分页查询pat更新策略
     * source = 1 airline 字段取ES
     * source = 0 airline 为当前值
     * @Date 16:13 2019/3/21
     * @Param [pageIndex, pageSize, queryBean]
     * @return com.github.pagehelper.PageInfo<com.flight.carryprice.entity.JdjpPatUpdatePolicy>
     **/
    PageInfo<JdjpPatUpdatePolicy> pagination(Integer pageIndex, Integer pageSize, JdjpPatUpdatePolicy queryBean);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 新增PAT运价更新策略
     * @Date 16:17 2019/3/21
     * @Param [policy]
     * @return java.lang.Object
     **/
    Object addUpdatePolicy(JdjpPatUpdatePolicy policy);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 根据航司 出发 到达查询airmis获取航线
     *              pat航班舱位配置 pat运价更新策略 用到
     * @Date 17:02 2019/3/21
     * @Param [airCorpCode, depAirportCode, arrAirportCode]
     * @return com.jd.airplane.flight.base.vo.airline.Airline
     **/
    Airline getAirlineList(String airCorpCode, String depAirportCode, String arrAirportCode);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 根据航司 舱位查询airmis获取舱位
     *              pat航班舱位配置 pat运价更新策略 用到
     * @Date 17:03 2019/3/21
     * @Param [airCorpCode, adultCode]
     * @return com.jd.airplane.flight.base.vo.cabin.Cabin
     **/
    Cabin getCabin(String airCorpCode, String adultCode);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 生成pat策略
     * @Date 19:00 2019/3/27
     * @Param [airLineType, deptDate]
     * @return void
     **/
    void patAutoUpdatePolicy(byte airLineType, String deptDate);
}
