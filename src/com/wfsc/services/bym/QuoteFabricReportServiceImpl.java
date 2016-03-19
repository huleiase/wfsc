package com.wfsc.services.bym;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.wfsc.common.bo.bym.QuoteFabricReport;
import com.wfsc.daos.bym.QuoteFabricReportDao;
import com.wfsc.services.bym.service.IQuoteFabricReportService;

@Service("quoteFabricReportService")
@SuppressWarnings("unchecked")
public class QuoteFabricReportServiceImpl implements IQuoteFabricReportService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private QuoteFabricReportDao quoteFabricReportDao;

	public void deleteByIds(List<Long> ids) {
		quoteFabricReportDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(QuoteFabricReport entity){
		quoteFabricReportDao.saveOrUpdateEntity(entity);
	}
	public QuoteFabricReport getQuoteFabricReportById(Long id){
		return quoteFabricReportDao.getEntityById(id);
	}
	
	@Override
	public List<QuoteFabricReport> getAll() {
		return quoteFabricReportDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<QuoteFabricReport> list) {
		quoteFabricReportDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<QuoteFabricReport> getQuoteFabricReportByDoId(Long doId) {
		String hql = "from QuoteFabricReport where doId="+doId+" order by id desc";
		return quoteFabricReportDao.getEntityByHql(hql);
	}

}
