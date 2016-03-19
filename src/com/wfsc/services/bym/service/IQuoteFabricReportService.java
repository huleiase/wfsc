package com.wfsc.services.bym.service;

import java.util.List;

import com.wfsc.common.bo.bym.QuoteFabricReport;


public interface IQuoteFabricReportService {

	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(QuoteFabricReport entity); 
	
	public QuoteFabricReport getQuoteFabricReportById(Long id);
	
	public List<QuoteFabricReport> getAll();
	
	public void saveOrUpdateAll(List<QuoteFabricReport> list);
	
	public List<QuoteFabricReport> getQuoteFabricReportByDoId(Long doId);
}
