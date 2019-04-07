package com.flight.carryprice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangyanwei 商旅事业部
 * @version 1.0.0
 * @Description 运价缓存和数据FD库差异表
 * @date 2019/3/7 09:42
 * @updateTime
 */
@Table(name="jdjp_fd_difference")
public class JdjpFdDifference {

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
     * 航线类型   0-普通航线   1-热门航线
     */
    @Column(name = "airline_type")
    private Byte airlineType;

    /**
     * 开始日期
     */
    @Column(name = "take_off_effect_date")
    private Date takeOffEffectDate;

    /**
     * 结束日期
     */
    @Column(name = "take_off_expire_date")
    private Date takeOffExpireDate;

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
     * 折扣（例0.95）
     */
    @Column(name = "discount")
    private BigDecimal discount;

    /**
     * FD中数据来源（0、FD。1、NFD）
     */
    @Column(name = "source")
    private Byte source;

    /**
     * 对应FD运价数据库id
     */
    @Column(name = "database_id")
    private Integer databaseId;

    /**
     * 批次号
     */
    @Column(name = "version_num")
    private String versionNum;

    /**
     * 出发城市三字码redis
     */
    @Column(name = "dep_code_redis")
    private String depCodeRedis;

    /**
     * 到达城市三字码redis
     */
    @Column(name = "arr_code_redis")
    private String arrCodeRedis;

    /**
     * 航司二字码redis
     */
    @Column(name = "air_ways_redis")
    private String airWaysRedis;

    /**
     * 里程redis
     */
    @Column(name = "distance_redis")
    private Integer distanceRedis;

    /**
     * 舱位redis
     */
    @Column(name = "seat_redis")
    private String seatRedis;

    /**
     * 价格
     */
    @Column(name = "carry_price_redis")
    private BigDecimal carryPriceRedis;

    /**
     * 航线类型   0-普通航线   1-热门航线redis
     */
    @Column(name = "airline_type_redis")
    private Byte airlineTypeRedis;

    /**
     * 开始日期redis
     */
    @Column(name = "take_off_effect_date_redis")
    private Date takeOffEffectDateRedis;

    /**
     * 结束日期redis
     */
    @Column(name = "take_off_expire_date_redis")
    private Date takeOffExpireDateRedis;

    /**
     * 舱位类型  1-经济舱  2-商务舱  3-头等舱  4-超级经济舱  5-特价舱redis
     */
    @Column(name = "seat_type_redis")
    private Byte seatTypeRedis;

    /**
     * 舱位等级  Y-经济舱   C-商务舱   F-头等舱redis
     */
    @Column(name = "seat_level_redis")
    private String seatLevelRedis;

    /**
     * 折扣（例0.95）redis
     */
    @Column(name = "discount_redis")
    private BigDecimal discountRedis;

    /**
     * FD中数据来源（0、FD。1、NFD）redis
     */
    @Column(name = "source_redis")
    private Byte sourceRedis;

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

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getDepCodeRedis() {
        return depCodeRedis;
    }

    public void setDepCodeRedis(String depCodeRedis) {
        this.depCodeRedis = depCodeRedis;
    }

    public String getArrCodeRedis() {
        return arrCodeRedis;
    }

    public void setArrCodeRedis(String arrCodeRedis) {
        this.arrCodeRedis = arrCodeRedis;
    }

    public String getAirWaysRedis() {
        return airWaysRedis;
    }

    public void setAirWaysRedis(String airWaysRedis) {
        this.airWaysRedis = airWaysRedis;
    }

    public Integer getDistanceRedis() {
        return distanceRedis;
    }

    public void setDistanceRedis(Integer distanceRedis) {
        this.distanceRedis = distanceRedis;
    }

    public String getSeatRedis() {
        return seatRedis;
    }

    public void setSeatRedis(String seatRedis) {
        this.seatRedis = seatRedis;
    }

    public BigDecimal getCarryPriceRedis() {
        return carryPriceRedis;
    }

    public void setCarryPriceRedis(BigDecimal carryPriceRedis) {
        this.carryPriceRedis = carryPriceRedis;
    }

    public Byte getAirlineTypeRedis() {
        return airlineTypeRedis;
    }

    public void setAirlineTypeRedis(Byte airlineTypeRedis) {
        this.airlineTypeRedis = airlineTypeRedis;
    }

    public Date getTakeOffEffectDateRedis() {
        return takeOffEffectDateRedis;
    }

    public void setTakeOffEffectDateRedis(Date takeOffEffectDateRedis) {
        this.takeOffEffectDateRedis = takeOffEffectDateRedis;
    }

    public Date getTakeOffExpireDateRedis() {
        return takeOffExpireDateRedis;
    }

    public void setTakeOffExpireDateRedis(Date takeOffExpireDateRedis) {
        this.takeOffExpireDateRedis = takeOffExpireDateRedis;
    }

    public Byte getSeatTypeRedis() {
        return seatTypeRedis;
    }

    public void setSeatTypeRedis(Byte seatTypeRedis) {
        this.seatTypeRedis = seatTypeRedis;
    }

    public String getSeatLevelRedis() {
        return seatLevelRedis;
    }

    public void setSeatLevelRedis(String seatLevelRedis) {
        this.seatLevelRedis = seatLevelRedis;
    }

    public BigDecimal getDiscountRedis() {
        return discountRedis;
    }

    public void setDiscountRedis(BigDecimal discountRedis) {
        this.discountRedis = discountRedis;
    }

    public Byte getSourceRedis() {
        return sourceRedis;
    }

    public void setSourceRedis(Byte sourceRedis) {
        this.sourceRedis = sourceRedis;
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
