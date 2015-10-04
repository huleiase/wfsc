package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Fabric;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("fabricDao")
public class FabricDao extends EnhancedHibernateDaoSupport<Fabric> {


	@Override
	protected String getEntityName() {
		return Fabric.class.getName();
	}
	
	public Page<Fabric> findForPage(Page<Fabric> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Fabric as obj where isHtCode='0' ");
		try {
			for (String key : paramap.keySet()) {
				if ("vcFactoryCode".equals(key)) {
					hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
					dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
					continue;
				}
				if ("vcBefModel".equals(key)) {
					hql.append(" and obj.vcBefModel like :vcBefModel");
					dataMap.put("vcBefModel", paramap.get("vcBefModel"));
					continue;
				}
				if ("vcType".equals(key)) {
					hql.append(" and obj.vcType like :vcType");
					dataMap.put("vcType", paramap.get("vcType"));
					continue;
				}
				if ("vcWidth".equals(key)) {
					hql.append(" and obj.vcWidth = "+paramap.get("vcWidth"));
				//	dataMap.put("vcWidth", paramap.get("vcWidth"));
					continue;
				}
				if ("vcDis".equals(key)) {
					hql.append(" and obj.vcDis = '"+paramap.get("vcDis")+"' ");
					continue;
				}
			}
			hql.append(" order by obj.id desc ");
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Fabric> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Fabric> getFabricByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Fabric as obj where isHtCode='0' ");
		for (String key : paramap.keySet()) {
			if ("vcFactoryCode".equals(key)) {
				hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
				dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
				continue;
			}
			if ("vcBefModel".equals(key)) {
				hql.append(" and obj.vcBefModel like :vcBefModel");
				dataMap.put("vcBefModel", paramap.get("vcBefModel"));
				continue;
			}
			if ("vcType".equals(key)) {
				hql.append(" and obj.vcType like :vcType");
				dataMap.put("vcType", paramap.get("vcType"));
				continue;
			}
			if ("vcWidth".equals(key)) {
				hql.append(" and obj.vcWidth  = "+paramap.get("vcWidth"));
			//	dataMap.put("vcWidth", paramap.get("vcWidth"));
				continue;
			}
			if ("vcDis".equals(key)) {
				hql.append(" and obj.vcDis = '"+paramap.get("vcDis")+"' ");
				continue;
			}
		}
		hql.append(" order by obj.id desc ");
		List<Fabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
	
	public Page<Fabric> findHTForPage(Page<Fabric> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Fabric as obj where isHtCode='1' ");
		try {
			for (String key : paramap.keySet()) {
				if ("vcFactoryCode".equals(key)) {
					hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
					dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
					continue;
				}
				if ("vcBefModel".equals(key)) {
					hql.append(" and obj.vcBefModel like :vcBefModel");
					dataMap.put("vcBefModel", paramap.get("vcBefModel"));
					continue;
				}
				if ("htCode".equals(key)) {
					hql.append(" and obj.htCode like :htCode");
					dataMap.put("htCode", paramap.get("htCode"));
					continue;
				}
				if ("vcWidth".equals(key)) {
					hql.append(" and obj.vcWidth  = "+paramap.get("vcWidth"));
					//dataMap.put("vcWidth", paramap.get("vcWidth"));
					continue;
				}
				if ("bookNo".equals(key)) {
					hql.append(" and obj.bookNo like :bookNo");
					dataMap.put("bookNo", paramap.get("bookNo"));
					continue;
				}
				if ("vcDis".equals(key)) {
					hql.append(" and obj.vcDis = '"+paramap.get("vcDis")+"' ");
					continue;
				}
				if ("brand".equals(key)) {
					hql.append(" and obj.brand like :brand");
					dataMap.put("brand", paramap.get("brand"));
					continue;
				}
			}
			hql.append(" order by obj.id desc ");
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Fabric> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Fabric> getHTFabricByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Fabric as obj where isHtCode='1' ");
		for (String key : paramap.keySet()) {
			if ("vcFactoryCode".equals(key)) {
				hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
				dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
				continue;
			}
			if ("vcBefModel".equals(key)) {
				hql.append(" and obj.vcBefModel like :vcBefModel");
				dataMap.put("vcBefModel", paramap.get("vcBefModel"));
				continue;
			}
			if ("htCode".equals(key)) {
				hql.append(" and obj.htCode like :htCode");
				dataMap.put("htCode", paramap.get("htCode"));
				continue;
			}
			if ("vcWidth".equals(key)) {
				hql.append(" and obj.vcWidth  = "+paramap.get("vcWidth"));
			//	dataMap.put("vcWidth", paramap.get("vcWidth"));
				continue;
			}
			if ("bookNo".equals(key)) {
				hql.append(" and obj.bookNo like :bookNo");
				dataMap.put("bookNo", paramap.get("bookNo"));
				continue;
			}
			if ("vcDis".equals(key)) {
				hql.append(" and obj.vcDis = '"+paramap.get("vcDis")+"' ");
				continue;
			}
			if ("brand".equals(key)) {
				hql.append(" and obj.brand like :brand");
				dataMap.put("brand", paramap.get("brand"));
				continue;
			}
		}
		hql.append(" order by obj.id desc ");
		List<Fabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}