package com.wfsc.common.bo.bym;


import java.util.Date;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_purchase" lazy="false"
 */
public class Purchase implements java.io.Serializable {
	

	private static final long serialVersionUID = 68098753471L;
	
	private Long id;
	/** 
	 * 待采购单
	*/
	public static final String STATUS_D = "1";
	/** 
	 * 采购单 
	 */
	public static final String STATUS_C = "2";
	
	/** 
	 *  合同编号
	 */
	private String contractNo;

	/** 
	 *  货期要求
	 */
	private String deliveryRequirements;
	/** 
	 *  依凭单
	 */
	private String voucher;

	/** 
	 *  差额因素
	 */
	private String balanceFactor;

	/** 
	 *  下单日期
	 */
	private Date contractDate;

	/** 
	 *  单号
	 */
	private String orderNo;

	/** 
	 *  定金确认
	 */
	private String depositConfirmation;

	/** 
	 *  余额确认
	 */
	private String balanceConfirmation;

	/** 
	 *  定金确认者
	 */
	private String dcPerson;
	
	/** 
	 *  余额确认者
	 */
	private String bcPerson;
	/** 
	 *  订单状态:0未提交,1提交,2待采购审核通过,3采购审核通过
	 */
	private String orderStatus;

	/** 
	 *  审批人
	 */
	private String approver;

	/** 
	 *  审核人
	 */
	private String auditor;

	/** 
	 *  地址
	 */
	private String address;

	/** 
	 *  到货日期
	 */
	private String remarks;

	/** 
	 *  制单
	 */
	private String touching;

	/** 
	 *  核对员
	 */
	private String checker;

	/** 
	 *  采购员
	 */
	private String purchasingAgent;

	/** 
	 *  客户名称
	 */
	private String customer;

	/** 
	 *  审核日期
	 */
	private Date orderDate;

	/** 
	 *  no
	 */
	private String purNo;

	/** 
	 *  包装说明
	 */
	private String packingInstruction;

	/** 
	 *  出货核实
	 */
	private String deliveryVerification;

	/** 
	 *  对应的报价，多对一，外键：quoteId
	 */
	private Quote quote;

	/** 
	 *  发货方式及快递付款方式
	 */
	private String paymentType;
	
	/** 
	 *  年月
	 */
	private String tbYearMonth;
	/** 
	 *  对应的报价布匹，一对多的关系
	 */
	//private Set<QuoteFabric> quoteFabric = new HashSet<QuoteFabric>(0);

	/** 
	 *  是否需要批核
	 */
	private String isApproved;
	
	/** 
	 *  批核人
	 */
	private String approvedPerson;
	
	/** 
	 *  是否为抵消订单的采购单
	 */
	private String isOffsetOrder;
	
	/** 
	 *  抵消的订单编号
	 */
	private String offsetOrderId;
	
	/** 
	 *  抵消的报价单编号
	 */
	private String offsetQuoteId;
	
	/** 
	 *  批核状态 0未批核 1已批核
	 */
	private String approvedStatus;
	
	/** 
	 *  地区
	 */
	private String area;
	/**
	 * 快递公司
	 */
	private String expressCom;
	
	/** 
	 *  采购单类型 1待采购单，2采购单
	 */
	private String purchaseType;
	
	private String isAudit;
	//1为软删除，0为正常
	private String delFlag;
	
	/**
	 * 差旅费
	 */
	private float travelExpenses;
	
	/**
	 * 加工费
	 */
	private float processFee;
	
	/**
	 * 安装费
	 */
	private float installFee;
	/**
	 * 材料合计
	 */
	private float clTotal;
	/**
	 * 其他
	 */
	private float otherFre;
	
	private String otherExpressCom;
	
	private String otheraddress;
	
	private boolean canAudit;
	
	private String rilegou;
	
	public String getRilegou() {
		return rilegou;
	}

	public void setRilegou(String rilegou) {
		this.rilegou = rilegou;
	}

	public boolean isCanAudit() {
		return canAudit;
	}

	public void setCanAudit(boolean canAudit) {
		this.canAudit = canAudit;
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
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getIsOffsetOrder() {
		return isOffsetOrder;
	}

	public void setIsOffsetOrder(String isOffsetOrder) {
		this.isOffsetOrder = isOffsetOrder;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOffsetOrderId() {
		return offsetOrderId;
	}

	public void setOffsetOrderId(String offsetOrderId) {
		this.offsetOrderId = offsetOrderId;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOffsetQuoteId() {
		return offsetQuoteId;
	}

	public void setOffsetQuoteId(String offsetQuoteId) {
		this.offsetQuoteId = offsetQuoteId;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getApprovedPerson() {
		return approvedPerson;
	}

	public void setApprovedPerson(String approvedPerson) {
		this.approvedPerson = approvedPerson;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTbYearMonth() {
		return tbYearMonth;
	}

	public void setTbYearMonth(String tbYearMonth) {
		this.tbYearMonth = tbYearMonth;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return
	 * @hibernate.many-to-one class="com.wfsc.common.bo.bym.Quote" column="quoteId" lazy="false"
	 */
	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	
	/**
	 * @hibernate.property type="string"
	 */
	public String getBcPerson() {
		return bcPerson;
	}

	public void setBcPerson(String bcPerson) {
		this.bcPerson = bcPerson;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDcPerson() {
		return dcPerson;
	}

	public void setDcPerson(String dcPerson) {
		this.dcPerson = dcPerson;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBalanceConfirmation() {
		return balanceConfirmation;
	}

	public void setBalanceConfirmation(String balanceConfirmation) {
		this.balanceConfirmation = balanceConfirmation;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBalanceFactor() {
		return balanceFactor;
	}

	public void setBalanceFactor(String balanceFactor) {
		this.balanceFactor = balanceFactor;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
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
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDeliveryRequirements() {
		return deliveryRequirements;
	}

	public void setDeliveryRequirements(String deliveryRequirements) {
		this.deliveryRequirements = deliveryRequirements;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDeliveryVerification() {
		return deliveryVerification;
	}

	public void setDeliveryVerification(String deliveryVerification) {
		this.deliveryVerification = deliveryVerification;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDepositConfirmation() {
		return depositConfirmation;
	}

	public void setDepositConfirmation(String depositConfirmation) {
		this.depositConfirmation = depositConfirmation;
	}


	/**
	 * @return
	 * @hibernate.property type="timestamp" column="orderDate"
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPackingInstruction() {
		return packingInstruction;
	}

	public void setPackingInstruction(String packingInstruction) {
		this.packingInstruction = packingInstruction;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPurchasingAgent() {
		return purchasingAgent;
	}

	public void setPurchasingAgent(String purchasingAgent) {
		this.purchasingAgent = purchasingAgent;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPurNo() {
		return purNo;
	}

	public void setPurNo(String purNo) {
		this.purNo = purNo;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTouching() {
		return touching;
	}

	public void setTouching(String touching) {
		this.touching = touching;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressCom() {
		return expressCom;
	}

	public void setExpressCom(String expressCom) {
		this.expressCom = expressCom;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getTravelExpenses() {
		return travelExpenses;
	}

	public void setTravelExpenses(float travelExpenses) {
		this.travelExpenses = travelExpenses;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getProcessFee() {
		return processFee;
	}

	public void setProcessFee(float processFee) {
		this.processFee = processFee;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInstallFee() {
		return installFee;
	}

	public void setInstallFee(float installFee) {
		this.installFee = installFee;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getClTotal() {
		return clTotal;
	}

	public void setClTotal(float clTotal) {
		this.clTotal = clTotal;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getOtherFre() {
		return otherFre;
	}

	public void setOtherFre(float otherFre) {
		this.otherFre = otherFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOtherExpressCom() {
		return otherExpressCom;
	}

	public void setOtherExpressCom(String otherExpressCom) {
		this.otherExpressCom = otherExpressCom;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOtheraddress() {
		return otheraddress;
	}

	public void setOtheraddress(String otheraddress) {
		this.otheraddress = otheraddress;
	}
	
	

}
