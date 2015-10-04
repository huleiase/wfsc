package com.wfsc.common.bo.bym;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_designerorder" lazy="false"
 */
public class DesignerOrder implements java.io.Serializable, Cloneable{

	private static final long serialVersionUID = 6845454567871L;
	
	private Long id;
	private Long orderId;
	
	private String orderNo;
	
	private Date contractDate;
	
	private String contractNo;
	
	private String quoteLocal;
	
	private float realTotel;//实际金额=本地收入
	/**
	 * 分摊地（广州）
	 */
	private String shareArea1;
	/**
	 * 分摊比例
	 */
	private float shareRate1;
	/**
	 * 分摊金额
	 */
	private float shareMony1;
	/**
	 * 上海
	 */
	private String shareArea2;
	private float shareRate2;
	private float shareMony2;
	/**
	 * 北京
	 */
	private String shareArea3;
	private float shareRate3;
	private float shareMony3;
	/**
	 * 深圳
	 */
	private String shareArea4;
	private float shareRate4;
	private float shareMony4;
	/**
	 * 香港
	 */
	private String shareArea5;
	private float shareRate5;
	private float shareMony5;
	/**
	 * 分摊合计
	 */
	private float sharetotle;
	
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 是否开具发票
	 */
	private String isInvoice;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 收款时间
	 */
	private Date gatheringDate;
	
	/**
	 * 定金
	 */
	
	private float frontMoney;
	/**
	 * 进度款1
	 */
	private float planMoney1;
	/**
	 * 进度款2
	 */
	private float planMoney2;
	/**
	 * 进度款3
	 */
	private float planMoney3;
	/**
	 * 保质金
	 */
	private float qualityMoney;
	
	/**
	 * 已付合计
	 */
	private float hasApplyTotle;
	
	/**
	 * 未付余额（合同金额-已付）
	 */
	private float hasNoApplyTotle;
	
	/**
	 * 是否付清
	 * @return
	 */
	private String isPayTotle;
	
	/**
	 * 收款地
	 */
	
	private String gatheringArea;
	
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
	
	private float mon1;
	private float mon2;
	private float mon3;
	private float mon4;
	private float mon5;
	private float mon6;
	private float mon7;
	private float mon8;
	private float mon9;
	private float mon10;
	private float mon11;
	private float mon12;
	//报价单
	private Long quoteId;
	//客户名称
	private String customerName;
	//项目
	private String projectName;
	//合同金额
	private float sumMoney;
	
	private Set<QuoteFabricReport> qfrSet = new HashSet<QuoteFabricReport>();
	
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
	 * @hibernate.property type="long"
	 */
	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getQuoteLocal() {
		return quoteLocal;
	}


	public void setQuoteLocal(String quoteLocal) {
		this.quoteLocal = quoteLocal;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getRealTotel() {
		return realTotel;
	}


	public void setRealTotel(float realTotel) {
		this.realTotel = realTotel;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShareArea1() {
		return shareArea1;
	}


	public void setShareArea1(String shareArea1) {
		this.shareArea1 = shareArea1;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareRate1() {
		return shareRate1;
	}


	public void setShareRate1(float shareRate1) {
		this.shareRate1 = shareRate1;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareMony1() {
		return shareMony1;
	}


	public void setShareMony1(float shareMony1) {
		this.shareMony1 = shareMony1;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShareArea2() {
		return shareArea2;
	}


	public void setShareArea2(String shareArea2) {
		this.shareArea2 = shareArea2;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareRate2() {
		return shareRate2;
	}


	public void setShareRate2(float shareRate2) {
		this.shareRate2 = shareRate2;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareMony2() {
		return shareMony2;
	}


	public void setShareMony2(float shareMony2) {
		this.shareMony2 = shareMony2;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShareArea3() {
		return shareArea3;
	}


	public void setShareArea3(String shareArea3) {
		this.shareArea3 = shareArea3;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareRate3() {
		return shareRate3;
	}


	public void setShareRate3(float shareRate3) {
		this.shareRate3 = shareRate3;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareMony3() {
		return shareMony3;
	}


	public void setShareMony3(float shareMony3) {
		this.shareMony3 = shareMony3;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShareArea4() {
		return shareArea4;
	}


	public void setShareArea4(String shareArea4) {
		this.shareArea4 = shareArea4;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareRate4() {
		return shareRate4;
	}


	public void setShareRate4(float shareRate4) {
		this.shareRate4 = shareRate4;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareMony4() {
		return shareMony4;
	}


	public void setShareMony4(float shareMony4) {
		this.shareMony4 = shareMony4;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getShareArea5() {
		return shareArea5;
	}


	public void setShareArea5(String shareArea5) {
		this.shareArea5 = shareArea5;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareRate5() {
		return shareRate5;
	}


	public void setShareRate5(float shareRate5) {
		this.shareRate5 = shareRate5;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getShareMony5() {
		return shareMony5;
	}


	public void setShareMony5(float shareMony5) {
		this.shareMony5 = shareMony5;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getSharetotle() {
		return sharetotle;
	}


	public void setSharetotle(float sharetotle) {
		this.sharetotle = sharetotle;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getIsInvoice() {
		return isInvoice;
	}


	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="gatheringDate"
	 */
	public Date getGatheringDate() {
		return gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getFrontMoney() {
		return frontMoney;
	}


	public void setFrontMoney(float frontMoney) {
		this.frontMoney = frontMoney;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getPlanMoney1() {
		return planMoney1;
	}


	public void setPlanMoney1(float planMoney1) {
		this.planMoney1 = planMoney1;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getPlanMoney2() {
		return planMoney2;
	}


	public void setPlanMoney2(float planMoney2) {
		this.planMoney2 = planMoney2;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getPlanMoney3() {
		return planMoney3;
	}


	public void setPlanMoney3(float planMoney3) {
		this.planMoney3 = planMoney3;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getQualityMoney() {
		return qualityMoney;
	}


	public void setQualityMoney(float qualityMoney) {
		this.qualityMoney = qualityMoney;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHasApplyTotle() {
		return hasApplyTotle;
	}


	public void setHasApplyTotle(float hasApplyTotle) {
		this.hasApplyTotle = hasApplyTotle;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHasNoApplyTotle() {
		return hasNoApplyTotle;
	}


	public void setHasNoApplyTotle(float hasNoApplyTotle) {
		this.hasNoApplyTotle = hasNoApplyTotle;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getIsPayTotle() {
		return isPayTotle;
	}


	public void setIsPayTotle(String isPayTotle) {
		this.isPayTotle = isPayTotle;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getGatheringArea() {
		return gatheringArea;
	}


	public void setGatheringArea(String gatheringArea) {
		this.gatheringArea = gatheringArea;
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
	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon1() {
		return mon1;
	}


	public void setMon1(float mon1) {
		this.mon1 = mon1;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon2() {
		return mon2;
	}


	public void setMon2(float mon2) {
		this.mon2 = mon2;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon3() {
		return mon3;
	}


	public void setMon3(float mon3) {
		this.mon3 = mon3;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon4() {
		return mon4;
	}


	public void setMon4(float mon4) {
		this.mon4 = mon4;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon5() {
		return mon5;
	}


	public void setMon5(float mon5) {
		this.mon5 = mon5;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon6() {
		return mon6;
	}


	public void setMon6(float mon6) {
		this.mon6 = mon6;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon7() {
		return mon7;
	}


	public void setMon7(float mon7) {
		this.mon7 = mon7;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon8() {
		return mon8;
	}


	public void setMon8(float mon8) {
		this.mon8 = mon8;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon9() {
		return mon9;
	}


	public void setMon9(float mon9) {
		this.mon9 = mon9;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon10() {
		return mon10;
	}


	public void setMon10(float mon10) {
		this.mon10 = mon10;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon11() {
		return mon11;
	}


	public void setMon11(float mon11) {
		this.mon11 = mon11;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getMon12() {
		return mon12;
	}


	public void setMon12(float mon12) {
		this.mon12 = mon12;
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
	 * @hibernate.collection-key column="doId"
	 * @hibernate.collection-one-to-many class="com.wfsc.common.bo.bym.QuoteFabricReport"
	 * @hibernate.set cascade="all" lazy="false" inverse="true"
	 */
	public Set<QuoteFabricReport> getQfrSet() {
		return qfrSet;
	}
	public void setQfrSet(Set<QuoteFabricReport> qfrSet) {
		this.qfrSet = qfrSet;
	}
	

}
