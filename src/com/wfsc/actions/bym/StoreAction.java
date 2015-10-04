package com.wfsc.actions.bym;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
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
import com.wfsc.common.bo.bym.Store;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IOrderService;
import com.wfsc.services.bym.service.IStoreFabricService;
import com.wfsc.services.bym.service.IStoreService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.ExcelUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("StoreAction")
@Scope("prototype")
public class StoreAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "库存模板.xls";

	@Resource(name = "storeService")
	private IStoreService storeService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "orderService")
	private IOrderService orderService;
	@Resource(name = "storeFabricService")
	private IStoreFabricService storeFabricService;
	
	private Store store;

	public String manager(){
		this.setTopMenu();
		Map<String,String> arerMap = new HashMap<String,String>();
		arerMap.put("GZ", "广州仓库");
		arerMap.put("SZ", "深圳仓库");
		arerMap.put("SH", "上海仓库");
		arerMap.put("BJ", "北京仓库");
		arerMap.put("HK", "香港仓库");
		Admin user = this.getCurrentAdminUser();
		boolean isAdmin = securityService.isAbleRole(user.getUsername(), "超级管理员");
		boolean isStorer = securityService.isAbleRole(user.getUsername(), "仓库管理员");
		boolean isPurManager = securityService.isAbleRole(user.getUsername(), "采购经理");
		Map<String,Object> paramap = new HashMap<String,Object>();
		if(!isAdmin&&!isStorer&&!isPurManager){
			paramap.put("storeName", arerMap.get(user.getArea()));
			paramap.put("userName", user.getUsername());
		}
		List<Store> stores = storeService.getStoreByPara(paramap);
		request.setAttribute("stores", stores);
		return "manager";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			store = storeService.getStoreById(Long.valueOf(id));
		}else{
			store = new Store();
		}
		request.setAttribute("isView", "0");
		return "input";
	}
	
	public String detail() {
		String id = request.getParameter("id");
		store = storeService.getStoreById(Long.valueOf(id));
		request.setAttribute("isView", "1");
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
			storeService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		storeService.saveOrUpdateEntity(store);
		return "ok";
	}
	
	public void transferAll() throws IOException{
		String storeId = request.getParameter("storeId");
		String oldStoreId = request.getParameter("oldStoreId");
		if (!"".equals(oldStoreId) && oldStoreId != null && !"".equals(storeId)
				&& storeId != null) {
			Store s = storeService.getStoreById(new Long(storeId));
			Store os = storeService.getStoreById(new Long(oldStoreId));
			List<StoreFabric> sfs = storeFabricService.getStoreFabricByStoreId(new Long(oldStoreId));
			for (StoreFabric sf : sfs) {
				sf.setStoreId(new Long(storeId));
				storeFabricService.saveOrUpdateEntity(sf);
				saveSystemLog(sf.getVcModelNum()+"_"+sf.getVcFactoryCode(), os.getStoreName() + "产品全部移向" + s.getStoreName());
			}
			
		}
		response.getWriter().write("sucess");
	}
	
	public void queryAllStore(){
		response.setContentType("text/html;charset=utf-8");
		List<Store> stores = storeService.getAll();
		String selectHtml = "<select style=\"width:152px\" id=\"storeSelect\">";
		for(Store s : stores){
			selectHtml += "<option value="+s.getId() +">"+s.getStoreName()+"</option>";
		}
		selectHtml += "</select>";
		
		try {
			response.getWriter().write(selectHtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toImport(){
		String storeId = request.getParameter("storeId");
		request.setAttribute("storeId", storeId);
		return "toImport";
	}
	
	public String importData(){
		String storeId = request.getParameter("storeId");
		request.setAttribute("storeId", storeId);
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
		mustTitles.add("单号");
		mustTitles.add("产品编号");
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
		List<StoreFabric> oldrecord = storeFabricService.getStoreFabricByStoreId(new Long(storeId));
		Map<String,StoreFabric> map = new HashMap<String,StoreFabric>();
		if(oldrecord!=null){
			for(StoreFabric s : oldrecord){
				map.put(s.getOrderNo()+"_"+s.getFabricNo(), s);
			}
			
		}
		List<StoreFabric> ss = new ArrayList<StoreFabric>();
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
			String orderNo = ExcelUtil.getCellValueAsString(row.getCell(0),"string");
			String fabricNo = ExcelUtil.getCellValueAsString(row.getCell(1),"string");
			StoreFabric s = new StoreFabric();
			//根据工厂代码判断是否是更新
			if(map.get(orderNo+"_"+fabricNo)!=null){
				s = map.get(orderNo+"_"+fabricNo);
			}
			for (int j = 0; j < colNums; j++) {
				Cell cell = row.getCell(j);
				title = titles.get(j);
				prefix = "第" + (i + 1) + "行,第" + (j + 1) + "列 " + title;
				if (StringUtils.equals(title, "单号")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setOrderNo(value);
					continue;
				}
				if (StringUtils.equals(title, "产品编号")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setFabricNo(value);
					continue;
				}
				if (StringUtils.equals(title, "数量")) {//到货数量和剩余数量
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcSubLay(value);
					s.setSurplus(value);
					continue;
				}
				if (StringUtils.equals(title, "单位")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setUnit(value);
					continue;
				}
				if (StringUtils.equals(title, "入库日期")) {
					try {
						value = ExcelUtil.getCellValueAsString(cell,"date");
						Date date = null;
						if(StringUtils.isEmpty(value)){
							s.setInStoreDate(new Date());
						}else{
							date = DateUtil.getDate(value);
							s.setInStoreDate(date);
						}
					} catch (ParseException e) {
						errorList.add(prefix + "日期格式错误");
					}
					continue;
				}
				if (StringUtils.equals(title, "经手采购")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcAssignAutor(value);
					continue;
				}
				if (StringUtils.equals(title, "出库日期")) {
					continue;
				}
				if (StringUtils.equals(title, "位置")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcAddr(value);
					continue;
				}
				if (StringUtils.equals(title, "备注")) {
					value = ExcelUtil.getCellValueAsString(cell,"string");
					s.setVcRmk(value);
					continue;
				}
			}
			ss.add(s);
		}
		try {
			//仅在校验无误的情况下保存
			if(CollectionUtils.isEmpty(errorList)){
				storeFabricService.saveOrUpdateAll(ss);
				request.setAttribute("successMsg", "数据导入成功!");
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			errorList.add("导入失败！");
		}
		request.setAttribute("errorMsg",errorList);
		return "toImport";
	}
	
	public String getStoreExcelModel(){
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	
}
