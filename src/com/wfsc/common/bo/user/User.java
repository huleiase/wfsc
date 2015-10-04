package com.wfsc.common.bo.user;

import java.util.Date;


/**
 * @author jacky
 * @version 1.0 用户基本信息,原则上不可修改的信息: 用户名(登录名),真实姓名,性别,等等
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_user" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class User implements java.io.Serializable {
	
	private static final long serialVersionUID = 2500471860354748649L;
	
	private Long id;
	
	/**
	 * 注册邮箱，可用于登录，相当于用户名，唯一，数据库中不应该存在两个相同的邮箱地址
	 */
	private String email;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 手机号码，可用于登录，唯一，数据库中不应该存在两个相同的手机号码
	 */
	private String telphone;
	
	/**
	 * 微信公众号的ID，保留
	 */
	private String openId;
	
	/**
	 * QQ号码
	 */
	private String qq;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLogin;
	
	/**
	 * 登录IP
	 */
	private String loginIp;
	
	/**
	 * MD5编码，密码
	 */
	private String password;
	
	/**
	 * 0 – 禁用， 1-可用， 9-待激活
	 */
	private Integer status = new Integer(0);
	
	/**
	 * 注册日期
	 */
	private Date regDate;
	
	/**
	 * 激活码，在用户注册成功以后生成
	 */
	private String activeCode;
	
	/**
	 * 用户级别，预留
	 */
	private Integer level;
	
	/** 是否在线 ,默认为不在线 */
	private boolean online = false;
	
	private String statusStr;
	
	private Integer integration;//积分
	
	/**
	 * 用户选择城市的编码，当用户登录后，每次更改了该编码后，将会记录如数据库
	 */
	private Integer cityCode;

	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="email" not-null="true" length="255"
	 * @hibernate.property unique="true"
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="nickname" length="255"
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="telphone" length="15"
	 */
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="openid" length="255"
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="qq" length="20"
	 */
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="lastlogin"
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="loginip" length="50"
	 */
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="password" not-null="true" length="255"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 * @hibernate.property type="int" column="status" not-null="true" length="1"
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="regdate" not-null="true"
	 */
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="activecode" length="100"
	 */
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	/**
	 * @return
	 * @hibernate.property type="int" column="level" length="1"
	 */
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/**
	 * 是否在线
	 * 
	 * @hibernate.property type="boolean" column="isOnline"
	 */
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	/**
	 * 积分
	 * 
	 * @hibernate.property type="int"
	 */
	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	

}