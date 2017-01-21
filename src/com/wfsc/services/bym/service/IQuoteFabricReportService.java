package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.MaterialTotal;
import com.wfsc.common.bo.bym.QuoteFabricReport;


public interface IQuoteFabricReportService {

	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(QuoteFabricReport entity); 
	
	public QuoteFabricReport getQuoteFabricReportById(Long id);
	
	public List<QuoteFabricReport> getAll();
	
	public void saveOrUpdateAll(List<QuoteFabricReport> list);
	
	public List<QuoteFabricReport> getQuoteFabricReportByDoId(Long doId);
	
	public Page<QuoteFabricReport> findForPage(Page<QuoteFabricReport> page, Map<String,Object> paramap);
	
	public List<QuoteFabricReport> getQuoteFabricReportByPara(Map<String,Object> paramap);
	public List<QuoteFabricReport> getQuoteFabricReportByQuoteId(Long quoteId);
	
	public Page<MaterialTotal> findSumForPage(Page<MaterialTotal> page, Map<String,Object> paramap);
	public void cp();
}
