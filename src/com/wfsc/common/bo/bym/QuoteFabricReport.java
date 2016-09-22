package com.wfsc.common.bo.bym;

import java.util.Date;



/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_qf_report" lazy="false"
 */
public class QuoteFabricReport implements java.io.Serializable, Cloneable{

	private static final long serialVersionUID = 682265456723471L;
	
	private Long id;
	
	private Long quoteId;

	/**
	 * 报价的型号
	 */
	private String vcModelNum;

	/**
	 * 最终单价 (报价)
	 */
	private float vcPrice;

	/**
	 * 最终单价数量的单位，比如m,yd等（报价）
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
	
	//private DesignerOrder designerOrder;
	
	
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
	 * 成本的型号
	 */
	private String cbModelNum;
	
	/**
	 * 最终单价 (成本) 实价
	 */
	private float cbPrice;

	/**
	 * 最终单价数量单位，比如m,yd等（成本）
	 */
	private String cbPriceUnit;

	/**
	 * 实订量(成本数量)
	 */
	private float cbQuantity;
	//报价合计
	private float bjTotal;
	//成本合计（币种合计）
	private float cbTotal;
	//销售毛利
	private float sellProfit; 
	//销售毛利率
	private float sellProfitRate;
	//替代型号
	private String replaceNO;
	
	private Date contractDate;
	
	private String contractNo;
	
	private String orderNo;
	//客户名称
	private String customerName;
	//项目
	private String projectName;
	//合同金额
	private float sumMoney;
	private Date createDate;
	private String quoteLocal;
	//报价色号
	private String bjColor;
	//订单色号
	private String cbColor;
	//订货量
	private float orderNum;
	//单价
	private float singleMoney;
	//税金
	private float taxes;
	//报价货币单位，比如RMB，HKD
	private String vcMoney;
	//成本货币单位,比如RMB，HKD等
	private String priceCur;
	//合计 cbTotal转换成RMB或hkd后的钱
	private float amountrmb;
	
	//供应商
	private String supplier;
	//订单id
	private Long bymOrderId;
	//
	private String vcFactoryNum;
	
	//新加

	/**
	 *  工程运费/零售运费
	 */
	private float vcFre;
	/**
	 * 工地运费
	 */
	private float vcSpecialExp;
	/**
	 * 面价*报价折扣
	 */
	private float vcOldPrice;
	
	//产品材料报价合计=bjPrice*vcQuantity
	private float vcOldPriceTotal;
	
	//单价调整
	private float priceAdjust;
	
	//材料明细表中报价的单价
	private float bjPrice;
	//幅宽
	private float vcWidth;
	
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
	/*public DesignerOrder getDesignerOrder() {
		return designerOrder;
	}

	public void setDesignerOrder(DesignerOrder designerOrder) {
		this.designerOrder = designerOrder;
	}*/
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
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbPrice() {
		return cbPrice;
	}

	public void setCbPrice(float cbPrice) {
		this.cbPrice = cbPrice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCbPriceUnit() {
		return cbPriceUnit;
	}

	public void setCbPriceUnit(String cbPriceUnit) {
		this.cbPriceUnit = cbPriceUnit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbQuantity() {
		return cbQuantity;
	}

	public void setCbQuantity(float cbQuantity) {
		this.cbQuantity = cbQuantity;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getBjTotal() {
		return bjTotal;
	}

	public void setBjTotal(float bjTotal) {
		this.bjTotal = bjTotal;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbTotal() {
		return cbTotal;
	}

	public void setCbTotal(float cbTotal) {
		this.cbTotal = cbTotal;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getSellProfit() {
		return sellProfit;
	}

	public void setSellProfit(float sellProfit) {
		this.sellProfit = sellProfit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getSellProfitRate() {
		return sellProfitRate;
	}

	public void setSellProfitRate(float sellProfitRate) {
		this.sellProfitRate = sellProfitRate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getReplaceNO() {
		return replaceNO;
	}

	public void setReplaceNO(String replaceNO) {
		this.replaceNO = replaceNO;
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
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	 * @return
	 * @hibernate.property type="timestamp" column="createDate"
	 */
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @hibernate.property type="string"
	 */
	public String getCbModelNum() {
		return cbModelNum;
	}

	public void setCbModelNum(String cbModelNum) {
		this.cbModelNum = cbModelNum;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getBjColor() {
		return bjColor;
	}

	public void setBjColor(String bjColor) {
		this.bjColor = bjColor;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCbColor() {
		return cbColor;
	}

	public void setCbColor(String cbColor) {
		this.cbColor = cbColor;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(float orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(float singleMoney) {
		this.singleMoney = singleMoney;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getTaxes() {
		return taxes;
	}

	public void setTaxes(float taxes) {
		this.taxes = taxes;
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
	 * @hibernate.property type="string"
	 */
	public String getVcMoney() {
		return vcMoney;
	}

	public void setVcMoney(String vcMoney) {
		this.vcMoney = vcMoney;
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
	 * @hibernate.property type="string"
	 */
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getBymOrderId() {
		return bymOrderId;
	}

	public void setBymOrderId(Long bymOrderId) {
		this.bymOrderId = bymOrderId;
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
	public float getVcSpecialExp() {
		return vcSpecialExp;
	}

	public void setVcSpecialExp(float vcSpecialExp) {
		this.vcSpecialExp = vcSpecialExp;
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
	 * @hibernate.property type="float"
	 */
	public float getVcOldPriceTotal() {
		return vcOldPriceTotal;
	}

	public void setVcOldPriceTotal(float vcOldPriceTotal) {
		this.vcOldPriceTotal = vcOldPriceTotal;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcFre() {
		return vcFre;
	}

	public void setVcFre(float vcFre) {
		this.vcFre = vcFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getPriceAdjust() {
		return priceAdjust;
	}

	public void setPriceAdjust(float priceAdjust) {
		this.priceAdjust = priceAdjust;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getBjPrice() {
		return bjPrice;
	}

	public void setBjPrice(float bjPrice) {
		this.bjPrice = bjPrice;
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
	
	
	
}
