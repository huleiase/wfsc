package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerOrder;


public interface IDesignerOrderService {

	public Page<DesignerOrder> findForPage(Page<DesignerOrder> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(DesignerOrder entity); 
	
	public DesignerOrder getDesignerOrderById(Long id);
	
	/*public void disableDesignerOrders(String[] ids);
	
	public void enableDesignerOrders(String[] ids);
	
	public void disableDesignerOrder(String id);
	
	public void enableDesignerOrder(String id);*/
	
	public List<DesignerOrder> getAll();
	
	public void saveOrUpdateAll(List<DesignerOrder> list);
	
	public List<DesignerOrder> getDesignerOrderByPara(Map<String,Object> paramap);
	
	public DesignerOrder getDesignerOrderByOrderId(Long orderId);
	public List<DesignerOrder> getDesignerOrderByOrderNo(String orderNo);
	
}
