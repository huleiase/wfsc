package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.DesignerOrder;
import com.wfsc.common.bo.bym.Email;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.Purchase;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.common.bo.bym.QuoteFabric;
import com.wfsc.common.bo.bym.Store;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IDesignerOrderService;
import com.wfsc.services.bym.service.IEmailService;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IQuoteFabricService;
import com.wfsc.services.bym.service.IStoreFabricService;
import com.wfsc.services.bym.service.IStoreService;
import com.wfsc.services.bym.service.ISupplierService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.PriceUtil;
import com.wfsc.util.QuoteFabricUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("OrderAction")
@Scope("prototype")
public class OrderAction extends DispatchPagerAction {

	private static final long serialVersionUID = 684081059299260353L;
	
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "supplierService")
	private ISupplierService supplierService;
	@Resource(name = "quoteFabricService")
	private IQuoteFabricService quoteFabricService;
	@Resource(name = "orderService")
	private IOrderService orderService;
	@Resource(name = "designerOrderService")
	private IDesignerOrderService designerOrderService;
	@Resource(name = "storeService")
	private IStoreService storeService;
	@Resource(name = "storeFabricService")
	private IStoreFabricService storeFabricService;
	@Resource(name = "emailService")
	private IEmailService emailService;
	
	private Order order;
	private DesignerOrder designerOrder;
	private List<QuoteFabric> quoteFabricList = new ArrayList<QuoteFabric>();

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	@SuppressWarnings("unchecked")
	public String list(){
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		boolean isPurMan = securityService.isAbleRole(admin.getUsername(), "采购员");
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
		boolean isCaiwu = securityService.isAbleRole(admin.getUsername(), "财务经理");
		boolean isLess = false;
		boolean canToQuote = false;
		if(isSale||isSaleManager||isQuoter){
			isLess = true;
		}
		if(isAdmin||isCaiwu){
			canToQuote = true;
		}
		boolean isLogistics = securityService.isAbleRole(admin.getUsername(), "物流专员");
		boolean isAreaCaiwu = securityService.isAbleRole(admin.getUsername(), "分区财务经理");
		boolean isMoreLess = false;
		if(isLogistics||isAreaCaiwu){
			isMoreLess = true;
			isLess = true;
		}
		request.setAttribute("isMoreLess", isMoreLess);
		request.setAttribute("isLess", isLess);
		request.setAttribute("canToQuote", canToQuote);
	//	request.setAttribute("isAdmin", isAdmin);
	//	request.setAttribute("purManager", purManager);
	//	request.setAttribute("purMan", purMan);
		Page<Order> page = new Page<Order>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		if(!isAdmin&&!isPurManager&&!isPurMan){
			if(!(isCaiwu&&"GZ".equalsIgnoreCase(admin.getArea()))){
				paramap.put("area", admin.getArea());
			}
			if(isSale&&!isSaleManager){
				paramap.put("saleName", admin.getUsername());
			}
		}
		
		page = orderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/order_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("isPurManager", isPurManager);
		request.setAttribute("isAdmin", isAdmin);
		return "list";
	}
	public String input() {
		Admin admin = this.getCurrentAdminUser();
		//财务收款完结（各地财务、管理员、系统管理员）才有权限下拉选择
		//财务付款完结（广州财务、管理员、系统管理员）才有权限下拉
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "管理员");
		boolean isCaiwu = securityService.isAbleRole(admin.getUsername(), "财务经理");
		boolean canCaiwuOver = false;
		if(isAdmin||isCaiwu){
			canCaiwuOver = true;
		}
		request.setAttribute("canCaiwuOver", canCaiwuOver);
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
		boolean isLess = false;
		if(isSale||isSaleManager||isQuoter){
			isLess = true;
		}
		boolean isLogistics = securityService.isAbleRole(admin.getUsername(), "物流专员");
		boolean isAreaCaiwu = securityService.isAbleRole(admin.getUsername(), "分区财务经理");
		boolean isMoreLess = false;
		if(isLogistics||isAreaCaiwu){
			isMoreLess = true;
			isLess = true;
		}
		request.setAttribute("isMoreLess", isMoreLess);
		request.setAttribute("isLess", isLess);
		String id = request.getParameter("id");
		String isView = request.getParameter("isView");
		request.setAttribute("isView", StringUtils.isBlank(isView)?"0":"1");
		String oper = request.getParameter("oper");
		order = this.orderService.getOrderById(Long.valueOf(id));
		String consignee = order.getConsignee();
		if (consignee == null || "".equals(consignee)) {
			order.setConsignee(order.getVcfrom());
		}
		Set<QuoteFabric> qfSet  = order.getPurchase().getQuote().getQuoteFabric();
		float sumMoney = 0f;
		if(qfSet!=null){
			List<QuoteFabric> qfList =  QuoteFabricUtil.sort(qfSet, "getOrderId", "asc");
			for(QuoteFabric qf : qfList){
				if(!"1".equals(qf.getIsReplaced()) && qf.getVcFactoryNum().equals(order.getFactoryNum())){
					float sigMoney = PriceUtil.getTwoDecimalFloat(qf.getSinglePrice() * qf.getVcPurDis());
					//sigMoney = (float) (Math.round((sigMoney) * 10)) / 10;
					qf.setSigMoney(sigMoney);
					float vcQuoteNum = qf.getVcQuoteNum() == 0 ? qf.getOrderQuantity() : qf.getVcQuoteNum();
					float shijia = qf.getShijia() == 0 ? qf.getSigMoney() : qf.getShijia();
					qf.setVcQuoteNum(vcQuoteNum);
					qf.setShijia(shijia);
					float amountrmb = qf.getAmountrmb() == 0 ? PriceUtil.getTwoDecimalFloat(vcQuoteNum * shijia * qf.getExchangeRate()) : qf.getAmountrmb();
					qf.setAmountrmb(amountrmb);
					qf.setRealMonny(qf.getRealMonny() == 0 ? PriceUtil.getTwoDecimalFloat(vcQuoteNum * shijia) : qf.getRealMonny());
					sumMoney += (vcQuoteNum * shijia);
					//qf.setVcModelNumDisplay(qf.getVcModelNum());
					if(isLess){
						if("1".equals(qf.getIsHtCode())){
							qf.setVcColorNum("");
						}
					}
					if("1".equals(qf.getIsHidden())&&StringUtils.isNotBlank(qf.getReplaceId())){
						qf.setVcModelNumDisplay(qf.getReplaceId());
					}
					/*else{
						qf.setVcModelNumDisplay(qf.getVcModelNum());
					}*/
					quoteFabricList.add(qf);//不是被替代的产品才在采购单中显示
				}
			}
			quoteFabricList = QuoteFabricUtil.sort(quoteFabricList, "getOrderId", "asc");
		}
		if(order.getSumMoney()>0){
			sumMoney = order.getSumMoney();
		}
		//sumMoney = (float) (Math.round((sumMoney) * 10)) / 10;
		order.setSumMoney(PriceUtil.getTwoDecimalFloat(sumMoney));
		Supplier s = this.supplierService.getSupplierByCode(order.getFactoryCode());
		if(s!=null){
			if (StringUtils.isBlank(order.getAtten())) {
				order.setAtten(s.getVcLinkMan());
			}

			if (StringUtils.isBlank(order.getAttenTel())) {
				order.setAttenTel(s.getVcTel());
			}
			if (StringUtils.isBlank(order.getAttenFax())) {
				order.setAttenFax(s.getVcFax());
			}
			request.setAttribute("supplier", s);
		}
		if(StringUtils.isBlank(order.getVcfromTel())){
			order.setVcfromTel("080-83309415");
		}
		if(StringUtils.isBlank(order.getVcfromFax())){
			order.setVcfromFax("02083309428");
		}
		if("1".equals(oper)||"3".equals(oper)){//提交，审核
			order.setOrderStatus(Integer.valueOf(oper));
			return "input";
		}else if("8".equals(oper)){//打印国内
			
			return "print";
		}else if("9".equals(oper)){//打印香港
			
			return "printHK";
		}
		
		return "input";
	}
	
	
	
	public String deleteByIds(){
		String idStr = request.getParameter("ids");
		String[] idArray = idStr.split(",");
		List<Long> ids = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer("");
		for(String id:idArray){
			Order o = this.orderService.getOrderById(Long.valueOf(id));
			sb.append(o.getOrderNo()).append(",");
			List<StoreFabric> sfdbList = storeFabricService.getStoreFabricByOrderId(o.getId());
			if(CollectionUtils.isNotEmpty(sfdbList)){
				storeFabricService.deleteAll(sfdbList);
			}
			ids.add(Long.valueOf(id));
		}
		this.orderService.deleteByIds(ids);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.orderLog, curAdminName+"删除了订单"+sb.toString());
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		Admin curAdmin = this.getCurrentAdminUser();
		Order odb = this.orderService.getOrderById(order.getId());
		Purchase pdb = odb.getPurchase();
		Quote q = pdb.getQuote();
		order.setOrderNo(odb.getOrderNo());
		order.setOrderDate(odb.getOrderDate());
		order.setTbYearMonth(odb.getTbYearMonth());
		order.setArea(odb.getArea());// 地区
		order.setPurchase(odb.getPurchase());
		order.setSupplier(odb.getSupplier());
		order.setFactoryCode(odb.getFactoryCode());
		order.setQuantation(odb.getQuantation());
		order.setQuoteId(odb.getQuoteId());
		order.setFactoryNum(odb.getFactoryNum());
		order.setHbUnit(odb.getHbUnit());
		order.setModifyDate(new Date());
		if(3==order.getOrderStatus()){
			order.setAuditor(curAdmin.getUsername());
		}
		orderService.saveOrUpdateEntity(order);
		List<QuoteFabric> qfdbList = new ArrayList<QuoteFabric>();
		for(QuoteFabric qf:quoteFabricList){
			QuoteFabric qfdb = this.quoteFabricService.getQuoteFabricById(qf.getId());
			
			qfdb.setVcSubLay(qf.getVcSubLay());
			qfdb.setVcRealityAog(qf.getVcRealityAog());
			qfdb.setVcShipmentNum(qf.getVcShipmentNum());
			qfdb.setVcType(qf.getVcType());
			qfdb.setVcPurchaseRmk(qf.getVcPurchaseRmk());
			qfdb.setVcQuoteNum(qf.getVcQuoteNum());
			qfdb.setShijia(qf.getShijia());
			qfdb.setRealMonny(qf.getRealMonny());
			qfdb.setAmountrmb(qf.getAmountrmb());
			quoteFabricService.saveOrUpdateEntity(qfdb);
			qfdbList.add(qfdb);
		}
		if(1==order.getOrderStatus()){
			List<Admin> saleManegers = this.securityService.getUserListByRoleName("采购经理");
			if(CollectionUtils.isNotEmpty(saleManegers)){
				for(Admin admin : saleManegers){
					Email e = new Email();
					e.setAction("order");
					e.setDetail("关于【" + q.getProjectName() + "】的订单已经提交！订单号为" + odb.getOrderNo());
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setOrderId(odb.getId());
					e.setOrderNo(odb.getOrderNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					e.setStatus("0");
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			Email e = new Email();
			e.setAction("order");
			e.setDetail("关于【" + q.getProjectName() + "】的订单已经提交！订单号为" + odb.getOrderNo() + "，请审核");
			e.setQuoteId(q.getId());
			e.setQuoteNo(q.getProjectNum());
			e.setPurchaseId(pdb.getId());
			e.setPurchaseNo(pdb.getContractNo());
			e.setOrderId(odb.getId());
			e.setOrderNo(odb.getOrderNo());
			e.setSender(curAdmin.getUsername());
			e.setSendTime(new Date());
			e.setState("1");
			e.setUsername(curAdmin.getUsername());
			e.setStatus("2");
			this.emailService.saveOrUpdateEntity(e);
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.orderLog, curAdminName+"提交了订单"+order.getOrderNo());
			this.saveProStroage(order, qfdbList);
		}
		if(3==order.getOrderStatus()){
			List<Admin> purManegers = this.securityService.getUserListByRoleName("采购经理");
			if(CollectionUtils.isNotEmpty(purManegers)){
				for(Admin admin : purManegers){
					Email e = new Email();
					e.setAction("order");
					e.setDetail("关于【" + q.getProjectName() + "】的订单已经审核！订单号为" + odb.getOrderNo());
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setOrderId(odb.getId());
					e.setOrderNo(odb.getOrderNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			List<Admin> caiwuManegers = this.securityService.getUserListByRoleName("财务经理");
			if(CollectionUtils.isNotEmpty(caiwuManegers)){
				for(Admin admin : caiwuManegers){
					Email e = new Email();
					e.setAction("order");
					e.setDetail("关于【" + q.getProjectName() + "】的订单已经审核！订单号为" + odb.getOrderNo() + "，请设计订单，填写相关信息");
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setOrderId(odb.getId());
					e.setOrderNo(odb.getOrderNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					e.setStatus("3");
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			List<Admin> storeManegers = this.securityService.getUserListByRoleName("仓库管理员");
			if(CollectionUtils.isNotEmpty(storeManegers)){
				for(Admin admin : storeManegers){
					Email e = new Email();
					e.setAction("order");
					e.setDetail("关于【" + q.getProjectName() + "】的订单已经审核入库至" + odb.getArea() + "仓库！订单号为" + odb.getOrderNo());
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setOrderId(odb.getId());
					e.setOrderNo(odb.getOrderNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					e.setStatus("0");
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			 List<Admin> saleManegers = this.securityService.getUsersByRoleAndArea("销售经理", curAdmin.getArea());
				if(CollectionUtils.isNotEmpty(saleManegers)){
					for(Admin admin : saleManegers){
						Email e = new Email();
						e.setAction("order");
						e.setDetail("关于【" + q.getProjectName() + "】的订单已经审核入库至" + odb.getArea() + "仓库！订单号为" + odb.getOrderNo());
						e.setQuoteId(q.getId());
						e.setQuoteNo(q.getProjectNum());
						e.setPurchaseId(pdb.getId());
						e.setPurchaseNo(pdb.getContractNo());
						e.setOrderId(odb.getId());
						e.setOrderNo(odb.getOrderNo());
						e.setSender(curAdmin.getUsername());
						e.setSendTime(new Date());
						e.setState("1");
						e.setUsername(admin.getUsername());
						e.setStatus("0");
						this.emailService.saveOrUpdateEntity(e);
					}
				}
				Set<String> salenames = q.getSalesman();
				if(CollectionUtils.isNotEmpty(salenames)){
					for(String name : salenames){
						Email e = new Email();
						e.setAction("order");
						e.setDetail("关于【" + q.getProjectName() + "】的订单已经审核入库至" + odb.getArea() + "仓库！订单号为" + odb.getOrderNo());
						e.setQuoteId(q.getId());
						e.setQuoteNo(q.getProjectNum());
						e.setPurchaseId(pdb.getId());
						e.setPurchaseNo(pdb.getContractNo());
						e.setOrderId(odb.getId());
						e.setOrderNo(odb.getOrderNo());
						e.setSender(curAdmin.getUsername());
						e.setSendTime(new Date());
						e.setState("1");
						e.setUsername(name);
						e.setStatus("0");
						this.emailService.saveOrUpdateEntity(e);
					}
				}
			saveSystemLog(LogModule.orderLog, curAdmin.getUsername()+"审核了订单"+order.getOrderNo());
			this.saveProStroage(order, qfdbList);
		}
		return "ok";
	}
	
	
	
	public String toImport(){
		String orderId = request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		return "toImport";
	}
	
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String startTime1 = request.getParameter("startTime1");
		String endTime1 = request.getParameter("endTime1");
		String startTime2 = request.getParameter("startTime2");
		String endTime2 = request.getParameter("endTime2");
		String supplier = request.getParameter("supplier");
		String vcfrom = request.getParameter("vcfrom");
		String vcModelNum = request.getParameter("vcModelNum");
		String orderNo = request.getParameter("orderNo");
		String expressNumber = request.getParameter("expressNumber");
		String isOrderConfirm = request.getParameter("isOrderConfirm");
		String isShipments = request.getParameter("isShipments");
		String orderStatus = request.getParameter("orderStatus");
		String area_zh = request.getParameter("area_zh");
		String isOver = request.getParameter("isOver");
		String htCode = request.getParameter("htCode");
		String comeCode = request.getParameter("comeCode");
		String isQC = request.getParameter("isQC");
		String isArrivalOver = request.getParameter("isArrivalOver");
		String isCaiwuOver = request.getParameter("isCaiwuOver");
		String isCaiwuPayOver = request.getParameter("isCaiwuPayOver");
		
		if(StringUtils.isNotEmpty(startTime1)){
			paramap.put("startTime1", startTime1);
			request.setAttribute("startTime1", startTime1);
		}
		if(StringUtils.isNotEmpty(endTime1)){
			paramap.put("endTime1", endTime1);
			request.setAttribute("endTime1", endTime1);
		}
		if(StringUtils.isNotEmpty(startTime2)){
			paramap.put("startTime2", startTime2);
			request.setAttribute("startTime2", startTime2);
		}
		if(StringUtils.isNotEmpty(endTime2)){
			paramap.put("endTime2", endTime2);
			request.setAttribute("endTime2", endTime2);
		}
		if(StringUtils.isNotEmpty(supplier)){
			paramap.put("supplier", supplier);
			request.setAttribute("supplier", supplier);
		}
		if(StringUtils.isNotEmpty(vcfrom)){
			paramap.put("vcfrom", vcfrom);
			request.setAttribute("vcfrom", vcfrom);
		}
		
		if(StringUtils.isNotEmpty(orderNo)){
			paramap.put("orderNo", orderNo);
			request.setAttribute("orderNo", orderNo);
		}
		if(StringUtils.isNotEmpty(expressNumber)){
			paramap.put("expressNumber", expressNumber);
			request.setAttribute("expressNumber", expressNumber);
		}
		if(StringUtils.isNotEmpty(isOrderConfirm)){
			paramap.put("isOrderConfirm", isOrderConfirm);
			request.setAttribute("isOrderConfirm", isOrderConfirm);
		}
		if(StringUtils.isNotEmpty(isShipments)){
			paramap.put("isShipments", isShipments);
			request.setAttribute("isShipments", isShipments);
		}
		if(StringUtils.isNotEmpty(orderStatus)){
			paramap.put("orderStatus", orderStatus);
			request.setAttribute("orderStatus", orderStatus);
		}
		if(StringUtils.isNotEmpty(area_zh)){
			paramap.put("area_zh", area_zh);
			request.setAttribute("area_zh", area_zh);
		}
		if(StringUtils.isNotEmpty(vcModelNum)){
			paramap.put("vcModelNum", vcModelNum);
			request.setAttribute("vcModelNum", vcModelNum);
		}
		if(StringUtils.isNotEmpty(isOver)){
			paramap.put("isOver", isOver);
			request.setAttribute("isOver", isOver);
		}
		if(StringUtils.isNotEmpty(htCode)){
			paramap.put("htCode", htCode);
			request.setAttribute("htCode", htCode);
		}
		if(StringUtils.isNotEmpty(comeCode)){
			paramap.put("comeCode", comeCode);
			request.setAttribute("comeCode", comeCode);
		}
		
		if(StringUtils.isNotEmpty(isQC)){
			paramap.put("isQC", isQC);
			request.setAttribute("isQC", isQC);
		}
		if(StringUtils.isNotEmpty(isArrivalOver)){
			paramap.put("isArrivalOver", isArrivalOver);
			request.setAttribute("isArrivalOver", isArrivalOver);
		}
		if(StringUtils.isNotEmpty(isCaiwuOver)){
			paramap.put("isCaiwuOver", isCaiwuOver);
			request.setAttribute("isCaiwuOver", isCaiwuOver);
		}
		if(StringUtils.isNotEmpty(isCaiwuPayOver)){
			paramap.put("isCaiwuPayOver", isCaiwuPayOver);
			request.setAttribute("isCaiwuPayOver", isCaiwuPayOver);
		}
		return paramap;
	}
	
	 public String importFile(){
		 String orderId = request.getParameter("orderId");
		 request.setAttribute("orderId", orderId);
		 String linkCode = "orderId_"+orderId;
		 return super.importFile(linkCode);
	 }
	 public String downloadFile(){
		 String orderId = request.getParameter("orderId");
		 String linkCode = "orderId_"+orderId;
		 return super.downloadFile(linkCode);
	 }
	 
	 public void downOrder() throws Exception {
		 Admin admin = this.getCurrentAdminUser();
			boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
			boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
			boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
			boolean isLess = false;
			if(isSale||isSaleManager||isQuoter){
				isLess = true;
			}
			request.setAttribute("isLess", isLess);
		 Long id = Long.valueOf(request.getParameter("id"));
			order = this.orderService.getOrderById(id);
			String fileUrl = "/model/order.xls";
			FileInputStream in = new FileInputStream(request.getRealPath(fileUrl));
			HSSFWorkbook book = new HSSFWorkbook(in);
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			HSSFFont font = book.createFont();
			font.setFontName("黑体");
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font.setFontHeightInPoints((short) 12);// 设置字体大小
			style.setFont(font);
			HSSFSheet sheet = book.getSheetAt(0);
			// 坐标从0开始
			int startRow = 8;
			HSSFRow row8 = sheet.getRow(startRow);
			if (row8 != null) {
				HSSFCell cell2 = row8.getCell(1);// 供应商
				if (cell2 != null) {
					// String dateStr = DATEFORMAT.format(order.getContractDate());
					cell2.setCellValue(order.getSupplier());
				}
				HSSFCell cell4 = row8.getCell(6);// 订单日期
				if (cell4 != null) {
					String dateStr = DateUtil.getShortDate(order.getOrderDate());
					cell4.setCellValue(dateStr);
				}
			}
			HSSFRow row9 = sheet.getRow(startRow + 1);
			if (row9 != null) {
				HSSFCell cell2 = row9.getCell(1);// 联系人
				if (cell2 != null) {
					cell2.setCellValue(order.getAtten());
				}
				HSSFCell cell4 = row9.getCell(6);// 经手人
				if (cell4 != null) {
					//if (StringUtils.isNotEmpty(order.getVcfrom())) {
						//Admin user = this.securityService.getUserWithPermissionByName(order.getVcfrom());
						cell4.setCellValue(order.getVcfrom()==null?"":order.getVcfrom());
					//}
				}
			}
			HSSFRow row10 = sheet.getRow(startRow + 2);
			if (row10 != null) {
				HSSFCell cell2 = row10.getCell(1);// 供应商电话
				if (cell2 != null) {
					cell2.setCellValue(order.getAttenTel());
				}
				HSSFCell cell4 = row10.getCell(6);// 经手人电话
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfromTel());
				}
			}
			HSSFRow row11 = sheet.getRow(startRow + 3);
			if (row11 != null) {
				HSSFCell cell2 = row11.getCell(1);// 供应商传真
				if (cell2 != null) {
					cell2.setCellValue(order.getAttenFax());
				}
				HSSFCell cell4 = row11.getCell(6);// 经手人传真
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfromFax());
				}
			}
			HSSFRow row12 = sheet.getRow(startRow + 4);
			if (row12 != null) {
				HSSFCell cell2 = row12.getCell(6);// 订单号
				if (cell2 != null) {
					// String dateStr = DATEFORMAT.format(order.getOrderDate());
					cell2.setCellValue(order.getOrderNo());
				}
			}
			HSSFRow row13 = sheet.getRow(startRow + 6);
			if (row11 != null) {
				HSSFCell cell4 = row13.getCell(6);// 订单号
				if (cell4 != null) {
					cell4.setCellValue(order.getOrderNo());
				}
			}
			HSSFRow row14 = sheet.getRow(startRow + 7);
			if (row14 != null) {
				HSSFCell cell4 = row14.getCell(1);// 注意事项
				if (cell4 != null) {
					String note = "";
					String noteThing2 = order.getNoteThing2();
					if ("1".equals(noteThing2)) {
						note = "请尽快出货！并提供运输单号！";
					} else if ("2".equals(noteThing2)) {
						note = "急！请今天出货!并提供运输单号！";
					} else if ("3".equals(noteThing2)) {
						note = "十万火急!请务必今天出货!并提供运输单号！";
					}
					cell4.setCellValue(note);
				}
			}
			HSSFRow row15 = sheet.getRow(startRow + 9);
			if (row15 != null) {
				HSSFCell cell4 = row15.getCell(1);// 备注
				if (cell4 != null) {
					cell4.setCellValue(order.getPaqueteRemark());
				}
			}
			HSSFRow row16 = sheet.getRow(startRow + 10);
			if (row16 != null) {
				HSSFCell cell4 = row16.getCell(1);// 包装材料2
				if (cell4 != null) {
					String paquete = "";
					String paquete2 = order.getPaquete2();
					if ("1".equals(paquete2)) {
						paquete = "特硬纸卷筒,两编一塑";
					} else if ("2".equals(paquete2)) {
						paquete = "特硬纸卷筒,纸箱(中间悬空)";
					} else if ("3".equals(paquete2)) {
						paquete = "打木条";
					} else if ("4".equals(paquete2)) {
						paquete = "打木架";
					}
					cell4.setCellValue(paquete);
				}
				HSSFCell cell5 = row16.getCell(5);// 付款方式
				if (cell5 != null) {
					cell5.setCellValue(order.getPayment());
				}
			}
			HSSFRow row17 = sheet.getRow(startRow + 11);
			if (row17 != null) {
				HSSFCell cell4 = row17.getCell(1);// 运输方式
				if (cell4 != null) {
					String note = "";
					String noteThing2 = order.getTransportMode();
					if ("1".equals(noteThing2)) {
						note = "顺丰";
					} else if ("2".equals(noteThing2)) {
						note = "顺丰四日件";
					} else if ("3".equals(noteThing2)) {
						note = "德邦精准卡航:广州芳村西朗站点";
					} else if ("4".equals(noteThing2)) {
						note = "德邦精准卡航:北京朝阳区百子湾站点";
					} else if ("5".equals(noteThing2)) {
						note = "和记(汽运-专线)";
					} else if ("6".equals(noteThing2)) {
						note = "海运快递(先垫付运费)";
					} else if ("7".equals(noteThing2)) {
						note = "普通海运(先垫付运费)";
					} else if ("8".equals(noteThing2)) {
						note = "待确认";
					}else if ("9".equals(noteThing2)) {
						note = "德邦精准卡航:送货上门";
					}
					cell4.setCellValue(note);
				}
			}

			HSSFRow row18 = sheet.getRow(startRow + 12);
			if (row11 != null) {
				HSSFCell cell4 = row18.getCell(1);// 收货地址
				if (cell4 != null) {
					cell4.setCellValue(order.getShipAddress());
				}
			}

			HSSFRow row19 = sheet.getRow(startRow + 13);
			if (row11 != null) {
				HSSFCell cell4 = row19.getCell(1);// 收货人
				if (cell4 != null) {
					String consignee = order.getConsignee();
					if("其他".equals(order.getConsignee())){
						consignee = order.getOtherConsignee()==null?"":order.getOtherConsignee();
					}
					cell4.setCellValue(consignee);
				}
			}

			List<QuoteFabric> qfList = new ArrayList<QuoteFabric>();
			for (QuoteFabric qf : order.getPurchase().getQuote().getQuoteFabric()) {
				if (!"1".equals(qf.getIsReplaced()) && order.getFactoryNum().equals(qf.getVcFactoryNum())) {
					qfList.add(qf);
				}
			}
			qfList = QuoteFabricUtil.sort(qfList, "getOrderId", "asc");
			int rows = qfList.size();
			int shifStartRow = 22;
			sheet.shiftRows(shifStartRow, sheet.getLastRowNum(), rows, true, false);
			HSSFRow row = sheet.getRow(shifStartRow);
			this.createCell(row, 7);

			HSSFCell ct0 = row.getCell(0);
			ct0.setCellStyle(style);
			ct0.setCellValue("序号");
			HSSFCell ct1 = row.getCell(1);
			ct1.setCellStyle(style);
			ct1.setCellValue("订货型号");
			HSSFCell ct1_2 = row.getCell(2);
			ct1_2.setCellStyle(style);
			HSSFCell ct1_3 = row.getCell(3);
			ct1_3.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(shifStartRow, // first row
																		// (0-based)
					shifStartRow, // last row (0-based)
					1, // first column (0-based)
					3 // last column (0-based)
					));
			HSSFCell ct2 = row.getCell(4);
			ct2.setCellStyle(style);
			ct2.setCellValue("数量");
			HSSFCell ct3 = row.getCell(5);
			ct3.setCellStyle(style);
			ct3.setCellValue("幅宽");
			HSSFCell ct4 = row.getCell(6);
			ct4.setCellStyle(style);
			ct4.setCellValue("备注");
			HSSFCell ct4_7 = row.getCell(7);
			ct4_7.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(shifStartRow, // first row
																		// (0-based)
					shifStartRow, // last row (0-based)
					6, // first column (0-based)
					7 // last column (0-based)
					));
			/*
			 * row.getCell(0).setCellValue("序号");
			 * row.getCell(1).setCellValue("订货型号");
			 * row.getCell(2).setCellValue("数量"); row.getCell(3).setCellValue("幅宽");
			 * row.getCell(4).setCellValue("备注");
			 */
			// HSSFCellStyle rowStyle = row.getRowStyle();
			for (int i = 0; i < rows; i++) {
				HSSFRow r = sheet.createRow(shifStartRow + i + 1);
				// r.setRowStyle(rowStyle);
				HSSFCell c1 = r.createCell(0);// 序号
				c1.setCellStyle(style);
				c1.setCellValue(qfList.get(i).getOrderId());
				if(isLess){
					if("1".equals(qfList.get(i).getIsHtCode())){
						qfList.get(i).setVcColorNum("");
					}
				}else{
					qfList.get(i).setVcModelNumDisplay(qfList.get(i).getVcModelNum());
				}
				HSSFCell c2 = r.createCell(1);// 型号
				c2.setCellStyle(style);
				String cv = qfList.get(i).getVcModelNumDisplay();
				if (StringUtils.isNotEmpty(qfList.get(i).getVcColorNum())) {
					cv += "-" + qfList.get(i).getVcColorNum();
				}
				c2.setCellValue(cv);
				HSSFCell c1_2 = r.createCell(2);
				c1_2.setCellStyle(style);
				HSSFCell c1_3 = r.createCell(3);
				c1_3.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(shifStartRow + i + 1, // first
																					// row
																					// (0-based)
						shifStartRow + i + 1, // last row (0-based)
						1, // first column (0-based)
						3 // last column (0-based)
						));
				HSSFCell c3 = r.createCell(4);// 实订货量
				c3.setCellStyle(style);
				c3.setCellValue(qfList.get(i).getVcQuoteNum() + " " + qfList.get(i).getVcOldPriceUnit());
				HSSFCell c4 = r.createCell(5);// 幅宽
				c4.setCellStyle(style);
				c4.setCellValue(qfList.get(i).getVcWidth() + " " + qfList.get(i).getVcWidthUnit());
				HSSFCell c5 = r.createCell(6);// 备注
				c5.setCellStyle(style);
				c5.setCellValue(qfList.get(i).getVcPurchaseRmk());
				HSSFCell c5_7 = r.createCell(7);
				c5_7.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(shifStartRow + i + 1, // first
																					// row
																					// (0-based)
						shifStartRow + i + 1, // last row (0-based)
						6, // first column (0-based)
						7 // last column (0-based)
						));
			}
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.orderLog, curAdminName+"下载了订单"+order.getOrderNo());
			String excelName = "order";
			// excelName = URLEncoder.encode(excelName, "gbk");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + excelName + ".xls");
			book.write(response.getOutputStream());
		}


		public void downOrder2() throws Exception {
			 Admin admin = this.getCurrentAdminUser();
				boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
				boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
				boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
				boolean isLess = false;
				if(isSale||isSaleManager||isQuoter){
					isLess = true;
				}
				request.setAttribute("isLess", isLess);
			Long id = Long.valueOf(request.getParameter("id"));
			order = this.orderService.getOrderById(id);
			String fileUrl = "/model/order2.xls";
			String wrPath = request.getRealPath("/files/order/");
			// File file = new File(wrPath+"/import"+id+".xls");
			FileInputStream in = new FileInputStream(request.getRealPath(fileUrl));
			HSSFWorkbook book = new HSSFWorkbook(in);
			CellStyle style2 = book.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			HSSFFont font = book.createFont();
			font.setFontName("黑体");
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font.setFontHeightInPoints((short) 12);// 设置字体大小
			style.setFont(font);
			HSSFSheet sheet = book.getSheetAt(0);
			// 坐标从0开始
			int startRow = 9;
			HSSFRow row8 = sheet.getRow(startRow);
			if (row8 != null) {
				HSSFCell cell2 = row8.getCell(1);// 供应商
				if (cell2 != null) {
					// String dateStr = DATEFORMAT.format(order.getContractDate());
					cell2.setCellValue(order.getSupplier());
				}
				HSSFCell cell4 = row8.getCell(5);// 订单日期
				if (cell4 != null) {
					String dateStr = DateUtil.getShortDate(order.getOrderDate());
					cell4.setCellValue(dateStr);
				}
			}
			HSSFRow row9 = sheet.getRow(startRow + 1);
			if (row9 != null) {
				HSSFCell cell2 = row9.getCell(1);// 供应商联系人
				if (cell2 != null) {
					cell2.setCellValue(order.getAtten());
				}
				HSSFCell cell4 = row9.getCell(5);// 经手人
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfrom());
				}
			}
			HSSFRow row10 = sheet.getRow(startRow + 2);
			if (row10 != null) {
				HSSFCell cell2 = row10.getCell(1);// 供应商电话
				if (cell2 != null) {
					cell2.setCellValue(order.getAttenTel());
				}
				HSSFCell cell4 = row10.getCell(5);// 经手人电话
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfromTel());
				}
			}
			HSSFRow row11 = sheet.getRow(startRow + 3);
			if (row11 != null) {
				HSSFCell cell2 = row11.getCell(1);// 供应商传真
				if (cell2 != null) {
					cell2.setCellValue(order.getAttenFax());
				}
				HSSFCell cell4 = row11.getCell(5);// 经手人传真
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfromFax());
				}
			}
			HSSFRow row12 = sheet.getRow(startRow + 4);
			if (row12 != null) {
				HSSFCell cell2 = row12.getCell(5);// 订单号
				if (cell2 != null) {
					cell2.setCellValue(order.getOrderNo());
				}
			}
			HSSFRow row13 = sheet.getRow(startRow + 7);
			if (row13 != null) {
				HSSFCell cell4 = row13.getCell(2);//
				if (cell4 != null) {
					String note = "";
					String shippingService = order.getShippingService();
					if ("1".equals(shippingService)) {
						note = "Fedex Intl Priority on our A/C: 7153 51862";
					} else if ("2".equals(shippingService)) {
						note = "Fedex Intl Economy on our A/C: 7153 51862";
					} else if ("3".equals(shippingService)) {
						note = "UPS Saver on your A/C and invoice us";
					} else if ("4".equals(shippingService)) {
						note = "UPS Expedited on your A/C and invoice us";
					} else if ("5".equals(shippingService)) {
						note = "DHL on your A/C and invoice us";
					} else if ("6".equals(shippingService)) {
						note = "TNT on your A/C and invoice us";
					} else if ("7".equals(shippingService)) {
						note = "To be advised";
					}else if ("8".equals(shippingService)) {
						note = "DHL on our A/C 967947655";
					}else if ("9".equals(shippingService)) {
						note = "UPS Saver on our A/C E8838F";
					}
					cell4.setCellValue(note);
				}
			}
			HSSFRow row14 = sheet.getRow(startRow + 8);
			if (row14 != null) {
				HSSFCell cell4 = row14.getCell(3);//
				if (cell4 != null) {
					cell4.setCellValue(order.getVcfrom());
					// cell4.setCellStyle(style2);
				}
			}
			HSSFRow row15 = sheet.getRow(startRow + 10);
			if (row15 != null) {
				HSSFCell cell4 = row15.getCell(5);//
				if (cell4 != null) {
					String dateStr = "";
					if (order.getShippingDate() != null) {
						dateStr = DateUtil.getShortDate(order.getShippingDate());
					}
					cell4.setCellValue(dateStr);
				}
			}
			HSSFRow row16 = sheet.getRow(startRow + 11);
			if (row16 != null) {
				HSSFCell cell4 = row16.getCell(2);//
				if (cell4 != null) {
					String paquete = "";
					String paquete2 = order.getPackingRemark();
					if ("1".equals(paquete2)) {
						paquete = "Pack it with roller and strenthen the packing";
					} else if ("2".equals(paquete2)) {
						paquete = "Pack it with Hard Paper Tube and double-layers Plastic Covering";
					} else if ("3".equals(paquete2)) {
						paquete = "Pack it with Hard Paper Tube and BOX";
					} else if ("4".equals(paquete2)) {
						paquete = "Pack it with Wooden Crate and keep it hanging";
					} else if ("5".equals(paquete2)) {
						paquete = "Pack and Fold it in Half If Available";
					} else if ("6".equals(paquete2)) {
						paquete = "Pack it with PAK";
					} else if ("7".equals(paquete2)) {
						paquete = "To be advised";
					}
					cell4.setCellValue(paquete);
				}
			}
			HSSFRow row17 = sheet.getRow(startRow + 12);
			if (row17 != null) {
				HSSFCell cell4 = row17.getCell(2);//
				if (cell4 != null) {
					String note = "";
					String noteThing2 = order.getPayment2();
					if ("1".equals(noteThing2)) {
						note = "Credit Card";
					} else if ("2".equals(noteThing2)) {
						note = "Statement";
					} else if ("3".equals(noteThing2)) {
						note = "Wire Transfer";
					}
					cell4.setCellValue(note);
				}
			}

			List<QuoteFabric> qfList = new ArrayList<QuoteFabric>();
			for (QuoteFabric qf : order.getPurchase().getQuote().getQuoteFabric()) {
				if (!"1".equals(qf.getIsReplaced()) && order.getFactoryNum().equals(qf.getVcFactoryNum())) {
					qfList.add(qf);
				}
			}
			qfList = QuoteFabricUtil.sort(qfList, "getOrderId", "asc");
			int rows = qfList.size();
			int shifStartRow = 22;
			sheet.shiftRows(shifStartRow, sheet.getLastRowNum(), rows, true, false);
			HSSFRow row = sheet.getRow(shifStartRow);
			this.createCell(row, 6);
			HSSFCell ct0 = row.getCell(0);
			ct0.setCellStyle(style);
			ct0.setCellValue("Item No#");
			HSSFCell ct1 = row.getCell(1);
			ct1.setCellStyle(style);
			ct1.setCellValue("Item Name");
			HSSFCell ct1_2 = row.getCell(2);
			ct1_2.setCellStyle(style);
			// HSSFCell ct1_3 = row.getCell(3);
			// ct1_3.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(shifStartRow, // first row
																		// (0-based)
					shifStartRow, // last row (0-based)
					1, // first column (0-based)
					2 // last column (0-based)
					));
			HSSFCell ct2 = row.getCell(3);
			ct2.setCellStyle(style);
			ct2.setCellValue("Quantity");
			HSSFCell ct3 = row.getCell(4);
			ct3.setCellStyle(style);
			ct3.setCellValue(" Width");
			HSSFCell ct4 = row.getCell(5);
			ct4.setCellStyle(style);
			ct4.setCellValue("Remark");
			HSSFCell c4_6 = row.getCell(6);
			c4_6.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(shifStartRow, // first row
																		// (0-based)
					shifStartRow, // last row (0-based)
					5, // first column (0-based)
					6 // last column (0-based)
					));
			// HSSFCellStyle rowStyle = row.getRowStyle();
			for (int i = 0; i < rows; i++) {
				HSSFRow r = sheet.createRow(shifStartRow + i + 1);
				// r.setRowStyle(rowStyle);
				HSSFCell c1 = r.createCell(0);// 序号
				c1.setCellStyle(style);
				c1.setCellValue(qfList.get(i).getOrderId());
				HSSFCell c2 = r.createCell(1);// 型号
				c2.setCellStyle(style);
				if(isLess){
					if("1".equals(qfList.get(i).getIsHtCode())){
						qfList.get(i).setVcColorNum("");
					}
				}else{
					qfList.get(i).setVcModelNumDisplay(qfList.get(i).getVcModelNum());
				}
				String cv = qfList.get(i).getVcModelNumDisplay();
				if (StringUtils.isNotEmpty(qfList.get(i).getVcColorNum())) {
					cv += "-" + qfList.get(i).getVcColorNum();
				}
				c2.setCellValue(cv);
				HSSFCell c2_2 = r.createCell(2);// 型号
				c2_2.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(shifStartRow + i + 1, // first
																					// row
																					// (0-based)
						shifStartRow + i + 1, // last row (0-based)
						1, // first column (0-based)
						2 // last column (0-based)
						));
				HSSFCell c3 = r.createCell(3);// 实订货量
				c3.setCellStyle(style);
				c3.setCellValue(qfList.get(i).getVcQuoteNum() + " " + qfList.get(i).getVcOldPriceUnit());
				HSSFCell c4 = r.createCell(4);// 幅宽
				c4.setCellStyle(style);
				c4.setCellValue(qfList.get(i).getVcWidth() + " " + qfList.get(i).getVcWidthUnit());
				HSSFCell c5 = r.createCell(5);// 备注
				c5.setCellStyle(style);
				c5.setCellValue(qfList.get(i).getVcPurchaseRmk());
				HSSFCell c5_6 = r.createCell(6);// 备注
				c5_6.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(shifStartRow + i + 1, // first
																					// row
																					// (0-based)
						shifStartRow + i + 1, // last row (0-based)
						5, // first column (0-based)
						6 // last column (0-based)
						));
			}
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.orderLog, curAdminName+"下载了订单"+order.getOrderNo());
			String excelName = "order2";
			// excelName = URLEncoder.encode(excelName, "gbk");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + excelName + ".xls");
			book.write(response.getOutputStream());
		}
		
		public String saveDesignOrder() throws ParseException {
			String orderNo = request.getParameter("orderNo");
			String id = request.getParameter("id");
			Order o = orderService.getOrderById(Long.valueOf(id));
			List<Order> oList = this.orderService.getOrderByOrderNo(orderNo);
			List<DesignerOrder> dodbList = designerOrderService.getDesignerOrderByOrderNo(orderNo);
			Map<Long,DesignerOrder> dodbMap = new HashMap<Long,DesignerOrder>();
			if(CollectionUtils.isNotEmpty(dodbList)){
				for(DesignerOrder dodb : dodbList){
					dodbMap.put(dodb.getOrderId(), dodb);
				}
			}
			String gatheringDate = request.getParameter("gatheringDate");
			if (StringUtils.isNotBlank(gatheringDate)) {
				designerOrder.setGatheringDate(DateUtil.getDate(gatheringDate));
			} else {
				designerOrder.setGatheringDate(null);
			}
			Purchase p = o.getPurchase();
			Quote quote = p.getQuote();
			designerOrder.setOrderId(o.getId());
			designerOrder.setOrderNo(o.getOrderNo());
			designerOrder.setVcSalesman(quote.getVcSalesman());
			designerOrder.setVcSalesman1(quote.getVcSalesman1());
			designerOrder.setVcSalesman2(quote.getVcSalesman2());
			designerOrder.setVcSalesman3(quote.getVcSalesman3());
			designerOrder.setVcSalesman4(quote.getVcSalesman4());
			designerOrder.setContractDate(p.getContractDate());
			designerOrder.setContractNo(p.getContractNo());
			designerOrder.setQuoteLocal(quote.getVcQuoteLocal());
			if (designerOrder.getShareArea1().equals(quote.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony1());
			} else if (designerOrder.getShareArea2().equals(quote.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony2());
			} else if (designerOrder.getShareArea3().equals(quote.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony3());
			} else if (designerOrder.getShareArea4().equals(quote.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony4());
			} else if (designerOrder.getShareArea5().equals(quote.getVcQuoteLocal())) {
				designerOrder.setRealTotel(designerOrder.getShareMony5());
			} else {
				designerOrder.setRealTotel(0F);
			}
			designerOrder.setHasNoApplyTotle(o.getPurchase().getQuote().getSumMoney() - (designerOrder.getHasApplyTotle()));
			for(Order entity :oList){
				DesignerOrder donew = null;
				DesignerOrder dodb = dodbMap.get(entity.getId());
				donew = (DesignerOrder)designerOrder.clone();
				donew.setOrderId(entity.getId());
				if(dodb != null){
					donew.setId(dodb.getId());
				}
				 
				designerOrderService.saveOrUpdateEntity(donew);
			}
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.orderLog, curAdminName+"设计了订单"+o.getOrderNo());
			return "ok";
		}

		private void saveProStroage(Order o, List<QuoteFabric> qfs) {
			List<StoreFabric> sfdbList = storeFabricService.getStoreFabricByOrderId(o.getId());
			if(CollectionUtils.isNotEmpty(sfdbList)){
				storeFabricService.deleteAll(sfdbList);
			}
			String vcLocal = o.getArea();
			String storeLocal = "广州仓库";
			if ("SH".equals(vcLocal)) {
				storeLocal = "上海仓库";
			} else if ("SZ".equals(vcLocal)) {
				storeLocal = "深圳仓库";
			} else if ("BJ".equals(vcLocal)) {
				storeLocal = "北京仓库";
			} else if ("HK".equals(vcLocal)) {
				storeLocal = "香港仓库";
			}
			Quote q = o.getPurchase().getQuote();
			Store s = this.storeService.getStore(storeLocal);
			for(QuoteFabric f : qfs){
				StoreFabric sf = new StoreFabric();
				sf.setQuoteId(q.getId());
				sf.setStoreId(s.getId());
				sf.setStoreName(s.getStoreName());
				sf.setOrderId(o.getId());
				sf.setOrderNo(o.getOrderNo());
				sf.setFabricNo("");
				sf.setSupplie(o.getSupplier());
				sf.setPayment(o.getPayment());
				sf.setInStoreDate(new Date());
				sf.setVcProject(q.getProjectName());
				sf.setVcModelNum(f.getVcModelNum());
				sf.setVcColorNum(f.getVcColorNum());
				sf.setVcFactoryCode(f.getVcFactoryCode());
				sf.setVcRmk(f.getVcRmk());
				sf.setVcQuoteNum(f.getVcQuoteNum());
				sf.setVcAssignAutor(o.getVcfrom());
				sf.setVcSubLay(f.getVcSubLay());//分铺端量
				sf.setSurplus(f.getVcRealityAog());//剩余数量=实际到货
				sf.setVcRealityAog(f.getVcRealityAog());//实际到货
				sf.setIsHtCode(f.getIsHtCode());
				sf.setHtCode(f.getHtCode());
				sf.setVcSalesman(q.getVcSalesman());
				sf.setVcSalesman1(q.getVcSalesman1());
				sf.setVcSalesman2(q.getVcSalesman2());
				sf.setVcSalesman3(q.getVcSalesman3());
				sf.setVcSalesman4(q.getVcSalesman4());
				sf.setUnit(f.getVcOldPriceUnit());
				sf.setFileName(f.getFilePath());
				sf.setQuoteNum(q.getProjectNum());
				sf.setDisplayNum(f.getVcModelNumDisplay());
				if("1".equals(f.getIsHidden())&&StringUtils.isNotBlank(f.getReplaceId())){
					sf.setDisplayNum(f.getReplaceId());
				}
				sf.setVcWidth(f.getVcWidth());
				sf.setVcWidthUnit(f.getVcWidthUnit());
				sf.setVcPurchaseRmk(f.getVcPurchaseRmk());
				sf.setOrderQuantity(f.getOrderQuantity());
				sf.setArrivalNum(f.getVcRealityAog());
				sf.setOrderDate(o.getOrderDate());
				sf.setIsOrderConfirm(o.getIsOrderConfirm());
				sf.setDeliveryRequirements(o.getPurchase().getDeliveryRequirements());
				sf.setArrivalDate(o.getShipDateRemark());
				this.storeFabricService.saveOrUpdateEntity(sf);
				this.saveSystemLog(sf.getVcModelNum()+"_"+sf.getVcFactoryCode(),storeLocal+"到货，型号:"+sf.getVcModelNum()+", 数量:"+sf.getVcRealityAog());
			}
		}
		
		public String design() {
			Long orderId = Long.valueOf(request.getParameter("id"));
			designerOrder = designerOrderService.getDesignerOrderByOrderId(orderId);
			Order order = this.orderService.getOrderById(orderId);
			Float sumMoney = order.getPurchase().getQuote().getSumMoney();
			request.setAttribute("sumMoney", sumMoney);
			return "design";
		}
		
		public String getOrderStatusForEmail(){
			String emailId = request.getParameter("emailId");
			if(StringUtils.isNotBlank(emailId)){
				Email e = this.emailService.getEmailById(Long.valueOf(emailId));
				e.setState("2");
				this.emailService.saveOrUpdateEntity(e);
			}
			String status = request.getParameter("status");
			String orderId = request.getParameter("orderId");
			Order o = this.orderService.getOrderById(Long.valueOf(orderId));
			int orderStatus = o.getOrderStatus();
			String canDo = "1";
			if("1".equals(status)){//订单未提交，要去提交
				if(orderStatus>0){
					canDo = "0";
				}
			}else if("2".equals(status)){//待采购单已经提交，要去审核
				if(orderStatus>2){
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
		/**
		 * 获取其它货币对RMB或HKD的汇率
		 */
		/*private float getExchangeRate(String quoteFormate, String priceCur) {

			float vcExchangeRate = 1;
			if ("1".equals(quoteFormate)) {
				if (!"RMB".equals(priceCur) || !"rmb".equals(priceCur)) {// 比如采购时是按美元采购的，这里就是查出美元对人民币的汇率，针对内地报价
					vcExchangeRate = this.currencyConversionService.getExchangeRate(priceCur,"RMB");
				}
			} else if ("2".equals(quoteFormate)) {
				if (!"HKD".equals(priceCur) || !"hkd".equals(priceCur)) {// 比如采购时是按美元采购的，这里就是查出美元对港币的汇率，针对香港报价
					vcExchangeRate = this.currencyConversionService.getExchangeRate(priceCur,"HKD");
				}
			}
			return vcExchangeRate;
		}*/
	public List<QuoteFabric> getQuoteFabricList() {
		return quoteFabricList;
	}

	public void setQuoteFabricList(List<QuoteFabric> quoteFabricList) {
		this.quoteFabricList = quoteFabricList;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public DesignerOrder getDesignerOrder() {
		return designerOrder;
	}
	public void setDesignerOrder(DesignerOrder designerOrder) {
		this.designerOrder = designerOrder;
	}
	
	public void downloadDesignOrder() throws ParseException {
		String policyBeginDayStr = request.getParameter("policyBeginDay");
		String policyEndDayStr = request.getParameter("policyEndDay");
		String sellman = request.getParameter("designer");
		String contractNo = request.getParameter("contractNo");
		String orderNo = request.getParameter("orderNo");
		// String quoteLocal = request.getParameter("quoteLocal");
		String flag = request.getParameter("flag");// flag=1,销售收入表;2,个人销售收入表;3,销售收入表dora;4,收款表;5,销售成本表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		Page<DesignerOrder> pagede = new Page<DesignerOrder>(9999);
		pagede.setOrder(Page.DESC);
		pagede.setOrderBy("contractDate");
		String userName = getCurUserName();
		User user = securityEntityManager.findUserByLoginName(userName);
		if (!(AuthUtil.checkRole(user, "管理员") || AuthUtil.checkRole(user, "超级财务"))) {
			// if (StringUtils.isNotBlank(user.getArea())) {
			filters.add(new PropertyFilter("EQS_quoteLocal", user.getArea()));
			// }
		}

		if (StringUtils.isNotBlank(policyBeginDayStr)) {
			Date policyBeginDay = DATEFORMATHOUR.parse(policyBeginDayStr);
			filters.add(new PropertyFilter("GED_contractDate", policyBeginDay));
		}
		if (StringUtils.isNotBlank(policyEndDayStr)) {
			Date policyEndDay = DATEFORMATHOUR.parse(policyEndDayStr);
			filters.add(new PropertyFilter("LED_contractDate", policyEndDay));
		}

		if (sellman != null && !"".equals(sellman)) {
			filters.add(new PropertyFilter("EQS_vcSalesman-OR-vcSalesman1-OR-vcSalesman2-OR-vcSalesman3-OR-vcSalesman4", sellman));
		}
		if (StringUtils.isNotEmpty(contractNo)) {
			filters.add(new PropertyFilter("LIKES_contractNo", contractNo));
		}
		if (StringUtils.isNotEmpty(orderNo)) {
			filters.add(new PropertyFilter("LIKES_orderNo", orderNo));
		}
		pagede = this.designerOrderManager.getPageDesignerOrder(pagede, filters);
		List<DesignerOrder> list = pagede.getResult();
		downloadDesignExpense(list, sellman, flag, user.getArea());
	}

	public void downloadDesignExpense(List<DesignerOrder> list, String sellman, String flag, String area) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileUrl = "";
		FileInputStream in = null;
		// 1,销售收入表;2,个人销售收入表;3,销售收入表dora;4,收款表;5,销售成本表
		try {
			if ("1".equals(flag)) {
				fileUrl = "model/销售收入表.xls";
			} else if ("2".equals(flag)) {
				fileUrl = "model/个人销售收入表.xls";
			} else if ("3".equals(flag)) {
				fileUrl = "model/销售收入表dora.xls";
			} else if ("4".equals(flag)) {
				fileUrl = "model/收款表.xls";
			} else if ("5".equals(flag)) {
				fileUrl = "model/销售成本表.xls";
			} else if ("6".equals(flag)) {
				fileUrl = "model/材料明细.xls";
			}

			in = new FileInputStream(request.getRealPath(fileUrl));
			HSSFWorkbook book = new HSSFWorkbook(in);
			CreationHelper createHelper = book.getCreationHelper();
			HSSFSheet sheet = book.getSheetAt(0);
			if ("1".equals(flag)) {
				HSSFRow row1 = sheet.getRow(1);
				HSSFCell cell0 = row1.getCell(0);
				String value = cell0.getStringCellValue();
				String lastValue = value + " " + area;
				cell0.setCellValue(lastValue);
				HSSFRow titleRow = sheet.getRow(2);
				HSSFRow row3 = sheet.getRow(3);
				HSSFCell cell8 = row3.getCell(8);
				HSSFCell cell9 = row3.getCell(9);
				HSSFCell cell10 = row3.getCell(10);
				HSSFCell cell11 = row3.getCell(11);
				HSSFCell cell12 = row3.getCell(12);
				if ("GZ".equals(area)) {
					cell8.setCellValue("本地收入（GZ）");
				}
				if ("BJ".equals(area)) {
					cell8.setCellValue("本地收入（BJ）");
					cell9.setCellValue("GZ");
				}
				if ("SH".equals(area)) {
					cell8.setCellValue("本地收入（SH）");
					cell10.setCellValue("GZ");
				}
				if ("SZ".equals(area)) {
					cell8.setCellValue("本地收入（SZ）");
					cell11.setCellValue("GZ");
				}
				if ("HK".equals(area)) {
					cell8.setCellValue("本地收入（HK）");
					cell12.setCellValue("GZ");
				}
				int startRow = 4;// 起始行
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					HSSFRow row = sheet.createRow(startRow);
					startRow++;
					createCell(row, titleRow.getLastCellNum());
					CellStyle cellStyle = book.createCellStyle();
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
					row.getCell(0).setCellValue(de.getOrderNo());
					HSSFCell cell = row.getCell(1);
					cell.setCellValue(de.getContractDate());
					cell.setCellStyle(cellStyle);
					row.getCell(2).setCellValue(de.getContractNo());
					row.getCell(3).setCellValue(q.getVcAttnName());
					row.getCell(4).setCellValue(q.getProjectName());
					String sellsman = "";
					if (StringUtils.isNotEmpty(de.getVcSalesman())) {
						sellsman += de.getVcSalesman() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman1())) {
						sellsman += de.getVcSalesman1() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman2())) {
						sellsman += de.getVcSalesman2() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman3())) {
						sellsman += de.getVcSalesman3() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman4())) {
						sellsman += de.getVcSalesman4() + ",";
					}
					row.getCell(5).setCellValue(sellsman);
					List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
					DesignerExpense d = new DesignerExpense();
					if (des != null && des.size() > 0) {
						d = des.get(des.size() - 1);
					}
					String designners = "";
					if (StringUtils.isNotEmpty(d.getDesigner1())) {
						designners += d.getDesigner1() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner2())) {
						designners += d.getDesigner2() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner3())) {
						designners += d.getDesigner3() + ",";
					}
					row.getCell(6).setCellValue(designners);
					row.getCell(7).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));

					if ("GZ".equals(area)) {
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("BJ".equals(area)) {
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SH".equals(area)) {
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SZ".equals(area)) {
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("HK".equals(area)) {
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
					}
					Float st = de.getSharetotle() == null ? 0F : de.getSharetotle();
					row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -st : st));
					Float rt = de.getRealTotel() == null ? 0F : de.getRealTotel();
					row.getCell(14).setCellValue(("1".equals(q.getOffsetQuote()) ? -rt : rt));
					row.getCell(15).setCellValue(de.getType());
					row.getCell(16).setCellValue(de.getIsInvoice());
					row.getCell(17).setCellValue(de.getRemark());
				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(6);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(7);

				HSSFCell sm1TotleCell = lastrow.createCell(8);
				HSSFCell sm2TotleCell = lastrow.createCell(9);
				HSSFCell sm3TotleCell = lastrow.createCell(10);
				HSSFCell sm4TotleCell = lastrow.createCell(11);
				HSSFCell sm5TotleCell = lastrow.createCell(12);
				HSSFCell stTotleCell = lastrow.createCell(13);
				HSSFCell rtTotleCell = lastrow.createCell(14);
				float sumTotle = 0F;
				float sm1Totle = 0F;
				float sm2Totle = 0F;
				float sm3Totle = 0F;
				float sm4Totle = 0F;
				float sm5Totle = 0F;
				float stTotle = 0F;
				float rtTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
						stTotle -= de.getSharetotle() == null ? 0F : de.getSharetotle();
						rtTotle -= de.getRealTotel() == null ? 0F : de.getRealTotel();
						if ("GZ".equals(area)) {
							sm1Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm2Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
						}
					} else {
						sumTotle += q.getSumMoney();
						stTotle += de.getSharetotle() == null ? 0F : de.getSharetotle();
						rtTotle += de.getRealTotel() == null ? 0F : de.getRealTotel();
						if ("GZ".equals(area)) {
							sm1Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm2Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
						}
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				sm1TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm1Totle) + "");
				sm2TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm2Totle) + "");
				sm3TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm3Totle) + "");
				sm4TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm4Totle) + "");
				sm5TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm5Totle) + "");
				stTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(stTotle) + "");
				rtTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(rtTotle) + "");
			} else if ("2".equals(flag)) {
				HSSFRow row1 = sheet.getRow(1);
				HSSFCell cell0 = row1.getCell(0);
				String value = cell0.getStringCellValue();
				String lastValue = value + " " + sellman == null ? "" : sellman;
				cell0.setCellValue(lastValue);
				HSSFRow titleRow = sheet.getRow(2);
				int startRow = 3;// 起始行
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					Quote q = o.getPurchase().getQuote();
					HSSFRow row = sheet.createRow(startRow);
					startRow++;
					createCell(row, titleRow.getLastCellNum());
					CellStyle cellStyle = book.createCellStyle();
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
					// row.getCell(0).setCellValue(de.getOrderNo());
					HSSFCell cell = row.getCell(0);
					cell.setCellValue(de.getContractDate());
					cell.setCellStyle(cellStyle);
					row.getCell(1).setCellValue(de.getContractNo());
					row.getCell(2).setCellValue(q.getVcAttnName());
					row.getCell(3).setCellValue(q.getProjectName());
					row.getCell(4).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));
					/*
					 * String sellsman = "";
					 * if(StringUtils.isNotEmpty(de.getVcSalesman())){ sellsman +=
					 * de.getVcSalesman()+","; }
					 * if(StringUtils.isNotEmpty(de.getVcSalesman1())){ sellsman +=
					 * de.getVcSalesman1()+","; }
					 * if(StringUtils.isNotEmpty(de.getVcSalesman2())){ sellsman +=
					 * de.getVcSalesman2()+","; }
					 * if(StringUtils.isNotEmpty(de.getVcSalesman3())){ sellsman +=
					 * de.getVcSalesman3()+","; }
					 * if(StringUtils.isNotEmpty(de.getVcSalesman4())){ sellsman +=
					 * de.getVcSalesman4()+","; }
					 * row.getCell(5).setCellValue(sellsman);
					 */
					if ("GZ".equals(area)) {
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(5).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						row.getCell(7).setCellValue(de.getShareArea3() == null ? "" : de.getShareArea3());// 北京
						row.getCell(8).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareArea2() == null ? "" : de.getShareArea2());// 上海
						row.getCell(10).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(11).setCellValue(de.getShareArea4() == null ? "" : de.getShareArea4());// 深圳
						row.getCell(12).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(13).setCellValue(de.getShareArea5() == null ? "" : de.getShareArea5());// 香港
						row.getCell(14).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
					}
					if ("BJ".equals(area)) {
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(5).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						row.getCell(7).setCellValue(de.getShareArea1() == null ? "" : de.getShareArea1());// 广州
						row.getCell(8).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
						row.getCell(9).setCellValue(de.getShareArea2() == null ? "" : de.getShareArea2());// 上海
						row.getCell(10).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(11).setCellValue(de.getShareArea4() == null ? "" : de.getShareArea4());// 深圳
						row.getCell(12).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(13).setCellValue(de.getShareArea5() == null ? "" : de.getShareArea5());// 香港
						row.getCell(14).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
					}
					if ("SH".equals(area)) {
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(5).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						row.getCell(7).setCellValue(de.getShareArea3() == null ? "" : de.getShareArea3());// 北京
						row.getCell(8).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareArea1() == null ? "" : de.getShareArea1());// 广州
						row.getCell(10).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
						row.getCell(11).setCellValue(de.getShareArea4() == null ? "" : de.getShareArea4());// 深圳
						row.getCell(12).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(13).setCellValue(de.getShareArea5() == null ? "" : de.getShareArea5());// 香港
						row.getCell(14).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
					}
					if ("SZ".equals(area)) {
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(5).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						row.getCell(7).setCellValue(de.getShareArea3() == null ? "" : de.getShareArea3());// 北京
						row.getCell(8).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareArea2() == null ? "" : de.getShareArea2());// 上海
						row.getCell(10).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(11).setCellValue(de.getShareArea1() == null ? "" : de.getShareArea1());// 广州
						row.getCell(12).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
						row.getCell(13).setCellValue(de.getShareArea5() == null ? "" : de.getShareArea5());// 香港
						row.getCell(14).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
					}
					if ("HK".equals(area)) {
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(5).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						row.getCell(7).setCellValue(de.getShareArea3() == null ? "" : de.getShareArea3());// 北京
						row.getCell(8).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareArea2() == null ? "" : de.getShareArea2());// 上海
						row.getCell(10).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(11).setCellValue(de.getShareArea4() == null ? "" : de.getShareArea4());// 深圳
						row.getCell(12).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(13).setCellValue(de.getShareArea1() == null ? "" : de.getShareArea1());// 广州
						row.getCell(14).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
					}
					List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
					DesignerExpense d = new DesignerExpense();
					if (des != null && des.size() > 0) {
						d = des.get(des.size() - 1);
					}
					row.getCell(15).setCellValue(d.getDesigner1());
					row.getCell(16).setCellValue(d.getDesignMony1() == null ? 0F : d.getDesignMony1());
					row.getCell(17).setCellValue(d.getDesigner2());
					row.getCell(18).setCellValue(d.getDesignMony2() == null ? 0F : d.getDesignMony2());
					row.getCell(19).setCellValue(d.getDesigner3());
					row.getCell(20).setCellValue(d.getDesignMony3() == null ? 0F : d.getDesignMony3());
					row.getCell(21).setCellValue(de.getRemark());
				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(3);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(4);
				HSSFCell localTotleCell = lastrow.createCell(6);
				float sumTotle = 0F;
				float localTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
						if ("GZ".equals(area)) {
							localTotle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
						} else if ("SH".equals(area)) {
							localTotle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
						} else if ("BJ".equals(area)) {
							localTotle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
						} else if ("SZ".equals(area)) {
							localTotle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
						} else if ("HK".equals(area)) {
							localTotle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						}
					} else {
						sumTotle += q.getSumMoney();
						if ("GZ".equals(area)) {
							localTotle += de.getShareMony1() == null ? 0F : de.getShareMony1();
						} else if ("SH".equals(area)) {
							localTotle += de.getShareMony2() == null ? 0F : de.getShareMony2();
						} else if ("BJ".equals(area)) {
							localTotle += de.getShareMony3() == null ? 0F : de.getShareMony3();
						} else if ("SZ".equals(area)) {
							localTotle += de.getShareMony4() == null ? 0F : de.getShareMony4();
						} else if ("HK".equals(area)) {
							localTotle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						}
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				localTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(localTotle) + "");
			} else if ("4".equals(flag)) {
				HSSFRow titleRow = sheet.getRow(1);
				int startRow = 2;// 起始行
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					Quote q = o.getPurchase().getQuote();
					HSSFRow row = sheet.createRow(startRow);
					startRow++;
					createCell(row, titleRow.getLastCellNum());
					CellStyle cellStyle = book.createCellStyle();
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
					row.getCell(0).setCellValue(de.getOrderNo());
					HSSFCell cell = row.getCell(1);
					cell.setCellValue(de.getContractDate());
					cell.setCellStyle(cellStyle);
					row.getCell(2).setCellValue(de.getContractNo());
					row.getCell(3).setCellValue(q.getVcAttnName());
					row.getCell(4).setCellValue(q.getProjectName());
					String sellsman = "";
					if (StringUtils.isNotEmpty(de.getVcSalesman())) {
						sellsman += de.getVcSalesman() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman1())) {
						sellsman += de.getVcSalesman1() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman2())) {
						sellsman += de.getVcSalesman2() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman3())) {
						sellsman += de.getVcSalesman3() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman4())) {
						sellsman += de.getVcSalesman4() + ",";
					}
					row.getCell(5).setCellValue(sellsman);
					List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
					DesignerExpense d = new DesignerExpense();
					if (des != null && des.size() > 0) {
						d = des.get(des.size() - 1);
					}
					String designners = "";
					if (StringUtils.isNotEmpty(d.getDesigner1())) {
						designners += d.getDesigner1() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner2())) {
						designners += d.getDesigner2() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner3())) {
						designners += d.getDesigner3() + ",";
					}
					row.getCell(6).setCellValue(designners);
					row.getCell(7).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));
					String shareArea = "";
					String shareRate = "";
					String shareMoney = "";
					if ("GZ".equals(area)) {
						row.getCell(8).setCellValue(de.getShareRate1() == null ? 0F : de.getShareRate1());
						row.getCell(9).setCellValue(de.getShareMony1() == null ? 0F : de.getShareMony1());// 广州
						if (StringUtils.isNotEmpty(de.getShareArea2())) {
							shareArea += de.getShareArea2() + ",";
							shareRate += de.getShareRate2() + ",";
							shareMoney += de.getShareMony2() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea3())) {
							shareArea += de.getShareArea3() + ",";
							shareRate += de.getShareRate3() + ",";
							shareMoney += de.getShareMony3() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea4())) {
							shareArea += de.getShareArea4() + ",";
							shareRate += de.getShareRate4() + ",";
							shareMoney += de.getShareMony4() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea5())) {
							shareArea += de.getShareArea5() + ",";
							shareRate += de.getShareRate5() + ",";
							shareMoney += de.getShareMony5() + ",";
						}
						row.getCell(10).setCellValue(shareArea);
						row.getCell(11).setCellValue(shareRate);
						row.getCell(12).setCellValue(shareMoney);
					}
					if ("BJ".equals(area)) {
						row.getCell(8).setCellValue(de.getShareRate3() == null ? 0F : de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareMony3() == null ? 0F : de.getShareMony3());
						if (StringUtils.isNotEmpty(de.getShareArea2())) {
							shareArea += de.getShareArea2() + ",";
							shareRate += de.getShareRate2() + ",";
							shareMoney += de.getShareMony2() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea1())) {
							shareArea += de.getShareArea1() + ",";
							shareRate += de.getShareRate1() + ",";
							shareMoney += de.getShareMony1() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea4())) {
							shareArea += de.getShareArea4() + ",";
							shareRate += de.getShareRate4() + ",";
							shareMoney += de.getShareMony4() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea5())) {
							shareArea += de.getShareArea5() + ",";
							shareRate += de.getShareRate5() + ",";
							shareMoney += de.getShareMony5() + ",";
						}
						row.getCell(10).setCellValue(shareArea);
						row.getCell(11).setCellValue(shareRate);
						row.getCell(12).setCellValue(shareMoney);
					}
					if ("SH".equals(area)) {
						row.getCell(8).setCellValue(de.getShareRate2() == null ? 0F : de.getShareRate2());
						row.getCell(9).setCellValue(de.getShareMony2() == null ? 0F : de.getShareMony2());
						if (StringUtils.isNotEmpty(de.getShareArea3())) {
							shareArea += de.getShareArea3() + ",";
							shareRate += de.getShareRate3() + ",";
							shareMoney += de.getShareMony3() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea1())) {
							shareArea += de.getShareArea1() + ",";
							shareRate += de.getShareRate1() + ",";
							shareMoney += de.getShareMony1() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea4())) {
							shareArea += de.getShareArea4() + ",";
							shareRate += de.getShareRate4() + ",";
							shareMoney += de.getShareMony4() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea5())) {
							shareArea += de.getShareArea5() + ",";
							shareRate += de.getShareRate5() + ",";
							shareMoney += de.getShareMony5() + ",";
						}

						row.getCell(10).setCellValue(shareArea);
						row.getCell(11).setCellValue(shareRate);
						row.getCell(12).setCellValue(shareMoney);
					}
					if ("SZ".equals(area)) {
						row.getCell(8).setCellValue(de.getShareRate4() == null ? 0F : de.getShareRate4());
						row.getCell(9).setCellValue(de.getShareMony4() == null ? 0F : de.getShareMony4());
						if (StringUtils.isNotEmpty(de.getShareArea2())) {
							shareArea += de.getShareArea2() + ",";
							shareRate += de.getShareRate2() + ",";
							shareMoney += de.getShareMony2() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea1())) {
							shareArea += de.getShareArea1() + ",";
							shareRate += de.getShareRate1() + ",";
							shareMoney += de.getShareMony1() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea3())) {
							shareArea += de.getShareArea3() + ",";
							shareRate += de.getShareRate3() + ",";
							shareMoney += de.getShareMony3() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea5())) {
							shareArea += de.getShareArea5() + ",";
							shareRate += de.getShareRate5() + ",";
							shareMoney += de.getShareMony5() + ",";
						}
						row.getCell(10).setCellValue(shareArea);
						row.getCell(11).setCellValue(shareRate);
						row.getCell(12).setCellValue(shareMoney);
					}
					if ("HK".equals(area)) {
						row.getCell(8).setCellValue(de.getShareRate5() == null ? 0F : de.getShareRate5());
						row.getCell(9).setCellValue(de.getShareMony5() == null ? 0F : de.getShareMony5());
						if (StringUtils.isNotEmpty(de.getShareArea2())) {
							shareArea += de.getShareArea2() + ",";
							shareRate += de.getShareRate2() + ",";
							shareMoney += de.getShareMony2() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea1())) {
							shareArea += de.getShareArea1() + ",";
							shareRate += de.getShareRate1() + ",";
							shareMoney += de.getShareMony1() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea4())) {
							shareArea += de.getShareArea4() + ",";
							shareRate += de.getShareRate4() + ",";
							shareMoney += de.getShareMony4() + ",";
						}
						if (StringUtils.isNotEmpty(de.getShareArea3())) {
							shareArea += de.getShareArea3() + ",";
							shareRate += de.getShareRate3() + ",";
							shareMoney += de.getShareMony3() + ",";
						}
						row.getCell(10).setCellValue(shareArea);
						row.getCell(11).setCellValue(shareRate);
						row.getCell(12).setCellValue(shareMoney);
					}

					row.getCell(13).setCellValue(d.getDesignTotelMoney() == null ? 0F : d.getDesignTotelMoney());
					row.getCell(14).setCellValue(q.getContainTax() == null ? 0F : q.getContainTax());
					row.getCell(15).setCellValue(de.getIsInvoice());
					row.getCell(16).setCellValue(de.getFrontMoney() == null ? 0F : de.getFrontMoney());
					row.getCell(17).setCellValue(de.getPlanMoney1() == null ? 0F : de.getPlanMoney1());
					row.getCell(18).setCellValue(de.getPlanMoney2() == null ? 0F : de.getPlanMoney2());
					row.getCell(19).setCellValue(de.getPlanMoney3() == null ? 0F : de.getPlanMoney3());
					row.getCell(20).setCellValue(de.getQualityMoney() == null ? 0F : de.getQualityMoney());
					Float ha = de.getHasApplyTotle() == null ? 0F : de.getHasApplyTotle();
					row.getCell(21).setCellValue(("1".equals(q.getOffsetQuote()) ? -ha : ha));
					Float hna = de.getHasNoApplyTotle() == null ? 0F : de.getHasNoApplyTotle();
					row.getCell(22).setCellValue(("1".equals(q.getOffsetQuote()) ? -hna : hna));
					row.getCell(23).setCellValue(de.getIsPayTotle());
					row.getCell(24).setCellValue(de.getGatheringArea());
					row.getCell(25).setCellValue(de.getRemark());
				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(6);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(7);
				HSSFCell haleCell = lastrow.createCell(21);
				HSSFCell hnaCell = lastrow.createCell(22);
				float sumTotle = 0F;
				float haTotle = 0F;
				float hnaTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
						haTotle -= de.getHasApplyTotle() == null ? 0F : de.getHasApplyTotle();
						hnaTotle -= de.getHasNoApplyTotle() == null ? 0F : de.getHasNoApplyTotle();
					} else {
						sumTotle += q.getSumMoney();
						haTotle += de.getHasApplyTotle() == null ? 0F : de.getHasApplyTotle();
						hnaTotle += de.getHasNoApplyTotle() == null ? 0F : de.getHasNoApplyTotle();
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				haleCell.setCellValue(ProPrice.getTwoDecimalFloat(haTotle) + "");
				hnaCell.setCellValue(ProPrice.getTwoDecimalFloat(hnaTotle) + "");
			} else if ("3".equals(flag)) {// 销售收入表dora
				HSSFRow row1 = sheet.getRow(1);
				HSSFCell cell0 = row1.getCell(0);
				String value = cell0.getStringCellValue();
				String lastValue = value + " " + area;
				cell0.setCellValue(lastValue);
				HSSFRow titleRow = sheet.getRow(2);
				HSSFRow row3 = sheet.getRow(3);
				HSSFCell cell9 = row3.getCell(9);
				HSSFCell cell10 = row3.getCell(10);
				HSSFCell cell11 = row3.getCell(11);
				HSSFCell cell12 = row3.getCell(12);
				HSSFCell cell13 = row3.getCell(13);
				if ("GZ".equals(area)) {
					cell9.setCellValue("本地收入（GZ）");
				}
				if ("BJ".equals(area)) {
					cell9.setCellValue("本地收入（BJ）");
					cell10.setCellValue("GZ");
				}
				if ("SH".equals(area)) {
					cell9.setCellValue("本地收入（SH）");
					cell11.setCellValue("GZ");
				}
				if ("SZ".equals(area)) {
					cell9.setCellValue("本地收入（SZ）");
					cell12.setCellValue("GZ");
				}
				if ("HK".equals(area)) {
					cell9.setCellValue("本地收入（HK）");
					cell13.setCellValue("GZ");
				}
				float yueTotle = 0F;
				int startRow = 4;// 起始行
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					Quote q = o.getPurchase().getQuote();
					HSSFRow row = sheet.createRow(startRow);
					startRow++;
					createCell(row, titleRow.getLastCellNum());
					CellStyle cellStyle = book.createCellStyle();
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
					row.getCell(0).setCellValue(de.getOrderNo());
					HSSFCell cell = row.getCell(1);
					cell.setCellValue(de.getContractDate());
					cell.setCellStyle(cellStyle);
					row.getCell(2).setCellValue(q.getVcFrom());
					row.getCell(3).setCellValue(de.getContractNo());
					row.getCell(4).setCellValue(q.getVcAttnName());
					row.getCell(5).setCellValue(q.getProjectName());
					String sellsman = "";
					if (StringUtils.isNotEmpty(de.getVcSalesman())) {
						sellsman += de.getVcSalesman() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman1())) {
						sellsman += de.getVcSalesman1() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman2())) {
						sellsman += de.getVcSalesman2() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman3())) {
						sellsman += de.getVcSalesman3() + ",";
					}
					if (StringUtils.isNotEmpty(de.getVcSalesman4())) {
						sellsman += de.getVcSalesman4() + ",";
					}
					row.getCell(6).setCellValue(sellsman);
					List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
					DesignerExpense d = new DesignerExpense();
					if (des != null && des.size() > 0) {
						d = des.get(des.size() - 1);
					}
					String designners = "";
					if (StringUtils.isNotEmpty(d.getDesigner1())) {
						designners += d.getDesigner1() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner2())) {
						designners += d.getDesigner2() + ",";
					}
					if (StringUtils.isNotEmpty(d.getDesigner3())) {
						designners += d.getDesigner3() + ",";
					}
					row.getCell(7).setCellValue(designners);
					row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));

					if ("GZ".equals(area)) {
						/*
						 * row.getCell(9).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						 * row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						 * row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						 * row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						 * row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						 */
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("BJ".equals(area)) {
						/*
						 * row.getCell(9).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						 * row.getCell(10).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						 * row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						 * row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						 * row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						 */
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SH".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						// row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港

						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SZ".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(12).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						// row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港

						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("HK".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(13).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州

						Float sm5 = de.getShareMony5() == null ? 0F : de.getShareMony5();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						Float sm3 = de.getShareMony3() == null ? 0F : de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 = de.getShareMony2() == null ? 0F : de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4() == null ? 0F : de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm1 = de.getShareMony1() == null ? 0F : de.getShareMony1();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
					}
					Float mon1 = de.getMon1() == null ? 0F : de.getMon1();
					Float mon2 = de.getMon2() == null ? 0F : de.getMon2();
					Float mon3 = de.getMon3() == null ? 0F : de.getMon3();
					Float mon4 = de.getMon4() == null ? 0F : de.getMon4();
					Float mon5 = de.getMon5() == null ? 0F : de.getMon5();
					Float mon6 = de.getMon6() == null ? 0F : de.getMon6();
					Float mon7 = de.getMon7() == null ? 0F : de.getMon7();
					Float mon8 = de.getMon8() == null ? 0F : de.getMon8();
					Float mon9 = de.getMon9() == null ? 0F : de.getMon9();
					Float mon10 = de.getMon10() == null ? 0F : de.getMon10();
					Float mon11 = de.getMon11() == null ? 0F : de.getMon11();
					Float mon12 = de.getMon12() == null ? 0F : de.getMon12();
					row.getCell(14).setCellValue(mon1);
					row.getCell(15).setCellValue(mon2);
					row.getCell(16).setCellValue(mon3);
					row.getCell(17).setCellValue(mon4);
					row.getCell(18).setCellValue(mon5);
					row.getCell(19).setCellValue(mon6);
					row.getCell(20).setCellValue(mon7);
					row.getCell(21).setCellValue(mon8);
					row.getCell(22).setCellValue(mon9);
					row.getCell(23).setCellValue(mon10);
					row.getCell(24).setCellValue(mon11);
					row.getCell(25).setCellValue(mon12);
					Float balance = q.getSumMoney() - mon1 - mon2 - mon3 - mon4 - mon5 - mon6 - mon7 - mon8 - mon9 - mon10 - mon11 - mon12;
					if ("1".equals(q.getOffsetQuote())) {
						yueTotle -= balance;
					} else {
						yueTotle += balance;
					}
					row.getCell(26).setCellValue("1".equals(q.getOffsetQuote()) ? -balance : balance);
					row.getCell(27).setCellValue(de.getType());
					row.getCell(28).setCellValue(de.getIsInvoice());
					row.getCell(29).setCellValue(de.getRemark());
				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(7);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(8);

				HSSFCell sm1TotleCell = lastrow.createCell(9);
				HSSFCell sm2TotleCell = lastrow.createCell(10);
				HSSFCell sm3TotleCell = lastrow.createCell(11);
				HSSFCell sm4TotleCell = lastrow.createCell(12);
				HSSFCell sm5TotleCell = lastrow.createCell(13);
				HSSFCell yueTotleCell = lastrow.createCell(26);
				float sumTotle = 0F;
				float sm1Totle = 0F;
				float sm2Totle = 0F;
				float sm3Totle = 0F;
				float sm4Totle = 0F;
				float sm5Totle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
						if ("GZ".equals(area)) {
							sm1Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm2Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm5Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle -= de.getShareMony5() == null ? 0F : de.getShareMony5();
							sm2Totle -= de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle -= de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle -= de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle -= de.getShareMony1() == null ? 0F : de.getShareMony1();
						}
					} else {
						sumTotle += q.getSumMoney();
						if ("GZ".equals(area)) {
							sm1Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm2Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
							sm5Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle += de.getShareMony5() == null ? 0F : de.getShareMony5();
							sm2Totle += de.getShareMony3() == null ? 0F : de.getShareMony3();
							sm3Totle += de.getShareMony2() == null ? 0F : de.getShareMony2();
							sm4Totle += de.getShareMony4() == null ? 0F : de.getShareMony4();
							sm5Totle += de.getShareMony1() == null ? 0F : de.getShareMony1();
						}
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				sm1TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm1Totle) + "");
				sm2TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm2Totle) + "");
				sm3TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm3Totle) + "");
				sm4TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm4Totle) + "");
				sm5TotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sm5Totle) + "");
				yueTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(yueTotle) + "");
			} else if ("5".equals(flag)) {// 成本表
				HSSFRow titleRow = sheet.getRow(1);
				int startRow = 3;// 起始行
				Float bjTotle = 0F;
				Float cbTotle = 0F;
				Float maoliTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					Purchase p = o.getPurchase();
					Quote q = p.getQuote();
					Set<QuoteFabric> sQF = q.getQuoteFabric();
					List<QuoteFabric> listcb = new ArrayList<QuoteFabric>();
					List<QuoteFabric> listbj = new ArrayList<QuoteFabric>();
					Iterator it = sQF.iterator();
					while (it.hasNext()) {
						QuoteFabric quoteFabric = (QuoteFabric) it.next();
						if (!"1".equals(quoteFabric.getIsReplaced()) && quoteFabric.getVcFactoryCode().equals(o.getFactoryCode())) {
							listcb.add(quoteFabric);
							if ("1".equals(quoteFabric.getIsHidden())) {
								String fabricId = quoteFabric.getReplaceId();
								Long quoteId = quoteFabric.getQuote().getNmId();
								if (fabricId != null && !"".equals(fabricId)) {
									String[] code = fabricId.split("_");
									List<QuoteFabric> xxqf = quoteFabricManager.getQuoteFabricByCodeAndQuoteId(code[0], code[1], quoteId);
									if (xxqf != null && xxqf.size() > 0) {
										listbj.add(xxqf.get(0));
									}
								}
							} else {
								listbj.add(quoteFabric);
							}
						}
					}
					int allQuoteNum = 0;
					for (int i = 0; i < listbj.size(); i++) {
						allQuoteNum += listbj.get(i).getVcQuantity().intValue();
					}

					for (int i = 0; i < listbj.size(); i++) {
						HSSFRow row = sheet.createRow(startRow);
						startRow++;
						createCell(row, titleRow.getLastCellNum());
						CellStyle cellStyle = book.createCellStyle();
						cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
						HSSFCell cell = row.getCell(0);
						cell.setCellValue(de.getContractDate());
						cell.setCellStyle(cellStyle);
						row.getCell(1).setCellValue(de.getContractNo());
						row.getCell(2).setCellValue(de.getOrderNo());
						row.getCell(3).setCellValue(q.getVcAttnName());
						row.getCell(4).setCellValue(q.getProjectName());
						row.getCell(5).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));
						// row.getCell(6).setCellValue(listbj.get(i).getVcModelNum());
						float clFre = 0.0f;
						// 后处理
						clFre += StringUtils.isNotEmpty(q.getVcAftertreatment()) ? Float.valueOf(q.getVcAftertreatment()) : 0.0f;
						// 其他
						clFre += StringUtils.isNotEmpty(q.getVcOther()) ? Float.valueOf(q.getVcOther()) : 0.0f;
						// 电机合计
						clFre += q.getEngineTotal() == null ? 0.0f : q.getEngineTotal();
						// 阻燃
						clFre += q.getFlameTotal() == null ? 0.0f : q.getEngineTotal();
						clFre += (q.getInputCol1() == null ? 0.0f : q.getInputCol1())
								+ (q.getInputCol2() == null ? 0.0f : q.getInputCol2())
								+ (q.getInputCol3() == null ? 0.0f : q.getInputCol3())
								+ (q.getInputCol4() == null ? 0.0f : q.getInputCol4())
								+ (q.getInputCol5() == null ? 0.0f : q.getInputCol5());
						row.getCell(6).setCellValue(clFre);

						// row.getCell(7).setCellValue(listbj.get(i).getVcQuantity()+"
						// "+listbj.get(i).getVcPriceUnit());
						// 加工费
						row.getCell(7).setCellValue(StringUtils.isNotEmpty(q.getVcProcessFre()) ? Float.valueOf(q.getVcProcessFre()) : 0.0f);
						/*
						 * if("2".equals(q.getQuoteFormate())){
						 * row.getCell(7).setCellValue(listbj.get(i).getVcPrice()*this.getExchangeRate("1",
						 * "HKD")); }else{
						 */
						// row.getCell(8).setCellValue(listbj.get(i).getVcPrice()+"
						// "+listbj.get(i).getVcMoney());
						// 量窗费
						row.getCell(8).setCellValue(q.getLcFre() == null ? 0.0f : q.getLcFre());
						// }
						String freight = ProPrice.getTwoDecimalFloat((((q.getLowestFreight() == null ? 0F : q.getLowestFreight()) / allQuoteNum) * listbj.get(i).getVcQuantity())) + "";
						// row.getCell(9).setCellValue(freight);
						// 安装费
						row.getCell(9).setCellValue(StringUtils.isNotEmpty(q.getVcInstallFre()) ? Float.valueOf(q.getVcInstallFre()) : 0.0f);
						// row.getCell(10).setCellValue(q.getContainTax());
						float tranFre = 0.0f;
						for (QuoteFabric qf : listbj) {
							tranFre += (qf.getVcPrice() - qf.getVcOldPrice()) * qf.getVcQuantity();
						}
						// 最低运费
						tranFre += q.getLowestFreight() == null ? 0.0f : q.getLowestFreight();
						// 货到工地运费
						tranFre += q.getArriveTransport() == null ? 0.0f : q.getArriveTransport();
						// 加急费
						tranFre += q.getUrgentCost() == null ? 0.0f : q.getUrgentCost();
						// 运费
						row.getCell(10).setCellValue(tranFre);
						// if("1".equals(listbj.get(i).getOffsetQF())){
						// bjTotle -= listbj.get(i).getVcTotal();
						// }else{
						// bjTotle += listbj.get(i).getVcTotal();
						// }
						// row.getCell(11).setCellValue(("1".equals(listbj.get(i).getOffsetQF()))?-listbj.get(i).getVcTotal():listbj.get(i).getVcTotal());
						// 税率
						row.getCell(11).setCellValue(q.getContainTax());

						float total = (clFre + (StringUtils.isNotEmpty(q.getVcProcessFre()) ? Float.valueOf(q.getVcProcessFre()) : 0.0f)
								+ (q.getLcFre() == null ? 0.0f : q.getLcFre())
								+ (StringUtils.isNotEmpty(q.getVcInstallFre()) ? Float.valueOf(q.getVcInstallFre()) : 0.0f) + tranFre) * q.getContainTax();
						bjTotle += total;
						// 合计
						row.getCell(12).setCellValue(total);
						float clTotal = p.getClTotal() == null ? 0.0f : p.getClTotal();
						cbTotle += clTotal;
						// 材料合计
						row.getCell(13).setCellValue(clTotal);
						// row.getCell(14).setCellValue(listcb.get(i).getVcQuoteNum()==null?0+"":listcb.get(i).getVcQuoteNum()+"
						// "+listbj.get(i).getVcOldPriceUnit());
						// 加工费
						row.getCell(14).setCellValue(p.getProcessFee() == null ? 0.0f : p.getProcessFee());
						float cbjia = listcb.get(i).getShijia() == null ? 0F : listcb.get(i).getShijia();
						if ("2".equals(q.getQuoteFormate())) {
							cbjia = cbjia * this.getExchangeRate("2", listcb.get(i).getPriceCur());
						} else {
							cbjia = cbjia * this.getExchangeRate("1", listcb.get(i).getPriceCur());
						}
						// String cbjiastr =
						// ProPrice.getTwoDecimalFloat(cbjia)+"";
						// row.getCell(15).setCellValue(cbjiastr+"
						// "+listbj.get(i).getVcMoney());
						// 安装费
						row.getCell(15).setCellValue(p.getInstallFee() == null ? 0.0f : p.getInstallFee());
						// if("1".equals(listbj.get(i).getOffsetQF())){
						// cbTotle -=
						// ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia);
						// }else{
						// cbTotle +=
						// ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia);
						// }
						// row.getCell(16).setCellValue(("1".equals(listbj.get(i).getOffsetQF()))?-ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia):ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia));
						// 运费
						row.getCell(16).setCellValue(o.getFreight() == null ? "0" : o.getFreight() + "");
						// row.getCell(17).setCellValue(q.getVcProcessFre());
						// 差旅费
						row.getCell(17).setCellValue(p.getTravelExpenses() == null ? 0.0f : p.getTravelExpenses());
						// row.getCell(18).setCellValue(q.getVcInstallFre());
						// 设计费
						List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
						Float designTotelMoney = 0F;
						if (des != null && des.size() > 0) {
							designTotelMoney = des.get(0).getDesignTotelMoney() == null ? 0F
									: des.get(0).getDesignTotelMoney();
							row.getCell(18).setCellValue(designTotelMoney);
						}
						// row.getCell(19).setCellValue(o.getFreight()==null?"0":o.getFreight()+"");
						// 税费
						Float sellFre = 0F;
						sellFre = (StringUtils.isEmpty(q.getVcProcessFre()) ? 0F : new Float(q.getVcProcessFre()))
								+ (StringUtils.isEmpty(q.getVcInstallFre()) ? 0F : new Float(q.getVcInstallFre()))
								+ (o.getFreight() == null ? 0F : o.getFreight())
								+ (StringUtils.isEmpty(q.getVcTravelFre()) ? 0F : new Float(q.getVcTravelFre()))
								+ (designTotelMoney)
								+ (q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1))
								+ (StringUtils.isEmpty(q.getVcOther()) ? 0F : new Float(q.getVcOther()));
						row.getCell(19).setCellValue(q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1));
						// 其他
						row.getCell(20).setCellValue(p.getOtherFre() == null ? 0.0f : p.getOtherFre());
						// 毛利
						Float maoli = listbj.get(i).getVcTotal() - (listcb.get(i).getVcQuoteNum() * cbjia) - sellFre;
						maoliTotle += maoli;
						row.getCell(21).setCellValue(ProPrice.getTwoDecimalFloat(maoli) + "");
						// 毛利率
						row.getCell(22).setCellValue(maoli / listbj.get(i).getVcTotal());
						// List<DesignerExpense> des =
						// designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
						// //设计费
						// Float designTotelMoney = 0F;
						// if(des!=null && des.size()>0){
						// designTotelMoney =
						// des.get(0).getDesignTotelMoney()==null?0F:des.get(0).getDesignTotelMoney();
						// row.getCell(21).setCellValue(designTotelMoney);
						// }

						// Float sellFre = 0F;
						// sellFre =
						// (StringUtils.isEmpty(q.getVcProcessFre())?0F:new
						// Float(q.getVcProcessFre()))+(StringUtils.isEmpty(q.getVcInstallFre())?0F:new
						// Float(q.getVcInstallFre()))+
						// (o.getFreight()==null?0F:o.getFreight())+(StringUtils.isEmpty(q.getVcTravelFre())?0F:new
						// Float(q.getVcTravelFre()))+
						// (designTotelMoney)+(q.getSumMoney()/q.getContainTax()*(q.getContainTax()-1))+
						// (StringUtils.isEmpty(q.getVcOther())?0F:new
						// Float(q.getVcOther()));
						// row.getCell(22).setCellValue(q.getSumMoney()/q.getContainTax()*(q.getContainTax()-1));
						// row.getCell(23).setCellValue(q.getVcOther());
						// if("1".equals(listbj.get(i).getOffsetQF())){
						// maoliTotle -=
						// listbj.get(i).getVcTotal()-(listcb.get(i).getVcQuoteNum()*cbjia)-sellFre;
						// }else{
						// maoliTotle +=
						// listbj.get(i).getVcTotal()-(listcb.get(i).getVcQuoteNum()*cbjia)-sellFre;
						// }
						// Float maoli =
						// listbj.get(i).getVcTotal()-(listcb.get(i).getVcQuoteNum()*cbjia)-sellFre;
						// row.getCell(24).setCellValue(ProPrice.getTwoDecimalFloat(maoli)+"");
						// row.getCell(25).setCellValue(maoli/listbj.get(i).getVcTotal());
					}

				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(4);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(5);
				HSSFCell bjTotleCell = lastrow.createCell(12);
				HSSFCell cbTotleCell = lastrow.createCell(13);
				HSSFCell maoliTotleCell = lastrow.createCell(21);
				float sumTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
					} else {
						sumTotle += q.getSumMoney();
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				bjTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(bjTotle) + "");
				cbTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(cbTotle) + "");
				maoliTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(maoliTotle) + "");
			} else if ("6".equals(flag)) {// 材料明细
				HSSFRow titleRow = sheet.getRow(1);
				int startRow = 3;
				Float bjTotle = 0F;
				Float cbTotle = 0F;
				Float maoliTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					Quote q = o.getPurchase().getQuote();
					Set<QuoteFabric> sQF = q.getQuoteFabric();
					List<QuoteFabric> listcb = new ArrayList<QuoteFabric>();
					List<QuoteFabric> listbj = new ArrayList<QuoteFabric>();
					Iterator it = sQF.iterator();
					while (it.hasNext()) {
						QuoteFabric quoteFabric = (QuoteFabric) it.next();
						if (!"1".equals(quoteFabric.getIsReplaced()) && quoteFabric.getVcFactoryCode().equals(o.getFactoryCode())) {
							listcb.add(quoteFabric);
							if ("1".equals(quoteFabric.getIsHidden())) {
								String fabricId = quoteFabric.getReplaceId();
								Long quoteId = quoteFabric.getQuote().getNmId();
								if (fabricId != null && !"".equals(fabricId)) {
									String[] code = fabricId.split("_");
									List<QuoteFabric> xxqf = quoteFabricManager.getQuoteFabricByCodeAndQuoteId(code[0], code[1], quoteId);
									if (xxqf != null && xxqf.size() > 0) {
										listbj.add(xxqf.get(0));
									}
								}
							} else {
								listbj.add(quoteFabric);
							}
						}
					}
					int allQuoteNum = 0;
					for (int i = 0; i < listbj.size(); i++) {
						allQuoteNum += listbj.get(i).getVcQuantity().intValue();
					}

					for (int i = 0; i < listbj.size(); i++) {
						HSSFRow row = sheet.createRow(startRow);
						startRow++;
						createCell(row, titleRow.getLastCellNum());
						CellStyle cellStyle = book.createCellStyle();
						cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
						HSSFCell cell = row.getCell(0);
						cell.setCellValue(de.getContractDate());
						cell.setCellStyle(cellStyle);
						row.getCell(1).setCellValue(de.getContractNo());
						row.getCell(2).setCellValue(de.getOrderNo());
						row.getCell(3).setCellValue(q.getVcAttnName());
						row.getCell(4).setCellValue(q.getProjectName());
						row.getCell(5).setCellValue(("1".equals(q.getOffsetQuote()) ? -q.getSumMoney() : q.getSumMoney()));
						row.getCell(6).setCellValue(listbj.get(i).getVcModelNum());
						row.getCell(7).setCellValue(listbj.get(i).getVcQuantity() + " " + listbj.get(i).getVcPriceUnit());
						/*
						 * if("2".equals(q.getQuoteFormate())){
						 * row.getCell(7).setCellValue(listbj.get(i).getVcPrice()*this.getExchangeRate("1",
						 * "HKD")); }else{
						 */
						row.getCell(8).setCellValue(listbj.get(i).getVcPrice() + " " + listbj.get(i).getVcMoney());
						// }
//						String freight = ProPrice.getTwoDecimalFloat((((q.getLowestFreight() == null ? 0F : q.getLowestFreight()) / allQuoteNum) * listbj.get(i).getVcQuantity())) + "";
//						row.getCell(9).setCellValue(freight);
						row.getCell(9).setCellValue(q.getContainTax());
//						if ("1".equals(listbj.get(i).getOffsetQF())) {
//							bjTotle -= listbj.get(i).getVcTotal();
//						} else {
//							bjTotle += listbj.get(i).getVcTotal();
//						}
						float quantity = listbj.get(i).getVcQuantity() == null ? 0.0f : listbj.get(i).getVcQuantity();
						float price = listbj.get(i).getVcPrice() == null ? 0.0f : listbj.get(i).getVcPrice();
						float tax = q.getContainTax() == null ? 0.0f : q.getContainTax();
						float ptotal = quantity * price * tax;
						ptotal = "1".equals(q.getOffsetQuote()) ? -ptotal : ptotal;
						//产品报价合计
						bjTotle += ptotal;
						//产品报价合计
						row.getCell(10).setCellValue(ptotal);
						row.getCell(11).setCellValue(listcb.get(i).getVcModelNum());
						row.getCell(12).setCellValue(listcb.get(i).getVcQuoteNum() == null ? 0 + "" : listcb.get(i).getVcQuoteNum() + " " + listbj.get(i).getVcOldPriceUnit());
						float cbjia = listcb.get(i).getShijia() == null ? 0F : listcb.get(i).getShijia();
						if ("2".equals(q.getQuoteFormate())) {
							cbjia = cbjia * this.getExchangeRate("2", listcb.get(i).getPriceCur());
						} else {
							cbjia = cbjia * this.getExchangeRate("1", listcb.get(i).getPriceCur());
						}
						String cbjiastr = ProPrice.getTwoDecimalFloat(cbjia) + "";
						row.getCell(13).setCellValue(cbjiastr + " " + listbj.get(i).getVcMoney());
//						if ("1".equals(listbj.get(i).getOffsetQF())) {
//							cbTotle -= ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum() * cbjia * q.getContainTax());
//						} else {
//							cbTotle += ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum() * cbjia * q.getContainTax());
//						}
						//销售成本税率
						row.getCell(14).setCellValue(q.getContainTax());
						float selltotal = "1".equals(q.getOffsetQuote()) ? -ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum() * cbjia * q.getContainTax()) : ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum() * cbjia * q.getContainTax());
						//销售成本总金额
						cbTotle += selltotal;
						//销售成本合计
						row.getCell(15).setCellValue(selltotal);
						
//						row.getCell(15).setCellValue(q.getVcProcessFre());
//						row.getCell(16).setCellValue(q.getVcInstallFre());
//						row.getCell(17).setCellValue(o.getFreight() == null ? "0" : o.getFreight() + "");
//						row.getCell(18).setCellValue(q.getVcTravelFre());
						List<DesignerExpense> des = designerExpenseManager.getDesignerExpenseByQuoteId(q.getNmId());
						Float sellFre = 0F;
						Float designTotelMoney = 0F;
						if (des != null && des.size() > 0) {
							designTotelMoney = des.get(0).getDesignTotelMoney() == null ? 0F : des.get(0).getDesignTotelMoney();
//							row.getCell(19).setCellValue(designTotelMoney);
						}
						sellFre = (StringUtils.isEmpty(q.getVcProcessFre()) ? 0F : new Float(q.getVcProcessFre())) + (StringUtils.isEmpty(q.getVcInstallFre()) ? 0F : new Float(q.getVcInstallFre()))
								+ (o.getFreight() == null ? 0F : o.getFreight()) + (StringUtils.isEmpty(q.getVcTravelFre()) ? 0F : new Float(q.getVcTravelFre()))
								+ (designTotelMoney)
								+ (q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1))
								+ (StringUtils.isEmpty(q.getVcOther()) ? 0F : new Float(q.getVcOther()));
//						row.getCell(20).setCellValue(q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1));
//						row.getCell(21).setCellValue(q.getVcOther());
//						if ("1".equals(listbj.get(i).getOffsetQF())) {
//							maoliTotle -= listbj.get(i).getVcTotal() - (listcb.get(i).getVcQuoteNum() * cbjia) - sellFre;
//						} else {
//							maoliTotle += listbj.get(i).getVcTotal() - (listcb.get(i).getVcQuoteNum() * cbjia) - sellFre;
//						}
						//产品报价-销售成本=毛利
						Float maoli = ptotal - selltotal;
						maoliTotle += maoli;
						row.getCell(16).setCellValue(ProPrice.getTwoDecimalFloat(maoli) + "");
						row.getCell(17).setCellValue(maoli / ptotal);
					}

				}
				HSSFRow lastrow = sheet.createRow(startRow + 1);
				HSSFCell sumTotleCellTitle = lastrow.createCell(4);
				sumTotleCellTitle.setCellValue("总金额：");
				HSSFCell sumTotleCell = lastrow.createCell(5);
				HSSFCell bjTotleCell = lastrow.createCell(10);
				HSSFCell cbTotleCell = lastrow.createCell(15);
				HSSFCell maoliTotleCell = lastrow.createCell(16);
				float sumTotle = 0F;
				for (DesignerOrder de : list) {
					Order o = this.orderManager.getOrder(de.getOrderId());
					if (!(o instanceof Order)) {
						continue;
					}
					Purchase p = o.getPurchase();
					if (p == null) {
						continue;
					}
					Quote q = p.getQuote();
					if (q == null) {
						continue;
					}
					if ("1".equals(q.getOffsetQuote())) {
						sumTotle -= q.getSumMoney();
					} else {
						sumTotle += q.getSumMoney();
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				bjTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(bjTotle) + "");
				cbTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(cbTotle) + "");
				maoliTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(maoliTotle) + "");
			}
			String excelName = "designOrder";
			excelName = URLEncoder.encode(excelName, "gbk");
			this.getResponse().setContentType("application/x-msdownload");
			this.getResponse().setHeader("Content-Disposition",
					"attachment; filename=" + excelName + ".xls");
			book.write(this.getResponse().getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


	
}
