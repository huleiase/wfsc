package com.wfsc.actions.bym;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.bym.Store;
import com.wfsc.common.bo.bym.StoreFabric;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.bym.service.IStoreFabricService;
import com.wfsc.services.bym.service.IStoreService;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("StoreFabricAction")
@Scope("prototype")
public class StoreFabricAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "仓库模板.xls";

	@Resource(name = "storeService")
	private IStoreService storeService;
	@Resource(name = "securityService")
	private ISecurityService securityService;
	@Resource(name = "storeFabricService")
	private IStoreFabricService storeFabricService;
	
	private StoreFabric storeFabric;
	@Autowired
	private ISystemLogService systemLogService;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<StoreFabric> page = new Page<StoreFabric>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		Admin admin = this.getCurrentAdminUser();
		boolean isSale = securityService.isAbleRole(admin.getUsername(), "销售");
		boolean isSaleManager = securityService.isAbleRole(admin.getUsername(), "销售经理");
		boolean isquoteer = securityService.isAbleRole(admin.getUsername(), "报价员");
		boolean isAreamgt = securityService.isAbleRole(admin.getUsername(), "区域经理");
		
		boolean isLogistics = securityService.isAbleRole(admin.getUsername(), "物流专员");
		boolean isAreaCaiwu = securityService.isAbleRole(admin.getUsername(), "分区财务经理");
		boolean lessPermission =false;
		if(isLogistics||isAreaCaiwu){
			lessPermission = true;
		}
		request.setAttribute("lessPermission", lessPermission);
		if(isSale&&!isSaleManager){
			paramap.put("userName", admin.getUsername());
		}
		page = storeFabricService.findForPage(page, paramap);
		List<StoreFabric> sfs = page.getData();
		if(sfs!=null){
			for(StoreFabric sf : sfs){
				Attachment attr = commonService.getOnlyAttachmentByKey(sf.getVcFactoryCode()+"_"+sf.getVcModelNum());
				if(attr!=null){
					sf.setFileName(attr.getAttachPath());
				}
				/*if("1".equals(sf.getIsHidden())&&StringUtils.isNotBlank(sf.getReplaceId())){
					sf.setDisplayNum(sf.getReplaceId());
				}*/
			}
		}
		String permission = request.getParameter("permission");
		if(isSale||isquoteer||isSaleManager||isAreamgt){
			permission = "0";
		}
		request.setAttribute("permission", permission);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/storeFabric_list.Q?permission="+permission;
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		if("0".equals(permission)){
			return "lesslist";
		}
		return "list";
	}
	
	public String input() {
		Admin admin = this.getCurrentAdminUser();
		boolean isLogistics = securityService.isAbleRole(admin.getUsername(), "物流专员");
		boolean isAreaCaiwu = securityService.isAbleRole(admin.getUsername(), "分区财务经理");
		String permission = "1";
		if(isLogistics||isAreaCaiwu){
			permission = "0";
		}
		request.setAttribute("permission", permission);
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			storeFabric = storeFabricService.getStoreFabricById(Long.valueOf(id));
		}else{
			storeFabric = new StoreFabric();
		}
		request.setAttribute("isView", false);
		return "input";
	}
	
	public String detail() {
		Admin admin = this.getCurrentAdminUser();
		boolean isLogistics = securityService.isAbleRole(admin.getUsername(), "物流专员");
		boolean isAreaCaiwu = securityService.isAbleRole(admin.getUsername(), "分区财务经理");
		String permission = "1";
		if(isLogistics||isAreaCaiwu){
			permission = "0";
		}
		request.setAttribute("permission", permission);
		String id = request.getParameter("id");
		storeFabric = storeFabricService.getStoreFabricById(Long.valueOf(id));
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
			storeFabricService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String storeOver(){
		String id = request.getParameter("id");
		try {
			StoreFabric sfdb = storeFabricService.getStoreFabricById(Long.valueOf(id));
			sfdb.setIsStoreOver("1");
			storeFabricService.saveOrUpdateEntity(sfdb);
			response.getWriter().write(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String save(){
		StoreFabric sfdb = storeFabricService.getStoreFabricById(storeFabric.getId());
		sfdb.setFabricNo(storeFabric.getFabricNo());
		sfdb.setVcQuoteNum(storeFabric.getVcQuoteNum());
		sfdb.setVcRealityAog(storeFabric.getVcRealityAog());
		sfdb.setVcRmk(storeFabric.getVcRmk());
		sfdb.setVcColorNum(storeFabric.getVcColorNum());
		sfdb.setSurplus(storeFabric.getSurplus());
		storeFabricService.saveOrUpdateEntity(sfdb);
		request.setAttribute("storeId", sfdb.getStoreId());
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.storeFabricLog, curAdminName+"修改了库存信息"+sfdb.getVcFactoryCode()+" "+sfdb.getVcModelNum());
		return "ok";
	}
	public String toImport(){
		String befModel = request.getParameter("befModel");
		String factoryCode = request.getParameter("factoryCode");
		request.setAttribute("befModel", befModel);
		request.setAttribute("factoryCode", factoryCode);
		return "toImport";
	}
	 public String importFile(){
		 String factoryCode = request.getParameter("factoryCode");
		 String befModle = request.getParameter("befModel");
		 request.setAttribute("factoryCode", factoryCode);
		 request.setAttribute("befModle", befModle);
		 String linkCode = "";
		 if(StringUtils.isNotBlank(factoryCode)&&StringUtils.isNotBlank(befModle)){
			linkCode = factoryCode+"_"+befModle;
		}
		 return super.importFile(linkCode);
	 }
	 public String downloadFile(){
		 String factoryCode = request.getParameter("factoryCode");
		 String befModle = request.getParameter("befModel");
		 String linkCode = "";
		 if(StringUtils.isNotBlank(factoryCode)&&StringUtils.isNotBlank(befModle)){
			linkCode = factoryCode+"_"+befModle;
		}
		 return super.downloadFile(linkCode);
	 }
	
	public String exportStoreFabricData(){
		List<StoreFabric> list = null;
		Map<String,Object> paramap = this.handleRequestParameter();
		list = storeFabricService.getStoreFabricByPara(paramap);
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=data.xls");
		try {
			outputStream = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Store");
			String titleStr [] = {"项目名称","报价单号", "订单号", "供应商", "支付方式","报价型号", "原厂型号", "色号", "幅宽","订货量","实订量", "分段铺量",
					"到货数量","实际到货", "剩余数量", "单位", "经手人","入库日期", "出库日期", "位置", "完结状态","备注1","订单日期", "发货地址",
					"出货经手人","快递单号", "快递公司", "到货公司", "订单确认","货期要求", "备注2", "到货地址", "特殊要求","要求到货日期"};
			HSSFRow thRow = sheet.createRow(0);//表头行
			for(int i = 0; i < titleStr.length; i++) {
				HSSFCell thCell = thRow.createCell( i);
				thCell.setCellValue(titleStr[i]);
			}
			
			int i = 1;
			for(StoreFabric sf : list) {
				HSSFRow cRow = sheet.createRow(i);
				Object values[] = {sf.getVcProject(),sf.getQuoteNum(),sf.getOrderNo(),sf.getSupplie(),sf.getPayment(),sf.getDisplayNum(),sf.getVcModelNum()
						,sf.getVcColorNum(),sf.getVcWidth()+sf.getVcWidthUnit()==null?"cm":sf.getVcWidthUnit(),sf.getOrderQuantity(),sf.getVcQuoteNum(),sf.getVcSubLay()
						,sf.getArrivalNum(),sf.getVcRealityAog(),sf.getSurplus(),sf.getUnit(),sf.getVcAssignAutor(),sf.getInStoreDate(),sf.getOutStoreDate()
						,sf.getVcAddr(),"1".equals(sf.getIsStoreOver())?"已完结":"未完结",sf.getVcRmk(),sf.getOrderDate(),sf.getShipAddress()
						,sf.getShipPerson(),sf.getExpressNumber(),sf.getExpressCompany(),sf.getArrivalCompany(),"1".equals(sf.getIsOrderConfirm())?"已确认":"未确认"
						,sf.getDeliveryRequirements(),sf.getVcRmk2(),sf.getArrivalAddress(),sf.getSpecialReq(),sf.getArrivalDate()};
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
		saveSystemLog(LogModule.storeFabricLog, curAdminName+"导出了库存信息");
		return null;
	}
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String vcProject = request.getParameter("vcProject");
		
		String vcModelNum = request.getParameter("vcModelNum");
		String orderNo = request.getParameter("orderNo");
		String surplus = request.getParameter("surplus");
		
		String storeId = request.getParameter("storeId");
		
		String htCode = request.getParameter("htCode");
		
		String comeCode = request.getParameter("comeCode");
		
		String isStoreOver = request.getParameter("isStoreOver");
		
		if(StringUtils.isNotEmpty(startTime)){
			paramap.put("startTime", startTime);
			request.setAttribute("startTime", startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			paramap.put("endTime", endTime);
			request.setAttribute("endTime", endTime);
		}
		if(StringUtils.isNotEmpty(vcProject)){
			paramap.put("vcProject", vcProject);
			request.setAttribute("vcProject", vcProject);
		}
		if(StringUtils.isNotEmpty(vcModelNum)){
			paramap.put("vcModelNum", vcModelNum);
			request.setAttribute("vcModelNum", vcModelNum);
		}
		if(StringUtils.isNotEmpty(orderNo)){
			paramap.put("orderNo", orderNo);
			request.setAttribute("orderNo", orderNo);
		}
		if(StringUtils.isNotEmpty(surplus)){
			paramap.put("surplus", surplus);
			request.setAttribute("surplus", surplus);
		}
		
		if(StringUtils.isNotEmpty(storeId)){
			paramap.put("storeId", storeId);
			request.setAttribute("storeId", storeId);
		}
		
		if(StringUtils.isNotEmpty(htCode)){
			paramap.put("htCode", htCode);
			request.setAttribute("htCode", htCode);
		}
		
		if(StringUtils.isNotEmpty(comeCode)){
			paramap.put("comeCode", comeCode);
			request.setAttribute("comeCode", comeCode);
		}
		
		if(StringUtils.isNotEmpty(isStoreOver)){
			paramap.put("isStoreOver", isStoreOver);
			request.setAttribute("isStoreOver", isStoreOver);
		}
		
		
		return paramap;
	}
	public String getStoreFabricExcelModel(){
		
		return super.getExcelModel(MODEL_EXCEL_NAME);
	}
	
	public String transfer(){
		String id = request.getParameter("id");
		storeFabric = this.storeFabricService.getStoreFabricById(Long.valueOf(id));
		String surplus = storeFabric.getSurplus();
		List<String> surpluslist = new ArrayList<String>();
		String[] suparr = surplus.split("\\+");
		for(String s : suparr){
			surpluslist.add(s);
		}
		request.setAttribute("surpluslist", surpluslist);
		request.setAttribute("surplus", surplus);
		List<Store> liststore = this.storeService.getAll();
		request.setAttribute("liststore", liststore);
		String shipments = request.getParameter("shipments");
		if("1".equals(shipments)){
			return "shipments";
		}
		return "transfer";
	}
	
	public String savetransfer(){
		String newStore = request.getParameter("newStore");
		if("".equals(newStore)){
			newStore = "0";
		}
		String fenduan = request.getParameter("fenduan");//选择的分段
		
		String id = request.getParameter("id");
		storeFabric = this.storeFabricService.getStoreFabricById(Long.valueOf(id));
		String transferNum = request.getParameter("transferNum");
		int tn = Integer.valueOf(transferNum).intValue();//转移数量
		String vcReason = request.getParameter("vcReason");
		String surplus = storeFabric.getSurplus();//剩余数量
		String newSurplus =  "";
		int newFenduan = (Integer.valueOf(fenduan).intValue()-tn);
		if(newFenduan==0){
			if(surplus.indexOf("+")<0){
				newSurplus = "0";
			}else{
				int index = surplus.indexOf(fenduan);
				if(0==index){
					String subStr = surplus.substring(index, index+fenduan.length()+1);
					newSurplus =  StringUtils.replaceOnce(surplus, subStr, "");
				}else{
					String subStr = surplus.substring(index-1, index+fenduan.length());
					newSurplus =  StringUtils.replaceOnce(surplus, subStr, "");
				}
			}
		}else{
			newSurplus =  StringUtils.replaceOnce(surplus, fenduan, newFenduan+"");
		}
		storeFabric.setSurplus(newSurplus);
		String vcFactoryCode = storeFabric.getVcFactoryCode();
		String vcModelNum = storeFabric.getVcModelNum();
		Store ns = this.storeService.getStoreById(Long.valueOf(newStore));
		String nsn = "";
		String osn = storeFabric.getStoreName();
		if(ns!=null){
			nsn = ns.getStoreName();
		}
		StoreFabric sf = this.storeFabricService.getStoreFabric(new Long(newStore), vcFactoryCode, vcModelNum);
		if(sf!=null){
			String oldvcSubLay = sf.getVcSubLay();
			String oldsurplus = sf.getSurplus();
			String newvcSubLay =  "";
			String newsurplus = "";
			if(oldvcSubLay==null || "".equals(oldvcSubLay)){
				newvcSubLay = transferNum;
			}else{
				newvcSubLay = oldvcSubLay+"+"+transferNum;
			}
			if(oldsurplus==null || "".equals(oldsurplus)){
				newsurplus = transferNum;
			}else{
				newsurplus = oldsurplus+"+"+transferNum;
			}
			
			sf.setVcSubLay(newvcSubLay);
			sf.setSurplus(newsurplus);
			this.storeFabricService.saveOrUpdateEntity(sf);
		}else{
			sf = new StoreFabric();
			sf.setStoreId(Long.valueOf(newStore));
			sf.setOrderId(storeFabric.getOrderId());
			sf.setOrderNo(storeFabric.getOrderNo());
			sf.setFabricNo(storeFabric.getFabricNo());
			sf.setSupplie(storeFabric.getSupplie());
			sf.setPayment(storeFabric.getPayment());
			sf.setInStoreDate(new Date());
			sf.setVcProject(storeFabric.getVcProject());
			sf.setVcModelNum(storeFabric.getVcModelNum());
			sf.setVcColorNum(storeFabric.getVcColorNum());
			sf.setVcFactoryCode(storeFabric.getVcFactoryCode());
			sf.setVcRmk(storeFabric.getVcRmk());
			sf.setVcQuoteNum(Float.valueOf(transferNum));
			sf.setVcAssignAutor(storeFabric.getVcAssignAutor());
			sf.setVcSubLay(transferNum);
			sf.setSurplus(transferNum);
			sf.setIsHtCode(storeFabric.getIsHtCode());
			sf.setHtCode(storeFabric.getHtCode());
			sf.setVcSalesman(storeFabric.getVcSalesman());
			sf.setVcSalesman1(storeFabric.getVcSalesman1());
			sf.setVcSalesman2(storeFabric.getVcSalesman2());
			sf.setVcSalesman3(storeFabric.getVcSalesman3());
			sf.setVcSalesman4(storeFabric.getVcSalesman4());
			sf.setStoreName(nsn);
			this.storeFabricService.saveOrUpdateEntity(sf);
		}
		
		this.storeFabricService.saveOrUpdateEntity(storeFabric);
		this.saveSystemLog( vcModelNum+"_"+vcFactoryCode, "从"+osn+"转移型号:"+vcModelNum+",数量:"+transferNum+"到"+nsn+",转移原因为:" + vcReason);
		request.setAttribute("success", "1");
		return "transfer";
	}
	public String saveshipments(){
		String fenduan = request.getParameter("fenduan");//选择的分段
		String shipmentsNo = request.getParameter("shipmentsNo");//发货单号
		String shipmentsDate = request.getParameter("shipmentsDate");
		String id = request.getParameter("id");
		storeFabric = this.storeFabricService.getStoreFabricById(Long.valueOf(id));
		String transferNum = request.getParameter("transferNum");
		int tn = Integer.valueOf(transferNum).intValue();//转移数量
		String vcReason = request.getParameter("vcReason");
		String surplus = storeFabric.getSurplus();//剩余数量
		String newSurplus =  "";
		int newFenduan = (Integer.valueOf(fenduan).intValue()-tn);
		if(newFenduan==0){
			if(surplus.indexOf("+")<0){
				newSurplus = "0";
			}else{
				int index = surplus.indexOf(fenduan);
				if(0==index){
					String subStr = surplus.substring(index, index+fenduan.length()+1);
					newSurplus =  StringUtils.replaceOnce(surplus, subStr, "");
				}else{
					String subStr = surplus.substring(index-1, index+fenduan.length());
					newSurplus =  StringUtils.replaceOnce(surplus, subStr, "");
				}
			}
		}else{
			newSurplus =  StringUtils.replaceOnce(surplus, fenduan, newFenduan+"");
		}
		storeFabric.setSurplus(newSurplus);
		String vcFactoryCode = storeFabric.getVcFactoryCode();
		String vcModelNum = storeFabric.getVcModelNum();
		String osn = storeFabric.getStoreName();
		this.storeFabricService.saveOrUpdateEntity(storeFabric);
		this.saveSystemLog(vcModelNum+"_"+vcFactoryCode, "从"+osn+"出库型号:"+vcModelNum+",数量为:"+transferNum+"，发货单号为"+shipmentsNo+",发货日期为："+shipmentsDate+"，备注:" + vcReason);
		request.setAttribute("success", "1");
		return "shipments";
	}
	
	public String recordManager(){
		this.setTopMenu();
		recordList();
		return "recordManager";
	}
	
	@SuppressWarnings("unchecked")
	public String recordList(){
		String vcModelNum = request.getParameter("vcModelNum");
		String vcFactoryCode = request.getParameter("vcFactoryCode");
		Page<SystemLog> page = new Page<SystemLog>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramas = new HashMap<String,Object>();
		paramas.put("tiLevel", vcModelNum+"_"+vcFactoryCode);
		page = systemLogService.getSystemLog4Page(page, paramas);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/storeFabric_recordList.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "recordList";
	}
	

	public StoreFabric getStoreFabric() {
		return storeFabric;
	}

	public void setStoreFabric(StoreFabric storeFabric) {
		this.storeFabric = storeFabric;
	}

	
}
