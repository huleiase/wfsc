package com.wfsc.util;


import java.math.BigDecimal;

/**
 * 工程报价中面价与报价单中的单位换算
 * 为了统一处理， 税率都到外面去乘了
 * 
 * @author xlj
 * 
 */
public class ProPrice {
	
	static final float rate = 0.914f;
	
	static final float sfrate = 10.764f;
	

	/************************************************************************************************************************
	 * 工程报价:平方米换平方米 面价,折扣,运费,
	 * @param ctax -税率
	 * 
	 * @return
	 */
	public static float getM2ToM2(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return getTwoDecimalFloat((proPrice * vcPurDis + fre + specialExp) * ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:平方米换SF
	 * （A*折扣+运费+特殊费用）/10.764*税率
	 * @param ctax 
	 * @return
	 */
	public static float getM2ToSF(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return getTwoDecimalFloat(((proPrice * vcPurDis + fre + specialExp) / sfrate)*ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:SF换平方米
	 * @param ctax 
	 * 
	 * @return
	 */
	public static float getSFToM2(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return getTwoDecimalFloat((proPrice * vcPurDis + fre + specialExp) * sfrate*ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:SF换SF
	 * @param ctax 
	 * 
	 * @return
	 */
	public static float getSFToSF(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		// (A*折扣+运费+特殊费用）*税率(一般为6%，即乘以1.06，下同）
		return getTwoDecimalFloat((proPrice * vcPurDis + fre + specialExp)*ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:YD换m2
	 * @param ctax 
	 * 
	 * @return
	 */
	public static float getYDToM2(float proPrice, float vcPurDis, float fre, float width, float specialExp, float ctax) {
		return getTwoDecimalFloat((((proPrice / rate * vcPurDis + fre + specialExp) / width) * 100)* ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:YD换M (A/0.914*折扣+运费+特殊费用）*税率
	 * @param ctax 
	 * 
	 * @return
	 */
	public static float getYDToM(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return getTwoDecimalFloat((proPrice / rate * vcPurDis + fre + specialExp)* ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:m换m2
	 * @param ctax 
	 * （A*折扣+运费+特殊费用）/幅宽*100*税率
	 * @return
	 */
	public static float getMToM2(float proPrice, float vcPurDis, float fre, float width, float specialExp, float ctax) {
		return getTwoDecimalFloat((((proPrice * vcPurDis + fre + specialExp) / width) * 100)* ctax);
	}

	/************************************************************************************************************************
	 * 工程报价:M换M
	 * proPrice - 面价A=原单价
	 * @param ctax 
	 * @return
	 */
	public static float getMToM(float proPrice, float vcPurDis, float fre, float specialExp, float ctax,String isFreight) {
		// (A*折扣+运费+特殊费用）*税率
		float vcPrice = getTwoDecimalFloat((proPrice * vcPurDis + fre + specialExp) * ctax);
		if("0".equals(isFreight)){
			vcPrice = getTwoDecimalFloat((proPrice * vcPurDis + specialExp) * ctax);
		}
		return vcPrice;
	}
	
	/**
	 * 计算原单价,即面价A
	 * 面价A=原始进价*采购折扣*汇率*系数
	 */
	public static float getOrigPrice(float vcOldPrice, float vcPurDis, float vcExchangeRate, float rate){
		return getTwoDecimalFloat(vcOldPrice * vcPurDis * vcExchangeRate * rate);
	}
	public static float getMToYD(float proPrice, float vcPurDis, float fre, float specialExp, float ctax){
		return getTwoDecimalFloat((proPrice*rate*vcPurDis+fre+specialExp)*ctax);
	}
	public static float getYDToYD(float proPrice, float vcPurDis, float fre, float specialExp, float ctax,String isFreight) {
		// (A*折扣+运费*0.914+特殊费用）*税率
		float vcPrice = getTwoDecimalFloat((proPrice * vcPurDis + fre*rate + specialExp) * ctax);
		if("0".equals(isFreight)){
			vcPrice = getTwoDecimalFloat((proPrice * vcPurDis + specialExp) * ctax);
		}
		return vcPrice;
	}
	public static float getM2ToM(float proPrice, float vcPurDis, float fre, float width, float specialExp, float ctax) {
		return getTwoDecimalFloat((((proPrice * vcPurDis + fre + specialExp) * width) / 100)* ctax);
	}
	
	/**
	 * 截取两位小数，四舍五入
	 * @param value
	 * @return
	 */
	 public static float getTwoDecimalFloat(double value){
		 BigDecimal b = new BigDecimal(value);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	 }
}
