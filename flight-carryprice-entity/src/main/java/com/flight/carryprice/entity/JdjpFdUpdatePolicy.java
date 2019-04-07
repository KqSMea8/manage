package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_fd_update_policy")
public class JdjpFdUpdatePolicy {

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
	 * 起飞时间
	 */
	@Column(name = "dep_date")
	private Date depDate;

	/**
	 * 计划执行更新策略时间（更新时间）
	 */
	@Column(name = "plan_quartz_time")
	private Date planQuartzTime;

	/**
	 * 同步状态 0-未同步 1-同步中 2-同步完成
	 */
	@Column(name = "sync_status")
	private Byte syncStatus;

	/**
	 * 航线类型 0-普通航线 1-热门航线
	 */
	@Column(name = "airline_type")
	private Byte airlineType;

	/**
	 * 备注信息
	 */
	private String remark;

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
	/**
	 * 计划执行更新策略时间（更新时间）-页面传时间用
	 */
	@Transient
	private String planTime;

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

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public Date getPlanQuartzTime() {
		return planQuartzTime;
	}

	public void setPlanQuartzTime(Date planQuartzTime) {
		this.planQuartzTime = planQuartzTime;
	}

	public Byte getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Byte syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Byte getAirlineType() {
		return airlineType;
	}

	public void setAirlineType(Byte airlineType) {
		this.airlineType = airlineType;
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

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

}