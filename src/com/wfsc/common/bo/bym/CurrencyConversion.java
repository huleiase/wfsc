package com.wfsc.common.bo.bym;



/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_currencyconversion" lazy="false"
 */
public class CurrencyConversion  implements java.io.Serializable{
	
	private static final long serialVersionUID = 688337555525571L;
	
	private Long id;
	/**
	 * 原始货币
	 */
	private String vcCurrency;
	/**
	 * 目标货币
	 */
	private String vcObjCurrency;
	/**
	 * 汇率
	 */
	private float vcExchangeRate;
	
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
	public String getVcCurrency() {
		return vcCurrency;
	}
	public void setVcCurrency(String vcCurrency) {
		this.vcCurrency = vcCurrency;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcExchangeRate() {
		return vcExchangeRate;
	}
	public void setVcExchangeRate(float vcExchangeRate) {
		this.vcExchangeRate = vcExchangeRate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcObjCurrency() {
		return vcObjCurrency;
	}
	public void setVcObjCurrency(String vcObjCurrency) {
		this.vcObjCurrency = vcObjCurrency;
	}
}
