package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Store;


public interface IStoreService {

	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Store entity); 
	
	public Store getStoreById(Long id);
	
	public List<Store> getAll();
	
	public void saveOrUpdateAll(List<Store> list);
	
	public List<Store> getStoreByPara(Map<String,Object> paramap);
	
	public Store getStore(String storeName);
}
