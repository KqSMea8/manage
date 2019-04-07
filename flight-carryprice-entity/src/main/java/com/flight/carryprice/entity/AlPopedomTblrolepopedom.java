package com.flight.carryprice.entity;

import javax.persistence.*;

@Table(name = "al_popedom_tblrolepopedom")
public class AlPopedomTblrolepopedom {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 引用角色表(tblRole)中RoleCode
	 */
	@Column(name = "RoleCode")
	private Integer rolecode;

	/**
	 * 引用菜单表(tblMenu)中MenuCode
	 */
	@Column(name = "MenuCode")
	private Integer menucode;

	/**
	 * 引用模块表(tblModule)中ModuleCode
	 */
	@Column(name = "ModuleCode")
	private Integer modulecode;

	/**
	 * 引用控件表(tblControl)中ControlCode(暂没用)
	 */
	@Column(name = "ControlCode")
	private Integer controlcode;

	public AlPopedomTblrolepopedom() {
	}

	public AlPopedomTblrolepopedom(Integer rolecode, Integer menucode, Integer modulecode) {
		this.rolecode = rolecode;
		this.menucode = menucode;
		this.modulecode = modulecode;
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
	 * 获取引用角色表(tblRole)中RoleCode
	 *
	 * @return RoleCode - 引用角色表(tblRole)中RoleCode
	 */
	public Integer getRolecode() {
		return rolecode;
	}

	/**
	 * 设置引用角色表(tblRole)中RoleCode
	 *
	 * @param rolecode
	 *            引用角色表(tblRole)中RoleCode
	 */
	public void setRolecode(Integer rolecode) {
		this.rolecode = rolecode;
	}

	/**
	 * 获取引用菜单表(tblMenu)中MenuCode
	 *
	 * @return MenuCode - 引用菜单表(tblMenu)中MenuCode
	 */
	public Integer getMenucode() {
		return menucode;
	}

	/**
	 * 设置引用菜单表(tblMenu)中MenuCode
	 *
	 * @param menucode
	 *            引用菜单表(tblMenu)中MenuCode
	 */
	public void setMenucode(Integer menucode) {
		this.menucode = menucode;
	}

	/**
	 * 获取引用模块表(tblModule)中ModuleCode
	 *
	 * @return ModuleCode - 引用模块表(tblModule)中ModuleCode
	 */
	public Integer getModulecode() {
		return modulecode;
	}

	/**
	 * 设置引用模块表(tblModule)中ModuleCode
	 *
	 * @param modulecode
	 *            引用模块表(tblModule)中ModuleCode
	 */
	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}

	/**
	 * 获取引用控件表(tblControl)中ControlCode(暂没用)
	 *
	 * @return ControlCode - 引用控件表(tblControl)中ControlCode(暂没用)
	 */
	public Integer getControlcode() {
		return controlcode;
	}

	/**
	 * 设置引用控件表(tblControl)中ControlCode(暂没用)
	 *
	 * @param controlcode
	 *            引用控件表(tblControl)中ControlCode(暂没用)
	 */
	public void setControlcode(Integer controlcode) {
		this.controlcode = controlcode;
	}
}