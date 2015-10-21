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
import com.wfsc.common.bo.bym.CurrencyConversion;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.util.ExcelUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("CurrencyConversionAction")
@Scope("prototype")
public class CurrencyConversionAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "汇率转换模板.xls";

	@Resource(name = "currencyConversionService")
	private ICurrencyConversionService currencyConversionService;
	
	private CurrencyConversion currencyConversion;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<CurrencyConversion> page = new Page<CurrencyConversion>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = currencyConversionService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/currencyConversion_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			currencyConversion = currencyConversionService.getCurrencyConversionById(Long.valueOf(id));
		}else{
			currencyConversion = new CurrencyConversion();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		currencyConversion = currencyConversionService.getCurrencyConversionById(Long.valueOf(id));
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
			currencyConversionService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.currencyConversionLog, curAdminName+"删除了货币信息");
		return null;
	}
	
	public String save(){
		currencyConversionService.saveOrUpdateEntity(currencyConversion);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.currencyConversionLog, curAdminName+"新增或修改了货币信息");
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
		mustTitles.add("币种");
		mustTitles.add("目标币种");
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
		List<CurrencyConversion> oldrecord = currencyConversionService.getAll();
		Map<String,CurrencyConversion> map = new HashMap<String,CurrencyConversion>();
		if(oldrecord!=null){
			for(CurrencyConversion s : oldrecord){
				map.put(s.getVcCurrency()+"_"+s.getVcObjCurrency(), s);
			}
			
		}
		List<CurrencyConversion> ss = new ArrayList<CurrencyConversion>();
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
			String vcCurrency = ExcelUtil.getCellValueAsString(row.getCell(0),"string");
			String vcObjCurrency = ExcelUtil.getCellValueAsString(row.getCell(1),"string");
			CurrencyConversion s = new CurrencyConversion();
			//根据工厂代码判断是否是更新
			if(map.get(vcCurrency+"_"+vcObjCurrency)!=null){
				s = map.get(vcCurrency+"_"+vcObjCurrency);
			}
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				if (StringUtils.equals(title, "币种")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcCurrency(value);
					continue;
				}
				if (StringUtils.equals(title, "目标币种")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcObjCurrency(value);
					continue;
				}
				if (StringUtils.equals(title, "汇率")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcExchangeRate(d.floatValue());
						}else{
							s.setVcExchangeRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
			}
			ss.add(s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				currencyConversionService.saveOrUpdateAll(ss);
				request.setAttribute("successMsg", "数据导入成功!");
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.currencyConversionLog, curAdminName+"导入了货币信息");
		return "toImport";
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String vcCurrency = request.getParameter("vcCurrency");
		String vcObjCurrency = request.getParameter("vcObjCurrency");
		if(StringUtils.isNotEmpty(vcCurrency)){
			paramap.put("vcCurrency", vcCurrency);
			request.setAttribute("vcCurrency", vcCurrency);
		}
		if(StringUtils.isNotEmpty(vcObjCurrency)){
			paramap.put("vcObjCurrency", vcObjCurrency);
			request.setAttribute("vcObjCurrency", vcObjCurrency);
		}
		return paramap;
	}
	public String getCurrencyConversionExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String exportCurrencyConversionData(){
		List<CurrencyConversion> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = currencyConversionService.getCurrencyConversionByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("CurrencyConversion");
			String titleStr [] = {"币种", "目标币种", "汇率"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(CurrencyConversion currencyConversion : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {currencyConversion.getVcCurrency(), currencyConversion.getVcObjCurrency(), new Float(currencyConversion.getVcExchangeRate())};
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
		saveSystemLog(LogModule.currencyConversionLog, curAdminName+"导出了货币信息");
		return null;
	}
	

	public CurrencyConversion getCurrencyConversion() {
		return currencyConversion;
	}

	public void setCurrencyConversion(CurrencyConversion currencyConversion) {
		this.currencyConversion = currencyConversion;
	}

	
}
