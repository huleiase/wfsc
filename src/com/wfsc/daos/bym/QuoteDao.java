package com.wfsc.daos.bym;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Quote;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("quoteDao")
public class QuoteDao extends EnhancedHibernateDaoSupport<Quote> {


	@Override
	protected String getEntityName() {
		return Quote.class.getName();
	}
	public Page<Quote> findForPage(Page<Quote> page, Map<String,Object> paramap){
		Session s = null;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Quote as obj LEFT OUTER join obj.quoteFabric as qf LEFT OUTER join obj.salesman as sellname where 1=1 ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(quote.id)) AS countId");
		countSql.append(" FROM bym_quote quote LEFT OUTER JOIN bym_quote_fabric qf ON quote.id=qf.quoteId");
		countSql.append(" LEFT OUTER JOIN bym_quote_salesman salesman ON quote.id=salesman.quoteId");
		countSql.append(" WHERE 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.vcFormDate >= :sdate " );
					dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(quote.vcFormDate,'%Y%m%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.vcFormDate <= :edate ");
					dataMap.put("edate", edate);
					countSql.append(" and DATE_FORMAT(quote.vcFormDate,'%Y%m%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("projectName".equals(key)) {
					hql.append(" and obj.projectName like :projectName");
					dataMap.put("projectName", paramap.get("projectName"));
					countSql.append(" and quote.projectName like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("vcSalesman".equals(key)) {
					hql.append(" and obj.vcSalesman like :vcSalesman");
					dataMap.put("vcSalesman", paramap.get("vcSalesman"));
					countSql.append(" and quote.vcSalesman like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("projectNum".equals(key)) {
					hql.append(" and obj.projectNum like :projectNum");
					dataMap.put("projectNum", paramap.get("projectNum"));
					countSql.append(" and quote.projectNum like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("vcAttn".equals(key)) {
					hql.append(" and obj.vcAttn like :vcAttn");
					dataMap.put("vcAttn", paramap.get("vcAttn"));
					countSql.append(" and quote.vcAttn like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteFormate".equals(key)) {
					hql.append(" and obj.quoteFormate = '").append(paramap.get("quoteFormate")).append("' ");
					countSql.append(" and quote.quoteFormate = '").append(paramap.get(key)).append("' ");
					continue;
				}
				if ("projectDesignComp".equals(key)) {
					hql.append(" and obj.projectDesignComp like :projectDesignComp");
					dataMap.put("projectDesignComp", paramap.get("projectDesignComp"));
					countSql.append(" and quote.projectDesignComp like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("curUserName".equals(key)) {
					hql.append(" and obj.curUserName like :curUserName");
					dataMap.put("curUserName", paramap.get("curUserName"));
					countSql.append(" and quote.curUserName like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if("vcQuoteLocal".equals(key)){
					hql.append(" and obj.vcQuoteLocal = '").append(paramap.get("vcQuoteLocal")).append("' ");
					countSql.append(" and quote.vcQuoteLocal = '").append(paramap.get(key)).append("' ");
					continue;
				}
				if("saleName".equals(key)){
					hql.append(" and sellname = '").append(paramap.get("saleName")).append("' ");
					countSql.append(" and salesman.salesname = '").append(paramap.get(key)).append("' ");
					continue;
				}
				if ("vcBefModel".equals(key)) {
					hql.append(" and ((qf.vcModelNum like '%"+paramap.get("vcBefModel")).append("%' and qf.isHtCode='0') ")
					.append("or (qf.htCode like '%"+paramap.get("vcBefModel")+"%'))");
					countSql.append(" and quote.curUserName like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				
			}
			
			List<Quote> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
	public List<Quote> getQuoteByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Quote as obj where 1=1 ");
		List<Quote> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
	
	public List<Quote> getQuoteForRef(String local,String yearMonth){
		String hql = "select distinct obj from Quote as obj where obj.vcQuoteLocal='"+local+"' and obj.vcYearMonth='"+yearMonth+"'";
		return this.getEntityByHql(hql);
	}
}