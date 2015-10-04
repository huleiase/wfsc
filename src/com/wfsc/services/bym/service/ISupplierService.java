package com.wfsc.services.bym.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Supplier;


public interface ISupplierService {

	public Page<Supplier> findForPage(Page<Supplier> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Supplier entity); 
	
	public Supplier getSupplierById(Long id);
	
	public void disableSuppliers(String[] ids);
	
	public void enableSuppliers(String[] ids);
	
	public void disableSupplier(String id);
	
	public void enableSupplier(String id);
	
	public List<Supplier> getAll();
	
	public void saveOrUpdateAll(List<Supplier> list);
	
	public List<Supplier> getSupplierByPara(Map<String,Object> paramap);
	
	public List<Supplier> getSupplierByNum(String num);
	
	public Supplier getSupplierByCode(String code);
	
	public void deleteAll(Collection<Supplier> entities);
}
