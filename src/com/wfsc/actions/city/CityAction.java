package com.wfsc.actions.city;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.util.Page;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.city.ICityService;
import com.wfsc.services.system.ISystemLogService;

@Controller("CityAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class CityAction extends CupidBaseAction{

	private static final long serialVersionUID = -8226696913379030518L;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	public String index(){
		list();
		return "index";
	}
	
	public String list(){
		Page<City> page = super.getPage();
		page.setPaginationSize(7);
		
		String name = request.getParameter("name");
		String supportStr = request.getParameter("support");
		
		Boolean support = null;
		if(StringUtils.isNotEmpty(supportStr)){
			support = Boolean.valueOf(supportStr);
		}
		request.setAttribute("name", name);
		request.setAttribute("support", support);
		
		page = cityService.queryAllCityForPage(page, name, support);
		
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/city_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public void setSupport(){
		String idStr = request.getParameter("id");
		String supportStr = request.getParameter("support");
		String result = "success";
		try{
			cityService.changeCitySupportStatus(Long.parseLong(idStr), Boolean.parseBoolean(supportStr));
			
		} catch (Exception ex){
			result = "failed";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}
	
	public void setHot(){
		String idStr = request.getParameter("id");
		String hotStr = request.getParameter("hot");
		String result = "success";
		try{
			cityService.changeCityHotStatus(Long.parseLong(idStr), Boolean.parseBoolean(hotStr));
			
		} catch (Exception ex){
			result = "failed";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

}
