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
	private String realArea;//本地
	private float realRate;//本地
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
	
	private String operation;
	
	private Date createDate;
	//////////////////////////////////////////////////////////////
	//报价材料合计。后处理+其它+电机合计+阻燃+5个报价的空
	private float bjClTotel;
	//加工费。读取报价页面
	private float vcProcessFre;
	//量窗费。读取报价页面
	private float lcFre;
	//安装费。读取报价页面
	private float vcInstallFre;
	//运费.（各个型号的实际采购数量的值*具体类型的运费，再相加）+最低运费+货到工地运费
	private float bjFreight;
	//报价合计
	private float bjTotel;
	//////////////////////////////
	//销售成本材料合计
	private float cbClTotel;
	/////////////////////////////////
	//加工费.读取采购页面
	private float processFee;
	//安装费 读取采购页面
	private float installFee;
	//运费 同一订单号订单运费相加
	private float cbFreight;
	//差旅费 读取采购页面
	private float travelExpenses;
	//设计费
	private float designFre;
	//其他 读取采购页面
	private float otherFre;
	//销售费用合计
	private float cbTotel;
	//毛利
	private float profit; 
	//毛利率
	private float profitRate;
	//设计师1
	private String designer1;
	private float designMony1;
	//设计师2
	private String designer2;
	private float designMony2;
	//设计师3
	private String designer3;
	private float designMony3;
	//抬头 读取报价单第三行的from
	private String vcFrom;
	//销售毛利
	//private float sellProfit; 
	//销售毛利率
	//private float sellProfitRate;
	//税率
	private float taxation;
	//税费
	private float taxationFee;
	//税金
	private float taxes;
	
	private float yue;
	//计算后面价的合计
	private float bjOldPriceTatol;
	
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
	/*
	public Set<QuoteFabricReport> getQfrSet() {
		return qfrSet;
	}
	public void setQfrSet(Set<QuoteFabricReport> qfrSet) {
		this.qfrSet = qfrSet;
	}*/
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
	 * @hibernate.property type="float"
	 */
	public float getBjClTotel() {
		return bjClTotel;
	}
	public void setBjClTotel(float bjClTotel) {
		this.bjClTotel = bjClTotel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcProcessFre() {
		return vcProcessFre;
	}
	public void setVcProcessFre(float vcProcessFre) {
		this.vcProcessFre = vcProcessFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getLcFre() {
		return lcFre;
	}
	public void setLcFre(float lcFre) {
		this.lcFre = lcFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcInstallFre() {
		return vcInstallFre;
	}
	public void setVcInstallFre(float vcInstallFre) {
		this.vcInstallFre = vcInstallFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getBjFreight() {
		return bjFreight;
	}
	public void setBjFreight(float bjFreight) {
		this.bjFreight = bjFreight;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getBjTotel() {
		return bjTotel;
	}
	public void setBjTotel(float bjTotel) {
		this.bjTotel = bjTotel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbClTotel() {
		return cbClTotel;
	}
	public void setCbClTotel(float cbClTotel) {
		this.cbClTotel = cbClTotel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getProcessFee() {
		return processFee;
	}
	public void setProcessFee(float processFee) {
		this.processFee = processFee;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getInstallFee() {
		return installFee;
	}
	public void setInstallFee(float installFee) {
		this.installFee = installFee;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbFreight() {
		return cbFreight;
	}
	public void setCbFreight(float cbFreight) {
		this.cbFreight = cbFreight;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getTravelExpenses() {
		return travelExpenses;
	}
	public void setTravelExpenses(float travelExpenses) {
		this.travelExpenses = travelExpenses;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignFre() {
		return designFre;
	}
	public void setDesignFre(float designFre) {
		this.designFre = designFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getOtherFre() {
		return otherFre;
	}
	public void setOtherFre(float otherFre) {
		this.otherFre = otherFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getCbTotel() {
		return cbTotel;
	}
	public void setCbTotel(float cbTotel) {
		this.cbTotel = cbTotel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getProfit() {
		return profit;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(float profitRate) {
		this.profitRate = profitRate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesigner1() {
		return designer1;
	}
	public void setDesigner1(String designer1) {
		this.designer1 = designer1;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony1() {
		return designMony1;
	}
	public void setDesignMony1(float designMony1) {
		this.designMony1 = designMony1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesigner2() {
		return designer2;
	}
	public void setDesigner2(String designer2) {
		this.designer2 = designer2;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony2() {
		return designMony2;
	}
	public void setDesignMony2(float designMony2) {
		this.designMony2 = designMony2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesigner3() {
		return designer3;
	}
	public void setDesigner3(String designer3) {
		this.designer3 = designer3;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignMony3() {
		return designMony3;
	}
	public void setDesignMony3(float designMony3) {
		this.designMony3 = designMony3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFrom() {
		return vcFrom;
	}
	public void setVcFrom(String vcFrom) {
		this.vcFrom = vcFrom;
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
	/**
	 * @hibernate.property type="float"
	 */
	public float getTaxationFee() {
		return taxationFee;
	}
	public void setTaxationFee(float taxationFee) {
		this.taxationFee = taxationFee;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRealArea() {
		return realArea;
	}
	public void setRealArea(String realArea) {
		this.realArea = realArea;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getRealRate() {
		return realRate;
	}
	public void setRealRate(float realRate) {
		this.realRate = realRate;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getYue() {
		return yue;
	}
	public void setYue(float yue) {
		this.yue = yue;
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
	public float getBjOldPriceTatol() {
		return bjOldPriceTatol;
	}
	public void setBjOldPriceTatol(float bjOldPriceTatol) {
		this.bjOldPriceTatol = bjOldPriceTatol;
	}
	

}
