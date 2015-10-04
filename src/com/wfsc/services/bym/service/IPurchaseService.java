package com.wfsc.services.bym.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.Purchase;


public interface IPurchaseService {

	public Page<Purchase> findForPage(Page<Purchase> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Purchase entity); 
	
	public Purchase getPurchaseById(Long id);
	
	public List<Purchase> getAll();
	
	public void saveOrUpdateAll(List<Purchase> list);
	
	public List<Purchase> getPurchaseByPara(Map<String,Object> paramap);
	
	public List<Purchase> getPurchaseByQuoteId(Long quoteId);
	
	public Purchase getUniqPurchaseByQuoteId(Long quoteId);
	
	public void deleteByProperty(String name,Object value);
	public void deleteByEntitys(Collection<Purchase> purs);
	
}
