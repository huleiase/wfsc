package com.wfsc.common.bo.bym;

import java.util.Date;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_email" lazy="false"
 */
public class Email implements java.io.Serializable {

	private static final long serialVersionUID = 68454678971L;
	
	private Long id;
	//报价单id
	private Long quoteId;
	
	private String quoteNo;
	//状态1未读，2已读
	private String state;
	//发送的人
	private String sender;
	//发送时间
	private Date sendTime;
	//发送内容
	private String detail;
	//要发送的人
	private String username;
	//值只有quote,toPurchase,purchase,order
	private String action;
	
	private Long purchaseId;
	
	private String purchaseNo;
	
	private Long orderId;
	
	private String orderNo;
	
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="sendTime"
	 */
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
