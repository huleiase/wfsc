package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.bym.Store;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("storeDao")
public class StoreDao extends EnhancedHibernateDaoSupport<Store> {


	@Override
	protected String getEntityName() {
		return Store.class.getName();
	}
	
	public List<Store> getStoreByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Store as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("storeName".equals(key)) {
				hql.append("  and obj.storeName = '"+paramap.get(key)+"'");
				continue;
			}
			if ("userName".equals(key)) {
				//sql += " or CONCAT(',',domainCode,',') LIKE '%,"+codes.get(i)+",%'";
				hql.append(" or CONCAT(',',obj.userName,',') LIKE '%,"+paramap.get(key)+",%'"); 
				continue;
			}
			
		}
		hql.append(" or obj.isPublic = 1 ");
		List<Store> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}