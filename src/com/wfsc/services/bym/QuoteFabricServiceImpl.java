package com.wfsc.services.bym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.QuoteFabric;
import com.wfsc.daos.bym.QuoteFabricDao;
import com.wfsc.services.bym.service.IQuoteFabricService;

@Service("quoteFabricService")
@SuppressWarnings("unchecked")
public class QuoteFabricServiceImpl implements IQuoteFabricService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private QuoteFabricDao quoteFabricDao;

	public Page<QuoteFabric> findForPage(Page<QuoteFabric> page, Map<String,Object> paramap){
		return quoteFabricDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		quoteFabricDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(QuoteFabric entity){
		quoteFabricDao.saveOrUpdateEntity(entity);
	}
	public QuoteFabric getQuoteFabricById(Long id){
		return quoteFabricDao.getEntityById(id);
	}
	
	@Override
	public List<QuoteFabric> getAll() {
		return quoteFabricDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<QuoteFabric> list) {
		quoteFabricDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<QuoteFabric> getQuoteFabricByPara(Map<String, Object> paramap) {
		return quoteFabricDao.getQuoteFabricByPara(paramap);
	}
	@Override
	public void deleteByProperty(String name, Object value) {
		quoteFabricDao.deleteEntityByProperty(name, value);
	}
	@Override
	public List<QuoteFabric> getQfByQuoteId(Long quoteId) {
		// TODO Auto-generated method stub
		return quoteFabricDao.getEntitiesByOneProperty("quoteId", quoteId);
	}
	@Override
	public List<QuoteFabric> getQuoteFabricByCodeAndQuoteId(String a, String b,
			Long c) {
		// TODO Auto-generated method stub
		return new ArrayList<QuoteFabric>();
	}
	@Override
	public List<QuoteFabric> getQfByOrderId(Long bymOrderId) {
		return quoteFabricDao.getEntitiesByOneProperty("bymOrderId", bymOrderId);
	}
	
}
