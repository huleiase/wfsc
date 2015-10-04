package com.wfsc.daos.bym;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Supplier;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("supplierDao")
public class SupplierDao extends EnhancedHibernateDaoSupport<Supplier> {


	@Override
	protected String getEntityName() {
		return Supplier.class.getName();
	}
	
	public Page<Supplier> findForPage(Page<Supplier> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Supplier as obj where 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("vcFactoryCode".equals(key)) {
					hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
					dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
					continue;
				}
				if ("vcFactoryName".equals(key)) {
					hql.append(" and obj.vcFactoryName like :vcFactoryName");
					dataMap.put("vcFactoryName", paramap.get("vcFactoryName"));
					continue;
				}
				if ("vcPlaceProduce".equals(key)) {
					hql.append(" and obj.vcPlaceProduce like :vcPlaceProduce");
					dataMap.put("vcPlaceProduce", paramap.get("vcPlaceProduce"));
					continue;
				}
				if ("vcLinkMan".equals(key)) {
					hql.append(" and obj.vcLinkMan like :vcLinkMan");
					dataMap.put("vcLinkMan", paramap.get("vcLinkMan"));
					continue;
				}
				if ("vcDis".equals(key)) {
					hql.append(" and obj.vcDis like :vcDis");
					dataMap.put("vcDis", paramap.get("vcDis"));
					continue;
				}
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.pdate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.pdate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
				if ("vcRemarks".equals(key)) {
					hql.append(" and obj.vcRemarks like :vcRemarks");
					dataMap.put("vcRemarks", paramap.get("vcRemarks"));
					continue;
				}
			}
			hql.append(" order by obj.createDate desc ");
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Supplier> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Supplier> getSupplierByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Supplier as obj where 1=1 ");
		for (String key : paramap.keySet()) {
			if ("vcFactoryCode".equals(key)) {
				hql.append(" and obj.vcFactoryCode like :vcFactoryCode");
				dataMap.put("vcFactoryCode", paramap.get("vcFactoryCode"));
				continue;
			}
			if ("vcFactoryName".equals(key)) {
				hql.append(" and obj.vcFactoryName like :vcFactoryName");
				dataMap.put("vcFactoryName", paramap.get("vcFactoryName"));
				continue;
			}
			if ("vcPlaceProduce".equals(key)) {
				hql.append(" and obj.vcPlaceProduce like :vcPlaceProduce");
				dataMap.put("vcPlaceProduce", paramap.get("vcPlaceProduce"));
				continue;
			}
			if ("vcLinkMan".equals(key)) {
				hql.append(" and obj.vcLinkMan like :vcLinkMan");
				dataMap.put("vcLinkMan", paramap.get("vcLinkMan"));
				continue;
			}
			if ("vcDis".equals(key)) {
				hql.append(" and obj.vcDis like :vcDis");
				dataMap.put("vcDis", paramap.get("vcDis"));
				continue;
			}
		}
		hql.append(" order by obj.createDate desc ");
		List<Supplier> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}