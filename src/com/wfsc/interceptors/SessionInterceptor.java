package com.wfsc.interceptors;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.constants.CupidStrutsConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.wfsc.annotation.Login;
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
		ActionContext actionContext = invocation.getInvocationContext(); 
		Map<String, Object> session = actionContext.getSession();
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		if(!StringUtils.equals(invocation.getProxy().getMethod(), "login") || StringUtils.equals(invocation.getProxy().getMethod(), "goLogin")
				|| StringUtils.equals(invocation.getProxy().getMethod(), "logout")){
			HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
			setToGoingURL(request, session, invocation);
		}
		
		boolean auth = false;
		Login login = invocation.getAction().getClass().getAnnotation(Login.class);
		if(login != null){
			auth = true;
		}else{
			Method method = invocation.getAction().getClass().getMethod(invocation.getProxy().getMethod(), null);
			if(method != null){
				login = method.getAnnotation(Login.class);
				if(login != null){
					auth = true;
				}
			}
			
		}
		
		if(auth){
			Object object = session.get(CupidStrutsConstants.SESSION_USER);
			if(object != null){
				return invocation.invoke();
			}else{
				response.sendRedirect("/wfsc/public/GoLogin.Q");
				return null;
			}
		}else{
			return invocation.invoke();
		}
	}
	
	private void setToGoingURL(HttpServletRequest request, Map<String, Object> session, ActionInvocation invocation) {
		if(StringUtils.equalsIgnoreCase("post", request.getMethod())){
			return;
		}
		String url = request.getHeader("referer");
		if (StringUtils.isEmpty(url)) {
			url = request.getRequestURI();
			Map<String, String[]> params = request.getParameterMap();
			if (params != null) {
				for (String key : params.keySet()) {
					String[] value = params.get(key);
					for (String val : value) {
						url = url + key + "=" + val + "&";
					}

				}
			}
		}
		if(url.endsWith("&")){
			url = url.substring(0, url.length() -1);
		}
		session.put(CupidStrutsConstants.GOTO_URL_KEY, url);
	}

}
