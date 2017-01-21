package com.wfsc.daos.bym;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerOrder;
import com.wfsc.common.bo.bym.MaterialTotal;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.common.bo.bym.QuoteFabric;
import com.wfsc.common.bo.bym.QuoteFabricReport;
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.PriceUtil;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("quoteFabricReportDao")
public class QuoteFabricReportDao extends EnhancedHibernateDaoSupport<QuoteFabricReport> {

	@Resource(name = "currencyConversionService")
	private ICurrencyConversionService currencyConversionService;
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	@Resource(name = "designerOrderDao")
	private DesignerOrderDao designerOrderDao;
	@Override
	protected String getEntityName() {
		return QuoteFabricReport.class.getName();
	}
	public Page<QuoteFabricReport> findForPage(Page<QuoteFabricReport> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		Session s = null;
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabricReport as obj where obj.operation='add' ");
		StringBuffer countSql = new StringBuffer("SELECT count(DISTINCT(de.id)) AS countId from bym_qf_report de where de.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		sdate = sf.parse(paramap.get(key).toString()+" 00:00:01");
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
			//		dataMap.put("sdate", sdate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
			//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		edate = sf.parse(paramap.get(key).toString()+" 23:59:59");
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
			//		dataMap.put("endDate", edate);
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					countSql.append(" and de.contractNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					countSql.append(" and de.orderNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					countSql.append(" and de.quoteLocal = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					countSql.append(" and de.supplier like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ");
			countSql.append(" and de.isReplaced='0' ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			List<QuoteFabricReport> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
	//		System.out.println("hql=="+hql.toString());
	//		System.out.println("countSql=="+countSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
	public List<QuoteFabricReport> getQuoteFabricReportByPara(Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
	//	Date sdate = null;
	//	Date edate = null;
		List<QuoteFabricReport> list = null;
		StringBuffer hql = new StringBuffer("select distinct obj from QuoteFabricReport as obj where obj.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
				//	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//		sdate = sf.parse(paramap.get(key).toString()+" 00:00:01");
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
				//	dataMap.put("sdate", sdate);
					continue;
				}
				if ("endDate".equals(key)) {
				//	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//	edate = sf.parse(paramap.get(key).toString()+" 23:59:59");
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
				//	dataMap.put("endDate", edate);
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					continue;
				}
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			list =  this.findList4PageWithParama(hql.toString(), -1,-1,dataMap);
			}
		
		catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
	public List<Map<String, Object>> getReportDatas(Map<String, String> paramMap){
		StringBuffer sql = new StringBuffer("select new map(qfr.yearMonth as ym,sum(vcOldPriceTotal) as sr) from QuoteFabricReport as qfr where qfr.operation='add' ");
		for (String key : paramMap.keySet()) {
			if ("sDate".equals(key)) {
				sql.append(" and qfr.yearMonth >='").append(paramMap.get(key)).append("'");
				continue;
			}
			if ("eDate".equals(key)) {
				sql.append(" and qfr.yearMonth <='").append(paramMap.get(key)).append("'");
				continue;
			}
			if("displayName".equals(key)){
				sql.append(" and qfr.vcModelNum ='").append(paramMap.get(key)).append("'");
				continue;
			}
		}
		sql.append(" group by qfr.yearMonth order by qfr.yearMonth");
		return this.getHibernateTemplate().find(sql.toString());
	}
	
	public List<Map<String, Object>> getQFRByAreaAndMounth(String mouth,String local,int limit,String vcModelNum){
		StringBuffer sql = new StringBuffer("select new map(qfr.vcModelNum as vm,sum(qfr.vcQuantity) as vq,avg(qfr.bjPrice) as bjPrice,qfr.vcMoney as vcMoney,sum(qfr.cbQuantity) as cbQuantity,avg(qfr.cbPrice) as cbPrice,sum(qfr.vcOldPriceTotal) as vcOldPriceTotal) from QuoteFabricReport as qfr where qfr.operation='add' and qfr.isCgbj='1' and qfr.isHidden='0' ");
		sql.append(" and qfr.yearMonth = '").append(mouth).append("'");
		sql.append(" and qfr.quoteLocal ='").append(local).append("'");
		if(StringUtils.isNotBlank(vcModelNum)){
			sql.append(" and qfr.vcModelNum='").append(vcModelNum).append("'");
			sql.append(" group by qfr.vcModelNum");
		}else{
			sql.append(" group by qfr.vcModelNum order by sum(qfr.vcQuantity) desc ");
		}
		HibernateTemplate hb = this.getHibernateTemplate();
		hb.setMaxResults(limit);
		return hb.find(sql.toString());
	} 
	
	public Page<MaterialTotal> findSumForPage(Page<MaterialTotal> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Session s = null;
		StringBuffer hql = new StringBuffer("select new map(obj.orderNo as orderNo,obj.contractNo as contractNo,obj.sumMoney as sumMoney,sum(obj.vcOldPriceTotal) as bjTotal,sum(amountrmb) as cbTotal) from QuoteFabricReport as obj where obj.operation='add' ");
		StringBuffer countSql = new StringBuffer("SELECT count(*) AS countId from bym_qf_report de where de.operation='add' ");
		try {
			for (String key : paramap.keySet()) {
				if ("beginDate".equals(key)) {
					hql.append(" and obj.createDate >='").append(paramap.get(key).toString()).append("'");
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')>='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("endDate".equals(key)) {
					hql.append(" and obj.createDate <='").append(paramap.get(key).toString()).append(" 23:59:59'");
					countSql.append(" and DATE_FORMAT(de.createDate,'%Y-%m-%d')<='").append(paramap.get(key).toString()+"'");
					continue;
				}
				if ("contractNo".equals(key)) {
					hql.append(" and obj.contractNo like :contractNo");
					dataMap.put("contractNo", paramap.get(key));
					countSql.append(" and de.contractNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("orderNo".equals(key)) {
					hql.append(" and obj.orderNo like :orderNo");
					dataMap.put("orderNo", paramap.get(key));
					countSql.append(" and de.orderNo like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
				if ("quoteLocal".equals(key)) {
					hql.append(" and obj.quoteLocal='"+paramap.get(key)+"'");
					countSql.append(" and de.quoteLocal = '").append(paramap.get(key).toString()+"'");
					continue;
				}
				
				if ("supplier".equals(key)) {
					hql.append(" and obj.supplier like :supplier");
					dataMap.put("supplier", paramap.get(key));
					countSql.append(" and de.supplier like '%").append(paramap.get(key).toString()+"%'");
					continue;
				}
			}
			hql.append(" and obj.isReplaced='0' ").append(" group by obj.orderNo ");
			countSql.append(" and de.isReplaced='0' ").append(" group by de.orderNo ");
			hql.append(" order by obj.createDate desc ,obj.id desc");
			List<Map<String, Object>> listMap = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			List<MaterialTotal> list = new ArrayList<MaterialTotal>();
			for(Map<String, Object> map : listMap){
				MaterialTotal mt = new MaterialTotal();
				mt.setOrderNo(map.get("orderNo").toString());
				mt.setContractNo(map.get("contractNo").toString());
				mt.setSumMoney(Float.valueOf(map.get("sumMoney").toString()));
				mt.setBjTotal(Float.valueOf(map.get("bjTotal").toString()));
				mt.setCbTotal(Float.valueOf(map.get("cbTotal").toString()));
				mt.setSellProfit(mt.getBjTotal()-mt.getCbTotal());
				if(mt.getBjTotal()==0F){
					mt.setSellProfitRate(0F);
				}else{
					mt.setSellProfitRate(mt.getSellProfit()/mt.getBjTotal());
				}
				
				list.add(mt);
			}
			/*List<MaterialTotal> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);*/
			page.setData(list);
			s = this.getSession();
			String totalCount = s.createSQLQuery(countSql.toString()).list().get(0).toString();
			page.setTotalCount(Integer.valueOf(totalCount));
			//System.out.println("hql=="+hql.toString());
			//System.out.println("countSql=="+countSql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return page;
	}
	
	public void copyQfToQfr(){
		StringBuffer sb = new StringBuffer("select * from bym_quote_fabric where quoteId in (");
		String sql = "SELECT id,quoteId FROM bym_qf_report WHERE operation='add' AND qfId NOT IN (SELECT id FROM bym_quote_fabric AS qf WHERE qf.quoteId=quoteId);";
		Session s = this.getSession();
		List list = s.createSQLQuery(sql).addScalar("id", Hibernate.LONG).addScalar("quoteId", Hibernate.LONG).list();
		if(CollectionUtils.isNotEmpty(list)){
			List<Long> qfrIds =new ArrayList<Long>();
			Set<Long> quoteIds = new HashSet<Long>();
			for(Iterator it = list.iterator();it.hasNext();){
				Object[] o = (Object[])it.next();
				qfrIds.add((Long)o[0]);
				quoteIds.add((Long)o[1]);
			}
			for(Long quoteId:quoteIds){
				sb.append(quoteId).append(",");
			}
			String s1 = sb.substring(0,sb.length()-1);
			s1+=") and id NOT IN (SELECT qfId FROM bym_qf_report AS qfr WHERE qfr.quoteId=quoteId);";
			System.out.println(s1);
			List l = s.createSQLQuery(s1).addEntity(QuoteFabric.class).list();
			this.deletAllEntities(qfrIds);
			for(Iterator t = l.iterator();t.hasNext();){
				QuoteFabric qf = (QuoteFabric)t.next();
				QuoteFabricReport qfr = new QuoteFabricReport();
				Quote q = qf.getQuote();
				DesignerOrder d = designerOrderDao.getUniqueEntityByOneProperty("quoteId", q.getId());
				qfr.setDoId(d==null?0L:d.getId());
				qfr.setHtCode(qf.getHtCode());
				qfr.setIsHidden(qf.getIsHidden());
				qfr.setIsHtCode(qf.getIsHtCode());
				qfr.setIsReplaced(qf.getIsReplaced());
				qfr.setOperation("add");
				qfr.setQfId(qf.getId());
				qfr.setQuoteId(q.getId());
				qfr.setTaxation(q.getContainTax());
				qfr.setVcBefModel(qf.getVcModelNum());
				qfr.setVcFactoryCode(qf.getVcFactoryCode());
				qfr.setVcModelNum(qf.getVcModelNumDisplay());
				qfr.setContractDate(q.getContractDate());
				qfr.setContractNo(q.getContractNo());
				qfr.setCustomerName(q.getVcAttnName());
				qfr.setProjectName(q.getProjectName());
				qfr.setSumMoney(q.getSumMoney());
				qfr.setCreateDate(new Date());
				qfr.setYearMonth(DateUtil.getOnlyYearMonth());
				qfr.setQuoteLocal(q.getVcQuoteLocal());
				qfr.setCbModelNum(qf.getVcFactoryCode()+" "+qf.getVcModelNum());
				qfr.setPriceCur(qf.getPriceCur());
				qfr.setReplaceNO(qf.getReplaceId());
				qfr.setCbPrice(qf.getShijia());
				qfr.setCbQuantity(qf.getVcQuoteNum());
				qfr.setCbPriceUnit(qf.getVcOldPriceUnit());
				float sigMoney = PriceUtil.getTwoDecimalFloat(qf.getSinglePrice() * qf.getVcPurDis());
				if(sigMoney==0){
					float vcPurDis = qf.getVcPurDis()==0?1F:qf.getVcPurDis();
					sigMoney = PriceUtil.getTwoDecimalFloat(qf.getDhjCost() * vcPurDis);
				}
				qfr.setSingleMoney(sigMoney);
				qfr.setOrderNum(qf.getOrderQuantity());
				qfr.setPriceCur(qf.getPriceCur());
				
				Order o = orderDao.getUniqueEntityByPropNames(new String[]{"quoteId","factoryNum"}, new Object[]{q.getId(),qf.getVcFactoryNum()});
				if(o!=null){
					qfr.setOrderNo(o.getOrderNo());
					qfr.setSupplier(o.getSupplier());
					qfr.setBymOrderId(o.getId());
				}
				
				if("1".equals(qf.getQuoteFormate())||"3".equals(qf.getQuoteFormate())){
					qfr.setVcFre(qf.getVcProFre());
				}else{
					qfr.setVcFre(PriceUtil.getTwoDecimalFloat(qf.getVcRetFre()*1.099));
				}
				qfr.setVcSpecialExp(qf.getVcSpecialExp());
				//材料明细表中的报价的单价
				float bjPrice = getVcPrice(q,qf,false,1.099F);
				qfr.setBjPrice(bjPrice);
				qfr.setPriceAdjust(qf.getPriceAdjust());
				qfr.setVcOldPriceTotal(qfr.getBjPrice()*qf.getVcQuantity());
				if("1".equals(qf.getIsReplaced())){
					String str = qf.getReplaceRemark();
					if(str!=null&&str.length()>3){
						String newStr = StringUtils.substring(str, 1, -2);
						qfr.setReplaceNO(newStr);
					}
					qfr.setBjColor(qf.getVcColorNum());
				}else if("1".equals(qf.getIsHidden())){
					qfr.setReplaceNO(qf.getReplaceId());
					qfr.setCbColor(qf.getVcColorNum());
				}else{
					qfr.setBjColor(qf.getVcColorNum());
					qfr.setCbColor(qf.getVcColorNum());
				}
				qfr.setVcOldPrice(qf.getVcOldPrice()*qf.getVcDiscount());
				qfr.setVcPrice(qf.getVcPrice());
				qfr.setVcPriceUnit(qf.getVcPriceUnit());
				qfr.setCbPriceUnit(qf.getVcOldPriceUnit());
				qfr.setVcQuantity(qf.getVcQuantity());
				qfr.setTaxes(q.getTaxes());
				qfr.setVcMoney(qf.getVcMoney());
				qfr.setBjTotal(qf.getVcTotal());
				qfr.setVcFactoryNum(qf.getVcFactoryNum());
				qfr.setVcWidth(qf.getVcWidth());
				qfr.setIsCgbj(qf.getIsCgbj());
				qfr.setBjTotal(qfr.getVcPrice()*qfr.getVcQuantity());
				qfr.setCbTotal(qfr.getCbPrice()*qfr.getCbQuantity());
				//材料明细表===>销售材料成本合计=实订量*实价*汇率
				float huilv = this.getExchangeRate("1", qfr.getPriceCur());
				qfr.setAmountrmb(qfr.getCbTotal()*huilv);
				qfr.setSellProfit(Math.abs(qfr.getVcOldPriceTotal())-Math.abs(qfr.getAmountrmb()));
				if(qfr.getVcOldPriceTotal()!=0){
					qfr.setSellProfitRate(qfr.getSellProfit()/qfr.getVcOldPriceTotal());
				}
				this.saveEntity(qfr);
			}
			
		}
		if(s!=null){
			s.close();
		}
	
	}
	public float getExchangeRate(String quoteFormate,String priceCur){
		
		float vcExchangeRate = 1;
		String defaultPriceCur = "RMB";
		if("1".equals(quoteFormate)) {
			defaultPriceCur = "RMB";
		} else if("2".equals(quoteFormate)) {
			defaultPriceCur = "HKD";
		} else if("3".equals(quoteFormate)) {
			defaultPriceCur = "RMB";
		} else if("4".equals(quoteFormate)) {
			defaultPriceCur = "HKD";
		} else if("5".equals(quoteFormate)) {
			defaultPriceCur = "RMB";
		}
		if(priceCur!=null&&!defaultPriceCur.equalsIgnoreCase(priceCur)) {
			vcExchangeRate = currencyConversionService.getExchangeRate(priceCur,defaultPriceCur);
		}
		return vcExchangeRate;
	}
	private float getVcPrice(Quote q,QuoteFabric qf,boolean flg,float rmb2hkd){
	    //所含税率
	  //  float ctax = q.getContainTax();
		float ctax =1;
	    // 报价折扣
	    float vcdiscount = qf.getVcDiscount();
	    //特殊费用
	    float vcSpecialExp = 0F;
	     if(flg){
	    	vcSpecialExp = qf.getVcSpecialExp();
	    }
	    
	     //单价调整
	     float priceAdjust = qf.getPriceAdjust();
	    
	    //面价
	     float oldPrice = qf.getVcOldPrice();
	    //幅宽
	     float width = qf.getVcWidth();
	    //面价单位
	    String oldPriceUnit = qf.getVcOldPriceUnit();
	    //最终单价单位
	    String vcPriceUnit = qf.getVcPriceUnit();
	 //是否包含运费
	 	String isFre = q.getIsFreight();
	 	//工程运费
	 	 float proFre = 0F;
	 	//大货价大陆运费
		float dhjInlandTransCost =0F;
		//大货价香港运费
	    float dhjHKTransCost = 0F;
		//零售运费
	    float retFre = 0F;
	    if(flg&&"1".endsWith(isFre)){
	    	proFre = qf.getVcProFre();
	    	dhjInlandTransCost = qf.getDhjInlandTransCost();
	    	retFre = qf.getVcRetFre()*rmb2hkd;
	    	dhjHKTransCost = qf.getDhjHKTransCost();
	    }
		//工程报价或零售报价
	   String quoteFormate = q.getQuoteFormate();
	   
		String isCgbj =qf.getIsCgbj();
	   
	   //初始化最终单价为0
	   float price = 0;
	   if("1".equalsIgnoreCase(quoteFormate) || "3".equalsIgnoreCase(quoteFormate) || "5".equalsIgnoreCase(quoteFormate)){
	   		float transCost = ("3".equalsIgnoreCase(quoteFormate) || isCgbj.equalsIgnoreCase("0")) ? dhjInlandTransCost : proFre;
	   		if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("sf")){
	   			price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/10.764*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("sf") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   			price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*10.764*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   			price = PriceUtil.getTwoDecimalFloat(((oldPrice)/0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/width*100*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("m") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   			price = ((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/width*100*(ctax);
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("m") ){
	   			price = PriceUtil.getTwoDecimalFloat(((oldPrice)/0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("m") && vcPriceUnit.equalsIgnoreCase("yd") ){
	   				if("1".equalsIgnoreCase(quoteFormate)){
	   					price = PriceUtil.getTwoDecimalFloat(((oldPrice)*0.914*(vcdiscount)+(transCost)*0.914+(vcSpecialExp)+(priceAdjust))*(ctax));
	   				}else{
	   					price = PriceUtil.getTwoDecimalFloat(((oldPrice)*0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   				}
	   		}else if(oldPriceUnit.equalsIgnoreCase("roll" ) && vcPriceUnit.equalsIgnoreCase("roll") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("m" ) && vcPriceUnit.equalsIgnoreCase("m") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("sf" ) && vcPriceUnit.equalsIgnoreCase("sf") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("yd") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)*0.914+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("m") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(width)/100*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("pc" ) && vcPriceUnit.equalsIgnoreCase("pc") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("set" ) && vcPriceUnit.equalsIgnoreCase("set") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("unit" ) && vcPriceUnit.equalsIgnoreCase("unit") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("pair" ) && vcPriceUnit.equalsIgnoreCase("pair") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}
	   }else{
	   		float transCost = ("4".equalsIgnoreCase(quoteFormate) || isCgbj.equalsIgnoreCase("0")) ? dhjHKTransCost : retFre;
	   		if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("sf") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/10.764*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("sf") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*10.764*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   					price = PriceUtil.getTwoDecimalFloat(((oldPrice)/0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/width*100*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("m") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))/width*100*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("m") ){
		   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)/0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("m") && vcPriceUnit.equalsIgnoreCase("yd") ){
	   				if("2".equalsIgnoreCase(quoteFormate)){
	   					price = PriceUtil.getTwoDecimalFloat(((oldPrice)*0.914*(vcdiscount)+(transCost)*0.914+(vcSpecialExp)+(priceAdjust))*(ctax));
	   				}else{
	   					price = PriceUtil.getTwoDecimalFloat(((oldPrice)*0.914*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   				}
	   				
	   		}else if(oldPriceUnit.equalsIgnoreCase("m") && vcPriceUnit.equalsIgnoreCase("m") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("roll") && vcPriceUnit.equalsIgnoreCase("roll") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("sf") && vcPriceUnit.equalsIgnoreCase("sf") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("㎡") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("yd") && vcPriceUnit.equalsIgnoreCase("yd") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)*0.914+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("㎡") && vcPriceUnit.equalsIgnoreCase("m") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(width)/100*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("pc")  && vcPriceUnit.equalsIgnoreCase("pc") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("set") && vcPriceUnit.equalsIgnoreCase("set") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("unit") && vcPriceUnit.equalsIgnoreCase("unit") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}else if(oldPriceUnit.equalsIgnoreCase("pair")&& vcPriceUnit.equalsIgnoreCase("pair") ){
	   				price = PriceUtil.getTwoDecimalFloat(((oldPrice)*(vcdiscount)+(transCost)+(vcSpecialExp)+(priceAdjust))*(ctax));
	   		}
	   	}
	   	return price;
}
}