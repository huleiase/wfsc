package com.wfsc.actions.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.actions.vo.PicRecObj;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;

@Controller("UserAction")
@Scope("prototype")
public class UserAction extends DispatchPagerAction {

	private static final long serialVersionUID = -1039247888666512706L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "userService")
	private IUserService userService;

	private File[] myFile;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;
	
	private User user;
	
	private String certType;
	
	private PicRecObj picobj;
	

	public User getUser() {
		return user;
	}
	 
 
	 
//	/**
//	 * 生成,发送验证码
//	 * @return
//	 */
//	public String sendVerifyCode(){
//		Long userId = this.getCurrentUser().getId();
//		String tel = this.getRequest().getParameter("telephone");
//		//生成随机6位验证码并存入用户表
//		String verifyCode = SysUtil.createVerifyCode();
//		logger.info(tel + " - " + verifyCode);
//		try{
//			boolean isOk = this.userService.updateUserTelVerifyCode(userId, tel, verifyCode);
//			String isMock = Version.getInstance().getNewProperty("mock");
//			//如果是测试,不调用短信接口,直接返回
//			if("true".equals(isMock)){
//				return "sendFinish";
//			}
//			
//			if(isOk){
//				//发送验证码到用户手机
//				Sender.getInstance().sendVerifyCode(verifyCode, tel);
//			} else {
//				return "error";
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return "sendFinish";
//	}
	
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	
	public String photo(){
		System.out.println("照片管理....");
		return SUCCESS;
	}
	/**
	 * 修改密码 
	 * @return
	 */
	public String modifyPass(){
		String flag = request.getParameter("flag");
		if("enter".equals(flag)){
			return SUCCESS;
		}
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String confirmPass = request.getParameter("confirmPass");
		if(!newPass.equals(confirmPass)){
			request.setAttribute("info", "对不起, 两次密码输入不匹配, 请重新输入!");
			return ERROR;
		}
		//加密后的密码
		String encrptPassword = new BASE64Encoder().encode(oldPass.getBytes());
		if(!this.getCurrentUser().getPassword().equals(encrptPassword)){
			request.setAttribute("info", "对不起, 旧密码输入错误, 请重新输入!");
		}else{
			this.userService.modifyPass(this.getCurrentUser().getId(), newPass);
			request.setAttribute("info", "密码修改成功!");
		}
		return SUCCESS;
	}
	public String manager(){
		list();
		return "manager";
	}
	
	public String list(){
		Page<User> page = new Page<User>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String nickName = request.getParameter("nickName");
		String status = request.getParameter("status");
		if(StringUtils.isNotEmpty(nickName)){
			paramap.put("nickName", nickName);
			request.setAttribute("nickName", nickName);
		}
		if(StringUtils.isNotEmpty(status)){
			paramap.put("status", status);
			request.setAttribute("status", status);
		}
		
		page = userService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/user_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("userlist", page.getData());
		return "list";
	}
	public String save() throws IOException{
		userService.saveOrUpdateEntity(user);
		return "ok";
	}
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		userService.deleteByIds(idList);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	
	public String detail() {
		String id = request.getParameter("id");
		user = userService.getUserById(Long.valueOf(id));
		int status = user.getStatus();
		String statusStr = "启用";
		if(0==status){
			statusStr = "禁用";
		}
		user.setStatusStr(statusStr);
		return "detail";
	}
	public String enableAccount(){
		String adminIds = request.getParameter("ids");
		String[] ids = adminIds.split(",");
		userService.enableUsers(ids);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String disableAccount(){
		String adminIds = request.getParameter("ids");
		String[] ids = adminIds.split(",");
		userService.disableUsers(ids);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//------------------->>>>>>>>>>>>>>>>>>>>>>> 微信平台  <<<<<<<<<<<<<<<<<<<<<<<<<--------------------

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}
	public void setMyFileContentType(String[] contentType) {
		this.myFileContentType = contentType;
	}

	public void setMyFileFileName(String[] fileName) {
		this.myFileFileName = fileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	
	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	
	public String[] getMyFileFileName() {
		return myFileFileName;
	}
	
	public String getCertType() {
		return certType;
	}

	
	public void setCertType(String certType) {
		this.certType = certType;
	}

	
	public PicRecObj getPicobj() {
		return picobj;
	}

	
	public void setPicobj(PicRecObj picobj) {
		this.picobj = picobj;
	}
}
