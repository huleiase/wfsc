package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.QuoteFabricReport;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("quoteFabricReportDao")
public class QuoteFabricReportDao extends EnhancedHibernateDaoSupport<QuoteFabricReport> {


	@Override
	protected String getEntityName() {
		return QuoteFabricReport.class.getName();
	}
	public Page<QuoteFabricReport> findForPage(Page<QuoteFabricReport> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabricReport as obj where obj.operation='add' ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(de.id)) AS countId from bym_qf_report de where de.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		sdate = sf.parse(paramap.get(key).toString()+" 00:00:01");
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
			//		dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		edate = sf.parse(paramap.get(key).toString()+" 23:59:59");
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
			//		dataMap.put("endDate", edate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					countSql.append(" and de.contractNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					countSql.append(" and de.orderNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					countSql.append(" and de.quoteLocal = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					countSql.append(" and de.supplier like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ");
			countSql.append(" and de.isReplaced='0' ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			List<QuoteFabricReport> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
	//		System.out.println("hql=="+hql.toString());
	//		System.out.println("countSql=="+countSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
	public List<QuoteFabricReport> getQuoteFabricReportByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		List<QuoteFabricReport> list = null;
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabricReport as obj where obj.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
				//	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		sdate = sf.parse(paramap.get(key).toString()+" 00:00:01");
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
				//	dataMap.put("sdate", sdate);
					continue;
				}
				if ("endDate".equals(key)) {
				//	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//	edate = sf.parse(paramap.get(key).toString()+" 23:59:59");
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
				//	dataMap.put("endDate", edate);
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					continue;
				}
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			list =  this.findList4PageWithParama(hql.toString(), -1,-1,dataMap);
			}
		
		catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
	public List<Map<String, Object>> getReportDatas(Map<String, String> paramMap){
		StringBuffer sql = new StringBuffer("select new map(qfr.yearMonth as ym,sum(vcOldPriceTotal) as sr) from QuoteFabricReport as qfr where qfr.operation='add' ");
		for (String key : paramMap.keySet()) {
			if ("sDate".equals(key)) {
				sql.append(" and qfr.yearMonth >='").append(paramMap.get(key)).append("'");
				continue;
			}
			if ("eDate".equals(key)) {
				sql.append(" and qfr.yearMonth <='").append(paramMap.get(key)).append("'");
				continue;
			}
			if("displayName".equals(key)){
				sql.append(" and qfr.vcModelNum ='").append(paramMap.get(key)).append("'");
				continue;
			}
		}
		sql.append(" group by qfr.yearMonth order by qfr.yearMonth");
		return this.getHibernateTemplate().find(sql.toString());
	}
	
	public List<Map<String, Object>> getQFRByAreaAndMounth(String mouth,String local,int limit,String vcModelNum){
		StringBuffer sql = new StringBuffer("select new map(qfr.vcModelNum as vm,sum(qfr.vcQuantity) as vq,avg(qfr.bjPrice) as bjPrice,qfr.vcMoney as vcMoney,sum(qfr.cbQuantity) as cbQuantity,avg(qfr.cbPrice) as cbPrice,sum(qfr.vcOldPriceTotal) as vcOldPriceTotal) from QuoteFabricReport as qfr where qfr.operation='add' and qfr.isCgbj='1' and qfr.isHidden='0' ");
		sql.append(" and qfr.yearMonth = '").append(mouth).append("'");
		sql.append(" and qfr.quoteLocal ='").append(local).append("'");
		if(StringUtils.isNotBlank(vcModelNum)){
			sql.append(" and qfr.vcModelNum='").append(vcModelNum).append("'");
			sql.append(" group by qfr.vcModelNum");
		}else{
			sql.append(" group by qfr.vcModelNum order by sum(qfr.vcQuantity) desc ");
		}
		HibernateTemplate hb = this.getHibernateTemplate();
		hb.setMaxResults(limit);
		return hb.find(sql.toString());
	} 
	
	public Page<QuoteFabricReport> findSumForPage(Page<QuoteFabricReport> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabricReport as obj where obj.operation='add' ");
		StringBuffer countSql = new StringBuffer("SELECT count(*) AS countId from bym_qf_report de where de.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					countSql.append(" and de.contractNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					countSql.append(" and de.orderNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					countSql.append(" and de.quoteLocal = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					countSql.append(" and de.supplier like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ");
			countSql.append(" and de.isReplaced='0' ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			List<QuoteFabricReport> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
	//		System.out.println("hql=="+hql.toString());
	//		System.out.println("countSql=="+countSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
}