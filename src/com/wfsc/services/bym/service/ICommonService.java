package com.wfsc.services.bym.service;

import java.util.List;

import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.QuoteFabricReport;


public interface ICommonService {
	
	public void deleteAttachmentByKey(String linkCode);
	
	public void saveOrUpdateEntity(Attachment attr); 
	
	public Attachment getOnlyAttachmentByKey(String linkCode);
	
	public List<Attachment> getAllAttachment();
	
	public void saveOrUpdateAllAttachment(List<Attachment> list);
	
	public List<Attachment> getAttachmentByKey(String linkCode);
	
	public void saveOrUpdateAllQFR(List<QuoteFabricReport> qfrs);
	
	public List<QuoteFabricReport> getByQuoteId(Long quoteId);
}
