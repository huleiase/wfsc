package com.wfsc.services.bym;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Purchase;
import com.wfsc.daos.bym.PurchaseDao;
import com.wfsc.services.bym.service.IPurchaseService;

@Service("purchaseService")
@SuppressWarnings("unchecked")
public class PurchaseServiceImpl implements IPurchaseService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private PurchaseDao purchaseDao;

	public Page<Purchase> findForPage(Page<Purchase> page, Map<String,Object> paramap){
		return purchaseDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		purchaseDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Purchase entity){
		purchaseDao.saveOrUpdateEntity(entity);
	}
	public Purchase getPurchaseById(Long id){
		return purchaseDao.getEntityById(id);
	}
	
	@Override
	public List<Purchase> getAll() {
		return purchaseDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Purchase> list) {
		purchaseDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Purchase> getPurchaseByPara(Map<String, Object> paramap) {
		return purchaseDao.getPurchaseByPara(paramap);
	}
	@Override
	public List<Purchase> getPurchaseByQuoteId(Long quoteId) {
		return purchaseDao.getEntitiesByOneProperty("quote.id", quoteId);
	}
	@Override
	public Purchase getUniqPurchaseByQuoteId(Long quoteId) {
		return purchaseDao.getUniqueEntityByOneProperty("quote.id", quoteId);
	}
	@Override
	public void deleteByProperty(String name, Object value) {
		purchaseDao.deleteEntityByProperty(name, value);
		
	}
	@Override
	public void deleteByEntitys(Collection<Purchase> purs) {
		purchaseDao.deleteAllEntities(purs);
	}
	
}
