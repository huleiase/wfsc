package com.wfsc.common.bo.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wfsc.common.bo.user.User;

/**
 * 订单
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_orders" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Orders implements Serializable {

	/** 失败订单 */
	public static final int STATUS_FAIL = 0;

	/** 经过支付服务器的确认，成功的订单 */
	public static final int STATUS_OK = 2;

	/** 订单已生成，待确认状态 */
	public static final int STATUS_PENDING = 1;

	private static final long serialVersionUID = -3222083537911724061L;

	public static final String CTYPE_RMB = "RMB";

	private Long id;

	/** 订单号 */
	private String orderNo;

	/** 财付通交易ID */
	private String transactionId;

	/** 银行订单号 */
	private String bankBillNo;

	/** 银行类型 */
	private String bankType;

	/** 支付用户 */
	private User user;
	
	private Long feePrice;

	/** 支付金额:单位：元 */
	private Float fee;

	/** 下单时间 */
	private Date odate;
	
	/** 支付时间 datetime */
	private Date chargeDate;

	/** 支付类型 */
	private String ctype = "RMB";

	/** 订单状态 订单状态 0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃*/
	private int status = 0;
	
	private Long transFeePrice;
	
	/** 运费 */
	private Float transFee;
	
	// 收货地址
	private String address;

	// 收货人姓名
	private String addressee;
	
	// 收货人电话
	private String phone;
	
	private List<OrdersDetail> ordersDetail = new ArrayList<OrdersDetail>();

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
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.User" column="userId"
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Float getFee() {
		if(this.feePrice != null){
			return Float.valueOf(this.feePrice / 100f);
		}
		return fee;
	}

	public void setFee(Float fee) {
		if(fee != null){
			this.feePrice = (long)(fee.floatValue() * 100);
		}
		this.fee = fee;
	}

	/**
	 * @hibernate.property type="timestamp" column="chargeDate"
	 */
	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
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
	 * @hibernate.property type="int"
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBankBillNo() {
		return bankBillNo;
	}

	public void setBankBillNo(String bankBillNo) {
		this.bankBillNo = bankBillNo;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @hibernate.property type="timestamp" column="odate"
	 */
	public Date getOdate() {
		return odate;
	}

	
	public void setOdate(Date odate) {
		this.odate = odate;
	}

	public Float getTransFee() {
		if(transFee != null){
			this.transFeePrice = (long)(transFee.floatValue() * 100);
		}
		return transFee;
	}

	public void setTransFee(Float transFee) {
		if(transFee != null){
			this.transFeePrice = (long)(transFee.floatValue() * 100);
		}
		this.transFee = transFee;
	}

	public List<OrdersDetail> getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
		this.ordersDetail = ordersDetail;
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
	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="long" column="fee"
	 */
	public Long getFeePrice() {
		return feePrice;
	}

	public void setFeePrice(Long feePrice) {
		this.feePrice = feePrice;
	}
	
	/**
	 * @hibernate.property type="long" column="transFee"
	 */
	public Long getTransFeePrice() {
		return transFeePrice;
	}

	public void setTransFeePrice(Long transFeePrice) {
		this.transFeePrice = transFeePrice;
	}
	
}
