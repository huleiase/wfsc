package com.wfsc.actions.system;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.wfsc.common.bo.system.EmailServerConfig;
import com.wfsc.common.bo.system.SeoConfig;
import com.wfsc.services.system.IEmailService;
import com.wfsc.services.system.ISeoConfigService;

@Controller("SystemAction")
@Scope("prototype")
public class SystemAction extends DispatchPagerAction {
	
	private static final long serialVersionUID = -9178287533525680929L;

	@Autowired
	private ISeoConfigService seoConfigService;
	
	@Autowired
	private IEmailService emailService;
	
	private SeoConfig seoConfig;
	
	private EmailServerConfig emailConfig;
	
	public String preSeo(){
		SeoConfig seoConf = seoConfigService.getSeoConfig();
		request.setAttribute("seoConfig", seoConf);
		return "seoConfig";
	}
	
	public String saveSeo(){
		seoConfigService.saveSeoConfig(seoConfig);
		return "seo";
	}
	
	public String preMail(){
		EmailServerConfig mailConfig = emailService.getEmailServerConfig();
		request.setAttribute("mailConfig", mailConfig);
		return "mailConfig";
	}
	
	public String saveMail(){
		emailConfig.setFromAddress(emailConfig.getUserName());
		emailService.saveEmailServerConfig(emailConfig);
		request.setAttribute("success", 1);
		return "mail";
	}
	
	public void testMail() throws IOException{
		emailConfig.setFromAddress(emailConfig.getUserName());
		boolean result = emailService.testEmailServer(emailConfig);
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}

	public SeoConfig getSeoConfig() {
		return seoConfig;
	}

	public void setSeoConfig(SeoConfig seoConfig) {
		this.seoConfig = seoConfig;
	}

	public EmailServerConfig getEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(EmailServerConfig emailConfig) {
		this.emailConfig = emailConfig;
	}
	
	
}
