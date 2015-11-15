package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import com.wfsc.common.bo.bym.Email;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.Purchase;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.common.bo.bym.QuoteFabric;
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IEmailService;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IPurchaseService;
import com.wfsc.services.bym.service.IQuoteFabricService;
import com.wfsc.services.bym.service.IStoreFabricService;
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
@Controller("PurchaseAction")
@Scope("prototype")
public class PurchaseAction extends DispatchPagerAction {

	private static final long serialVersionUID = 684081059299260353L;
	
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "supplierService")
	private ISupplierService supplierService;
	@Resource(name = "quoteFabricService")
	private IQuoteFabricService quoteFabricService;
	@Resource(name = "purchaseService")
	private IPurchaseService purchaseService;
	@Resource(name = "orderService")
	private IOrderService orderService;
	@Resource(name = "storeFabricService")
	private IStoreFabricService storeFabricService;
	@Resource(name = "emailService")
	private IEmailService emailService;
	private Purchase purchase;
	private List<QuoteFabric> quoteFabricList = new ArrayList<QuoteFabric>();

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	public String managerToPur(){
		this.setTopMenu();
		listToPur();
		return "managerToPur";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "管理员");
		boolean purManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		boolean purMan = securityService.isAbleRole(admin.getUsername(), "采购员");
	//	request.setAttribute("isAdmin", isAdmin);
	//	request.setAttribute("purManager", purManager);
	//	request.setAttribute("purMan", purMan);
		Page<Purchase> page = new Page<Purchase>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		paramap.put("purchaseType", "2");
		if(!isAdmin&&!purManager&&!purMan){
			paramap.put("area", admin.getArea());
		}
		page = purchaseService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/purchase_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		boolean isPermissionAudit = true;
		Map<String,String> map = this.getPropertiesMap();
		int money = new Integer(map.get("money"));
		String manager = map.get("manager");
		boolean canAudit = false;
		if(page.getData()!=null){
			for(Purchase p : page.getData()){
				if(p.getQuote().getSumMoney()<money||manager.contains(admin.getUsername())||isAdmin){
					canAudit = true;
				}
				p.setCanAudit(canAudit);
				if(StringUtils.isNotBlank(p.getOtheraddress())){
					p.setAddress(p.getOtheraddress());
				}
			}
		}
		
		request.setAttribute("isPermissionAudit", isPermissionAudit);
		return "list";
	}
	public String listToPur(){
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "管理员");
		boolean purManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		boolean purMan = securityService.isAbleRole(admin.getUsername(), "采购员");
	//	request.setAttribute("isAdmin", isAdmin);
	//	request.setAttribute("purManager", purManager);
	//	request.setAttribute("purMan", purMan);
		Page<Purchase> page = new Page<Purchase>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		paramap.put("purchaseType", "1");
		if(!isAdmin&&!purManager&&!purMan){
			paramap.put("area", admin.getArea());
		}
		page = purchaseService.findForPage(page, paramap);
		if(page.getData()!=null){
			for(Purchase p : page.getData()){
				if(StringUtils.isNotBlank(p.getOtheraddress())){
					p.setAddress(p.getOtheraddress());
				}
			}
		}
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/purchase_listToPur.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listToPur";
	}
	private Map<String,String> getPropertiesMap(){
		Map<String,String> prop = new HashMap<String,String>();
		Properties proper = new Properties();
		InputStream  input = null;
		try {
			input =this.getClass().getResourceAsStream("/purchaseAudit.properties");
			proper.load(input);
			String money = proper.getProperty("quoteMoney");
			String manager = proper.getProperty("manager");
			String assistantManager = proper.getProperty("assistantManager");
			prop.put("money", money);
			prop.put("manager", manager);
			prop.put("assistantManager", assistantManager);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != input){
				try {
					input.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}

		return prop;
	}
	public String input() {
		String rilegou = "1";
		Admin admin = this.getCurrentAdminUser();
		boolean salesMan = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean quoterMan = securityService.isAbleRole(admin.getUsername(), "报价员");
		String currPageNo = request.getParameter("currPageNo");
		String pageSize = request.getParameter("pageSize");
		String orderStatus = request.getParameter("orderStatus");
		request.setAttribute("currPageNo", currPageNo);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("orderStatus", orderStatus);
		String isToPur = request.getParameter("isToPur");
		String id = request.getParameter("id");
		String isView = request.getParameter("isView");
		request.setAttribute("isView", StringUtils.isBlank(isView)?"0":"1");
		String oper = request.getParameter("oper");
		purchase = this.purchaseService.getPurchaseById(Long.valueOf(id));
		if(salesMan||quoterMan){
			rilegou = "0";
		}
		purchase.setRilegou(rilegou);
		Set<QuoteFabric> qfSet  = purchase.getQuote().getQuoteFabric();
		if(qfSet!=null){
			List<QuoteFabric> qfList =  QuoteFabricUtil.sort(qfSet, "getOrderId", "asc");
			for(QuoteFabric qf : qfList){
				if(!"1".equals(qf.getIsReplaced())){//不是被替代的产品才在采购单中显示
				//	if(StringUtils.isBlank(isToPur)){
				//		qf.setVcModelNumDisplay(qf.getVcFactoryCode()+" "+qf.getVcModelNum());
				//	}else{
						if(StringUtils.isNotBlank(isToPur)&&"1".equals(qf.getIsHtCode())){
							qf.setVcColorNum("");
						}
				//	}
					quoteFabricList.add(qf);
				}
			}
		}
	//	request.setAttribute("status", status);
		if("1".equals(oper)){//提交，审核待采购单
			return "inputToPur";
		}else if("2".equals(oper)){//审核采购单
			List<Admin> purMans = this.securityService.getUserListByRoleName("采购员");
			List<Admin> purManagers = this.securityService.getUserListByRoleName("采购经理");
			purMans.addAll(purManagers);
			request.setAttribute("userList", purMans);
			return "input";
		}else if("3".equals(oper)){//打印待采购单
			return "printPurchaseToPur";
		}else if("4".equals(oper)){//打印采购单
			return "printPurchase";
		}
		
		return "input";
	}
	
	public void downPurchase() throws Exception{
		String id = request.getParameter("id");
		Purchase entity = this.purchaseService.getPurchaseById(Long.valueOf(id));
		String fileName = "purchase";
        String fileUrl = "/model/purchase.xls";
     	String wrPath = request.getRealPath("/files/purchase/");
        
		 FileInputStream in = new FileInputStream(request.getRealPath(fileUrl));
         HSSFWorkbook book = new HSSFWorkbook(in);
         CellStyle style = book.createCellStyle();
		 style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中    
		 style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		 style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		 style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
         HSSFFont font = book.createFont();    
         font.setFontName("黑体");    
         font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示   
         font.setFontHeightInPoints((short) 12);//设置字体大小  
         style.setFont(font);
         HSSFSheet sheet = book.getSheetAt(0);
        //坐标从0开始
        int startRow = 6;
        HSSFRow row8 = sheet.getRow(startRow);
        if(row8!=null){
        	HSSFCell cell2 = row8.getCell(2);//订货日期
        	if(cell2!=null){
        		String dateStr = DateUtil.getShortDate(entity.getContractDate());
        		cell2.setCellValue(dateStr);
        	}
        	HSSFCell cell4 = row8.getCell(5);//订单编号
        	if(cell4!=null){
        		cell4.setCellValue(entity.getOrderNo());
        	}
        }
        HSSFRow row9 = sheet.getRow(startRow+2);
        if(row9!=null){
        	HSSFCell cell2 = row9.getCell(0);//依凭单
        	if(cell2!=null){
        		cell2.setCellValue(entity.getVoucher());
        	}
        	HSSFCell cell4 = row9.getCell(2);//报价单号
        	if(cell4!=null){
        		cell4.setCellValue(entity.getQuote().getProjectNum());
        	}
        	HSSFCell cell6 = row9.getCell(3);//定金确认
        	if(cell6!=null){
        		cell6.setCellValue(entity.getDcPerson());
        	}
        	HSSFCell cell7 = row9.getCell(5);//余款确认
        	if(cell7!=null){
        		cell7.setCellValue(entity.getBcPerson());
        	}
        	HSSFCell cell8 = row9.getCell(7);//货期要求
        	if(cell8!=null){
        		cell8.setCellValue(entity.getDeliveryRequirements());
        	}
        }
        HSSFRow row10 = sheet.getRow(startRow+3);
        if(row10!=null){
        	HSSFCell cell2 = row10.getCell(2);//收货地址
        	if(cell2!=null){
        		if(StringUtils.isNotBlank(entity.getOtheraddress())){
        			cell2.setCellValue(entity.getOtheraddress());
				}else{
					cell2.setCellValue(entity.getAddress());
				}
        		
        	}
        }
        HSSFRow row11 = sheet.getRow(startRow+5);
        if(row11!=null){
        	HSSFCell cell2 = row11.getCell(2);//付款方式
        	if(cell2!=null){
        		cell2.setCellValue(entity.getPaymentType());
        	}
        	HSSFCell cell4 = row11.getCell(3);//快递公司
        	if(cell4!=null){
        		cell4.setCellValue(entity.getExpressCom());
        	}
        }
        HSSFRow row12 = sheet.getRow(startRow+6);
        if(row12!=null){
        	HSSFCell cell2 = row12.getCell(3);//到货日期
        	if(cell2!=null){
        		cell2.setCellValue(entity.getRemarks()+" 之前");
        	}
        	
        List<QuoteFabric> qfList = new ArrayList<QuoteFabric>();
        for(QuoteFabric qf : entity.getQuote().getQuoteFabric()){
        	if(!"1".equals(qf.getIsReplaced())){
        		qfList.add(qf);
        	}
        }
		qfList = QuoteFabricUtil.sort(qfList, "getOrderId", "asc");
        int rows = qfList.size();
        int shifStartRow = 13;
        sheet.shiftRows(shifStartRow,  sheet.getLastRowNum(), rows, true, false);
        HSSFRow row = sheet.getRow(shifStartRow);
        this.createCell(row, 9);
        row.getCell(0).setCellStyle(style);
        row.getCell(0).setCellValue("序号");
        row.getCell(1).setCellStyle(style);
        row.getCell(1).setCellValue("*区域编号");
        row.getCell(2).setCellStyle(style);
        row.getCell(2).setCellValue("*型号");
        row.getCell(3).setCellStyle(style);
        row.getCell(3).setCellValue("*幅宽(CM)");
        row.getCell(4).setCellStyle(style);
        row.getCell(4).setCellValue("*⑦订货数量");
        row.getCell(5).setCellStyle(style);
        row.getCell(5).setCellValue("*④单位 ");
        row.getCell(6).setCellStyle(style);
        row.getCell(6).setCellValue("*② 类型");
        row.getCell(7).setCellStyle(style);
        row.getCell(7).setCellValue("备注");
        HSSFCell ct4_7 = row.getCell(8);
        ct4_7.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(
        		shifStartRow, //first row (0-based)
        		shifStartRow, //last row  (0-based)
                7, //first column (0-based)
                8  //last column  (0-based)
        ));
      //  HSSFCellStyle rowStyle = row.getRowStyle();
        for(int i = 0; i < rows; i++){
        	HSSFRow r = sheet.createRow(shifStartRow+i+1);
        //	r.setRowStyle(rowStyle);
        	HSSFCell c1 = r.createCell(0);//序号
        	c1.setCellStyle(style);
        	c1.setCellValue(qfList.get(i).getOrderId());
        	HSSFCell c2 = r.createCell(1);//工厂代码
        	c2.setCellStyle(style);
        	c2.setCellValue(qfList.get(i).getVcFactoryCode());
        	HSSFCell c3 = r.createCell(2);//型号
        	c3.setCellStyle(style);
        //	String isHtCode = qfList.get(i).getIsHtCode();
	    //    String vcModelNumDisplay = isHtCode.equals("0")?qfList.get(i).getVcModelNum():qfList.get(i).getHtCode();//显示的型号
        	String cv = qfList.get(i).getVcModelNum();
        	if(StringUtils.isNotEmpty(qfList.get(i).getVcColorNum())){
        		cv += "-"+qfList.get(i).getVcColorNum();
        	}
        	c3.setCellValue(cv);
        	HSSFCell c4 = r.createCell(3);//幅宽
        	c4.setCellStyle(style);
        	c4.setCellValue(qfList.get(i).getVcWidth());
        	HSSFCell c5 = r.createCell(4);//订货量
        	c5.setCellStyle(style);
        	c5.setCellValue(qfList.get(i).getOrderQuantity());
        	HSSFCell c6 = r.createCell(5);//单位
        	c6.setCellStyle(style);
        	c6.setCellValue(qfList.get(i).getVcOldPriceUnit());
        	HSSFCell c7 = r.createCell(6);//类型
        	c7.setCellStyle(style);
        	c7.setCellValue(qfList.get(i).getVcType());
        	HSSFCell c8 = r.createCell(7);//备注
        	c8.setCellStyle(style);
        	c8.setCellValue(qfList.get(i).getVcPurchaseRmk());
        	HSSFCell c5_7 = r.createCell(8);
        	c5_7.setCellStyle(style);
        	sheet.addMergedRegion(new CellRangeAddress(
        			shifStartRow+i+1, //first row (0-based)
        			shifStartRow+i+1, //last row  (0-based)
                    7, //first column (0-based)
                    8  //last column  (0-based)
            ));
        	
        }
        HSSFRow afterInsertRow1 = sheet.getRow(shifStartRow+rows+1);
        if(afterInsertRow1!=null){
        	HSSFCell cella1 = afterInsertRow1.getCell(1);//制单员
        	if(cella1!=null){
        		cella1.setCellValue(entity.getQuote().getCurUserName());
        	}
        	HSSFCell cella2 = afterInsertRow1.getCell(4);//业务员
        	if(cella2!=null){
        		cella2.setCellValue(entity.getQuote().getVcSalesman());
        	}
        }
        HSSFRow afterInsertRow2 = sheet.getRow(shifStartRow+rows+2);
        if(afterInsertRow2!=null){
        	HSSFCell cellb1 = afterInsertRow2.getCell(1);//核对员
        	if(cellb1!=null){
        		cellb1.setCellValue(entity.getAuditor());
        	}
        	HSSFCell cellb2 = afterInsertRow2.getCell(4);//采购员
        	if(cellb2!=null){
        		cellb2.setCellValue("");
        	}
        }
        }
        String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.purchaseLog, curAdminName+"下载了采购单"+entity.getContractNo());
            String excelName = "purchase" ;
            excelName = URLEncoder.encode(excelName, "gbk");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + excelName + ".xls");
            book.write(response.getOutputStream());
        
	}
        
        public void createCell(HSSFRow row,int num) {
   	     for(int i=0;i<=num;i++){
   	      HSSFCell cell=null;
   	      if(row.getCell(i)!=null){
   	       cell=row.getCell(i);
   	     //  cell.setEncoding(HSSFCell.ENCODING_UTF_16);
   	      }else{
   	       cell=row.createCell(i);
   	      // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
   	      }
   	     }
   	 }
	
	public String deleteByIds(){
		String idStr = request.getParameter("ids");
		String[] idArray = idStr.split(",");
		List<Long> ids = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer("");
		String title = "待采购单";
		String modul = LogModule.toPurchaseLog;
		for(String id:idArray){
			Purchase p = this.purchaseService.getPurchaseById(Long.valueOf(id));
			if(Integer.valueOf(p.getOrderStatus()).intValue()>2){
				title = "采购单";
				modul = LogModule.purchaseLog;
			}
			sb.append(p.getContractNo());
			storeFabricService.deleteByProperty("quoteId", p.getQuote().getId());
			orderService.deleteByProperty("quoteId", p.getQuote().getId());
			ids.add(Long.valueOf(id));
		}
		this.purchaseService.deleteByIds(ids);
		 String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(modul, curAdminName+"删除了"+title+sb.toString());
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		String currPageNo = request.getParameter("currPageNo");
		String pageSize = request.getParameter("pageSize");
		String orderStatus = request.getParameter("orderStatus");
		request.setAttribute("currPageNo", currPageNo);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("orderStatus", orderStatus);
		Purchase pdb = this.purchaseService.getPurchaseById(purchase.getId());
		Quote q = pdb.getQuote();
		Admin curAdmin = this.getCurrentAdminUser();
		purchase.setCustomer(pdb.getCustomer());
		purchase.setPurchaseType(pdb.getPurchaseType());
		purchase.setContractDate(pdb.getContractDate());
		purchase.setContractNo(pdb.getContractNo());
		purchase.setTbYearMonth(pdb.getTbYearMonth());
		purchase.setQuote(pdb.getQuote());
		purchase.setArea(pdb.getArea());
		if("1".equals(purchase.getOrderStatus())){
			List<Admin> saleManegers = this.securityService.getUserListByRoleName("财务经理");
			if(CollectionUtils.isNotEmpty(saleManegers)){
				for(Admin admin : saleManegers){
					Email e = new Email();
					e.setAction("toPurchase");
					e.setDetail("关于【" + q.getProjectName() + "】的待采购单已经提交！合同编号为"+pdb.getContractNo()+"，请审核");
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
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
					e.setAction("toPurchase");
					e.setDetail("关于【" + q.getProjectName() + "】的待采购单已经提交！报价单号为"+q.getProjectNum()+"，合同编号为"+pdb.getContractNo());
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(name);
					e.setStatus("0");
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			purchaseService.saveOrUpdateEntity(purchase);
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.toPurchaseLog, curAdminName+"提交了待采购单"+pdb.getContractNo());
		}
		if("2".equals(purchase.getOrderStatus())){
			purchase.setOrderDate(new Date());// 审核时间
			purchase.setAuditor(super.getCurrentAdminUser().getUsername());// 待采购单的审核人
			purchase.setPurchaseType("2");
			List<Admin> saleManegers = this.securityService.getUserListByRoleName("采购经理");
			if(CollectionUtils.isNotEmpty(saleManegers)){
				Map<String,String> map = this.getPropertiesMap();
				int money = new Integer(map.get("money"));
				String manager = map.get("manager");
				for(Admin admin : saleManegers){
					Email e = new Email();
					e.setAction("purchase");
					if(q.getSumMoney()<money||manager.contains(admin.getUsername())){
						e.setDetail("关于【" + q.getProjectName() + "】的待采购单已经审核！合同编号为"+pdb.getContractNo()+"，请审核采购单");
						e.setStatus("3");
					}else{
						e.setDetail("关于【" + q.getProjectName() + "】的待采购单已经审核！合同编号为"+pdb.getContractNo());
						e.setStatus("0");
					}
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			Email e = new Email();
			e.setAction("toPurchase");
			e.setDetail("关于【" + q.getProjectName() + "】的待采购单已经审核！合同编号为"+pdb.getContractNo());
			e.setQuoteId(q.getId());
			e.setQuoteNo(q.getProjectNum());
			e.setPurchaseId(pdb.getId());
			e.setPurchaseNo(pdb.getContractNo());
			e.setSender(curAdmin.getUsername());
			e.setSendTime(new Date());
			e.setState("1");
			e.setUsername(q.getModifyUser());
			e.setStatus("0");
			this.emailService.saveOrUpdateEntity(e);
			purchaseService.saveOrUpdateEntity(purchase);
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.toPurchaseLog, curAdminName+"审核了待采购单"+pdb.getContractNo());
		}
		if("3".equals(purchase.getOrderStatus())){
			purchase.setOrderDate(pdb.getOrderDate());
			purchase.setAuditor(pdb.getAuditor());
			purchase.setPurchaseType(pdb.getPurchaseType());
			purchase.setApprover(super.getCurrentAdminUser().getUsername());// 采购单的审核人
			List<Admin> saleManegers = this.securityService.getUserListByRoleName("财务经理");
			if(CollectionUtils.isNotEmpty(saleManegers)){
				for(Admin admin : saleManegers){
					Email e = new Email();
					e.setAction("puchase");
					e.setDetail("关于【" + q.getProjectName() + "】的采购单已经审核！合同编号为"+pdb.getContractNo());
					e.setQuoteId(q.getId());
					e.setQuoteNo(q.getProjectNum());
					e.setPurchaseId(pdb.getId());
					e.setPurchaseNo(pdb.getContractNo());
					e.setSender(curAdmin.getUsername());
					e.setSendTime(new Date());
					e.setState("1");
					e.setUsername(admin.getUsername());
					e.setStatus("0");
					this.emailService.saveOrUpdateEntity(e);
				}
			}
			String curAdminName = this.getCurrentAdminUser().getUsername();
			saveSystemLog(LogModule.purchaseLog, curAdminName+"审核了采购单"+pdb.getContractNo());
		}
	//	purchaseService.saveOrUpdateEntity(purchase);
		Set<QuoteFabric> qfSet  = new HashSet<QuoteFabric>();
		for(QuoteFabric qf:quoteFabricList){
			if(!"1".equals(qf.getIsReplaced())){
				QuoteFabric qfdb = this.quoteFabricService.getQuoteFabricById(qf.getId());
				qfdb.setVcSubLay(qf.getVcSubLay());
				qfdb.setVcRealityAog(qf.getVcRealityAog());
				qfdb.setVcShipmentNum(qf.getVcShipmentNum());
				qfdb.setVcType(qf.getVcType());
				qfdb.setVcPurchaseRmk(qf.getVcPurchaseRmk());
				qfdb.setVcAssignAutor(qf.getVcAssignAutor());
				if("3".equals(purchase.getOrderStatus())){
					qfSet.add(qfdb);
				}else{
					quoteFabricService.saveOrUpdateEntity(qfdb);
				}
			}
			
		}
		if("3".equals(purchase.getOrderStatus())){
			saveOrder(purchase,qfSet);
			return "ok";
		}
		return "okToPur";
	}
	
	private void saveOrder(Purchase purchase,Set<QuoteFabric> qfSet){
		Admin curAdmin = this.getCurrentAdminUser();
		/*List<String> caigouyuan = new ArrayList<String>();
		for(QuoteFabric qf : qfSet){
			if(StringUtils.isNotBlank(qf.getVcAssignAutor())){
				caigouyuan.add(qf.getVcAssignAutor());
			}
		}*/
		List<Supplier> ss = this.supplierService.getAll();
		Map<String,String> ssMap = new HashMap<String,String>();
		Map<String,String> ssMap2 = new HashMap<String,String>();
		for(Supplier s : ss){
			ssMap.put(s.getVcFactoryNum(), s.getVcFactoryName());
		}
		// 根据地区和年月查询订单数
		long num = orderService.getCurrentOrderNum(purchase.getArea(), purchase.getTbYearMonth());
		String qlocal = purchase.getQuote().getVcQuoteLocal();		
		if("GZ".equals(qlocal)){
			qlocal = "A";
		} else if("SZ".equals(qlocal)){
			qlocal = "D";
		} else if("SH".equals(qlocal)){
			qlocal = "F";
		} else if("BJ".equals(qlocal)){
			qlocal = "C";
		} else if("HK".equals(qlocal)){
			qlocal = "E";
		} 		
		String orderNo = "P" + qlocal + "-" + purchase.getTbYearMonth()
				+ String.format("%03d", (num + 1));
		
		
		List<Order> ordersdb = this.orderService.getOrderByPurchaseId(purchase.getId());
		Map<String,Order> orderMap = new HashMap<String,Order>();
		if(CollectionUtils.isNotEmpty(ordersdb)){
			for(Order o : ordersdb){
				orderMap.put(o.getFactoryNum(), o);
			}
		}
		Map<String,QuoteFabric> map = new HashMap<String,QuoteFabric>();
		for(QuoteFabric qf : qfSet){
				ssMap2.put(qf.getVcFactoryNum(), qf.getVcFactoryCode());
				Order order = orderMap.get(qf.getVcFactoryNum());
				if(order==null){
					qf.setOrderNo(orderNo);
				}else{
					qf.setOrderNo(order.getOrderNo());
				}
				map.put(qf.getVcFactoryNum(),qf);
				quoteFabricService.saveOrUpdateEntity(qf);
			
		}
		for(String fnum : map.keySet()){
			Order order = orderMap.get(fnum);
			if(order==null){
				order = new Order();
				if(orderMap.size()==0){//第一次由采购单生成订单
					order.setOrderNo(orderNo);// 新的单号
					
				}else{//修改报价单后新增了报价布匹，而且这个新增的报价布匹之前没有生成对应的订单
					order.setOrderNo(ordersdb.get(0).getOrderNo());// 用原来的单号
				}
				order.setOrderDate(new Date());
				order.setTbYearMonth(DateUtil.getYearMonthStr());
				order.setVcfromTel("080-83309415");
				order.setVcfromFax("02083309428");
				order.setArea(purchase.getArea());// 地区
				order.setPurchase(purchase);
				order.setSupplier(ssMap.get(fnum));
				order.setFactoryCode(ssMap2.get(fnum));
				order.setFactoryNum(fnum);
				// 报价单号
				order.setQuantation(purchase.getQuote().getProjectNum());
				order.setQuoteId(purchase.getQuote().getId());
			}
			purchase.setOrderNo(order.getOrderNo());
			this.purchaseService.saveOrUpdateEntity(purchase);
			String formUser = map.get(fnum).getVcAssignAutor();
			order.setOrderStatus(0);
			order.setVcfrom(formUser);
			order.setHbUnit(map.get(fnum).getPriceCur());
			float sumMoney = 0f;
			for(QuoteFabric qf : qfSet){
				if(!"1".equals(qf.getIsReplaced()) && qf.getVcFactoryNum().equals(order.getFactoryNum())){
					float sigMoney = PriceUtil.getTwoDecimalFloat(qf.getSinglePrice() * qf.getVcPurDis());
				//	sigMoney = (float) (Math.round((sigMoney) * 10)) / 10;
					qf.setSigMoney(sigMoney);
					float vcQuoteNum = qf.getVcQuoteNum() == 0 ? qf.getOrderQuantity() : qf.getVcQuoteNum();
					float shijia = qf.getShijia() == 0 ? qf.getSigMoney() : qf.getShijia();
					sumMoney += (vcQuoteNum * shijia);
				}
			}
			if(order.getSumMoney()>0){
				sumMoney = order.getSumMoney();
			}
			//sumMoney = (float) (Math.round((sumMoney) * 10)) / 10;
			order.setSumMoney(PriceUtil.getTwoDecimalFloat(sumMoney));
			this.orderService.saveOrUpdateEntity(order);
				Email e = new Email();
				e.setAction("order");
				e.setDetail("关于【" + purchase.getQuote().getProjectName() + "】的采购单已经审核！订单号为"+order.getOrderNo()+",请去提交");
				e.setQuoteId(purchase.getQuote().getId());
				e.setQuoteNo(purchase.getQuote().getProjectNum());
				e.setPurchaseId(purchase.getId());
				e.setPurchaseNo(purchase.getContractNo());
				e.setSender(curAdmin.getUsername());
				e.setSendTime(new Date());
				e.setState("1");
				e.setUsername(formUser);
				e.setStatus("1");
				e.setOrderId(order.getId());
				e.setOrderNo(order.getOrderNo());
				this.emailService.saveOrUpdateEntity(e);
		}
		
	}
	
	public String toImport(){
		String purchaseId = request.getParameter("purchaseId");
		request.setAttribute("purchaseId", purchaseId);
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
		String contractNo = request.getParameter("contractNo");
		String orderStatus = request.getParameter("orderStatus");
		String orderNo = request.getParameter("orderNo");
		
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime);
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime);
			request.setAttribute("endTime", endTime);
		}
		if(StringUtils.isNotEmpty(contractNo)){
			paramap.put("contractNo", contractNo);
			request.setAttribute("contractNo", contractNo);
		}
		if(StringUtils.isNotEmpty(orderStatus)){
			paramap.put("orderStatus", orderStatus);
			request.setAttribute("orderStatus", orderStatus);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			paramap.put("orderNo", orderNo);
			request.setAttribute("orderNo", orderNo);
		}
		return paramap;
	}
	
	 public String importFile(){
		 String purchaseId = request.getParameter("purchaseId");
		 request.setAttribute("purchaseId", purchaseId);
		 String linkCode = "purchaseId_"+purchaseId;
		 return super.importFile(linkCode);
	 }
	 public String downloadFile(){
		 String purchaseId = request.getParameter("purchaseId");
		 String linkCode = "purchaseId_"+purchaseId;
		 return super.downloadFile(linkCode);
	 }
	 
	 public String getPurchaseStatus(){
		 String emailId = request.getParameter("emailId");
			if(StringUtils.isNotBlank(emailId)){
				Email e = this.emailService.getEmailById(Long.valueOf(emailId));
				e.setState("2");
				this.emailService.saveOrUpdateEntity(e);
			}
			String status = request.getParameter("status");
			String purchaseId = request.getParameter("purchaseId");
		//	String action = request.getParameter("action");
			Purchase p = this.purchaseService.getPurchaseById(Long.valueOf(purchaseId));
			if(p!=null){
				String orderStatus = p.getOrderStatus();
				int s = Integer.valueOf(orderStatus);
				String canDo = "1";
				if("1".equals(status)){//待采购单未提交，要去提交
					if(s>0){
						canDo = "0";
					}
				}else if("2".equals(status)){//待采购单已经提交，要去审核
					if(s>1){
						canDo = "0";
					}
				}else if("3".equals(status)){//采购单未审核，要去审核
					if(s>2){
						canDo = "0";
					}
				}
				try {
					response.getWriter().write(canDo);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}

	public List<QuoteFabric> getQuoteFabricList() {
		return quoteFabricList;
	}

	public void setQuoteFabricList(List<QuoteFabric> quoteFabricList) {
		this.quoteFabricList = quoteFabricList;
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}


	
}
