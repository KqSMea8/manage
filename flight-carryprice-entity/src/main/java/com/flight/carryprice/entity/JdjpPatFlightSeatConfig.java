package com.flight.carryprice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author wanghaiyuan
 * Date 2019/03/19 16:47.
 */
@Table(name = "jdjp_pat_flight_seat_config")
public class JdjpPatFlightSeatConfig {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 热门航线类型 普通 热门1 热门2
     */
    @Column(name = "airline_type")
    private Byte airlineType;

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
     * 状态 0-禁用 1-启用
     */
    @Column(name = "state")
    private Byte state;

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
     * 操作人
     */
    @Column(name = "operator")
    private String operator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Byte getSeatType() {
        return seatType;
    }

    public void setSeatType(Byte seatType) {
        this.seatType = seatType;
    }

    public String getSeatLevel() {
        return seatLevel;
    }

    public void setSeatLevel(String seatLevel) {
        this.seatLevel = seatLevel;
    }

    public Byte getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(Byte airlineType) {
        this.airlineType = airlineType;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
