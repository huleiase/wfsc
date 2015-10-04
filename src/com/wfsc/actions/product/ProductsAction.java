package com.wfsc.actions.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.tools.Version;
import com.base.util.FTPUtil;
import com.base.util.Page;
import com.exttool.MarkConfig;
import com.exttool.MarkTool;
import com.exttool.ScaleImage;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.ProductStock;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.city.ICityService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.services.stock.IStockService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("ProductsAction")
@Scope("prototype")
public class ProductsAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840812222299260353L;

	@Resource(name = "productsService")
	private IProductsService productsService;
	
	@Resource(name = "productCatService")
	private IProductCatService productCatService;
	
	@Autowired
	private IStockService stockService;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private ISystemLogService systemLogService;

	private Products products;
	
	private File[] myFile;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;

	public String manager(){
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Products> page = new Page<Products>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String name = request.getParameter("name");
		String recommend = request.getParameter("recommend");
		String prdCatCode = request.getParameter("prdCatCode");
		String prdCode = request.getParameter("prdCode");
		if(StringUtils.isNotEmpty(name)){
			paramap.put("name", name);
			request.setAttribute("name", name);
		}
		if(StringUtils.isNotEmpty(recommend)){
			paramap.put("recommend", Integer.valueOf(recommend));
			request.setAttribute("recommend", recommend);
		}
		if(StringUtils.isNotEmpty(prdCatCode)){
			paramap.put("prdCatCode", prdCatCode);
			request.setAttribute("prdCatCode", prdCatCode);
			request.setAttribute("prdCatName", request.getParameter("prdCatName"));
		}
		if(StringUtils.isNotEmpty(prdCode)){
			paramap.put("prdCode", prdCode);
			request.setAttribute("prdCode", prdCode);
		}
		
		page = productsService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/products_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("productslist", page.getData());
		return "list";
	}
	
	public String openPrdCatTree(){
		return "prdCatTree";
	}
	public String getPrdCatTreeData(){
		response.setCharacterEncoding("UTF-8");
		List<ProductCat> cats= productCatService.getAllProductCat();
		 JSONArray jsons = new JSONArray();
		 for(ProductCat type : cats){
			 JSONObject json = new JSONObject();
			 json.put("id", type.getId());
			 json.put("name", type.getName());
			 json.put("pId", type.getParentId());
			 json.put("code", type.getCode());
			 jsons.add(json);
		 }
		 try {
			response.getWriter().write(jsons.toString());
		} catch (IOException e) {
		}
		return null;
	}
	
	public String chekRecommend(){
		String recommend = request.getParameter("recommend");
		String prdCatCode = request.getParameter("prdCatCode");
		String result = "0";
		int s = productsService.getRecommendCount(Integer.valueOf(recommend), prdCatCode);
		//小图推荐，一级分类里面只能有6个小图推荐
		if("1".equals(recommend)){
			if(s>=6){
				result = "1";
			}
		}else if("2".equals(recommend)){//大图推荐，一级分类里面只能有1个大图推荐
			if(s>=1){
				result = "2";
			}
		}
		 try {
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}



	public String productInput() {
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			products = new Products();
		}else{
			products = productsService.getById(Long.valueOf(id));
			String p1 = products.getPicUrl1();
			if(StringUtils.isNotEmpty(p1)){
				products.setPicUrl1(p1.substring(p1.lastIndexOf("/")+1));
			}
			String p2 = products.getPicUrl2();
			if(StringUtils.isNotEmpty(p2)){
				products.setPicUrl2(p2.substring(p2.lastIndexOf("/")+1));
			}
			String p3 = products.getPicUrl3();
			if(StringUtils.isNotEmpty(p3)){
				products.setPicUrl3(p3.substring(p3.lastIndexOf("/")+1));
			}
			String p4 = products.getPicUrl4();
			if(StringUtils.isNotEmpty(p4)){
				products.setPicUrl4(p4.substring(p4.lastIndexOf("/")+1));
			}
			String p5 = products.getPicUrl5();
			if(StringUtils.isNotEmpty(p5)){
				products.setPicUrl5(p5.substring(p5.lastIndexOf("/")+1));
			}
			
		}
		return "add";
	}
	
	public String detail() {
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String id = request.getParameter("id");
		String prdCode = request.getParameter("prdCode");
		if(StringUtils.isEmpty(id)){
			if(StringUtils.isNotEmpty(prdCode)){
				products = productsService.getByPrdCode(prdCode);
				return "detail";
			}
			return "ok";
			
		}else{
			products = productsService.getById(Long.valueOf(id));
		}
		return "detail";
	}
	
	public String stock(){
		stocklist();
		return "stock";
	}
	
	@SuppressWarnings("unchecked")
	public String stocklist(){
		String code = request.getParameter("code");
		Products product = productsService.findByCode(code);
		if(product == null)
			throw new CupidRuntimeException("查看的商品不存在或已被删除");
		
		Page<ProductStock> page = new Page<ProductStock>();
		this.setPageParams(page);
		page = stockService.getProductStockByPrdCode(page, code);
		
		List<City> cities = cityService.queryCitiesForSupport();
		
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/products_stocklist.Q?code=" + code;
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("productName", product.getName());
		request.setAttribute("productCode", code);
		request.setAttribute("cities", cities);
		return "stockList";
	}
	
	public void editStock() throws IOException{
		String cityCode = request.getParameter("cityCode");
		String prdCode = request.getParameter("prdCode");
		String stock = request.getParameter("stock");
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(prdCode) || StringUtils.isEmpty(stock)){
			json.put("result", "failed");
		}else{
			stockService.saveOrUpdateProductStock(prdCode, Integer.parseInt(cityCode), Integer.parseInt(stock));
			json.put("result", "success");
		}
		
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
		
	}
	
	public String save() throws IOException{
		String productImg = this.getAbsoluteRootPath() + "/images/product";
		
		String opdescr = "";
		if(products.getId()==null||products.getId()<1){
			String prdCode = productsService.getMaxCodeByCatCode(products.getPrdCatCode());
			products.setPrdCode(prdCode);
			products.setCreateDate(new Date());
			products.setLastModifyDate(new Date());
			opdescr = "新增";
		}else{
			Products p = productsService.getById(products.getId());
			products.setLastModifyDate(new Date());
			products.setCreateDate(p.getCreateDate());
			products.setPicUrl1(p.getPicUrl1());
			products.setPicUrl2(p.getPicUrl2());
			products.setPicUrl3(p.getPicUrl3());
			products.setPicUrl4(p.getPicUrl4());
			products.setPicUrl5(p.getPicUrl5());
			opdescr = "编辑";
		}
		//取得需要上传的文件数组
        File[] files = getMyFile();
        String[] picUrl = request.getParameterValues("picUrl");
        if (files !=null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                //建立上传文件的输出流, getImageFileName()[i]
                FileOutputStream fos = null;
                FileInputStream fis = null;
                String filePath = productImg + "/" + getMyFileFileName()[i];
                System.out.println(productImg + "/" + getMyFileFileName()[i]);
                try {
					fos = new FileOutputStream(filePath);
					fis = new FileInputStream(files[i]);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				}finally{
					if(fis!=null){
							fos.close();
					}
					if(fis!=null){
							fis.close();
					}
				}
				ScaleImage sc = new ScaleImage();
				try {
					File picFile = new File(filePath);
					if(StringUtils.isNotEmpty(picUrl[0])&&i==0){
						sc.saveImageAsJpg(picFile.getPath(), picFile.getParent()+"/small/" + getMyFileFileName()[i], 350, 355);
						System.out.println("第一张缩略图生成完毕..");
					}
					/*else{
						sc.saveImageAsJpg(picFile.getPath(), picFile.getParent()+"/small/" + getMyFileFileName()[i], 99, 100);
					}*/
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            //让上传的文件与字段对应起来
            for(int j=0;j<picUrl.length;j++){
            	 if(StringUtils.isNotEmpty(picUrl[j])){
            		 String filepath = picUrl[j];
            		 String filename = "";
            		 if(filepath.indexOf("\\")>0){
            			 filepath = picUrl[j].replaceAll("\\\\", "/");
            			 filename = filepath.substring(filepath.lastIndexOf("/")+1);
            		 }else{
            			 filename = filepath;
            		 }
            		 String ftpFileName = products.getPrdCode()+"-"+j+"_"+filename;
            		 if(j==0){
          				products.setPicUrl1(ftpFileName);
          			}else if(j==1){
          				products.setPicUrl2(ftpFileName);
          			}else if(j==2){
          				products.setPicUrl3(ftpFileName);
          			}else if(j==3){
          				products.setPicUrl4(ftpFileName);
          			}else if(j==4){
          				products.setPicUrl5(ftpFileName);
          			}
            		 FileInputStream input = null;
       			    try {
       			    	String imgPath = productImg+"/"+filename;
       			    	input = new FileInputStream(new File(imgPath));  
       			    	boolean flag = FTPUtil.uploadFile(ftpFileName, input);
    			        System.out.println(j+"=======>"+flag);
       			    	if(j==0){
       			    		String smallImgPath = productImg+"/small/"+filename;
           			    	input = new FileInputStream(new File(smallImgPath));  
           			    //	String sub = filename.substring(filename.indexOf("."));
           			        boolean flag2 = FTPUtil.uploadFile(products.getPrdCode()+"_small.jpg", input);
           			        System.out.println(j+"=======>"+flag2);
       			    	}
       			    	  
       			    } catch (Exception e) {
       			        e.printStackTrace();  
       			    }finally{
       			    	if(input!=null){
       			    		input.close();
       			    	}
       			    }
     			}
     			
            }
           
            
        }
		productsService.saveOrUpdateEntity(products);
		
		return "ok";
	}
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		productsService.deleteByIds(idList);
		
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 生成会员略图并使大图带上水印，大幅提高浏览速度 
	 * @return
	 */
	public String reducePic(){
		String bigUrl = this.getAbsoluteRootPath() + "/private/UploadImages";
//		String bigUrl = this.getAbsoluteRootPath() + "/private/test";
		
		MarkConfig config = new MarkConfig();
		config.setAlpha(0.5f);
		config.setSrcImgType("1");//1-本地 ，2 -网络
		config.setColor("#FF69B4");
		config.setMarkText("吴方商城");//水印文字
//		config.setFontSize(200);
//		config.setOutputImageDir("d:/test/output3");
		config.setRootPath(bigUrl);
		try {
			MarkTool.batchMarkImageByText(config);
			
			//缩小图片88*100  自动在该会员目录下生成一个thunmb.jpg缩略图，
			MarkTool.reduceImage(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tool";
	}
	
	public String tool(){
		return "tool";
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String[] myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String[] getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String[] myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	

	
}
