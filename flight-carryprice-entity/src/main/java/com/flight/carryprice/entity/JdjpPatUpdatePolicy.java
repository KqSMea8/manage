package com.flight.carryprice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author hYuan 机票供应链研发部
 * @Date 18:33 2019/3/20
 **/
@Table(name = "jdjp_pat_update_policy")
public class JdjpPatUpdatePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
     */
    @Column(name = "airlines")
    private String airlines;
    /**
     * 航司二字码
     */
    @Column(name = "air_ways")
    private String airWays;

    /**
     * 出发城市三字码
     */
    @Column(name = "dep_code")
    private String depCode;

    /**
     * 到达城市三字码
     */
    @Column(name = "arr_code")
    private String arrCode;

    /**
     * 航班号
     */
    @Column(name = "flight_no")
    private String flightNo;

    /**
     * 舱位
     */
    @Column(name = "seat")
    private String seat;

    /**
     * 里程
     */
    @Column(name = "distance")
    private Integer distance;
    /**
     * 折扣
     */
    @Column(name = "discount")
    private BigDecimal discount;
    /**
     * 舱位类型
     */
    @Column(name = "seat_type")
    private Byte seatType;
    /**
     * 舱位等级
     */
    @Column(name = "seat_level")
    private String seatLevel;
    /**
     * 有效日期
     */
    @Column(name = "dept_date")
    private String deptDate;

    /**
     * 预定时间范围起止
     */
    @Column(name = "reserve_time_duration")
    private String reserveTimeDuration;
    /**
     * 起飞时间（P价时间参数）
     */
    @Column(name = "dep_date")
    private String depDate;

    /**
     * 计划执行更新策略时间（更新时间）
     */
    @Column(name = "plan_quartz_time")
    private Date planQuartzTime;

    @Transient
    private String planTime;

    /**
     * 同步状态 0-未同步 1-同步中 2-同步完成
     */
    @Column(name = "sync_status")
    private Byte syncStatus;

    /**
     * 热门类型 普通 热门1 热门2
     */
    @Column(name = "airline_type")
    private Byte airlineType;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 执行更新策略时间
     */
    @Column(name = "execute_quartz_time")
    private Date executeQuartzTime;

    /**
     * 执行完成时间
     */
    @Column(name = "execute_finish_time")
    private Date executeFinishTime;

    /**
     * 0 自动新增 1 手工新增
     */
    @Column(name = "source")
    private Byte source;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }


    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDeptDate() {
        return deptDate;
    }

    public void setDeptDate(String deptDate) {
        this.deptDate = deptDate;
    }

    public String getReserveTimeDuration() {
        return reserveTimeDuration;
    }

    public void setReserveTimeDuration(String reserveTimeDuration) {
        this.reserveTimeDuration = reserveTimeDuration;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public Date getPlanQuartzTime() {
        return planQuartzTime;
    }

    public void setPlanQuartzTime(Date planQuartzTime) {
        this.planQuartzTime = planQuartzTime;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public Byte getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Byte syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Date getExecuteQuartzTime() {
        return executeQuartzTime;
    }

    public void setExecuteQuartzTime(Date executeQuartzTime) {
        this.executeQuartzTime = executeQuartzTime;
    }

    public Date getExecuteFinishTime() {
        return executeFinishTime;
    }

    public void setExecuteFinishTime(Date executeFinishTime) {
        this.executeFinishTime = executeFinishTime;
    }


    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Byte getSeatType() {
        return seatType;
    }

    public void setSeatType(Byte seatType) {
        this.seatType = seatType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSeatLevel() {
        return seatLevel;
    }

    public void setSeatLevel(String seatLevel) {
        this.seatLevel = seatLevel;
    }

    public String getAirWays() {
        return airWays;
    }

    public void setAirWays(String airWays) {
        this.airWays = airWays;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Byte getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(Byte airlineType) {
        this.airlineType = airlineType;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }
}
