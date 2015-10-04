package com.wfsc.common.bo.bym;


import java.util.Date;

/**
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bym_fabric" lazy="false"
 */
public class Fabric implements java.io.Serializable{

	private static final long serialVersionUID = 684099868971L;
	
	private Long id;
	/**
	 * 工厂编号
	 */
	private String vcFactoryCode;
	/**
	 * 类型
	 */
	private String vcType;
	/**
	 * 原厂型号
	 */
	private String vcBefModel;
	/**
	 * 副宽
	 */
	private float vcWidth;
	/**
	 * 卷长
	 */
	private String vcPieceLength;
	/**
	 * 起订量
	 */
	private String vcMinNum;
	/**
	 * 最小分格
	 */
	private String vcMinLattice;
	/**
	 * 成份
	 */
	private String vcComposition;
	/**
	 * 密度
	 */
	private String vcDensity;
	/**
	 * 回位
	 */
	private String vcRepeat;
	/**
	 * 定高宽
	 */
	private String vcFixWidthHight;
	/**
	 * 收缩
	 */
	private String vcShrink;
	/**
	 * 耐磨度
	 */
	private String vcDr;
	/**
	 * 阻燃
	 */
	private String vcFr;
	/**
	 * 环保
	 */
	private String vcEco;
	/**
	 * 防污
	 */
	private String vcAntifouling;
	/**
	 * 色牢度
	 */
	private String vcLightFas;
	/**
	 * 洗涤
	 */
	private String vcClean;
	/**
	 * 用途
	 */
	private String vcApp;
	/**
	 * 进价价格
	 */
	private float vcOldPrice;
	/**
	 * 进价单位
	 */
	private String vcMeasure;
	/**
	 * 进价货币
	 */
	private String vcPriceCur;
	/**
	 * 工程系数
	 */
	private float vcProRatio;
	/**
	 * 分销系数
	 */
	private float vcRetailRatio;
	/**
	 * 工程运费
	 */
	private float vcProFre;
	/**
	 * 零售运费
	 */
	private float vcRetFre;
	/**
	 * 采购折扣
	 */
	private float vcPurDis;
	/**
	 * 是否停用
	 */
	private String vcDis;
	/**
	 * 备注1
	 */
	private String vcRemark1;
	/**
	 * 备注2
	 */
	private String vcRemark2;
	/**
	 * 备注3
	 */
	private String vcRemark3;
	/**
	 * 操作者
	 */
	private String curUserName;
	
//------------------------------------
//	面价=原始进价*采购折扣*汇率*系数
//	工程面价和零售面价 就是系数不同
//------------------------------------
	/**
	 * 工程面价
	 */
//	private Integer vcProPrice;
	/**
	 * 零售面价
	 */
//	private Integer vcRetailPrice;
	/**
	 * 日期
	 */
	private Date createDate;
	/**
	 * 色号
	 */
	private String colorCode;
	/**
	 * HT型号
	 */
	private String htCode;
	/**
	 * 是否有HT型号
	 */
	private String isHtCode;
	/**
	 * 1:进口厂，0：国产厂
	 */
	private String importFactory;
	//书号
	private String bookNo;
	//报价时报价员填写的备注，以便让其他报价员或者下次报价时看到
	private String quoteRemark;
	//工厂名称
	private String factoryName;
	//ht型号对应的原厂型号的ID
	private Long refid;
	private float dhjCost;//大货价成本
	private float dhjInlandRate;//大货价内地系数
	private float dhjInlandTransCost;//大货价内地运费
	private float dhjHKRate;//大货价香港系数
	private float dhjHKTransCost;//大货价香港运费
	private float dhjWidth;//大货价幅宽
	
	private String brand;//品牌
	private float inlandRefitRate;//大陆加工系数
	private float hkRefitRate;//香港加工系数
	private float inlandRaiseRate;//大陆上调系数
	private float hkRaiseRate;//香港上调系数
	private float inlandDownRate;//大陆下调系数
	private float hkDownRate;//香港下调系数
	
	
	//内地价格(面价)不存储，在导出ht时用到
	private String innerPrice;
	//香港价格(面价)不存储，在导出ht时用到
	private String hkPrice;
	//ht型号内地二次报价，不存储，在导出ht时用到
	private String innerSecPrice;
	//ht型号香港二次报价，不存储，在导出ht时用到
	private String hkSecPrice;
	//人民币转港币的汇率，在计算香港报价的时候暂时用到，不要存入数据库
	private float rmb2hkd = 1.0F;
	//private float hkFrirtPrice;//香港一次报价
	//private float inlandFrirtPrice;//大陆一次报价
	//private float hkSecondPrice;//香港二次报价
	//private float inlandSecondPrice;//大陆二次报价
	
	
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
	public String getQuoteRemark() {
		return quoteRemark;
	}
	public void setQuoteRemark(String quoteRemark) {
		this.quoteRemark = quoteRemark;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getHtCode() {
		return htCode;
	}
	public void setHtCode(String htCode) {
		this.htCode = htCode;
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
	public String getVcAntifouling() {
		return vcAntifouling;
	}
	public void setVcAntifouling(String vcAntifouling) {
		this.vcAntifouling = vcAntifouling;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcApp() {
		return vcApp;
	}
	public void setVcApp(String vcApp) {
		this.vcApp = vcApp;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcClean() {
		return vcClean;
	}
	public void setVcClean(String vcClean) {
		this.vcClean = vcClean;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcComposition() {
		return vcComposition;
	}
	public void setVcComposition(String vcComposition) {
		this.vcComposition = vcComposition;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcDensity() {
		return vcDensity;
	}
	public void setVcDensity(String vcDensity) {
		this.vcDensity = vcDensity;
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
	public String getVcDr() {
		return vcDr;
	}
	public void setVcDr(String vcDr) {
		this.vcDr = vcDr;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcEco() {
		return vcEco;
	}
	public void setVcEco(String vcEco) {
		this.vcEco = vcEco;
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
	public String getVcFixWidthHight() {
		return vcFixWidthHight;
	}
	public void setVcFixWidthHight(String vcFixWidthHight) {
		this.vcFixWidthHight = vcFixWidthHight;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcFr() {
		return vcFr;
	}
	public void setVcFr(String vcFr) {
		this.vcFr = vcFr;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcLightFas() {
		return vcLightFas;
	}
	public void setVcLightFas(String vcLightFas) {
		this.vcLightFas = vcLightFas;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcMeasure() {
		return vcMeasure;
	}
	public void setVcMeasure(String vcMeasure) {
		this.vcMeasure = vcMeasure;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcMinLattice() {
		return vcMinLattice;
	}
	public void setVcMinLattice(String vcMinLattice) {
		this.vcMinLattice = vcMinLattice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcMinNum() {
		return vcMinNum;
	}
	public void setVcMinNum(String vcMinNum) {
		this.vcMinNum = vcMinNum;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcBefModel() {
		return vcBefModel;
	}
	public void setVcBefModel(String vcBefModel) {
		this.vcBefModel = vcBefModel;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcOldPrice() {
		return vcOldPrice;
	}
	public void setVcOldPrice(float vcOldPrice) {
		this.vcOldPrice = vcOldPrice;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcPieceLength() {
		return vcPieceLength;
	}
	public void setVcPieceLength(String vcPieceLength) {
		this.vcPieceLength = vcPieceLength;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcPriceCur() {
		return vcPriceCur;
	}
	public void setVcPriceCur(String vcPriceCur) {
		this.vcPriceCur = vcPriceCur;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcProFre() {
		return vcProFre;
	}
	public void setVcProFre(float vcProFre) {
		this.vcProFre = vcProFre;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcProRatio() {
		return vcProRatio;
	}
	public void setVcProRatio(float vcProRatio) {
		this.vcProRatio = vcProRatio;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcPurDis() {
		return vcPurDis;
	}
	public void setVcPurDis(float vcPurDis) {
		this.vcPurDis = vcPurDis;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRemark1() {
		return vcRemark1;
	}
	public void setVcRemark1(String vcRemark1) {
		this.vcRemark1 = vcRemark1;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRemark2() {
		return vcRemark2;
	}
	public void setVcRemark2(String vcRemark2) {
		this.vcRemark2 = vcRemark2;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRemark3() {
		return vcRemark3;
	}
	public void setVcRemark3(String vcRemark3) {
		this.vcRemark3 = vcRemark3;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcRepeat() {
		return vcRepeat;
	}
	public void setVcRepeat(String vcRepeat) {
		this.vcRepeat = vcRepeat;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcRetailRatio() {
		return vcRetailRatio;
	}
	public void setVcRetailRatio(float vcRetailRatio) {
		this.vcRetailRatio = vcRetailRatio;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcRetFre() {
		return vcRetFre;
	}
	public void setVcRetFre(float vcRetFre) {
		this.vcRetFre = vcRetFre;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcShrink() {
		return vcShrink;
	}
	public void setVcShrink(String vcShrink) {
		this.vcShrink = vcShrink;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getVcType() {
		return vcType;
	}
	public void setVcType(String vcType) {
		this.vcType = vcType;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getVcWidth() {
		return vcWidth;
	}
	public void setVcWidth(float vcWidth) {
		this.vcWidth = vcWidth;
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
	public String getIsHtCode() {
		return isHtCode;
	}
	public void setIsHtCode(String isHtCode) {
		this.isHtCode = isHtCode;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getImportFactory() {
		return importFactory;
	}
	public void setImportFactory(String importFactory) {
		this.importFactory = importFactory;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	/**
	 * @hibernate.property type="long"
	 */
	public Long getRefid() {
		return refid;
	}
	public void setRefid(Long refid) {
		this.refid = refid;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjCost() {
		return dhjCost;
	}
	public void setDhjCost(float dhjCost) {
		this.dhjCost = dhjCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjInlandRate() {
		return dhjInlandRate;
	}
	public void setDhjInlandRate(float dhjInlandRate) {
		this.dhjInlandRate = dhjInlandRate;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjInlandTransCost() {
		return dhjInlandTransCost;
	}
	public void setDhjInlandTransCost(float dhjInlandTransCost) {
		this.dhjInlandTransCost = dhjInlandTransCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjHKRate() {
		return dhjHKRate;
	}
	public void setDhjHKRate(float dhjHKRate) {
		this.dhjHKRate = dhjHKRate;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjHKTransCost() {
		return dhjHKTransCost;
	}
	public void setDhjHKTransCost(float dhjHKTransCost) {
		this.dhjHKTransCost = dhjHKTransCost;
	}
	/**
	 * @hibernate.property type="float"
	 */
	public float getDhjWidth() {
		return dhjWidth;
	}
	public void setDhjWidth(float dhjWidth) {
		this.dhjWidth = dhjWidth;
	}
	
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInlandRefitRate() {
		return inlandRefitRate;
	}
	public void setInlandRefitRate(float inlandRefitRate) {
		this.inlandRefitRate = inlandRefitRate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHkRefitRate() {
		return hkRefitRate;
	}
	public void setHkRefitRate(float hkRefitRate) {
		this.hkRefitRate = hkRefitRate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInlandRaiseRate() {
		return inlandRaiseRate;
	}
	public void setInlandRaiseRate(float inlandRaiseRate) {
		this.inlandRaiseRate = inlandRaiseRate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHkRaiseRate() {
		return hkRaiseRate;
	}
	public void setHkRaiseRate(float hkRaiseRate) {
		this.hkRaiseRate = hkRaiseRate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getInlandDownRate() {
		return inlandDownRate;
	}
	public void setInlandDownRate(float inlandDownRate) {
		this.inlandDownRate = inlandDownRate;
	}

	/**
	 * @hibernate.property type="float"
	 */
	public float getHkDownRate() {
		return hkDownRate;
	}
	public void setHkDownRate(float hkDownRate) {
		this.hkDownRate = hkDownRate;
	}
	
	
	public String getInnerPrice() {
		return innerPrice;
	}
	public void setInnerPrice(String innerPrice) {
		this.innerPrice = innerPrice;
	}
	public String getHkPrice() {
		return hkPrice;
	}
	public void setHkPrice(String hkPrice) {
		this.hkPrice = hkPrice;
	}
	public String getInnerSecPrice() {
		return innerSecPrice;
	}
	public void setInnerSecPrice(String innerSecPrice) {
		this.innerSecPrice = innerSecPrice;
	}
	public String getHkSecPrice() {
		return hkSecPrice;
	}
	public void setHkSecPrice(String hkSecPrice) {
		this.hkSecPrice = hkSecPrice;
	}
	public float getRmb2hkd() {
		return rmb2hkd;
	}
	public void setRmb2hkd(float rmb2hkd) {
		this.rmb2hkd = rmb2hkd;
	}
}
