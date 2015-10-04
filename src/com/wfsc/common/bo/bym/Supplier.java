package com.wfsc.common.bo.bym;


import java.util.Date;


/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_supplier" lazy="false"
 */
public class Supplier implements java.io.Serializable {

	private static final long serialVersionUID = 6845098543471L;
	
	private Long id;
	/**
	 * 工厂代码
	 */
	private String vcFactoryCode ;
	/**
	 * 工厂名称
	 */
	private String vcFactoryName ;
	/**
	 * 产地
	 */
	private String vcPlaceProduce ;
	/**
	 * 布料进货货币
	 */
	private String vcFabPriceCur ;
	/**
	 * 布料进货尺度
	 */
	private String vcFabMeasure;
	/**
	 * 布料进货折扣
	 */
	private float vcFabPurDis;
	/**
	 * 皮料进货货币
	 */
	private String vcLeaPriceCur;
	/**
	 * 皮料进货尺度
	 */
	private String vcLeaMeasure;
	//皮料进货折扣
	private float vcLeaPurDis ;
	/**
	 * 布料最低运费
	 */
	private float vcFabMinFre;
	/**
	 * 皮料最低费用
	 */
	private float vcLeaMinFre;
	/**
	 * 地址
	 */
	private String vcFactoryAddr;
	/**
	 * 联系人
	 */
	private String vcLinkMan;
	/**
	 * 联系电话
	 */
	private String vcTel;
	/**
	 * 传真
	 */
	private String vcFax;
	/**
	 * 电子邮件
	 */
	private String vcEmail;
	/**
	 * 备注
	 */
	private String vcRemarks;
	/**
	 * 是否停用
	 */
	private String vcDis;
	/**
	 * 日期（废弃）
	 */
	private Date createDate;
	/**
	 * 产地编号
	 */
	private String vcFactoryNum;
	/**
	 * 操作者（废弃）
	 */
	private String curUserName;

	/**
	 * 国内运费
	 */
	private String homeTransportCost;
	/**
	 * 国内最低运费
	 */
	private float homeLowTransportCost;
	/**
	 * 香港运费
	 */
	private String hkTransportCost;
	/**
	 * 香港最低运费
	 */
	private float hkLowTransportCost;
	/**
	 * 零售系数
	 */
	private float retailCoefficient;
	
	/**
	 * 品牌属性
	 */
	private String brandAttri;
	/**
	 * 产品范围
	 */
	private String productRange;
	
	//订货折扣
	private String orderDis;
	
	//QQ
	private String qq;
	
	//市场透明度
	private String marketClarity;
	
	//工厂属性
	private String factoryPro;
	
	//备注二
	private String vcRemarks2;
	
	private String MOQ;//最小起订量
	private String leastIncrement;//最小增量
	
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
	public String getCurUserName() {
		return curUserName;
	}
	public void setCurUserName(String curUserName) {
		this.curUserName = curUserName;
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
	public String getVcDis() {
		return vcDis;
	}
	public void setVcDis(String vcDis) {
		this.vcDis = vcDis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcEmail() {
		return vcEmail;
	}
	public void setVcEmail(String vcEmail) {
		this.vcEmail = vcEmail;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFabMeasure() {
		return vcFabMeasure;
	}
	public void setVcFabMeasure(String vcFabMeasure) {
		this.vcFabMeasure = vcFabMeasure;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcFabMinFre() {
		return vcFabMinFre;
	}
	public void setVcFabMinFre(float vcFabMinFre) {
		this.vcFabMinFre = vcFabMinFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFabPriceCur() {
		return vcFabPriceCur;
	}
	public void setVcFabPriceCur(String vcFabPriceCur) {
		this.vcFabPriceCur = vcFabPriceCur;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcFabPurDis() {
		return vcFabPurDis;
	}
	public void setVcFabPurDis(float vcFabPurDis) {
		this.vcFabPurDis = vcFabPurDis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFactoryAddr() {
		return vcFactoryAddr;
	}
	public void setVcFactoryAddr(String vcFactoryAddr) {
		this.vcFactoryAddr = vcFactoryAddr;
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
	public String getVcFactoryName() {
		return vcFactoryName;
	}
	public void setVcFactoryName(String vcFactoryName) {
		this.vcFactoryName = vcFactoryName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFax() {
		return vcFax;
	}
	public void setVcFax(String vcFax) {
		this.vcFax = vcFax;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLeaMeasure() {
		return vcLeaMeasure;
	}
	public void setVcLeaMeasure(String vcLeaMeasure) {
		this.vcLeaMeasure = vcLeaMeasure;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcLeaMinFre() {
		return vcLeaMinFre;
	}
	public void setVcLeaMinFre(float vcLeaMinFre) {
		this.vcLeaMinFre = vcLeaMinFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLeaPriceCur() {
		return vcLeaPriceCur;
	}
	public void setVcLeaPriceCur(String vcLeaPriceCur) {
		this.vcLeaPriceCur = vcLeaPriceCur;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcLeaPurDis() {
		return vcLeaPurDis;
	}
	public void setVcLeaPurDis(float vcLeaPurDis) {
		this.vcLeaPurDis = vcLeaPurDis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLinkMan() {
		return vcLinkMan;
	}
	public void setVcLinkMan(String vcLinkMan) {
		this.vcLinkMan = vcLinkMan;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcPlaceProduce() {
		return vcPlaceProduce;
	}
	public void setVcPlaceProduce(String vcPlaceProduce) {
		this.vcPlaceProduce = vcPlaceProduce;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRemarks() {
		return vcRemarks;
	}
	public void setVcRemarks(String vcRemarks) {
		this.vcRemarks = vcRemarks;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcTel() {
		return vcTel;
	}
	public void setVcTel(String vcTel) {
		this.vcTel = vcTel;
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
	 * @hibernate.property type="string"
	 */
	public String getHomeTransportCost() {
		return homeTransportCost;
	}
	public void setHomeTransportCost(String homeTransportCost) {
		this.homeTransportCost = homeTransportCost;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHomeLowTransportCost() {
		return homeLowTransportCost;
	}
	public void setHomeLowTransportCost(float homeLowTransportCost) {
		this.homeLowTransportCost = homeLowTransportCost;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getHkTransportCost() {
		return hkTransportCost;
	}
	public void setHkTransportCost(String hkTransportCost) {
		this.hkTransportCost = hkTransportCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getHkLowTransportCost() {
		return hkLowTransportCost;
	}
	public void setHkLowTransportCost(float hkLowTransportCost) {
		this.hkLowTransportCost = hkLowTransportCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRetailCoefficient() {
		return retailCoefficient;
	}
	public void setRetailCoefficient(float retailCoefficient) {
		this.retailCoefficient = retailCoefficient;
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
	 * @hibernate.property type="string"
	 */
	public String getOrderDis() {
		return orderDis;
	}
	public void setOrderDis(String orderDis) {
		this.orderDis = orderDis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getMarketClarity() {
		return marketClarity;
	}
	public void setMarketClarity(String marketClarity) {
		this.marketClarity = marketClarity;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getFactoryPro() {
		return factoryPro;
	}
	public void setFactoryPro(String factoryPro) {
		this.factoryPro = factoryPro;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRemarks2() {
		return vcRemarks2;
	}
	public void setVcRemarks2(String vcRemarks2) {
		this.vcRemarks2 = vcRemarks2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getMOQ() {
		return MOQ;
	}
	public void setMOQ(String moq) {
		MOQ = moq;
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
	
	
}
