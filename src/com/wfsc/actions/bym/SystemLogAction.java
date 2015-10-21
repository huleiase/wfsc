package com.wfsc.actions.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.services.system.ISystemLogService;

@Controller("SystemLogAction")
@Scope("prototype")
public class SystemLogAction extends DispatchPagerAction {

	private static final long serialVersionUID = -1102339874936038061L;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	public String list(){
		//String module = request.getParameter("module");
		String vcLogUser = request.getParameter("vcLogUser");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
	//	request.setAttribute("module", module);
		request.setAttribute("vcLogUser", vcLogUser);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		
		Page<SystemLog> page = new Page<SystemLog>();
		Map<String,Object> params = new HashMap<String,Object>();
		/*if(StringUtils.isNotBlank(module)){
			params.put("module", module);
		}*/
		if(StringUtils.isNotBlank(vcLogUser)){
			params.put("vcLogUser", vcLogUser);
		}
		
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotEmpty(endTime)){
			params.put("startTime", startTime);
			params.put("endTime", endTime);
		}
		this.setPageParams(page);
		page.setPaginationSize(7);
		
		page = systemLogService.getSystemLog4Page(page, params);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/syslog_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("sysloglist", page.getData());
		
		return "list";
	}

}
