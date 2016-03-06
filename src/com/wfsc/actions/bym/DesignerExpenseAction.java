package com.wfsc.actions.bym;

import java.io.FileInputStream;
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
import com.constants.LogModule;
import com.wfsc.common.bo.bym.DesignerExpense;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IDesignerExpenseService;
import com.wfsc.services.security.ISecurityService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("DesignerExpenseAction")
@Scope("prototype")
public class DesignerExpenseAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	@Resource(name = "designerExpenseService")
	private IDesignerExpenseService designerExpenseService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	private DesignerExpense designerExpense;

	public String managerSellPerson(){
		this.setTopMenu();
		listSellPerson();
		return "managerSellPerson";
	}
	
	@SuppressWarnings("unchecked")
	public String listSellPerson(){
		Page<DesignerExpense> page = new Page<DesignerExpense>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		String designer = paramap.get("designer")==null?"":paramap.get("designer").toString();
		if(StringUtils.isBlank(designer)){
			List<Integer> li = page.getPageNos();
			String listUrl = "/wfsc/admin/designerExpense_listSellPerson.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			return "listSellPerson";
		}
		page = designerExpenseService.findForPage(page, paramap);
		for(DesignerExpense de : page.getData()){
			if(designer.equalsIgnoreCase(de.getDesigner2())){
				de.setDesigner1(de.getDesigner2());
				de.setCounselorRate1(de.getCounselorRate2());
				de.setDesigner1(de.getDesigner2());
				de.setHasApply1(de.getHasApply2());
				de.setIsGetAll1(de.getIsGetAll2());
				de.setIsApply1(de.getIsApply2());
				de.setApplyDate1(de.getApplyDate2());
				de.setRemark1(de.getRemark2());
			}else if(designer.equalsIgnoreCase(de.getDesigner3())){
				de.setDesigner1(de.getDesigner3());
				de.setCounselorRate1(de.getCounselorRate3());
				de.setDesigner1(de.getDesigner3());
				de.setHasApply1(de.getHasApply3());
				de.setIsGetAll1(de.getIsGetAll3());
				de.setIsApply1(de.getIsApply3());
				de.setApplyDate1(de.getApplyDate3());
				de.setRemark1(de.getRemark3());
			}
		}
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerExpense_listSellPerson.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listSellPerson";
	}
	public String managerSell(){
		this.setTopMenu();
		listSell();
		return "managerSell";
	}
	
	@SuppressWarnings("unchecked")
	public String listSell(){
		Page<DesignerExpense> page = new Page<DesignerExpense>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerExpenseService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerExpense_listSell.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listSell";
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String designer = request.getParameter("designer");
		String isApply = request.getParameter("isApply");
		String isSell = request.getParameter("isSell");//销售的设计费：1；财务的设计费：2
		String contractNo = request.getParameter("contractNo");
		String quoteNo = request.getParameter("quoteNo");
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		
		Admin admin = this.getCurrentAdminUser();
		boolean isAdminb = securityService.isAbleRole(admin.getUsername(), "管理员");
		if(!isAdminb){
			paramap.put("quoteLocal", admin.getArea());
		}
		
		if(StringUtils.isNotEmpty(quoteNo)){
			paramap.put("quoteNo", quoteNo);
			request.setAttribute("quoteNo", quoteNo);
		}
		if(StringUtils.isNotEmpty(beginDate)){
			paramap.put("beginDate", beginDate);
			request.setAttribute("beginDate", beginDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			paramap.put("endDate", endDate);
			request.setAttribute("endDate", endDate);
		}
		
		if(StringUtils.isNotEmpty(designer)){
			paramap.put("designer", designer);
			request.setAttribute("designer", designer);
		}
		if(StringUtils.isNotEmpty(isApply)){
			paramap.put("isApply", isApply);
			request.setAttribute("isApply", isApply);
		}
		if(StringUtils.isNotEmpty(isSell)){
			paramap.put("isSell", isSell);
			request.setAttribute("isSell", isSell);
		}
		if(StringUtils.isNotEmpty(contractNo)){
			paramap.put("contractNo", contractNo);
			request.setAttribute("contractNo", contractNo);
		}
		return paramap;
	}
	
	public String exportDesignerExpenseData(){
		List<DesignerExpense> list = null;
		Map<String,Object> paramap = this.handleRequestParameter();
		list = designerExpenseService.getDesignerExpenseByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("DesignerExpense");
			String[] titleStr = null;
			if(paramap.get("designer")==null){//非个人
				titleStr = new String[]{"时间","报价单号","合同号","客户名称","项目","合同金额","加工费","安装费","加急费","运费","税费","后处理","其它"
						,"实际金额","设计师","比例","设计费金额","已付金额","未付金额","支付时间","备注","设计师","比例","设计费金额","已付金额","未付金额","支付时间","备注"
						,"设计师","比例","设计费金额","已付金额","未付金额","支付时间","备注","总设计费"};
			}else{//个人
				titleStr = new String[]{"时间","报价单号","合同号","客户名称","项目","合同金额","加工费","安装费","加急费","运费","税费","后处理","其它","实际金额"
						,"设计师","比例","设计费金额","实际收款金额","是否收齐款","是否支付","支付时间","备注"};
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
					HSSFCell c = cRow.createCell(5);
					c.setCellValue(sumMoney);
				}else{
					DesignerExpense de = list.get(m-1);
					sumMoney+=de.getSumMoney();
					Object[] values = null;
					if(paramap.get("designer")==null){//非个人
						values = new Object[]{de.getCreateDate(),de.getQuoteNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getVcProcessFre(),de.getVcInstallFre(),de.getUrgentCost(),de.getFreight(),de.getTaxationCost(),
								de.getVcAftertreatment(),de.getVcOther(),de.getRealTotel(),de.getDesigner1(),de.getCounselorRate1(),
								de.getDesignMony1(),de.getHasApply1(),de.getHasNoApply1(),de.getApplyDate1(),de.getRemark1(),de.getDesigner2(),de.getCounselorRate2(),
								de.getDesignMony2(),de.getHasApply2(),de.getHasNoApply2(),de.getApplyDate2(),de.getRemark2(),de.getDesigner3(),de.getCounselorRate3(),
								de.getDesignMony3(),de.getHasApply3(),de.getHasNoApply3(),de.getApplyDate3(),de.getRemark3(),de.getDesignTotelMoney()};
					}else{
						if(paramap.get("designer").toString().equalsIgnoreCase(de.getDesigner2())){
							de.setDesigner1(de.getDesigner2());
							de.setCounselorRate1(de.getCounselorRate2());
							de.setDesigner1(de.getDesigner2());
							de.setHasApply1(de.getHasApply2());
							de.setIsGetAll1(de.getIsGetAll2());
							de.setIsApply1(de.getIsApply2());
							de.setApplyDate1(de.getApplyDate2());
							de.setRemark1(de.getRemark2());
						}else if(paramap.get("designer").toString().equalsIgnoreCase(de.getDesigner3())){
							de.setDesigner1(de.getDesigner3());
							de.setCounselorRate1(de.getCounselorRate3());
							de.setDesigner1(de.getDesigner3());
							de.setHasApply1(de.getHasApply3());
							de.setIsGetAll1(de.getIsGetAll3());
							de.setIsApply1(de.getIsApply3());
							de.setApplyDate1(de.getApplyDate3());
							de.setRemark1(de.getRemark3());
						}
						values = new Object[]{de.getCreateDate(),de.getQuoteNo(),de.getContractNo(),de.getCustomerName(),de.getProjectName(),de.getSumMoney()
								,de.getVcProcessFre(),de.getVcInstallFre(),de.getUrgentCost(),de.getFreight(),de.getTaxationCost(),
								de.getVcAftertreatment(),de.getVcOther(),de.getRealTotel(),de.getDesigner1(),de.getCounselorRate1(),
								de.getDesignMony1(),de.getHasApply1(),de.getIsGetAll1(),de.getIsApply1(),de.getApplyDate1(),de.getRemark1()};
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
	

	public DesignerExpense getDesignerExpense() {
		return designerExpense;
	}

	public void setDesignerExpense(DesignerExpense designerExpense) {
		this.designerExpense = designerExpense;
	}

	
}
