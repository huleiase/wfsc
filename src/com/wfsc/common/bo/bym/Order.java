package com.wfsc.common.bo.bym;

import java.util.Date;


/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_order" lazy="false"
 */
public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 6845478653471L;
	
	private Long id;
	/**
	 * 供应商
	 */ 
	private String supplier;
	/**
	 * 订货日期
	 */
	private Date orderDate;
	/**
	 * 联系人
	 */
	private String atten;
	/**
	 * 联系人电话
	 */
	private String attenTel;
	/**
	 * 联系人传真
	 */
	private String attenFax;
	/**
	 * 经手人
	 */
	private String vcfrom;
	/**
	 * 经手人电话
	 */
	private String vcfromTel;
	/**
	 * 经手人传真
	 */
	private String vcfromFax;
	/**
	 * 报价订单
	 */
	private String quantation;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 货期
	 */
	private Date shipDateRemark;
	
	private Date shippingDate;
	/**
	 * 发货要求
	 */
	private String consignmentRequirements;
	/**
	 * 包装材料
	 */
	private String paquete;
	/**
	 * 包装材料备注
	 */
	private String paqueteRemark;
	/**
	 * 注意事项
	 */
	private String notice;
	/**
	 * 注意事项备注
	 */
	private String noticeRemark;
	/**
	 * 发货公司
	 */
	private String consignmentCompany;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 付款方式
	 */
	private String payment;
	/**
	 * 收货地址
	 */
	private String shipAddress;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 总金额
	 */
	private float sumMoney;
	/**
	 * 订单状态 0未提交,1已提交,3已审核
	 */
	private int orderStatus;
	/**
	 * 采购单对象
	 */
	private Purchase purchase;
	/**
	 * 年月
	 */
	private String tbYearMonth;
	
	/**
	 * 是否为抵消订单
	 */
	private String isOffsetOrder;
	
	/**
	 * 抵消的订单编号
	 */
	private String offsetOrderId;
	
	/**
	 * 抵消的报价单编号
	 */
	private String offsetQuoteId;
	
	/**
	 * 地区
	 */
	private String area;
	/**
	 * 物流方式1
	 */
	private String express1;
	/**
	 * 快递单号1
	 */
	private String expressNumber1;
	/**
	 * 物流方式2
	 */
	private String express2;	
	/**
	 * 快递单号2
	 */
	private String expressNumber2;
	/**
	 * 订单是否确认
	 */
	private String isOrderConfirm;
	/**
	 * 是否发货
	 */
	private String isShipments;
	/**
	 * 特殊情况时的发货备注
	 */
	private String shipmentsMemo;
	/**
	 * 下单地区（中文）
	 */
	private String areaZh;
	
	private String factoryCode;
	
	private String packingRemark;
	private String noteThing2;
	private String paquete2;
	private String shippingService;
	private String payment2;
	private String transportMode;
	private float freight;
	
	private String hbUnit;
	//1为软删除，0为正常
	private String delFlag;
	
	private Long quoteId;
	//已议价/未议价
	private String isBargain;
	//已付款/未付款
	private String isPay;
	//不需要裁剪/裁剪/未裁剪
	private String isCutting ;
	//已有快递单号/未有快递单号
	private String isEMS;
	//货物已上网/货物未上网
	private String isOnline;
	//货物已到HK/货物未到HK/货物不需要到HK
	private String isToHK;
	//货物已到JT/未到JT/不需要到JT
	private String isToJT;
	//货物QC完成/QC未完成/QC异常
	private String isQC;
	//已交货给客户/未交留仓
	private String isStore;
	//订单完结
	private String isOver;
	/**
	 * 物流方式3
	 */
	private String express3;
	/**
	 * 快递单号3
	 */
	private String expressNumber3;
	//快递单号费
	private float expressMoney1;
	private float expressMoney2;
	private float expressMoney3;
	//码单
	private String madan;
	//其他地址
	private String otherShipAddress;
	//产地编号
	private String factoryNum;
	
	private String auditor;//审核人
	
	private String otherConsignee;//其他收货人
	private Date modifyDate;
	//财务收款完结
	private String isCaiwuOver;
	//货到目的地完结
	private String isArrivalOver;
	
	private String expressRmk1;
	private String expressRmk2;
	private String expressRmk3;
	
	//财务付款完结
	private String isCaiwuPayOver;
	//QC异常
	private String qcException;
	
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="modifyDate"
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getOtherConsignee() {
		return otherConsignee;
	}

	public void setOtherConsignee(String otherConsignee) {
		this.otherConsignee = otherConsignee;
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
	public String getFactoryNum() {
		return factoryNum;
	}

	public void setFactoryNum(String factoryNum) {
		this.factoryNum = factoryNum;
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
	public String getTbYearMonth() {
		return tbYearMonth;
	}

	public void setTbYearMonth(String tbYearMonth) {
		this.tbYearMonth = tbYearMonth;
	}
	/**
	 * 
	 * @return
	 * @hibernate.many-to-one class="com.wfsc.common.bo.bym.Purchase" column="purchaseId" lazy="false"
	 */
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	public String getAtten() {
		return atten;
	}

	public void setAtten(String atten) {
		this.atten = atten;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAttenTel() {
		return attenTel;
	}

	public void setAttenTel(String attenTel) {
		this.attenTel = attenTel;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAttenFax() {
		return attenFax;
	}

	public void setAttenFax(String attenFax) {
		this.attenFax = attenFax;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcfrom() {
		return vcfrom;
	}

	public void setVcfrom(String vcfrom) {
		this.vcfrom = vcfrom;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcfromTel() {
		return vcfromTel;
	}

	public void setVcfromTel(String vcfromTel) {
		this.vcfromTel = vcfromTel;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcfromFax() {
		return vcfromFax;
	}

	public void setVcfromFax(String vcfromFax) {
		this.vcfromFax = vcfromFax;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getQuantation() {
		return quantation;
	}

	public void setQuantation(String quantation) {
		this.quantation = quantation;
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
	 * @return
	 * @hibernate.property type="timestamp" column="shipDateRemark"
	 */
	public Date getShipDateRemark() {
		return shipDateRemark;
	}

	public void setShipDateRemark(Date shipDateRemark) {
		this.shipDateRemark = shipDateRemark;
	}
	
	
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="shippingDate"
	 */
	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getConsignmentRequirements() {
		return consignmentRequirements;
	}

	public void setConsignmentRequirements(String consignmentRequirements) {
		this.consignmentRequirements = consignmentRequirements;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPaqueteRemark() {
		return paqueteRemark;
	}

	public void setPaqueteRemark(String paqueteRemark) {
		this.paqueteRemark = paqueteRemark;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getNoticeRemark() {
		return noticeRemark;
	}

	public void setNoticeRemark(String noticeRemark) {
		this.noticeRemark = noticeRemark;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getConsignmentCompany() {
		return consignmentCompany;
	}

	public void setConsignmentCompany(String consignmentCompany) {
		this.consignmentCompany = consignmentCompany;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
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
	 * @hibernate.property type="float"
	 */
	public float getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(float sumMoney) {
		this.sumMoney = sumMoney;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getExpress1() {
		return express1;
	}

	public void setExpress1(String express1) {
		this.express1 = express1;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressNumber1() {
		return expressNumber1;
	}

	public void setExpressNumber1(String expressNumber1) {
		this.expressNumber1 = expressNumber1;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpress2() {
		return express2;
	}

	public void setExpress2(String express2) {
		this.express2 = express2;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressNumber2() {
		return expressNumber2;
	}

	public void setExpressNumber2(String expressNumber2) {
		this.expressNumber2 = expressNumber2;
	}
	
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsOrderConfirm() {
		return isOrderConfirm;
	}

	public void setIsOrderConfirm(String isOrderConfirm) {
		this.isOrderConfirm = isOrderConfirm;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsShipments() {
		return isShipments;
	}

	public void setIsShipments(String isShipments) {
		this.isShipments = isShipments;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getShipmentsMemo() {
		return shipmentsMemo;
	}

	public void setShipmentsMemo(String shipmentsMemo) {
		this.shipmentsMemo = shipmentsMemo;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getAreaZh() {
		return areaZh;
	}

	public void setAreaZh(String areaZh) {
		this.areaZh = areaZh;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPackingRemark() {
		return packingRemark;
	}

	public void setPackingRemark(String packingRemark) {
		this.packingRemark = packingRemark;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getNoteThing2() {
		return noteThing2;
	}

	public void setNoteThing2(String noteThing2) {
		this.noteThing2 = noteThing2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPaquete2() {
		return paquete2;
	}

	public void setPaquete2(String paquete2) {
		this.paquete2 = paquete2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getShippingService() {
		return shippingService;
	}

	public void setShippingService(String shippingService) {
		this.shippingService = shippingService;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPayment2() {
		return payment2;
	}

	public void setPayment2(String payment2) {
		this.payment2 = payment2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
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
	 * @hibernate.property type="string"
	 */
	public String getHbUnit() {
		return hbUnit;
	}

	public void setHbUnit(String hbUnit) {
		this.hbUnit = hbUnit;
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
	public String getIsBargain() {
		return isBargain;
	}

	public void setIsBargain(String isBargain) {
		this.isBargain = isBargain;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsCutting() {
		return isCutting;
	}

	public void setIsCutting(String isCutting) {
		this.isCutting = isCutting;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsEMS() {
		return isEMS;
	}

	public void setIsEMS(String isEMS) {
		this.isEMS = isEMS;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsToHK() {
		return isToHK;
	}

	public void setIsToHK(String isToHK) {
		this.isToHK = isToHK;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsToJT() {
		return isToJT;
	}

	public void setIsToJT(String isToJT) {
		this.isToJT = isToJT;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsQC() {
		return isQC;
	}

	public void setIsQC(String isQC) {
		this.isQC = isQC;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsStore() {
		return isStore;
	}

	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpress3() {
		return express3;
	}

	public void setExpress3(String express3) {
		this.express3 = express3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressNumber3() {
		return expressNumber3;
	}

	public void setExpressNumber3(String expressNumber3) {
		this.expressNumber3 = expressNumber3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getExpressMoney1() {
		return expressMoney1;
	}

	public void setExpressMoney1(float expressMoney1) {
		this.expressMoney1 = expressMoney1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getExpressMoney2() {
		return expressMoney2;
	}

	public void setExpressMoney2(float expressMoney2) {
		this.expressMoney2 = expressMoney2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getExpressMoney3() {
		return expressMoney3;
	}

	public void setExpressMoney3(float expressMoney3) {
		this.expressMoney3 = expressMoney3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getMadan() {
		return madan;
	}

	public void setMadan(String madan) {
		this.madan = madan;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOtherShipAddress() {
		return otherShipAddress;
	}

	public void setOtherShipAddress(String otherShipAddress) {
		this.otherShipAddress = otherShipAddress;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsCaiwuOver() {
		return isCaiwuOver;
	}

	public void setIsCaiwuOver(String isCaiwuOver) {
		this.isCaiwuOver = isCaiwuOver;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsArrivalOver() {
		return isArrivalOver;
	}

	public void setIsArrivalOver(String isArrivalOver) {
		this.isArrivalOver = isArrivalOver;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressRmk1() {
		return expressRmk1;
	}

	public void setExpressRmk1(String expressRmk1) {
		this.expressRmk1 = expressRmk1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressRmk2() {
		return expressRmk2;
	}

	public void setExpressRmk2(String expressRmk2) {
		this.expressRmk2 = expressRmk2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressRmk3() {
		return expressRmk3;
	}

	public void setExpressRmk3(String expressRmk3) {
		this.expressRmk3 = expressRmk3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsCaiwuPayOver() {
		return isCaiwuPayOver;
	}

	public void setIsCaiwuPayOver(String isCaiwuPayOver) {
		this.isCaiwuPayOver = isCaiwuPayOver;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getQcException() {
		return qcException;
	}

	public void setQcException(String qcException) {
		this.qcException = qcException;
	}


	
}
