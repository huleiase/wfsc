package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Customer;


public interface ICustomerService {

	public Page<Customer> findForPage(Page<Customer> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Customer entity); 
	
	public Customer getCustomerById(Long id);
	
	public void disableCustomers(String[] ids);
	
	public void enableCustomers(String[] ids);
	
	public void disableCustomer(String id);
	
	public void enableCustomer(String id);
	
	public List<Customer> getAll();
	
	public void saveOrUpdateAll(List<Customer> list);
	
	public List<Customer> getCustomerByPara(Map<String,Object> paramap);
}
