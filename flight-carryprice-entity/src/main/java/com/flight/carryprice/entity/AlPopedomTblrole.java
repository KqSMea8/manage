package com.flight.carryprice.entity;

import javax.persistence.*;

@Table(name = "al_popedom_tblrole")
public class AlPopedomTblrole {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 应用系统代号
	 */
	@Column(name = "AppCode")
	private Integer appcode;

	/**
	 * 角色代号
	 */
	@Column(name = "RoleCode")
	private Integer rolecode;

	/**
	 * 角色名称
	 */
	@Column(name = "RoleName")
	private String rolename;

	/**
	 * 角色组代号
	 */
	@Column(name = "GroupsCode")
	private Integer groupscode;

	/**
	 * 是否禁用(1：禁用/1：未禁用)
	 */
	@Column(name = "IsEnabled")
	private Short isenabled;

	public AlPopedomTblrole() {

	}

	public AlPopedomTblrole(Integer appcode, Integer rolecode, String rolename) {
		this.appcode = appcode;
		this.rolecode = rolecode;
		this.rolename = rolename;
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
	 * 获取应用系统代号
	 *
	 * @return AppCode - 应用系统代号
	 */
	public Integer getAppcode() {
		return appcode;
	}

	/**
	 * 设置应用系统代号
	 *
	 * @param appcode
	 *            应用系统代号
	 */
	public void setAppcode(Integer appcode) {
		this.appcode = appcode;
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
	 * 获取角色名称
	 *
	 * @return RoleName - 角色名称
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * 设置角色名称
	 *
	 * @param rolename
	 *            角色名称
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 获取角色组代号
	 *
	 * @return GroupsCode - 角色组代号
	 */
	public Integer getGroupscode() {
		return groupscode;
	}

	/**
	 * 设置角色组代号
	 *
	 * @param groupscode
	 *            角色组代号
	 */
	public void setGroupscode(Integer groupscode) {
		this.groupscode = groupscode;
	}

	/**
	 * 获取是否禁用(1：禁用/1：未禁用)
	 *
	 * @return IsEnabled - 是否禁用(1：禁用/1：未禁用)
	 */
	public Short getIsenabled() {
		return isenabled;
	}

	/**
	 * 设置是否禁用(1：禁用/1：未禁用)
	 *
	 * @param isenabled
	 *            是否禁用(1：禁用/1：未禁用)
	 */
	public void setIsenabled(Short isenabled) {
		this.isenabled = isenabled;
	}
}