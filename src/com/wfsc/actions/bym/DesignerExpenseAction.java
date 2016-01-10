package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerExpense;
import com.wfsc.common.bo.bym.Quote;
import com.wfsc.services.bym.service.IDesignerExpenseService;
import com.wfsc.util.ProPrice;

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
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "designerExpense.xls";

	@Resource(name = "designerExpenseService")
	private IDesignerExpenseService designerExpenseService;
	
	private DesignerExpense designerExpense;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<DesignerExpense> page = new Page<DesignerExpense>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designerExpenseService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designerExpense_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			designerExpense = designerExpenseService.getDesignerExpenseById(Long.valueOf(id));
		}else{
			designerExpense = new DesignerExpense();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		designerExpense = designerExpenseService.getDesignerExpenseById(Long.valueOf(id));
		request.setAttribute("isView", true);
		return "detail";
	}
	
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		try {
			designerExpenseService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		designerExpenseService.saveOrUpdateEntity(designerExpense);
		return "ok";
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
		
		Map<String,Object> paramap = new HashMap<String,Object>();
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
	public String getDesignerExpenseExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String exportDesignerExpenseData(){
		List<DesignerExpense> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = designerExpenseService.getDesignerExpenseByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("DesignerExpense");
			String titleStr [] = {"编号", "合作方", "顾问费率"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(DesignerExpense designerExpense : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {designerExpense.getApplyDate1(), designerExpense.getApplyDate1(), designerExpense.getApplyDate1()};
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
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		return null;
	}
	
	public void downloadDesignExpense(List<DesignerExpense> list,String designer,String isSell){
		String fileUrl = "";
			if ("1".equals(isSell)) {// 销售
				if (StringUtils.isNotEmpty(designer)) {// 个人设计费
					fileUrl = "model/销售设计费明细.xls";
				} else {
					fileUrl = "model/销售设计费.xls";
				}
			} else if ("2".equals(isSell)) {// 财务
				if (StringUtils.isNotEmpty(designer)) {// 个人设计费
					fileUrl = "model/财务设计费明细.xls";
				} else {
					fileUrl = "model/财务设计费.xls";
				}
			}
		
	}
	
	
	

	public DesignerExpense getDesignerExpense() {
		return designerExpense;
	}

	public void setDesignerExpense(DesignerExpense designerExpense) {
		this.designerExpense = designerExpense;
	}

	
}
