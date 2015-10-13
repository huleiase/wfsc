package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.CurrencyConversion;
import com.wfsc.common.bo.bym.Fabric;
import com.wfsc.common.bo.bym.Supplier;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.ICurrencyConversionService;
import com.wfsc.services.bym.service.IFabricService;
import com.wfsc.services.bym.service.ISupplierService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.ExcelUtil;
import com.wfsc.util.PriceUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("FabricAction")
@Scope("prototype")
public class FabricAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "布料模板.xls";
	
	private static final String MODEL_EXCEL_NAME_HT = "对照码模板.xls";
	
	private static final String MODEL_EXCEL_NAME_DHJ = "大货价模板.xls";
	
	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	@Resource(name = "fabricService")
	private IFabricService fabricService;
	
	@Resource(name = "currencyConversionService")
	private ICurrencyConversionService currencyConversionService;
	
	@Resource(name = "supplierService")
	private ISupplierService supplierService;
	
	@Resource(name = "securityService")
	private ISecurityService securityService;
	
	private Fabric fabric;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	public String managerHT(){
		this.setTopMenu();
		listHT();
		return "managerHT";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Fabric> page = new Page<Fabric>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = fabricService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/fabric_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	@SuppressWarnings("unchecked")
	public String listHT(){
		Page<Fabric> page = new Page<Fabric>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = fabricService.findHTForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/fabric_listHT.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listHT";
	}
	
	public String input() {
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isPurManager", isPurManager);
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			fabric = fabricService.getFabricById(Long.valueOf(id));
		}else{
			fabric = new Fabric();
		}
		String factoryCode = fabric.getVcFactoryCode();
		Supplier s = this.supplierService.getSupplierByCode(factoryCode);
		if(s!=null){
			fabric.setFactoryName(s.getVcFactoryName());
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String inputHT() {
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isPurManager", isPurManager);
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			fabric = fabricService.getFabricById(Long.valueOf(id));
		}else{
			fabric = new Fabric();
		}
		String factoryCode = fabric.getVcFactoryCode();
		Supplier s = this.supplierService.getSupplierByCode(factoryCode);
		if(s!=null){
			fabric.setFactoryName(s.getVcFactoryName());
		}
		request.setAttribute("isView", false);
		return "inputHT";
	}
	
	public String detail() {
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			fabric = fabricService.getFabricById(Long.valueOf(id));
		}else{
			String vcFactoryCode = request.getParameter("vcFactoryCode");
			String vcBefModel = request.getParameter("vcBefModel");
			fabric = fabricService.getFabricByCode(vcFactoryCode, vcBefModel);
		}
		fabric = fabric == null?new Fabric():fabric;
		String factoryCode = fabric.getVcFactoryCode();
		Supplier s = this.supplierService.getSupplierByCode(factoryCode);
		if(s!=null){
			fabric.setFactoryName(s.getVcFactoryName());
		}
		request.setAttribute("isView", true);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isPurManager", isPurManager);
		return "detail";
	}
	
	public String detailHT() {
		Admin admin = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(admin.getUsername(), "超级管理员");
		boolean isPurManager = securityService.isAbleRole(admin.getUsername(), "采购经理");
		String id = request.getParameter("id");
		fabric = fabricService.getFabricById(Long.valueOf(id));
		String factoryCode = fabric.getVcFactoryCode();
		Supplier s = this.supplierService.getSupplierByCode(factoryCode);
		if(s!=null){
			fabric.setFactoryName(s.getVcFactoryName());
		}
		request.setAttribute("isView", true);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isPurManager", isPurManager);
		return "detailHT";
	}
	
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		try {
			fabricService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String deleteByQuery(){
		String isHtCode = request.getParameter("isHtCode");
		try {
			Map<String,Object> paramap = handleRequestParameter();
			List<Fabric> fs = new ArrayList<Fabric>();
			if("1".equals(isHtCode)){
				fs = fabricService.getHTFabricByPara(paramap);
			}else{
				fs = fabricService.getFabricByPara(paramap);
			}
			fabricService.deleteFabrics(fs);
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
			fabricService.disableFabrics(idsArray);
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
			fabricService.enableFabrics(idsArray);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		fabric.setIsHtCode("0");
		fabricService.saveOrUpdateEntity(fabric);
		return "ok";
	}
	
	public String saveHT(){
		String vcFactoryCode = fabric.getVcFactoryCode();
		String vcBefModel = fabric.getVcBefModel();
		Fabric f = fabricService.getFabricByCode(vcFactoryCode, vcBefModel);
		fabric.setRefid(f.getId());
		fabric.setIsHtCode("1");
		fabricService.saveOrUpdateEntity(fabric);
		return "okHT";
	}
	
	public String toImport(){
		String importFactory = request.getParameter("importFactory");
		request.setAttribute("importFactory", importFactory);
		return "toImport";
	}
	public String toImportDHJ(){
		return "toImportDHJ";
	}
	
	public String toImportHT(){
		return "toImportHT";
	}
	
	public String importData(){
		String importFactory = request.getParameter("importFactory");
		request.setAttribute("importFactory", importFactory);
		InputStream is = null;
		Workbook workbook = null;
		List<String> errorList = new ArrayList<String>();
		try {
			long size = getAttachment().length();
			if(size>=1024*1024*5){
				errorList.add("文件不能大于5M，请拆分！");
				request.setAttribute("errorMsg", errorList);
				return "toImport";
			}
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
		mustTitles.add("工厂代号");
		mustTitles.add("原厂型号");
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
		Map<String,Fabric> excelMap = new HashMap<String,Fabric>();
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
			String vcBefModel = ExcelUtil.getCellValueAsString(row.getCell(2),"string");
			boolean isNew = true;
			boolean isDHJOnly = false;
			Fabric s = fabricService.getFabricByCode(vcFactoryCode, vcBefModel);
			String addOrUpdate = "更新";
			if(s!=null){
				isNew = false;
				if(s.getDhjCost()>0&&s.getVcOldPrice()<1){
					isDHJOnly = true;
				}
			}else{
				addOrUpdate = "新增";
				s = new Fabric();
			}
			log.info(i+"-->"+addOrUpdate+"-->"+vcFactoryCode+"-->"+vcBefModel);
			if(excelMap.containsKey(vcFactoryCode+vcBefModel)){
				log.info(i+"-->"+"有重复-->"+vcFactoryCode+"-->"+vcBefModel);
				excelMap.remove(vcFactoryCode+vcBefModel);
			}
			if(StringUtils.isNotBlank(importFactory)){
				s.setImportFactory(importFactory);
			}
			s.setCreateDate(new Date());
			s.setIsHtCode("0");
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				if (s.getId()==null) {
					if (StringUtils.equals(title, "工厂代号")) {
						value = ExcelUtil.getCellValueAsString(cell, "string");
						s.setVcFactoryCode(value);
						continue;
					}
					if (StringUtils.equals(title, "原厂型号")) {
						value = ExcelUtil.getCellValueAsString(cell, "string");
						s.setVcBefModel(value);
						continue;
					}
				}
				//1、常规导入都要执行，2、只有大货价也要执行,3、第一次导入
				if(StringUtils.isNotBlank(importFactory)||isDHJOnly||isNew){
					if (StringUtils.equals(title, "类型")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcType(value);
						continue;
					}
					if (StringUtils.equals(title, "卷长")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcPieceLength(value);
						continue;
					}
					
					if (StringUtils.equals(title, "起订量")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcMinNum(value);
						continue;
					}
					if (StringUtils.equals(title, "最小分格")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcMinLattice(value);
						continue;
					}
					
					if (StringUtils.equals(title, "成份")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcComposition(value);
						continue;
					}
					if (StringUtils.equals(title, "密度")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcDensity(value);
						continue;
					}
					if (StringUtils.equals(title, "回位")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcRepeat(value);
						continue;
					}
					if (StringUtils.equals(title, "定高宽")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcFixWidthHight(value);
						continue;
					}
					if (StringUtils.equals(title, "收缩")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcShrink(value);
						continue;
					}
					if (StringUtils.equals(title, "耐磨度(DR)")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcDr(value);
						continue;
					}
					
					if (StringUtils.equals(title, "阻燃(FR)")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcFr(value);
						continue;
					}
					if (StringUtils.equals(title, "环保(ECO)")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcEco(value);
						continue;
					}
					
					if (StringUtils.equals(title, "防污")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcAntifouling(value);
						continue;
					}
					if (StringUtils.equals(title, "色牢度")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcLightFas(value);
						continue;
					}
					if (StringUtils.equals(title, "洗涤")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcClean(value);
						continue;
					}
					if (StringUtils.equals(title, "用途")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcApp(value);
						continue;
					}
					if (StringUtils.equals(title, "进价单位")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcMeasure(value);
						continue;
					}
					
					if (StringUtils.equals(title, "进价货币")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcPriceCur(value);
						continue;
					}
					if (StringUtils.equals(title, "采购折扣")) {
						try{
							value = ExcelUtil.getCellValueAsString(cell,"float");
							if(StringUtils.isNotBlank(value)){
								Double d = Double.valueOf(value);
								s.setVcPurDis(d.floatValue());
							}else{
								s.setVcPurDis(1.0F);
							}
						}catch (Exception e) {
							errorList.add(prefix + "数字格式错误");
						}
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
					if (StringUtils.equals(title, "备注1")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcRemark1(value);
						continue;
					}
					if (StringUtils.equals(title, "备注2")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcRemark2(value);
						continue;
					}
					
					if (StringUtils.equals(title, "备注3")) {
						value = ExcelUtil.getCellValueAsString(cell,"string");
						s.setVcRemark3(value);
						continue;
					}
				}

				if (StringUtils.equals(title, "幅宽")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcWidth(d.floatValue());
						}else{
							s.setVcWidth(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "原始进价")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcOldPrice(d.floatValue());
						}else{
							s.setVcOldPrice(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "工程系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcProRatio(d.floatValue());
						}else{
							s.setVcProRatio(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				
				if (StringUtils.equals(title, "分销系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcRetailRatio(d.floatValue());
						}else{
							s.setVcRetailRatio(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				
				if (StringUtils.equals(title, "工程运费")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcProFre(d.floatValue());
						}else{
							s.setVcProFre(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				
				if (StringUtils.equals(title, "零售运费")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setVcRetFre(d.floatValue());
						}else{
							s.setVcRetFre(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价幅宽")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjWidth(d.floatValue());
						}else{
							s.setDhjWidth(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价成本")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjCost(d.floatValue());
						}else{
							s.setDhjCost(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价大陆系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjInlandRate(d.floatValue());
						}else{
							s.setDhjInlandRate(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价香港系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjHKRate(d.floatValue());
						}else{
							s.setDhjHKRate(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价大陆运费")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjInlandTransCost(d.floatValue());
						}else{
							s.setDhjInlandTransCost(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大货价香港运费")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setDhjHKTransCost(d.floatValue());
						}else{
							s.setDhjHKTransCost(0.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
			}
			excelMap.put(vcFactoryCode+vcBefModel, s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				//Collection<Fabric> ssList = excelMap.values();
				for(String key : excelMap.keySet()){
					fabricService.saveOrUpdateEntity(excelMap.get(key));
				}
				//fabricService.saveOrUpdateAll(ssList);
				request.setAttribute("successMsg", "数据导入成功!");
				excelMap.clear();
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		return "toImport";
	}
	public String importHTData(){
		InputStream is = null;
		Workbook workbook = null;
		List<String> errorList = new ArrayList<String>();
		try {
			long size = getAttachment().length();
			if(size>=1024*1024*5){
				errorList.add("文件不能大于3M，请拆分！");
				request.setAttribute("errorMsg", errorList);
				return "toImportHT";
			}
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
			return "toImportHT";
		}
		// 必填项
		List<String> mustTitles = new ArrayList<String>();
		mustTitles.add("工厂代号");
		mustTitles.add("原厂型号");
		mustTitles.add("HT型号");
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
			return "toImportHT";
		}
		Map<String,Fabric> excelMap = new HashMap<String,Fabric>();
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
			String vcBefModel = ExcelUtil.getCellValueAsString(row.getCell(1),"string");
			String htCode = ExcelUtil.getCellValueAsString(row.getCell(3),"string");
			String addOrUpdate = "更新";
			Fabric s = fabricService.getHTFabricByCode(vcFactoryCode, vcBefModel, htCode);
			if(s==null){
				addOrUpdate = "新增";
				s = new Fabric();
				Long refId = fabricService.getRefIdByCode(vcFactoryCode, vcBefModel);
				if(refId == null){
					errorList.add("第" + (i + 1) + "行" + "没有找到对应的原厂型号");
					continue;
				}
				s.setRefid(refId);
			}
			log.info(i+"-->"+addOrUpdate+"-->"+vcFactoryCode+"-->"+vcBefModel+"-->"+htCode);
			if(excelMap.containsKey(vcFactoryCode+vcBefModel+htCode)){
				log.info(i+"-->"+"有重复-->"+vcFactoryCode+"-->"+vcBefModel+"-->"+htCode);
				excelMap.remove(vcFactoryCode+vcBefModel+htCode);
			}
			s.setCreateDate(new Date());
			s.setIsHtCode("1");
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				//只有新增的时候才设置这几个，因为这几个不会变，而且加了索引，更新的话会影响效率
				if (s.getId()==null) {
					if (StringUtils.equals(title, "工厂代号")) {
						value = ExcelUtil.getCellValueAsString(cell, "string");
						s.setVcFactoryCode(value);
						continue;
					}
					if (StringUtils.equals(title, "原厂型号")) {
						value = ExcelUtil.getCellValueAsString(cell, "string");
						s.setVcBefModel(value);
						continue;
					}
					if (StringUtils.equals(title, "HT型号")) {
						value = ExcelUtil.getCellValueAsString(cell, "string");
						s.setHtCode(value);
						continue;
					}
				}
				if (StringUtils.equals(title, "色号")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setColorCode(value);
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
				if (StringUtils.equals(title, "书号")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setBookNo(value);
					continue;
				}
				if (StringUtils.equals(title, "品牌")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setBrand(value);
					continue;
				}
				if (StringUtils.equals(title, "大陆加工系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setInlandRefitRate(d.floatValue());
						}else{
							s.setInlandRefitRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "香港加工系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setHkRefitRate(d.floatValue());
						}else{
							s.setHkRefitRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大陆上调系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setInlandRaiseRate(d.floatValue());
						}else{
							s.setInlandRaiseRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "香港上调系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setHkRaiseRate(d.floatValue());
						}else{
							s.setHkRaiseRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "大陆下调系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setInlandDownRate(d.floatValue());
						}else{
							s.setInlandDownRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "香港下调系数")) {
					try{
						value = ExcelUtil.getCellValueAsString(cell,"float");
						if(StringUtils.isNotBlank(value)){
							Double d = Double.valueOf(value);
							s.setHkDownRate(d.floatValue());
						}else{
							s.setHkDownRate(1.0F);
						}
					}catch (Exception e) {
						errorList.add(prefix + "数字格式错误");
					}
					continue;
				}
			}
			excelMap.put(vcFactoryCode+vcBefModel+htCode, s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				//Collection<Fabric> ss = excelMap.values();
				//fabricService.saveOrUpdateAll(ss);
				for(String key : excelMap.keySet()){
					fabricService.saveOrUpdateEntity(excelMap.get(key));
				}
				request.setAttribute("successMsg", "数据导入成功!");
				excelMap.clear();
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		return "toImportHT";
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String vcFactoryCode = request.getParameter("vcFactoryCode");
		String vcBefModel = request.getParameter("vcBefModel");
		String vcDis = request.getParameter("vcDis");
		String htCode = request.getParameter("htCode");
		String vcType = request.getParameter("vcType");
		String vcWidth = request.getParameter("vcWidth");
		String bookNo = request.getParameter("bookNo");
		String brand = request.getParameter("brand");
		if(StringUtils.isNotEmpty(vcFactoryCode)){
			paramap.put("vcFactoryCode", vcFactoryCode);
			request.setAttribute("vcFactoryCode", vcFactoryCode);
		}
		if(StringUtils.isNotEmpty(vcBefModel)){
			paramap.put("vcBefModel", vcBefModel);
			request.setAttribute("vcBefModel", vcBefModel);
		}
		if(StringUtils.isNotEmpty(vcDis)){
			paramap.put("vcDis", vcDis);
			request.setAttribute("vcDis", vcDis);
		}
		if(StringUtils.isNotEmpty(htCode)){
			paramap.put("htCode", htCode);
			request.setAttribute("htCode", htCode);
		}
		if(StringUtils.isNotEmpty(vcType)){
			paramap.put("vcType", vcType);
			request.setAttribute("vcType", vcType);
		}
		if(StringUtils.isNotEmpty(vcWidth)){
			paramap.put("vcWidth", vcWidth);
			request.setAttribute("vcWidth", vcWidth);
		}
		if(StringUtils.isNotEmpty(bookNo)){
			paramap.put("bookNo", bookNo);
			request.setAttribute("bookNo", bookNo);
		}
		if(StringUtils.isNotEmpty(brand)){
			paramap.put("brand", brand);
			request.setAttribute("brand", brand);
		}
		return paramap;
	}
	public String getFabricExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String getHTFabricExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME_HT);
	}
	
	public String getDHJFabricExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME_DHJ);
	}
	
	public String exportFabricData(){
		List<Fabric> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = fabricService.getFabricByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		Map<String,Supplier> map = new HashMap<String,Supplier>();
		try {
			List<Supplier> ss = supplierService.getAll();
			for(Supplier s : ss){
				map.put(s.getVcFactoryCode(), s);
			}
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Fabric");
			String titleStr [] = {"工厂代码","供应商名称","类型", "原厂型号", "幅宽", "卷长","起订量", "最小分格", "成份",
					"密度","回位","定高宽", "收缩","耐磨度","阻燃","环保","防污","色牢度","洗涤","用途","原始进价",
					"进价单位","进价货币", "工程系数", "分销系数","工程运费","零售运费","采购折扣","国内最低运费",
					"香港最低运费","内地面价","香港面价","零售面价", "状态","备注1","备注2","备注3"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(Fabric fabric : list) {
				Supplier supplier = map.get(fabric.getVcFactoryCode());
				if(supplier==null){
					continue;
				}
				String factoryName = supplier.getVcFactoryName();
				float homeLowTransportCost = supplier.getHomeLowTransportCost();
				float hkLowTransportCost = supplier.getHkLowTransportCost();
				String homePrice = PriceUtil.getCommonFacePrice(fabric.getVcOldPrice(), fabric.getVcPurDis(), getExchangeRate("1",fabric.getVcPriceCur()), fabric.getVcProRatio())+" RMB";
				String hkPrice  = PriceUtil.getCommonFacePrice(fabric.getVcOldPrice(), fabric.getVcPurDis(), getExchangeRate("2",fabric.getVcPriceCur()), fabric.getVcRetailRatio())+" HKD";
				String lsPrice = PriceUtil.getRetailFacePrice(fabric.getVcOldPrice(), fabric.getVcPurDis(), getExchangeRate("5",fabric.getVcPriceCur()), fabric.getVcProFre(), supplier.getRetailCoefficient())+" RMB";
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {fabric.getVcFactoryCode(),factoryName,fabric.getVcType(), fabric.getVcBefModel(),
						fabric.getVcWidth(),  fabric.getVcPieceLength(),fabric.getVcMinNum(),fabric.getVcMinLattice(),
						fabric.getVcComposition(), fabric.getVcDensity(),fabric.getVcRepeat(),fabric.getVcFixWidthHight(),
						fabric.getVcShrink(),fabric.getVcDr(),fabric.getVcFr(),fabric.getVcEco(),fabric.getVcAntifouling(),fabric.getVcLightFas(),
						fabric.getVcClean(),fabric.getVcApp(),fabric.getVcOldPrice(),fabric.getVcMeasure(),fabric.getVcPriceCur(),
						fabric.getVcProRatio(),fabric.getVcRetailRatio(),fabric.getVcProFre(),fabric.getVcRetFre(),fabric.getVcPurDis(),homeLowTransportCost,
						hkLowTransportCost,homePrice,hkPrice,lsPrice,fabric.getVcDis(),fabric.getVcRemark1(),fabric.getVcRemark2(),fabric.getVcRemark3()};
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
	
	public String exportHTFabricData(){
		List<Fabric> list = null;
		Map<String,Object> paramap = handleRequestParameter();
		list = fabricService.getHTFabricByPara(paramap);
		List<Fabric> fabrics = this.HtTofabrics(list);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		Map<String,Supplier> map = new HashMap<String,Supplier>();
		try {
			List<Supplier> ss = supplierService.getAll();
			for(Supplier s : ss){
				map.put(s.getVcFactoryCode(), s);
			}
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Fabric");
			String titleStr [] = {"工厂代码","供应商名称", "原厂型号","色号", "HT型号","书号","幅宽", "原始进价", "进价单位","进价货币", "内地价格", "大陆二次出价", "香港价格", "香港二次出价", "工程运费","零售运费","国内最低运费","香港最低运费","采购折扣", "状态","成分","备注1"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(Fabric fabric : fabrics) {
				Supplier supplier = map.get(fabric.getVcFactoryCode());
				if(supplier==null){
					continue;
				}
				String factoryName = supplier.getVcFactoryName();
				float homeLowTransportCost = supplier.getHomeLowTransportCost();
				float hkLowTransportCost = supplier.getHkLowTransportCost();
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {fabric.getVcFactoryCode(),factoryName,fabric.getVcBefModel(), fabric.getColorCode(),fabric.getHtCode(),fabric.getBookNo(),fabric.getVcWidth(), fabric.getVcOldPrice(),fabric.getVcMeasure(),fabric.getVcPriceCur(),
						fabric.getInnerPrice(), 
						fabric.getInnerSecPrice(),  
						fabric.getHkPrice(),
						fabric.getHkSecPrice(),
						fabric.getVcProFre(),fabric.getVcRetFre(),fabric.getVcPurDis(),homeLowTransportCost,hkLowTransportCost,
						fabric.getVcDis(),fabric.getVcComposition(),fabric.getVcRemark1()};
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
	private List<Fabric> HtTofabrics(List<Fabric> htfbs){
		logger.info("htfbs.size()======="+htfbs.size());
		List<CurrencyConversion> conlist = currencyConversionService.getAll();
		Map<String,Float> conMap = new HashMap<String,Float>();
		conMap.put("RMB_RMB", new Float(1));
		conMap.put("HKD_HKD", new Float(1));
		for(CurrencyConversion con : conlist){
			conMap.put(con.getVcCurrency()+"_"+con.getVcObjCurrency(), con.getVcExchangeRate());
		}
		List<Long> refidList = new ArrayList<Long>();
		for(Fabric f : htfbs){
			Long fcode = f.getRefid();
			if(fcode==null||fcode==0){
				logger.info("没有找到refid的Ht型号===="+f.getVcFactoryCode()+"--"+f.getVcBefModel()+"--"+f.getHtCode());
				logger.info("fcode==null||fcode==0");
				continue;
			}
			refidList.add(fcode);
		}
		logger.info("refidList.size()======="+refidList.size());
		String testhql = "from Fabric WHERE isHtCode ='0' and id in (";
		String refdis = "";
		for(Long id : refidList){
			refdis += id.longValue()+",";
		}
		refdis = refdis.substring(0, refdis.length()-1);
		testhql += refdis+")";
		logger.info(testhql);
		List<Fabric> fbs = fabricService.getFabricByHql(testhql);
		Map<Long,Fabric> map = new HashMap<Long,Fabric>();
		for(Fabric f : fbs){
			map.put(f.getId(), f);
		}
		logger.info("根据refid找到的远厂型号——fbs.size()======="+fbs.size());
		for(int i=0;i<htfbs.size();i++){
			Fabric fb = htfbs.get(i);
			if(fb==null){
				logger.info("fb========null---"+i);
				continue;
			}
			Fabric f = map.get(fb.getRefid());
			if(f==null){
				logger.info("f========null---"+i);
				continue;
			}
			fb.setDhjCost(f.getDhjCost());
			fb.setDhjHKRate(f.getDhjHKRate());
			fb.setDhjHKTransCost(f.getDhjHKTransCost());
			fb.setDhjInlandRate(f.getDhjInlandRate());
			fb.setDhjInlandTransCost(f.getDhjInlandTransCost());
			fb.setDhjWidth(f.getDhjWidth());
			fb.setVcWidth(f.getVcWidth());
			fb.setVcOldPrice(f.getVcOldPrice());
			fb.setVcMeasure(f.getVcMeasure());
			fb.setVcPriceCur(f.getVcPriceCur());
			fb.setVcProFre(f.getVcProFre());
			fb.setVcRetFre(f.getVcRetFre());
			fb.setVcPurDis(f.getVcPurDis());
			fb.setVcComposition(f.getVcComposition());
			fb.setVcRemark1(f.getVcRemark1());
			fb.setFactoryName(f.getFactoryName());
			String innKey = f.getVcPriceCur().toUpperCase()+"_RMB";
			float innerPrice = getPrice(f,"1",conMap.get(innKey),conMap.get(innKey));
			String hkKey = f.getVcPriceCur().toUpperCase()+"_HKD";
			float hkPrice = getPrice(f,"2",conMap.get(hkKey),conMap.get(innKey));
			float innerSecondPrice = innerPrice;
			float hkSecondPrice = hkPrice;
			if(StringUtils.isNotBlank(fb.getBrand())){
				innerSecondPrice = PriceUtil.getSecondInlandFacePrice(innerPrice, fb.getBrand(), fb.getInlandRaiseRate(), fb.getInlandDownRate(), fb.getInlandRefitRate());
				hkSecondPrice = PriceUtil.getSecondHKFacePrice(hkPrice, fb.getBrand(), fb.getHkRaiseRate(), fb.getHkDownRate(), fb.getHkRefitRate());
			}
			fb.setInnerPrice(innerPrice+" RMB");
			fb.setHkPrice(hkPrice+" HKD");
			fb.setInnerSecPrice(innerSecondPrice+" RMB");
			fb.setHkSecPrice(hkSecondPrice+" HKD");
			
			
		}
		return htfbs;
	}
	private float getPrice(Fabric f,String quoteFormate,Float exchangeRate,Float other2mrb){
		float vcExchangeRate = exchangeRate==null?0F:exchangeRate.floatValue();
		float vcOther2mrb = other2mrb==null?0F:other2mrb.floatValue();
		float rate = 1;//工程系数/分销系数/大货价内地系数/大货价香港系数
		float price = f.getVcOldPrice();//原始进价/大货价进价
		if(price<1&&f.getDhjCost()>0){
			price = f.getDhjCost();
		}
		//确定系数
		if ("1".equals(quoteFormate)) {//工程报价
			rate = f.getVcProRatio();//工程系数
			if(f.getVcOldPrice()<1&&f.getDhjCost()>0){
				rate = f.getDhjInlandRate();
			}
			
		}else if("2".equals(quoteFormate)){
			rate = f.getVcRetailRatio();//分销系数
			if(f.getVcOldPrice()<1&&f.getDhjCost()>0){
				rate = f.getDhjHKRate();
			}
		}
		float origPrice = 0F;
		if("2".equals(quoteFormate) && "0".equals(f.getImportFactory())){//香港报价并且是国产厂
			origPrice = PriceUtil.getHKDomesticFacePrice(f.getVcOldPrice(), f.getVcPurDis(), vcOther2mrb, vcExchangeRate);
		}else{
			origPrice = PriceUtil.getCommonFacePrice(price, f.getVcPurDis(), vcExchangeRate, rate);
		}
		return origPrice;
	}
	public String listForQuote(){
		String isHtCode = request.getParameter("isHtCode");
		if(StringUtils.isBlank(isHtCode)){
			isHtCode = "0";
		}
		request.setAttribute("isHtCode", isHtCode);
		String selectedFabricHidden = request.getParameter("selectedFabricHidden");
		List<JSONObject> selectedFabric = new ArrayList<JSONObject>();
		if(StringUtils.isNotBlank(selectedFabricHidden)){
			request.setAttribute("selectedFabricHidden", selectedFabricHidden);
			String[] a = selectedFabricHidden.split(";");
			for(String fb :a){
				JSONObject json = new JSONObject();
				json.put("id",StringUtils.substringBefore(fb, "="));
				json.put("fabricCode", StringUtils.substringAfter(fb, "="));
				selectedFabric.add(json);
			}
		}
		request.setAttribute("selectedFabric", selectedFabric);
		Page<Fabric> page = new Page<Fabric>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		//paramap.put("vcDis", "启用");
		if("1".equals(isHtCode)){
			page = fabricService.findHTForPage(page, paramap);
		}else{
			page = fabricService.findForPage(page, paramap);
		}
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/fabric_listForQuote.Q?";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "listForQuote";
	}
	public String getFabricForQuote(){
		String quoteFormate = request.getParameter("quoteFormate");
		request.setAttribute("quoteFormate", quoteFormate);
		listForQuote();
		return "fabricForQuote";
	}
	
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

	public Fabric getFabric() {
		return fabric;
	}

	public void setFabric(Fabric fabric) {
		this.fabric = fabric;
	}

	
}
