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
import com.wfsc.common.bo.bym.Quote;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("designerExpenseDao")
public class DesignerExpenseDao extends EnhancedHibernateDaoSupport<DesignerExpense> {


	@Override
	protected String getEntityName() {
		return DesignerExpense.class.getName();
	}
	
	public Page<DesignerExpense> findForPage(Page<DesignerExpense> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Date sdate = null;
		Date edate = null;
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerExpense as obj where 1=1 ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(de.id)) AS countId from bym_designerexpense de where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate >= :sdate " );
					dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate <= :endDate " );
					dataMap.put("endDate", edate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("designer".equals(key)) {
					hql.append(" and (obj.designer1 = '").append(paramap.get(key).toString()).append("' or obj.designer2 = '")
					.append(paramap.get(key).toString()).append("' or obj.designer3 = '").append(paramap.get(key).toString())
					.append("')");
					countSql.append(" and (de.designer1 = '").append(paramap.get(key).toString()).append("' or de.designer2 = '")
					.append(paramap.get(key).toString()).append("' or de.designer3 = '").append(paramap.get(key).toString())
					.append("')");
					continue;
				}
				if ("isApply".equals(key)) {
					hql.append(" and obj.isApply = '"+paramap.get(key)+"'");
					countSql.append(" and de.isApply = '"+paramap.get(key)+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					countSql.append(" and de.contractNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteNo".equals(key)) {
					hql.append(" and obj.quoteNo like :quoteNo");
					dataMap.put("quoteNo", paramap.get(key));
					countSql.append(" and de.quoteNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					countSql.append(" and de.quoteLocal = '").append(paramap.get(key).toString()+"'");
					continue;
				}
			}
			hql.append(" order by obj.id desc");
			List<DesignerExpense> list = this.findList4PageWithParama(hql.toString(), page
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
	public List<DesignerExpense> getDesignerExpenseByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Date sdate = null;
		Date edate = null;
		List<DesignerExpense> list = null;
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerExpense as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endDate".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.createDate <= :endDate " );
					dataMap.put("endDate", edate);
					continue;
				}
				if ("designer".equals(key)) {
					hql.append(" and (obj.designer1 = '").append(paramap.get(key).toString()).append("' or obj.designer2 = '")
					.append(paramap.get(key).toString()).append("' or obj.designer3 = '").append(paramap.get(key).toString())
					.append("')");
					continue;
				}
				if ("isApply".equals(key)) {
					hql.append(" and obj.isApply = '"+paramap.get(key)+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					continue;
				}
				if ("quoteNo".equals(key)) {
					hql.append(" and obj.quoteNo like :quoteNo");
					dataMap.put("quoteNo", paramap.get(key));
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					continue;
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			hql.append(" order by obj.id desc");
			list =  this.findList4PageWithParama(hql.toString(), -1,-1,dataMap);
		return list;
	}
}