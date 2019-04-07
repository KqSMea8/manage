package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_carry_price_task")
public class JdjpCarryPriceTask {

	/**
	 * 任务id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
	 */
	@Column(name = "query_condition")
	private String queryCondition;

	/**
	 * 下次更新开始序号
	 */
	@Column(name = "next_data")
	private Integer nextData;

	/**
	 * 一次批量操作的条数
	 */
	@Column(name = "operate_no")
	private Integer operateNo;

	/**
	 * 更新开始时间
	 */
	@Column(name = "update_begin_time")
	private Date updateBeginTime;

	/**
	 * 更新结束时间
	 */
	@Column(name = "update_end_time")
	private Date updateEndTime;

	/**
	 * 运价类型(0:FD，1:NFD，2:SSD)
	 */
	@Column(name = "carry_price_type")
	private Byte carryPriceType;

	/**
	 * 任务类型(0:自动定时，1手动)
	 */
	@Column(name = "task_type")
	private Byte taskType;

	/**
	 * 状态(0待执行、1执行中、2中断、3执行完成)
	 */
	@Column(name = "task_status")
	private Byte taskStatus;

	/**
	 * 备注
	 */
	@Column(name = "task_remark")
	private String taskRemark;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 操作时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private String operator;

	/**
	 * 获取任务id
	 *
	 * @return id - 任务id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置任务id
	 *
	 * @param id
	 *            任务id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
	 *
	 * @return query_condition - 查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
	 */
	public String getQueryCondition() {
		return queryCondition;
	}

	/**
	 * 设置查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
	 *
	 * @param queryCondition
	 *            查询条件，格式如：航司-CA;出发-PEK;到达-SHA;舱位-Y
	 */
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	/**
	 * 获取下次更新开始序号
	 *
	 * @return next_data - 下次更新开始序号
	 */
	public Integer getNextData() {
		return nextData;
	}

	/**
	 * 设置下次更新开始序号
	 *
	 * @param nextData
	 *            下次更新开始序号
	 */
	public void setNextData(Integer nextData) {
		this.nextData = nextData;
	}

	/**
	 * 获取一次批量操作的条数
	 *
	 * @return operate_no - 一次批量操作的条数
	 */
	public Integer getOperateNo() {
		return operateNo;
	}

	/**
	 * 设置一次批量操作的条数
	 *
	 * @param operateNo
	 *            一次批量操作的条数
	 */
	public void setOperateNo(Integer operateNo) {
		this.operateNo = operateNo;
	}

	/**
	 * 获取更新开始时间
	 *
	 * @return update_begin_time - 更新开始时间
	 */
	public Date getUpdateBeginTime() {
		return updateBeginTime;
	}

	/**
	 * 设置更新开始时间
	 *
	 * @param updateBeginTime
	 *            更新开始时间
	 */
	public void setUpdateBeginTime(Date updateBeginTime) {
		this.updateBeginTime = updateBeginTime;
	}

	/**
	 * 获取更新结束时间
	 *
	 * @return update_end_time - 更新结束时间
	 */
	public Date getUpdateEndTime() {
		return updateEndTime;
	}

	/**
	 * 设置更新结束时间
	 *
	 * @param updateEndTime
	 *            更新结束时间
	 */
	public void setUpdateEndTime(Date updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	/**
	 * 获取运价类型(0:FD，1:NFD，2:SSD)
	 *
	 * @return carryprice_type - 运价类型(0:FD，1:NFD，2:SSD)
	 */
	public Byte getCarryPriceType() {
		return carryPriceType;
	}

	/**
	 * 设置运价类型(0:FD，1:NFD，2:SSD)
	 *
	 * @param carryPriceType
	 *            运价类型(0:FD，1:NFD，2:SSD)
	 */
	public void setCarryPriceType(Byte carryPriceType) {
		this.carryPriceType = carryPriceType;
	}

	/**
	 * 获取任务类型(0:手工，1定时)
	 *
	 * @return task_type - 任务类型(0:手工，1定时)
	 */
	public Byte getTaskType() {
		return taskType;
	}

	/**
	 * 设置任务类型(0:手工，1定时)
	 *
	 * @param taskType
	 *            任务类型(0:手工，1定时)
	 */
	public void setTaskType(Byte taskType) {
		this.taskType = taskType;
	}

	/**
	 * 获取状态(0待执行、1执行中、2中断、3执行完成)
	 *
	 * @return task_status - 状态(0待执行、1执行中、2中断、3执行完成)
	 */
	public Byte getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置状态(0待执行、1执行中、2中断、3执行完成)
	 *
	 * @param taskStatus
	 *            状态(0待执行、1执行中、2中断、3执行完成)
	 */
	public void setTaskStatus(Byte taskStatus) {
		this.taskStatus = taskStatus;
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
	 * 获取操作时间
	 *
	 * @return motify_time - 操作时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置操作时间
	 *
	 * @param updateTime
	 *            操作时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取备注
	 *
	 * @return task_remark - 备注
	 */
	public String getTaskRemark() {
		return taskRemark;
	}

	/**
	 * 设置备注
	 *
	 * @param taskRemark
	 *            备注
	 */
	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	/**
	 * 获取创建时间
	 *
	 * @return createTime - 备注
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}