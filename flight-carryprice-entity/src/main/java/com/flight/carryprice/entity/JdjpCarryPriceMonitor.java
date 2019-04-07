package com.flight.carryprice.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 运价监控表实体
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Table(name = "jdjp_carry_price_monitor")
public class JdjpCarryPriceMonitor {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 工单订单号
     */
    @Column(name = "order_id")
    private String orderId;
    /**
     * pnr
     */
    @Column(name = "order_pnr")
    private String orderPnr;
    /**
     * 出发机场3字码
     */
    @Column(name = "dep_code")
    private String depCode;
    /**
     * 到达机场3字码
     */
    @Column(name = "arr_code")
    private String arrCode;
    /**
     * 航司
     */
    @Column(name = "air_ways")
    private String airWays;
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
     * 起飞时间
     */
    @Column(name = "dep_time")
    private Date depTime;
    /**
     * 错误运价
     */
    @Column(name = "error_carry_price")
    private BigDecimal errorCarryPrice;
    /**
     * 实际运价
     */
    @Column(name = "real_carry_price")
    private BigDecimal realCarryPrice;
    /**
     * 0-未处理  1-已处理
     */
    @Column(name = "operate_status")
    private Byte operateStatus;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderPnr() {
        return orderPnr;
    }

    public void setOrderPnr(String orderPnr) {
        this.orderPnr = orderPnr;
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

    public String getAirWays() {
        return airWays;
    }

    public void setAirWays(String airWays) {
        this.airWays = airWays;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Date getDepTime() {
        return depTime;
    }

    public void setDepTime(Date depTime) {
        this.depTime = depTime;
    }

    public BigDecimal getErrorCarryPrice() {
        return errorCarryPrice;
    }

    public void setErrorCarryPrice(BigDecimal errorCarryPrice) {
        this.errorCarryPrice = errorCarryPrice;
    }

    public BigDecimal getRealCarryPrice() {
        return realCarryPrice;
    }

    public void setRealCarryPrice(BigDecimal realCarryPrice) {
        this.realCarryPrice = realCarryPrice;
    }

    public Byte getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Byte operateStatus) {
        this.operateStatus = operateStatus;
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
}
