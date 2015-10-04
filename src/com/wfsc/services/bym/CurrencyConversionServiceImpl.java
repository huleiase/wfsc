package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.CurrencyConversion;
import com.wfsc.daos.bym.CurrencyConversionDao;
import com.wfsc.services.bym.service.ICurrencyConversionService;

@Service("currencyConversionService")
@SuppressWarnings("unchecked")
public class CurrencyConversionServiceImpl implements ICurrencyConversionService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private CurrencyConversionDao currencyConversionDao;

	public Page<CurrencyConversion> findForPage(Page<CurrencyConversion> page, Map<String,Object> paramap){
		return currencyConversionDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		currencyConversionDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(CurrencyConversion entity){
		currencyConversionDao.saveOrUpdateEntity(entity);
	}
	public CurrencyConversion getCurrencyConversionById(Long id){
		return currencyConversionDao.getEntityById(id);
	}
	
	@Override
	public List<CurrencyConversion> getAll() {
		return currencyConversionDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<CurrencyConversion> list) {
		currencyConversionDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<CurrencyConversion> getCurrencyConversionByPara(Map<String, Object> paramap) {
		return currencyConversionDao.getCurrencyConversionByPara(paramap);
	}
	@Override
	public float getExchangeRate(String source, String target) {
		float rate = 1.0F;
		CurrencyConversion cc = currencyConversionDao.getUniqueEntityByPropNames(new String[]{"vcCurrency","vcObjCurrency"}, new Object[]{source,target});
		if(cc!=null){
			rate = cc.getVcExchangeRate();
		}
		return rate;
	}

}
