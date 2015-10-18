package com.wfsc.actions.bym;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Email;
import com.wfsc.services.bym.service.IEmailService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("EmailAction")
@Scope("prototype")
public class EmailAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	@Resource(name = "emailService")
	private IEmailService emailService;
	
	private Email email;

	public String manager(){
	//	this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Email> page = new Page<Email>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = emailService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/email_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		email = emailService.getEmailById(Long.valueOf(id));
		email.setState("2");
		emailService.saveOrUpdateEntity(email);
		request.setAttribute("isView", true);
		return "detail";
	}
	
	public String getUnreadCount(){
		String username = this.getCurrentAdminUser().getUsername();
		int count = emailService.getUnreadCount(username);
		try {
			System.out.println("username===="+username+"-->"+count);
			response.getWriter().write(count+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public String updateStatus(){
		String id = request.getParameter("id");
		String result = "1";
		try {
			Email e = emailService.getEmailById(Long.valueOf(id));
			e.setState("2");
			emailService.saveOrUpdateEntity(e);
			response.getWriter().write(result);
		} catch (IOException e) {
			result = "0";
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		try {
			emailService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String username = this.getCurrentAdminUser().getUsername();
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(username)){
			paramap.put("username", username);
			request.setAttribute("username", username);
		}
		if(StringUtils.isNotEmpty(state)){
			if(!"0".endsWith(state)){
				paramap.put("state", state);
				request.setAttribute("state", state);
			}
		}else{
			paramap.put("state", "1");
			request.setAttribute("state", "1");
		}
		return paramap;
	}
	

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	
}
