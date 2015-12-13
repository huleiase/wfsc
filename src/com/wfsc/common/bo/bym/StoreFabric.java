package com.wfsc.common.bo.bym;


import java.util.Date;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_store_fabric" lazy="false"
 */
public class StoreFabric implements java.io.Serializable{

	private static final long serialVersionUID = 684587639471L;
	
	private Long id;
	/**
	 * 关联的仓库
	 */
	private Long storeId;
	
	private String storeName;
	
	private Long orderId;//订单id
	
	private String fabricNo;//产品编号
	
	private String supplie;//供应商
	
	private String payment;//支付方式
	
	private Date inStoreDate;//入库日期
	
	private String fileName;
	private String vcAddr;//位置
	
	//


	/**
	 * 项目名称
	 */
	private String vcProject;

	/**
	 * 型号
	 */
	private String vcModelNum;

	/**
	 * 色号
	 */
	private String vcColorNum;

	/**
	 * 工厂编号
	 */
	private String vcFactoryCode;


	/**
	 * 备注
	 */
	private String vcRmk;

	
	/**
	 * 实定量
	 */
	private float vcQuoteNum;
	
	private String unit;//单位

	
	/**
	 * 经手人
	 */
	private String vcAssignAutor;
	
	/**
	 * 分段铺量（到货数量，分段）
	 */
	private String vcSubLay;
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	private String surplus;//剩余数量，分段
	
	/**
	 * 是否有HT型号
	 */
	private String isHtCode;

	/**
	 * HT型号
	 */
	private String htCode;
	
	/**
	 * 销售1
	 */
	private String vcSalesman;
	
	/**
	 * 销售1
	 */
	private String vcSalesman1;
	
	/**
	 * 销售2
	 */
	private String vcSalesman2;
	
	/**
	 * 销售3
	 */
	private String vcSalesman3;
	
	/**
	 * 销售4
	 */
	private String vcSalesman4;
	
	private String vcRealityAog;//实际到货
	
	private Long quoteId;
	
	private String quoteNum;//报价单号
	
	private String displayNum;//显示的型号
	
	private String isStoreOver;//仓库是否完结
	
	private float vcWidth;//幅宽
	
	private String vcWidthUnit;//幅宽单位
	
	private float orderQuantity;//订货量
	
	private String vcPurchaseRmk;
	
	
	private Date orderDate;//订单日期，读取订单模块的订单日期
	private String shipAddress;//发货地址
	private String shipPerson;//出货经手人
	private String expressNumber;//快递单号
	private String expressCompany;//快递公司
	private String arrivalCompany;//到货公司
	private String isOrderConfirm;//订单是否确认
	private String deliveryRequirements;//货期要求,读取采购单列表的货期要求
	private String vcRmk2;//备注2
	private String arrivalAddress;//到货地址
	private String specialReq;//特殊要求
	private Date arrivalDate;//要求到货日期,读取订单管理的货期
	private String arrivalNum;//到货数量
	private Date outStoreDate;//出库日期
	
	/**
	  * hibernate中的主键
	  * 
	  * @hibernate.id column="id" generator-class="native" type="long"
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
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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
	public String getVcSubLay() {
		return vcSubLay;
	}

	public void setVcSubLay(String vcSubLay) {
		this.vcSubLay = vcSubLay;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAssignAutor() {
		return vcAssignAutor;
	}

	public void setVcAssignAutor(String vcAssignAutor) {
		this.vcAssignAutor = vcAssignAutor;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcQuoteNum() {
		return vcQuoteNum;
	}

	public void setVcQuoteNum(float vcQuoteNum) {
		this.vcQuoteNum = vcQuoteNum;
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
	public String getVcRmk() {
		return vcRmk;
	}


	public void setVcRmk(String vcRmk) {
		this.vcRmk = vcRmk;
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
	public String getVcColorNum() {
		return vcColorNum;
	}

	public void setVcColorNum(String vcColorNum) {
		this.vcColorNum = vcColorNum;
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
	 * @hibernate.property type="string"
	 */
	public String getVcProject() {
		return vcProject;
	}

	public void setVcProject(String vcProject) {
		this.vcProject = vcProject;
	}

	/**
	 * @hibernate.property type="long"
	 */
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getFabricNo() {
		return fabricNo;
	}

	public void setFabricNo(String fabricNo) {
		this.fabricNo = fabricNo;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getSupplie() {
		return supplie;
	}

	public void setSupplie(String supplie) {
		this.supplie = supplie;
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
	 * @return
	 * @hibernate.property type="timestamp" column="inStoreDate"
	 */
	public Date getInStoreDate() {
		return inStoreDate;
	}

	public void setInStoreDate(Date inStoreDate) {
		this.inStoreDate = inStoreDate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getSurplus() {
		return surplus;
	}

	public void setSurplus(String surplus) {
		this.surplus = surplus;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcSalesman() {
		return vcSalesman;
	}

	public void setVcSalesman(String vcSalesman) {
		this.vcSalesman = vcSalesman;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcSalesman1() {
		return vcSalesman1;
	}

	public void setVcSalesman1(String vcSalesman1) {
		this.vcSalesman1 = vcSalesman1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcSalesman2() {
		return vcSalesman2;
	}

	public void setVcSalesman2(String vcSalesman2) {
		this.vcSalesman2 = vcSalesman2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcSalesman3() {
		return vcSalesman3;
	}

	public void setVcSalesman3(String vcSalesman3) {
		this.vcSalesman3 = vcSalesman3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcSalesman4() {
		return vcSalesman4;
	}

	public void setVcSalesman4(String vcSalesman4) {
		this.vcSalesman4 = vcSalesman4;
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
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAddr() {
		return vcAddr;
	}

	public void setVcAddr(String vcAddr) {
		this.vcAddr = vcAddr;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRealityAog() {
		return vcRealityAog;
	}

	public void setVcRealityAog(String vcRealityAog) {
		this.vcRealityAog = vcRealityAog;
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
	public String getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(String quoteNum) {
		this.quoteNum = quoteNum;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDisplayNum() {
		return displayNum;
	}

	public void setDisplayNum(String displayNum) {
		this.displayNum = displayNum;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsStoreOver() {
		return isStoreOver;
	}

	public void setIsStoreOver(String isStoreOver) {
		this.isStoreOver = isStoreOver;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcWidth() {
		return vcWidth;
	}

	public void setVcWidth(float vcWidth) {
		this.vcWidth = vcWidth;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcWidthUnit() {
		return vcWidthUnit;
	}

	public void setVcWidthUnit(String vcWidthUnit) {
		this.vcWidthUnit = vcWidthUnit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(float orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcPurchaseRmk() {
		return vcPurchaseRmk;
	}

	public void setVcPurchaseRmk(String vcPurchaseRmk) {
		this.vcPurchaseRmk = vcPurchaseRmk;
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
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getShipPerson() {
		return shipPerson;
	}

	public void setShipPerson(String shipPerson) {
		this.shipPerson = shipPerson;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getArrivalCompany() {
		return arrivalCompany;
	}

	public void setArrivalCompany(String arrivalCompany) {
		this.arrivalCompany = arrivalCompany;
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
	public String getDeliveryRequirements() {
		return deliveryRequirements;
	}

	public void setDeliveryRequirements(String deliveryRequirements) {
		this.deliveryRequirements = deliveryRequirements;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRmk2() {
		return vcRmk2;
	}

	public void setVcRmk2(String vcRmk2) {
		this.vcRmk2 = vcRmk2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getSpecialReq() {
		return specialReq;
	}

	public void setSpecialReq(String specialReq) {
		this.specialReq = specialReq;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="arrivalDate"
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(String arrivalNum) {
		this.arrivalNum = arrivalNum;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="outStoreDate"
	 */
	public Date getOutStoreDate() {
		return outStoreDate;
	}

	public void setOutStoreDate(Date outStoreDate) {
		this.outStoreDate = outStoreDate;
	}
	

}
