package com.wfsc.daos.city;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.system.City;

@Repository("cityDao")
@SuppressWarnings("unchecked")
public class CityDao extends EnhancedHibernateDaoSupport<City> {

	@Override
	protected String getEntityName() {
		return City.class.getName();
	}
	
	public Page<City> queryAllCityForPage(Page<City> page, String name, Boolean support){
		StringBuffer hql = new StringBuffer("from City where 1=1 ");
		if(support != null)
			hql.append(" and support=" + support.booleanValue());
		if(StringUtils.isNotBlank(name)){
			hql.append(" and name like '%" + name + "%'");
		}
		hql.append(" order by hot desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<City> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
	public List<City> queryCityBySupport(boolean support){
		String hql = "from City where support = ? order by hot desc";
		return getHibernateTemplate().find(hql, support);
	}
	
	
}
