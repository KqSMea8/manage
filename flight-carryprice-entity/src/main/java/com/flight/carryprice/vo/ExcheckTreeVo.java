package com.flight.carryprice.vo;

/**
 * 
 * @Description 复选框树节点对象
 * @see
 * @author lgp 商旅事业部
 * @version 1.0.0
 * @date 2016年9月9日 上午11:20:19
 * @updateTime
 */
public class ExcheckTreeVo {

	/**
	 * 节点id
	 */
	private String id;

	/**
	 * 父节点id，如果是根节点，则为0
	 */
	private String pId;

	/**
	 * 节点名称
	 */
	private String name;

	/**
	 * 是否打开节点
	 */
	private Boolean open;

	/**
	 * 是否默认选中
	 */
	private Boolean checked;

	/**
	 * 是否不显示checkbox
	 */
	private Boolean nocheck;

	public ExcheckTreeVo() {
	}

	/**
	 * 生成根节点
	 * 
	 * @param id
	 * @param name
	 * @param open
	 * @param checked
	 */
	public ExcheckTreeVo(String id, String name, Boolean open, Boolean checked, Boolean nocheck) {
		this.id = id;
		this.pId = "0";
		this.name = name;
		this.open = open;
		this.checked = checked;
		this.nocheck = nocheck;
	}

	/**
	 * 生成根节点，默认不选中,无checkbox
	 * 
	 * @param id
	 * @param name
	 * @param open
	 */
	public ExcheckTreeVo(String id, String name, Boolean open) {
		this(id, name, open, false, true);
	}

	/**
	 * 生成根节点，默认不选中，不打开,无checkbox
	 * 
	 * @param id
	 * @param name
	 */
	public ExcheckTreeVo(String id, String name) {
		this(id, name, false, false, true);
	}

	/**
	 * 生成非根节点
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @param open
	 * @param checked
	 */
	public ExcheckTreeVo(String id, String pId, String name, Boolean open, Boolean checked) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.checked = checked;
	}

	/**
	 * 生成非根节点，默认不打开
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @param open
	 */
	public ExcheckTreeVo(String id, String pId, String name, Boolean checked) {
		this(id, pId, name, false, checked);
	}

	/**
	 * 生成非根节点，默认不选中，不打开
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 */
	public ExcheckTreeVo(String id, String pId, String name) {
		this(id, pId, name, false, false);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
}
