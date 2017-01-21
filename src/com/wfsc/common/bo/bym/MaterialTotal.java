package com.wfsc.common.bo.bym;

public class MaterialTotal {
	private String orderNo;
	private Float bjTotal;
	private Float sumMoney;
	private Float cbTotal;
	private Float sellProfit;
	private Float sellProfitRate;
	private String contractNo;
	public Float getBjTotal() {
		return bjTotal;
	}
	public void setBjTotal(Float bjTotal) {
		this.bjTotal = bjTotal;
	}
	public Float getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(Float sumMoney) {
		this.sumMoney = sumMoney;
	}
	public Float getCbTotal() {
		return cbTotal;
	}
	public void setCbTotal(Float cbTotal) {
		this.cbTotal = cbTotal;
	}
	public Float getSellProfit() {
		return sellProfit;
	}
	public void setSellProfit(Float sellProfit) {
		this.sellProfit = sellProfit;
	}
	public Float getSellProfitRate() {
		return sellProfitRate;
	}
	public void setSellProfitRate(Float sellProfitRate) {
		this.sellProfitRate = sellProfitRate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}
