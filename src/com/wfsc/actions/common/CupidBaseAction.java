package com.wfsc.actions.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;

/**
 * 基类action,一些公共方法定义在这里
 * @author Administrator
 * @version 1.0
 * @param <T>
 * @since cupid 1.0
 */
public class CupidBaseAction<T> extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = -3286145746001535749L;
	protected String sessionID = ""; 
	
	public HttpServletRequest request;
	
	public HttpServletResponse response;
	
	public Map<String, Object> session;
	
	/**
	 * 设置请求来源 
	 */
	public void setRefererUrl(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String header = request.getHeader("referer");
		if(header != null){
			request.getSession().setAttribute(CupidStrutsConstants.SES_REFERER, header);
		}
	}
	/**
	 * 从session里获得当前会员用户 
	 * @return
	 */
	public User getCurrentUser(){
		
		if(session.get(CupidStrutsConstants.SESSION_USER) != null){
			User user = (User) session.get(CupidStrutsConstants.SESSION_USER);
			return user;
		}else{
			return null;
		}
	}
	/**
	 * 从session里获得当前管理员用户 
	 * @return
	 */
	public Admin getCurrentAdminUser(){
		if(session.get(CupidStrutsConstants.SESSION_ADMIN) != null){
			Admin admin = (Admin) session.get(CupidStrutsConstants.SESSION_ADMIN);
			return admin;
		}else{
			return null;
		}
	}
	/**
	 * 更新session里的user对象 
	 * @param user2
	 */
	@SuppressWarnings("unchecked")
	protected void updateSessionUser(User user) {
		this.session.put(CupidStrutsConstants.SESSION_USER, user);
	}
	
	protected void setMsg(String msg, Object[] param){
		String str = "";
		if(param == null){
			request.setAttribute("msg", msg);
			return;
		}
		if(msg.indexOf("{0}") != -1){
			str = msg.replaceAll("{0}", param[0].toString());
		}
		if(msg.indexOf("{1}") != -1){
			str = msg.replaceAll("{1}", param[1].toString());
		}
		if(msg.indexOf("{2}") != -1){
			str = msg.replaceAll("{2}", param[2].toString());
		}
		if(msg.indexOf("{3}") != -1){
			str = msg.replaceAll("{3}", param[3].toString());
		}
		request.setAttribute("msg", str);
	}
	
	/**
	 * 支持分页的方法，设置参数 
	 * @param request
	 * @param page
	 */
	protected void setPageParams(Page<T> page){
		String currPageNo = request.getParameter("currPageNo");
		if(StringUtils.isBlank(currPageNo)||"null".equalsIgnoreCase(currPageNo)){
			page.setCurrPageNo(1);
		}else{
			page.setCurrPageNo(Integer.valueOf(currPageNo));
		}
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isBlank(pageSize)||"null".equalsIgnoreCase(pageSize)){
			page.setPageSize(10);
		}else{
			page.setPageSize(Integer.valueOf(pageSize));
		}
	
	}
	
	/**
	 * 获取带参数的分页对象
	 * @return
	 */
	protected Page<T> getPage(){
		Page<T> page = new Page<T>();
		setPageParams(page);
		return page;
	}
	
	protected String gotoError(String msg) {
		this.setMsg(msg, null);
		return "error";
	}
	
	protected String gotoError(String msg, Object[] param) {
		this.setMsg(msg, param);
		return "error";
	}
	
	/**
	 * 保存sessionId 
	 */
	public void saveSessionId(){
		this.sessionID = ServletActionContext.getRequest().getSession().getId();
	}

	
	public String getSessionID() {
		return sessionID;
	}

	
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		 this.request = request;  
		
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		 this.response = response;  
	}
	
	public void setAttr(String name, Object o) {
		request.setAttribute(name, o);
	}
	
	public String getPara(String name){
		return request.getParameter(name);
	}
	
	public Integer getParaToInt(String name){
		return toInt(getPara(name), null);
	}
	
	public Long getParaToLong(String name){
		return toLong(getPara(name), null);
	}
	
	private Integer toInt(String value, Integer defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		if (value.startsWith("N") || value.startsWith("n"))
			return -Integer.parseInt(value.substring(1));
		return Integer.parseInt(value);
	}
	
	private Long toLong(String value, Long defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		if (value.startsWith("N") || value.startsWith("n"))
			return -Long.parseLong(value.substring(1));
		return Long.parseLong(value);
	}

	/**
	 * 返回JSON
	 * @param json
	 */
	public void renderJson(String json) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

