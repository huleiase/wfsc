package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Email;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("emailDao")
public class EmailDao extends EnhancedHibernateDaoSupport<Email> {


	@Override
	protected String getEntityName() {
		return Email.class.getName();
	}
	
	public int getUnreadCount(String username){
		String hql = "from Email where username='"+username+"' and state='1'";
		int count =this.countByHql(hql);
		return count;
	}
	
	public Page<Email> findForPage(Page<Email> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Email as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("username".equals(key)) {
					hql.append(" and obj.username ='"+paramap.get(key)+"'");
					continue;
				}
				if ("state".equals(key)) {
					hql.append(" and obj.state ='"+paramap.get(key)+"'");
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Email> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Email> getEmailByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Email as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("username".equals(key)) {
				hql.append(" and obj.username ='"+paramap.get(key)+"'");
				continue;
			}
			if ("state".equals(key)) {
				hql.append(" and obj.state ='"+paramap.get(key)+"'");
				continue;
			}
		}
		List<Email> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}