package com.wfsc.actions.bym;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerOrder;
import com.wfsc.common.bo.bym.MaterialTotal;
import com.wfsc.common.bo.bym.QuoteFabricReport;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.services.bym.service.IDesignerExpenseService;
import com.wfsc.services.bym.service.IDesignerOrderService;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IQuoteFabricReportService;
import com.wfsc.services.bym.service.IQuoteFabricService;
import com.wfsc.services.security.ISecurityService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("DesignerOrderAction")
@Scope("prototype")
public class DesignerOrderAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;

	@Resource(name = "designerOrderService")
	private IDesignerOrderService designerOrderService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "quoteFabricReportService")
	private IQuoteFabricReportService quoteFabricReportService;
	
	@Resource(name = "orderService")
	private IOrderService orderService;
	@Resource(name = "designerExpenseService")
	private IDesignerExpenseService designerExpenseService;
	@Resource(name = "quoteFabricService")
	private IQuoteFabricService quoteFabricService;
	@Resource(name = "currencyConversionService")
	private ICurrencyConversionService currencyConversionService;
	private DesignerOrder designerOrder;
	public String managerSellIn(){
		this.setTopMenu();
		listSellIn();
		return "managerSellIn";
	}
	
	@SuppressWarnings("unchecked")
	public String listSellIn(){
		Page<DesignerOrder> page = new Page<DesignerOrder>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerOrderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listSellIn.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listSellIn";
	}
	public String managerSellInPerson(){
		this.setTopMenu();
		listSellInPerson();
		return "managerSellInPerson";
	}
	
	@SuppressWarnings("unchecked")
	public String listSellInPerson(){
		Page<DesignerOrder> page = new Page<DesignerOrder>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		/*String sellman = paramap.get("sellman")==null?"":paramap.get("sellman").toString();
		if(StringUtils.isBlank(sellman)){
			List<Integer> li = page.getPageNos();
			String listUrl = "/wfsc/admin/designerOrder_listSellInPerson.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			return "listSellInPerson";
		}*/
		page = designerOrderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listSellInPerson.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listSellInPerson";
	}
	
	public String managerInCome(){
		this.setTopMenu();
		listInCome();
		return "managerInCome";
	}
	
	@SuppressWarnings("unchecked")
	public String listInCome(){
		Page<DesignerOrder> page = new Page<DesignerOrder>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerOrderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listInCome.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listInCome";
	}
	public String managerSellCost(){
		this.setTopMenu();
		listSellCost();
		return "managerSellCost";
	}
	
	@SuppressWarnings("unchecked")
	public String listSellCost(){
		Page<DesignerOrder> page = new Page<DesignerOrder>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerOrderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listSellCost.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listSellCost";
	}
	
	public String cp(){
		quoteFabricReportService.cp();
		return managerMaterial();
	}
	
	public String managerMaterial(){
		this.setTopMenu();
		listMaterial();
		return "managerMaterial";
	}
	
	@SuppressWarnings("unchecked")
	public String listMaterial(){
		boolean isAdmin = securityService.isAbleRole(this.getCurrentAdminUser().getUsername(), "管理员");
		request.setAttribute("isAdmin", isAdmin);
		Page<QuoteFabricReport> page = new Page<QuoteFabricReport>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = quoteFabricReportService.findForPage(page, paramap);
		/*List<QuoteFabricReport> qfrsDb = page.getData();
		List<QuoteFabricReport> qfrs = new ArrayList<QuoteFabricReport>();
		for(QuoteFabricReport qfr : qfrsDb){
			if(!"1".equals(qfr.getIsHidden())){
				qfrs.add(qfr);
			}
		}
		page.setData(qfrs);*/
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listMaterial.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listMaterial";
	}
	
	public String managerMaterialTotal(){
		this.setTopMenu();
		listMaterialTotal();
		return "managerMaterialTotal";
	}
	public String listMaterialTotal(){
		Page<MaterialTotal> page = new Page<MaterialTotal>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = quoteFabricReportService.findSumForPage(page, paramap);
		/*List<QuoteFabricReport> qfrsDb = page.getData();
		List<QuoteFabricReport> qfrs = new ArrayList<QuoteFabricReport>();
		for(QuoteFabricReport qfr : qfrsDb){
			if(!"1".equals(qfr.getIsHidden())){
				qfrs.add(qfr);
			}
		}
		page.setData(qfrs);*/
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listMaterialTotal.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listMaterialTotal";
	}
	
	public String managerDora(){
		this.setTopMenu();
		listDora();
		return "managerDora";
	}
	
	@SuppressWarnings("unchecked")
	public String listDora(){
		Page<DesignerOrder> page = new Page<DesignerOrder>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerOrderService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerOrder_listDora.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listDora";
	}
	
	
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String supplier = request.getParameter("supplier");
		String sellman = request.getParameter("sellman");
		String contractNo = request.getParameter("contractNo");
		String orderNo = request.getParameter("orderNo");
		String flag = request.getParameter("flag");// flag=1,销售收入表;2,个人销售收入表;3,收款表,4,销售成本表，5.销售收入表dora;
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		Admin admin = this.getCurrentAdminUser();
		boolean isAdminb = securityService.isAbleRole(admin.getUsername(), "管理员");
		if(!isAdminb){
			paramap.put("quoteLocal", admin.getArea());
		}
		if(StringUtils.isNotEmpty(beginDate)){
			paramap.put("beginDate", beginDate);
			request.setAttribute("beginDate", beginDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			paramap.put("endDate", endDate);
			request.setAttribute("endDate", endDate);
		}
		
		if(StringUtils.isNotEmpty(sellman)){
			paramap.put("sellman", sellman);
			request.setAttribute("sellman", sellman);
		}
		if(StringUtils.isNotEmpty(contractNo)){
			paramap.put("contractNo", contractNo);
			request.setAttribute("contractNo", contractNo);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			paramap.put("orderNo", orderNo);
			request.setAttribute("orderNo", orderNo);
		}
		if(StringUtils.isNotEmpty(flag)){
			paramap.put("flag", flag);
			//request.setAttribute("flag", flag);
		}
		
		
		if(StringUtils.isNotEmpty(supplier)){
			paramap.put("supplier", supplier);
			request.setAttribute("supplier", supplier);
		}
		return paramap;
	}
	
	public String exportDesignerOrderData(){
		// flag=1,销售收入表;2,个人销售收入表;3,收款表,4,销售成本表，5.销售收入表dora;
		List<DesignerOrder> list = null;
		Map<String,Object> paramap = this.handleRequestParameter();
		String flg = paramap.get("flag").toString();
		list = designerOrderService.getDesignerOrderByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("DesignerOrder");
			String[] titleStr = null;
			if("1".equals(flg)){//销售收入表
				titleStr = new String[]{"时间","PO号","合同号","客户名称","项目","销售人员","设计师","合同金额","GZ","SH","BJ","SZ","HK"
						,"分摊合计","实际金额","类型","发票","备注"};
			}else if("2".equals(flg)){//个人销售收入表
				titleStr = new String[]{"时间","合同号","客户名称","项目","合同金额","本地比例","本地收入金额","分摊地","分摊比例","分摊地","分摊比例",
						"分摊地","分摊比例","分摊地","分摊比例","分摊地","分摊比例","设计师1","设计费1","设计师2","设计费2","设计师3","设计费3","备注"};
			}else if("3".equals(flg)){//收款表
				titleStr = new String[]{"PO号","收款时间","合同号","客户名称","项目","销售人员","设计师","合同金额","本地比例","本地收入金额","分摊地","分摊比例","分摊金额"
						,"设计费","税率","是否开具发票","订金","进度款1","进度款2","进度款3","保质金","已付合计","未付余额","是否已付清","收款地","备注"};
			}else if("4".equals(flg)){//销售成本表,title有跨行.TO DO
				titleStr = new String[]{"时间","PO号","合同号","客户名称","项目","合同金额","材料合计","加工费","量窗费","安装费","运费","税金","合计"
						,"材料合计","加工费","安装费","运费","差旅费","设计费","其他","合计","毛利","毛利率"};
			}else if("5".equals(flg)){//销售收入表dora
				titleStr = new String[]{"时间","PO号","合同号","客户名称","项目","销售人员","设计师","抬头","合同金额","GZ","SH","BJ","SZ","HK"
						,"分摊合计","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","余款","类型","发票","备注"};
			}
			
			HSSFRow thRow = sheet.createRow(0);//表头行
			
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			float sumMoney = 0F;
			for(int m=1;m<=list.size()+1;m++) {
				HSSFRow cRow = sheet.createRow(m);
				if(m==list.size()+1){
					if("1".equals(flg)){//销售收入表
						HSSFCell c = cRow.createCell(7);
						c.setCellValue(sumMoney);
					}else if("2".equals(flg)){//个人销售收入表
						HSSFCell c = cRow.createCell(4);
						c.setCellValue(sumMoney);
					}else if("3".equals(flg)){//收款表
						HSSFCell c = cRow.createCell(7);
						c.setCellValue(sumMoney);
					}else if("4".equals(flg)){//销售成本表,title有跨行.TO DO
						HSSFCell c = cRow.createCell(5);
						c.setCellValue(sumMoney);
					}else if("5".equals(flg)){//销售收入表dora
						HSSFCell c = cRow.createCell(8);
						c.setCellValue(sumMoney);
					}
					
				}else{
					DesignerOrder de = list.get(m-1);
					sumMoney+=de.getSumMoney();
					Object[] values = null;
					if("1".equals(flg)){//销售收入表
						values = new Object[]{de.getCreateDate(),de.getOrderNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getVcSalesman(),
								de.getDesigner1()+","+de.getDesigner2()+","+de.getDesigner3(),de.getSumMoney(),de.getShareMony1(),de.getShareMony2(),de.getShareMony3(),
								de.getShareMony4(),de.getShareMony5(),de.getSharetotle(),de.getRealTotel(),de.getType(),de.getIsInvoice(),de.getRemark()};
					}else if("2".equals(flg)){//个人销售收入表
						values = new Object[]{de.getCreateDate(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getRealRate(),de.getRealTotel(),de.getShareArea1(),de.getShareRate1(),de.getShareArea2(),de.getShareRate2(),
								de.getShareArea3(),de.getShareRate3(),de.getShareArea4(),de.getShareRate4(),de.getShareArea5(),de.getShareRate5(),
								de.getDesigner1(),de.getDesignMony1(),de.getDesigner2(),de.getDesignMony2(),de.getDesigner3(),de.getDesignMony3(),
								de.getRemark()};
					}else if("3".equals(flg)){//收款表
						values = new Object[]{de.getOrderNo(),de.getGatheringDate(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getVcSalesman(),
								de.getDesigner1()+","+de.getDesigner2()+","+de.getDesigner3(),de.getSumMoney()
								,de.getRealRate(),de.getRealTotel(),de.getShareArea1()+","+de.getShareArea2()+","+de.getShareArea3()+","+de.getShareArea4()+","+de.getShareArea5(),
								de.getShareRate1()+","+de.getShareRate2()+","+de.getShareRate3()+","+de.getShareRate4()+","+de.getShareRate5(),
								de.getShareMony1()+","+de.getShareMony2()+","+de.getShareMony3()+","+de.getShareMony4()+","+de.getShareMony5(),
								de.getDesignFre(),de.getTaxation(),de.getIsInvoice(),de.getFrontMoney(),de.getPlanMoney1(),de.getPlanMoney2(),de.getPlanMoney3(),
								de.getQualityMoney(),de.getHasApplyTotle(),de.getHasNoApplyTotle(),de.getIsPayTotle(),de.getGatheringArea(),
								de.getRemark()};
					}else if("4".equals(flg)){//销售成本表,title有跨行.TO DO
						values = new Object[]{de.getCreateDate(),de.getOrderNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getBjOldPriceTatol(),de.getVcProcessFre(),de.getLcFre(),de.getVcInstallFre(),de.getBjFreight(),de.getTaxationFee(),
								de.getBjTotel(),de.getCbClTotel(),de.getProcessFee(),de.getInstallFee(),de.getCbFreight(),de.getTravelExpenses(),
								de.getDesignFre(),de.getOtherFre(),de.getCbTotel(),de.getProfit(),de.getProfitRate()};
					}else if("5".equals(flg)){//销售收入表dora
						values = new Object[]{de.getCreateDate(),de.getOrderNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getVcSalesman(),
								de.getDesigner1()+","+de.getDesigner2()+","+de.getDesigner3(),de.getVcFrom(),de.getSumMoney()
								,de.getShareMony1(),de.getShareMony2(),de.getShareMony3(),de.getShareMony4(),de.getShareMony5(),de.getSharetotle()
								,de.getMon1(),de.getMon2(),de.getMon3(),de.getMon4(),de.getMon5(),de.getMon6(),de.getMon7(),de.getMon8(),de.getMon9(),
								de.getMon10(),de.getMon11(),de.getMon12(),de.getHasNoApplyTotle(),de.getType(),de.getIsInvoice(),de.getRemark()};
					}
					for(int j = 0; j < values.length; j++) {
						HSSFCell c = cRow.createCell( j);
						if(values[j] instanceof Float) {
							c.setCellValue(values[j]==null?0F:(Float)values[j]);
						} else if(values[j] instanceof Integer) {
							c.setCellValue(values[j]==null?0:(Integer)values[j]);
						} else if(values[j] instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							c.setCellValue(values[j]==null?"":sdf.format((Date)values[j]));
						} else {
							c.setCellValue(values[j]==null?"":(String)values[j]);
						}
					}
				}
			}
			wb.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String exportDesignerQFRData(){
		List<QuoteFabricReport> list = null;
		Map<String,Object> paramap = this.handleRequestParameter();
		Object quoteLocal = paramap.get("paramap");
		list = quoteFabricReportService.getQuoteFabricReportByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("DesignerQFR");
			String[] titleStr = null;
			if(quoteLocal!=null){
				titleStr = new String[]{"时间","PO号","合同号","客户名称","项目","合同金额","型号","色号","数量","单价","合计","运费","工地运费","单价调整","型号"
						,"色号","订货量","实订量","单价","实价","币种合计","合计","毛利","毛利率"};
			}else{
				titleStr = new String[]{"时间","PO号","合同号","供应商","客户名称","项目","合同金额","型号","色号","数量","单价","合计","运费","工地运费","单价调整","型号"
						,"色号","订货量","实订量","单价","实价","币种合计","合计","毛利","毛利率"};
			}
			HSSFRow thRow = sheet.createRow(0);//表头行
			
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			float sumMoney = 0F;
			for(int m=1;m<=list.size()+1;m++) {
				HSSFRow cRow = sheet.createRow(m);
				if(m==list.size()+1){
					HSSFCell c = cRow.createCell(6);
					c.setCellValue(sumMoney);
				}else{
					QuoteFabricReport de = list.get(m-1);
					sumMoney+=de.getSumMoney();
					Object[] values = null;
					if(quoteLocal!=null){//vcFre,vcSpecialExp,priceAdjust
						values = new Object[]{de.getCreateDate(),de.getOrderNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getVcModelNum(),de.getBjColor(),de.getVcQuantity()+" "+de.getVcPriceUnit(),de.getBjPrice()+" "+de.getVcMoney()
								,de.getVcOldPriceTotal()+" "+de.getVcMoney(),de.getVcFre(),de.getVcSpecialExp(),de.getPriceAdjust(),
								de.getCbModelNum(),de.getCbColor(),de.getOrderNum()+" "+de.getCbPriceUnit(),de.getCbQuantity()+" "+de.getCbPriceUnit()
								,de.getSingleMoney()+" "+de.getPriceCur(),de.getCbPrice()+" "+de.getPriceCur(),de.getCbTotal()+" "+de.getPriceCur(),
								de.getAmountrmb()+" "+de.getVcMoney(),de.getSellProfit(),de.getSellProfitRate()};
					}else{
						values = new Object[]{de.getCreateDate(),de.getOrderNo(),de.getContractNo(),de.getSupplier(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getVcModelNum(),de.getBjColor(),de.getVcQuantity()+" "+de.getVcPriceUnit(),de.getBjPrice()+" "+de.getVcMoney()
								,de.getVcOldPriceTotal()+" "+de.getVcMoney(),de.getVcFre(),de.getVcSpecialExp(),de.getPriceAdjust(),
								de.getCbModelNum(),de.getCbColor(),de.getOrderNum()+" "+de.getCbPriceUnit(),de.getCbQuantity()+" "+de.getCbPriceUnit()
								,de.getSingleMoney()+" "+de.getPriceCur(),de.getCbPrice()+" "+de.getPriceCur(),de.getCbTotal()+" "+de.getPriceCur(),
								de.getAmountrmb()+" "+de.getVcMoney(),de.getSellProfit(),de.getSellProfitRate()};
					}
					for(int j = 0; j < values.length; j++) {
						HSSFCell c = cRow.createCell( j);
						if(values[j] instanceof Float) {
							c.setCellValue(values[j]==null?0F:(Float)values[j]);
						} else if(values[j] instanceof Integer) {
							c.setCellValue(values[j]==null?0:(Integer)values[j]);
						} else if(values[j] instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							c.setCellValue(values[j]==null?"":sdf.format((Date)values[j]));
						} else {
							c.setCellValue(values[j]==null?"":(String)values[j]);
						}
					}
				}
			}
			wb.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

	public DesignerOrder getDesignerOrder() {
		return designerOrder;
	}

	public void setDesignerOrder(DesignerOrder designerOrder) {
		this.designerOrder = designerOrder;
	}
	
	
	/*public void downloadDesignExpense(List<DesignerOrder> list, String sellman, String flag, String area) {
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
					Order o = this.orderService.getOrderById(de.getOrderId());
					if (o==null) {
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
					row.getCell(5).setCellValue(de.getVcSalesman());
					List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
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
						Float sm1 =  de.getShareMony1();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm3 =  de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 =  de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("BJ".equals(area)) {
						Float sm3 =  de.getShareMony3();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 =  de.getShareMony1();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm2 =  de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 =  de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SH".equals(area)) {
						Float sm2 =  de.getShareMony2();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm3 = de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 =  de.getShareMony1();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm4 =  de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SZ".equals(area)) {
						Float sm4 =  de.getShareMony4();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm3 =  de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm1 =  de.getShareMony1();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm5 =  de.getShareMony5();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("HK".equals(area)) {
						Float sm5 =  de.getShareMony5();
						row.getCell(8).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						Float sm3 =  de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 =  de.getShareMony4();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm1 = de.getShareMony1();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
					}
					Float st =  de.getSharetotle();
					row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -st : st));
					Float rt =  de.getRealTotel();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
						stTotle -= de.getSharetotle();
						rtTotle -= de.getRealTotel();
						if ("GZ".equals(area)) {
							sm1Totle -=  de.getShareMony1();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle -=  de.getShareMony3();
							sm2Totle -= de.getShareMony1();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -= de.getShareMony4();
							sm5Totle -= de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle -= de.getShareMony2();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -= de.getShareMony1();
							sm4Totle -= de.getShareMony4();
							sm5Totle -= de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle -= de.getShareMony4();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -= de.getShareMony1();
							sm5Totle -=  de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle -=  de.getShareMony5();
							sm2Totle -= de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony1();
						}
					} else {
						sumTotle += q.getSumMoney();
						stTotle +=  de.getSharetotle();
						rtTotle +=  de.getRealTotel();
						if ("GZ".equals(area)) {
							sm1Totle += de.getShareMony1();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle +=  de.getShareMony3();
							sm2Totle +=  de.getShareMony1();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle +=  de.getShareMony2();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony1();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle +=  de.getShareMony4();
							sm2Totle += de.getShareMony3();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony1();
							sm5Totle +=  de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle +=  de.getShareMony5();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony1();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
					 
					if ("GZ".equals(area)) {
						Float sm1 =  de.getShareMony1();
						row.getCell(5).setCellValue( de.getShareRate1());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						row.getCell(7).setCellValue( de.getShareArea3());// 北京
						row.getCell(8).setCellValue(de.getShareRate3());
						row.getCell(9).setCellValue(de.getShareArea2());// 上海
						row.getCell(10).setCellValue(de.getShareRate2());
						row.getCell(11).setCellValue( de.getShareArea4());// 深圳
						row.getCell(12).setCellValue( de.getShareRate4());
						row.getCell(13).setCellValue( de.getShareArea5());// 香港
						row.getCell(14).setCellValue( de.getShareRate5());
					}
					if ("BJ".equals(area)) {
						Float sm3 = de.getShareMony3();
						row.getCell(5).setCellValue( de.getShareRate3());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						row.getCell(7).setCellValue( de.getShareArea1());// 广州
						row.getCell(8).setCellValue( de.getShareRate1());
						row.getCell(9).setCellValue(de.getShareArea2());// 上海
						row.getCell(10).setCellValue( de.getShareRate2());
						row.getCell(11).setCellValue(de.getShareArea4());// 深圳
						row.getCell(12).setCellValue( de.getShareRate4());
						row.getCell(13).setCellValue( de.getShareArea5());// 香港
						row.getCell(14).setCellValue( de.getShareRate5());
					}
					if ("SH".equals(area)) {
						Float sm2 =  de.getShareMony2();
						row.getCell(5).setCellValue( de.getShareRate2());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						row.getCell(7).setCellValue( de.getShareArea3());// 北京
						row.getCell(8).setCellValue( de.getShareRate3());
						row.getCell(9).setCellValue( de.getShareArea1());// 广州
						row.getCell(10).setCellValue( de.getShareRate1());
						row.getCell(11).setCellValue( de.getShareArea4());// 深圳
						row.getCell(12).setCellValue( de.getShareRate4());
						row.getCell(13).setCellValue( de.getShareArea5());// 香港
						row.getCell(14).setCellValue( de.getShareRate5());
					}
					if ("SZ".equals(area)) {
						Float sm4 =  de.getShareMony4();
						row.getCell(5).setCellValue( de.getShareRate4());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						row.getCell(7).setCellValue( de.getShareArea3());// 北京
						row.getCell(8).setCellValue( de.getShareRate3());
						row.getCell(9).setCellValue( de.getShareArea2());// 上海
						row.getCell(10).setCellValue( de.getShareRate2());
						row.getCell(11).setCellValue( de.getShareArea1());// 广州
						row.getCell(12).setCellValue( de.getShareRate1());
						row.getCell(13).setCellValue( de.getShareArea5());// 香港
						row.getCell(14).setCellValue( de.getShareRate5());
					}
					if ("HK".equals(area)) {
						Float sm5 = de.getShareMony5();
						row.getCell(5).setCellValue( de.getShareRate5());
						row.getCell(6).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						row.getCell(7).setCellValue( de.getShareArea3());// 北京
						row.getCell(8).setCellValue( de.getShareRate3());
						row.getCell(9).setCellValue( de.getShareArea2());// 上海
						row.getCell(10).setCellValue( de.getShareRate2());
						row.getCell(11).setCellValue( de.getShareArea4());// 深圳
						row.getCell(12).setCellValue( de.getShareRate4());
						row.getCell(13).setCellValue( de.getShareArea1());// 广州
						row.getCell(14).setCellValue( de.getShareRate1());
					}
					List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
					DesignerExpense d = new DesignerExpense();
					if (des != null && des.size() > 0) {
						d = des.get(des.size() - 1);
					}
					row.getCell(15).setCellValue(d.getDesigner1());
					row.getCell(16).setCellValue( d.getDesignMony1());
					row.getCell(17).setCellValue(d.getDesigner2());
					row.getCell(18).setCellValue( d.getDesignMony2());
					row.getCell(19).setCellValue(d.getDesigner3());
					row.getCell(20).setCellValue( d.getDesignMony3());
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
							localTotle -=  de.getShareMony1();
						} else if ("SH".equals(area)) {
							localTotle -=  de.getShareMony2();
						} else if ("BJ".equals(area)) {
							localTotle -=  de.getShareMony3();
						} else if ("SZ".equals(area)) {
							localTotle -=  de.getShareMony4();
						} else if ("HK".equals(area)) {
							localTotle -=  de.getShareMony5();
						}
					} else {
						sumTotle += q.getSumMoney();
						if ("GZ".equals(area)) {
							localTotle +=  de.getShareMony1();
						} else if ("SH".equals(area)) {
							localTotle +=  de.getShareMony2();
						} else if ("BJ".equals(area)) {
							localTotle +=  de.getShareMony3();
						} else if ("SZ".equals(area)) {
							localTotle +=  de.getShareMony4();
						} else if ("HK".equals(area)) {
							localTotle += de.getShareMony5();
						}
					}
				}
				sumTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(sumTotle) + "");
				localTotleCell.setCellValue(ProPrice.getTwoDecimalFloat(localTotle) + "");
			} else if ("4".equals(flag)) {
				HSSFRow titleRow = sheet.getRow(1);
				int startRow = 2;// 起始行
				for (DesignerOrder de : list) {
					Order o = this.orderService.getOrderById(de.getOrderId());
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
					row.getCell(5).setCellValue(de.getVcSalesman());
					List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
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
						row.getCell(8).setCellValue( de.getShareRate1());
						row.getCell(9).setCellValue( de.getShareMony1());// 广州
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
						row.getCell(8).setCellValue( de.getShareRate3());
						row.getCell(9).setCellValue( de.getShareMony3());
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
						row.getCell(8).setCellValue( de.getShareRate2());
						row.getCell(9).setCellValue( de.getShareMony2());
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
						row.getCell(8).setCellValue( de.getShareRate4());
						row.getCell(9).setCellValue( de.getShareMony4());
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
						row.getCell(8).setCellValue( de.getShareRate5());
						row.getCell(9).setCellValue( de.getShareMony5());
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

					row.getCell(13).setCellValue( d.getDesignTotelMoney());
					row.getCell(14).setCellValue( q.getContainTax());
					row.getCell(15).setCellValue(de.getIsInvoice());
					row.getCell(16).setCellValue( de.getFrontMoney());
					row.getCell(17).setCellValue( de.getPlanMoney1());
					row.getCell(18).setCellValue( de.getPlanMoney2());
					row.getCell(19).setCellValue( de.getPlanMoney3());
					row.getCell(20).setCellValue( de.getQualityMoney());
					Float ha =  de.getHasApplyTotle();
					row.getCell(21).setCellValue(("1".equals(q.getOffsetQuote()) ? -ha : ha));
					Float hna =  de.getHasNoApplyTotle();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
						haTotle -=  de.getHasApplyTotle();
						hnaTotle -=  de.getHasNoApplyTotle();
					} else {
						sumTotle += q.getSumMoney();
						haTotle += de.getHasApplyTotle();
						hnaTotle +=  de.getHasNoApplyTotle();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
					row.getCell(6).setCellValue(de.getVcSalesman());
					List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
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
						
						 * row.getCell(9).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						 * row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						 * row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						 * row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						 * row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						 
						Float sm1 =  de.getShareMony1();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm3 =  de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 = de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("BJ".equals(area)) {
						
						 * row.getCell(9).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						 * row.getCell(10).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						 * row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						 * row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						 * row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						 
						Float sm3 = de.getShareMony3();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 =  de.getShareMony1();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm2 =  de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 =  de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SH".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						// row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港

						Float sm2 =  de.getShareMony2();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm3 =  de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm1 =  de.getShareMony1();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm4 =  de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm5 =  de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("SZ".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(12).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州
						// row.getCell(13).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港

						Float sm4 = de.getShareMony4();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm3 = de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm1 =  de.getShareMony1();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
						Float sm5 =  de.getShareMony5();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
					}
					if ("HK".equals(area)) {
						// row.getCell(9).setCellValue(de.getShareMony5()==null?0F:de.getShareMony5());//香港
						// row.getCell(10).setCellValue(de.getShareMony3()==null?0F:de.getShareMony3());//北京
						// row.getCell(11).setCellValue(de.getShareMony2()==null?0F:de.getShareMony2());//上海
						// row.getCell(12).setCellValue(de.getShareMony4()==null?0F:de.getShareMony4());//深圳
						// row.getCell(13).setCellValue(de.getShareMony1()==null?0F:de.getShareMony1());//广州

						Float sm5 = de.getShareMony5();
						row.getCell(9).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm5 : sm5));// 香港
						Float sm3 =  de.getShareMony3();
						row.getCell(10).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm3 : sm3));// 北京
						Float sm2 =  de.getShareMony2();
						row.getCell(11).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm2 : sm2));// 上海
						Float sm4 =  de.getShareMony4();
						row.getCell(12).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm4 : sm4));// 深圳
						Float sm1 =  de.getShareMony1();
						row.getCell(13).setCellValue(("1".equals(q.getOffsetQuote()) ? -sm1 : sm1));// 广州
					}
					Float mon1 =  de.getMon1();
					Float mon2 =  de.getMon2();
					Float mon3 =  de.getMon3();
					Float mon4 =  de.getMon4();
					Float mon5 =  de.getMon5();
					Float mon6 =  de.getMon6();
					Float mon7 =  de.getMon7();
					Float mon8 =  de.getMon8();
					Float mon9 =  de.getMon9();
					Float mon10 = de.getMon10();
					Float mon11 = de.getMon11();
					Float mon12 = de.getMon12();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
							sm1Totle -=  de.getShareMony1();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle -=  de.getShareMony3();
							sm2Totle -=  de.getShareMony1();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle -=  de.getShareMony2();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony1();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle -=  de.getShareMony4();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony1();
							sm5Totle -=  de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle -=  de.getShareMony5();
							sm2Totle -=  de.getShareMony3();
							sm3Totle -=  de.getShareMony2();
							sm4Totle -=  de.getShareMony4();
							sm5Totle -=  de.getShareMony1();
						}
					} else {
						sumTotle += q.getSumMoney();
						if ("GZ".equals(area)) {
							sm1Totle +=  de.getShareMony1();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("BJ".equals(area)) {
							sm1Totle +=  de.getShareMony3();
							sm2Totle +=  de.getShareMony1();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("SH".equals(area)) {
							sm1Totle +=  de.getShareMony2();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony1();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony5();
						} else if ("SZ".equals(area)) {
							sm1Totle +=  de.getShareMony4();
							sm2Totle +=  de.getShareMony3();
							sm3Totle += de.getShareMony2();
							sm4Totle +=  de.getShareMony1();
							sm5Totle +=  de.getShareMony5();
						} else if ("HK".equals(area)) {
							sm1Totle +=  de.getShareMony5();
							sm2Totle +=  de.getShareMony3();
							sm3Totle +=  de.getShareMony2();
							sm4Totle +=  de.getShareMony4();
							sm5Totle +=  de.getShareMony1();
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
								Long quoteId = quoteFabric.getQuote().getId();
								if (fabricId != null && !"".equals(fabricId)) {
									String[] code = fabricId.split("_");
									List<QuoteFabric> xxqf = quoteFabricService.getQuoteFabricByCodeAndQuoteId(code[0], code[1], quoteId);
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
						allQuoteNum += listbj.get(i).getVcQuantity();
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
						clFre +=  q.getEngineTotal();
						// 阻燃
						clFre +=  q.getEngineTotal();
						clFre += ( q.getInputCol1())
								+ ( q.getInputCol2())
								+ ( q.getInputCol3())
								+ ( q.getInputCol4())
								+ ( q.getInputCol5());
						row.getCell(6).setCellValue(clFre);

						// row.getCell(7).setCellValue(listbj.get(i).getVcQuantity()+"
						// "+listbj.get(i).getVcPriceUnit());
						// 加工费
						row.getCell(7).setCellValue(StringUtils.isNotEmpty(q.getVcProcessFre()) ? Float.valueOf(q.getVcProcessFre()) : 0.0f);
						
						 * if("2".equals(q.getQuoteFormate())){
						 * row.getCell(7).setCellValue(listbj.get(i).getVcPrice()*this.getExchangeRate("1",
						 * "HKD")); }else{
						 
						// row.getCell(8).setCellValue(listbj.get(i).getVcPrice()+"
						// "+listbj.get(i).getVcMoney());
						// 量窗费
						row.getCell(8).setCellValue( q.getLcFre());
						// }
						String freight = ProPrice.getTwoDecimalFloat(((( q.getLowestFreight()) / allQuoteNum) * listbj.get(i).getVcQuantity())) + "";
						// row.getCell(9).setCellValue(freight);
						// 安装费
						row.getCell(9).setCellValue(StringUtils.isNotEmpty(q.getVcInstallFre()) ? Float.valueOf(q.getVcInstallFre()) : 0.0f);
						// row.getCell(10).setCellValue(q.getContainTax());
						float tranFre = 0.0f;
						for (QuoteFabric qf : listbj) {
							tranFre += (qf.getVcPrice() - qf.getVcOldPrice()) * qf.getVcQuantity();
						}
						// 最低运费
						tranFre +=  q.getLowestFreight();
						// 货到工地运费
						tranFre += q.getArriveTransport();
						// 加急费
						tranFre +=  q.getUrgentCost();
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
								+ ( q.getLcFre())
								+ (StringUtils.isNotEmpty(q.getVcInstallFre()) ? Float.valueOf(q.getVcInstallFre()) : 0.0f) + tranFre) * q.getContainTax();
						bjTotle += total;
						// 合计
						row.getCell(12).setCellValue(total);
						float clTotal =  p.getClTotal();
						cbTotle += clTotal;
						// 材料合计
						row.getCell(13).setCellValue(clTotal);
						// row.getCell(14).setCellValue(listcb.get(i).getVcQuoteNum()==null?0+"":listcb.get(i).getVcQuoteNum()+"
						// "+listbj.get(i).getVcOldPriceUnit());
						// 加工费
						row.getCell(14).setCellValue( p.getProcessFee());
						float cbjia =  listcb.get(i).getShijia();
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
						row.getCell(15).setCellValue( p.getInstallFee());
						// if("1".equals(listbj.get(i).getOffsetQF())){
						// cbTotle -=
						// ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia);
						// }else{
						// cbTotle +=
						// ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia);
						// }
						// row.getCell(16).setCellValue(("1".equals(listbj.get(i).getOffsetQF()))?-ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia):ProPrice.getTwoDecimalFloat(listcb.get(i).getVcQuoteNum()*cbjia));
						// 运费
						row.getCell(16).setCellValue( o.getFreight() + "");
						// row.getCell(17).setCellValue(q.getVcProcessFre());
						// 差旅费
						row.getCell(17).setCellValue( p.getTravelExpenses());
						// row.getCell(18).setCellValue(q.getVcInstallFre());
						// 设计费
						List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
						Float designTotelMoney = 0F;
						if (des != null && des.size() > 0) {
							designTotelMoney =  des.get(0).getDesignTotelMoney();
							row.getCell(18).setCellValue(designTotelMoney);
						}
						// row.getCell(19).setCellValue(o.getFreight()==null?"0":o.getFreight()+"");
						// 税费
						Float sellFre = 0F;
						sellFre = (StringUtils.isEmpty(q.getVcProcessFre()) ? 0F : new Float(q.getVcProcessFre()))
								+ (StringUtils.isEmpty(q.getVcInstallFre()) ? 0F : new Float(q.getVcInstallFre()))
								+ (o.getFreight())
								+ (StringUtils.isEmpty(q.getVcTravelFre()) ? 0F : new Float(q.getVcTravelFre()))
								+ (designTotelMoney)
								+ (q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1))
								+ (StringUtils.isEmpty(q.getVcOther()) ? 0F : new Float(q.getVcOther()));
						row.getCell(19).setCellValue(q.getSumMoney() / q.getContainTax() * (q.getContainTax() - 1));
						// 其他
						row.getCell(20).setCellValue( p.getOtherFre());
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
								Long quoteId = quoteFabric.getQuote().getId();
								if (fabricId != null && !"".equals(fabricId)) {
									String[] code = fabricId.split("_");
									List<QuoteFabric> xxqf = quoteFabricService.getQuoteFabricByCodeAndQuoteId(code[0], code[1], quoteId);
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
						allQuoteNum += listbj.get(i).getVcQuantity();
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
						
						 * if("2".equals(q.getQuoteFormate())){
						 * row.getCell(7).setCellValue(listbj.get(i).getVcPrice()*this.getExchangeRate("1",
						 * "HKD")); }else{
						 
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
						float quantity =  listbj.get(i).getVcQuantity();
						float price =  listbj.get(i).getVcPrice();
						float tax =  q.getContainTax();
						float ptotal = quantity * price * tax;
						ptotal = "1".equals(q.getOffsetQuote()) ? -ptotal : ptotal;
						//产品报价合计
						bjTotle += ptotal;
						//产品报价合计
						row.getCell(10).setCellValue(ptotal);
						row.getCell(11).setCellValue(listcb.get(i).getVcModelNum());
						row.getCell(12).setCellValue( listcb.get(i).getVcQuoteNum() + " " + listbj.get(i).getVcOldPriceUnit());
						float cbjia =  listcb.get(i).getShijia();
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
						List<DesignerExpense> des = designerExpenseService.getDesignerExpenseByQuoteId(q.getId());
						Float sellFre = 0F;
						Float designTotelMoney = 0F;
						if (des != null && des.size() > 0) {
							designTotelMoney =  des.get(0).getDesignTotelMoney();
//							row.getCell(19).setCellValue(designTotelMoney);
						}
						sellFre = (StringUtils.isEmpty(q.getVcProcessFre()) ? 0F : new Float(q.getVcProcessFre())) + (StringUtils.isEmpty(q.getVcInstallFre()) ? 0F : new Float(q.getVcInstallFre()))
								+ ( o.getFreight()) + (StringUtils.isEmpty(q.getVcTravelFre()) ? 0F : new Float(q.getVcTravelFre()))
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
					Order o = this.orderService.getOrderById(de.getOrderId());
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
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + excelName + ".xls");
			book.write(response.getOutputStream());
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

	}*/

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
	
}
