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
import com.wfsc.common.bo.bym.Purchase;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("purchaseDao")
public class PurchaseDao extends EnhancedHibernateDaoSupport<Purchase> {


	@Override
	protected String getEntityName() {
		return Purchase.class.getName();
	}
	
	public Page<Purchase> findForPage(Page<Purchase> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Purchase as obj where 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			String orderStatus = paramap.get("orderStatus")==null?"":paramap.get("orderStatus").toString();
			String purchaseType = paramap.get("purchaseType")==null?"":paramap.get("purchaseType").toString();
			if("".equals(orderStatus)&&"1".equals(purchaseType)){
				hql.append(" and obj.orderStatus <= 3");
			}else if("".equals(orderStatus)&&"2".equals(purchaseType)){
				hql.append(" and obj.orderStatus >= 2");
			}
			for (String key : paramap.keySet()) {
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.contractDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.contractDate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key).toString());
					continue;
				}
				
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key).toString());
					continue;
				}
				
				if ("area".equals(key)) {
					hql.append(" and obj.area = '"+paramap.get("area")+"'");
					continue;
				}
				if ("deliveryRequirements".equals(key)) {
					hql.append(" and obj.deliveryRequirements = '"+paramap.get("deliveryRequirements")+"'");
					continue;
				}
				if ("orderStatus".equals(key)) {
					if("1".equals(purchaseType)&&"2".equals(orderStatus)){
						hql.append(" and obj.orderStatus >= 2 ");
					}else{
						hql.append(" and obj.orderStatus = '"+orderStatus+"'");
					}
					
					continue;
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			hql.append(" order by obj.contractDate desc ");
			List<Purchase> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	public List<Purchase> getPurchaseByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Purchase as obj where 1=1 ");
		Date sdate = null;
		Date edate = null;
		try {
			for (String key : paramap.keySet()) {
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.contractDate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.contractDate <= :edate ");
					dataMap.put("edate", edate);
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key).toString());
					continue;
				}
				
				if ("area".equals(key)) {
					hql.append(" and obj.area = '"+paramap.get("area")+"'");
					continue;
				}
				if ("deliveryRequirements".equals(key)) {
					hql.append(" and obj.deliveryRequirements = '"+paramap.get("deliveryRequirements")+"'");
					continue;
				}
				
				if ("orderStatus".equals(key)) {
					hql.append(" and obj.orderStatus = '"+paramap.get("orderStatus")+"'");
					continue;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Purchase> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}