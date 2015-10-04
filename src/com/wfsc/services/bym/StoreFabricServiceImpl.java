package com.wfsc.services.bym;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.daos.bym.StoreFabricDao;
import com.wfsc.services.bym.service.IStoreFabricService;

@Service("storeFabricService")
@SuppressWarnings("unchecked")
public class StoreFabricServiceImpl implements IStoreFabricService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private StoreFabricDao storeFabricDao;

	public Page<StoreFabric> findForPage(Page<StoreFabric> page, Map<String,Object> paramap){
		return storeFabricDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		storeFabricDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(StoreFabric entity){
		storeFabricDao.saveOrUpdateEntity(entity);
	}
	public StoreFabric getStoreFabricById(Long id){
		return storeFabricDao.getEntityById(id);
	}
	
	@Override
	public List<StoreFabric> getAll() {
		return storeFabricDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<StoreFabric> list) {
		storeFabricDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<StoreFabric> getStoreFabricByPara(Map<String, Object> paramap) {
		return storeFabricDao.getStoreFabricByPara(paramap);
	}
	@Override
	public List<StoreFabric> getStoreFabricByStoreId(Long storeId) {
		return storeFabricDao.getEntitiesByOneProperty("storeId", storeId);
	}
	@Override
	public List<StoreFabric> getStoreFabricByOrderId(Long orderId) {
		return storeFabricDao.getEntitiesByOneProperty("orderId", orderId);
	}
	public void deleteAll(Collection<StoreFabric> sfs){
		storeFabricDao.deleteAllEntities(sfs);
	}
	@Override
	public List<StoreFabric> getStoreFabricBystoreName(String storeName) {
		return storeFabricDao.getEntitiesByOneProperty("storeName", storeName);
	}
	@Override
	public StoreFabric getStoreFabric(Long storeId, String vcFactoryCode,
			String vcModelNum) {
		return storeFabricDao.getUniqueEntityByPropNames(new String[]{"isHtCode","storeId","vcFactoryCode","vcModelNum"}, new Object[]{"0",storeId,vcFactoryCode,vcModelNum});
	}
	@Override
	public void deleteByProperty(String name, Object value) {
		storeFabricDao.deleteEntityByProperty(name, value);
		
	}
	@Override
	public List<StoreFabric> getStoreFabricByQuoteId(Long quoteId) {
		return storeFabricDao.getEntitiesByOneProperty("quoteId", quoteId);
	}
	@Override
	public void deleteByEntitys(Collection<StoreFabric> sfs) {
		storeFabricDao.deleteAllEntities(sfs);
	}
	
}
