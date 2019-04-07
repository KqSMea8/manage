package com.flight.carryprice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "al_users")
public class AlUsers {

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
	 * 用户名
	 */
	@Column(name = "UserName")
	private String username;

	/**
	 * 联系人
	 */
	@Column(name = "ChargePerson")
	private String chargeperson;

	/**
	 * 密文密码
	 */
	private String password;

	/**
	 * 支付密码
	 */
	@Column(name = "PayPD")
	private String paypd;

	/**
	 * 状态0:等待审核 1 :正常 2:锁定
	 */
	@Column(name = "Status")
	private Integer status;

	/**
	 * 电话
	 */
	@Column(name = "Tel")
	private String tel;

	/**
	 * 传真
	 */
	@Column(name = "Fax")
	private String fax;

	/**
	 * 邮箱
	 */
	@Column(name = "Email")
	private String email;

	/**
	 * qq
	 */
	@Column(name = "QQ")
	private String qq;

	/**
	 * msn
	 */
	@Column(name = "Msn")
	private String msn;

	/**
	 * 是否是默认的联系的人
	 */
	@Column(name = "IsDefault")
	private Integer isdefault;

	/**
	 * 手机
	 */
	@Column(name = "Mobile")
	private String mobile;

	/**
	 * 公司id
	 */
	@Column(name = "CompanyId")
	private Integer companyid;

	/**
	 * ip
	 */
	@Column(name = "IP")
	private String ip;

	/**
	 * 创建时间
	 */
	@Column(name = "CreateTime")
	private Date createtime;

	/**
	 * 应用系统id
	 */
	@Column(name = "AppId")
	private Integer appid;

	/**
	 * 工号
	 */
	@Column(name = "Worknumber")
	private String worknumber;

	/**
	 * 分机号
	 */
	@Column(name = "ExtNumber")
	private String extnumber;

	/**
	 * 内部员工职位
	 */
	@Column(name = "Post")
	private String post;

	/**
	 * 管理账户的二次认证码
	 */
	@Column(name = "SecondaryAuthentication")
	private String secondaryauthentication;

	/**
	 * 用户级别 A.B.C.D.......
	 */
	@Column(name = "UserLevel")
	private String userlevel;

	/**
	 * 政策密码
	 */
	@Column(name = "RatePwd")
	private String ratepwd;

	/**
	 * 绑定供票商的类型[1:出票/2:退票/3:升舱/4:改签]
	 */
	@Column(name = "BindType")
	private String bindtype;

	/**
	 * 绑定供票商账号
	 */
	@Column(name = "BindProvider")
	private String bindprovider;

	@Column(name = "AgencyCode")
	private Integer agencycode;

	@Column(name = "IsCompany")
	private Integer iscompany;

	@Column(name = "RegisterUrl")
	private String registerurl;

	@Column(name = "TheSpeed")
	private String thespeed;

	@Column(name = "LastLoginTime")
	private Date lastlogintime;

	@Column(name = "LastLoginCity")
	private String lastlogincity;

	@Column(name = "LastLoginIp")
	private String lastloginip;

	/**
	 * 政策失效天数统计
	 */
	@Column(name = "RateInvalidDay")
	private Integer rateinvalidday;

	/**
	 * 接口签名所需的key
	 */
	@Column(name = "sign_key")
	private String signKey;

	/**
	 * AES加密所需的key
	 */
	@Column(name = "aes_key")
	private String aesKey;

	/**
	 * 本账号所属公司
	 */
	private String company;

	/**
	 * 备注
	 */
	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "IUrl")
	private String iurl;

	/*********** 扩展属性 *********/
	@Transient
	private List<AlPopedomTblrole> roleList;

	@Transient
	private Integer rolecode;

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
	 * 获取用户名
	 *
	 * @return UserName - 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取联系人
	 *
	 * @return ChargePerson - 联系人
	 */
	public String getChargeperson() {
		return chargeperson;
	}

	/**
	 * 设置联系人
	 *
	 * @param chargeperson
	 *            联系人
	 */
	public void setChargeperson(String chargeperson) {
		this.chargeperson = chargeperson;
	}

	/**
	 * 获取密文密码
	 *
	 * @return password - 密文密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密文密码
	 *
	 * @param password
	 *            密文密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取支付密码
	 *
	 * @return PayPD - 支付密码
	 */
	public String getPaypd() {
		return paypd;
	}

	/**
	 * 设置支付密码
	 *
	 * @param paypd
	 *            支付密码
	 */
	public void setPaypd(String paypd) {
		this.paypd = paypd;
	}

	/**
	 * 获取状态0:等待审核 1 :正常 2:锁定
	 *
	 * @return Status - 状态0:等待审核 1 :正常 2:锁定
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态0:等待审核 1 :正常 2:锁定
	 *
	 * @param status
	 *            状态0:等待审核 1 :正常 2:锁定
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取电话
	 *
	 * @return Tel - 电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置电话
	 *
	 * @param tel
	 *            电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取传真
	 *
	 * @return Fax - 传真
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置传真
	 *
	 * @param fax
	 *            传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 获取邮箱
	 *
	 * @return Email - 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 *
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取qq
	 *
	 * @return QQ - qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 设置qq
	 *
	 * @param qq
	 *            qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 获取msn
	 *
	 * @return Msn - msn
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * 设置msn
	 *
	 * @param msn
	 *            msn
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * 获取是否是默认的联系的人
	 *
	 * @return IsDefault - 是否是默认的联系的人
	 */
	public Integer getIsdefault() {
		return isdefault;
	}

	/**
	 * 设置是否是默认的联系的人
	 *
	 * @param isdefault
	 *            是否是默认的联系的人
	 */
	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	/**
	 * 获取手机
	 *
	 * @return Mobile - 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 *
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取公司id
	 *
	 * @return CompanyId - 公司id
	 */
	public Integer getCompanyid() {
		return companyid;
	}

	/**
	 * 设置公司id
	 *
	 * @param companyid
	 *            公司id
	 */
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	/**
	 * 获取ip
	 *
	 * @return IP - ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置ip
	 *
	 * @param ip
	 *            ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取创建时间
	 *
	 * @return CreateTime - 创建时间
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createtime
	 *            创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 获取应用系统id
	 *
	 * @return AppId - 应用系统id
	 */
	public Integer getAppid() {
		return appid;
	}

	/**
	 * 设置应用系统id
	 *
	 * @param appid
	 *            应用系统id
	 */
	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	/**
	 * 获取工号
	 *
	 * @return Worknumber - 工号
	 */
	public String getWorknumber() {
		return worknumber;
	}

	/**
	 * 设置工号
	 *
	 * @param worknumber
	 *            工号
	 */
	public void setWorknumber(String worknumber) {
		this.worknumber = worknumber;
	}

	/**
	 * 获取分机号
	 *
	 * @return ExtNumber - 分机号
	 */
	public String getExtnumber() {
		return extnumber;
	}

	/**
	 * 设置分机号
	 *
	 * @param extnumber
	 *            分机号
	 */
	public void setExtnumber(String extnumber) {
		this.extnumber = extnumber;
	}

	/**
	 * 获取内部员工职位
	 *
	 * @return Post - 内部员工职位
	 */
	public String getPost() {
		return post;
	}

	/**
	 * 设置内部员工职位
	 *
	 * @param post
	 *            内部员工职位
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * 获取管理账户的二次认证码
	 *
	 * @return SecondaryAuthentication - 管理账户的二次认证码
	 */
	public String getSecondaryauthentication() {
		return secondaryauthentication;
	}

	/**
	 * 设置管理账户的二次认证码
	 *
	 * @param secondaryauthentication
	 *            管理账户的二次认证码
	 */
	public void setSecondaryauthentication(String secondaryauthentication) {
		this.secondaryauthentication = secondaryauthentication;
	}

	/**
	 * 获取用户级别 A.B.C.D.......
	 *
	 * @return UserLevel - 用户级别 A.B.C.D.......
	 */
	public String getUserlevel() {
		return userlevel;
	}

	/**
	 * 设置用户级别 A.B.C.D.......
	 *
	 * @param userlevel
	 *            用户级别 A.B.C.D.......
	 */
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	/**
	 * 获取政策密码
	 *
	 * @return RatePwd - 政策密码
	 */
	public String getRatepwd() {
		return ratepwd;
	}

	/**
	 * 设置政策密码
	 *
	 * @param ratepwd
	 *            政策密码
	 */
	public void setRatepwd(String ratepwd) {
		this.ratepwd = ratepwd;
	}

	/**
	 * 获取绑定供票商的类型[1:出票/2:退票/3:升舱/4:改签]
	 *
	 * @return BindType - 绑定供票商的类型[1:出票/2:退票/3:升舱/4:改签]
	 */
	public String getBindtype() {
		return bindtype;
	}

	/**
	 * 设置绑定供票商的类型[1:出票/2:退票/3:升舱/4:改签]
	 *
	 * @param bindtype
	 *            绑定供票商的类型[1:出票/2:退票/3:升舱/4:改签]
	 */
	public void setBindtype(String bindtype) {
		this.bindtype = bindtype;
	}

	/**
	 * 获取绑定供票商账号
	 *
	 * @return BindProvider - 绑定供票商账号
	 */
	public String getBindprovider() {
		return bindprovider;
	}

	/**
	 * 设置绑定供票商账号
	 *
	 * @param bindprovider
	 *            绑定供票商账号
	 */
	public void setBindprovider(String bindprovider) {
		this.bindprovider = bindprovider;
	}

	/**
	 * @return AgencyCode
	 */
	public Integer getAgencycode() {
		return agencycode;
	}

	/**
	 * @param agencycode
	 */
	public void setAgencycode(Integer agencycode) {
		this.agencycode = agencycode;
	}

	/**
	 * @return IsCompany
	 */
	public Integer getIscompany() {
		return iscompany;
	}

	/**
	 * @param iscompany
	 */
	public void setIscompany(Integer iscompany) {
		this.iscompany = iscompany;
	}

	/**
	 * @return RegisterUrl
	 */
	public String getRegisterurl() {
		return registerurl;
	}

	/**
	 * @param registerurl
	 */
	public void setRegisterurl(String registerurl) {
		this.registerurl = registerurl;
	}

	/**
	 * @return TheSpeed
	 */
	public String getThespeed() {
		return thespeed;
	}

	/**
	 * @param thespeed
	 */
	public void setThespeed(String thespeed) {
		this.thespeed = thespeed;
	}

	/**
	 * @return LastLoginTime
	 */
	public Date getLastlogintime() {
		return lastlogintime;
	}

	/**
	 * @param lastlogintime
	 */
	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	/**
	 * @return LastLoginCity
	 */
	public String getLastlogincity() {
		return lastlogincity;
	}

	/**
	 * @param lastlogincity
	 */
	public void setLastlogincity(String lastlogincity) {
		this.lastlogincity = lastlogincity;
	}

	/**
	 * @return LastLoginIp
	 */
	public String getLastloginip() {
		return lastloginip;
	}

	/**
	 * @param lastloginip
	 */
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	/**
	 * 获取政策失效天数统计
	 *
	 * @return RateInvalidDay - 政策失效天数统计
	 */
	public Integer getRateinvalidday() {
		return rateinvalidday;
	}

	/**
	 * 设置政策失效天数统计
	 *
	 * @param rateinvalidday
	 *            政策失效天数统计
	 */
	public void setRateinvalidday(Integer rateinvalidday) {
		this.rateinvalidday = rateinvalidday;
	}

	/**
	 * 获取接口签名所需的key
	 *
	 * @return sign_key - 接口签名所需的key
	 */
	public String getSignKey() {
		return signKey;
	}

	/**
	 * 设置接口签名所需的key
	 *
	 * @param signKey
	 *            接口签名所需的key
	 */
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	/**
	 * 获取AES加密所需的key
	 *
	 * @return aes_key - AES加密所需的key
	 */
	public String getAesKey() {
		return aesKey;
	}

	/**
	 * 设置AES加密所需的key
	 *
	 * @param aesKey
	 *            AES加密所需的key
	 */
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	/**
	 * 获取本账号所属公司
	 *
	 * @return company - 本账号所属公司
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * 设置本账号所属公司
	 *
	 * @param company
	 *            本账号所属公司
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 获取备注
	 *
	 * @return Remarks - 备注
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 设置备注
	 *
	 * @param remarks
	 *            备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return IUrl
	 */
	public String getIurl() {
		return iurl;
	}

	/**
	 * @param iurl
	 */
	public void setIurl(String iurl) {
		this.iurl = iurl;
	}

	public List<AlPopedomTblrole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AlPopedomTblrole> roleList) {
		this.roleList = roleList;
	}

	public Integer getRolecode() {
		return rolecode;
	}

	public void setRolecode(Integer rolecode) {
		this.rolecode = rolecode;
	}
}