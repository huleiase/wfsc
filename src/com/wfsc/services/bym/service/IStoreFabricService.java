package com.wfsc.services.bym.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.StoreFabric;


public interface IStoreFabricService {

	public Page<StoreFabric> findForPage(Page<StoreFabric> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(StoreFabric entity); 
	
	public StoreFabric getStoreFabricById(Long id);
	
	public List<StoreFabric> getAll();
	
	public void saveOrUpdateAll(List<StoreFabric> list);
	
	public List<StoreFabric> getStoreFabricByPara(Map<String,Object> paramap);
	
	public List<StoreFabric> getStoreFabricByStoreId(Long storeId);
	public List<StoreFabric> getStoreFabricBystoreName(String storeName);
	
	public List<StoreFabric> getStoreFabricByOrderId(Long orderId);
	public void deleteAll(Collection<StoreFabric> sfs);
	public StoreFabric getStoreFabric(Long storeId,String vcFactoryCode,String vcModelNum);
	public void deleteByProperty(String name,Object value);
	public List<StoreFabric> getStoreFabricByQuoteId(Long quoteId);
	public void deleteByEntitys(Collection<StoreFabric> sfs);
}
