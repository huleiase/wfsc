package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Store;
import com.wfsc.daos.bym.StoreDao;
import com.wfsc.services.bym.service.IStoreService;

@Service("storeService")
@SuppressWarnings("unchecked")
public class StoreServiceImpl implements IStoreService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private StoreDao storeDao;

	public void deleteByIds(List<Long> ids) {
		storeDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Store entity){
		storeDao.saveOrUpdateEntity(entity);
	}
	public Store getStoreById(Long id){
		return storeDao.getEntityById(id);
	}
	
	@Override
	public List<Store> getAll() {
		return storeDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Store> list) {
		storeDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Store> getStoreByPara(Map<String, Object> paramap) {
		return storeDao.getStoreByPara(paramap);
	}
	@Override
	public Store getStore(String storeName) {
		return storeDao.getUniqueEntityByOneProperty("storeName", storeName);
	}

	
}
