package com.wfsc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
	public static boolean isNumeric(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9\\.]+");
	    return pattern.matcher(str).matches();   
	 }
	
	public static String getNumericFromStr(String str){
		if(StringUtils.isBlank(str)){
			return "0";
		}
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(str);
		while(m.find()){
			sb.append(m.group());
		}
		return sb.toString();
	 }
	
	public static void main(String[] args) {
		System.out.println(isNumeric("12.7m"));
		System.out.println(isNumeric("12.7cm"));
		System.out.println(isNumeric("12m"));
		System.out.println(isNumeric("12.7"));
		System.out.println(isNumeric("12"));
		
		System.out.println(getNumericFromStr("12.7m"));
		System.out.println(getNumericFromStr("12.7cm"));
		System.out.println(getNumericFromStr("12m"));
		System.out.println(getNumericFromStr("12.7"));
		System.out.println(getNumericFromStr("12"));
		
	}

}
