package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Designer;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("designerDao")
public class DesignerDao extends EnhancedHibernateDaoSupport<Designer> {


	@Override
	protected String getEntityName() {
		return Designer.class.getName();
	}
	
	public Page<Designer> findForPage(Page<Designer> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Designer as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("codeName".equals(key)) {
					hql.append(" and obj.codeName like :codeName");
					dataMap.put("codeName", paramap.get("codeName"));
					continue;
				}
				if ("designerName".equals(key)) {
					hql.append(" and obj.designerName like :designerName");
					dataMap.put("designerName", paramap.get("designerName"));
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Designer> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Designer> getDesignerByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Designer as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("codeName".equals(key)) {
				hql.append(" and obj.codeName like :codeName");
				dataMap.put("codeName", paramap.get("codeName"));
				continue;
			}
			if ("designerName".equals(key)) {
				hql.append(" and obj.designerName like :designerName");
				dataMap.put("designerName", paramap.get("designerName"));
				continue;
			}
		}
		List<Designer> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}