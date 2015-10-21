package com.wfsc.actions.bym;

import java.io.File;
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

import net.sf.json.JSONObject;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.Customer;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.ICommonService;
import com.wfsc.services.bym.service.ICustomerService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.ExcelUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("CustomerAction")
@Scope("prototype")
public class CustomerAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "客户模板.xls";

	@Resource(name = "customerService")
	private ICustomerService customerService;
	
	private Customer customer;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Customer> page = new Page<Customer>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = customerService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/customer_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			customer = customerService.getCustomerById(Long.valueOf(id));
		}else{
			customer = new Customer();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		customer = customerService.getCustomerById(Long.valueOf(id));
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
			customerService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.customLog, curAdminName+"删除了客户信息");
		return null;
	}
	
	public String disableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			customerService.disableCustomers(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.customLog, curAdminName+"禁用了客户信息");
		return null;
	}
	public String enableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			customerService.enableCustomers(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.customLog, curAdminName+"启用了客户信息");
		return null;
	}
	
	public String save(){
		customerService.saveOrUpdateEntity(customer);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.customLog, curAdminName+"新增或修改了客户信息");
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
		List<Customer> oldrecord = customerService.getAll();
		Map<String,Customer> map = new HashMap<String,Customer>();
		if(oldrecord!=null){
			for(Customer s : oldrecord){
				map.put(s.getCompanyName(), s);
			}
			
		}
		List<Customer> ss = new ArrayList<Customer>();
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
			Customer s = new Customer();
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
					}else{
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
				customerService.saveOrUpdateAll(ss);
				request.setAttribute("successMsg", "数据导入成功!");
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.customLog, curAdminName+"导入了客户信息");
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
	public String getCustomerExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String exportCustomerData(){
		List<Customer> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = customerService.getCustomerByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Customer");
			String titleStr [] = {"公司名称", "电话", "传真","地址","联系人","手机","邮箱","备注", "状态"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(Customer customer : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {customer.getCompanyName(), customer.getTel(), customer.getFax(),customer.getAddr(),
						customer.getLinkman(),customer.getPhone(),customer.getEmail(),customer.getRemk(),customer.getDis()};
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
		saveSystemLog(LogModule.customLog, curAdminName+"导出了客户信息");
		return null;
	}
	
	public String getCustomerByName(){
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> map = handleRequestParameter();
		List<Customer> list = customerService.getCustomerByPara(map);
		String result = "{}";
		if(CollectionUtils.isNotEmpty(list)){
			result = JSONObject.fromObject(list.get(0)).toString();
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
