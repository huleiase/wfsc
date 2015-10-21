package com.wfsc.daos.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.system.SystemLog;

@Repository("systemLogDao")
@SuppressWarnings("unchecked")
public class SystemLogDao extends EnhancedHibernateDaoSupport<SystemLog>  {

	@Override
	protected String getEntityName() {
		return SystemLog.class.getName();
	}
	
	public Page<SystemLog> getSystemLogForPage(Page<SystemLog> page, Map<String, Object> params){
		String hql = "from SystemLog where 1=1";
		if(MapUtils.isNotEmpty(params)){
			/*if(params.get("tiLevel") != null){
				hql += " and tiLevel = '" + params.get("tiLevel").toString() + "'";
			}*/
			if(params.get("vcLogUser") != null){
				hql += " and vcLogUser = '" + params.get("vcLogUser").toString() + "'";
			}
			if(params.get("startTime") != null && params.get("endTime") != null){
				hql += " and (operatTime >= '" + params.get("startTime").toString() + "' and operatTime <= '" + params.get("endTime").toString() + "')";
			}
		}
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		hql+=" order by operatTime desc";
		List<SystemLog> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}

}
