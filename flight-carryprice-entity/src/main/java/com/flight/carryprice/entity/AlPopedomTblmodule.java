package com.flight.carryprice.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "al_popedom_tblmodule")
public class AlPopedomTblmodule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956930231046187996L;

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
	 * 菜单代号
	 */
	@Column(name = "MenuCode")
	private Integer menucode;

	/**
	 * 模块代号（三位）
	 */
	@Column(name = "ModuleCode")
	private Integer modulecode;

	/**
	 * 模块名称
	 */
	@Column(name = "ModuleName")
	private String modulename;

	/**
	 * 模块样式
	 */
	@Column(name = "ModuleNameStyle")
	private String modulenamestyle;

	/**
	 * 跳转目标
	 */
	@Column(name = "Target")
	private String target;

	/**
	 * 排序序号
	 */
	@Column(name = "ModuleSort")
	private Short modulesort;

	/**
	 * 父模块标识
	 */
	@Column(name = "ParentId")
	private Integer parentid;

	/**
	 * 是否开启模块(0:开启/1:关闭)
	 */
	@Column(name = "IsOpen")
	private Short isopen;

	/**
	 * 是否禁用(0:未禁用/1:已禁用/2:已删除)
	 */
	@Column(name = "IsEnabled")
	private Short isenabled;

	/**
	 * 模块地址（弃用）
	 */
	@Column(name = "ModuleURL")
	private String moduleurl;

	/**
	 * 模块地址
	 */
	@Column(name = "NewModuleURL")
	private String newmoduleurl;

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
	 * 获取菜单代号
	 *
	 * @return MenuCode - 菜单代号
	 */
	public Integer getMenucode() {
		return menucode;
	}

	/**
	 * 设置菜单代号
	 *
	 * @param menucode
	 *            菜单代号
	 */
	public void setMenucode(Integer menucode) {
		this.menucode = menucode;
	}

	/**
	 * 获取模块代号（三位）
	 *
	 * @return ModuleCode - 模块代号（三位）
	 */
	public Integer getModulecode() {
		return modulecode;
	}

	/**
	 * 设置模块代号（三位）
	 *
	 * @param modulecode
	 *            模块代号（三位）
	 */
	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}

	/**
	 * 获取模块名称
	 *
	 * @return ModuleName - 模块名称
	 */
	public String getModulename() {
		return modulename;
	}

	/**
	 * 设置模块名称
	 *
	 * @param modulename
	 *            模块名称
	 */
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	/**
	 * 获取模块样式
	 *
	 * @return ModuleNameStyle - 模块样式
	 */
	public String getModulenamestyle() {
		return modulenamestyle;
	}

	/**
	 * 设置模块样式
	 *
	 * @param modulenamestyle
	 *            模块样式
	 */
	public void setModulenamestyle(String modulenamestyle) {
		this.modulenamestyle = modulenamestyle;
	}

	/**
	 * 获取跳转目标
	 *
	 * @return Target - 跳转目标
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置跳转目标
	 *
	 * @param target
	 *            跳转目标
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 获取排序序号
	 *
	 * @return ModuleSort - 排序序号
	 */
	public Short getModulesort() {
		return modulesort;
	}

	/**
	 * 设置排序序号
	 *
	 * @param modulesort
	 *            排序序号
	 */
	public void setModulesort(Short modulesort) {
		this.modulesort = modulesort;
	}

	/**
	 * 获取父模块标识
	 *
	 * @return ParentId - 父模块标识
	 */
	public Integer getParentid() {
		return parentid;
	}

	/**
	 * 设置父模块标识
	 *
	 * @param parentid
	 *            父模块标识
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	/**
	 * 获取是否开启模块(0:开启/1:关闭)
	 *
	 * @return IsOpen - 是否开启模块(0:开启/1:关闭)
	 */
	public Short getIsopen() {
		return isopen;
	}

	/**
	 * 设置是否开启模块(0:开启/1:关闭)
	 *
	 * @param isopen
	 *            是否开启模块(0:开启/1:关闭)
	 */
	public void setIsopen(Short isopen) {
		this.isopen = isopen;
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

	/**
	 * 获取模块地址（弃用）
	 *
	 * @return ModuleURL - 模块地址（弃用）
	 */
	public String getModuleurl() {
		return moduleurl;
	}

	/**
	 * 设置模块地址（弃用）
	 *
	 * @param moduleurl
	 *            模块地址（弃用）
	 */
	public void setModuleurl(String moduleurl) {
		this.moduleurl = moduleurl;
	}

	/**
	 * 获取模块地址
	 *
	 * @return NewModuleURL - 模块地址
	 */
	public String getNewmoduleurl() {
		return newmoduleurl;
	}

	/**
	 * 设置模块地址
	 *
	 * @param newmoduleurl
	 *            模块地址
	 */
	public void setNewmoduleurl(String newmoduleurl) {
		this.newmoduleurl = newmoduleurl;
	}
}