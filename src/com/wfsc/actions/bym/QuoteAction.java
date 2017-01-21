package com.wfsc.actions.bym;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.Customer;
import com.wfsc.common.bo.bym.Designer;
import com.wfsc.common.bo.bym.DesignerExpense;
import com.wfsc.common.bo.bym.DesignerOrder;
import com.wfsc.common.bo.bym.Email;
import com.wfsc.common.bo.bym.Fabric;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.Purchase;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.common.bo.bym.QuoteFabric;
import com.wfsc.common.bo.bym.QuoteFabricReport;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.UserGroup;
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.services.bym.service.ICustomerService;
import com.wfsc.services.bym.service.IDesignerExpenseService;
import com.wfsc.services.bym.service.IDesignerOrderService;
import com.wfsc.services.bym.service.IDesignerService;
import com.wfsc.services.bym.service.IEmailService;
import com.wfsc.services.bym.service.IFabricService;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IPurchaseService;
import com.wfsc.services.bym.service.IQuoteFabricReportService;
import com.wfsc.services.bym.service.IQuoteFabricService;
import com.wfsc.services.bym.service.IQuoteService;
import com.wfsc.services.bym.service.IStoreFabricService;
import com.wfsc.services.bym.service.ISupplierService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.PriceUtil;
import com.wfsc.util.ProPrice;
import com.wfsc.util.QuoteFabricUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("QuoteAction")
@Scope("prototype")
public class QuoteAction extends DispatchPagerAction {

	private static final long serialVersionUID = 684081059299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "quote.xls";

	@Resource(name = "quoteService")
	private IQuoteService quoteService;
	@Resource(name = "currencyConversionService")
	private ICurrencyConversionService currencyConversionService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "supplierService")
	private ISupplierService supplierService;
	@Resource(name = "fabricService")
	private IFabricService fabricService;
	@Resource(name = "quoteFabricService")
	private IQuoteFabricService quoteFabricService;
	@Resource(name = "designerExpenseService")
	private IDesignerExpenseService designerExpenseService;
	@Resource(name = "purchaseService")
	private IPurchaseService purchaseService;
	@Resource(name = "orderService")
	private IOrderService orderService;
	@Resource(name = "storeFabricService")
	private IStoreFabricService storeFabricService;
	@Resource(name = "designerOrderService")
	private IDesignerOrderService designerOrderService;
	@Resource(name = "designerService")
	private IDesignerService designerService;
	@Resource(name = "customerService")
	private ICustomerService customerService;
	@Resource(name = "emailService")
	private IEmailService emailService;
	@Resource(name = "quoteFabricReportService")
	private IQuoteFabricReportService quoteFabricReportService;
	
	
	private Quote quote;
	private List<QuoteFabric> quoteFabricList = new ArrayList<QuoteFabric>();
	private DesignerExpense designerExpense;
	private DesignerOrder designerOrder;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "管理员");
		boolean isLocalManager = securityService.isAbleRole(admin.getUsername(), "区域经理");
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isCaiwuManager = securityService.isAbleRole(admin.getUsername(), "财务经理");
		boolean canPrint = true;
		boolean canUpdate = true;
		if(isAdmin){
			isLocalManager = true;
		}
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isLocalManager", isLocalManager);
		request.setAttribute("isSale", isSale);
		Page<Quote> page = new Page<Quote>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		if(!isAdmin){
			paramap.put("vcQuoteLocal", admin.getArea());
		}
		if(isSale&&!isSaleManager){
			paramap.put("saleName", admin.getUsername());
		}
		page = quoteService.findForPage(page, paramap);
		List<Quote> quoteList = page.getData();
		if(quoteList!=null){
			for(Quote q : quoteList){
				if((!"1".equals(q.getVcAudit()))&&(isSale&&!isSaleManager)){
					canPrint = false;
				}
				q.setCanPrint(canPrint);
				canPrint = true;
				if("1".equals(q.getVcAudit())&&"1".equals(q.getIsWritPerm())){
					if(!isAdmin&&!"CQ".equalsIgnoreCase(admin.getUsername())&&!isCaiwuManager){
						canUpdate = false;
					}
				}
				q.setCanUpdate(canUpdate);
				canUpdate = true;
			}
		}
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/quote_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		String isCopy = request.getParameter("isCopy");
		String isView = request.getParameter("isView");
		request.setAttribute("isView", StringUtils.isBlank(isView)?"0":"1");
		String local = "GZ";
		List<UserGroup> salesmanGroup = this.securityService.getAllGroups();
		request.setAttribute("salesmanGroup", salesmanGroup);
		List<Admin> sellmanList = securityService.getUserListByRoleName("销售");
		request.setAttribute("sellmanList", sellmanList);
		float rmb2hkd = this.getExchangeRate("2", "RMB");
		request.setAttribute("rmb2hkd", rmb2hkd);
		List<Customer> cs = customerService.getAll();
		JSONArray json = new JSONArray();
		if(CollectionUtils.isNotEmpty(cs)){
			for(Customer c : cs){
				json.add(c.getCompanyName());
			}
		}
		request.setAttribute("vcattncompany", json.toString());
		if(StringUtils.isNotBlank(id)){
			quote = quoteService.getQuoteById(Long.valueOf(id));
			local = quote.getVcQuoteLocal();
			Set<QuoteFabric> qfSet = quote.getQuoteFabric();
			quoteFabricList = QuoteFabricUtil.sort(qfSet,"getOrderId","asc");
			for(QuoteFabric qf : quoteFabricList){
				qf.setVcTotalStr(cancelGroupingUsed(qf.getVcTotal()));
				if(StringUtils.isBlank(qf.getVcProject())){
					qf.setVcProject(qf.getOrderId()+"");
				}
				Attachment attr = commonService.getOnlyAttachmentByKey(qf.getVcModelNumDisplay()+"_"+quote.getProjectNum()+"_"+qf.getOrderId());
				/*if(attr==null){
					attr = commonService.getOnlyAttachmentByKey(qf.getVcFactoryCode()+"_"+qf.getVcModelNum());
				}*/
				if(attr!=null){
					qf.setFilePath(attr.getAttachPath());
				}
			}
			if("1".equals(isCopy)){
				for(QuoteFabric qf : quoteFabricList){
					qf.setId(null);
				}
				quote.setId(null);
				quote.setCurUserName(this.getCurrentAdminUser().getUsername());
			}
		}else{
			quote = new Quote();
			String quoteNum = quoteService.getQuoteRef(local);
			quote.setCurUserName(this.getCurrentAdminUser().getUsername());
			quote.setVcQuoteNum(quoteNum);
			quote.setVcFormDate(new Date());
			quote.setContainTax(1.08F);
			quote.setSumMoneyRemark("以上报价含国内普通税金8%，含国内运费。");
			quote.setIsFreight("1");
			quote.setProjectLPSellInverse(0F);
			quote.setDesignLPSelllnverse(100F);
			quote.setDesInfo("您好! 很荣幸提供如下报价予贵司，请尽快确认并回覆。谢谢！");
			quote.setVcFormTel("020-83309415");
			quote.setVcFormFax("020-83363021");
			quote.setVcQuoteLocal("GZ");
			quote.setItem("1)付款方式：請贵方先付货款的50%（即人民币：￥此处填写金额元）作为订金，款到敝方帐户后开始订货。貨到广州(貴方可到敝方指定地點驗貨)，一次性付清余款后，敝方安排发貨。@"+" \n"+								
							"2)交貨期：從敝方收到货款订金之日起約2-3周交货；若在訂貨時厂方存货不足，则需4--6 星期造货期，以厂方最終書面確認為准。敝司不承担任何因厂方，运输或贵司引起的延误責任。请贵方预留弹性时间，及早订货较好@"+	" \n"+							
							"3)贵方允許每批訂貨与原板有细微可理解之色差。@"+" \n"+
							"4)如果是皮革或卷訂面料，由于沒有固定的數量， 来货與訂貨數量会有10%的浮動，以來貨數量計算貨款，在餘款里結清。@"+" \n"+
							"5)运费：我司所代收运费均为货到我司运费，如货款低于人民币5,000元，由客户自行到敝司验收、提货，货款高于人民币5000元，我司可免费市区内送货，超出市区我司可协助发货，但运费请由贵方承担。@"+	" \n"+							
							"6)如果某一品牌的數量太少(例如總數少于十米)，則按運輸公司收費規定，運輸費最低消費為600元。@"+" \n"+
							"7)贵方在接到我方的到货通知后，必须在5个工作日内安排验货和提货，逾期贵方需付仓储费，每天仓储费为货款的千分之五。@"+	" \n"+
							"8)以上所有货物，一经订购， 概不退换。 @"+" \n"+
							"9)此报价30日内有效，过期需因应市場变化重新报价。@"+" \n"+
							"10)零剪布匹会有分段的情况，我们不负责分段所引的数量不足问题，订货前请与我们查询以订足够数量。@"+		" \n"+	
							"11）以上为原材料单价，成品单价必须另加损耗成本。@"+" \n"+
							"12）所有条款以打印文字为准，经双方友好协商并签署后生效。合同签署后若因情事变更对合同项下相关问题需作出调整，该调整内容须经双方重新签署方可有效；单方擅自更改或增删条款的，相对人对此不承担责任。@"+" \n"+
							"为维护贵我双方的利益，在签定本报价表时敬请注意：@"+" \n"+
							"1、请贵方务必在本报价表上签字（盖章）并回传我司，作为最终确认。@"+" \n"+
							"2、货款请汇入由我司盖章确认的帐号方为有效，否则我司将概不负责由此引起的一切责任。@");
		}
		quote.setSumMoneyStr(cancelGroupingUsed(quote.getSumMoney()));
		quote.setSubtotalStr(cancelGroupingUsed(quote.getSubtotal()));
		List<Admin> localsellmanList = securityService.getUsersByRoleAndArea("销售", local);
		request.setAttribute("localsellmanList", localsellmanList);
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		String oper = request.getParameter("operate");
		quote = quoteService.getQuoteById(Long.valueOf(id));
		List<UserGroup> salesmanGroup = this.securityService.getAllGroups();
		request.setAttribute("salesmanGroup", salesmanGroup);
		List<Admin> sellmanList = securityService.getUserListByRoleName("销售");
		request.setAttribute("sellmanList", sellmanList);
		if (quote.getQuoteFabric() != null) {
			Set<QuoteFabric> qfSet = quote.getQuoteFabric();
			quoteFabricList = QuoteFabricUtil.sort(qfSet,"getOrderId", "asc");
			for(QuoteFabric qf : quoteFabricList){
				qf.setVcTotalStr(cancelGroupingUsed(qf.getVcTotal()));
			}
		}
		quote.setSumMoneyStr(cancelGroupingUsed(quote.getSumMoney()));
		quote.setSubtotalStr(cancelGroupingUsed(quote.getSubtotal()));
		request.setAttribute("oper", oper);
		return "detail";
	}
	
	/**
	 * 将要删除的报价单及其关联的采购单，订单，库存全部删除
	 * @return
	 */
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idsList = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer("");
		try {
			for(String idstr : idArray){
				if(StringUtils.isEmpty(idstr)){
					 continue;
				 }
				 Long quoteId = new Long(idstr);
				 idsList.add(quoteId);
				 Quote q = this.quoteService.getQuoteById(quoteId);
				 sb.append(q.getProjectNum()).append(",");
				 //生成的采购单订单库存等都要删除
				 List<StoreFabric> sfs = storeFabricService.getStoreFabricByQuoteId(quoteId);
				 if(sfs!=null){
					 storeFabricService.deleteByProperty("quoteId", quoteId);
				 }
				 List<Order> os = orderService.getOrderByPurchaseId(quoteId);
				 if(os!=null){
					 orderService.deleteByProperty("quoteId", quoteId);
				 }
				 Purchase p =  purchaseService.getUniqPurchaseByQuoteId(quoteId);
				 if(p!=null){
					 purchaseService.deleteByProperty("quoteId", quoteId);
				 }
				 if("1".equals(q.getIsWritPerm())){
							//增加一条抵消设计费记录，原来的还保留
							List<DesignerExpense> des = this.designerExpenseService.getDesignerExpenseByQuoteId(quoteId);
							if(CollectionUtils.isNotEmpty(des)){
								DesignerExpense oldDe = des.get(0);
								oldDe.setOperation("old");
								designerExpenseService.saveOrUpdateEntity(oldDe);
								DesignerExpense offsetDe = (DesignerExpense)oldDe.clone();
								offsetDe.setId(null);
								offsetDe.setOperation("offset");
								offsetDe.setSumMoney(-oldDe.getSumMoney());
								offsetDe.setCreateDate(new Date());
								designerExpenseService.saveOrUpdateEntity(offsetDe);
							}
							//设计费处理结束
							//财务设计开始
							List<DesignerOrder> deos = this.designerOrderService.getDesignerOrderByQuoteId(quoteId);
							if(CollectionUtils.isNotEmpty(deos)){
								DesignerOrder oldDeo = deos.get(0);
									oldDeo.setOperation("old");
									designerOrderService.saveOrUpdateEntity(oldDeo);
									DesignerOrder offsetDeo = (DesignerOrder)oldDeo.clone();
									offsetDeo.setSumMoney(-oldDeo.getSumMoney());
									offsetDeo.setHasApplyTotle(-oldDeo.getHasApplyTotle());
									offsetDeo.setRealTotel(-oldDeo.getRealTotel());
									offsetDeo.setSharetotle(-oldDeo.getSharetotle());
									offsetDeo.setBjTotel(-oldDeo.getBjTotel());
									offsetDeo.setCbClTotel(-oldDeo.getCbClTotel());
									offsetDeo.setCbTotel(-oldDeo.getCbTotel());
									offsetDeo.setProfit(-oldDeo.getProfit());
									offsetDeo.setProfitRate(-oldDeo.getProfitRate());
									offsetDeo.setOperation("offset");
									offsetDeo.setCreateDate(new Date());
									offsetDeo.setId(null);
									designerOrderService.saveOrUpdateEntity(offsetDeo);
									
									List<QuoteFabricReport> oldQfrs = this.quoteFabricReportService.getQuoteFabricReportByDoId(oldDeo.getId());
									if(oldQfrs!=null){
										for(QuoteFabricReport dbQfr : oldQfrs){
											//更新旧的
											dbQfr.setOperation("old");
											quoteFabricReportService.saveOrUpdateEntity(dbQfr);
											//新增一条抵消的
											QuoteFabricReport offsetQfr = (QuoteFabricReport)dbQfr.clone();
											offsetQfr.setOperation("offset");
											offsetQfr.setDoId(offsetDeo.getId());
											offsetQfr.setCbTotal(-dbQfr.getCbTotal());
											offsetQfr.setBjTotal(-dbQfr.getBjTotal());
											offsetQfr.setSumMoney(-dbQfr.getSumMoney());
											offsetQfr.setSellProfit(-dbQfr.getSellProfit());
											offsetQfr.setSellProfitRate(-dbQfr.getSellProfitRate());
											offsetQfr.setCreateDate(new Date());
											offsetQfr.setAmountrmb(-dbQfr.getAmountrmb());
											offsetQfr.setId(null);
											quoteFabricReportService.saveOrUpdateEntity(offsetQfr);
										}
									}
									
							}
							//财务设计结束
							
					}
				 }
			String curAdminName = this.getCurrentAdminUser().getUsername();
			quoteService.deleteByIds(idsList);
			saveSystemLog(LogModule.quoteLog, curAdminName+"删除了报价单"+sb.toString());
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	Admin user = getCurrentAdminUser();
	//	SystemLog systemLog = new SystemLog(SystemLog.MODULE_SUPPLIER, user.getUsername(), "删除");
	//	systemLogService.saveSystemLog(systemLog);
		return null;
	}
	
	public String cancelQuote(){
		String id = request.getParameter("id");
		
		try {
			
				 Long quoteId = new Long(id);
				 Quote q = this.quoteService.getQuoteById(quoteId);
				 //生成的采购单订单库存等都要删除
				 List<StoreFabric> sfs = storeFabricService.getStoreFabricByQuoteId(quoteId);
				 if(sfs!=null){
					 storeFabricService.deleteByProperty("quoteId", quoteId);
				 }
				 List<Order> os = orderService.getOrderByPurchaseId(quoteId);
				 if(os!=null){
					 orderService.deleteByProperty("quoteId", quoteId);
				 }
				 Purchase p =  purchaseService.getUniqPurchaseByQuoteId(quoteId);
				 if(p!=null){
					 purchaseService.deleteByProperty("quoteId", quoteId);
				 }
				 if("1".equals(q.getIsWritPerm())){
						//	Quote oldQuote = this.quoteService.getQuoteById(quoteId);
							//增加一条抵消设计费记录，原来的还保留
							List<DesignerExpense> des = this.designerExpenseService.getDesignerExpenseByQuoteId(quoteId);
							if(CollectionUtils.isNotEmpty(des)){
								DesignerExpense oldDe = des.get(0);
								oldDe.setOperation("old");
								designerExpenseService.saveOrUpdateEntity(oldDe);
								DesignerExpense offsetDe = (DesignerExpense)oldDe.clone();
								offsetDe.setId(null);
								offsetDe.setOperation("offset");
								offsetDe.setSumMoney(-oldDe.getSumMoney());
								designerExpenseService.saveOrUpdateEntity(offsetDe);
							}
							//设计费处理结束
							
							//财务设计开始
							List<DesignerOrder> deos = this.designerOrderService.getDesignerOrderByQuoteId(quoteId);
							if(CollectionUtils.isNotEmpty(deos)){
								DesignerOrder oldDeo = deos.get(0);
									oldDeo.setOperation("old");
									designerOrderService.saveOrUpdateEntity(oldDeo);
									DesignerOrder offsetDeo = (DesignerOrder)oldDeo.clone();
									offsetDeo.setSumMoney(-oldDeo.getSumMoney());
									offsetDeo.setHasApplyTotle(-oldDeo.getHasApplyTotle());
									offsetDeo.setRealTotel(-oldDeo.getRealTotel());
									offsetDeo.setSharetotle(-oldDeo.getSharetotle());
									offsetDeo.setBjTotel(-oldDeo.getBjTotel());
									offsetDeo.setCbClTotel(-oldDeo.getCbClTotel());
									offsetDeo.setCbTotel(-oldDeo.getCbTotel());
									offsetDeo.setProfit(-oldDeo.getProfit());
									offsetDeo.setProfitRate(-oldDeo.getProfitRate());
									offsetDeo.setOperation("offset");
									offsetDeo.setCreateDate(new Date());
									offsetDeo.setId(null);
									designerOrderService.saveOrUpdateEntity(offsetDeo);
									
									List<QuoteFabricReport> oldQfrs = this.quoteFabricReportService.getQuoteFabricReportByDoId(oldDeo.getId());
									if(oldQfrs!=null){
										for(QuoteFabricReport oldQfr : oldQfrs){
											//更新旧的
											oldQfr.setOperation("old");
											quoteFabricReportService.saveOrUpdateEntity(oldQfr);
											//新增一条抵消的
											QuoteFabricReport offsetQfr = (QuoteFabricReport)oldQfr.clone();
											offsetQfr.setOperation("offset");
											offsetQfr.setDoId(offsetDeo.getId());
											offsetQfr.setCbTotal(-oldQfr.getCbTotal());
											offsetQfr.setBjTotal(-oldQfr.getBjTotal());
											offsetQfr.setSumMoney(-oldQfr.getSumMoney());
											offsetQfr.setSellProfit(-oldQfr.getSellProfit());
											offsetQfr.setSellProfitRate(-oldQfr.getSellProfitRate());
											offsetQfr.setCreateDate(new Date());
											offsetQfr.setAmountrmb(-oldQfr.getAmountrmb());
											offsetQfr.setId(null);
											quoteFabricReportService.saveOrUpdateEntity(offsetQfr);
										}
									}
									
							}
							//财务设计结束
					}
			q.setIsWritPerm("0");
			q.setVcAudit("0");
			quoteService.saveOrUpdateEntity(q);
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.quoteLog, curAdminName+"作废了报价单"+id);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	Admin user = getCurrentAdminUser();
	//	SystemLog systemLog = new SystemLog(SystemLog.MODULE_SUPPLIER, user.getUsername(), "删除");
	//	systemLogService.saveSystemLog(systemLog);
		return null;
	}
	
	public String save(){
		Admin curAdmin = this.getCurrentAdminUser();
		String addOrUpdate = "提交";
		//float freight = 0F;//运费
		Set<QuoteFabric> qfset = new HashSet<QuoteFabric>();
		for(QuoteFabric qf : quoteFabricList){
			if(qf==null||StringUtils.isBlank(qf.getVcModelNumDisplay())){
				continue;
			}
			qfset.add(qf);
		}
		if(quote.getId()!=null){
			addOrUpdate = "修改";
			List<Long> delQfIds = new ArrayList<Long>();
			Quote oldQuote = this.quoteService.getQuoteById(quote.getId());
			quote.setCreatDate(oldQuote.getCreatDate());//创建时间
			quote.setCurUserName(oldQuote.getCurUserName());//创建人
			Set<QuoteFabric> oldQfSet = oldQuote.getQuoteFabric();
			
			//比较数据库中与当前的qf，数据库中有而当前没有，说明是删除了该qf，那么就要执行删除方法，因为页面上的删除只是dom上的删除
			for(QuoteFabric oldqf : oldQfSet){
				int compareCount = 0;
				for(QuoteFabric newqf : qfset){
					if(newqf==null||StringUtils.isBlank(newqf.getVcModelNumDisplay()))continue;
					if(oldqf.getId().longValue()!=(newqf.getId()==null?-1L:newqf.getId().longValue())){
						++compareCount;
					}
				}
				if(compareCount==qfset.size()){
					delQfIds.add(oldqf.getId());
				}
			}
			this.quoteFabricService.deleteByIds(delQfIds);
		}else{
			quote.setCurUserName(curAdmin.getUsername());//创建人
			quote.setCreatDate(new Date());//创建时间
		}
		//将所有与报价单关联的销售统一放到sales里面保存起来，方便以后查询
		Set<String> sales = new HashSet<String>();
		String salesman = quote.getVcSalesman();
		String salesman1 = quote.getVcSalesman1();
		String salesman2 = quote.getVcSalesman2();
		String salesman3 = quote.getVcSalesman3();
		String salesman4 = quote.getVcSalesman4();
		Long groupId = quote.getGroupId();
		if(StringUtils.isNotBlank(salesman)){
			sales.add(salesman);
		}
		if(StringUtils.isNotBlank(salesman1)){
			sales.add(salesman1);
		}
		if(StringUtils.isNotBlank(salesman2)){
			sales.add(salesman2);
		}
		if(StringUtils.isNotBlank(salesman3)){
			sales.add(salesman3);
		}
		if(StringUtils.isNotBlank(salesman4)){
			sales.add(salesman4);
		}
		List<Admin> admins = this.securityService.getUserListByGroupId(groupId);
		if(admins!=null){
			for(Admin a : admins){
				sales.add(a.getUsername());
			}
		}
		quote.setSalesman(sales);
		quote.setModifyUser(curAdmin.getUsername());
		quote.setVcFormDate(new Date());
		quote.setVcYearMonth(DateUtil.getYear(new Date())+""+String.format("%02d", DateUtil.getMonth(new Date())));
		quote.setVcAudit("0");
		quote.setIsWritPerm("0");
		for(QuoteFabric qf : qfset){
			if(qf==null||StringUtils.isBlank(qf.getVcModelNumDisplay())){
				continue;
			}
			qf.setQuoteNum(quote.getProjectNum());
			qf.setQuote(quote);
			float sigMoney = PriceUtil.getTwoDecimalFloat(qf.getSinglePrice() * qf.getVcPurDis());
			if(sigMoney==0){
				float vcPurDis = qf.getVcPurDis()==0?1F:qf.getVcPurDis();
				sigMoney = PriceUtil.getTwoDecimalFloat(qf.getDhjCost() * vcPurDis);
			}
			qf.setSigMoney(sigMoney);
		}
		quote.setQuoteFabric(qfset);
		quoteService.saveOrUpdateEntity(quote);
		
		List<Admin> saleManegers = this.securityService.getUsersByRoleAndArea("销售经理", curAdmin.getArea());
		if(CollectionUtils.isNotEmpty(saleManegers)){
			for(Admin admin : saleManegers){
				Email e = new Email();
				e.setAction("quote");
				e.setDetail("关于【" + quote.getProjectName() + "】的报价已经"+addOrUpdate+"！报价单号为"+quote.getProjectNum()+"，请审核");
				e.setQuoteId(quote.getId());
				e.setQuoteNo(quote.getProjectNum());
				e.setSender(curAdmin.getUsername());
				e.setSendTime(new Date());
				e.setState("1");
				e.setUsername(admin.getUsername());
				e.setStatus("1");
				this.emailService.saveOrUpdateEntity(e);
			}
		}
		List<Admin> quoteer = this.securityService.getUsersByRoleAndArea("报价审核员", curAdmin.getArea());
		if(CollectionUtils.isNotEmpty(quoteer)){
			for(Admin admin : quoteer){
				Email e = new Email();
				e.setAction("quote");
				e.setDetail("关于【" + quote.getProjectName() + "】的报价已经"+addOrUpdate+"！报价单号为"+quote.getProjectNum()+"，请审核");
				e.setQuoteId(quote.getId());
				e.setQuoteNo(quote.getProjectNum());
				e.setSender(curAdmin.getUsername());
				e.setSendTime(new Date());
				e.setState("1");
				e.setUsername(admin.getUsername());
				e.setStatus("1");
				this.emailService.saveOrUpdateEntity(e);
			}
		}
		Set<String> salenames = quote.getSalesman();
		if(CollectionUtils.isNotEmpty(salenames)){
			for(String name : salenames){
				Email e = new Email();
				e.setAction("quote");
				e.setDetail("关于【" + quote.getProjectName() + "】的报价已经"+addOrUpdate+"！报价单号为"+quote.getProjectNum());
				e.setQuoteId(quote.getId());
				e.setQuoteNo(quote.getProjectNum());
				e.setSender(curAdmin.getUsername());
				e.setSendTime(new Date());
				e.setState("1");
				e.setStatus("0");
				e.setUsername(name);
				this.emailService.saveOrUpdateEntity(e);
			}
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.quoteLog, curAdminName+addOrUpdate+"了报价单"+quote.getProjectNum());
		return "ok";
	}
	
	public String toImport(){
		String quoteId = request.getParameter("quoteId");
		String vcModelNumDisplay = request.getParameter("vcModelNumDisplay");
		String vcCount = request.getParameter("vcCount");
		String projectNum = request.getParameter("projectNum");
		request.setAttribute("quoteId", quoteId);
		request.setAttribute("vcModelNumDisplay", vcModelNumDisplay);
		request.setAttribute("vcCount", vcCount);
		request.setAttribute("projectNum", projectNum);
		
		return "toImport";
	}
	
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String projectName = request.getParameter("projectName");
		String vcSalesman = request.getParameter("vcSalesman");
		String projectNum = request.getParameter("projectNum");
		String vcAttn = request.getParameter("vcAttn");
		String quoteFormate = request.getParameter("quoteFormate");
		String projectDesignComp = request.getParameter("projectDesignComp");
		String vcBefModel = request.getParameter("vcBefModel");
		String curUserName = request.getParameter("curUserName");
		String isWritPerm = request.getParameter("isWritPerm");
		String htCode = request.getParameter("htCode");
		String vcAudit = request.getParameter("vcAudit");
		String projectType = request.getParameter("projectType");
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime);
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime);
			request.setAttribute("endTime", endTime);
		}
		if(StringUtils.isNotEmpty(projectName)){
			paramap.put("projectName", projectName);
			request.setAttribute("projectName", projectName);
		}
		if(StringUtils.isNotEmpty(vcSalesman)){
			paramap.put("vcSalesman", vcSalesman);
			request.setAttribute("vcSalesman", vcSalesman);
		}
		if(StringUtils.isNotEmpty(projectNum)){
			paramap.put("projectNum", projectNum);
			request.setAttribute("projectNum", projectNum);
		}
		if(StringUtils.isNotEmpty(vcAttn)){
			paramap.put("vcAttn", vcAttn);
			request.setAttribute("vcAttn", vcAttn);
		}
		if(StringUtils.isNotEmpty(quoteFormate)){
			paramap.put("quoteFormate", quoteFormate);
			request.setAttribute("quoteFormate", quoteFormate);
		}
		if(StringUtils.isNotEmpty(projectDesignComp)){
			paramap.put("projectDesignComp", projectDesignComp);
			request.setAttribute("projectDesignComp", projectDesignComp);
		}
		if(StringUtils.isNotEmpty(vcBefModel)){
			paramap.put("vcBefModel", vcBefModel);
			request.setAttribute("vcBefModel", vcBefModel);
		}
		if(StringUtils.isNotEmpty(curUserName)){
			paramap.put("curUserName", curUserName);
			request.setAttribute("curUserName", curUserName);
		}
		if(StringUtils.isNotEmpty(isWritPerm)){
			paramap.put("isWritPerm", isWritPerm);
			request.setAttribute("isWritPerm", isWritPerm);
		}
		if(StringUtils.isNotEmpty(htCode)){
			paramap.put("htCode", htCode);
			request.setAttribute("htCode", htCode);
		}
		if(StringUtils.isNotEmpty(vcAudit)){
			paramap.put("vcAudit", vcAudit);
			request.setAttribute("vcAudit", vcAudit);
		}
		if(StringUtils.isNotEmpty(projectType)){
			paramap.put("projectType", projectType);
			request.setAttribute("projectType", projectType);
		}
		
		return paramap;
	}
	
	/**
	 * 获取其它货币对RMB或HKD的汇率
	 */
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
		if(!defaultPriceCur.equals(priceCur.toUpperCase())) {
			vcExchangeRate = currencyConversionService.getExchangeRate(priceCur,defaultPriceCur);
		}
		return vcExchangeRate;
	}
	
	public String getSalesByArea(){
		response.setContentType("text/html;charset=utf-8");
		String area = request.getParameter("area");
		List<Admin> list = this.securityService.getUsersByRoleAndArea("销售", area);
		JSONArray jsonArray = new JSONArray();
		for(Admin a : list){
			JSONObject json = new JSONObject();
			json.put("username", a.getUsername());
			jsonArray.add(json);
		}
		try {
			response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getRefNo(){
		String area = request.getParameter("area");
		String quoteNum = quoteService.getQuoteRef(area);
		try {
			response.getWriter().write(quoteNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getSellPhone(){
		response.setContentType("text/html;charset=utf-8");
		String sellname = request.getParameter("sellname");
		Admin admin = this.securityService.getUserWithPermissionByName(sellname);
		JSONObject json = new JSONObject();
		if(admin!=null){
			json.put("phone", admin.getPhone());
			json.put("zhname", admin.getZhname());
		}
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isNumeric(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	 }
	
	public String ajaxQuoteFabric() {
		response.setContentType("text/html;charset=utf-8");
		// 税率
		float ctax = 1.08f;
		String ctaxStr = request.getParameter("ctax");
		if (StringUtils.isNotBlank(ctaxStr)) {
			ctax = Float.valueOf(ctaxStr);
		}
		// 报价方式
		String quoteFormate = request.getParameter("quoteFormate");
		// 是否含运费
		String isFreight = request.getParameter("isFreight");
		// 已选择的产品的数量，为了计算后面新选择的产品的序号（vcIndex）
		String trNumberStr = request.getParameter("trNumber");
		int trNumber = 0;
		if(StringUtils.isNotBlank(trNumberStr)){
			trNumber = Integer.valueOf(trNumberStr).intValue();
		}
		// 人民币转港币的汇率，在香港报价的时候用到
		float rmb2hkd = this.getExchangeRate("2", "RMB");
		// 选择的产品id
		String fbids = request.getParameter("fbids");
		List<Fabric> fbList = new ArrayList<Fabric>();
		String[] fbidArray = fbids.split(",");
		for (String id : fbidArray) {
			if(!isNumeric(id)){
				continue;
			}
			Fabric fb = this.fabricService.getFabricById(Long.valueOf(id));
			if(fb!=null){
				fbList.add(fb);
			}
		}
		List<QuoteFabric> qfList = new ArrayList<QuoteFabric>();
		for (Fabric f : fbList) {
			QuoteFabric qf = new QuoteFabric();
			qf.setVcDis(f.getVcDis());
			String isHtCode = f.getIsHtCode();
			String htCode = f.getHtCode();
			qf.setIsHtCode(isHtCode);
			qf.setHtCode(htCode);
			//品牌
			String brand = f.getBrand();
			//内地上调系数
			float raiseRate = f.getInlandRaiseRate();
			//内地下调系数
			float downRate = f.getInlandDownRate();
			//内地加工系数
			float refitRate = f.getInlandRefitRate();
			//香港上调系数
			float hkRaiseRate = f.getHkRaiseRate();
			//香港下调系数
			float hkDownRate = f.getHkDownRate();
			//香港加工系数
			float hkRefitRate = f.getHkRefitRate();
			qf.setVcColorNum(f.getColorCode());// 色号
			if (f.getIsHtCode().equals("1")) {
				f = fabricService.getFabricByCode(f.getVcFactoryCode(), f.getVcBefModel());
				if (f == null)
					continue;
			}
			
			qf.setVcIndex(trNumber++);
			qf.setOrderId(qf.getVcIndex()+1);
			qf.setVcProject(qf.getOrderId()+"");
			// 工厂编号
			qf.setVcFactoryCode(f.getVcFactoryCode());
			// 原厂型号
			qf.setVcModelNum(f.getVcBefModel());
			qf.setVcComposition(f.getVcComposition());
			// 最终单价单位（将最终单价单位设置成采购时单价的单位）
			qf.setVcPriceUnit(f.getVcMeasure() == null ? "" : f.getVcMeasure().trim().toLowerCase());
			// 进价货币
			String priceCur = f.getVcPriceCur();
			qf.setPriceCur(priceCur);
			// 进价货币对RMB或港币的汇率，根据报价方式决定
			float vcExchangeRate = this.getExchangeRate(quoteFormate, priceCur);
			// 进价货币对RMB的汇率
			float other2rmb = this.getExchangeRate("1", priceCur);
			qf.setExchangeRate(other2rmb);
			// 原始进价
			qf.setSinglePrice(f.getVcOldPrice());
			// 进价时的折扣
			qf.setVcPurDis(f.getVcPurDis());
			// 设置报价布匹是工程报价还是零售报价
			qf.setQuoteFormate(quoteFormate);
			// 设置产品备注二
			qf.setRemark2(f.getVcRemark2());

			// 进价时的单位
			String measure = f.getVcMeasure().toLowerCase();
			// 设置默认客户提供的单位
			qf.setCustomerUnit(measure);
			// 设置工程运费
			qf.setVcProFre(f.getVcProFre());
			// 设置零售运费
			qf.setVcRetFre(f.getVcRetFre());
			// 幅宽
			qf.setVcWidth(f.getVcWidth());

			// 设置大货价
			qf.setDhjHKTransCost(f.getDhjHKTransCost());
			qf.setDhjInlandTransCost(f.getDhjInlandTransCost());
			qf.setDhjCost(f.getDhjCost());
			qf.setDhjWidth(f.getDhjWidth());
			qf.setDhjHKRate(f.getDhjHKRate());
			qf.setDhjInlandRate(f.getDhjInlandRate());
			// 面价单位
			qf.setVcOldPriceUnit(measure);
			Supplier s = supplierService.getSupplierByCode(f.getVcFactoryCode());
			if (s != null) {
				qf.setVcProduceLocal(s.getVcPlaceProduce());
				qf.setVcFactoryNum(s.getVcFactoryNum());
				if ("1".equals(quoteFormate) || "3".equals(quoteFormate)|| "5".equals(quoteFormate)) {// 内地报价，取供应商内地运费、最低运费
					qf.setFreight(s.getHomeTransportCost() == null ? "" : s.getHomeTransportCost()+ "");
					qf.setLowFreight(s.getHomeLowTransportCost() + "");
				} else if ("2".equals(quoteFormate) || "4".equals(quoteFormate)) {
					qf.setFreight(s.getHkTransportCost() == null ? "" : s.getHkTransportCost()+ "");
					qf.setLowFreight(s.getHkLowTransportCost() + "");
				}
				qf.setBrandAttri(s.getBrandAttri() == null ? "" : s.getBrandAttri());
				qf.setProductRange(s.getProductRange() == null ? "" : s.getProductRange());
				qf.setMOQ(s.getMOQ() == null ? "" : s.getMOQ());
				qf.setLeastIncrement(s.getLeastIncrement() == null ? "" : s.getLeastIncrement());
			}
			// 特殊费用 手动填写 默认为0
			qf.setVcSpecialExp(0F);
			// 数量 手动填写 默认为0
			qf.setVcQuantity(0F);
			// 起订量
			qf.setVcLeastNum(f.getVcMinNum());
			// 销售折扣，这里不同于采购折扣
			qf.setVcDiscount(1F);
			// 备注
			qf.setVcRmk(f.getVcRemark1());
			qf.setIsHidden("0");// 设置该型号是否为隐形型号，默认不是
			qf.setIsReplaced("0");// 设置该型号是否为被替代的型号，默认不是
			qf.setConversionQuantity(0F);
			qf.setCustomerQuantity(0F);
			qf.setOrderQuantity(0F);
			qf.setQfId(0L);
			qf.setIsHiddenisReplaced("");
			// 面价
			float oldPrice = 0F;
			// 最终单价
			float vcPrice = 0F;
			qf.setIsCgbj("1");
			if ("1".equals(quoteFormate)) {// 内地报价
				//计算公式：面价= 原始进价*采购折扣*汇率*工程系数
				oldPrice = PriceUtil.getCommonFacePrice(f.getVcOldPrice(), f.getVcPurDis(), vcExchangeRate, f.getVcProRatio());
				//HT型号品牌不为空的话需要在原有价格上算二次面价
				if("1".endsWith(isHtCode)&&StringUtils.isNotBlank(brand)){
					oldPrice = PriceUtil.getSecondInlandFacePrice(oldPrice, brand, raiseRate, downRate, refitRate);
				}
				if ("yd".equalsIgnoreCase(measure.trim())) {
					vcPrice = PriceUtil.getYDProjectFinalPrice(oldPrice, 1, f.getVcProFre(), 0, ctax, isFreight);
				}else{
					vcPrice = PriceUtil.getCommonProjectFinalPrice(oldPrice, 1, f.getVcProFre(), 0, ctax, isFreight);
				}
				qf.setVcMoney("RMB");
			} else if ("2".equals(quoteFormate)) {//香港报价
				oldPrice = PriceUtil.getCommonFacePrice(f.getVcOldPrice(), f.getVcPurDis(), vcExchangeRate, f.getVcRetailRatio());
				//香港报价国产厂
				if("0".equals(f.getImportFactory())){
					oldPrice = PriceUtil.getHKDomesticFacePrice(f.getVcOldPrice(), f.getVcPurDis(), other2rmb, vcExchangeRate);
				}
				//HT型号品牌不为空的话需要在原有价格上算二次面价
				if("1".equals(isHtCode)&&StringUtils.isNotBlank(brand)){
					oldPrice = PriceUtil.getSecondHKFacePrice(oldPrice, brand, hkRaiseRate, hkDownRate, hkRefitRate);
				}
				if ("yd".equalsIgnoreCase(measure.trim())) {
					vcPrice = PriceUtil.getYDDistributionFinalPrice(oldPrice, 1, f.getVcRetFre(), 0, ctax, rmb2hkd, isFreight);
				}else{
					vcPrice = PriceUtil.getCommonDistributionFinalPrice(oldPrice, 1, f.getVcRetFre(), 0, ctax, rmb2hkd, isFreight);
				}
				qf.setVcMoney("HKD");
			} else if ("3".equals(quoteFormate)) {//大货价内地报价
				oldPrice = PriceUtil.getCommonFacePrice(f.getDhjCost(), f.getVcPurDis(), vcExchangeRate, f.getDhjInlandRate());
				if ("yd".equalsIgnoreCase(measure.trim())) {
					vcPrice = PriceUtil.getYDProjectFinalPrice(oldPrice, 1, f.getDhjInlandTransCost(), 0, ctax, isFreight);
				}else{
					vcPrice = PriceUtil.getCommonProjectFinalPrice(oldPrice, 1, f.getDhjInlandTransCost(), 0, ctax, isFreight);
				}
				qf.setIsCgbj("0");
				
				qf.setVcMoney("RMB");
				qf.setVcWidth(qf.getDhjWidth());
			} else if ("4".equals(quoteFormate)) {//大货价香港报价
				oldPrice = PriceUtil.getCommonFacePrice(f.getDhjCost(), f.getVcPurDis(), vcExchangeRate, f.getDhjHKRate());
				if ("yd".equalsIgnoreCase(measure.trim())) {
					vcPrice = PriceUtil.getYDDistributionFinalPrice(oldPrice, 1, f.getDhjHKTransCost(), 0, ctax, rmb2hkd, isFreight);
				}else{
					vcPrice = PriceUtil.getCommonDistributionFinalPrice(oldPrice, 1, f.getDhjHKTransCost(), 0, ctax, rmb2hkd, isFreight);
				}
				qf.setIsCgbj("0");
				qf.setVcMoney("HKD");
				qf.setVcWidth(qf.getDhjWidth());
			} else if ("5".equals(quoteFormate)) {// 零售报价
				oldPrice = PriceUtil.getRetailFacePrice(f.getVcOldPrice(), f.getVcPurDis(), vcExchangeRate, f.getVcProRatio(), s.getRetailCoefficient());
				if ("yd".equalsIgnoreCase(measure.trim())) {
					vcPrice = PriceUtil.getYDProjectFinalPrice(oldPrice, 1, f.getVcProFre(), 0, ctax, isFreight);
				}else{
					vcPrice = PriceUtil.getCommonProjectFinalPrice(oldPrice, 1, f.getVcProFre(), 0, ctax, isFreight);
				}
				qf.setVcMoney("RMB");
			}
			// 设置面价
			qf.setVcOldPrice(Math.round(oldPrice));
			// 设置最终单价
			qf.setVcPrice(Math.round(vcPrice));
			
			// 大货价大陆报价
			float dhjVcOldPrice = 0F;
			float dhjVcFinalPrice = 0F;
			if (f.getDhjCost() > 0) {
				dhjVcOldPrice = PriceUtil.getCommonFacePrice(f.getDhjCost(), f.getVcPurDis(), vcExchangeRate, f.getDhjInlandRate());
				if ("yd".equalsIgnoreCase(measure.trim())) {
					dhjVcFinalPrice = PriceUtil.getYDProjectFinalPrice(dhjVcOldPrice, 1, f.getDhjInlandTransCost(), 0, ctax, isFreight);
				}else{
					dhjVcFinalPrice = PriceUtil.getCommonProjectFinalPrice(dhjVcOldPrice, 1, f.getDhjInlandTransCost(), 0, ctax, isFreight);
				}
				qf.setDhjVcOldPrice(Math.round(dhjVcOldPrice));
				qf.setDhjFinalOldPrice(Math.round(dhjVcFinalPrice));
			}
			
			// 大货价香港报价
			float dhjHKVcPrice = 0F;
			float dhjHKFinalPrice = 0F;
			if (f.getDhjCost() > 0) {
				dhjHKVcPrice = PriceUtil.getCommonFacePrice(f.getDhjCost(), f.getVcPurDis(), vcExchangeRate, f.getDhjHKRate());
				if ("yd".equalsIgnoreCase(measure.trim())) {
					dhjHKFinalPrice = PriceUtil.getYDDistributionFinalPrice(dhjHKVcPrice, 1, f.getDhjHKTransCost(), 0, ctax, rmb2hkd, isFreight);
				}else{
					dhjHKFinalPrice = PriceUtil.getCommonDistributionFinalPrice(dhjHKVcPrice, 1, f.getDhjHKTransCost(), 0, ctax, rmb2hkd, isFreight);
				}
				qf.setDhjHKVcPrice(Math.round(dhjHKVcPrice));
				qf.setDhjHKFinalPrice(Math.round(dhjHKFinalPrice));
			}
			if("1".equals(isHtCode)){
				qf.setVcModelNumDisplay(htCode);
			}else{
				qf.setVcModelNumDisplay(qf.getVcFactoryCode()+" "+qf.getVcModelNum());
			}
			qfList.add(qf);
		}
		request.setAttribute("qfList", qfList);
		return "appendquoteFabric";
	}
	public void downloadQuote() throws Exception{
		String id = request.getParameter("id");
		Quote entity = quoteService.getQuoteById(Long.valueOf(id));
		String quoteFormate = entity.getQuoteFormate();
		String quoteFormateFlag = entity.getQuoteFormate();
	//	String fileName = "quote.xls";
		String header = request.getParameter("header");
		String imgUrl = "";
		if("1".equals(header)){
			imgUrl = "/images/title1.jpg";//瀚姆特斯HT（二）
		}else if("2".equals(header)){
			imgUrl = "/images/title.jpg";//SONKON CHINA（三）
		}else if("3".equals(header)){
			imgUrl = "/images/title3.jpg";//协诚洋行（第一顺序）
		}else{
			imgUrl = "/images/title3.jpg";
		}
		 BufferedImage bufferImg = null;  
		 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
         bufferImg = ImageIO.read(new File(request.getRealPath(imgUrl)));     
         ImageIO.write(bufferImg, "jpg", byteArrayOut); 
         String fileUrl = "/model/quote1.xls";
         if("2".equals(quoteFormate)||"4".equals(quoteFormate)){
        	 fileUrl = "/model/quote2.xls";
         }
		 FileInputStream in = new FileInputStream(request.getRealPath(fileUrl));
         HSSFWorkbook book = new HSSFWorkbook(in);
         HSSFSheet sheet = book.getSheetAt(0);
         HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
         HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 715, 120,(short) 0, 0, (short) 6, 6);
         anchor.setAnchorType(3); 
         patriarch.createPicture(anchor, book.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));   
        //坐标从0开始
        int startRow = 7;
        HSSFRow row8 = sheet.getRow(startRow);
        if(row8!=null){
        	HSSFCell cell2 = row8.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVcAttn());
        	}
        	HSSFCell cell4 = row8.getCell(3);
        	if(cell4!=null){
        		cell4.setCellValue(entity.getVcAttnTel());
        	}
        	HSSFCell cell6 = row8.getCell(6);
        	if(cell6!=null){        
        		cell6.setCellValue(entity.getVcAttnFax());
        	}
        }
        HSSFRow row9 = sheet.getRow(startRow+1);
        if(row9!=null){
        	HSSFCell cell2 = row9.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVcAttnName());
        	}
        	HSSFCell cell4 = row9.getCell(3);
        	if(cell4!=null){
        		cell4.setCellValue(entity.getVcAttnPhone());
        	}
        	HSSFCell cell6 = row9.getCell(6);
        	if(cell6!=null){
        		cell6.setCellValue(entity.getVcAttnEmail());
        	}
        }
        HSSFRow row10 = sheet.getRow(startRow+2);
        if(row10!=null){
        	HSSFCell cell2 = row10.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVcFrom());
        	}
        	HSSFCell cell4 = row10.getCell(3);
        	if(cell4!=null){
        		cell4.setCellValue(entity.getVcFormTel());
        	}
        	HSSFCell cell6 = row10.getCell(6);
        	if(cell6!=null){
        		cell6.setCellValue(entity.getVcFormFax());
        	}
        }
        HSSFRow row11 = sheet.getRow(startRow+3);
        if(row11!=null){
        	HSSFCell cell2 = row11.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVcFormName());
        	}
        	HSSFCell cell4 = row11.getCell(3);
        	if(cell4!=null){
        		cell4.setCellValue(entity.getVcFormPhone());
        	}
        	HSSFCell cell6 = row11.getCell(6);
        	if(cell6!=null){
        		String dateStr = DateUtil.getShortDate(entity.getVcFormDate());
        		cell6.setCellValue(dateStr);
        	}
        }
        HSSFRow row12 = sheet.getRow(startRow+4);
        if(row12!=null){
        	HSSFCell cell2 = row12.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVcAttnName());
        	}
        	HSSFCell cell4 = row12.getCell(3);
        	if(cell4!=null){
        		
        		String quoteFormateDis = "";
        		if("1".equals(quoteFormate)){
        			//quoteFormateDis="内地报价";
        		}else{
        			//quoteFormateDis="香港报价";
        		}
        		cell4.setCellValue(quoteFormateDis);
        	}
        	HSSFCell cell6 = row12.getCell(6);
        	if(cell6!=null){
        		cell6.setCellValue(entity.getProjectNum());
        	}
        }
        HSSFRow row14 = sheet.getRow(startRow+6);
        if(row14!=null){
        	HSSFCell cell2 = row14.getCell(1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getProjectName());
        	}
        	if(!"2".equals(quoteFormateFlag)){
//        		HSSFCellStyle  style = cell4.getCellStyle();
//            	HSSFCell cell5 = row14.createCell(4);
//            	cell5.setCellStyle(style);
//            	cell5.setCellValue("Trade terms");
        		HSSFCell cell4 = row14.getCell(3);
            	if(cell4!=null){
            		cell4.setCellValue(entity.getProjectLocalPreson());
            	}
        	}
        }
        HSSFRow row15 = sheet.getRow(startRow+7);
        if(row15!=null){
        	if("2".equals(quoteFormateFlag)){
//        		HSSFCellStyle  style = cell4.getCellStyle();
//            	HSSFCell cell5 = row15.createCell(4);
//            	cell5.setCellStyle(style);
//            	cell5.setCellValue("ExWorks Hong Kong");
        		HSSFCell cell2 = row15.getCell(1);
            	if(cell2!=null){
            		cell2.setCellValue(entity.getDesignLocalPreson());
            	}
        	} else {
        		HSSFCell cell2 = row15.getCell(1);
            	if(cell2!=null){
            		cell2.setCellValue(entity.getProjectDesignComp());
            	}
            	HSSFCell cell4 = row15.getCell(3);
            	if(cell4!=null){
            		cell4.setCellValue(entity.getDesignLocalPreson());
            	}
        	}
        }

        HSSFRow row16 = sheet.getRow(startRow+8);
        if(row16 != null) {
        	HSSFCell cell1 = row16.getCell(0);
        	
        	HSSFFont font = (HSSFFont) book.createFont();
        	font.setFontName("宋体"); // 字体
        	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
        	font.setFontHeightInPoints((short) 16); // 字体高度
        	
    		HSSFCellStyle style = book.createCellStyle();     
    		style.setFont(font); 
    		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中      
    		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
    		
    		if(cell1 != null) {
    			cell1.setCellStyle(style);
        		if("0".equals(entity.getFabricTitle())) {
        			if("2".equals(quoteFormateFlag)){
        				cell1.setCellValue("Quotation 報價表");
        			} else{
        				cell1.setCellValue("Quotation 报价表");
        			}
        		} else if("1".equals(entity.getFabricTitle())) {
        			cell1.setCellValue("Confirmation 合 同");
        		}else{
        			cell1.setCellValue("INVOICE");
        		}
        		
        	}
        }
        List<QuoteFabric> qfList = QuoteFabricUtil.sort(entity.getQuoteFabric(), "getOrderId", "asc");
        int rows = qfList.size();
        int shifStartRow = 17;
        if("2".equals(quoteFormateFlag)) {
        	shifStartRow = 16;
        }
        sheet.shiftRows(shifStartRow,  sheet.getLastRowNum(), rows, true, false);
        sheet.shiftRows(shifStartRow + rows,  sheet.getLastRowNum(), 1, true, false);
        sheet.shiftRows(shifStartRow + rows + 1,  sheet.getLastRowNum(), 1, true, false);
        sheet.shiftRows(shifStartRow + rows + 2,  sheet.getLastRowNum(), 1, true, false);
        
        int rowcount = 0;
        if(entity.getTitleCol1() != null && !"".equals(entity.getTitleCol1())) {
        	rowcount ++;
        	sheet.shiftRows(shifStartRow + rows + 2 + rowcount,  sheet.getLastRowNum(), 1, true, false);
        }
        if(entity.getTitleCol2() != null && !"".equals(entity.getTitleCol2())) {
        	rowcount ++;
        	sheet.shiftRows(shifStartRow + rows + 2 + rowcount,  sheet.getLastRowNum(), 1, true, false);
        }
        if(entity.getTitleCol3() != null && !"".equals(entity.getTitleCol3())) {
        	rowcount ++;
        	sheet.shiftRows(shifStartRow + rows + 2 + rowcount,  sheet.getLastRowNum(), 1, true, false);
        }
        if(entity.getTitleCol4() != null && !"".equals(entity.getTitleCol4())) {
        	rowcount ++;
        	sheet.shiftRows(shifStartRow + rows + 2 + rowcount,  sheet.getLastRowNum(), 1, true, false);
        }
        if(entity.getTitleCol5() != null && !"".equals(entity.getTitleCol5())) {
        	rowcount ++;
        	sheet.shiftRows(shifStartRow + rows + 2 + rowcount,  sheet.getLastRowNum(), 1, true, false);
        }
        
        short mergeCol = 7;
        if("2".equals(quoteFormateFlag)) {
        	mergeCol = 6;
        }
        
        HSSFCellStyle style = book.createCellStyle();     
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中      
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
		style.setBorderTop((short)1);
		style.setBorderBottom((short)1);
		style.setBorderLeft((short)1);
		style.setBorderRight((short)1);
		
		HSSFRow row = sheet.getRow(shifStartRow);
		this.createCell(row, 9);
		row.getCell(0).setCellStyle(style);
		row.getCell(1).setCellStyle(style);
		row.getCell(2).setCellStyle(style);
		row.getCell(3).setCellStyle(style);
		row.getCell(4).setCellStyle(style);
		row.getCell(5).setCellStyle(style);
		row.getCell(6).setCellStyle(style);
		row.getCell(0).setCellValue("Items");
		row.getCell(1).setCellValue("Description");
		row.getCell(2).setCellValue("Model number");
		row.getCell(3).setCellValue("color");
		row.getCell(4).setCellValue("width");
		row.getCell(5).setCellValue("Unit price");
		row.getCell(6).setCellValue("Quantity");

		if("2".equals(quoteFormateFlag)) {
			HSSFFont font = (HSSFFont) book.createFont();
        	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
        	
			HSSFCellStyle style1 = book.createCellStyle();     
			style1.setFont(font);
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中      
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
			style1.setBorderTop((short)1);
			style1.setBorderBottom((short)1);
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			
			row.getCell(7).setCellValue("Total");
			row.getCell(0).setCellStyle(style1);
			row.getCell(1).setCellStyle(style1);
			row.getCell(2).setCellStyle(style1);
			row.getCell(3).setCellStyle(style1);
			row.getCell(4).setCellStyle(style1);
			row.getCell(5).setCellStyle(style1);
			row.getCell(6).setCellStyle(style1);
			row.getCell(6).setCellStyle(style1);
		} else {
			row.getCell(7).setCellStyle(style);
			row.getCell(7).setCellValue("Note");
			row.getCell(8).setCellStyle(style);
			row.getCell(8).setCellValue("Total");
		}
        
      //  HSSFCellStyle rowStyle = row.getRowStyle();
        for(int i = 0; i < rows; i++){
        	HSSFRow r = sheet.createRow(shifStartRow+i+1);
        //	r.setRowStyle(rowStyle);
        	HSSFCell c1 = r.createCell(0);
        	c1.setCellStyle(style);
        	String project = qfList.get(i).getVcProject();
        	if(StringUtils.isBlank(project)){
        		project = qfList.get(i).getOrderId()+"";
        	}
        	c1.setCellValue(project);
        	HSSFCell c2 = r.createCell(1);
        	c2.setCellStyle(style);
        	c2.setCellValue(qfList.get(i).getVcDes());
        	HSSFCell c3 = r.createCell(2);
        	String isHtCode = qfList.get(i).getIsHtCode();
	        String vcModelNumDisplay = isHtCode.equals("0")?qfList.get(i).getVcFactoryCode()+" "+qfList.get(i).getVcModelNum():qfList.get(i).getHtCode();//显示的型号
	        c3.setCellStyle(style);
        	c3.setCellValue(vcModelNumDisplay);
        	
        	HSSFCell c4 = r.createCell(3);
        	c4.setCellStyle(style);
        	c4.setCellValue(qfList.get(i).getVcColorNum());
        	
        	HSSFCell c5 = r.createCell(4);
        	c5.setCellStyle(style);
        	c5.setCellValue(qfList.get(i).getVcWidth()+" "+qfList.get(i).getVcWidthUnit());
        	HSSFCell c6 = r.createCell(5);
        	c6.setCellStyle(style);
        	c6.setCellValue(qfList.get(i).getVcPrice()+" "+qfList.get(i).getVcMoney());
        	HSSFCell c7 = r.createCell(6);
        	c7.setCellStyle(style);
        	c7.setCellValue(qfList.get(i).getVcQuantity()+" "+qfList.get(i).getVcPriceUnit());
        	
        	if("2".equals(quoteFormateFlag)){
        		HSSFCell c8 = r.createCell(7);
        		String vctotle = ProPrice.getTwoDecimalFloat(qfList.get(i).getVcTotal())+"";
        		c8.setCellStyle(style);
            	c8.setCellValue(vctotle);
        	} else {
        		HSSFCell c8 = r.createCell(7);
        		c8.setCellStyle(style);
        		c8.setCellValue("无".equals(qfList.get(i).getReplaceRemark())?"":qfList.get(i).getReplaceRemark());
        		HSSFCell c9 = r.createCell(8);
        		String vctotle = ProPrice.getTwoDecimalFloat(qfList.get(i).getVcTotal())+"";
        		c9.setCellStyle(style);
        		c9.setCellValue(vctotle);
        	}
        }
        
        int addrows = 0;
        HSSFRow rowx = sheet.createRow(shifStartRow + rows + addrows + 1);
        rowx.setHeightInPoints(30);
        sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol)); 
        for(int i = 0; i <= (mergeCol + 1); i++) {
        	HSSFCell cell = rowx.createCell(i);
            cell.setCellStyle(style);
            if(i == 0) {
            	cell.setCellValue("电机合计：");
            } else if(i == (mergeCol + 1)) {
            	cell.setCellValue(entity.getEngineTotal());
            }
        }
        addrows ++;
        
        HSSFRow rowx1 = sheet.createRow(shifStartRow + rows + addrows + 1);
        rowx1.setHeightInPoints(30);
        sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol)); 
        for(int i = 0; i <= (mergeCol + 1); i++) {
        	HSSFCell cell = rowx1.createCell(i);
            cell.setCellStyle(style);
            if(i == 0) {
            	cell.setCellValue("阻燃：");
            } else if(i == (mergeCol + 1)) {
            	cell.setCellValue(entity.getFlameTotal());
            }
        }
        addrows ++;

        HSSFRow rowx2 = sheet.createRow(shifStartRow + rows + addrows + 1);
        rowx2.setHeightInPoints(30);
        sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol)); 
        for(int i = 0; i <= (mergeCol + 1); i++) {
        	HSSFCell cell = rowx2.createCell(i);
            cell.setCellStyle(style);
            if(i == 0) {
            	cell.setCellValue("货到工地运费：");
            } else if(i == (mergeCol + 1)) {
            	cell.setCellValue(entity.getArriveTransport());
            }
        }
        addrows ++;
        
        if(entity.getTitleCol1() != null && !"".equals(entity.getTitleCol1())) {
        	HSSFRow r = sheet.createRow(shifStartRow + rows + addrows + 1);
        	r.setHeightInPoints(30);
            sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol));
            for(int i = 0; i <= (mergeCol + 1); i++) {
            	HSSFCell cell = r.createCell(i);
                cell.setCellStyle(style);
                if(i == 0) {
                	cell.setCellValue(entity.getTitleCol1() + "：");
                } else if(i == (mergeCol + 1)) {
                	cell.setCellValue(entity.getInputCol1());
                }
            }
            addrows ++;
        }
        if(entity.getTitleCol2() != null && !"".equals(entity.getTitleCol2())) {
        	HSSFRow r = sheet.createRow(shifStartRow + rows + addrows + 1);
        	r.setHeightInPoints(30);
            sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol));
            for(int i = 0; i <= (mergeCol + 1); i++) {
            	HSSFCell cell = r.createCell(i);
                cell.setCellStyle(style);
                if(i == 0) {
                	cell.setCellValue(entity.getTitleCol2() + "：");
                } else if(i == (mergeCol + 1)) {
                	cell.setCellValue(entity.getInputCol2());
                }
            }
            addrows ++;
        }
        if(entity.getTitleCol3() != null && !"".equals(entity.getTitleCol3())) {
        	HSSFRow r = sheet.createRow(shifStartRow + rows + addrows + 1);
        	r.setHeightInPoints(30);
            sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol));
            for(int i = 0; i <= (mergeCol + 1); i++) {
            	HSSFCell cell = r.createCell(i);
                cell.setCellStyle(style);
                if(i == 0) {
                	cell.setCellValue(entity.getTitleCol3() + "：");
                } else if(i == (mergeCol + 1)) {
                	cell.setCellValue(entity.getInputCol3());
                }
            }
            addrows ++;
        }
        if(entity.getTitleCol4() != null && !"".equals(entity.getTitleCol4())) {
        	HSSFRow r = sheet.createRow(shifStartRow + rows + addrows + 1);
        	r.setHeightInPoints(30);
            sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol));
            for(int i = 0; i <= (mergeCol + 1); i++) {
            	HSSFCell cell = r.createCell(i);
                cell.setCellStyle(style);
                if(i == 0) {
                	cell.setCellValue(entity.getTitleCol4() + "：");
                } else if(i == (mergeCol + 1)) {
                	cell.setCellValue(entity.getInputCol4());
                }
            }
            addrows ++;
        }
        if(entity.getTitleCol5() != null && !"".equals(entity.getTitleCol5())) {
        	HSSFRow r = sheet.createRow(shifStartRow + rows + addrows + 1);
        	r.setHeightInPoints(30);
            sheet.addMergedRegion(new Region(shifStartRow + rows + addrows + 1,(short)0,shifStartRow + rows + addrows + 1,mergeCol));
            for(int i = 0; i <= (mergeCol + 1); i++) {
            	HSSFCell cell = r.createCell(i);
                cell.setCellStyle(style);
                if(i == 0) {
                	cell.setCellValue(entity.getTitleCol5() + "：");
                } else if(i == (mergeCol + 1)) {
                	cell.setCellValue(entity.getInputCol5());
                }
            }
            addrows ++;
        }
        
        HSSFRow afterInsertRow1 = sheet.getRow(shifStartRow + rows + addrows + 1);
        if(afterInsertRow1!=null){
        	HSSFCell cell2 = afterInsertRow1.getCell(mergeCol + 1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getUrgentCost());
        	}
        }
        HSSFRow afterInsertRow2 = sheet.getRow(shifStartRow+rows+addrows+2);
        if(afterInsertRow2!=null){
        	HSSFCell cell2 = afterInsertRow2.getCell(mergeCol + 1);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getLowestFreight());
        	}
        }
        HSSFRow afterInsertRow3 = sheet.getRow(shifStartRow+rows+addrows+3);
        if(afterInsertRow3!=null){
        	HSSFCell cell2 = afterInsertRow3.getCell(mergeCol + 1);
        	if(cell2!=null){
        		String sub = PriceUtil.getTwoDecimalFloat(entity.getSubtotal())+"";
        		cell2.setCellValue(sub);
        	}
        }
        HSSFRow afterInsertRow4 = sheet.getRow(shifStartRow+rows+addrows+4);
        if(afterInsertRow4!=null){
        	HSSFCell cell2 = afterInsertRow4.getCell(mergeCol + 1);
        	if(cell2!=null){
        		String sum = PriceUtil.getTwoDecimalFloat(entity.getSumMoney())+"";
        		cell2.setCellValue(sum);
        	}
        }
        if("1".equals(quoteFormateFlag)){
        	 HSSFRow afterInsertRow6 = sheet.getRow(shifStartRow+rows+addrows+6);
             if(afterInsertRow6!=null){
             	HSSFCell cell2 = afterInsertRow6.getCell(0);
             	if(cell2!=null){
             		String cellvalue = "1)付款方式：請贵方先一次性付齐货款（即人民币：￥"+ProPrice.getTwoDecimalFloat(entity.getSumMoney())+"元）作为订金，款到敝方帐户后开始订货。貨到广州(貴方可到敝方指定地點驗貨)，敝方安排发貨。";
             		cell2.setCellValue(cellvalue);
             	}
             }
        }
       
        int lastRowNum = sheet.getLastRowNum();
        HSSFRow lastRow = sheet.getRow(lastRowNum);
        if(lastRow!=null){
        	HSSFCell cell1 = lastRow.getCell(0);
        	if(cell1!=null){
        		if("1".equals(quoteFormate)){
        			cell1.setCellValue(entity.getDeputyCom());
        		}else{
        			cell1.setCellValue("Harmontex Living Concept Co. Ltd.");
        		}
        		
        	}
        	HSSFCell cell2 = lastRow.getCell(5);
        	if(cell2!=null){
        		cell2.setCellValue(entity.getDeputyName());
        	}
        }
        String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.quoteLog, curAdminName+"下载了报价单"+entity.getProjectNum());
            String excelName = "quote" ;
            excelName = URLEncoder.encode(excelName, "gbk");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + excelName + ".xls");
            book.write(response.getOutputStream());
	}
	
	 public String importFile(){
		 String quoteId = request.getParameter("quoteId");
		 String vcModelNumDisplay = request.getParameter("vcModelNumDisplay");
		String vcCount = request.getParameter("vcCount");
		 String projectNum = request.getParameter("projectNum");
		 request.setAttribute("quoteId", quoteId);
		 request.setAttribute("vcModelNumDisplay", vcModelNumDisplay);
		 request.setAttribute("vcCount", vcCount);
		 request.setAttribute("projectNum", projectNum);
		 String linkCode = "";
		 if(StringUtils.isNotBlank(quoteId)){
			 linkCode = "quoteId_"+quoteId;
		 }
		 if(StringUtils.isNotBlank(vcModelNumDisplay)){
			linkCode = vcModelNumDisplay;
			if(StringUtils.isNotBlank(projectNum)){
				linkCode+="_"+projectNum;
			}
			if(StringUtils.isNotBlank(vcCount)){
				linkCode+="_"+vcCount;
			}
		}
		 return super.importFile(linkCode);
	 }
	 public String downloadFile(){
		 String quoteId = request.getParameter("quoteId");
		 String factoryCode = request.getParameter("factoryCode");
		 String befModle = request.getParameter("befModel");
		 String linkCode = "";
		 if(StringUtils.isNotBlank(quoteId)){
			 linkCode = "quoteId_"+quoteId;
		 }
		 if(StringUtils.isNotBlank(factoryCode)&&StringUtils.isNotBlank(befModle)){
			linkCode = factoryCode+"_"+befModle;
		}
		 return super.downloadFile(linkCode);
	 }
	 
	
	 public String designQuote(){
		String id = request.getParameter("id");
		quote = this.quoteService.getQuoteById(Long.valueOf(id));
		String vcProcessFre = quote.getVcProcessFre();
		float processFre = 0F;
		if(StringUtils.isNotEmpty(vcProcessFre)){
			processFre = new Float(vcProcessFre);
		}
		String vcInstallFre = quote.getVcInstallFre();
		float installFre = 0F;
		if(StringUtils.isNotEmpty(vcInstallFre)){
			installFre = new Float(vcInstallFre);
		}
		String vcAftertreatment = quote.getVcAftertreatment();
		float aftertreatment = 0F;
		if(StringUtils.isNotEmpty(vcAftertreatment)){
			aftertreatment = new Float(vcAftertreatment);
		}
		String vcOther = quote.getVcOther();
		float other = 0F;
		if(StringUtils.isNotEmpty(vcOther)){
			other = new Float(vcOther);
		}
		float containFre = (quote.getSumMoney() / quote.getContainTax())*(quote.getContainTax() - 1);
		float freight = this.getFreight(quote);
		float realTotel = quote.getSumMoney() - processFre - installFre - quote.getUrgentCost()
		- freight - containFre - aftertreatment - other;
		List<DesignerExpense> des = designerExpenseService
				.getDesignerExpenseByQuoteId(Long.valueOf(id));
		if (des != null && des.size() > 0) {
			designerExpense = des.get(0);
		}
		if(designerExpense.getRealTotel()==0){
			designerExpense.setRealTotel(PriceUtil.getTwoDecimalFloat((realTotel)));
		}
		
		String item = quote.getItem().replaceAll("@", "<br><br>");
		quote.setItem(item);
		quote.setSumMoneyStr(cancelGroupingUsed(quote.getSumMoney()));
		quote.setSubtotalStr(cancelGroupingUsed(quote.getSubtotal()));
		// 该报价单相关联的产品
		if (quote.getQuoteFabric() != null) {
			Set<QuoteFabric> qfSet = quote.getQuoteFabric();
			quoteFabricList = QuoteFabricUtil.sort(qfSet,
					"getOrderId", "asc");
		}
		List<Designer> designs = designerService.getAll();
		request.setAttribute("designs", designs);
		List<Admin> sellmanList = securityService.getUserListByRoleName("销售");
		request.setAttribute("sellmanList", sellmanList);
		List<UserGroup> salesmanGroup = this.securityService.getAllGroups();
		request.setAttribute("salesmanGroup", salesmanGroup);
		return "designQuote";
	}
	 
	 public String designOrder(){
		 String quoteId = request.getParameter("id");
		 List<DesignerOrder>  deos = this.designerOrderService.getDesignerOrderByQuoteId(Long.valueOf(quoteId));
		 if(deos!=null&&deos.size()>0){
			 designerOrder = deos.get(0);
		 }
		 return "designOrder";
	 }
	 
	/* public String downtest(){
		 log.info("尼玛");
		 List<Fabric> list = this.fabricService.getAll();
		 FileInputStream is = null;
		 OutputStream os = null;
		 String rootPath = this.getAbsoluteRootPath();
		 String filename = "export-"+DateUtil.convertLong2String(new Date().getTime(), "yyyy/MM/dd")+".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+filename);
		 try {
			 File file = new File(rootPath+"/model/test.xls");
			 
			 if(!file.exists()){
				 log.info("bucunzai");
			 }
			 Map<String, Object> map = new HashMap<String, Object>();
				XLSTransformer transformer = new XLSTransformer();
				Workbook workbook ;
				transformer.setSpreadsheetToRename("sheetName", "工单列表");
				map.put("fs", list);
				workbook = transformer.transformXLS(new FileInputStream(file), map);
				os= response.getOutputStream();
				workbook.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParsePropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(os!=null){
					os.flush();
					os.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		 return null;
	 }*/
	 
	 public String auditOrWritPerm(){
		 Admin curAdmin = this.getCurrentAdminUser();
		 String oper = request.getParameter("oper");
		 String quoteId = request.getParameter("quoteId");
		 Quote q = this.quoteService.getQuoteById(Long.valueOf(quoteId));
		 if("audit".equals(oper)){
			 q.setAuditDate(new Date());
			 q.setAuditPerson(this.getCurrentAdminUser().getUsername());
			 q.setVcAudit("1");
			 this.quoteService.saveOrUpdateEntity(q);
			 List<Admin> saleManegers = this.securityService.getUsersByRoleAndArea("销售经理", curAdmin.getArea());
				if(CollectionUtils.isNotEmpty(saleManegers)){
					for(Admin admin : saleManegers){
						Email e = new Email();
						e.setAction("quote");
						e.setDetail("关于【" + q.getProjectName() + "】的报价已经审核！报价单号为"+q.getProjectNum()+"，请签单");
						e.setQuoteId(q.getId());
						e.setQuoteNo(q.getProjectNum());
						e.setSender(curAdmin.getUsername());
						e.setSendTime(new Date());
						e.setState("1");
						e.setUsername(admin.getUsername());
						e.setStatus("2");
						this.emailService.saveOrUpdateEntity(e);
					}
				}
				Set<String> salenames = q.getSalesman();
				if(CollectionUtils.isNotEmpty(salenames)){
					for(String name : salenames){
						Email e = new Email();
						e.setAction("quote");
						e.setDetail("关于【" + q.getProjectName() + "】的报价已经审核！报价单号为"+q.getProjectNum()+"，已可以打印该报价单");
						e.setQuoteId(q.getId());
						e.setQuoteNo(q.getProjectNum());
						e.setSender(curAdmin.getUsername());
						e.setSendTime(new Date());
						e.setState("1");
						e.setUsername(name);
						e.setStatus("3");
						this.emailService.saveOrUpdateEntity(e);
					}
				}
				Email e = new Email();
				e.setAction("quote");
				e.setDetail("关于【" + q.getProjectName() + "】的报价已经审核！报价单号为"+q.getProjectNum());
				e.setQuoteId(q.getId());
				e.setQuoteNo(q.getProjectNum());
				e.setSender(curAdmin.getUsername());
				e.setSendTime(new Date());
				e.setState("1");
				e.setUsername(q.getModifyUser());
				e.setStatus("0");
				this.emailService.saveOrUpdateEntity(e);
				String curAdminName = this.getCurrentAdminUser().getUsername();
				saveSystemLog(LogModule.quoteLog, curAdminName+"审核了报价单"+q.getProjectNum());
				
		 }else if("writPerm".equals(oper)){
			 q.setIsWritPerm("1");
			 q.setContractDate(new Date());
			 String contractPrefix = "C"+q.getVcQuoteLocal();
			 String contractSubffi = q.getVcQuoteNum();
			 String contractNo = contractPrefix+contractSubffi;
			 q.setContractNo(contractNo);
				// 签单人
			 q.setAuditPerson(this.getCurrentAdminUser().getUsername());
			 this.quoteService.saveOrUpdateEntity(q);
			 Set<String> salenames = q.getSalesman();
				if(CollectionUtils.isNotEmpty(salenames)){
					for(String name : salenames){
						Email e = new Email();
						e.setAction("quote");
						e.setDetail("关于【" + q.getProjectName() + "】的报价已经签单！报价单号为"+q.getProjectNum());
						e.setQuoteId(q.getId());
						e.setQuoteNo(q.getProjectNum());
						e.setSender(curAdmin.getUsername());
						e.setSendTime(new Date());
						e.setState("1");
						e.setUsername(name);
						e.setStatus("0");
						this.emailService.saveOrUpdateEntity(e);
					}
				}
			 savePurchase(q);
			 String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.quoteLog, curAdminName+"签单了报价单"+q.getProjectNum());
			
			DesignerExpense oldDe = this.designerExpenseService.getDEByQuoteIdAndOperation(q.getId(),"add");
			if(oldDe==null){
				oldDe = new DesignerExpense();
			}
			oldDe.setContractDate(q.getContractDate());
			oldDe.setContractNo(q.getContractNo());
			oldDe.setCreateDate(new Date());
			oldDe.setCustomerName(q.getVcAttnName());
			oldDe.setQuoteId(q.getId());
			oldDe.setOperation("add");
			oldDe.setQuoteLocal(q.getVcQuoteLocal());
			oldDe.setQuoteNo(q.getProjectNum());
			oldDe.setSumMoney(q.getSumMoney());
			oldDe.setProjectName(q.getProjectName());
			oldDe.setVcProcessFre(StringUtils.isBlank(q.getVcProcessFre())?0F:Float.valueOf(q.getVcProcessFre()));
			oldDe.setVcInstallFre(StringUtils.isBlank(q.getVcInstallFre())?0F:Float.valueOf(q.getVcInstallFre()));
			oldDe.setUrgentCost(q.getUrgentCost());
			float freight = this.getFreight(q);
			Set<QuoteFabric> qfs = q.getQuoteFabric();
			
			//---------老数据没这个值，需要在导出的时候再计算一遍
			oldDe.setFreight(freight);
			//------------
			oldDe.setVcAftertreatment(StringUtils.isBlank(q.getVcAftertreatment())?0F:Float.valueOf(q.getVcAftertreatment()));
			oldDe.setVcOther(StringUtils.isBlank(q.getVcOther())?0F:Float.valueOf(q.getVcOther()));
			oldDe.setTaxationCost((q.getSumMoney() / q.getContainTax())*(q.getContainTax() - 1));
			String vcProcessFre = q.getVcProcessFre();
			float processFre = 0F;
			if(StringUtils.isNotEmpty(vcProcessFre)){
				processFre = new Float(vcProcessFre);
			}
			String vcInstallFre = q.getVcInstallFre();
			float installFre = 0F;
			if(StringUtils.isNotEmpty(vcInstallFre)){
				installFre = new Float(vcInstallFre);
			}
			String vcAftertreatment = q.getVcAftertreatment();
			float aftertreatment = 0F;
			if(StringUtils.isNotEmpty(vcAftertreatment)){
				aftertreatment = new Float(vcAftertreatment);
			}
			String vcOther = q.getVcOther();
			float other = 0F;
			if(StringUtils.isNotEmpty(vcOther)){
				other = new Float(vcOther);
			}
			float realTotel = q.getSumMoney() - processFre - installFre - q.getUrgentCost()
					- freight - oldDe.getTaxationCost() - aftertreatment - other;
			oldDe.setRealTotel(realTotel);
			//保存最新的
			this.designerExpenseService.saveOrUpdateEntity(oldDe);
			
			DesignerOrder oldDeo = this.designerOrderService.getDOByQuoteIdAndOperation(q.getId(), "add");
			if(oldDeo==null){
				oldDeo = new DesignerOrder();
			}
			oldDeo.setContractDate(q.getContractDate());
			oldDeo.setContractNo(q.getContractNo());
			oldDeo.setCreateDate(new Date());
			oldDeo.setCustomerName(q.getVcAttnName());
			oldDeo.setQuoteId(q.getId());
			oldDeo.setOperation("add");
			oldDeo.setQuoteLocal(q.getVcQuoteLocal());
			oldDeo.setSumMoney(q.getSumMoney());
			oldDeo.setProjectName(q.getProjectName());
			
			String saleMan = "";
			if(StringUtils.isNotBlank(q.getVcSalesman())){
				if(!saleMan.contains(q.getVcSalesman())){
					saleMan+=q.getVcSalesman()+",";
				}
				
			}
			if(StringUtils.isNotBlank(q.getVcSalesman1())){
				if(!saleMan.contains(q.getVcSalesman1())){
				saleMan+=q.getVcSalesman1()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman2())){
				if(!saleMan.contains(q.getVcSalesman2())){
				saleMan+=q.getVcSalesman2()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman3())){
				if(!saleMan.contains(q.getVcSalesman3())){
				saleMan+=q.getVcSalesman3()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman4())){
				if(!saleMan.contains(q.getVcSalesman4())){
				saleMan+=q.getVcSalesman4()+",";
				}
			}
			
			oldDeo.setVcSalesman(saleMan);
			//报价材料合计=材料明细表里同一合同号的所有型号的产品报价的合计总和
			float bjClTotel = 0F;
			//成本表===>产品报价===>材料合计=材料明细里面同一合同号中的所有型号的“产品材料报价合计”总和
			float bjOldPriceTatol = 0F;
			//加工费。读取报价页面
			if(StringUtils.isNotBlank(q.getVcProcessFre())){
				oldDeo.setVcProcessFre(Float.valueOf(q.getVcProcessFre()).floatValue());
			}
			//量窗费。读取报价页面
			oldDeo.setLcFre(q.getLcFre());
			//安装费。读取报价页面
			if(StringUtils.isNotBlank(q.getVcInstallFre())){
				oldDeo.setVcInstallFre(Float.valueOf(q.getVcInstallFre()));
			}
			//运费=各个型号的实际采购数量的值*具体类型的运费)+(实际采购数量的值*特殊费用)+自填框费用+最低运费+货到工地运费+加急费
			oldDeo.setBjFreight(freight);
			//税率
			oldDeo.setTaxation(q.getContainTax());
			//税金
			oldDeo.setTaxes(q.getTaxes());
			//报价合计=合同金额
			oldDeo.setBjTotel(oldDeo.getSumMoney());
			
			//抬头 读取报价单第三行的from
			oldDeo.setVcFrom(q.getVcFrom());
			//税费
			oldDeo.setTaxationFee(q.getTaxes()+q.getSumMoney()*(q.getContainTax()-1));
			//保存最新的
			this.designerOrderService.saveOrUpdateEntity(oldDeo);
			float rmb2hk = this.getExchangeRate("2", "RMB");
			List<QuoteFabricReport> oldQfrs = this.quoteFabricReportService.getQuoteFabricReportByDoId(oldDeo.getId());
			Map<String,QuoteFabricReport> qfrMap = new HashMap<String,QuoteFabricReport>();
			if(CollectionUtils.isNotEmpty(oldQfrs)){
				for(QuoteFabricReport oldQfr : oldQfrs){
					String color = oldQfr.getBjColor()==null?"":oldQfr.getBjColor();
					qfrMap.put(q.getId().toString()+color+oldQfr.getQfId().toString(), oldQfr);
				}
			}
			Set<String> qfIds = new HashSet<String>();
			for(QuoteFabric qf :qfs){
				String color = qf.getVcColorNum()==null?"":qf.getVcColorNum();
				qfIds.add(q.getId().toString()+color+qf.getId().toString());
			}
			List<Long> delQfrIds = new ArrayList<Long>();
			for(String qfrId : qfrMap.keySet()){
				if(!qfIds.contains(qfrId)){
					if(qfrMap.get(qfIds)==null){
						continue;
					}
					delQfrIds.add(qfrMap.get(qfIds).getId());
					qfrMap.remove(qfrId);
				}
			}
			if(delQfrIds.size()>0){
				quoteFabricReportService.deleteByIds(delQfrIds);
			}
			
			for(QuoteFabric qf :qfs){
				//保存最新的
				String color = qf.getVcColorNum()==null?"":qf.getVcColorNum();
				QuoteFabricReport qfr = qfrMap.get(q.getId().toString()+color+qf.getId().toString());
				if(qfr==null){
					qfr = new QuoteFabricReport();
				}
				qfr.setDoId(oldDeo.getId());
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
				
				if("1".equals(qf.getQuoteFormate())||"3".equals(qf.getQuoteFormate())){
					qfr.setVcFre(qf.getVcProFre());
				}else{
					qfr.setVcFre(qf.getVcRetFre()*rmb2hk);
				}
				qfr.setVcSpecialExp(qf.getVcSpecialExp());
				//材料明细表中的报价的单价
				float bjPrice = getVcPrice(q,qf,false,rmb2hk);
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
					bjClTotel+=qf.getVcTotal();
					bjOldPriceTatol+=qfr.getVcOldPriceTotal();
				}else if("1".equals(qf.getIsHidden())){
					qfr.setReplaceNO(qf.getReplaceId());
					qfr.setCbColor(qf.getVcColorNum());
				}else{
					qfr.setBjColor(qf.getVcColorNum());
					qfr.setCbColor(qf.getVcColorNum());
					bjClTotel+=qf.getVcTotal();
					bjOldPriceTatol+=qfr.getVcOldPriceTotal();
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
				quoteFabricReportService.saveOrUpdateEntity(qfr);
			}
			oldDeo.setBjClTotel(bjClTotel);
			//成本表===>产品报价===>材料合计=材料明细里面同一合同号中的所有型号的“产品材料报价合计”总和
			oldDeo.setBjOldPriceTatol(bjOldPriceTatol);
			this.designerOrderService.saveOrUpdateEntity(oldDeo);
		 }
		 return "ok";
	 }
	 //(qf.getVcOldPrice()*qf.getVcDiscount()+qf.getPriceAdjust())/10.764*q.getContainTax()
	 private float getVcPrice(Quote q,QuoteFabric qf,boolean flg,float rmb2hkd){
			    //所含税率
		//	    float ctax = q.getContainTax();
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
	 
	 private void savePurchase(Quote q){
		 Admin curAdmin = this.getCurrentAdminUser();
		 /** 1.插入purchase * */
			Purchase purchase = this.purchaseService.getUniqPurchaseByQuoteId(q.getId());
			if(purchase == null){
				purchase = new Purchase();
			}
		//	SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		//	String year_month = format.format(new Date());
		//	String contractPrefix = "C"+q.getVcQuoteLocal();
		//	String contractNo = contractPrefix + q.getVcQuoteNum();
			purchase.setContractNo(q.getContractNo());
			if(StringUtils.isEmpty(purchase.getTbYearMonth())){
				purchase.setTbYearMonth(DateUtil.getYearMonthStr());
			}
			purchase.setContractDate(q.getContractDate());// 下单时间
			purchase.setQuote(q);
			purchase.setArea(q.getVcQuoteLocal());
			purchase.setOrderStatus("0");// 订单状态
			purchase.setCustomer(q.getVcAttnName());
			// 成为代采购单
			purchase.setPurchaseType(Purchase.STATUS_D);
			purchaseService.saveOrUpdateEntity(purchase);
			Email e = new Email();
			e.setAction("toPurchase");
			e.setDetail("关于【" + q.getProjectName() + "】的报价已经签单！报价单号为"+q.getProjectNum()+"，请提交待采购单【"+q.getContractNo()+"】");
			e.setQuoteId(q.getId());
			e.setQuoteNo(q.getProjectNum());
			e.setSender(curAdmin.getUsername());
			e.setSendTime(new Date());
			e.setState("1");
			e.setUsername(q.getCurUserName());
			e.setStatus("1");
			e.setPurchaseId(purchase.getId());
			e.setPurchaseNo(purchase.getContractNo());
			this.emailService.saveOrUpdateEntity(e);
	 }
	 
	 public String saveDE(){
		 Long deId = designerExpense.getId();
		 DesignerExpense de = designerExpenseService.getDesignerExpenseById(deId);
		 designerExpense.setContractNo(de.getContractNo());
		 designerExpense.setContractDate(de.getContractDate());
		 designerExpense.setQuoteId(de.getQuoteId());
		 designerExpense.setOperation(de.getOperation());
		 designerExpense.setQuoteLocal(de.getQuoteLocal());
		 designerExpense.setQuoteNo(de.getQuoteNo());
		 designerExpense.setSumMoney(de.getSumMoney());
		 designerExpense.setCustomerName(de.getCustomerName());
		 designerExpense.setProjectName(de.getProjectName());
		 designerExpense.setVcProcessFre(de.getVcProcessFre());
		 designerExpense.setVcInstallFre(de.getVcInstallFre());
		 designerExpense.setUrgentCost(de.getUrgentCost());
		// designerExpense.setFreight(de.getFreight());
		 designerExpense.setTaxationCost(de.getTaxationCost());
		 designerExpense.setVcAftertreatment(de.getVcAftertreatment());
		 designerExpense.setVcOther(de.getVcOther());
		 designerExpense.setDesignTotelMoney(designerExpense.getDesignMony1()+designerExpense.getDesignMony2()+designerExpense.getDesignMony3());
		 designerExpense.setCreateDate(de.getCreateDate());
	//	 if(designerExpense.getFreight()==0){
			 Long qId = de.getQuoteId();
			 Quote q = this.quoteService.getQuoteById(qId);
			 float freight = getFreight(q);
				//---------老数据没这个值，需要在导出的时候再计算一遍
				designerExpense.setFreight(freight);
				//------------ 
		// }
				/*String vcProcessFre = q.getVcProcessFre();
				float processFre = 0F;
				if(StringUtils.isNotEmpty(vcProcessFre)){
					processFre = new Float(vcProcessFre);
				}
				String vcInstallFre = q.getVcInstallFre();
				float installFre = 0F;
				if(StringUtils.isNotEmpty(vcInstallFre)){
					installFre = new Float(vcInstallFre);
				}
				String vcAftertreatment = q.getVcAftertreatment();
				float aftertreatment = 0F;
				if(StringUtils.isNotEmpty(vcAftertreatment)){
					aftertreatment = new Float(vcAftertreatment);
				}
				String vcOther = q.getVcOther();
				float other = 0F;
				if(StringUtils.isNotEmpty(vcOther)){
					other = new Float(vcOther);
				}*/
		/*float realTotel = q.getSumMoney() - processFre - installFre - q.getUrgentCost()
					- freight - de.getTaxationCost() - aftertreatment - other;
			de.setRealTotel(realTotel);*/
		 designerExpenseService.saveOrUpdateEntity(designerExpense);
		 
		 List<DesignerOrder> deos = this.designerOrderService.getDesignerOrderByQuoteId(qId);
		 if(deos!=null){
			 for(DesignerOrder deo : deos){
				 deo.setDesigner1(designerExpense.getDesigner1());
					deo.setDesigner2(designerExpense.getDesigner2());
					deo.setDesigner3(designerExpense.getDesigner3());
					deo.setDesignMony1(Math.abs(designerExpense.getDesignMony1()));
					deo.setDesignMony2(Math.abs(designerExpense.getDesignMony2()));
					deo.setDesignMony3(Math.abs(designerExpense.getDesignMony3()));
					deo.setDesignFre(deo.getDesignMony1()+deo.getDesignMony2()+deo.getDesignMony3());
					designerOrderService.saveOrUpdateEntity(deo);
			 }
		 }
		
		 
		 String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.quoteLog, curAdminName+"设计了报价单"+de.getQuoteNo());
		 return "ok";
	 }

	 public String saveDO(){
		 DesignerOrder deoDb = designerOrderService.getDesignerOrderById(designerOrder.getId());
		 designerOrder.setContractDate(deoDb.getContractDate());
		 designerOrder.setContractNo(deoDb.getContractNo());
		 designerOrder.setCreateDate(deoDb.getCreateDate());
		 designerOrder.setCustomerName(deoDb.getCustomerName());
		 designerOrder.setQuoteId(deoDb.getQuoteId());
		 designerOrder.setOperation(deoDb.getOperation());
		 designerOrder.setQuoteLocal(deoDb.getQuoteLocal());
		 designerOrder.setSumMoney(deoDb.getSumMoney());
		 designerOrder.setProjectName(deoDb.getProjectName());
		 designerOrder.setBjClTotel(deoDb.getBjClTotel());
		 designerOrder.setVcProcessFre(deoDb.getVcProcessFre());
		 designerOrder.setLcFre(deoDb.getLcFre());
		 designerOrder.setVcInstallFre(deoDb.getVcInstallFre());
		 Quote q = this.quoteService.getQuoteById(deoDb.getQuoteId());
		 float freight = getFreight(q);
		 designerOrder.setBjFreight(freight);
		 designerOrder.setVcFrom(deoDb.getVcFrom());
		 designerOrder.setTaxation(deoDb.getTaxation());
		 designerOrder.setTaxes(deoDb.getTaxes());
		 designerOrder.setTaxationFee(deoDb.getTaxationFee());
		 designerOrder.setCbClTotel(deoDb.getCbClTotel());
		// float bjTotel = (deoDb.getBjClTotel()+deoDb.getVcProcessFre()+deoDb.getLcFre()+deoDb.getVcInstallFre()+freight)*deoDb.getTaxation();
		 designerOrder.setBjTotel(deoDb.getSumMoney());
		 String saleMan = deoDb.getVcSalesman()==null?"":deoDb.getVcSalesman();
			if(StringUtils.isNotBlank(q.getVcSalesman())){
				if(!saleMan.contains(q.getVcSalesman())){
					saleMan+=q.getVcSalesman()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman1())){
				if(!saleMan.contains(q.getVcSalesman1())){
				saleMan+=q.getVcSalesman1()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman2())){
				if(!saleMan.contains(q.getVcSalesman2())){
				saleMan+=q.getVcSalesman2()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman3())){
				if(!saleMan.contains(q.getVcSalesman3())){
				saleMan+=q.getVcSalesman3()+",";
				}
			}
			if(StringUtils.isNotBlank(q.getVcSalesman4())){
				if(!saleMan.contains(q.getVcSalesman4())){
				saleMan+=q.getVcSalesman4()+",";
				}
			}
			/*Set<String> salesMan = q.getSalesman();
			if(salesMan!=null){
				for(String sale : salesMan){
					if(StringUtils.isNotBlank(sale)){
						if(!saleMan.contains(sale)){
						saleMan+=sale+",";
						}
					}
				}
			}*/
			designerOrder.setVcSalesman(saleMan);
		 List<Order> os = this.orderService.getOrderByQuoteId(q.getId());
		 Order order = null;
			if(os!=null&&os.size()>0){
				order = os.get(0);
				designerOrder.setOrderNo(os.get(0).getOrderNo());
				//运费 同一订单号订单运费相加
				float cbFreight = 0F;
				for(Order o :os){
					cbFreight+=o.getFreight();
				}
				designerOrder.setCbFreight(cbFreight);
			}
			List<Purchase>  ps = this.purchaseService.getPurchaseByQuoteId(q.getId());
			if(ps!=null&&ps.size()>0){
				Purchase p = ps.get(0);
				//销售成本材料合计.读取采购页面
			//	designerOrder.setCbClTotel(p.getClTotal());
				//加工费.读取采购页面
				designerOrder.setProcessFee(p.getProcessFee());
				//安装费 读取采购页面
				designerOrder.setInstallFee(p.getInstallFee());
				//差旅费 读取采购页面
				designerOrder.setTravelExpenses(p.getTravelExpenses());
				//其他 读取采购页面
				designerOrder.setOtherFre(p.getOtherFre());
			}
			List<DesignerExpense> des = this.designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
			//设计师1-3,设计费
			if(CollectionUtils.isNotEmpty(des)){
				DesignerExpense oldDe = des.get(0);
				designerOrder.setDesigner1(oldDe.getDesigner1());
				designerOrder.setDesigner2(oldDe.getDesigner2());
				designerOrder.setDesigner3(oldDe.getDesigner3());
				designerOrder.setDesignMony1(oldDe.getDesignMony1());
				designerOrder.setDesignMony2(oldDe.getDesignMony2());
				designerOrder.setDesignMony3(oldDe.getDesignMony3());
				designerOrder.setDesignFre(designerOrder.getDesignMony1()+designerOrder.getDesignMony2()+designerOrder.getDesignMony3());
			}
			
			//销售费用合计(加工费+安装费+运费+差旅费+设计费+税费+其他)
			float cbTotel = designerOrder.getProcessFee()+designerOrder.getInstallFee()+designerOrder.getCbFreight()+designerOrder.getTravelExpenses()+designerOrder.getDesignFre()+designerOrder.getOtherFre();
			designerOrder.setCbTotel(cbTotel);
			//毛利(报价合计-销售成本材料合计-销售费用合计)
			float profit = designerOrder.getBjTotel()-designerOrder.getCbClTotel()-designerOrder.getCbTotel();
			designerOrder.setProfit(profit);
			//毛利率(毛利/报价合计)
			if(designerOrder.getBjTotel()>0){
				designerOrder.setProfitRate(designerOrder.getProfit()/designerOrder.getBjTotel());
			}
			
			Set<QuoteFabric> qfs = q.getQuoteFabric();
			List<QuoteFabricReport>  qfrs = this.quoteFabricReportService.getQuoteFabricReportByDoId(deoDb.getId());
			Map<String,QuoteFabricReport> map = new HashMap<String,QuoteFabricReport>();
			if(qfrs!=null){
				for(QuoteFabricReport qfr : qfrs){
					for(QuoteFabric qf : qfs){
						if(qfr.getQfId().longValue()==qf.getId().longValue()){
							if("1".equals(qf.getIsReplaced())){
								String str = qf.getReplaceRemark();
								if(str!=null&&str.length()>3){
									String newStr = StringUtils.substring(str, 1, -2);
									qfr.setReplaceNO(newStr);
								}
								
							}
							if("1".equals(qf.getIsHidden())){
								qfr.setReplaceNO(qf.getReplaceId());
							}
							qfr.setCbPrice(qf.getShijia());
							qfr.setCbPriceUnit(qf.getPriceCur());
							qfr.setCbQuantity(qf.getVcQuoteNum());
							float sigMoney = PriceUtil.getTwoDecimalFloat(qf.getSinglePrice() * qf.getVcPurDis());
							if(sigMoney==0){
								float vcPurDis = qf.getVcPurDis()==0?1F:qf.getVcPurDis();
								sigMoney = PriceUtil.getTwoDecimalFloat(qf.getDhjCost() * vcPurDis);
							}
							qfr.setSingleMoney(sigMoney);
							qfr.setOrderNum(qf.getOrderQuantity());
							
						}
					}
					map.put(qfr.getVcModelNum(), qfr);
				}
				for(QuoteFabricReport qfr : qfrs){
					if("1".equals(qfr.getIsReplaced())){
						qfr.setBjTotal(qfr.getVcPrice()*qfr.getVcQuantity());
						QuoteFabricReport hidden = map.get(qfr.getReplaceNO());
						if(hidden!=null){
							qfr.setCbModelNum(hidden.getVcFactoryCode()+" "+hidden.getVcBefModel());
							qfr.setCbTotal(hidden.getCbPrice()*hidden.getCbQuantity());
							qfr.setCbColor(hidden.getCbColor());
						}
					}else if("1".equals(qfr.getIsHidden())){
						QuoteFabricReport replaced = map.get(qfr.getReplaceNO());
						if(replaced!=null){
							qfr.setBjTotal(replaced.getVcPrice()*replaced.getVcQuantity());
							qfr.setBjColor(replaced.getBjColor());
						}
						qfr.setCbTotal(qfr.getCbPrice()*qfr.getCbQuantity());
					}else{
						qfr.setBjTotal(qfr.getVcPrice()*qfr.getVcQuantity());
						qfr.setCbTotal(qfr.getCbPrice()*qfr.getCbQuantity());
					}
					qfr.setSellProfit(qfr.getBjTotal()-qfr.getCbTotal());
					if(qfr.getBjTotal()>0){
						qfr.setSellProfitRate(qfr.getSellProfit()/qfr.getBjTotal());
					}
					if(order!=null){
						qfr.setOrderNo(order.getOrderNo());
					}
					quoteFabricReportService.saveOrUpdateEntity(qfr);
				}
			}
			
			
		 if (designerOrder.getShareArea1().equals(q.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony1());
				designerOrder.setRealArea(designerOrder.getShareArea1());
				designerOrder.setRealRate(designerOrder.getShareRate1());
			} else if (designerOrder.getShareArea2().equals(q.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony2());
				designerOrder.setRealArea(designerOrder.getShareArea2());
				designerOrder.setRealRate(designerOrder.getShareRate2());
			} else if (designerOrder.getShareArea3().equals(q.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony3());
				designerOrder.setRealArea(designerOrder.getShareArea3());
				designerOrder.setRealRate(designerOrder.getShareRate3());
			} else if (designerOrder.getShareArea4().equals(q.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony4());
				designerOrder.setRealArea(designerOrder.getShareArea4());
				designerOrder.setRealRate(designerOrder.getShareRate4());
			} else if (designerOrder.getShareArea5().equals(q.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony5());
				designerOrder.setRealArea(designerOrder.getShareArea5());
				designerOrder.setRealRate(designerOrder.getShareRate5());
			} 
		 designerOrderService.saveOrUpdateEntity(designerOrder);
		 String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.quoteLog, curAdminName+"设计了财务报表"+deoDb.getContractNo());
		 return "ok";
	 }
	 //运费=各个型号的实际采购数量的值*具体类型的运费)+(实际采购数量的值*特殊费用)+自填框费用+最低运费+货到工地运费+加急费
	private float getFreight(Quote q) {
			float freight = 0F;
				if("1".equals(q.getIsFreight())){
					Set<QuoteFabric> qfs = q.getQuoteFabric();
					for(QuoteFabric qf :qfs){
						if(qf==null||StringUtils.isBlank(qf.getVcModelNumDisplay())||"1".equals(qf.getIsHidden()))continue;
						float lowFreight = 0F;
						//quoteFormate==1内地报价,quoteFormate==2香港报价,quoteFormate==3大货价内地报价,quoteFormate==4大货价香港报价,quoteFormate==5零售报价
						if("1".equals(q.getQuoteFormate())||"5".equals(q.getQuoteFormate())){
							lowFreight = qf.getVcProFre();
						}else if("2".equals(q.getQuoteFormate())){
							lowFreight = qf.getVcRetFre();
						}else if("3".equals(q.getQuoteFormate())){
							lowFreight = qf.getDhjInlandTransCost();
						}else if("4".equals(q.getQuoteFormate())){
							lowFreight = qf.getDhjHKTransCost();
						}
						lowFreight+=qf.getVcSpecialExp();
						if("yd".equalsIgnoreCase(qf.getVcOldPriceUnit())){
							freight +=qf.getOrderQuantity()*lowFreight*0.914;
						}else{
							freight +=qf.getOrderQuantity()*lowFreight;
						}
					}
				}
				freight+=q.getLowestFreight()+q.getArriveTransport()+q.getUrgentCost()+q.getInputCol1()+q.getInputCol2()+q.getInputCol3()+q.getInputCol4()+q.getInputCol5();
			return freight;
		}
	 
	 public String getCounselorRate(){
		 String codeName = request.getParameter("objValue");
			Designer d = designerService.getDesigner(codeName);
			String counselorRate = "{}";
			if(d!=null){
				counselorRate = d.getCounselorRate();
			}
			try {
				response.getWriter().write(counselorRate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	 
	 private String cancelGroupingUsed(double d){
		 NumberFormat nf = NumberFormat.getInstance();
		 nf.setGroupingUsed(false);
		 return nf.format(Math.round(d));
	 }
	 
	public String printQuote(){
		quote = this.quoteService.getQuoteById(Long.valueOf(request.getParameter("id")));
		String item = quote.getItem()==null?"":quote.getItem().replaceAll("@", "<br>");
		quote.setItem(item);
		if (quote.getQuoteFabric() != null) {
			Set<QuoteFabric> qfSet = quote.getQuoteFabric();
			quoteFabricList = QuoteFabricUtil.sort(qfSet,"getOrderId", "asc");
		}
		List<QuoteFabric> listQF = new ArrayList<QuoteFabric>();
		for(QuoteFabric qf : quoteFabricList){
			qf.setVcTotalStr(cancelGroupingUsed(qf.getVcTotal()));
			if(!"1".equals(qf.getIsHidden())){//不是隐藏型号的才打印
				if(StringUtils.isBlank(qf.getVcProject())){
					qf.setVcProject(qf.getOrderId()+"");
				}
				Attachment attr = commonService.getOnlyAttachmentByKey(qf.getVcModelNumDisplay()+"_"+quote.getProjectNum()+"_"+qf.getOrderId());
				/*if(attr==null){
					attr = commonService.getOnlyAttachmentByKey(qf.getVcFactoryCode()+"_"+qf.getVcModelNum());
				}*/
				if(attr!=null){
					qf.setFilePath(attr.getAttachPath());
				}
				listQF.add(qf);
			}
		}
		request.setAttribute("listQF", listQF);
		quote.setLowestFreight(Math.round(quote.getLowestFreight()*quote.getContainTax()));
		quote.setUrgentCost(Math.round(quote.getUrgentCost()*quote.getContainTax()));
		quote.setEngineTotal(Math.round(quote.getEngineTotal()*quote.getContainTax()));
		quote.setFlameTotal(Math.round(quote.getFlameTotal()*quote.getContainTax()));
		quote.setArriveTransport(Math.round(quote.getArriveTransport()*quote.getContainTax()));
		quote.setInputCol1(Math.round(quote.getInputCol1()*quote.getContainTax()));
		quote.setInputCol2(Math.round(quote.getInputCol2()*quote.getContainTax()));
		quote.setInputCol3(Math.round(quote.getInputCol3()*quote.getContainTax()));
		quote.setInputCol4(Math.round(quote.getInputCol4()*quote.getContainTax()));
		quote.setInputCol5(Math.round(quote.getInputCol5()*quote.getContainTax()));
		
		float VcProcessFre = Float.valueOf(StringUtils.isBlank(quote.getVcProcessFre())?"0":quote.getVcProcessFre());
		quote.setVcProcessFre(Math.round(VcProcessFre*quote.getContainTax())+"");
		float VcInstallFre = Float.valueOf(StringUtils.isBlank(quote.getVcInstallFre())?"0":quote.getVcInstallFre());
		quote.setVcInstallFre(Math.round(VcInstallFre*quote.getContainTax())+"");
		float VcAftertreatment = Float.valueOf(StringUtils.isBlank(quote.getVcAftertreatment())?"0":quote.getVcAftertreatment());
		quote.setVcAftertreatment(Math.round(VcAftertreatment*quote.getContainTax())+"");
		quote.setSumMoneyStr(cancelGroupingUsed(quote.getSumMoney()));
		quote.setSubtotalStr(cancelGroupingUsed(quote.getSubtotal()));
		String local =  request.getParameter("local");
		request.setAttribute("local", local);
		if("hk".equals(local)){
			return "printHK";
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.quoteLog, curAdminName+"打印了报价单"+quote.getProjectNum());
		return "print";
	}
	public String selPriceByModel(){
		String quoteFormate = request.getParameter("quoteFormate");
		String vcFactoryCode = request.getParameter("factoryCode");
		String vcModelNum = request.getParameter("model");
		String htCode = request.getParameter("htCode");
		String isHtCode = request.getParameter("isHtCode");
		Page<QuoteFabric> page = new Page<QuoteFabric>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = new HashMap<String,Object>();
		if("1".equals(isHtCode)){
			paramap.put("isHtCode", "1");
			paramap.put("htCode", htCode);
			request.setAttribute("isHtCode", "1");
			request.setAttribute("htCode", htCode);
		}else{
			paramap.put("isHtCode", "0");
			paramap.put("vcFactoryCode",vcFactoryCode);
			paramap.put("vcModelNum",vcModelNum);
			request.setAttribute("isHtCode", "0");
			request.setAttribute("factoryCode",vcFactoryCode);
			request.setAttribute("model",vcModelNum);
		}
		paramap.put("quoteFormate", quoteFormate);
		request.setAttribute("quoteFormate",quoteFormate);
		page = this.quoteFabricService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/quote_selPriceByModel.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		String parentJsp = request.getParameter("parentJsp");
		if("1".equals(parentJsp)){
			return "selPriceByModel";
		}else{
			return "selPriceByModelList";
		}
		
	}
	
	public String getQuoteByNum(){
		 String projectNum = request.getParameter("projectNum");
			Quote q = quoteService.getQuote(projectNum);
			String result = "success";
			if(q!=null){
				result="error";
			}
			try {
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public String getQuoteStatus(){
		if(StringUtils.isBlank(request.getParameter("quoteId"))){
			return null;
		}
		String emailId = request.getParameter("emailId");
		if(StringUtils.isNotBlank(emailId)){
			Email e = this.emailService.getEmailById(Long.valueOf(emailId));
			e.setState("2");
			this.emailService.saveOrUpdateEntity(e);
		}
		String status = request.getParameter("status");
		String quoteId = request.getParameter("quoteId");
		Quote q = this.quoteService.getQuoteById(Long.valueOf(quoteId));
		String canDo = "1";
		if("1".equals(status)){//已经提交，要去审核
			String isAudit = q.getVcAudit();
			if("1".equals(isAudit)){
				canDo = "0";
			}
		}else if("2".equals(status)){//已经审核，要去签单
			String isWritePerm = q.getIsWritPerm();
			if("1".equals(isWritePerm)){
				canDo = "0";
			}
		}
		try {
			response.getWriter().write(canDo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public List<QuoteFabric> getQuoteFabricList() {
		return quoteFabricList;
	}

	public void setQuoteFabricList(List<QuoteFabric> quoteFabricList) {
		this.quoteFabricList = quoteFabricList;
	}

	public DesignerExpense getDesignerExpense() {
		return designerExpense;
	}

	public void setDesignerExpense(DesignerExpense designerExpense) {
		this.designerExpense = designerExpense;
	}

	public DesignerOrder getDesignerOrder() {
		return designerOrder;
	}

	public void setDesignerOrder(DesignerOrder designerOrder) {
		this.designerOrder = designerOrder;
	}
	
	
}
