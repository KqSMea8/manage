package com.flight.carryprice.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "jdjp_nfd_carry_price_info")
public class JdjpNfdCarryPriceInfo implements Serializable {

    private static final long serialVersionUID = 5530840327463045178L;


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 出发城市3字码
     */
    @Column(name = "dep_code")
    private String depCode;
    /**
     * 到达城市3字码
     */
    @Column(name = "arr_code")
    private String arrCode;
    /**
     * 航空公司
     */
    @Column(name = "air_ways")
    private String airWays;
    /**
     * 舱位代码
     */
    @Column(name = "seat")
    private String seat;

    /**
     * 运价级别（政策码）
     */
    @Column(name = "price_level")
    private String priceLevel;
    /**
     * 运价等级对应的舱位
     */
    @Column(name = "price_level_seat")
    private String priceLevelSeat;

    @Column(name = "carry_price")
    private BigDecimal carryPrice;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "airline_type")
    private Integer airlineType;

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
     * 提前预定最小天数
     */
    @Column(name = "advance_smallest_day")
    private Integer advanceSmallestDay;
    /**
     * 提前预定最大天数
     */
    @Column(name = "advance_biggest_day")
    private Integer advanceBiggestDay;
    /**
     * 适用航班
     */
    @Column(name = "include_flight_no")
    private String includeFlightNo;
    /**
     * 不适用航班
     */
    @Column(name = "exclude_flight_no")
    private String excludeFlightNo;
    /**
     * 退票规则
     */
    @Column(name = "refund_rule")
    private String refundRule;
    /**
     * 改签规则
     */
    @Column(name = "change_rule")
    private String changeRule;
    /**
     * 班期
     */
    @Column(name = "schedules")
    private String schedules;
    /**
     * 舱位类型  1-经济舱  2-商务舱  3-头等舱  4-超级经济舱  5-特价舱
     */
    @Column(name = "seat_type")
    private Byte seatType;
    /**
     * 舱位等级  Y-经济舱   C-商务舱   F-头等舱
     */
    @Column(name = "seat_level")
    private String seatLevel;
    /**
     * 折扣 （例0.95）
     */
    @Column(name = "discount")
    private BigDecimal discount;
    /**
     * 使用状态( 0-禁用;1-使用)
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 去程时刻限制
     */
    @Column(name = "flight_time_limit")
    private String flightTimeLimit;
    /**
     * NFD和PAT运价标识 0-NFD 1-PAT
     */
    @Column(name = "airline_popular_type")
    private Integer airlinePopularType;
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

    /**
     * 出发日期(扩展属性)
     */
    @Transient
    private String depDate;

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

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getPriceLevelSeat() {
        return priceLevelSeat;
    }

    public void setPriceLevelSeat(String priceLevelSeat) {
        this.priceLevelSeat = priceLevelSeat;
    }

    public BigDecimal getCarryPrice() {
        return carryPrice;
    }

    public void setCarryPrice(BigDecimal carryPrice) {
        this.carryPrice = carryPrice;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getAirlineType() {
        return airlineType;
    }

    public void setAirlineType(Integer airlineType) {
        this.airlineType = airlineType;
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

    public Integer getAdvanceSmallestDay() {
        return advanceSmallestDay;
    }

    public void setAdvanceSmallestDay(Integer advanceSmallestDay) {
        this.advanceSmallestDay = advanceSmallestDay;
    }

    public Integer getAdvanceBiggestDay() {
        return advanceBiggestDay;
    }

    public void setAdvanceBiggestDay(Integer advanceBiggestDay) {
        this.advanceBiggestDay = advanceBiggestDay;
    }

    public String getIncludeFlightNo() {
        return includeFlightNo;
    }

    public void setIncludeFlightNo(String includeFlightNo) {
        this.includeFlightNo = includeFlightNo;
    }

    public String getExcludeFlightNo() {
        return excludeFlightNo;
    }

    public void setExcludeFlightNo(String excludeFlightNo) {
        this.excludeFlightNo = excludeFlightNo;
    }

    public String getRefundRule() {
        return refundRule;
    }

    public void setRefundRule(String refundRule) {
        this.refundRule = refundRule;
    }

    public String getChangeRule() {
        return changeRule;
    }

    public void setChangeRule(String changeRule) {
        this.changeRule = changeRule;
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

    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFlightTimeLimit() {
        return flightTimeLimit;
    }

    public void setFlightTimeLimit(String flightTimeLimit) {
        this.flightTimeLimit = flightTimeLimit;
    }

    public Integer getAirlinePopularType() {
        return airlinePopularType;
    }

    public void setAirlinePopularType(Integer airlinePopularType) {
        this.airlinePopularType = airlinePopularType;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seatLevel != null ? seatLevel.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (airWays != null ? airWays.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (priceLevel != null ? priceLevel.hashCode() : 0);
        result = 31 * result + (depCode != null ? depCode.hashCode() : 0);
        result = 31 * result + (arrCode != null ? arrCode.hashCode() : 0);
        result = 31 * result + (carryPrice != null ? carryPrice.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (airlineType != null ? airlineType.hashCode() : 0);
        result = 31 * result + (advanceSmallestDay != null ? advanceSmallestDay.hashCode() : 0);
        result = 31 * result + (advanceBiggestDay != null ? advanceBiggestDay.hashCode() : 0);
        result = 31 * result + (includeFlightNo != null ? includeFlightNo.hashCode() : 0);
        result = 31 * result + (excludeFlightNo != null ? excludeFlightNo.hashCode() : 0);
        result = 31 * result + (refundRule != null ? refundRule.hashCode() : 0);
        result = 31 * result + (changeRule != null ? changeRule.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (seatType != null ? seatType.hashCode() : 0);
        result = 31 * result + (flightTimeLimit != null ? flightTimeLimit.hashCode() : 0);
        return result;
        }
}

