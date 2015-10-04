package com.wfsc.util;


/**
 * 零售中面价与报价单中的单位换算
 * 
 * @author xlj
 * 
 */
public class RetailPrice {
	
	static final float rate = 0.914f;
	static final float rate2 = 10.764f;

	/************************************************************************************************************************
	 * 零售报价:YD换M
	 * @param ctax 
	 * 
	 * @return
	 */
	/*public static float getYDToM(float proPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		float f = (float) (((proPrice + fre + specialExp) / rate) * vcPurDis);
		return Math.round(f * ctax);
	}*/

	/************************************************************************************************************************
	 * 零售报价:M换M
	 * 
	 * @param ctax
	 * @return
	 */
	public static float getMToM(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*ctax);
	}
	
	public static float getYDToYD(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*ctax);
	}
	public static float getM2ToM2(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*ctax);
	}
	public static float getSFToSF(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*ctax);
	}
	
	public static float getM2ToSF(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)/rate2*ctax);
	}
	public static float getSFToM2(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*rate2*ctax);
	}
	public static float getYDToM2(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax,float width) {
		return ProPrice.getTwoDecimalFloat((oldPrice/rate*vcPurDis+fre+specialExp)/width*100*ctax);
	}
	
	public static float getMToM2(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax,float width) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)/width*100*ctax);
	}
	
	public static float getYDToM(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice/rate*vcPurDis+fre+specialExp)*ctax);
	}
	public static float getMToYD(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax) {
		return ProPrice.getTwoDecimalFloat((oldPrice*rate*vcPurDis+fre+specialExp)*ctax);
	}
	public static float getM2ToM(float oldPrice, float vcPurDis, float fre, float specialExp, float ctax,float width) {
		return ProPrice.getTwoDecimalFloat((oldPrice*vcPurDis+fre+specialExp)*width/100*ctax);
	}
	
}
