package com.wfsc.services.bym;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.QuoteFabricReport;
import com.wfsc.daos.bym.AttachmentDao;
import com.wfsc.daos.bym.QuoteFabricReportDao;
import com.wfsc.services.bym.service.ICommonService;

@Service("commonService")
@SuppressWarnings("unchecked")
public class CommonServiceImpl implements ICommonService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private AttachmentDao attachmentDao;
	@Resource
	private QuoteFabricReportDao quoteFabricReportDao;

	@Override
	public void deleteAttachmentByKey(String linkCode) {
		attachmentDao.deleteEntityByProperty("linkCode", linkCode);
	}

	@Override
	public List<Attachment> getAllAttachment() {
		return attachmentDao.getAllEntities();
	}

	@Override
	public List<Attachment> getAttachmentByKey(String linkCode) {
		return attachmentDao.getEntitiesByOneProperty("linkCode", linkCode);
	}

	@Override
	public Attachment getOnlyAttachmentByKey(String linkCode) {
		return attachmentDao.getUniqueEntityByOneProperty("linkCode", linkCode);
	}

	@Override
	public void saveOrUpdateAllAttachment(List<Attachment> list) {
		attachmentDao.saveOrUpdateAllEntities(list);
	}

	@Override
	public void saveOrUpdateEntity(Attachment attr) {
		attachmentDao.saveOrUpdateEntity(attr);
	}

	@Override
	public void saveOrUpdateAllQFR(List<QuoteFabricReport> qfrs) {
		quoteFabricReportDao.saveOrUpdateAllEntities(qfrs);
		
	}

	@Override
	public List<QuoteFabricReport> getByQuoteId(Long quoteId) {
		return quoteFabricReportDao.getEntitiesByPropNames(new String[]{"quoteId","operation"}, new Object[]{quoteId,"add"});
	}

	
}
