package com.wfsc.actions.orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.base.util.Page;
import com.wfsc.common.bo.comment.Comments;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 
 * @author hl
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("OrdersAction")
@Scope("prototype")
public class OrdersAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "ordersService")
	private IOrdersService ordersService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private Orders orders;
	
	private OrdersDetail od;

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public OrdersDetail getOd() {
		return od;
	}

	public void setOd(OrdersDetail od) {
		this.od = od;
	}
	public String manager(){
		list();
		return "manager";
	}
	
	public String list(){
		Page<Orders> page = new Page<Orders>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String orderNo = request.getParameter("orderNo");
		String status = request.getParameter("status");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotEmpty(orderNo)){
			paramap.put("orderNo", orderNo);
			request.setAttribute("orderNo", orderNo);
		}
		
		if(StringUtils.isNotEmpty(status)){
			paramap.put("status", status);
			request.setAttribute("status", Integer.valueOf(status));
		}
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime+" 00:00:01");
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime+" 23:59:59");
			request.setAttribute("endTime", endTime);
		}
		
		page = ordersService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/orders_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("orderslist", page.getData());
		return "list";
	}
	public String changeOrderState(){
		String orderState = request.getParameter("orderState");
		String ost = "";
		if("2".equals(orderState)){
			ost = "已发货";
		}else if("3".equals(orderState)){
			ost = "已完成";
		}
		String orderNo = request.getParameter("orderNo");
		Orders orders = ordersService.getOrderByCode(orderNo);
		orders.setStatus(Integer.valueOf(orderState));
		ordersService.saveOrUpdateOrder(orders);
		return "ok";
	}
	public String ordersDetai(){
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String orderNo = request.getParameter("orderNo");
		orders = ordersService.getOrderByCode(orderNo);
		List<OrdersDetail> ods = new ArrayList<OrdersDetail>();
		if(orders!=null&&orders.getOrdersDetail()!=null){
			ods = orders.getOrdersDetail();
		}
		request.setAttribute("ods", ods);
		return "detail";
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		ordersService.deleteByIds(idList);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
