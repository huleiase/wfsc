package com.base;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.base.log.LogUtil;

/**
 * 服务端的显式对spring bean管理工厂
 * 
 */
public class ServerBeanFactory {
	
	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private static ApplicationContext ctx = null;
	
	private static ServletContext scx = null;

	public static void setAppContext(ApplicationContext appCtx) {
		ctx = appCtx;
	}
	
	public static ApplicationContext getAppContext(){
		return ctx;
	}
	
	public static void setServletContext(ServletContext servletContext){
		scx = servletContext;
	}
		
	public static ServletContext getServletContext(){
		return scx;
	}
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
	
	
	public static void addShutdownHook() {
		try {
			Runtime.getRuntime().addShutdownHook(new ShutdownHookService());
			logger.info("Add JVM shutdown hook.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 
//	 * 获取struts国际化文件中资源值
//	 * @param resourKey 国际化文件key
//	 * @param key 资源key
//	 * @return
//	 */
//	public static String getMSGFromStrustI18N(String resourKey,String key){
//		MessageResources resource =(MessageResources) ServerBeanFactory.getServletContext().getAttribute(resourKey);
//		return resource.getMessage(key);
//	}
//	
//
//	/**
//	 * 
//	 * 获取struts国际化文件中资源值
//	 * @param resourKey 国际化文件key
//	 * @param key 资源key
//	 * @return
//	 */
//	public static String getMSGFromStrustI18N(String resourKey,String key,Object[] params){
//		MessageResources resource =(MessageResources) ServerBeanFactory.getServletContext().getAttribute(resourKey);
//		return resource.getMessage(key,params);
//	}
//	
	
}