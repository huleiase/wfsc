package com.wfsc.services.bym;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.daos.bym.QuoteDao;
import com.wfsc.services.bym.service.IQuoteService;

@Service("quoteService")
@SuppressWarnings("unchecked")
public class QuoteServiceImpl implements IQuoteService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private QuoteDao quoteDao;

	public Page<Quote> findForPage(Page<Quote> page, Map<String,Object> paramap){
		return quoteDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		quoteDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Quote entity){
		quoteDao.saveOrUpdateEntity(entity);
	}
	public Quote getQuoteById(Long id){
		return quoteDao.getEntityById(id);
	}
	
	@Override
	public List<Quote> getAll() {
		return quoteDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Quote> list) {
		quoteDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Quote> getQuoteByPara(Map<String, Object> paramap) {
		return quoteDao.getQuoteByPara(paramap);
	}
	@Override
	public Quote getQuote(String projectNum) {
		return quoteDao.getUniqueEntityByOneProperty("projectNum", projectNum);
	}
	@Override
	public String getQuoteRef(String local) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		String ym = year+String.format("%02d", month);
		List<Quote> list = quoteDao.getQuoteForRef(local,ym);
		if(CollectionUtils.isEmpty(list)){
			return ym+"001";
		}else{
			String num = String.format("%03d", list.size()+1);
			return ym+num;
		}
	}

}
