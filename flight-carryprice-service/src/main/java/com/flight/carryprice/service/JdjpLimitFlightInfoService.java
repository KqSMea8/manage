package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpLimitFlightInfo;
import com.github.pagehelper.PageInfo;

/**
 * Author wanghaiyuan
 * Date 2019/3/21 18:40.
 */
public interface JdjpLimitFlightInfoService extends BaseService<JdjpLimitFlightInfo> {
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 分页查询
     * @Date 18:45 2019/3/21
     * @Param [pageIndex, pageSize, jdjpLimitFlightInfo]
     * @return com.github.pagehelper.PageInfo<com.flight.carryprice.entity.JdjpLimitFlightInfo>
     **/
    PageInfo<JdjpLimitFlightInfo> pagination(Integer pageIndex, Integer pageSize, JdjpLimitFlightInfo jdjpLimitFlightInfo);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 新增限制航班信息到库 并同步缓存
     * @Date 14:52 2019/3/26
     * @Param [jdjpLimitFlightInfo]
     * @return int
     **/
    int addLimitFlights(JdjpLimitFlightInfo jdjpLimitFlightInfo);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 修改限制航班信息到库 并同步缓存
     * @Date 14:52 2019/3/26
     * @Param [jdjpLimitFlightInfo]
     * @return int
     **/
    int updateLimitFlights(JdjpLimitFlightInfo jdjpLimitFlightInfo);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description <br>更新某一条限制航班信息到缓存 可以用于新增和更新接口<br/>
     *              <br>修改某一条限制航班 要把修改前和修改后的数据全部更新其所在List集合<br/>
     *              <br>防止脏数据存在<br/>
     * @Date 15:23 2019/3/25
     * @Param [jdjpLimitFlightInfo]
     * @return boolean
     **/
    boolean flushFlightInfoToJimdb(JdjpLimitFlightInfo jdjpLimitFlightInfo);
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 查询所有限制航班从缓存清除原数据 再入缓存</br>
     *              给同步缓存worker 和 页面‘同步缓存’按钮使用</br>
     * @Date 11:44 2019/3/26
     * @Param []
     * @return boolean
     **/
    int flushAllFlightInfoToJimdb();
    /**
     * @Author hYuan 机票供应链研发部
     * @Description 删除限制航班信息 同步删除缓存
     * @Date 14:52 2019/3/26
     * @Param [id]
     * @return int
     **/
    int delFlightInfo(long id);

}
