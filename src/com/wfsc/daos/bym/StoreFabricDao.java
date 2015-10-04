package com.wfsc.daos.bym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.bym.Supplier;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("storeFabricDao")
public class StoreFabricDao extends EnhancedHibernateDaoSupport<StoreFabric> {


	@Override
	protected String getEntityName() {
		return StoreFabric.class.getName();
	}
	
	public Page<StoreFabric> findForPage(Page<StoreFabric> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from StoreFabric as obj where obj.storeId=").append(paramap.get("storeId"));
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("vcProject".equals(key)) {
					hql.append(" and obj.vcProject like :vcProject");
					dataMap.put("vcProject", paramap.get(key));
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.vcModelNum like :vcModelNum");
					dataMap.put("vcModelNum", paramap.get(key));
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					continue;
				}
				if ("qsurplus".equals(key)) {
					if("0".equals(paramap.get(key))){
						hql.append(" and obj.qsurplus = 0");
					}else{
						hql.append(" and obj.qsurplus != 0");
					}
					continue;
				}
				
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
				if ("userName".equals(key)) {
					hql.append(" and (obj.vcSalesman = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman1 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman2 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman3 = '").append(paramap.get(key)).append("'");
					hql.append(" or obj.vcSalesman4 = '").append(paramap.get(key)).append("') ");
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<StoreFabric> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<StoreFabric> getStoreFabricByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from StoreFabric as obj where storeId=").append(paramap.get("storeId"));
		Date sdate = null;
		Date edate = null;
		try {
			for (String key : paramap.keySet()) {
				if ("vcProject".equals(key)) {
					hql.append(" and obj.vcProject like :vcProject");
					dataMap.put("vcProject", paramap.get(key));
					continue;
				}
				if ("vcModelNum".equals(key)) {
					hql.append(" and obj.vcModelNum like :vcModelNum");
					dataMap.put("vcModelNum", paramap.get(key));
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					continue;
				}
				if ("qsurplus".equals(key)) {
					if("0".equals(paramap.get(key))){
						hql.append(" and obj.qsurplus = 0");
					}else{
						hql.append(" and obj.qsurplus != 0");
					}
					continue;
				}
				
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.inStoreDate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<StoreFabric> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}