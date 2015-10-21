package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.DesignCompany;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IDesignCompanyService;
import com.wfsc.util.ExcelUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("DesignCompanyAction")
@Scope("prototype")
public class DesignCompanyAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "设计公司模板.xls";

	@Resource(name = "designCompanyService")
	private IDesignCompanyService designCompanyService;
	
	private DesignCompany designCompany;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<DesignCompany> page = new Page<DesignCompany>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = designCompanyService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/designCompany_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			designCompany = designCompanyService.getDesignCompanyById(Long.valueOf(id));
		}else{
			designCompany = new DesignCompany();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		designCompany = designCompanyService.getDesignCompanyById(Long.valueOf(id));
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
			designCompanyService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"删除了设计公司信息");
		return null;
	}
	
	public String disableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			designCompanyService.disableDesignCompanys(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"禁用了设计公司信息");
		return null;
	}
	public String enableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			designCompanyService.enableDesignCompanys(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"启用了设计公司信息");
		return null;
	}
	
	public String save(){
		designCompanyService.saveOrUpdateEntity(designCompany);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"新增或修改了设计公司信息");
		return "ok";
	}
	
	public String toImport(){
		return "toImport";
	}
	
	public String importData(){
		InputStream is = null;
		Workbook workbook = null;
		List<String> errorList = new ArrayList<String>();
		try {
			is = new FileInputStream(getAttachment());
			workbook = WorkbookFactory.create(is);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			errorList.add("请选择文件！");
			request.setAttribute("errorMsg", errorList);
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
			errorList.add("读取Excel文件格式发生错误！");
			request.setAttribute("errorMsg", errorList);
		}catch (IOException e1) {
			e1.printStackTrace();
			errorList.add("读取Excel文件格式发生错误！");
			request.setAttribute("errorMsg", errorList);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(CollectionUtils.isNotEmpty(errorList)){
			request.setAttribute("errorMsg", errorList);
			return "toImport";
		}
		// 必填项
		List<String> mustTitles = new ArrayList<String>();
		mustTitles.add("公司名称");
		Sheet sheet = workbook.getSheetAt(0);
		Row firstRow = sheet.getRow(0);
		int colNums = firstRow.getLastCellNum();
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < colNums; i++) {
			titles.add(firstRow.getCell(i).getStringCellValue());
		}
		// 判断是否缺少必填列
		for (String s : mustTitles) {
			if (!titles.contains(s)) {
				errorList.add("缺少必填项:" + s);
			}
		}
		if(errorList.size()>0){
			request.setAttribute("errorMsg", errorList);
			return "toImport";
		}
		List<DesignCompany> oldrecord = designCompanyService.getAll();
		Map<String,DesignCompany> map = new HashMap<String,DesignCompany>();
		if(oldrecord!=null){
			for(DesignCompany s : oldrecord){
				map.put(s.getCompanyName(), s);
			}
			
		}
		List<DesignCompany> ss = new ArrayList<DesignCompany>();
		// 开始校验每一行数据
		String title = null;
		String value = null;
		String prefix = null;
		Row row = null;
		for(int i=1;i<sheet.getLastRowNum()+1;i++){
			if(sheet.getRow(i) == null || sheet.getRow(i).getCell(0) == null){
				continue;
			}
			row = sheet.getRow(i);
			String companyName = ExcelUtil.getCellValueAsString(row.getCell(0),"string");
			DesignCompany s = new DesignCompany();
			//根据工厂代码判断是否是更新
			if(map.get(companyName)!=null){
				s = map.get(companyName);
			}
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				if (StringUtils.equals(title, "公司名称")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setCompanyName(value);
					continue;
				}
				if (StringUtils.equals(title, "电话")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setTel(value);
					continue;
				}
				if (StringUtils.equals(title, "传真")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setFax(value);
					continue;
				}
				if (StringUtils.equals(title, "地址")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setAddr(value);
					continue;
				}
				if (StringUtils.equals(title, "联系人")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setLinkman(value);
					continue;
				}
				
				if (StringUtils.equals(title, "手机")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setPhone(value);
					continue;
				}
				if (StringUtils.equals(title, "邮箱")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setEmail(value);
					continue;
				}
				
				if (StringUtils.equals(title, "备注")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setRemk(value);
					continue;
				}
				if (StringUtils.equals(title, "停用")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					if("1".equals(value)){
						s.setDis("停用");
					}else {
						s.setDis("启用");
					}
					continue;
				}
				
			}
			ss.add(s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				designCompanyService.saveOrUpdateAll(ss);
				request.setAttribute("successMsg", "数据导入成功!");
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"导入了设计公司信息");
		return "toImport";
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String companyName = request.getParameter("companyName");
		String linkman = request.getParameter("linkman");
		String dis = request.getParameter("dis");
		if(StringUtils.isNotEmpty(companyName)){
			paramap.put("companyName", companyName);
			request.setAttribute("companyName", companyName);
		}
		if(StringUtils.isNotEmpty(linkman)){
			paramap.put("linkman", linkman);
			request.setAttribute("linkman", linkman);
		}
		if(StringUtils.isNotEmpty(dis)){
			paramap.put("dis", dis);
			request.setAttribute("dis", dis);
		}
		return paramap;
	}
	public String getDesignCompanyExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String exportDesignCompanyData(){
		List<DesignCompany> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = designCompanyService.getDesignCompanyByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("DesignCompany");
			String titleStr [] = {"公司名称", "电话", "传真","地址","联系人","手机","邮箱","备注", "状态"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(DesignCompany designCompany : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {designCompany.getCompanyName(), designCompany.getTel(), designCompany.getFax(),designCompany.getAddr(),
						designCompany.getLinkman(),designCompany.getPhone(),designCompany.getEmail(),designCompany.getRemk(),designCompany.getDis()};
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
		saveSystemLog(LogModule.designCompanyLog, curAdminName+"导出了设计公司信息");
		return null;
	}
	

	public DesignCompany getDesignCompany() {
		return designCompany;
	}

	public void setDesignCompany(DesignCompany designCompany) {
		this.designCompany = designCompany;
	}

	
}
