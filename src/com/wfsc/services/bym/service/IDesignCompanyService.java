package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignCompany;


public interface IDesignCompanyService {

	public Page<DesignCompany> findForPage(Page<DesignCompany> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(DesignCompany entity); 
	
	public DesignCompany getDesignCompanyById(Long id);
	
	public void disableDesignCompanys(String[] ids);
	
	public void enableDesignCompanys(String[] ids);
	
	public void disableDesignCompany(String id);
	
	public void enableDesignCompany(String id);
	
	public List<DesignCompany> getAll();
	
	public void saveOrUpdateAll(List<DesignCompany> list);
	
	public List<DesignCompany> getDesignCompanyByPara(Map<String,Object> paramap);
}
