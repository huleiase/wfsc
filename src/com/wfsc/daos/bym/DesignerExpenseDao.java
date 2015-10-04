package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerExpense;

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
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerExpense as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal = '"+paramap.get("quoteLocal")+"'");
					continue;
				}
				if ("quoteId".equals(key)) {
					hql.append(" and obj.quoteId = "+paramap.get("quoteId"));
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<DesignerExpense> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<DesignerExpense> getDesignerExpenseByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerExpense as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("quoteLocal".equals(key)) {
				hql.append(" and obj.quoteLocal = '"+paramap.get("quoteLocal")+"'");
				continue;
			}
			if ("quoteId".equals(key)) {
				hql.append(" and obj.quoteId = "+paramap.get("quoteId"));
				continue;
			}
		}
		List<DesignerExpense> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}