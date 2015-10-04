package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Customer;
import com.wfsc.daos.bym.CustomerDao;
import com.wfsc.services.bym.service.ICustomerService;

@Service("customerService")
@SuppressWarnings("unchecked")
public class CustomerServiceImpl implements ICustomerService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private CustomerDao customerDao;

	public Page<Customer> findForPage(Page<Customer> page, Map<String,Object> paramap){
		return customerDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		customerDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Customer entity){
		customerDao.saveOrUpdateEntity(entity);
	}
	public Customer getCustomerById(Long id){
		return customerDao.getEntityById(id);
	}
	
	public void disableCustomer(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Customer s = customerDao.getEntityById(Long.valueOf(id));
		s.setDis("停用");
		customerDao.updateEntity(s);
	}
	
	public void enableCustomer(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Customer s = customerDao.getEntityById(Long.valueOf(id));
		s.setDis("启用");
		customerDao.updateEntity(s);
	}
	@Override
	public void disableCustomers(String[] ids) {
		for(String id : ids){
			disableCustomer(id);
		}
		
	}
	@Override
	public void enableCustomers(String[] ids) {
		for(String id : ids){
			enableCustomer(id);
		}
	}
	@Override
	public List<Customer> getAll() {
		return customerDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Customer> list) {
		customerDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Customer> getCustomerByPara(Map<String, Object> paramap) {
		return customerDao.getCustomerByPara(paramap);
	}

}
