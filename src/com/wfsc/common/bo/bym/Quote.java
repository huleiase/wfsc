package com.wfsc.common.bo.bym;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_quote" lazy="false"
 */
public class Quote implements java.io.Serializable {

	private static final long serialVersionUID = 684549853471L;
	
	private Long id;
	/**
	 * 询价方
	 */
	private String vcAttn;

	/**
	 * 询价方电话
	 */
	private String vcAttnTel;

	/**
	 * 询价方传真
	 */
	private String vcAttnFax;

	/**
	 * 询价方名称
	 */
	private String vcAttnName;

	/**
	 * 询价方手机
	 */
	private String vcAttnPhone;

	/**
	 * 询价方邮箱
	 */
	private String vcAttnEmail;

	/**
	 * 设计方公司名称
	 */
	private String vcFrom;

	/**
	 * 设计方公司电话
	 */
	private String vcFormTel;

	/**
	 * 设计方公司传真
	 */
	private String vcFormFax;

	/**
	 * 设计方公司填写报价单人的姓名(跟进的销售)
	 */
	private String vcFormName;

	/**
	 * 设计方公司手机
	 */
	private String vcFormPhone;

	/**
	 * 填写报价单的日期
	 */
	private Date vcFormDate;

	/**
	 * 报价地
	 */
	private String vcQuoteLocal;
	
	/**
	 * 销售人员
	 */
	private String vcSalesman;

	/**
	 * 报价编号
	 */
	private String vcQuoteNum;

	/**
	 * 客户名称
	 */
	private String customer;

	/**
	 *  报价方式
	 */
	private String quoteFormate;

	/**
	 * 立项编号
	 */
	private String projectNum;

	/**
	 * 描述信息
	 */
	private String desInfo;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 项目地负责人
	 */
	private String projectLocalPreson;

	/**
	 * 项目地负责人销售比例
	 */
	private float projectLPSellInverse;

	/**
	 * 设计公司
	 */
	private String projectDesignComp;

	/**
	 * 设计地根进人
	 */
	private String designLocalPreson;

	/**
	 * 设计地根进人销售比例
	 */
	private float designLPSelllnverse;

	/**
	 * 加急费用
	 */
	private float urgentCost;
	
	/**
	 * 加急费备注
	 */
	private String urgentCostRmk;

	/**
	 * 最低费用
	 */
	private float lowestFreight;
	
	/**
	 * 最低运费备注
	 */
	private String lowestFreightRmk;

	/**
	 * 是否含运费
	 */
	private String isFreight;

	/**
	 * 所含税金
	 */
	private float containTax;

	/**
	 * 总金额
	 */
	private float sumMoney;
	
	/**
	 * 小计
	 */
	private float subtotal;

	/**
	 * 总金额备注
	 */
	private String sumMoneyRemark;

	/**
	 * 备注
	 */
	private String remk;

	/**
	 * 条款
	 */
	private String item;

	/**
	 * 代表方公司名称
	 */
	private String deputyCom;

	/**
	 * 代表方姓名
	 */
	private String deputyName;

	/**
	 * 审核状态
	 */
	private String vcAudit;

	/**
	 * 审核人
	 */
	private String auditPerson;

	/**
	 * 审核日期
	 */
	private Date auditDate;

	/**
	 * 操作者
	 */
	private String curUserName;
	
	/**
	 * 优惠金额
	 */
	private float discountMoney;

	/**
	 * 是否签售
	 */
	private String isWritPerm;
	/**
	 * 年月
	 */
	private String vcYearMonth;

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
	
	/**
	 * 加工费
	 */
	private String vcProcessFre;
	
	/**
	 * 安装费
	 */
	private String vcInstallFre;
	
	/**
	 * 后处理
	 */
	private String vcAftertreatment;
	
	/**
	 * 差旅费
	 */
	private String vcTravelFre;
	
	/**
	 * 其它
	 */
	private String vcOther;
	
	private Date contractDate;
	
	private String contractNo;
	//1为软删除，0为正常
	private String delFlag;
	//抵消报价单1是
	private String offsetQuote;
	
	//添加采购物品时，2表示INVOICE，1表示Confirmation 合同，0表示Quotation 报价表
	private String fabricTitle;
	
	
	private float engineTotal;//电机合计

	private float flameTotal;//阻燃

	private float arriveTransport;//货到工地运费

	private String titleCol1;// varchar(30);

	private float inputCol1;

	private String titleCol2;

	private float inputCol2;

	private String titleCol3;

	private float inputCol3;

	private String titleCol4;

	private float inputCol4;

	private String titleCol5;

	private float inputCol5;
	
	private float lcFre;
	
	private String modifyUser;//修改用户
	
	private Set<String> salesman = new HashSet<String>();
	
	private Long groupId;
	
	private boolean canPrint;//临时字段，没审核的报价单销售不能打印，其他角色都可以打印
	
	
	/**
	 * 关联的报价布匹
	 */
	private Set<QuoteFabric> quoteFabric = new HashSet<QuoteFabric>();
	
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
	public String getVcYearMonth() {
		return vcYearMonth;
	}

	public void setVcYearMonth(String vcYearMonth) {
		this.vcYearMonth = vcYearMonth;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getIsWritPerm() {
		return isWritPerm;
	}

	public void setIsWritPerm(String isWritPerm) {
		this.isWritPerm = isWritPerm;
	}

	
	
	
	/**
	 * @hibernate.collection-key column="quoteId"
	 * @hibernate.collection-one-to-many class="com.wfsc.common.bo.bym.QuoteFabric"
	 * @hibernate.set cascade="all" lazy="false" inverse="true"
	 */
	public Set<QuoteFabric> getQuoteFabric() {
		return this.quoteFabric;
	}

	public void setQuoteFabric(Set<QuoteFabric> quoteFabric) {
		this.quoteFabric = quoteFabric;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="auditDate"
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getContainTax() {
		return containTax;
	}

	public void setContainTax(float containTax) {
		this.containTax = containTax;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDeputyCom() {
		return deputyCom;
	}

	public void setDeputyCom(String deputyCom) {
		this.deputyCom = deputyCom;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDeputyName() {
		return deputyName;
	}

	public void setDeputyName(String deputyName) {
		this.deputyName = deputyName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesignLocalPreson() {
		return designLocalPreson;
	}

	public void setDesignLocalPreson(String designLocalPreson) {
		this.designLocalPreson = designLocalPreson;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDesignLPSelllnverse() {
		return designLPSelllnverse;
	}

	public void setDesignLPSelllnverse(float designLPSelllnverse) {
		this.designLPSelllnverse = designLPSelllnverse;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getDesInfo() {
		return desInfo;
	}

	public void setDesInfo(String desInfo) {
		this.desInfo = desInfo;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getIsFreight() {
		return isFreight;
	}

	public void setIsFreight(String isFreight) {
		this.isFreight = isFreight;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getLowestFreight() {
		return lowestFreight;
	}

	public void setLowestFreight(float lowestFreight) {
		this.lowestFreight = lowestFreight;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getProjectDesignComp() {
		return projectDesignComp;
	}

	public void setProjectDesignComp(String projectDesignComp) {
		this.projectDesignComp = projectDesignComp;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getProjectLocalPreson() {
		return projectLocalPreson;
	}

	public void setProjectLocalPreson(String projectLocalPreson) {
		this.projectLocalPreson = projectLocalPreson;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getProjectLPSellInverse() {
		return projectLPSellInverse;
	}

	public void setProjectLPSellInverse(float projectLPSellInverse) {
		this.projectLPSellInverse = projectLPSellInverse;
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
	 * @hibernate.property type="string"
	 */
	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getQuoteFormate() {
		return quoteFormate;
	}

	public void setQuoteFormate(String quoteFormate) {
		this.quoteFormate = quoteFormate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getRemk() {
		return remk;
	}

	public void setRemk(String remk) {
		this.remk = remk;
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
	 * @hibernate.property type="string"
	 */
	public String getSumMoneyRemark() {
		return sumMoneyRemark;
	}

	public void setSumMoneyRemark(String sumMoneyRemark) {
		this.sumMoneyRemark = sumMoneyRemark;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getUrgentCost() {
		return urgentCost;
	}

	public void setUrgentCost(float urgentCost) {
		this.urgentCost = urgentCost;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttn() {
		return vcAttn;
	}

	public void setVcAttn(String vcAttn) {
		this.vcAttn = vcAttn;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttnFax() {
		return vcAttnFax;
	}

	public void setVcAttnFax(String vcAttnFax) {
		this.vcAttnFax = vcAttnFax;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttnName() {
		return vcAttnName;
	}

	public void setVcAttnName(String vcAttnName) {
		this.vcAttnName = vcAttnName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttnTel() {
		return vcAttnTel;
	}

	public void setVcAttnTel(String vcAttnTel) {
		this.vcAttnTel = vcAttnTel;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAudit() {
		return vcAudit;
	}

	public void setVcAudit(String vcAudit) {
		this.vcAudit = vcAudit;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="vcFormDate"
	 */
	public Date getVcFormDate() {
		return vcFormDate;
	}

	public void setVcFormDate(Date vcFormDate) {
		this.vcFormDate = vcFormDate;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFormFax() {
		return vcFormFax;
	}

	public void setVcFormFax(String vcFormFax) {
		this.vcFormFax = vcFormFax;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFormName() {
		return vcFormName;
	}

	public void setVcFormName(String vcFormName) {
		this.vcFormName = vcFormName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFormTel() {
		return vcFormTel;
	}

	public void setVcFormTel(String vcFormTel) {
		this.vcFormTel = vcFormTel;
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
	 * @hibernate.property type="string"
	 */
	public String getVcQuoteLocal() {
		return vcQuoteLocal;
	}

	public void setVcQuoteLocal(String vcQuoteLocal) {
		this.vcQuoteLocal = vcQuoteLocal;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcQuoteNum() {
		return vcQuoteNum;
	}

	public void setVcQuoteNum(String vcQuoteNum) {
		this.vcQuoteNum = vcQuoteNum;
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
	public String getCurUserName() {
		return curUserName;
	}

	public void setCurUserName(String curUserName) {
		this.curUserName = curUserName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getLowestFreightRmk() {
		return lowestFreightRmk;
	}

	public void setLowestFreightRmk(String lowestFreightRmk) {
		this.lowestFreightRmk = lowestFreightRmk;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getUrgentCostRmk() {
		return urgentCostRmk;
	}

	public void setUrgentCostRmk(String urgentCostRmk) {
		this.urgentCostRmk = urgentCostRmk;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttnPhone() {
		return vcAttnPhone;
	}

	public void setVcAttnPhone(String vcAttnPhone) {
		this.vcAttnPhone = vcAttnPhone;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAttnEmail() {
		return vcAttnEmail;
	}

	public void setVcAttnEmail(String vcAttnEmail) {
		this.vcAttnEmail = vcAttnEmail;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFormPhone() {
		return vcFormPhone;
	}

	public void setVcFormPhone(String vcFormPhone) {
		this.vcFormPhone = vcFormPhone;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(float discountMoney) {
		this.discountMoney = discountMoney;
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
	public String getVcProcessFre() {
		return vcProcessFre;
	}

	public void setVcProcessFre(String vcProcessFre) {
		this.vcProcessFre = vcProcessFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcInstallFre() {
		return vcInstallFre;
	}

	public void setVcInstallFre(String vcInstallFre) {
		this.vcInstallFre = vcInstallFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcAftertreatment() {
		return vcAftertreatment;
	}

	public void setVcAftertreatment(String vcAftertreatment) {
		this.vcAftertreatment = vcAftertreatment;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcTravelFre() {
		return vcTravelFre;
	}

	public void setVcTravelFre(String vcTravelFre) {
		this.vcTravelFre = vcTravelFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcOther() {
		return vcOther;
	}

	public void setVcOther(String vcOther) {
		this.vcOther = vcOther;
	}

	
	/**
	 * @hibernate.property type="float"
	 */
	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
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
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getOffsetQuote() {
		return offsetQuote;
	}

	public void setOffsetQuote(String offsetQuote) {
		this.offsetQuote = offsetQuote;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getFabricTitle() {
		return fabricTitle;
	}

	public void setFabricTitle(String fabricTitle) {
		this.fabricTitle = fabricTitle;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getEngineTotal() {
		return engineTotal;
	}

	public void setEngineTotal(float engineTotal) {
		this.engineTotal = engineTotal;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getFlameTotal() {
		return flameTotal;
	}

	public void setFlameTotal(float flameTotal) {
		this.flameTotal = flameTotal;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getArriveTransport() {
		return arriveTransport;
	}

	public void setArriveTransport(float arriveTransport) {
		this.arriveTransport = arriveTransport;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTitleCol1() {
		return titleCol1;
	}

	public void setTitleCol1(String titleCol1) {
		this.titleCol1 = titleCol1;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInputCol1() {
		return inputCol1;
	}

	public void setInputCol1(float inputCol1) {
		this.inputCol1 = inputCol1;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTitleCol2() {
		return titleCol2;
	}

	public void setTitleCol2(String titleCol2) {
		this.titleCol2 = titleCol2;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInputCol2() {
		return inputCol2;
	}

	public void setInputCol2(float inputCol2) {
		this.inputCol2 = inputCol2;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTitleCol3() {
		return titleCol3;
	}

	public void setTitleCol3(String titleCol3) {
		this.titleCol3 = titleCol3;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInputCol3() {
		return inputCol3;
	}

	public void setInputCol3(float inputCol3) {
		this.inputCol3 = inputCol3;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTitleCol4() {
		return titleCol4;
	}

	public void setTitleCol4(String titleCol4) {
		this.titleCol4 = titleCol4;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getInputCol4() {
		return inputCol4;
	}

	public void setInputCol4(float inputCol4) {
		this.inputCol4 = inputCol4;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTitleCol5() {
		return titleCol5;
	}

	public void setTitleCol5(String titleCol5) {
		this.titleCol5 = titleCol5;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getInputCol5() {
		return inputCol5;
	}

	public void setInputCol5(float inputCol5) {
		this.inputCol5 = inputCol5;
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
	 * @hibernate.property type="string"
	 */
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	/**
	 * @hibernate.set table="bym_quote_salesman" lazy="false"
	 * @hibernate.collection-element column="salesname" type="string" not-null="true"
	 * @hibernate.collection-key column="quoteId"
	 */
	public Set<String> getSalesman() {
		return salesman;
	}

	public void setSalesman(Set<String> salesman) {
		this.salesman = salesman;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean isCanPrint() {
		return canPrint;
	}

	public void setCanPrint(boolean canPrint) {
		this.canPrint = canPrint;
	}
	
	
	
}
