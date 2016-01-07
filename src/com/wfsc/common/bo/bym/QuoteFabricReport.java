package com.wfsc.common.bo.bym;



/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_qf_report" lazy="false"
 */
public class QuoteFabricReport implements java.io.Serializable{

	private static final long serialVersionUID = 682265456723471L;
	
	private Long id;
	
	private Long quoteId;

	/**
	 * 报价的型号
	 */
	private String vcModelNum;

	/**
	 * 最终单价 
	 */
	private float vcPrice;

	/**
	 * 最终单价单位
	 */
	private String vcPriceUnit;

	/**
	 * (报价数量)
	 */
	private float vcQuantity;
	
	/**
	 * 是否是隐形型号1是0不是
	 */
	private String isHidden;
	/**
	 * 是否是被替代型号1是0不是
	 */
	private String isReplaced;
	
	//税率
	private float taxation;
	
	private DesignerOrder designerOrder;
	
	
	//第一次add，修改后第一次的改为old，新增的改为add，再增加一条offset（抵消）
	private String operation;
	
	private Long doId;
	
	/**
	 * 工厂编号
	 */
	private String vcFactoryCode;
	
	/**
	 * 原厂型号
	 */
	private String vcBefModel;
	
	private String isHtCode;
	
	private String htCode;
	
	private Long qfId;
	
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
	public String getVcModelNum() {
		return vcModelNum;
	}

	public void setVcModelNum(String vcModelNum) {
		this.vcModelNum = vcModelNum;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcPrice() {
		return vcPrice;
	}

	public void setVcPrice(float vcPrice) {
		this.vcPrice = vcPrice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcPriceUnit() {
		return vcPriceUnit;
	}

	public void setVcPriceUnit(String vcPriceUnit) {
		this.vcPriceUnit = vcPriceUnit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcQuantity() {
		return vcQuantity;
	}

	public void setVcQuantity(float vcQuantity) {
		this.vcQuantity = vcQuantity;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsReplaced() {
		return isReplaced;
	}

	public void setIsReplaced(String isReplaced) {
		this.isReplaced = isReplaced;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getTaxation() {
		return taxation;
	}

	public void setTaxation(float taxation) {
		this.taxation = taxation;
	}
//	/**
//	 * @return
//	 * @hibernate.many-to-one class="com.wfsc.common.bo.bym.DesignerOrder" column="doId"
//	 */
	public DesignerOrder getDesignerOrder() {
		return designerOrder;
	}

	public void setDesignerOrder(DesignerOrder designerOrder) {
		this.designerOrder = designerOrder;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getDoId() {
		return doId;
	}

	public void setDoId(Long doId) {
		this.doId = doId;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFactoryCode() {
		return vcFactoryCode;
	}

	public void setVcFactoryCode(String vcFactoryCode) {
		this.vcFactoryCode = vcFactoryCode;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcBefModel() {
		return vcBefModel;
	}

	public void setVcBefModel(String vcBefModel) {
		this.vcBefModel = vcBefModel;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsHtCode() {
		return isHtCode;
	}

	public void setIsHtCode(String isHtCode) {
		this.isHtCode = isHtCode;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getHtCode() {
		return htCode;
	}

	public void setHtCode(String htCode) {
		this.htCode = htCode;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getQfId() {
		return qfId;
	}

	public void setQfId(Long qfId) {
		this.qfId = qfId;
	}
	
	
	
	
}
