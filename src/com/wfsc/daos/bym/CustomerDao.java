package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Customer;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("customerDao")
public class CustomerDao extends EnhancedHibernateDaoSupport<Customer> {


	@Override
	protected String getEntityName() {
		return Customer.class.getName();
	}
	
	public Page<Customer> findForPage(Page<Customer> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Customer as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("companyName".equals(key)) {
					hql.append(" and obj.companyName like :companyName");
					dataMap.put("companyName", paramap.get("companyName"));
					continue;
				}
				if ("linkman".equals(key)) {
					hql.append(" and obj.linkman like :linkman");
					dataMap.put("linkman", paramap.get("linkman"));
					continue;
				}
				if ("dis".equals(key)) {
					hql.append(" and obj.dis like :dis");
					dataMap.put("dis", paramap.get("dis"));
					continue;
				}
				
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Customer> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Customer> getCustomerByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Customer as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("companyName".equals(key)) {
				hql.append(" and obj.companyName like :companyName");
				dataMap.put("companyName", paramap.get("companyName"));
				continue;
			}
			if ("linkman".equals(key)) {
				hql.append(" and obj.linkman like :linkman");
				dataMap.put("linkman", paramap.get("linkman"));
				continue;
			}
			if ("dis".equals(key)) {
				hql.append(" and obj.dis like :dis");
				dataMap.put("dis", paramap.get("dis"));
				continue;
			}
			
		}
		List<Customer> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}