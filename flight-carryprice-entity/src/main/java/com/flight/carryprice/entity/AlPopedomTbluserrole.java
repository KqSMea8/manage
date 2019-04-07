package com.flight.carryprice.entity;

import javax.persistence.*;

@Table(name = "al_popedom_tbluserrole")
public class AlPopedomTbluserrole {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户代号
	 */
	@Column(name = "UserCode")
	private Integer usercode;

	/**
	 * 角色代号
	 */
	@Column(name = "RoleCode")
	private Integer rolecode;

	/**
	 * 是否禁用(1：禁用/0：未禁用)
	 */
	@Column(name = "IsEnabled")
	private Short isenabled;

	public AlPopedomTbluserrole() {
	}

	public AlPopedomTbluserrole(Integer usercode, Integer rolecode) {
		this.usercode = usercode;
		this.rolecode = rolecode;
		this.isenabled = 0;
	}

	/**
	 * @return Id
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
	 * 获取用户代号
	 *
	 * @return UserCode - 用户代号
	 */
	public Integer getUsercode() {
		return usercode;
	}

	/**
	 * 设置用户代号
	 *
	 * @param usercode
	 *            用户代号
	 */
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}

	/**
	 * 获取角色代号
	 *
	 * @return RoleCode - 角色代号
	 */
	public Integer getRolecode() {
		return rolecode;
	}

	/**
	 * 设置角色代号
	 *
	 * @param rolecode
	 *            角色代号
	 */
	public void setRolecode(Integer rolecode) {
		this.rolecode = rolecode;
	}

	/**
	 * 获取是否禁用(1：禁用/0：未禁用)
	 *
	 * @return IsEnabled - 是否禁用(1：禁用/0：未禁用)
	 */
	public Short getIsenabled() {
		return isenabled;
	}

	/**
	 * 设置是否禁用(1：禁用/0：未禁用)
	 *
	 * @param isenabled
	 *            是否禁用(1：禁用/0：未禁用)
	 */
	public void setIsenabled(Short isenabled) {
		this.isenabled = isenabled;
	}
}