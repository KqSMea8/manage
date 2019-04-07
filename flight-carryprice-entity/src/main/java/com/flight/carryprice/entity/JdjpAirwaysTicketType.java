package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_airways_ticket_type")
public class JdjpAirwaysTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 航空公司
     */
    @Column(name = "air_ways")
    private String airways;

    /**
     * 开关 1.BSP 2.B2B 3.关闭
     */
    @Column(name = "swith")
    private Byte swith;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 操作时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否急速出票，0：否，1：是
     */
    @Column(name = "is_speed_ticket")
    private Byte isSpeedTicket;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取航空公司
     *
     * @return airways - 航空公司
     */
    public String getAirways() {
        return airways;
    }

    /**
     * 设置航空公司
     *
     * @param airways 航空公司
     */
    public void setAirways(String airways) {
        this.airways = airways;
    }

    /**
     * 获取开关 1.BSP 2.B2B 3.关闭
     *
     * @return swith - 开关 1.BSP 2.B2B 3.关闭
     */
    public Byte getSwith() {
        return swith;
    }

    /**
     * 设置开关 1.BSP 2.B2B 3.关闭
     *
     * @param swith 开关 1.BSP 2.B2B 3.关闭
     */
    public void setSwith(Byte swith) {
        this.swith = swith;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsSpeedTicket() {
        return isSpeedTicket;
    }

    public void setIsSpeedTicket(Byte isSpeedTicket) {
        this.isSpeedTicket = isSpeedTicket;
    }
}