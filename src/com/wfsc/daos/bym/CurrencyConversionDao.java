package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.CurrencyConversion;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("currencyConversionDao")
public class CurrencyConversionDao extends EnhancedHibernateDaoSupport<CurrencyConversion> {


	@Override
	protected String getEntityName() {
		return CurrencyConversion.class.getName();
	}
	
	public Page<CurrencyConversion> findForPage(Page<CurrencyConversion> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from CurrencyConversion as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("vcCurrency".equals(key)) {
					hql.append(" and obj.vcCurrency like :vcCurrency");
					dataMap.put("vcCurrency", paramap.get("vcCurrency"));
					continue;
				}
				if ("vcObjCurrency".equals(key)) {
					hql.append(" and obj.vcObjCurrency like :vcObjCurrency");
					dataMap.put("vcObjCurrency", paramap.get("vcObjCurrency"));
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<CurrencyConversion> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<CurrencyConversion> getCurrencyConversionByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from CurrencyConversion as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("vcCurrency".equals(key)) {
				hql.append(" and obj.vcCurrency like :vcCurrency");
				dataMap.put("vcCurrency", paramap.get("vcCurrency"));
				continue;
			}
			if ("vcObjCurrency".equals(key)) {
				hql.append(" and obj.vcObjCurrency like :vcObjCurrency");
				dataMap.put("vcObjCurrency", paramap.get("vcObjCurrency"));
				continue;
			}
		}
		List<CurrencyConversion> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}