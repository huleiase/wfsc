package com.wfsc.common.bo.account;
/**
 * 优惠券
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_coupon" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Coupon {
	private static final long serialVersionUID = 2500471860354748649L;
	private Long id;
	private String couponCode;//优惠券代码
	private String couponName;//优惠券名称
	private Float couponMoney;//优惠券金额
	private Float needCustomMoney;//需消费金额
	private String couponType;//优惠券类型
	private String useLimit;//使用限制
	private String userfulLife;//有效期限
	private Integer status;//状态
	private Long userid;
	
	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	/**
	 * @return
	 * @hibernate.property type="float"
	 */
	public Float getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(Float couponMoney) {
		this.couponMoney = couponMoney;
	}
	/**
	 * @return
	 * @hibernate.property type="float"
	 */
	public Float getNeedCustomMoney() {
		return needCustomMoney;
	}
	public void setNeedCustomMoney(Float needCustomMoney) {
		this.needCustomMoney = needCustomMoney;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getUseLimit() {
		return useLimit;
	}
	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getUserfulLife() {
		return userfulLife;
	}
	public void setUserfulLife(String userfulLife) {
		this.userfulLife = userfulLife;
	}
	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	

}
