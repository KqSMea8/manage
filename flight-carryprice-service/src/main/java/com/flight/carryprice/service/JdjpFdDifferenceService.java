package com.flight.carryprice.service;

import com.flight.carryprice.common.service.BaseService;
import com.flight.carryprice.entity.JdjpFdDifference;
import com.flight.carryprice.entity.JdjpFdUpdatePolicy;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description fd运价缓存和数据库差异service层
 * @date 2019/3/07 14:15
 * @updateTime
 */
public interface JdjpFdDifferenceService extends BaseService<JdjpFdDifference> {

    /**
     * 功能描述: 列表查询
     * @param:
     * @return:
     */
    PageInfo<JdjpFdDifference> pagination(Integer pageIndex, Integer pageSize, JdjpFdDifference queryBean);

    /**
     * 获取版本号列表
     * @param
     * @return
     */
    List<String> getVersionNumList();

    /**
     * 功能描述: 下载数据
     * @param:
     * @return:
     */
    void downLoadExcel(HttpServletRequest req, HttpServletResponse res, JdjpFdDifference queryBean);
}
