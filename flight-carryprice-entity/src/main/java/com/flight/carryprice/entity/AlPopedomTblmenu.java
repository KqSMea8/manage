package com.flight.carryprice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "al_popedom_tblmenu")
public class AlPopedomTblmenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6471180032158179328L;

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
	 * 菜单编号（只能二位）
	 */
	@Column(name = "MenuCode")
	private Integer menucode;

	/**
	 * 菜单名称
	 */
	@Column(name = "MenuName")
	private String menuname;

	/**
	 * 菜单样式
	 */
	@Column(name = "MenuNameStyle")
	private String menunamestyle;

	/**
	 * 排序序号
	 */
	@Column(name = "MenuSort")
	private Short menusort;

	/**
	 * 是否禁用(0:未禁用/1:已禁用/2:已删除)
	 */
	@Column(name = "IsEnabled")
	private Short isenabled;

	@Transient
	private List<AlPopedomTblmodule> moduleList;

	@Transient
	private String isCurrent;

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
	 * 获取菜单编号（只能二位）
	 *
	 * @return MenuCode - 菜单编号（只能二位）
	 */
	public Integer getMenucode() {
		return menucode;
	}

	/**
	 * 设置菜单编号（只能二位）
	 *
	 * @param menucode
	 *            菜单编号（只能二位）
	 */
	public void setMenucode(Integer menucode) {
		this.menucode = menucode;
	}

	/**
	 * 获取菜单名称
	 *
	 * @return MenuName - 菜单名称
	 */
	public String getMenuname() {
		return menuname;
	}

	/**
	 * 设置菜单名称
	 *
	 * @param menuname
	 *            菜单名称
	 */
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	/**
	 * 获取菜单样式
	 *
	 * @return MenuNameStyle - 菜单样式
	 */
	public String getMenunamestyle() {
		return menunamestyle;
	}

	/**
	 * 设置菜单样式
	 *
	 * @param menunamestyle
	 *            菜单样式
	 */
	public void setMenunamestyle(String menunamestyle) {
		this.menunamestyle = menunamestyle;
	}

	/**
	 * 获取排序序号
	 *
	 * @return MenuSort - 排序序号
	 */
	public Short getMenusort() {
		return menusort;
	}

	/**
	 * 设置排序序号
	 *
	 * @param menusort
	 *            排序序号
	 */
	public void setMenusort(Short menusort) {
		this.menusort = menusort;
	}

	/**
	 * 获取是否禁用(0:未禁用/1:已禁用/2:已删除)
	 *
	 * @return IsEnabled - 是否禁用(0:未禁用/1:已禁用/2:已删除)
	 */
	public Short getIsenabled() {
		return isenabled;
	}

	/**
	 * 设置是否禁用(0:未禁用/1:已禁用/2:已删除)
	 *
	 * @param isenabled
	 *            是否禁用(0:未禁用/1:已禁用/2:已删除)
	 */
	public void setIsenabled(Short isenabled) {
		this.isenabled = isenabled;
	}

	public List<AlPopedomTblmodule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<AlPopedomTblmodule> moduleList) {
		this.moduleList = moduleList;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
}