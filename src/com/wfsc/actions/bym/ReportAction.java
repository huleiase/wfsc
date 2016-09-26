package com.wfsc.actions.bym;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.wfsc.services.bym.service.IReportService;
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
	
	public String test(){
		this.setTopMenu();
		return "test";
	}
	/*
	  var myChart = echarts.init(document.getElementById('main'));

		$.get('data.json').done(function (data) {
		    myChart.setOption({
		        title: {
		            text: '异步数据加载示例'
		        },
		        tooltip: {},
		        legend: {
		            data:['销量']
		        },
		        xAxis: {
		            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		        },
		        yAxis: {},
		        series: [{
		            name: '销量',
		            type: 'bar',
		            data: [5, 20, 36, 10, 10, 20]
		        }]
		    });
		});
	 */

	public String getData(){
		Map<String,Object> pMap = handleRequestParameter();
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
	
		/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String sDate = request.getParameter("sDate");
		String eDate = request.getParameter("eDate");
		String displayName = request.getParameter("displayName");
		paramap.put("displayName", displayName);
		paramap.put("sDate", sDate);
		paramap.put("eDate", eDate);
		return paramap;
	}
}
