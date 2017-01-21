package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.MaterialTotal;
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
	@Override
	public Page<QuoteFabricReport> findForPage(Page<QuoteFabricReport> page,
			Map<String, Object> paramap) {
		// TODO Auto-generated method stub
		return quoteFabricReportDao.findForPage(page, paramap);
	}
	@Override
	public List<QuoteFabricReport> getQuoteFabricReportByPara(
			Map<String, Object> paramap) {
		// TODO Auto-generated method stub
		return quoteFabricReportDao.getQuoteFabricReportByPara(paramap);
	}
	
	@Override
	public List<QuoteFabricReport> getQuoteFabricReportByQuoteId(Long quoteId) {
		String hql = "from QuoteFabricReport where quoteId="+quoteId+" order by id desc";
		return quoteFabricReportDao.getEntityByHql(hql);
	}
	@Override
	public Page<MaterialTotal> findSumForPage(Page<MaterialTotal> page,
			Map<String, Object> paramap) {
		return quoteFabricReportDao.findSumForPage(page, paramap);
	}
	@Override
	public void cp(){
		quoteFabricReportDao.copyQfToQfr();
	}

}
