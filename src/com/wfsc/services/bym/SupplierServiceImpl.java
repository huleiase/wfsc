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
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.daos.bym.SupplierDao;
import com.wfsc.services.bym.service.ISupplierService;

@Service("supplierService")
@SuppressWarnings("unchecked")
public class SupplierServiceImpl implements ISupplierService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private SupplierDao supplierDao;

	public Page<Supplier> findForPage(Page<Supplier> page, Map<String,Object> paramap){
		return supplierDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		supplierDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Supplier entity){
		supplierDao.saveOrUpdateEntity(entity);
	}
	public Supplier getSupplierById(Long id){
		return supplierDao.getEntityById(id);
	}
	
	public void disableSupplier(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Supplier s = supplierDao.getEntityById(Long.valueOf(id));
		s.setVcDis("停用");
		supplierDao.updateEntity(s);
	}
	
	public void enableSupplier(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Supplier s = supplierDao.getEntityById(Long.valueOf(id));
		s.setVcDis("启用");
		supplierDao.updateEntity(s);
	}
	@Override
	public void disableSuppliers(String[] ids) {
		for(String id : ids){
			disableSupplier(id);
		}
		
	}
	@Override
	public void enableSuppliers(String[] ids) {
		for(String id : ids){
			enableSupplier(id);
		}
	}
	@Override
	public List<Supplier> getAll() {
		return supplierDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Supplier> list) {
		supplierDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Supplier> getSupplierByPara(Map<String, Object> paramap) {
		return supplierDao.getSupplierByPara(paramap);
	}
	@Override
	public Supplier getSupplierByCode(String code) {
		return supplierDao.getUniqueEntityByOneProperty("vcFactoryCode", code);
	}
	@Override
	public List<Supplier> getSupplierByNum(String num) {
		return supplierDao.getEntitiesByOneProperty("vcFactoryNum", num);
	}
	@Override
	public void deleteAll(Collection<Supplier> entities){
		supplierDao.deleteAllEntities(entities);
	}

}
