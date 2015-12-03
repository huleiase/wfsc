package com.wfsc.daos.bym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.StoreFabric;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("storeFabricDao")
public class StoreFabricDao extends EnhancedHibernateDaoSupport<StoreFabric> {


	@Override
	protected String getEntityName() {
		return StoreFabric.class.getName();
	}
	
	public Page<StoreFabric> findForPage(Page<StoreFabric> page, Map<String,Object> paramap){
		Session s = null;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from StoreFabric obj,Quote quote left join quote.salesman as sellname where obj.quoteId=quote.id and obj.storeId=").append(paramap.get("storeId"));
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(sf.id)) AS countId");
		countSql.append(" FROM bym_store_fabric sf inner JOIN bym_quote quote on sf.quoteId=quote.id");
		countSql.append(" LEFT OUTER JOIN bym_quote_salesman salesman ON quote.id=salesman.quoteId");
		countSql.append(" WHERE sf.storeId='"+paramap.get("storeId")+"' ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("vcProject".equals(key)) {
					hql.append(" and obj.vcProject like :vcProject");
					dataMap.put("vcProject", paramap.get(key));
					countSql.append(" and sf.vcProject like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.displayNum like '%").append(paramap.get(key).toString()+"%'");
				//	dataMap.put("displayNum", paramap.get(key));
					countSql.append(" and sf.displayNum like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("htCode".equals(key)) {
					hql.append(" and obj.displayNum like '%").append(paramap.get(key).toString()+"%'");
				//	dataMap.put("displayNum", paramap.get(key));
					countSql.append(" and sf.displayNum like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				
				if("comeCode".equalsIgnoreCase(key)){
					hql.append(" and obj.vcModelNum like '%").append(paramap.get(key)).append("%'");
					countSql.append(" and sf.vcModelNum like '%").append(paramap.get(key).toString()+"%'");
				}
				
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					countSql.append(" and sf.orderNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("surplus".equals(key)) {
					if("0".equals(paramap.get(key))){
						hql.append(" and obj.surplus = 0");
						countSql.append(" and sf.surplus = '0'");
					}else{
						hql.append(" and obj.surplus != 0");
						countSql.append(" and sf.surplus != '0'");
					}
					continue;
				}
				
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate >= :sdate " );
					dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(sf.inStoreDate,'%Y%m%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate <= :edate ");
					dataMap.put("edate", edate);
					countSql.append(" and DATE_FORMAT(sf.inStoreDate,'%Y%m%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("userName".equals(key)) {
					hql.append(" and sellname = '").append(paramap.get(key)).append("' ");
					countSql.append(" and salesman.salesname = '").append(paramap.get(key)).append("' ");
					continue;
				}
				if("isStoreOver".equalsIgnoreCase(key)){
					hql.append(" and obj.isStoreOver = '").append(paramap.get(key)).append("' ");
					countSql.append(" and sf.isStoreOver = '").append(paramap.get(key).toString()+"' ");
				}
			}
		//	System.out.println(hql);
		/*	int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);*/
			List<StoreFabric> list = this.findList4PageWithParama(hql.toString(), page
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
	public List<StoreFabric> getStoreFabricByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from StoreFabric obj,Quote quote left join quote.salesman as sellname where obj.quoteId=quote.id and obj.storeId=").append(paramap.get("storeId"));
		Date sdate = null;
		Date edate = null;
		try {
			for (String key : paramap.keySet()) {
				if ("vcProject".equals(key)) {
					hql.append(" and obj.vcProject like :vcProject");
					dataMap.put("vcProject", paramap.get(key));
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.vcModelNum like :vcModelNum");
					dataMap.put("vcModelNum", paramap.get(key));
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					continue;
				}
				if ("surplus".equals(key)) {
					if("0".equals(paramap.get(key))){
						hql.append(" and obj.surplus = 0");
					}else{
						hql.append(" and obj.surplus != 0");
					}
					continue;
				}
				
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
				if ("userName".equals(key)) {
					/*hql.append(" and (obj.vcSalesman = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman1 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman2 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman3 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman4 = '").append(paramap.get(key)).append("') ");*/
					//quote.salesman
					hql.append(" and sellname = '").append(paramap.get(key)).append("' ");
					continue;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<StoreFabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}