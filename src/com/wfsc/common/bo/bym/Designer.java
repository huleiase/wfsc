package com.wfsc.common.bo.bym;




/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_designer" lazy="false"
 */
public class Designer implements java.io.Serializable {

	private static final long serialVersionUID = 6845456925571L;
	
	private Long id;
	/**
	 * 编号
	 */
	private String codeName; 
	/**
	 * 合作方
	 */
	private String designerName; 
	/**
	 * 顾问费率
	 */
	private String counselorRate; 
	
	
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
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCounselorRate() {
		return counselorRate;
	}

	public void setCounselorRate(String counselorRate) {
		this.counselorRate = counselorRate;
	}
	/**
	 * @hibernate.property type="string"
	 */

	public String getDesignerName() {
		return designerName;
	}

	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	
	

}
