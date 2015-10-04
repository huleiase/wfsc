package com.wfsc.common.bo.bym;

import java.util.Date;


/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_alarm" lazy="false"
 */
public class Alarm implements java.io.Serializable {
	
	private static final long serialVersionUID = 6883377245593425571L;
	private Long id;
	private String detail;
	private Date warn_time;
	private int isOpen;
	private int state;
	private String creater;
	private Date create_time;
	private long warntime;
	
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
	 * @hibernate.property type="string"
	 */
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="warn_time"
	 */
	public Date getWarn_time() {
		return warn_time;
	}
	public void setWarn_time(Date warn_time) {
		this.warn_time = warn_time;
	}
	
	/**
	 * @hibernate.property type="int"
	 */
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	/**
	 * @hibernate.property type="int"
	 */
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="create_time"
	 */
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public long getWarntime() {
		return warntime;
	}
	public void setWarntime(long warntime) {
		this.warntime = warntime;
	}
	
}
