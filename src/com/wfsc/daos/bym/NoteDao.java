package com.wfsc.daos.bym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Note;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("noteDao")
public class NoteDao extends EnhancedHibernateDaoSupport<Note> {


	@Override
	protected String getEntityName() {
		return Note.class.getName();
	}
	
	public Page<Note> findForPage(Page<Note> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Note as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("vcUser".equals(key)) {
					hql.append(" and obj.vcUser='"+paramap.get(key)+"'");
					continue;
				}
				if ("vcName".equals(key)) {
					hql.append(" and obj.vcName='"+paramap.get(key)+"'");
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Note> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Note> getNotes(){
		StringBuffer hql = new StringBuffer("select distinct obj from Note as obj where current_date() between obj.dtsTime and obj.dteTime ");
		List<Note> list = this.getEntityByHql(hql.toString());
		return list;
	}
}