package com.wfsc.common.bo.bym;


import java.util.Date;


/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_designerexpense" lazy="false"
 */
public class DesignerExpense implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 6845456925571L;
	
	private Long id;
	private Long quoteId;
	private String quoteNo;
	
	private Date contractDate;
	
	private String contractNo;
	
	private String quoteLocal;
	
	private float realTotel;
	
	private float designTotelMoney;
	/**
	 * 设计师代码
	 */
	private String designer1;
	/**
	 * 顾问费率
	 */
	private float counselorRate1;
	/**
	 * 设计费金额
	 */
	private float designMony1;
	/**
	 * 实际收款金额
	 */
	private float realMony1;
	/**
	 * 是否收齐款
	 */
	private String isGetAll1;
	/**
	 * 是否支付
	 */
	private String isApply1;
	/**
	 * 支付时间
	 */
	private Date applyDate1;
	/**
	 * 备注
	 */
	private String remark1;
	/**
	 * 已付款
	 */
	private float hasApply1;
	/**
	 * 未付款
	 */
	private float hasNoApply1;
	
	private String designer2;
	private float counselorRate2;
	private float designMony2;
	private float realMony2;
	private String isGetAll2;
	private String isApply2;
	private Date applyDate2;
	private String remark2;
	private float hasApply2;
	private float hasNoApply2;
	
	private String designer3;
	private float counselorRate3;
	private float designMony3;
	private float realMony3;
	private String isGetAll3;
	private String isApply3;
	private Date applyDate3;
	private String remark3;
	private float hasApply3;
	private float hasNoApply3;
	//客户名称
	private String customerName;
	//项目
	private String projectName;
	//合同金额
	private float sumMoney;
	/**
	 * 加工费
	 */
	private float vcProcessFre;
	
	/**
	 * 安装费
	 */
	private float vcInstallFre;
	/**
	 * 加急费
	 */
	private float urgentCost;
	//运费（各个型号的实际采购数量的值*具体类型的运费，再相加）+最低运费+货到工地运费
	private float freight;
	//税费
	private float taxationCost;
	//后处理
	private float vcAftertreatment;
	//其他
	private float vcOther;
	private String operation;
	
	public Object clone() {   
        try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }   
    }   
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
	public String getDesigner1() {
		return designer1;
	}
	public void setDesigner1(String designer1) {
		this.designer1 = designer1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCounselorRate1() {
		return counselorRate1;
	}
	public void setCounselorRate1(float counselorRate1) {
		this.counselorRate1 = counselorRate1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony1() {
		return designMony1;
	}
	public void setDesignMony1(float designMony1) {
		this.designMony1 = designMony1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealMony1() {
		return realMony1;
	}
	public void setRealMony1(float realMony1) {
		this.realMony1 = realMony1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsGetAll1() {
		return isGetAll1;
	}
	public void setIsGetAll1(String isGetAll1) {
		this.isGetAll1 = isGetAll1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsApply1() {
		return isApply1;
	}
	public void setIsApply1(String isApply1) {
		this.isApply1 = isApply1;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="applyDate1"
	 */
	public Date getApplyDate1() {
		return applyDate1;
	}
	public void setApplyDate1(Date applyDate1) {
		this.applyDate1 = applyDate1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesigner2() {
		return designer2;
	}
	public void setDesigner2(String designer2) {
		this.designer2 = designer2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCounselorRate2() {
		return counselorRate2;
	}
	public void setCounselorRate2(float counselorRate2) {
		this.counselorRate2 = counselorRate2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony2() {
		return designMony2;
	}
	public void setDesignMony2(float designMony2) {
		this.designMony2 = designMony2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealMony2() {
		return realMony2;
	}
	public void setRealMony2(float realMony2) {
		this.realMony2 = realMony2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsGetAll2() {
		return isGetAll2;
	}
	public void setIsGetAll2(String isGetAll2) {
		this.isGetAll2 = isGetAll2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsApply2() {
		return isApply2;
	}
	public void setIsApply2(String isApply2) {
		this.isApply2 = isApply2;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="applyDate2"
	 */
	public Date getApplyDate2() {
		return applyDate2;
	}
	public void setApplyDate2(Date applyDate2) {
		this.applyDate2 = applyDate2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesigner3() {
		return designer3;
	}
	public void setDesigner3(String designer3) {
		this.designer3 = designer3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCounselorRate3() {
		return counselorRate3;
	}
	public void setCounselorRate3(float counselorRate3) {
		this.counselorRate3 = counselorRate3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony3() {
		return designMony3;
	}
	public void setDesignMony3(float designMony3) {
		this.designMony3 = designMony3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealMony3() {
		return realMony3;
	}
	public void setRealMony3(float realMony3) {
		this.realMony3 = realMony3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsGetAll3() {
		return isGetAll3;
	}
	public void setIsGetAll3(String isGetAll3) {
		this.isGetAll3 = isGetAll3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsApply3() {
		return isApply3;
	}
	public void setIsApply3(String isApply3) {
		this.isApply3 = isApply3;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="applyDate3"
	 */
	public Date getApplyDate3() {
		return applyDate3;
	}
	public void setApplyDate3(Date applyDate3) {
		this.applyDate3 = applyDate3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
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
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="contractDate"
	 */
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getQuoteLocal() {
		return quoteLocal;
	}
	public void setQuoteLocal(String quoteLocal) {
		this.quoteLocal = quoteLocal;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasApply1() {
		return hasApply1;
	}
	public void setHasApply1(float hasApply1) {
		this.hasApply1 = hasApply1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasNoApply1() {
		return hasNoApply1;
	}
	public void setHasNoApply1(float hasNoApply1) {
		this.hasNoApply1 = hasNoApply1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasApply2() {
		return hasApply2;
	}
	public void setHasApply2(float hasApply2) {
		this.hasApply2 = hasApply2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasNoApply2() {
		return hasNoApply2;
	}
	public void setHasNoApply2(float hasNoApply2) {
		this.hasNoApply2 = hasNoApply2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasApply3() {
		return hasApply3;
	}
	public void setHasApply3(float hasApply3) {
		this.hasApply3 = hasApply3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHasNoApply3() {
		return hasNoApply3;
	}
	public void setHasNoApply3(float hasNoApply3) {
		this.hasNoApply3 = hasNoApply3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealTotel() {
		return realTotel;
	}
	public void setRealTotel(float realTotel) {
		this.realTotel = realTotel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignTotelMoney() {
		return designTotelMoney;
	}
	public void setDesignTotelMoney(float designTotelMoney) {
		this.designTotelMoney = designTotelMoney;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(float sumMoney) {
		this.sumMoney = sumMoney;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcProcessFre() {
		return vcProcessFre;
	}
	public void setVcProcessFre(float vcProcessFre) {
		this.vcProcessFre = vcProcessFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcInstallFre() {
		return vcInstallFre;
	}
	public void setVcInstallFre(float vcInstallFre) {
		this.vcInstallFre = vcInstallFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getUrgentCost() {
		return urgentCost;
	}
	public void setUrgentCost(float urgentCost) {
		this.urgentCost = urgentCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getFreight() {
		return freight;
	}
	public void setFreight(float freight) {
		this.freight = freight;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getTaxationCost() {
		return taxationCost;
	}
	public void setTaxationCost(float taxationCost) {
		this.taxationCost = taxationCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcAftertreatment() {
		return vcAftertreatment;
	}
	public void setVcAftertreatment(float vcAftertreatment) {
		this.vcAftertreatment = vcAftertreatment;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcOther() {
		return vcOther;
	}
	public void setVcOther(float vcOther) {
		this.vcOther = vcOther;
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
	

}
