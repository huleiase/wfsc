package com.wfsc.services.bym.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Order;


public interface IOrderService {

	public Page<Order> findForPage(Page<Order> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Order entity); 
	
	public Order getOrderById(Long id);
	
	/*public void disableOrders(String[] ids);
	
	public void enableOrders(String[] ids);
	
	public void disableOrder(String id);
	
	public void enableOrder(String id);*/
	
	public List<Order> getAll();
	
	public void saveOrUpdateAll(List<Order> list);
	
	public List<Order> getOrderByPara(Map<String,Object> paramap);
	
	public List<Order> getOrderByPurchaseId(Long purchaseId);
	public long getCurrentOrderNum(String area, String tbYearMonth);
	public List<Order> getOrderByOrderNo(String orderNo);
	public void deleteByProperty(String name,Object value);
	public List<Order> getOrderByQuoteId(Long quoteId);
	public void deleteByEntitys(Collection<Order> orders);
}
