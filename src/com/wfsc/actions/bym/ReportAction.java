package com.wfsc.actions.bym;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IReportService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.PriceUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("ReportAction")
@Scope("prototype")
public class ReportAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	@Resource(name = "reportService")
	private IReportService reportService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	
	public String test(){
		this.setTopMenu();
		return "test";
	}
	public String getData(){
		Map<String,String> pMap = handleRequestParameter();
		List<Map<String,Object>> datas = reportService.getReportDatas(pMap);
		JSONArray jax = new JSONArray();
		JSONArray jas = new JSONArray();
		for(Map<String,Object> map : datas){
			jax.add(map.get("ym").toString());
			jas.add(map.get("sr").toString());
		}
		JSONObject j = new JSONObject();
		j.put("text", pMap.get("displayName")+" 型号销量");
		j.put("name", "销售RMB");
		j.put("jax", jax);
		j.put("jas", jas);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(j.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDetailSellData(){
		Map<String,String> pMap = handleRequestParameter();
		String displayName = pMap.get("displayName");
		String yearMonth = pMap.get("yearMonth");
		String quoteLocal = pMap.get("quoteLocal");
		List<Map<String,Object>> datas = reportService.getQFRByAreaAndMounth(yearMonth, quoteLocal, 1,displayName);
		StringBuffer sb =new StringBuffer("<table border=1><tr><th>型号</th><th>数量</th><th>单价</th><th>金额</th></tr>");
		sb.append("<tr><td>").append(displayName).append("</td>");
		Map<String,Object> map = datas.get(0);
		sb.append("<td>").append(map.get("vq")).append("</td>");
		sb.append("<td>").append(map.get("bjPrice")).append(map.get("vcMoney")).append("</td>");
		sb.append("<td>").append(map.get("vcOldPriceTotal")).append("</td>").append("</tr></table>");
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String managerMostSell(){
		this.setTopMenu();
		Admin admin = this.getCurrentAdminUser();
		boolean isAdminb = securityService.isAbleRole(admin.getUsername(), "管理员");
		request.setAttribute("isAdminb", isAdminb);
		if(!isAdminb){
			request.setAttribute("quoteLocal", admin.getArea());
		}
		return "mostSell";
	}
	public String getMostSellData(){
		
		Map<String,String> pMap = handleRequestParameter();
		String year = pMap.get("reportYear");
		int limit = Integer.valueOf(pMap.get("limit"));
		String quoteLocal = pMap.get("quoteLocal");
		List<String> yearMounth = new ArrayList<String>(12);
		for(int i=1;i<13;i++){
			if(i>9){
				yearMounth.add(year+"-"+i);
			}else{
				yearMounth.add(year+"-0"+i);
			}
		}
		JSONArray jsa = new JSONArray();
		for(String mouth : yearMounth){
			List<Map<String,Object>> datas = reportService.getQFRByAreaAndMounth(mouth, quoteLocal, limit,null);
			if(datas.size()<limit){
				List<Map<String,Object>> zeroDatas = new ArrayList<Map<String,Object>>(); 
				for(int j=datas.size();j<limit;j++){
					Map<String,Object> zeroMap = new HashMap<String,Object>();
					zeroMap.put("vm", "no data "+(j+1));
					zeroMap.put("vq", 0);
					zeroDatas.add(zeroMap);
				}
				datas.addAll(zeroDatas);
			}
			JSONObject jsonMounthData = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONArray jsonVm = new JSONArray();
			for(Map<String,Object> map :datas ){
				JSONObject json = new JSONObject();
				JSONArray dataJsa = new JSONArray();
				dataJsa.add(map.get("vq"));
				jsonVm.add(map.get("vm"));
				json.put("name", map.get("vm"));
				json.put("type", "bar");
				json.put("data", dataJsa);
				jsonArray.add(json);
			}
			JSONObject jsonTitle = new JSONObject();
			jsonTitle.put("text", quoteLocal+"地区"+mouth+"月"+"最畅销的"+limit+"个型号");
			JSONObject jsonLegend = new JSONObject();
			jsonLegend.put("left", "30%");
			jsonLegend.accumulate("data", jsonVm);
			jsonMounthData.put("title", jsonTitle);
			jsonMounthData.put("legend", jsonLegend);
			jsonMounthData.put("series", jsonArray);
			jsa.add(jsonMounthData);
		}
		
		JSONObject j = new JSONObject();
		j.put("jsonMounth", JSONArray.fromObject(yearMounth));
		j.put("jsa", jsa);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(j.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
		/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,String> handleRequestParameter(){
		Map<String,String> paramap = new HashMap<String,String>();
		String sDate = request.getParameter("sDate");
		String eDate = request.getParameter("eDate");
		String displayName = request.getParameter("displayName");
		String reportYear = request.getParameter("reportYear");
		String quoteLocal = request.getParameter("quoteLocal");
		String limit = request.getParameter("limit");
		String yearMonth = request.getParameter("yearMonth");
		paramap.put("displayName", displayName);
		paramap.put("sDate", sDate);
		paramap.put("eDate", eDate);
		paramap.put("reportYear", reportYear);
		paramap.put("quoteLocal", quoteLocal);
		paramap.put("limit", limit);
		paramap.put("yearMonth", yearMonth);
		return paramap;
	}
}
