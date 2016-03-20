package com.wfsc.actions.vo;

import com.wfsc.common.bo.bym.DesignerOrder;

public class MaterialDetailDto extends DesignerOrder {
	/**
	 * 报价的型号
	 */
	private String bjModelNum;

	/**
	 * 最终单价 (报价)
	 */
	private float vcPrice;

	/**
	 * 最终单价单位（报价）
	 */
	private String vcPriceUnit;

	/**
	 * (报价数量)
	 */
	private float vcQuantity;
	
	//税率
	private float taxation;
	
	/**
	 * 成本的型号
	 */
	private String cbModelNum;
	
	/**
	 * 最终单价 (成本)
	 */
	private float cbPrice;

	/**
	 * 最终单价单位（成本）
	 */
	private String cbPriceUnit;

	/**
	 * (成本数量)
	 */
	private float cbQuantity;
	//报价合计
	private float bjTotal;
	//成本合计
	private float cbTotal;
	//销售毛利
	private float sellProfit; 
	//销售毛利率
	private float sellProfitRate;
	public String getBjModelNum() {
		return bjModelNum;
	}
	public void setBjModelNum(String bjModelNum) {
		this.bjModelNum = bjModelNum;
	}
	public float getVcPrice() {
		return vcPrice;
	}
	public void setVcPrice(float vcPrice) {
		this.vcPrice = vcPrice;
	}
	public String getVcPriceUnit() {
		return vcPriceUnit;
	}
	public void setVcPriceUnit(String vcPriceUnit) {
		this.vcPriceUnit = vcPriceUnit;
	}
	public float getVcQuantity() {
		return vcQuantity;
	}
	public void setVcQuantity(float vcQuantity) {
		this.vcQuantity = vcQuantity;
	}
	public float getTaxation() {
		return taxation;
	}
	public void setTaxation(float taxation) {
		this.taxation = taxation;
	}
	public String getCbModelNum() {
		return cbModelNum;
	}
	public void setCbModelNum(String cbModelNum) {
		this.cbModelNum = cbModelNum;
	}
	public float getCbPrice() {
		return cbPrice;
	}
	public void setCbPrice(float cbPrice) {
		this.cbPrice = cbPrice;
	}
	public String getCbPriceUnit() {
		return cbPriceUnit;
	}
	public void setCbPriceUnit(String cbPriceUnit) {
		this.cbPriceUnit = cbPriceUnit;
	}
	public float getCbQuantity() {
		return cbQuantity;
	}
	public void setCbQuantity(float cbQuantity) {
		this.cbQuantity = cbQuantity;
	}
	public float getBjTotal() {
		return bjTotal;
	}
	public void setBjTotal(float bjTotal) {
		this.bjTotal = bjTotal;
	}
	public float getCbTotal() {
		return cbTotal;
	}
	public void setCbTotal(float cbTotal) {
		this.cbTotal = cbTotal;
	}
	public float getSellProfit() {
		return sellProfit;
	}
	public void setSellProfit(float sellProfit) {
		this.sellProfit = sellProfit;
	}
	public float getSellProfitRate() {
		return sellProfitRate;
	}
	public void setSellProfitRate(float sellProfitRate) {
		this.sellProfitRate = sellProfitRate;
	}
	
	

}
