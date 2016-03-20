package com.wfsc.common.bo.bym;

import java.util.Date;



/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_quote_fabric" lazy="false"
 */
public class QuoteFabric implements java.io.Serializable{

	private static final long serialVersionUID = 68222222223471L;
	
	private Long id;
	/**
	 * 关联的报价
	 */
	private Quote quote;


	/**
	 * 项目名称
	 */
	private String vcProject;

	/**
	 * 描述
	 */
	private String vcDes;

	/**
	 * 型号
	 */
	private String vcModelNum;

	/**
	 * 色号
	 */
	private String vcColorNum;
	
	/**
	 * 幅宽
	 */
	private float vcWidth;

	/**
	 * 幅宽的单位
	 */
	private String vcWidthUnit;

	/**
	 * 最终单价 
	 */
	private float vcPrice;

	/**
	 * 最终单价单位
	 */
	private String vcPriceUnit;

	/**
	 * 面价
	 */
	private float vcOldPrice;

	/**
	 * 面价单位
	 */
	private String vcOldPriceUnit;
	
	/**
	 * 报价货币
	 */
	private String vcMoney;

	/**
	 * 产地
	 */
	private String vcProduceLocal;

	/**
	 * 特殊费用
	 */
	private float vcSpecialExp;

	/**
	 * (报价数量)
	 */
	private float vcQuantity;

	/**
	 * 起订量
	 */
	private String vcLeastNum;

	/**
	 * 报价折扣
	 */
	private float vcDiscount;

	/**
	 * 每一行的金额
	 */
	private float vcTotal;

	/**
	 * 工厂编号
	 */
	private String vcFactoryCode;

	/**
	 * 工程运费
	 */
	private float vcProFre;

	/**
	 *  零售运费
	 */
	private float vcRetFre;

	/**
	 * 备注
	 */
	private String vcRmk;

	/**
	 * 是否有HT型号
	 */
	private String isHtCode;

	/**
	 * HT型号
	 */
	private String htCode;

	/**
	 * 排序标识--新加字段
	 */
	private int orderId;

	/**
	 * 索引标识--新加字段
	 */
	private int vcIndex;
	
	/**
	 * 采购单是否审核
	 */
	private String isAudit;
	
	/**
	 * 实定量
	 */
	private float vcQuoteNum;

	/**
	 * 没用到
	 */
	private String vcQuoteMeasure;
	
	/**
	 * 采购单、订单备注
	 */
	private String vcPurchaseRmk;
	
	/**
	 * 采购单、订单分配人员
	 */
	private String vcAssignAutor;
	
	/**
	 * 分段铺量
	 */
	private String vcSubLay;
	
	/**
	 * 实际到货
	 */
	private String vcRealityAog;
	
	/**
	 * 出货数明细
	 */
	private String vcShipmentNum;
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 产品类型
	 */
	private String vcType;
	
	/**
	 * 原始进价
	 */
	private float singlePrice;
	
	/**
	 * 订单去厂家的时候实价的价格，比如在原始进价的基础上除了折扣再有优惠
	 */
	private float shijia;
	
	/**
	 * 进价货币
	 */
	private String priceCur;
	
	/**
	 * 进价时的折扣
	 */
	private float vcPurDis;
	
	/**
	 * 标识是否是拷贝
	 */
	private String copy = "N";
	/**
	 * 报价方式
	 */
	private String quoteFormate;
	
	/**
	 * 产品的备注二
	 */
	private String remark2;
	
	/**
	 * 报价员备注
	 */
	private String quoteRemark;
	
	/**
	 * 是否是隐形型号1是0不是
	 */
	private String isHidden;
	/**
	 * 是否是被替代型号1是0不是
	 */
	private String isReplaced;
	/**
	 * 对应的替代型号布匹的型号和厂号
	 */
	private String replaceId;
	
	 private String replaceRemark;
	/**
	 * 客户提供的数量和单位
	 */
	private float customerQuantity;
	private String customerUnit;
	/**
	 * 经过换算的数量，单位就是面价的单位，也就是进货时的单位
	 */
	private float conversionQuantity;
	/**
	 * 实际订货的数量，单位也是面价的单位（给采购部看的）
	 */
	private float orderQuantity;
	
	/**
	 * 进价货币对RMB的汇率，订单和报表中统计成本时用到
	 */
	private float exchangeRate;
	/**
	 * 订单上显示的单价，等于原始进价*进价折扣
	 */
	private float sigMoney;
	/**
	 * 显示的型号
	 */
	
	private Long qfId;
	
	private String isHiddenisReplaced;
	
	
	/**
	 * 产地编号
	 */
	private String vcFactoryNum;
	
	private float amountrmb ;//订单里面的每一行中的折算RMB
	private float realMonny;//订单里面每一行实际金额=实价*实定量
	
	private String offsetQF;
	
	/**
	 * 运费
	 */
	private String freight;
	/**
	 * 最低运费
	 */
	private String lowFreight;
	/**
	 * 品牌属性
	 */
	private String brandAttri;
	/**
	 * 产品范围
	 */
	private String productRange;
	
	private float dhjCost;//大货价成本
	private float dhjInlandRate;//大货价内地系数
	private float dhjInlandTransCost;//大货价内地运费
	private float dhjHKRate;//大货价香港系数
	private float dhjHKTransCost;//大货价香港运费
	private float dhjWidth;//大货价幅宽
	private float dhjVcOldPrice;//大货价内地面价
	private float dhjHKVcPrice;//大货价香港面价
	private float dhjFinalOldPrice;//大货价内地最终价格
	private float dhjHKFinalPrice;//大货价香港最终价格
	
	private String vcComposition;
	
	private String MOQ;//最小起订量
	private String leastIncrement;//最小增量
	private String vcModelNumDisplay;
	private String isCgbj = "1";
	private String filePath;
	
	private String vcDis;
	//订单里面的实定量
	private String quoteNum; 
	
	
	private String outNum;//1、出库数量
	private Date outStoreDate;//2、出库日期
	private String shipPerson;//3、出货经手人
	private String expressNumber;//4、快递单号
	private String expressCompany;//5、快递公司
	private String arrivalAddress;//6、到货地址
	
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getOutNum() {
		return outNum;
	}

	public void setOutNum(String outNum) {
		this.outNum = outNum;
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
	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
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
	public String getVcType() {
		return vcType;
	}

	public void setVcType(String vcType) {
		this.vcType = vcType;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjCost() {
		return dhjCost;
	}

	public void setDhjCost(float dhjCost) {
		this.dhjCost = dhjCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjInlandRate() {
		return dhjInlandRate;
	}

	public void setDhjInlandRate(float dhjInlandRate) {
		this.dhjInlandRate = dhjInlandRate;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjHKRate() {
		return dhjHKRate;
	}

	public void setDhjHKRate(float dhjHKRate) {
		this.dhjHKRate = dhjHKRate;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjWidth() {
		return dhjWidth;
	}

	public void setDhjWidth(float dhjWidth) {
		this.dhjWidth = dhjWidth;
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
	public String getVcRealityAog() {
		return vcRealityAog;
	}

	public void setVcRealityAog(String vcRealityAog) {
		this.vcRealityAog = vcRealityAog;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcShipmentNum() {
		return vcShipmentNum;
	}

	public void setVcShipmentNum(String vcShipmentNum) {
		this.vcShipmentNum = vcShipmentNum;
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
	 * @hibernate.property type="string"
	 */
	public String getVcPurchaseRmk() {
		return vcPurchaseRmk;
	}

	public void setVcPurchaseRmk(String vcPurchaseRmk) {
		this.vcPurchaseRmk = vcPurchaseRmk;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcQuoteMeasure() {
		return vcQuoteMeasure;
	}

	public void setVcQuoteMeasure(String vcQuoteMeasure) {
		this.vcQuoteMeasure = vcQuoteMeasure;
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
	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getVcIndex() {
		return vcIndex;
	}

	public void setVcIndex(int vcIndex) {
		this.vcIndex = vcIndex;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	 * @hibernate.property type="float"
	 */
	public float getVcProFre() {
		return vcProFre;
	}

	public void setVcProFre(float vcProFre) {
		this.vcProFre = vcProFre;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcRetFre() {
		return vcRetFre;
	}

	public void setVcRetFre(float vcRetFre) {
		this.vcRetFre = vcRetFre;
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
	public String getVcDes() {
		return vcDes;
	}

	public void setVcDes(String vcDes) {
		this.vcDes = vcDes;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcDiscount() {
		return vcDiscount;
	}

	public void setVcDiscount(float vcDiscount) {
		this.vcDiscount = vcDiscount;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLeastNum() {
		return vcLeastNum;
	}

	public void setVcLeastNum(String vcLeastNum) {
		this.vcLeastNum = vcLeastNum;
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
	public String getVcMoney() {
		return vcMoney;
	}

	public void setVcMoney(String vcMoney) {
		this.vcMoney = vcMoney;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcOldPrice() {
		return vcOldPrice;
	}

	public void setVcOldPrice(float vcOldPrice) {
		this.vcOldPrice = vcOldPrice;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getVcOldPriceUnit() {
		return vcOldPriceUnit;
	}

	public void setVcOldPriceUnit(String vcOldPriceUnit) {
		this.vcOldPriceUnit = vcOldPriceUnit;
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
	 * @hibernate.property type="string"
	 */
	public String getVcProduceLocal() {
		return vcProduceLocal;
	}

	public void setVcProduceLocal(String vcProduceLocal) {
		this.vcProduceLocal = vcProduceLocal;
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
	 * @hibernate.property type="float"
	 */
	public float getVcQuantity() {
		return vcQuantity;
	}

	public void setVcQuantity(float vcQuantity) {
		this.vcQuantity = vcQuantity;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcSpecialExp() {
		return vcSpecialExp;
	}

	public void setVcSpecialExp(float vcSpecialExp) {
		this.vcSpecialExp = vcSpecialExp;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getVcTotal() {
		return vcTotal;
	}

	public void setVcTotal(float vcTotal) {
		this.vcTotal = vcTotal;
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
	 * 
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
	 * @hibernate.property type="float"
	 */
	public float getSinglePrice() {
		return singlePrice;
	}

	
	public void setSinglePrice(float singlePrice) {
		this.singlePrice = singlePrice;
	}

	public String getCopy() {
		return copy;
	}

	
	public void setCopy(String copy) {
		this.copy = copy;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getQuoteFormate() {
		return quoteFormate;
	}

	public void setQuoteFormate(String quoteFormate) {
		this.quoteFormate = quoteFormate;
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
	public String getQuoteRemark() {
		return quoteRemark;
	}
	public void setQuoteRemark(String quoteRemark) {
		this.quoteRemark = quoteRemark;
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
	public String getReplaceId() {
		return replaceId;
	}

	public void setReplaceId(String replaceId) {
		this.replaceId = replaceId;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getCustomerQuantity() {
		return customerQuantity;
	}

	public void setCustomerQuantity(float customerQuantity) {
		this.customerQuantity = customerQuantity;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCustomerUnit() {
		return customerUnit;
	}

	public void setCustomerUnit(String customerUnit) {
		this.customerUnit = customerUnit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getConversionQuantity() {
		return conversionQuantity;
	}

	public void setConversionQuantity(float conversionQuantity) {
		this.conversionQuantity = conversionQuantity;
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
	public String getReplaceRemark() {
		return replaceRemark;
	}

	public void setReplaceRemark(String replaceRemark) {
		this.replaceRemark = replaceRemark;
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
	 * @hibernate.property type="string"
	 */
	public String getPriceCur() {
		return priceCur;
	}

	public void setPriceCur(String priceCur) {
		this.priceCur = priceCur;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcPurDis() {
		return vcPurDis;
	}

	public void setVcPurDis(float vcPurDis) {
		this.vcPurDis = vcPurDis;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getShijia() {
		return shijia;
	}

	public void setShijia(float shijia) {
		this.shijia = shijia;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public float getSigMoney() {
		return sigMoney;
	}

	public void setSigMoney(float sigMoney) {
		this.sigMoney = sigMoney;
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
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsHiddenisReplaced() {
		return isHiddenisReplaced;
	}

	public void setIsHiddenisReplaced(String isHiddenisReplaced) {
		this.isHiddenisReplaced = isHiddenisReplaced;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFactoryNum() {
		return vcFactoryNum;
	}

	public void setVcFactoryNum(String vcFactoryNum) {
		this.vcFactoryNum = vcFactoryNum;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getAmountrmb() {
		return amountrmb;
	}

	public void setAmountrmb(float amountrmb) {
		this.amountrmb = amountrmb;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealMonny() {
		return realMonny;
	}

	public void setRealMonny(float realMonny) {
		this.realMonny = realMonny;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOffsetQF() {
		return offsetQF;
	}

	public void setOffsetQF(String offsetQF) {
		this.offsetQF = offsetQF;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getLowFreight() {
		return lowFreight;
	}

	public void setLowFreight(String lowFreight) {
		this.lowFreight = lowFreight;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getBrandAttri() {
		return brandAttri;
	}
	public void setBrandAttri(String brandAttri) {
		this.brandAttri = brandAttri;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getProductRange() {
		return productRange;
	}
	public void setProductRange(String productRange) {
		this.productRange = productRange;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjInlandTransCost() {
		return dhjInlandTransCost;
	}

	public void setDhjInlandTransCost(float dhjInlandTransCost) {
		this.dhjInlandTransCost = dhjInlandTransCost;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjHKTransCost() {
		return dhjHKTransCost;
	}

	public void setDhjHKTransCost(float dhjHKTransCost) {
		this.dhjHKTransCost = dhjHKTransCost;
	}
	public float getDhjVcOldPrice() {
		return dhjVcOldPrice;
	}

	public void setDhjVcOldPrice(float dhjVcOldPrice) {
		this.dhjVcOldPrice = dhjVcOldPrice;
	}
	public float getDhjHKVcPrice() {
		return dhjHKVcPrice;
	}

	public void setDhjHKVcPrice(float dhjHKVcPrice) {
		this.dhjHKVcPrice = dhjHKVcPrice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcComposition() {
		return vcComposition;
	}

	public void setVcComposition(String vcComposition) {
		this.vcComposition = vcComposition;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getMOQ() {
		return MOQ;
	}
	public void setMOQ(String moq) {
		this.MOQ = moq;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getLeastIncrement() {
		return leastIncrement;
	}
	public void setLeastIncrement(String leastIncrement) {
		this.leastIncrement = leastIncrement;
	}


	public float getDhjFinalOldPrice() {
		return dhjFinalOldPrice;
	}

	public void setDhjFinalOldPrice(float dhjFinalOldPrice) {
		this.dhjFinalOldPrice = dhjFinalOldPrice;
	}

	public float getDhjHKFinalPrice() {
		return dhjHKFinalPrice;
	}

	public void setDhjHKFinalPrice(float dhjHKFinalPrice) {
		this.dhjHKFinalPrice = dhjHKFinalPrice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcModelNumDisplay() {
		return vcModelNumDisplay;
	}

	public void setVcModelNumDisplay(String vcModelNumDisplay) {
		this.vcModelNumDisplay = vcModelNumDisplay;
	}

	public String getIsCgbj() {
		return isCgbj;
	}

	public void setIsCgbj(String isCgbj) {
		this.isCgbj = isCgbj;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcDis() {
		return vcDis;
	}

	public void setVcDis(String vcDis) {
		this.vcDis = vcDis;
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
	
	
	
}
