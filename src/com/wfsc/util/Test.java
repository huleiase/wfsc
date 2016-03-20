package com.wfsc.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*Map<String,String> map = new HashMap<String,String>();
		
		map.put("chedan", "teng");
		map.put("hehe", "gun");
		map.put("hehe", "nima");
		System.out.println(map);*/
		String str = "被HTCM-P15 LS-DRA28替代";
		String newStr = StringUtils.substring(str, 1, -2);
		System.out.println(newStr);
		System.out.println(0F/0F);
		System.out.println("me 10 sd".equals("me 10 sd"));
		//test();

	}
	
	public static long test(){
		long a = 0L;
		a = (long)Math.scalb(1D, 20);
		System.out.println(a);
		return a;
	}

}
