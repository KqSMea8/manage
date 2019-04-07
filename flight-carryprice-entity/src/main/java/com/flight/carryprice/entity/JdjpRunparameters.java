package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_runparameters")
public class JdjpRunparameters {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 当前参数值
     */
    private String value;

    /**
     * 说明
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;

    /**
     *  操作时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     *  创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取当前参数值
     *
     * @return value - 当前参数值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置当前参数值
     *
     * @param value 当前参数值
     */
    public void setValue(String value) {
        this.value = value;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}