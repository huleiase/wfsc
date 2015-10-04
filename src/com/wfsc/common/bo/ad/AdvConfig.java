package com.wfsc.common.bo.ad;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告维护表
 * 
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_advconfig" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class AdvConfig implements Serializable {

	private static final long serialVersionUID = 85902413697507266L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 广告类型 1-普通广告  2-幻灯片广告
	 */
	private Integer adType;

	/**
	 * 广告图片的URL
	 */
	private String picUrl;

	/**
	 * 点击该图片跳转的URL（只允许站内）
	 */
	private String url;

	/**
	 * 有效期
	 */
	private Date dueDate;

	/***
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @hibernate.property type="timestamp"
	 */
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
