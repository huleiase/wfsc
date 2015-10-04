package com.wfsc.common.bo.bym;





/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_store" lazy="false"
 */
public class Store implements java.io.Serializable{

	private static final long serialVersionUID = 684345653471L;
	
	private Long id;
	/**
	 * 仓库名
	 */
	private String storeName; 
	/**
	 * 仓库所属地
	 */
	private String vcLocal; 
	/**
	 * 公仓还是私仓1是公仓，0是私仓
	 */
	private String isPublic; 
	
	private String storeAddr;//仓库地址
	
	private String tel;//仓库电话
	
	private String fax;//仓库传真
	
	private String linkMethod;//联系方式
	
	private String remark;//备注
	
	private String isZhiBanYuan;//是否是制版员的仓库1是0不是
	
	private String userName;//该仓库对谁可见，多个人用逗号分隔
	
	
	
	private String creater;//创建人
	
	
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
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLocal() {
		return vcLocal;
	}

	public void setVcLocal(String vcLocal) {
		this.vcLocal = vcLocal;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
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
	public String getLinkMethod() {
		return linkMethod;
	}

	public void setLinkMethod(String linkMethod) {
		this.linkMethod = linkMethod;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsZhiBanYuan() {
		return isZhiBanYuan;
	}

	public void setIsZhiBanYuan(String isZhiBanYuan) {
		this.isZhiBanYuan = isZhiBanYuan;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	

}
