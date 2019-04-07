package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_nfd_update_policy")
public class JdjpNfdUpdatePolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

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
	 * 航司2字码
	 */
	@Column(name = "air_ways")
	private String airWays;

	/**
	 * 起飞时间或距当前日期点
	 */
	@Column(name = "dep_date")
	private String depDate;

	/**
	 * 计划执行更新策略时间（更新时间）
	 */
	@Column(name = "plan_quartz_time")
	private Date planQuartzTime;

	/**
	 * 计划执行更新策略时间（更新时间）-页面传数据用
	 */
	@Transient
	private String planTime;

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
	 * 操作人
	 */
	private String operator;

	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

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
	private Byte source;

	/**
	 * 航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
	 */
	private String airlines;

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
	 * 获取出发城市3字码
	 *
	 * @return dep_code - 出发城市3字码
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * 设置出发城市3字码
	 *
	 * @param depCode
	 *            出发城市3字码
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 获取到达城市3字码
	 *
	 * @return arr_code - 到达城市3字码
	 */
	public String getArrCode() {
		return arrCode;
	}

	/**
	 * 设置到达城市3字码
	 *
	 * @param arrCode
	 *            到达城市3字码
	 */
	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}

	/**
	 * 获取航司2字码
	 *
	 * @return air_ways - 航司2字码
	 */
	public String getAirWays() {
		return airWays;
	}

	/**
	 * 设置航司2字码
	 *
	 * @param airWays
	 *            航司2字码
	 */
	public void setAirWays(String airWays) {
		this.airWays = airWays;
	}

	/**
	 * @return dep_date
	 */
	public String getDepDate() {
		return depDate;
	}

	/**
	 * @param depDate
	 */
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	/**
	 * 获取计划执行更新策略时间（更新时间）
	 *
	 * @return plan_quartz_time - 计划执行更新策略时间（更新时间）
	 */
	public Date getPlanQuartzTime() {
		return planQuartzTime;
	}

	/**
	 * 设置计划执行更新策略时间（更新时间）
	 *
	 * @param planQuartzTime
	 *            计划执行更新策略时间（更新时间）
	 */
	public void setPlanQuartzTime(Date planQuartzTime) {
		this.planQuartzTime = planQuartzTime;
	}

	/**
	 * 获取同步状态 0-未同步 1-同步中 2-同步完成
	 *
	 * @return sync_status - 同步状态 0-未同步 1-同步中 2-同步完成
	 */
	public Byte getSyncStatus() {
		return syncStatus;
	}

	/**
	 * 设置同步状态 0-未同步 1-同步中 2-同步完成
	 *
	 * @param syncStatus
	 *            同步状态 0-未同步 1-同步中 2-同步完成
	 */
	public void setSyncStatus(Byte syncStatus) {
		this.syncStatus = syncStatus;
	}

	/**
	 * 获取航线类型 0-普通航线 1-热门航线
	 *
	 * @return airline_type - 航线类型 0-普通航线 1-热门航线
	 */
	public Byte getAirlineType() {
		return airlineType;
	}

	/**
	 * 设置航线类型 0-普通航线 1-热门航线
	 *
	 * @param airlineType
	 *            航线类型 0-普通航线 1-热门航线
	 */
	public void setAirlineType(Byte airlineType) {
		this.airlineType = airlineType;
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
	 * @param operator
	 *            操作人
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取备注信息
	 *
	 * @return remark - 备注信息
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注信息
	 *
	 * @param remark
	 *            备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取执行更新策略时间
	 *
	 * @return execute_quartz_time - 执行更新策略时间
	 */
	public Date getExecuteQuartzTime() {
		return executeQuartzTime;
	}

	/**
	 * 设置执行更新策略时间
	 *
	 * @param executeQuartzTime
	 *            执行更新策略时间
	 */
	public void setExecuteQuartzTime(Date executeQuartzTime) {
		this.executeQuartzTime = executeQuartzTime;
	}

	/**
	 * 获取执行完成时间
	 *
	 * @return execute_finish_time - 执行完成时间
	 */
	public Date getExecuteFinishTime() {
		return executeFinishTime;
	}

	/**
	 * 设置执行完成时间
	 *
	 * @param executeFinishTime
	 *            执行完成时间
	 */
	public void setExecuteFinishTime(Date executeFinishTime) {
		this.executeFinishTime = executeFinishTime;
	}
	/**
	 *  获取0 自动新增 1 手工新增
	 **/
	public Byte getSource() {
		return source;
	}
	/**
	 *  获取0 自动新增 1 手工新增
	 **/
	public void setSource(Byte source) {
		this.source = source;
	}

	/**
	 * 获取航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
	 *
	 * @return airlines - 航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
	 */
	public String getAirlines() {
		return airlines;
	}

	/**
	 * 设置航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
	 *
	 * @param airlines
	 *            航线，格式（航司:出发机场-到达机场,航司:出发机场-到达机场）
	 */
	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}

	/**
	 * @return planTime
	 */
	public String getPlanTime() {
		return planTime;
	}

	/**
	 * @param planTime
	 *            要设置的 planTime
	 */
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

}