package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.QuoteFabric;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("quoteFabricDao")
public class QuoteFabricDao extends EnhancedHibernateDaoSupport<QuoteFabric> {


	@Override
	protected String getEntityName() {
		return QuoteFabric.class.getName();
	}
	
	public Page<QuoteFabric> findForPage(Page<QuoteFabric> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabric as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("vcFactoryCode".equals(key)) {
					hql.append(" and obj.vcFactoryCode = '"+paramap.get("vcFactoryCode")+"'");
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.vcModelNum  = '"+paramap.get("vcModelNum")+"'");
					continue;
				}
				if ("isHtCode".equals(key)) {
					hql.append(" and obj.isHtCode = '"+paramap.get("isHtCode")+"'");
					continue;
				}
				if ("htCode".equals(key)) {
					hql.append(" and obj.htCode = '"+paramap.get("htCode")+"'");
					continue;
				}
				if ("quoteFormat".equals(key)) {
					hql.append(" and obj.quoteFormat = '"+paramap.get("quoteFormat")+"'");
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<QuoteFabric> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<QuoteFabric> getQuoteFabricByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabric as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("vcFactoryCode".equals(key)) {
				hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
				dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
				continue;
			}
			if ("vcModelNum".equals(key)) {
				hql.append(" and obj.vcModelNum like :vcModelNum");
				dataMap.put("vcModelNum", paramap.get("vcModelNum"));
				continue;
			}
			if ("isHtCode".equals(key)) {
				hql.append(" and obj.isHtCode = '"+paramap.get("isHtCode")+"'");
				continue;
			}
			if ("quoteId".equals(key)) {
				hql.append(" and obj.quote.id = "+paramap.get("quoteId"));
				continue;
			}
		}
		List<QuoteFabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}