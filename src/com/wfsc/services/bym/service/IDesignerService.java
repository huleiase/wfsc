package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Designer;


public interface IDesignerService {

	public Page<Designer> findForPage(Page<Designer> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Designer entity); 
	
	public Designer getDesignerById(Long id);
	
	/*public void disableDesigners(String[] ids);
	
	public void enableDesigners(String[] ids);
	
	public void disableDesigner(String id);
	
	public void enableDesigner(String id);*/
	
	public List<Designer> getAll();
	
	public void saveOrUpdateAll(List<Designer> list);
	
	public List<Designer> getDesignerByPara(Map<String,Object> paramap);
	
	public Designer getDesigner(String codeName);
}
