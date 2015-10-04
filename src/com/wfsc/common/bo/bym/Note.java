package com.wfsc.common.bo.bym;


import java.util.Date;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_note" lazy="false"
 */
public class Note implements java.io.Serializable {

	private static final long serialVersionUID = 684549853471L;
	
	private Long id;
	private Date dtsTime;
	private Date dteTime;
	private String vcUser;
	private String vcName;
	private String vcMemo;
	
	
	/**
	  * hibernate中的主键
	  * 
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
	 * @hibernate.property type="timestamp" column="dtsTime"
	 */
	public Date getDtsTime() {
		return dtsTime;
	}
	public void setDtsTime(Date dtsTime) {
		this.dtsTime = dtsTime;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="dteTime"
	 */
	public Date getDteTime() {
		return dteTime;
	}
	public void setDteTime(Date dteTime) {
		this.dteTime = dteTime;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcMemo() {
		return vcMemo;
	}
	public void setVcMemo(String vcMemo) {
		this.vcMemo = vcMemo;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcName() {
		return vcName;
	}
	public void setVcName(String vcName) {
		this.vcName = vcName;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcUser() {
		return vcUser;
	}
	public void setVcUser(String vcUser) {
		this.vcUser = vcUser;
	}
	

}
