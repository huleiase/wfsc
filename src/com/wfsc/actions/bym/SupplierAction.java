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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.wfsc.common.bo.bym.Fabric;
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.ISupplierService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.ExcelUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("SupplierAction")
@Scope("prototype")
public class SupplierAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "供应商模板.xls";

	@Resource(name = "supplierService")
	private ISupplierService supplierService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	private Supplier supplier;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Supplier> page = new Page<Supplier>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = supplierService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/supplier_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
	//	request.setAttribute("list", page.getData());
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			supplier = supplierService.getSupplierById(Long.valueOf(id));
		}else{
			supplier = new Supplier();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean purManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		String flag = "0";
		if(isAdmin||purManager){
			flag = "1";
		}
		request.setAttribute("flag", flag);
		String id = request.getParameter("id");
		supplier = supplierService.getSupplierById(Long.valueOf(id));
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
			supplierService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String disableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			supplierService.disableSuppliers(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String enableByIds(){
		String ids = request.getParameter("ids");
		String[] idsArray = ids.split(",");
		try {
			supplierService.enableSuppliers(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		supplier.setCreateDate(new Date());
		supplierService.saveOrUpdateEntity(supplier);
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
		mustTitles.add("工厂代码");
		mustTitles.add("产地编号");
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
		List<Supplier> oldrecord = supplierService.getAll();
		Map<String,Supplier> map = new HashMap<String,Supplier>();
		if(oldrecord!=null){
			for(Supplier s : oldrecord){
				map.put(s.getVcFactoryCode(), s);
			}
			
		}
		List<Supplier> ss = new ArrayList<Supplier>();
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
			String vcFactoryCode = ExcelUtil.getCellValueAsString(row.getCell(0),"string");
			Supplier s = new Supplier();
			//根据工厂代码判断是否是更新
			if(map.get(vcFactoryCode)!=null){
				s = map.get(vcFactoryCode);
			}
			s.setCreateDate(new Date());
			s.setCurUserName(this.getCurrentAdminUser().getUsername());
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				if (StringUtils.equals(title, "工厂代码")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFactoryCode(value);
					continue;
				}
				if (StringUtils.equals(title, "名称")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFactoryName(value);
					continue;
				}
				if (StringUtils.equals(title, "产地")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcPlaceProduce(value);
					continue;
				}
				if (StringUtils.equals(title, "布料进货货币")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFabPriceCur(value);
					continue;
				}
				if (StringUtils.equals(title, "布料进货尺度")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFabMeasure(value);
					continue;
				}
				if (StringUtils.equals(title, "布料进货折扣")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcFabPurDis(d.floatValue());
						}else{
							s.setVcFabPurDis(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "皮料进货货币")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcLeaPriceCur(value);
					continue;
				}
				if (StringUtils.equals(title, "皮料进货尺度")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcLeaMeasure(value);
					continue;
				}
				if (StringUtils.equals(title, "皮料进货折扣")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcLeaPurDis(d.floatValue());
						}else{
							s.setVcLeaPurDis(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "布料最低运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcFabMinFre(d.floatValue());
						}else{
							s.setVcFabMinFre(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "皮料最低运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcLeaMinFre(d.floatValue());
						}else{
							s.setVcLeaMinFre(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "地址")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFactoryAddr(value);
					continue;
				}
				if (StringUtils.equals(title, "联系人")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcLinkMan(value);
					continue;
				}
				if (StringUtils.equals(title, "电话")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcTel(value);
					continue;
				}
				if (StringUtils.equals(title, "传真")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFax(value);
					continue;
				}
				if (StringUtils.equals(title, "邮箱")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcEmail(value);
					continue;
				}
				if (StringUtils.equals(title, "备注")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcRemarks(value);
					continue;
				}
				if (StringUtils.equals(title, "停用")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					if("1".equals(value)){
						s.setVcDis("停用");
					}else{
						s.setVcDis("启用");
					}
					continue;
				}
				if (StringUtils.equals(title, "产地编号")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcFactoryNum(value);
					continue;
				}
				
				if (StringUtils.equals(title, "国内运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setHomeTransportCost(value);
					continue;
				}
				if (StringUtils.equals(title, "国内最低运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setHomeLowTransportCost(d.floatValue());
						}else{
							s.setHomeLowTransportCost(0.0F);
						}
					}catch (Exception e) {
						Pattern pattern = Pattern.compile("\\d+");
				        Matcher matcher = pattern.matcher(value);
				        if(matcher.find()){
				        	value = matcher.group();
					        s.setHomeLowTransportCost(Float.valueOf(value));
				        }else{
				        	errorList.add(prefix + "数字格式错误");
				        }
					}
					continue;
				}
				if (StringUtils.equals(title, "香港运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setHkTransportCost(value);
					continue;
				}
				if (StringUtils.equals(title, "香港最低运费")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setHkLowTransportCost(d.floatValue());
						}else{
							s.setHkLowTransportCost(0.0F);
						}
					}catch (Exception e) {
						Pattern pattern = Pattern.compile("\\d+");
				        Matcher matcher = pattern.matcher(value);
				        if(matcher.find()){
				        	  value = matcher.group();
						      s.setHkLowTransportCost(Float.valueOf(value));
				        }else{
				        	errorList.add(prefix + "数字格式错误");
				        }
					}
					continue;
				}
				if (StringUtils.equals(title, "零售系数")) {
					value = ExcelUtil.getCellValueAsString(cell,"float");
					try{
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setRetailCoefficient(d.floatValue());
						}else{
							s.setRetailCoefficient(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "品牌属性")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setBrandAttri(value);
					continue;
				}
				if (StringUtils.equals(title, "产品范围")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setProductRange(value);
					continue;
				}
				if (StringUtils.equals(title, "订货折扣")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setOrderDis(value);
					continue;
				}
				if (StringUtils.equalsIgnoreCase(title, "QQ")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setQq(value);
					continue;
				}
				if (StringUtils.equals(title, "市场透明度")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setMarketClarity(value);
					continue;
				}
				if (StringUtils.equals(title, "工厂属性")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setFactoryPro(value);
					continue;
				}
				if (StringUtils.equals(title, "备注2")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcRemarks2(value);
					continue;
				}
				if (StringUtils.equals(title, "最小起订量")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setMOQ(value);
					continue;
				}
				if (StringUtils.equals(title, "最小增量")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setLeastIncrement(value);
					continue;
				}
			}
			ss.add(s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				supplierService.saveOrUpdateAll(ss);
				request.setAttribute("successMsg", "数据导入成功!");
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		return "toImport";
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String vcFactoryCode = request.getParameter("vcFactoryCode");
		String vcFactoryName = request.getParameter("vcFactoryName");
		String vcPlaceProduce = request.getParameter("vcPlaceProduce");
		String vcLinkMan = request.getParameter("vcLinkMan");
		String vcDis = request.getParameter("vcDis");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String vcRemarks = request.getParameter("vcRemarks");
		if(StringUtils.isNotEmpty(vcRemarks)){
			paramap.put("vcRemarks", vcRemarks);
			request.setAttribute("vcRemarks", vcRemarks);
		}
		if(StringUtils.isNotEmpty(vcFactoryCode)){
			paramap.put("vcFactoryCode", vcFactoryCode);
			request.setAttribute("vcFactoryCode", vcFactoryCode);
		}
		if(StringUtils.isNotEmpty(vcFactoryName)){
			paramap.put("vcFactoryName", vcFactoryName);
			request.setAttribute("vcFactoryName", vcFactoryName);
		}
		if(StringUtils.isNotEmpty(vcPlaceProduce)){
			paramap.put("vcPlaceProduce", vcPlaceProduce);
			request.setAttribute("vcPlaceProduce", vcPlaceProduce);
		}
		if(StringUtils.isNotEmpty(vcLinkMan)){
			paramap.put("vcLinkMan", vcLinkMan);
			request.setAttribute("vcLinkMan", vcLinkMan);
		}
		if(StringUtils.isNotEmpty(vcDis)){
			paramap.put("vcDis", vcDis);
			request.setAttribute("vcDis", vcDis);
		}
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime);
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime);
			request.setAttribute("endTime", endTime);
		}
		return paramap;
	}
	public String getSupplierExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String exportSupplierData(){
		List<Supplier> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = supplierService.getSupplierByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Supplier");
			String titleStr [] = {"工厂代码", "名称", "产地","布料进货货币","布料进货尺度","布料进货折扣","皮料进货货币","皮料进货尺度","皮料进货折扣", "布料最低运费","皮料最低运费", "地址", "联系人", "电话", "传真", "邮箱", "备注", "状态", "产地编号"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(Supplier supplier : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {supplier.getVcFactoryCode(), supplier.getVcFactoryName(), supplier.getVcPlaceProduce(),supplier.getVcFabPriceCur(),
						supplier.getVcFabMeasure(),supplier.getVcFabPurDis(),supplier.getVcLeaPriceCur(),supplier.getVcLeaMeasure(),supplier.getVcLeaPurDis(),
						supplier.getVcFabMinFre(), supplier.getVcLeaMinFre(), supplier.getVcFactoryAddr(), supplier.getVcLinkMan(),
						supplier.getVcTel(), supplier.getVcFax(), supplier.getVcEmail(), supplier.getVcRemarks(), supplier.getVcDis(),
						supplier.getVcFactoryNum()};
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
		
		return null;
	}
	public String deleteByQuery(){
		try {
			Map<String,Object> paramap = handleRequestParameter();
			List<Supplier> ss = supplierService.getSupplierByPara(paramap);
			supplierService.deleteAll(ss);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
