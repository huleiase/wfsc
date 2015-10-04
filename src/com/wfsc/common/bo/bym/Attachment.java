package com.wfsc.common.bo.bym;


import java.util.Date;


/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_attachment" lazy="false"
 */
public class Attachment  implements java.io.Serializable {
	private static final long serialVersionUID = 68833773393425571L;
	
	private Long id;
	/** 上传附件的人 */
	 private String uploadUser;

	 /** 附件名称,上传前的名称 */
	 private String attachName;
	 
	 /**附件储存名称，上传后的名称*/
	 private String depositedName;

	 /** 附件大小 */
	 private long attachSize;
	 
	 /**附件在系统中的路径（包含名称）*/
	 private String attachPath;
	 
	 /** 关联编号*/
	 private String linkCode;
	 
	 /**上传时间*/
	 private Date uploadTime;

	 
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
	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDepositedName() {
		return depositedName;
	}

	public void setDepositedName(String depositedName) {
		this.depositedName = depositedName;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(long attachSize) {
		this.attachSize = attachSize;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getLinkCode() {
		return linkCode;
	}

	public void setLinkCode(String linkCode) {
		this.linkCode = linkCode;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="uploadTime"
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	 
	 

}
