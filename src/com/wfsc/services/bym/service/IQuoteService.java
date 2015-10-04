package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Quote;


public interface IQuoteService {

	public Page<Quote> findForPage(Page<Quote> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Quote entity); 
	
	public Quote getQuoteById(Long id);
	
	public List<Quote> getAll();
	
	public void saveOrUpdateAll(List<Quote> list);
	
	public List<Quote> getQuoteByPara(Map<String,Object> paramap);
	
	public Quote getQuote(String projectNum);
	public String getQuoteRef(String local);
}
