package com.wfsc.common.bo.bym;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_designcompany" lazy="false"
 */
public class DesignCompany implements java.io.Serializable {
	private static final long serialVersionUID = 68833999925571L;
	
	private Long id;
	/**
	 * 设计公司名称
	 */
	private String companyName; 
	/**
	 * 设计公司电话
	 */
	private String tel; 
	/**
	 * 设计公司传真
	 */
	private String fax; 
	/**
	 * 设计公司地址
	 */
	private String addr;
	/**
	 * 设计公司联系人（一个公司可能有多个联系人，后台导入时用逗号分隔）
	 */
	private String linkman;
	/**
	 * 联系人手机（因为联系人可能有多个，一个联系人一个手机，所以手机也可能会有多个，后台导入时用逗号分隔）
	 */
	private String phone;
	/**
	 * 联系人邮箱（同上）
	 */
	private String email;
	
	/**
	 * 备注
	 */
	private String remk;
	
	/**
	 * 停用
	 */
	private String dis; 
	
	
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
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getRemk() {
		return remk;
	}

	public void setRemk(String remk) {
		this.remk = remk;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
