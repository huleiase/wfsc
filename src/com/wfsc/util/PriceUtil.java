package com.wfsc.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class PriceUtil {
	static final float YD_Coefficient = 0.914f;
	
	static final float SF_Coefficient = 10.764f;
	
	/**
	 * 截取两位小数，四舍五入
	 * @param value
	 * @return
	 */
	 public static float getTwoDecimalFloat(double value){
		 BigDecimal b = new BigDecimal(value);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	 }
	 
	 /**
		 * 截取三位小数，四舍五入
		 * @param value
		 * @return
		 */
		 public static float getThreeDecimalFloat(double value){
			 BigDecimal b = new BigDecimal(value);
			return b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		 }
	
	 /**
	  * 面价= 原始进价*采购折扣*汇率*工程系数/分销系数
	  * @param purchasePrice 原始进价
	  * @param purchasePurDis 采购折扣
	  * @param exchangeRate 汇率
	  * @param coefficient 工程系数/分销系数
	  * @return
	  */
	public static float getCommonFacePrice(float purchasePrice, float purchasePurDis, float exchangeRate, float coefficient){
		return getTwoDecimalFloat(purchasePrice * purchasePurDis * exchangeRate * coefficient);
	}
	
	public static float getHKDomesticFacePrice(float purchasePrice, float purchasePurDis, float other2rmb, float vcExchangeRate){
		float origPrice = 0F;
		float priceRMB = purchasePrice * other2rmb; // 把原始进价先全转为RMB货币，为了下面比较价格
		if (priceRMB > 0 && priceRMB < 60) {
			// 原始进价在60rmb以下的,原始进价在60rmb以下的,原单价=（原始进价*【（350-300）/60】+300）*采购折扣
			origPrice = ProPrice.getTwoDecimalFloat((purchasePrice* (50.0 / 60.0) + 300) * purchasePurDis);
		} else if (priceRMB > 60 && priceRMB < 80) {
			// 原单价=（（原始进价-60）*【（450-350）/（80-60）】+350）*采购折扣
			origPrice = ProPrice.getTwoDecimalFloat(((purchasePrice - 60) * 5 + 350) * purchasePurDis);
		} else if (priceRMB > 80 && priceRMB < 200) {
			// 原单价=（（原始进价-80）*【（600-450）/（200-80）】+450）*采购折扣
			origPrice = ProPrice.getTwoDecimalFloat(((purchasePrice - 80)* (150.0 / 120.0) + 450) * purchasePurDis);
		} else if (priceRMB > 200) {// 原单价=原始进价*采购折扣*汇率*2.5
			origPrice = ProPrice.getTwoDecimalFloat(purchasePrice* vcExchangeRate * purchasePurDis * 2.5F);
		} else if (priceRMB == 0) {
			origPrice = 0.0F;
		} else if (priceRMB == 60) {
			origPrice = 350.0F;
		} else if (priceRMB == 80) {
			origPrice = 450.0F;
		} else if (priceRMB == 200) {
			origPrice = 600.0F;
		}
		return origPrice;
	}
	
	/**
	  * 零售面价= 原始进价*采购折扣*汇率*工程系数*零售系数
	  * @param purchasePrice 原始进价
	  * @param purchasePurDis 采购折扣
	  * @param exchangeRate 汇率
	  * @param projectCoefficient 工程系数
	  * @param retailCoefficient 零售系数
	  * @return
	  */
	public static float getRetailFacePrice(float purchasePrice, float purchasePurDis, float exchangeRate, float projectCoefficient,float retailCoefficient){
		return getTwoDecimalFloat(purchasePrice * purchasePurDis * exchangeRate * projectCoefficient * retailCoefficient);
	}
	
	/**
	 * 最终价格(内地) = （面价 * 折扣 + 工程运费 + 特殊费用） * 税率
	 * @param facePrice
	 * @param sellPurDis
	 * @param projectFreight
	 * @param specialExp
	 * @param taxRate
	 * @param isFreight 0:不含运费，1：含运费
	 * @return
	 */
	public static float getCommonProjectFinalPrice(float facePrice, float sellPurDis, float projectFreight, float specialExp, float taxRate,String isFreight){
		float finalPrice = (facePrice*sellPurDis+projectFreight+specialExp)*taxRate;
		if("0".equals(isFreight)){
			finalPrice = (facePrice*sellPurDis+specialExp)*taxRate;
		}
		return finalPrice;
	}
	/**
	 * 最终价格(内地YD) = （面价 * 折扣 + 工程运费*0.914 + 特殊费用） * 税率
	 * @param facePrice
	 * @param sellPurDis
	 * @param projectFreight
	 * @param specialExp
	 * @param taxRate
	 * @param isFreight
	 * @return
	 */
	public static float getYDProjectFinalPrice(float facePrice, float sellPurDis, float projectFreight, float specialExp, float taxRate,String isFreight){
		float finalPrice = (facePrice*sellPurDis+projectFreight*YD_Coefficient+specialExp)*taxRate;
		if("0".equals(isFreight)){
			finalPrice = (facePrice*sellPurDis+specialExp)*taxRate;
		}
		return finalPrice;
	}
	/**
	 * 最终价格(香港) = （面价 * 折扣 + 分销运费*rmb转hkd的汇率 + 特殊费用） * 税率
	 * @param facePrice
	 * @param sellPurDis
	 * @param distributionFreight
	 * @param specialExp
	 * @param taxRate
	 * @param rmb2hkd
	 * @param isFreight
	 * @return
	 */
	public static float getCommonDistributionFinalPrice(float facePrice, float sellPurDis, float distributionFreight, float specialExp, float taxRate,float rmb2hkd,String isFreight){
		float finalPrice = (facePrice*sellPurDis+distributionFreight*rmb2hkd+specialExp)*taxRate;
		if("0".equals(isFreight)){
			finalPrice = (facePrice*sellPurDis+specialExp)*taxRate;
		}
		return finalPrice;
	}
	/**
	 * 最终价格(香港YD) = （面价 * 折扣 + 分销运费*rmb转hkd的汇率*0.914 + 特殊费用） * 税率
	 * @param facePrice
	 * @param sellPurDis
	 * @param distributionFreight
	 * @param specialExp
	 * @param taxRate
	 * @param rmb2hkd
	 * @param isFreight
	 * @return
	 */
	public static float getYDDistributionFinalPrice(float facePrice, float sellPurDis, float distributionFreight, float specialExp, float taxRate,float rmb2hkd,String isFreight){
		float finalPrice = (facePrice*sellPurDis+distributionFreight*YD_Coefficient*rmb2hkd+specialExp)*taxRate;
		if("0".equals(isFreight)){
			finalPrice = (facePrice*sellPurDis+specialExp)*taxRate;
		}
		return finalPrice;
	}
	/**
	 * HT型号内地二次报价
	 * @param firstFacePrice
	 * @param brand
	 * @param raiseRate
	 * @param downRate
	 * @param refitRate
	 * @return
	 */
	public static float getSecondInlandFacePrice(float firstFacePrice,String brand,float raiseRate,float downRate,float refitRate){
		if("waa".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice < 0) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 300) {
				return firstFacePrice * downRate * refitRate;
			} else {
				return firstFacePrice * refitRate;
			}
		} else if("ht".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice <= 300) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 600) {
				return firstFacePrice * downRate * refitRate;
			} else {
				return firstFacePrice * refitRate;
			}
		} else if("is".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice <= 600) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 600) {
				return firstFacePrice * refitRate;
			}
		}
		return firstFacePrice;
	}
	/**
	 * HT型号香港二次报价
	 * @param firstFacePrice
	 * @param brand
	 * @param raiseRate
	 * @param downRate
	 * @param refitRate
	 * @return
	 */
	public static float getSecondHKFacePrice(float firstFacePrice,String brand,float raiseRate,float downRate,float refitRate){
		if("waa".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice < 300) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 450) {
				return firstFacePrice * downRate * refitRate;
			} else {
				return firstFacePrice * refitRate;
			}
		} else if("ht".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice <= 450) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 800) {
				return firstFacePrice * downRate * refitRate;
			} else {
				return firstFacePrice * refitRate;
			}
		} else if("is".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice <= 800) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 800) {
				return firstFacePrice * refitRate;
			}
		} else if("改码-国产".equalsIgnoreCase(StringUtils.trim(brand))) {
			if(firstFacePrice < 300) {
				return firstFacePrice * raiseRate * refitRate;
			} else if(firstFacePrice > 450) {
				return firstFacePrice * downRate * refitRate;
			} else {
				return firstFacePrice * refitRate;
			}
		}
		return firstFacePrice;
	}
}
