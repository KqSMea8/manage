package com.flight.carryprice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "jdjp_nfd_pat_auto_update")
public class JdjpNfdPatAutoUpdate {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 航线类型 包含NFD 0-普通航线   1-热门航线
     *             PAT 0-PAT普通   1-PAT热门1    2-PAT热门2...
     */
    @Column(name = "airline_type")
    private Byte airlineType;
    /**
     * 类型  0-NFD   1-PAT
     */
    @Column(name = "airline_popular_type")
    private Byte airlinePopularType;
    /**
     * 更新时间
     */
    @Column(name = "dept_date")
    private String deptDate;

    /**
     * 执行NFD PAT运价自动更新配置时间
     */
    @Column(name = "execute_quartz_time")
    private Date executeQuartzTime;

    /**
     * 更新频率（单位：天）
     */
    @Column(name = "execute_interval_day")
    private BigDecimal executeIntervalDay;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }
    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    /**
     * 获取航线类型  0-普通航线   1-热门航线
     *
     * @return airline_type - 航线类型  0-普通航线   1-热门航线
     */
    public Byte getAirlineType() {
        return airlineType;
    }

    /**
     * 设置航线类型  0-普通航线   1-热门航线
     *
     * @param airlineType 航线类型  0-普通航线   1-热门航线
     */
    public void setAirlineType(Byte airlineType) {
        this.airlineType = airlineType;
    }

    public Byte getAirlinePopularType() {
        return airlinePopularType;
    }

    public void setAirlinePopularType(Byte airlinePopularType) {
        this.airlinePopularType = airlinePopularType;
    }

    /**
     * 获取更新时间
     *
     * @return dept_date - 更新时间
     */
    public String getDeptDate() {
        return deptDate;
    }

    /**
     * 设置更新时间
     *
     * @param deptDate 更新时间
     */
    public void setDeptDate(String deptDate) {
        this.deptDate = deptDate;
    }

    /**
     * 获取执行NFD运价自动更新配置时间
     *
     * @return execute_quartz_time - 执行NFD运价自动更新配置时间
     */
    public Date getExecuteQuartzTime() {
        return executeQuartzTime;
    }

    /**
     * 设置执行NFD运价自动更新配置时间
     *
     * @param executeQuartzTime 执行NFD运价自动更新配置时间
     */
    public void setExecuteQuartzTime(Date executeQuartzTime) {
        this.executeQuartzTime = executeQuartzTime;
    }

    /**
     * 获取更新频率（单位：天）
     *
     * @return execute_interval_day - 更新频率（单位：天）
     */
    public BigDecimal getExecuteIntervalDay() {
        return executeIntervalDay;
    }

    /**
     * 设置更新频率（单位：天）
     *
     * @param executeIntervalDay 更新频率（单位：天）
     */
    public void setExecuteIntervalDay(BigDecimal executeIntervalDay) {
        this.executeIntervalDay = executeIntervalDay;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "JdjpNfdPatAutoUpdate{" +
                "id=" + id +
                ", airlineType=" + airlineType +
                ", airlinePopularType=" + airlinePopularType +
                ", deptDate='" + deptDate + '\'' +
                ", executeQuartzTime=" + executeQuartzTime +
                ", executeIntervalDay=" + executeIntervalDay +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operator='" + operator + '\'' +
                '}';
    }
}