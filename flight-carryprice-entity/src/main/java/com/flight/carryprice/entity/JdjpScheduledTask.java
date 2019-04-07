package com.flight.carryprice.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Description 定时任务表实体
 * @Author: qinhaoran1
 * @Date: 2019/3/14
 */
@Table(name = "jdjp_scheduled_task")
public class JdjpScheduledTask {

    /**
     * 计划任务表主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 任务类型 10-验证虚假舱位
     */
    @Column(name = "task_type")
    private Integer taskType;
    /**
     * 扫描状态 0-待扫描  1-扫描处理中  2-处理成功  3-处理失败
     */
    @Column(name = "scan_status")
    private Integer scanStatus;
    /**
     * 扫描次数
     */
    @Column(name = "scan_count")
    private Integer scanCount;
    /**
     * 扫描处理时间
     */
    @Column(name = "scan_time")
    private Date scanTime;
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
     * 计划任务执行相应信息
     */
    @Column(name = "response_info")
    private String responseInfo;
    /**
     * 备用参数1
     */
    @Column(name = "param1")
    private String param1;
    /**
     * 备用参数2
     */
    @Column(name = "param2")
    private String param2;
    /**
     * 备用参数3
     */
    @Column(name = "param3")
    private String param3;
    /**
     * 第一次的间隔时间
     */
    @Transient
    private Date zeroDate;
    /**
     * 每次间隔时间
     */
    @Transient
    private Date oneDate;
    /**
     * 每次查询获取数量
     */
    @Transient
    private Integer pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(Integer scanStatus) {
        this.scanStatus = scanStatus;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
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

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Date getZeroDate() {
        return zeroDate;
    }

    public void setZeroDate(Date zeroDate) {
        this.zeroDate = zeroDate;
    }

    public Date getOneDate() {
        return oneDate;
    }

    public void setOneDate(Date oneDate) {
        this.oneDate = oneDate;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
