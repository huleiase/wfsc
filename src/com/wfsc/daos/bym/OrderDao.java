package com.wfsc.daos.bym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Order;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("orderDao")
public class OrderDao extends EnhancedHibernateDaoSupport<Order> {


	@Override
	protected String getEntityName() {
		return Order.class.getName();
	}
	
	public Page<Order> findForPage(Page<Order> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from Order as obj left join obj.purchase.quote as quote left join obj.purchase.quote.quoteFabric as qf LEFT  join obj.purchase.quote.salesman as sellname where obj.factoryNum=qf.vcFactoryNum and qf.isReplaced != 1 ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(ordera.id)) AS countId");
		countSql.append(" FROM bym_order ordera LEFT OUTER JOIN bym_quote quote on ordera.quoteId=quote.id left join bym_quote_fabric qf ON quote.id=qf.quoteId");
		countSql.append(" LEFT OUTER JOIN bym_quote_salesman salesman ON quote.id=salesman.quoteId");
		countSql.append(" WHERE ordera.factoryNum=qf.vcFactoryNum and  qf.isReplaced != 1 ");
		try {
			Date sdate1 = null;
			Date edate1 = null;
			Date sdate2 = null;
			Date edate2 = null;
			for (String key : paramap.keySet()) {
				if ("startTime1".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate1 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.orderDate >= :sdate1 " );
					dataMap.put("sdate1", sdate1);
					countSql.append(" and ordera.orderDate>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endTime1".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate1 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.orderDate <= :edate1 ");
					dataMap.put("edate1", edate1);
					countSql.append(" and ordera.orderDate<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("startTime2".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate2 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.shipDateRemark >= :sdate2 " );
					dataMap.put("sdate2", sdate2);
					countSql.append(" and ordera.shipDateRemark>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endTime2".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate2 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.shipDateRemark <= :edate2 ");
					dataMap.put("edate2", edate2);
					countSql.append(" and ordera.shipDateRemark<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if("supplier".equalsIgnoreCase(key)){
					hql.append(" and obj.supplier like :supplier ");
					dataMap.put(key, paramap.get(key).toString());
					countSql.append(" and ordera.supplier like '%").append(paramap.get(key).toString()+"%'");
				}
				if("vcfrom".equalsIgnoreCase(key)){
					hql.append(" and obj.vcfrom like :vcfrom ");
					dataMap.put(key, paramap.get(key).toString());
					countSql.append(" and ordera.vcfrom like '%").append(paramap.get(key).toString()+"%'");
				}
				if("orderNo".equalsIgnoreCase(key)){
					hql.append(" and obj.orderNo like :orderNo ");
					dataMap.put(key, paramap.get(key).toString());
					countSql.append(" and ordera.orderNo like '%").append(paramap.get(key).toString()+"%'");
				}
				if("expressNumber".equalsIgnoreCase(key)){
					hql.append(" and (obj.expressNumber1 like :expressNumber1 or obj.expressNumber2 like :expressNumber2 or obj.expressNumber3 like :expressNumber3 ) ");
					dataMap.put("expressNumber1", paramap.get(key).toString());
					dataMap.put("expressNumber2", paramap.get(key).toString());
					dataMap.put("expressNumber3", paramap.get(key).toString());
					String en = paramap.get(key).toString();
					countSql.append(" and (ordera.expressNumber1 like '%"+en+"%'").append(" or ordera.expressNumber2 like '%"+en+"%'").append(" or ordera.expressNumber3 like '%"+en+"%')");
				}
				if ("isOrderConfirm".equals(key)) {
					hql.append(" and obj.isOrderConfirm = '"+paramap.get(key)+"'");
					countSql.append(" and ordera.isOrderConfirm = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("isShipments".equals(key)) {
					hql.append(" and obj.isShipments = '"+paramap.get(key)+"'");
					countSql.append(" and ordera.isShipments = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("orderStatus".equals(key)) {
					hql.append(" and obj.orderStatus = '"+paramap.get(key)+"'");
					countSql.append(" and ordera.orderStatus = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("area_zh".equals(key)) {
					hql.append(" and obj.areaZh = '"+paramap.get(key)+"'");
					countSql.append(" and ordera.areaZh = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				if("vcModelNum".equalsIgnoreCase(key)){
					hql.append(" and qf.vcModelNum like :vcModelNum ");
					dataMap.put(key, paramap.get(key).toString());
					countSql.append(" and qf.vcModelNum like '%").append(paramap.get(key).toString()+"%'");
				}
				if("saleName".equals(key)){
					hql.append(" and sellname = '").append(paramap.get("saleName")).append("' ");
					countSql.append(" and salesman.salesname = '").append(paramap.get(key)).append("' ");
					continue;
				}
				if ("isOver".equals(key)) {
					hql.append(" and obj.isOver = '"+paramap.get(key)+"'");
					countSql.append(" and ordera.isOver = '").append(paramap.get(key).toString()+"'");
					continue;
				}
			}
			//System.out.println("查订单数量的sql==="+countSql.toString());
		//	int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
		//	page.setTotalCount(totalCount);
			List<Order> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
	public List<Order> getOrderByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct obj from Order as obj where 1=1 ");
		try {
			Date sdate1 = null;
			Date edate1 = null;
			Date sdate2 = null;
			Date edate2 = null;
			for (String key : paramap.keySet()) {
				if ("startTime1".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate1 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.orderDate >= :sdate1 " );
					dataMap.put("sdate1", sdate1);
					continue;
				}
				if ("endTime1".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate1 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.orderDate <= :edate1 ");
					dataMap.put("edate1", edate1);
					continue;
				}
				if ("startTime2".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate2 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.shipDateRemark >= :sdate2 " );
					dataMap.put("sdate2", sdate2);
					continue;
				}
				if ("endTime2".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate2 = sf.parse(paramap.get(key).toString());
					hql.append(" and obj.shipDateRemark <= :edate2 ");
					dataMap.put("edate2", edate2);
					continue;
				}
				if("supplier".equalsIgnoreCase(key)){
					hql.append(" and obj.supplier like :supplier ");
					dataMap.put(key, paramap.get(key).toString());
				}
				if("vcfrom".equalsIgnoreCase(key)){
					hql.append(" and obj.vcfrom like :vcfrom ");
					dataMap.put(key, paramap.get(key).toString());
				}
				if("orderNo".equalsIgnoreCase(key)){
					hql.append(" and obj.orderNo like :orderNo ");
					dataMap.put(key, paramap.get(key).toString());
				}
				if("expressNumber".equalsIgnoreCase(key)){
					hql.append(" and obj.expressNumber like :expressNumber ");
					dataMap.put(key, paramap.get(key).toString());
				}
				if ("isOrderConfirm".equals(key)) {
					hql.append(" and obj.isOrderConfirm = '"+paramap.get(key)+"'");
					continue;
				}
				if ("isShipments".equals(key)) {
					hql.append(" and obj.isShipments = '"+paramap.get(key)+"'");
					continue;
				}
				if ("orderStatus".equals(key)) {
					hql.append(" and obj.orderStatus = '"+paramap.get(key)+"'");
					continue;
				}
				if ("area_zh".equals(key)) {
					hql.append(" and obj.areaZh = '"+paramap.get(key)+"'");
					continue;
				}
				if ("isOver".equals(key)) {
					hql.append(" and obj.isOver = '"+paramap.get(key)+"'");
					continue;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Order> list = this.findListWithParama(hql.toString(), dataMap);
		return list;
	}
}