package com.wfsc.daos.bym;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerExpense;
import com.wfsc.common.bo.bym.DesignerOrder;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("designerOrderDao")
public class DesignerOrderDao extends EnhancedHibernateDaoSupport<DesignerOrder> {


	@Override
	protected String getEntityName() {
		return DesignerOrder.class.getName();
	}
	
	public Page<DesignerOrder> findForPage(Page<DesignerOrder> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerOrder as obj where 1=1 ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(de.id)) AS countId from bym_designerorder de where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			//		sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
			//		dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
				//	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				//	edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
				//	dataMap.put("endDate", edate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("sellman".equals(key)) {
					String vcSalesman = paramap.get(key)+",";
					hql.append(" and obj.vcSalesman like :vcSalesman");
					dataMap.put("vcSalesman", vcSalesman);
					countSql.append(" and de.vcSalesman like '%").append(vcSalesman+"%'");
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
			}
			hql.append(" order by obj.createDate desc,obj.id desc");
			List<DesignerOrder> list = this.findList4PageWithParama(hql.toString(), page
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
	public List<DesignerOrder> getDesignerOrderByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		List<DesignerOrder> list = null;
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerOrder as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			//		sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
			//		dataMap.put("sdate", sdate);
					continue;
				}
				if ("endDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				//	edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
			//		dataMap.put("endDate", edate);
					continue;
				}
				if ("sellman".equals(key)) {
					String vcSalesman = paramap.get(key)+",";
					hql.append(" and obj.vcSalesman like :vcSalesman");
					dataMap.put("vcSalesman", vcSalesman);
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
			}
			hql.append(" order by obj.createDate desc,obj.id desc");
			list =  this.findList4PageWithParama(hql.toString(), -1,-1,dataMap);
		
	}catch(Exception e){
		e.printStackTrace();
		}
	return list;
	}
}