package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
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
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerOrder as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("orderId".equals(key)) {
					hql.append(" and obj.orderId = "+paramap.get("orderId"));
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal = '"+paramap.get("quoteLocal")+"'");
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<DesignerOrder> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<DesignerOrder> getDesignerOrderByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from DesignerOrder as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("orderId".equals(key)) {
				hql.append(" and obj.orderId = "+paramap.get("orderId"));
				continue;
			}
			if ("quoteLocal".equals(key)) {
				hql.append(" and obj.quoteLocal = '"+paramap.get("quoteLocal")+"'");
				continue;
			}
		}
		List<DesignerOrder> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}