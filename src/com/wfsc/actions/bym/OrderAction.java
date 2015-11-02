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
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.CurrencyConversion;
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
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.services.bym.service.IDesignerOrderService;
import com.wfsc.services.bym.service.IEmailService;
import com.wfsc.services.bym.service.IFabricService;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IPurchaseService;
import com.wfsc.services.bym.service.IQuoteFabricService;
import com.wfsc.services.bym.service.IQuoteService;
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
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean isSysAdmin = securityService.isAbleRole(admin.getUsername(), "系统管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		boolean isPurMan = securityService.isAbleRole(admin.getUsername(), "采购员");
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
		boolean isLess = false;
		if(isSale||isSaleManager||isQuoter){
			isLess = true;
		}
		request.setAttribute("isLess", isLess);
	//	request.setAttribute("isAdmin", isAdmin);
	//	request.setAttribute("purManager", purManager);
	//	request.setAttribute("purMan", purMan);
		Page<Order> page = new Page<Order>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		if(!isAdmin&&!isPurManager&&!isPurMan&&!isSysAdmin){
			paramap.put("area", admin.getArea());
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
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isQuoter = securityService.isAbleRole(admin.getUsername(), "报价员");
		boolean isLess = false;
		if(isSale||isSaleManager||isQuoter){
			isLess = true;
		}
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
			List<QuoteFabric> qfList =  QuoteFabricUtil.sort(qfSet, "getVcIndex", "asc");
			for(QuoteFabric qf : qfList){
				if(!"1".equals(qf.getIsReplaced()) && qf.getVcFactoryNum().equals(order.getFactoryNum())){
					float sigMoney = qf.getSinglePrice() * qf.getVcPurDis();
					sigMoney = (float) (Math.round((sigMoney) * 10)) / 10;
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
					}else{
						qf.setVcModelNumDisplay(qf.getVcModelNum());
					}
					quoteFabricList.add(qf);//不是被替代的产品才在采购单中显示
				}
			}
			quoteFabricList = QuoteFabricUtil.sort(quoteFabricList, "getOrderId", "asc");
		}
		if(order.getSumMoney()>0){
			sumMoney = order.getSumMoney();
		}
		sumMoney = (float) (Math.round((sumMoney) * 10)) / 10;
		order.setSumMoney(sumMoney);
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
						note = "Fedex Intl Priority on our A/C:2820 72386";
					} else if ("2".equals(shippingService)) {
						note = "Fedex Intl Economy on our A/C:2820 72386";
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


	
}
