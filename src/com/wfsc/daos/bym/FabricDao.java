package com.wfsc.daos.bym;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
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
				if ("refid".equals(key)) {
					if("0".equals(paramap.get(key))){
						hql.append(" and obj.refid IS NULL");
					}else if("1".equals(paramap.get(key))){
						hql.append(" and obj.refid IS NOT NULL");
					}
					
					continue;
				}
			}
			hql.append(" order by obj.id desc ");
			System.out.println(hql.toString());
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
			if ("refid".equals(key)) {
				if("0".equals(paramap.get(key))){
					hql.append(" and obj.refid IS NULL");
				}else if("1".equals(paramap.get(key))){
					hql.append(" and obj.refid IS NOT NULL");
				}
				
				continue;
			}
		}
		hql.append(" order by obj.id desc ");
		List<Fabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
	
	public Map<String, Long> getRefMap(){
		Map<String,Long> map = new HashMap<String,Long>();
		String hql = "select f.id,f.vcFactoryCode,f.vcBefModel from Fabric f where f.isHtCode = '0'";
		Iterator it = getHibernateTemplate().find(hql).iterator();
		while(it.hasNext()){
			Object[] row = (Object[])it.next();
			map.put(row[1]+"_"+row[2], (Long)row[0]);
		}
		return map;
	}
	public Fabric getFabricByCode(String vcFactoryCode,String vcBefModel){
		String hql = "from Fabric where vcFactoryCode=? and vcBefModel=? and isHtCode='0'";
		List<Fabric> list = this.getHibernateTemplate().find(hql, vcFactoryCode,vcBefModel);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public Fabric getHtFabricByCode(String vcFactoryCode,String vcBefModel,String htCode){
		String hql = "from Fabric where vcFactoryCode=? and vcBefModel=? and htCode=?";
		List<Fabric> list = this.getHibernateTemplate().find(hql, vcFactoryCode,vcBefModel,htCode);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public Long getRefIdByCode(String vcFactoryCode,String vcBefmodle){
		String hql = "select f.id from Fabric f where f.vcFactoryCode=? and f.vcBefModel=? and f.isHtCode = '0'";
		List list = getHibernateTemplate().find(hql, vcFactoryCode,vcBefmodle);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return (Long)list.get(0);
		}
	}
	public Page<Fabric> findForQuotePage(Page<Fabric> page,
			String vcFactoryCode ,String vcBefModel) {
		Session s = null;
		String sql = "select id,vcFactoryCode,vcBefModel,vcDis from bym_fabric where isHtCode='0' ";
		String countSql = "select count(id) from bym_fabric where isHtCode='0'  ";
		if(StringUtils.isNotBlank(vcFactoryCode)){
			sql += " and vcFactoryCode like '%"+vcFactoryCode+"%'";
			countSql += " and vcFactoryCode like '%"+vcFactoryCode+"%'";
		}
		if(StringUtils.isNotBlank(vcBefModel)){
			sql += " and vcBefModel like '%"+vcBefModel+"%'";
			countSql += " and vcBefModel like '%"+vcBefModel+"%'";
		}
		System.out.println("报价查询原厂型号sql==="+sql);
		try {
			s = this.getSession();
			List<Object[]> list = s.createSQLQuery(sql).addScalar("id", Hibernate.LONG).addScalar("vcFactoryCode", Hibernate.STRING).addScalar("vcBefModel", Hibernate.STRING).addScalar("vcDis", Hibernate.STRING).setFirstResult(page.getFirst()-1).setMaxResults(page.getPageSize()).list();
			List<Fabric> fs = new ArrayList<Fabric>();
			if(CollectionUtils.isNotEmpty(list)){
				for(Object[] obj : list){
					if(obj!=null&&obj.length==4){
						Fabric f = new Fabric();
						f.setId((Long)obj[0]);
						f.setVcFactoryCode(obj[1].toString());
						f.setVcBefModel(obj[2].toString());
						f.setVcDis(obj[3].toString());
						f.setIsHtCode("0");
						fs.add(f);
					}
				}
				
			}
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
			page.setData(fs);
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		
		return page;
	}
	public Page<Fabric> findHTForQuotePage(Page<Fabric> page,
			String htCode) {
		Session s = null;
		String sql = "select id,vcFactoryCode,vcBefModel,htCode,vcDis from bym_fabric where isHtCode='1' ";
		String countSql = "select count(id) from bym_fabric where isHtCode='1' ";
		if(StringUtils.isNotBlank(htCode)){
			sql += " and htCode like '%"+htCode+"%'";
			countSql += " and htCode like '%"+htCode+"%'";
		}
		System.out.println("报价查询HT型号sql==="+sql);
		try {
			s = this.getSession();
			List<Object[]> list = s.createSQLQuery(sql).addScalar("id", Hibernate.LONG).addScalar("vcFactoryCode", Hibernate.STRING).addScalar("vcBefModel", Hibernate.STRING).addScalar("htCode", Hibernate.STRING).addScalar("vcDis", Hibernate.STRING).setFirstResult(page.getFirst()-1).setMaxResults(page.getPageSize()).list();
			List<Fabric> fs = new ArrayList<Fabric>();
			if(CollectionUtils.isNotEmpty(list)){
				for(Object[] obj : list){
					if(obj!=null&&obj.length==5){
						Fabric f = new Fabric();
						f.setId((Long)obj[0]);
						f.setVcFactoryCode(obj[1].toString());
						f.setVcBefModel(obj[2].toString());
						f.setHtCode(obj[3].toString());
						f.setVcDis(obj[4].toString());
						f.setIsHtCode("1");
						fs.add(f);
					}
				}
			}
			page.setData(fs);
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		
		return page;
	}
	
	public int updateVcdis(){
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String updateSql = "update bym_fabric hb,bym_fabric fb set hb.vcDis =fb.vcDis where hb.refid=fb.id ;";
				SQLQuery query = session.createSQLQuery(updateSql);
				return query.executeUpdate();
			}
		});
	}
}