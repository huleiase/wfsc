package com.wfsc.common.bo.system;

import java.io.Serializable;
import java.util.Date;

/**
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_syslog" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class SystemLog implements Serializable {

	private static final long serialVersionUID = 6684848744882311343L;
	
	private Long id;
	
	private Date operatTime;
	private String vcLogUser;
	private String vcContent;
	private String tiLevel;
	
	public SystemLog(){}
	
	public SystemLog(String tiLevel, String vcLogUser, String vcContent){
		this.tiLevel = tiLevel;
		this.vcLogUser = vcLogUser;
		this.operatTime = new Date();
		this.vcContent = vcContent;
	}
	
	public SystemLog(String tiLevel, String vcLogUser, Date operatTime, String vcContent){
		this.tiLevel = tiLevel;
		this.vcLogUser = vcLogUser;
		this.operatTime = operatTime;
		this.vcContent = vcContent;
	}

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
	 * @hibernate.property type="timestamp" column="operatTime"
	 */
	public Date getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLogUser() {
		return vcLogUser;
	}

	public void setVcLogUser(String vcLogUser) {
		this.vcLogUser = vcLogUser;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcContent() {
		return vcContent;
	}

	public void setVcContent(String vcContent) {
		this.vcContent = vcContent;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getTiLevel() {
		return tiLevel;
	}

	public void setTiLevel(String tiLevel) {
		this.tiLevel = tiLevel;
	}

}
