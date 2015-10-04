package com.wfsc.actions.ad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.util.FTPUtil;
import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.ad.IAdService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 广告维护后台
 * @author Xiaojiapeng
 *
 */
@SuppressWarnings("unchecked")
@Controller("AdAction")
@Scope("prototype")
public class AdAction extends DispatchPagerAction {

	private static final long serialVersionUID = 5145438857558748715L;
	
	@Resource(name = "adService")
	private IAdService adService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private AdvConfig advConfig;
	
	private String picName;
	
	private File picFile;
	
	public IAdService getAdService() {
		return adService;
	}

	public void setAdService(IAdService adService) {
		this.adService = adService;
	}

	public AdvConfig getAdvConfig() {
		return advConfig;
	}

	public void setAdvConfig(AdvConfig advConfig) {
		this.advConfig = advConfig;
	}

	public File getPicFile() {
		return picFile;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	/**
	 * 广告管理页面
	 * @return
	 */
	public String index(){
		list();
		return "index";
	}
	
	/**
	 * 广告列表 table部分
	 * @return
	 */
	public String list(){
		Page<AdvConfig> page = super.getPage();
		page.setPaginationSize(7);
		
		String adType = request.getParameter("adType");
		int type = 0;
		if(StringUtils.isNotEmpty(adType))
			type = Integer.parseInt(adType);
		page = adService.queryAllAds(page, type);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/ad_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("adType", adType);
		return "list";
	}
	
	public String input() {
		AdvConfig advConfig=null;
		String id =(String) request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			advConfig= new AdvConfig();
		}else{
			advConfig = adService.getAdById(Long.valueOf(id));
			if(advConfig == null)
				throw new CupidRuntimeException("需要编辑的广告不存在或者已被删除");
		}
		request.setAttribute("advConfig", advConfig);
		return "input";
	}
	
	public String save(){
		String fileName = "";
		//编辑不更新图片
		if(advConfig.getId() == null){
			if(picFile == null || StringUtils.isBlank(picName))
				throw new CupidRuntimeException("请选择广告图片后保存");
			
			fileName = UUID.randomUUID().toString().replaceAll("-", "") + picName.substring(picName.lastIndexOf("."));
			advConfig.setPicUrl(fileName);
		}
		
		adService.saveOrUpdateAd(advConfig);
		
		
		Admin admin = getCurrentAdminUser();
		String adType = advConfig.getAdType() == 1 ? "普通广告":"幻灯片广告";
		
		
		//保存成功后，再将图片上传到服务器上面
		if(StringUtils.isNotEmpty(fileName)){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(picFile);
				boolean result = FTPUtil.uploadFile(fileName, fis);
				if(!result){
					throw new CupidRuntimeException("上传图片到图片服务器失败");
				}
			} catch (Exception e) {
				throw new CupidRuntimeException("上传图片到图片服务器失败");
			}finally{
				if(fis != null)
					try {
						fis.close();
					} catch (IOException e) {
					}
			}
			
		}else{
		}
		
		return SUCCESS;
	}
	
	/**
	 * 删除广告，支持批量删除
	 */
	public void deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		JSONObject result = new JSONObject();
		try {
			adService.deleteAdByIds(idList);
			
			Admin admin = getCurrentAdminUser();
			
			
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "failed");
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

}
