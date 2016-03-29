package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.QuoteFabric;


public interface IQuoteFabricService {

	public Page<QuoteFabric> findForPage(Page<QuoteFabric> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(QuoteFabric entity); 
	
	public QuoteFabric getQuoteFabricById(Long id);
	
	/*public void disableQuoteFabrics(String[] ids);
	
	public void enableQuoteFabrics(String[] ids);
	
	public void disableQuoteFabric(String id);
	
	public void enableQuoteFabric(String id);*/
	
	public List<QuoteFabric> getAll();
	
	public void saveOrUpdateAll(List<QuoteFabric> list);
	
	public List<QuoteFabric> getQuoteFabricByPara(Map<String,Object> paramap);
	
	public  List<QuoteFabric> getQfByQuoteId(Long quoteId);
	public void deleteByProperty(String name,Object value);
	public  List<QuoteFabric> getQfByOrderId(Long orderId);
	public List<QuoteFabric> getQuoteFabricByCodeAndQuoteId(String a,String b,Long c);
	
}
