package com.wfsc.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


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
		/*String str = "被HTCM-P15 LS-DRA28替代";
		String newStr = StringUtils.substring(str, 1, -2);
		System.out.println(newStr);
		System.out.println(0F/0F);
		System.out.println("me 10 sd".equals("me 10 sd"));*/
		//test();
		Logger comAseLogger = Logger.getLogger("com.ase");
		System.out.println("comAseLogger.getName()=="+comAseLogger.getName());//com.ase
		System.out.println("comAseLogger.getParent()=="+comAseLogger.getParent().getClass().getName());//java.util.logging.LogManager$RootLogger
		System.out.println("RootLogger.getName()=="+comAseLogger.getParent().getName());//""
		System.out.println("RootLogger.getName()=="+comAseLogger.getParent().getHandlers()[0]);//java.util.logging.ConsoleHandler
		//ConsoleHandler cHandler = new ConsoleHandler();
		System.out.println("comAseLogger.getClass().getSuperclass()=="+comAseLogger.getClass().getSuperclass());//java.lang.Object
		System.out.println(comAseLogger.getHandlers().length);//0
	}
	
	public static long test(){
		long a = 0L;
		a = (long)Math.scalb(1D, 20);
		System.out.println(a);
		return a;
	}

}
