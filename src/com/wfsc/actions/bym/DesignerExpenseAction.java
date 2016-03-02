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
import com.wfsc.common.bo.bym.DesignerExpense;
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
	/*public String managerCaiwuPerson(){
		this.setTopMenu();
		listCaiwuPerson();
		return "managerCaiwuPerson";
	}
	
	@SuppressWarnings("unchecked")
	public String listCaiwuPerson(){
		Page<DesignerExpense> page = new Page<DesignerExpense>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerExpenseService.findForPage(page, paramap);
		String designer = paramap.get("designer").toString();
		for(DesignerExpense de : page.getData()){
			if(designer.equals(de.getDesigner2())){
				de.setDesigner1(de.getDesigner2());
				de.setCounselorRate1(de.getCounselorRate2());
				de.setDesigner1(de.getDesigner2());
				de.setHasApply1(de.getHasApply2());
				de.setIsGetAll1(de.getIsGetAll2());
				de.setIsApply1(de.getIsApply2());
				de.setApplyDate1(de.getApplyDate2());
				de.setRemark1(de.getRemark2());
			}else if(designer.equals(de.getDesigner3())){
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
		String listUrl = "/wfsc/admin/designerExpense_listCaiwuPerson.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listCaiwuPerson";
	}
	public String managerCaiwu(){
		this.setTopMenu();
		listCaiwu();
		return "managerCaiwu";
	}
	
	@SuppressWarnings("unchecked")
	public String listCaiwu(){
		Page<DesignerExpense> page = new Page<DesignerExpense>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerExpenseService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerExpense_listCaiwu.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listCaiwu";
	}*/
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
		FileInputStream in = null;
		List<DesignerExpense> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		String isSell = paramap.get("isSell").toString();
		String designer = paramap.get("designer")==null?"":paramap.get("designer").toString();
		String fileUrl = "";
		//if ("1".equals(isSell)) {// 销售
			if (StringUtils.isNotEmpty(designer)) {// 个人设计费
				fileUrl = "model/销售个人设计费.xls";
			} else {
				fileUrl = "model/销售设计费.xls";
			}
			/*} else if ("2".equals(isSell)) {// 财务
			if (StringUtils.isNotEmpty(designer)) {// 个人设计费
				fileUrl = "model/财务个人设计费.xls";
			} else {
				fileUrl = "model/财务设计费.xls";
			}
		}*/
	
		list = designerExpenseService.getDesignerExpenseByPara(paramap);
		
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			in = new FileInputStream(request.getRealPath(fileUrl));
			HSSFWorkbook wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			/*HSSFRow row0 = sheet.getRow(0);
			HSSFCell cell0 = row0.getCell(0);
			if (StringUtils.isNotEmpty(designer)) {// 如果是个人明细表要第一行要加上 设计师的名字
				String value = cell0.getStringCellValue();
				String lastValue = value + "(" + designer + ")";
				cell0.setCellValue(lastValue);
			}*/
			
			outputStream = response.getOutputStream();
			int i = 1;
			for(DesignerExpense designerExpense : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object[] values = {designerExpense.getApplyDate1(), designerExpense.getApplyDate1(), designerExpense.getApplyDate1()};
				
			//	if ("1".equals(isSell)) {// 销售
					if (StringUtils.isNotEmpty(designer)) {// 个人设计费
						values = null;
					} else {
						values = null;
					}
				/*} else if ("2".equals(isSell)) {// 财务
					if (StringUtils.isNotEmpty(designer)) {// 个人设计费
						values = null;
					} else {
						values = null;
					}
				}*/
				
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
				i++;
			}
			wb.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
