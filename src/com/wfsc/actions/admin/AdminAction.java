package com.wfsc.actions.admin;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.constants.LogModule;
import com.exttool.MarkConfig;
import com.exttool.MarkTool;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.UserGroup;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.DateUtil;

/**
 * 后台管理,维护会员信息,系统信息
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("AdminAction")
@Scope("prototype")
public class AdminAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840817897299260353L;

	@Resource(name = "securityService")
	private ISecurityService securityService;
	
	@Autowired
	private ISystemLogService systemLogService;

	private File[] myFile;

	private static final int BUFFER_SIZE = 16 * 1024;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;

	private Admin admin;

	private User user;
	
	
	private String[] roleIds;
	private String[] groupIds;

	
	public Admin getAdmin() {
		return admin;
	}

	/**
	 * 到管理员登陆界面
	 * 
	 * @return
	 */
	public String gologin() {
		if(session.get(CupidStrutsConstants.SESSION_ADMIN) != null)
			return gotoAdminIndex();
		return "login";
	}
	
	
	public String gotoAdminIndex(){
		return SUCCESS;
	}
	
	public String login(){
		String CODE_IMAGE = this.session.get(CupidStrutsConstants.CODE_IMAGE_ADMIN)==null?"":this.session.get(CupidStrutsConstants.CODE_IMAGE_ADMIN).toString();
		String verifyCode = this.request.getParameter("verifyCode");
		if(StringUtils.isNotBlank(CODE_IMAGE)&&!CODE_IMAGE.equalsIgnoreCase(verifyCode)){
			request.setAttribute("errorMsg", "验证码错误");
			return "login";
		}
	//	String encrptPassword = new BASE64Encoder().encode(admin.getPassword().getBytes());
		String encrptPassword = admin.getPassword();
		Admin user = securityService.getUserWithPermissionByName(admin.getUsername());
		if(user!=null){
			if(user.getPassword().trim().equals(encrptPassword.trim())){
				if(user.getStatus()==0){
					request.setAttribute("errorMsg", "该账号已被禁用");
					return "login";
				}
				
				session.put(CupidStrutsConstants.SESSION_ADMIN, user);
				session.put(CupidStrutsConstants.SESSION_ADMIN_ROLE, user.getRoleString());//角色名
				session.put(CupidStrutsConstants.SESSION_SUPER_ADMIN, user.isSuperAdmin());//是否是超级管理员
				user.setOnline(true);
				user.setLastLoginDate(new Date());
				user.setLogCount(user.getLogCount()+1);
				securityService.updateAdminUser(user); 
				saveSystemLog(LogModule.systemLog, admin.getUsername()+"登录系统");
				return SUCCESS;
			}else{
				request.setAttribute("errorMsg", "密码错误");
				return "login";
			}
		}else{
			request.setAttribute("errorMsg", "用户名错误");
			return "login";
		}
		
	}
	
	public String index(){
		return SUCCESS;
	}
	
	public String manager(){
		this.setTopMenu();
		list();
		return "adminManager";
	}
	
	public String list(){
		Page<Admin> page = new Page<Admin>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String username = request.getParameter("username");
		String rolename = request.getParameter("rolename");
		String area = request.getParameter("area");
		if(StringUtils.isNotEmpty(username)){
			paramap.put("username", username);
			request.setAttribute("username", username);
		}
		if(StringUtils.isNotEmpty(area)){
			paramap.put("area", area);
			request.setAttribute("area", area);
		}
		if(StringUtils.isNotEmpty(rolename)){
			paramap.put("rolename", rolename);
			request.setAttribute("rolename", rolename);
		}
		
		page = securityService.findPageForAdmin(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/admin_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("adminList", page.getData());
		return "adminList";
	}

	/**
	 * 管理员退出
	 * 
	 * @return
	 */
	public String logout() {
		System.out.println("管理员退出....");
		Object uObj = session.get(CupidStrutsConstants.SESSION_ADMIN);
		if (uObj != null) {
			Admin user = (Admin)uObj;
			user.setOnline(false);
			securityService.updateAdminUser(user); 
		//	session.remove(CupidStrutsConstants.SESSION_ADMIN);
		//	session.remove(CupidStrutsConstants.SESSION_ADMIN_ROLE);
		//	session.remove(CupidStrutsConstants.SESSION_SUPER_ADMIN);
			session.clear();
			
		}
		request.setAttribute("msg", "您已经成功退出!");
		return "login";
	}


	/**
	 * 后台管理员:录入用户页面
	 * 
	 * @return
	 */
	public String inputAdmin() {
		String username = request.getParameter("username");
		if(StringUtils.isEmpty(username)){
			admin = new Admin();
		}else{
			admin = this.securityService.getUserWithPermissionByName(username);
		}
		Set<Role> hasRoles = admin.getRoles();
		List<Role> roles = this.securityService.getAllRoles();
		for(Role role : roles){
			for(Role hr : hasRoles){
				if(role.getId()==hr.getId()){
					role.setCk("1");
				}
			}
		}
		request.setAttribute("roles", roles);
		Set<UserGroup> hasGroups = admin.getGroups();
		List<UserGroup> groups = this.securityService.getAllGroups();
		for(UserGroup group : groups){
			for(UserGroup hr : hasGroups){
				if(group.getId()==hr.getId()){
					group.setCk("1");
				}
			}
		}
		request.setAttribute("groups", groups);
		return "input";
	}
	public String saveAdmin(){
		Set<Role> roleset = new HashSet<Role>();
		for (String rid : roleIds) {
			Role role = securityService.getRoleByRoleId(rid);
			roleset.add(role);
		}
		Set<UserGroup> groupset = new HashSet<UserGroup>();
		if(groupIds!=null&&groupIds.length>0&&StringUtils.isNotBlank(groupIds[0])){
			for (String gid : groupIds) {
				UserGroup group = securityService.getUserGroupById(Long.valueOf(gid));
				groupset.add(group);
			}
		}
		
		if(admin.getId()==null||admin.getId()<1){
			admin.setPassword("11111111");
			admin.setStatus(1);
			admin.setRoles(roleset);
			admin.setGroups(groupset);
			securityService.addAdminUser(admin);
		}else{
			Admin a = securityService.getAdminInfo(admin.getId());
			a.setEmail(admin.getEmail());
			a.setPhone(admin.getPhone());
			a.setArea(admin.getArea());
			a.setZhname(admin.getZhname());
			a.setRoles(roleset);
			a.setGroups(groupset);
			a.setPassword(admin.getPassword());
			securityService.updateAdminUser(a);
		}
		
		return "ok";
	}
	public String isExitAdmin(){
		String msg = "";
		String userName = request.getParameter("username");
		Admin a = securityService.getUserWithPermissionByName(userName);
		if(a==null){
			msg = "ok";
		}else{
			msg = "no";
		}
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String delAdmin(){
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.delSelectAdmin(ids);
		
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String enableAccount(){
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.enableUsers(ids);
		
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String disableAccount(){
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.disableUsers(ids);
		
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改密码 
	 * @return
	 */
	public String modifyPwd() {
	//	String id = request.getParameter("adminId");
	//	request.setAttribute("adminId", id);
		return "modifyPwd";
	}
	
	/**
	 * 保存密码 
	 * @return
	 */
	public String savePwd() {
	//	String username = request.getParameter("adminId");
	//	Admin adminInfo = securityService.getAdminInfo(Long.valueOf(adminId));
		Admin adminInfo = (Admin)session.get(CupidStrutsConstants.SESSION_ADMIN);
		//保存密码
		String newPwd = request.getParameter("newPwd");
	//	adminInfo.setPassword(SysUtil.encodeBase64(newPwd));
		adminInfo.setPassword(newPwd);
		securityService.updateAdminUser(adminInfo);
		return SUCCESS;
	}

	/**
	 * 后台管理员:录入用户
	 * 
	 * @return
	 */
	public String addUser() {
		
		return manager(); // 录入成功后,返回会员列表页.
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
	
	
	public String photo() {
		System.out.println("照片管理....");
		return SUCCESS;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/*public void setUserService(IUserService userService) {
		this.userService = userService;
	}*/

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


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	
}
