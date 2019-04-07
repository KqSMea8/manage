package com.flight.carryprice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "jdjp_fd_carry_price_info")
public class JdjpFdCarryPriceInfo {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 航司二字码
     */
    @Column(name = "air_ways")
    private String airWays;

    /**
     * 里程
     */
    @Column(name = "distance")
    private Integer distance;

    /**
     * 舱位
     */
    @Column(name = "seat")
    private String seat;

    /**
     * 价格
     */
    @Column(name = "carry_price")
    private BigDecimal carryPrice;

    /**
     * 航线类型 0-普通航线 1-热门航线
     */
    @Column(name = "airline_type")
    private Byte airlineType;

    /**
     * 使用状态( 0-禁用;1-使用)
     */
    @Column(name = "state")
    private Byte state;

    /**
     * 旅行有效日期起
     */
    @Column(name = "take_off_effect_date")
    private Date takeOffEffectDate;

    /**
     * 旅行有效日期止
     */
    @Column(name = "take_off_expire_date")
    private Date takeOffExpireDate;

    /**
     * 舱位类型 1-经济舱 2-商务舱 3-头等舱 4-超级经济舱 5-特价舱
     */
    @Column(name = "seat_type")
    private Byte seatType;

    /**
     * 舱位等级 Y-经济舱 C-商务舱 F-头等舱
     */
    @Column(name = "seat_level")
    private String seatLevel;

    /**
     * 折扣
     */
    @Column(name = "discount")
    private BigDecimal discount;

    /**
     * 数据来源( 0-FD;1-NFD)
     */
    private Byte source;

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


    /**** 扩展属性 ********/
    //高价标识
    @Transient
    private String longPrice;
    //低价标识
    @Transient
    private String lowPrice;
    @Transient
    private String versionNum;
    @Transient
    private Date depDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getAirWays() {
        return airWays;
    }

    public void setAirWays(String airWays) {
        this.airWays = airWays;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getCarryPrice() {
        return carryPrice;
    }

    public void setCarryPrice(BigDecimal carryPrice) {
        this.carryPrice = carryPrice;
    }

    public Byte getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(Byte airlineType) {
        this.airlineType = airlineType;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getTakeOffEffectDate() {
        return takeOffEffectDate;
    }

    public void setTakeOffEffectDate(Date takeOffEffectDate) {
        this.takeOffEffectDate = takeOffEffectDate;
    }

    public Date getTakeOffExpireDate() {
        return takeOffExpireDate;
    }

    public void setTakeOffExpireDate(Date takeOffExpireDate) {
        this.takeOffExpireDate = takeOffExpireDate;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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

    public String getLongPrice() {
        return longPrice;
    }

    public void setLongPrice(String longPrice) {
        this.longPrice = longPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }
}