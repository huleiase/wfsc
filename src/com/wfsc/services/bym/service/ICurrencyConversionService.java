package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.CurrencyConversion;


public interface ICurrencyConversionService {

	public Page<CurrencyConversion> findForPage(Page<CurrencyConversion> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(CurrencyConversion entity); 
	
	public CurrencyConversion getCurrencyConversionById(Long id);
	
	/*public void disableCurrencyConversions(String[] ids);
	
	public void enableCurrencyConversions(String[] ids);
	
	public void disableCurrencyConversion(String id);
	
	public void enableCurrencyConversion(String id);*/
	
	public List<CurrencyConversion> getAll();
	
	public void saveOrUpdateAll(List<CurrencyConversion> list);
	
	public List<CurrencyConversion> getCurrencyConversionByPara(Map<String,Object> paramap);
	
	public float getExchangeRate(String source,String target);
}
