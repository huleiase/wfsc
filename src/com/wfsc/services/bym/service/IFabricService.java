package com.wfsc.services.bym.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Fabric;


public interface IFabricService {

	public Page<Fabric> findForPage(Page<Fabric> page, Map<String,Object> paramap);
	
	public Page<Fabric> findHTForPage(Page<Fabric> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids,String isHtCode);
	
	public void saveOrUpdateEntity(Fabric entity); 
	
	public Fabric getFabricById(Long id);
	
	public void disableFabrics(String[] ids);
	
	public void enableFabrics(String[] ids);
	
	public void disableFabric(String id);
	
	public void enableFabric(String id);
	
	public List<Fabric> getAll();
	
	public void saveOrUpdateAll(Collection<Fabric> list);
	
	public List<Fabric> getFabricByPara(Map<String,Object> paramap);
	
	public List<Fabric> getHTFabricByPara(Map<String,Object> paramap);
	
	public Fabric getFabricByCode(String vcFactoryCode,String vcBefModel);
	
	public List<Fabric> getHTFabricByRefid(Long refId);
	
	public List<Fabric> getAllFabric(); 
	
	public List<Fabric> getAllHTFabric(); 
	
	public List<Fabric> getFabricByHql(String hql);
	
	public void deleteFabrics(List<Fabric> fs,String isHtCode);
	
	public Fabric getHTFabricByCode(String vcFactoryCode, String vcBefModel,String htCode);
	
	public Map<String,Long> getRefMap();
	
	public Long getRefIdByCode(String vcFactoryCode, String vcBefModel);
	
	public Page<Fabric> findForQuotePage(Page<Fabric> page, String vcFactoryCode ,String vcBefModel);
	
	public Page<Fabric> findHTForQuotePage(Page<Fabric> page, String htCode);
	
	public int updateVcdis();
	
	public List<Long> getHbIdByCode(String vcFactoryCode,String vcBefModel);
	
	public int updateRefIdByHtId(List<Long> ids,Long fbid);
}
