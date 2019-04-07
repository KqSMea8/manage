package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "jdjp_limit_flight_info")
public class JdjpLimitFlightInfo {
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 航司二字码
	 */
	@Column(name = "air_ways")
	private String airWays;

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
	 * 舱位
	 */
	@Column(name = "seat")
	private String seat;

	/**
	 * 航班号
	 */
	@Column(name = "flight_no")
	private String flightNo;

	/**
	 * 限制有效时间起始
	 */
	@Column(name = "limit_time_begin")
	private Date limitTimeBegin;

	/**
	 * 限制航班有效时间截止
	 */
	@Column(name = "limit_time_end")
	private Date limitTimeEnd;

	/**
	 * 限制航班销售有效时间开始
	 */
	@Column(name = "limit_sale_time_begin")
	private Date limitSaleTimeBegin;

	/**
	 * 限制航班销售有效时间截止
	 */
	@Column(name = "limit_sale_time_end")
	private Date limitSaleTimeEnd;

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
	 * 是否为虚假舱位 0-否 1-是
	 */
	@Column(name = "is_false_seat")
	private Byte isFalseSeat;

	/**
	 * 操作人
	 */
	private String operator;

	/**
	 * 获取主键
	 *
	 * @return id - 主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键
	 *
	 * @param id
	 *            主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取航司二字码
	 *
	 * @return air_ways - 航司二字码
	 */
	public String getAirWays() {
		return airWays;
	}

	/**
	 * 设置航司二字码
	 *
	 * @param airWays
	 *            航司二字码
	 */
	public void setAirWays(String airWays) {
		this.airWays = airWays;
	}

	/**
	 * 获取出发城市三字码
	 *
	 * @return dep_code - 出发城市三字码
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * 设置出发城市三字码
	 *
	 * @param depCode
	 *            出发城市三字码
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 获取到达城市三字码
	 *
	 * @return arr_code - 到达城市三字码
	 */
	public String getArrCode() {
		return arrCode;
	}

	/**
	 * 设置到达城市三字码
	 *
	 * @param arrCode
	 *            到达城市三字码
	 */
	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}

	/**
	 * 获取航班号
	 *
	 * @return flight_no - 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * 设置航班号
	 *
	 * @param flightNo
	 *            航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 获取限制有效时间起始
	 *
	 * @return limit_time_begin - 限制有效时间起始
	 */
	public Date getLimitTimeBegin() {
		return limitTimeBegin;
	}

	/**
	 * 设置限制有效时间起始
	 *
	 * @param limitTimeBegin
	 *            限制有效时间起始
	 */
	public void setLimitTimeBegin(Date limitTimeBegin) {
		this.limitTimeBegin = limitTimeBegin;
	}

	/**
	 * 获取限制航班有效时间截止
	 *
	 * @return limit_time_end - 限制航班有效时间截止
	 */
	public Date getLimitTimeEnd() {
		return limitTimeEnd;
	}

	/**
	 * 设置限制航班有效时间截止
	 *
	 * @param limitTimeEnd
	 *            限制航班有效时间截止
	 */
	public void setLimitTimeEnd(Date limitTimeEnd) {
		this.limitTimeEnd = limitTimeEnd;
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
	 * 获取更新时间
	 *
	 * @return update_time - 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 *
	 * @param updateTime
	 *            更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	/**
	 * 获取限制销售时间开始
	 *
	 * @param limitSaleTimeBegin
	 *            限制销售时间开始
	 */
	public Date getLimitSaleTimeBegin() {
		return limitSaleTimeBegin;
	}

	/**
	 * 获取限制销售时间开始
	 *
	 * @param limitSaleTimeBegin
	 *            限制销售时间开始
	 */
	public void setLimitSaleTimeBegin(Date limitSaleTimeBegin) {
		this.limitSaleTimeBegin = limitSaleTimeBegin;
	}

	/**
	 * 获取限制销售时间结束
	 *
	 * @param limitSaleTimeEnd
	 *            限制销售时间结束
	 */
	public Date getLimitSaleTimeEnd() {
		return limitSaleTimeEnd;
	}

	/**
	 * 设置限制销售时间结束
	 *
	 * @param limitSaleTimeEnd
	 *            限制销售时间结束
	 */
	public void setLimitSaleTimeEnd(Date limitSaleTimeEnd) {
		this.limitSaleTimeEnd = limitSaleTimeEnd;
	}

	/**
	 * 获取是否为虚假舱位
	 *
	 * @param isFalseSeat
	 *            是否为虚假舱位
	 */
	public Byte getIsFalseSeat() {
		return isFalseSeat;
	}

	/**
	 * 设置是否为虚假舱位
	 *
	 * @param isFalseSeat
	 *            是否为虚假舱位
	 */
	public void setIsFalseSeat(Byte isFalseSeat) {
		this.isFalseSeat = isFalseSeat;
	}

}