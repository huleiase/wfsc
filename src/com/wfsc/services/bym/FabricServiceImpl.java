package com.wfsc.services.bym;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Fabric;
import com.wfsc.daos.bym.FabricDao;
import com.wfsc.services.bym.service.IFabricService;

@Service("fabricService")
@SuppressWarnings("unchecked")
public class FabricServiceImpl implements IFabricService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private FabricDao fabricDao;

	public Page<Fabric> findForPage(Page<Fabric> page, Map<String,Object> paramap){
		return fabricDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		fabricDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Fabric entity){
		fabricDao.saveOrUpdateEntity(entity);
	}
	public Fabric getFabricById(Long id){
		return fabricDao.getEntityById(id);
	}
	
	public void disableFabric(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Fabric s = fabricDao.getEntityById(Long.valueOf(id));
		s.setVcDis("停用");
		List<Fabric> htList = getHTFabricByRefid(s.getId());
		if(htList!=null){
			for(Fabric ht : htList){
				ht.setVcDis("停用");
			}
		}
		htList.add(s);
		fabricDao.saveOrUpdateAllEntities(htList);
	}
	
	public void enableFabric(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Fabric s = fabricDao.getEntityById(Long.valueOf(id));
		s.setVcDis("启用");
		fabricDao.updateEntity(s);
	}
	@Override
	public void disableFabrics(String[] ids) {
		for(String id : ids){
			disableFabric(id);
		}
		
	}
	@Override
	public void enableFabrics(String[] ids) {
		for(String id : ids){
			enableFabric(id);
		}
	}
	@Override
	public List<Fabric> getAll() {
		return fabricDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(Collection<Fabric> list) {
		fabricDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Fabric> getFabricByPara(Map<String, Object> paramap) {
		return fabricDao.getFabricByPara(paramap);
	}
	@Override
	public Page<Fabric> findHTForPage(Page<Fabric> page,
			Map<String, Object> paramap) {
		return fabricDao.findHTForPage(page, paramap);
	}
	@Override
	public Fabric getFabricByCode(String vcFactoryCode, String vcBefModel) {
		return fabricDao.getUniqueEntityByPropNames(new String[]{"vcFactoryCode","vcBefModel","isHtCode"}, new Object[]{vcFactoryCode,vcBefModel,"0"});
	}
	@Override
	public Fabric getHTFabricByCode(String vcFactoryCode, String vcBefModel,String htCode) {
		return fabricDao.getUniqueEntityByPropNames(new String[]{"vcFactoryCode","vcBefModel","htCode"}, new Object[]{vcFactoryCode,vcBefModel,htCode});
	}
	@Override
	public List<Fabric> getHTFabricByRefid(Long refId) {
		return fabricDao.getEntitiesByOneProperty("refid", refId);
	}
	@Override
	public List<Fabric> getHTFabricByPara(Map<String, Object> paramap) {
		return fabricDao.getHTFabricByPara(paramap);
	}
	@Override
	public List<Fabric> getAllFabric() {
		return fabricDao.getEntitiesByOneProperty("isHtCode", "0");
	}
	@Override
	public List<Fabric> getAllHTFabric() {
		return fabricDao.getEntitiesByOneProperty("isHtCode", "1");
	}
	@Override
	public List<Fabric> getFabricByHql(String hql) {
		return fabricDao.getEntityByHql(hql);
	}
	@Override
	public void deleteFabrics(Collection<Fabric> fs) {
		fabricDao.deleteAllEntities(fs);
	}
	@Override
	public Map<String, Long> getRefMap() {
		
		return fabricDao.getRefMap();
	}

}
