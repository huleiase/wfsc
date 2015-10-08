package com.wfsc.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 判断session过期的拦截器
 * 仅仅对除了login,regist之外的方法进行拦截
 * @author Administrator
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 6574746004090789248L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
			return invocation.invoke();
	}
	
	

}
