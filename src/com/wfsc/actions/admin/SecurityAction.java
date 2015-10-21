package com.wfsc.actions.admin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.constants.LogModule;
import com.wfsc.common.bo.system.BackUpPlan;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Permission;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.services.system.IBackUpPlanService;
import com.wfsc.util.CipherUtil;

/**
 * 权限管理
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("SecurityAction")
@Scope("prototype")
public class SecurityAction extends DispatchPagerAction {

	private static final long serialVersionUID = 8318731748566413432L;
	
	@Resource(name = "securityService")
	private ISecurityService securityService;
	
	@Resource(name = "backUpPlanService")
	private IBackUpPlanService backUpPlanService;
	
	private BackUpPlan backUpPlan;

	private Admin admin;

	private User user;
	
	private Role role;

	/** 用来接收从添加角色界面选择的权限ID */
	//private String[] actionIds;
	
	/** 用来接收从添加管理员界面选择的角色ID */
	private String[] roleIds;

	public Admin getAdmin() {
		return admin;
	}
	
	public BackUpPlan getBackUpPlan() {
		return backUpPlan;
	}

	public void setBackUpPlan(BackUpPlan backUpPlan) {
		this.backUpPlan = backUpPlan;
	}



	/**
	 * 角色管理：添加角色
	 * 
	 * @return
	 */
	/*public String addRole() {
		//加载所有的权限项
		List<Permission> allPerms = securityService.getAllPerms();
		request.setAttribute("allPerms", allPerms);
		return "ok";
	}*/
	
	public String addRole(){
		try{
			String roleId = request.getParameter("roleId");
			//Role role = null;
			if(StringUtils.isNotEmpty(roleId)){
				role = securityService.getRoleByRoleId(roleId);
			}else{
				role = new Role();
			}
			
			//加载所有的权限项
			List<Permission> allPerms = securityService.getAllPerms();
		//	List<Permission> allSubPerms = securityService.getAllSubPermission();
			//设置下选中的状态
			Set<Permission> perms = role.getPerms();
			for (Permission p : allPerms) {
				for (Permission permission : perms) {
				//	System.out.println("所有权限的id=="+p.getId()+"--------------》角色拥有的权限的id=="+permission.getId());
					if(p.getId().longValue()==permission.getId().longValue()){
						System.out.println("相等===="+p.getId());
						p.setCk("1");
					}
				}
			}
			/*for (Permission p : allSubPerms) {
				for (Permission permission : perms) {
					if(p.getActionId().equals(permission.getActionId())){
						p.setCk("1");
					}
				}
			}*/
			
		//	request.setAttribute("allSubPerms", allSubPerms);
			request.setAttribute("allPerms", allPerms);
			return "addRole";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("info", "操作失败。。");
			return ERROR;
		}
	}
	
	
	/**
	 * 团队管理：新建管理员
	 * 
	 * @return
	 */
	public String addAdmin() {
		//加载所有的角色
		List<Role> allRoles = securityService.getAllRoles();
		request.setAttribute("allRoles", allRoles);
		return "ok";
	}
	/**
	 * 团队管理：修改管理员
	 * 
	 * @return
	 */
	public String editAdmin() {
		String adminId = request.getParameter("adminId");
		//加载所有的角色
		Admin adminInfo = securityService.getAdminInfo(Long.valueOf(adminId));
		request.setAttribute("admin", adminInfo);
		List<String> list = new ArrayList<String>();
		for (Role r : adminInfo.getRoles()) {
			list.add(r.getId() + "");
		}
		request.setAttribute("CHECKED_ROLE_LIST", list);
		return "ok";
	}
	
	/**
	 * 修改密码 
	 * @return
	 */
	public String modifyPwd() {
		String id = request.getParameter("adminId");
		request.setAttribute("adminId", id);
		return "ok";
	}
	
	/**
	 * 保存密码 
	 * @return
	 */
	public String savePwd() {
		String adminId = request.getParameter("adminId");
		String oldPwd = request.getParameter("oldPwd");
		Admin adminInfo = securityService.getAdminInfo(Long.valueOf(adminId));
		if(CipherUtil.validatePassword(adminInfo.getPassword(), oldPwd)){//原密码错误
			setMsg("原密码输入不正确！", null);
			return ERROR;
		}
		//保存密码
		String newPwd = request.getParameter("newPwd");
		adminInfo.setPassword(CipherUtil.generatePassword(newPwd));
		securityService.updateAdminUser(adminInfo);
		setMsg("密码修改成功！", null);
		return "info";
	}
	
	
	
	/**
	 * 角色管理：新建角色
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String saveRole() throws IllegalAccessException, InvocationTargetException {
		String[] idArray = request.getParameterValues("permissionIds");
		for (String aid : idArray) {
			Permission perm = securityService.getPermissionById(Long.valueOf(aid));
			role.getPerms().add(perm);
		}
		if(role.getId()!=1){
			role.setDeletable(true);
		}
		//保存角色到数据库
		securityService.addRole(role);
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.systemLog, curAdminName+"新增或修改了角色");
		//转到角色列表
		return "ok";
	}
	
	public String roleManager(){
		this.setTopMenu();
		List<Role> allRoles = securityService.getAllRoles();
		request.setAttribute("allRoles", allRoles);
		return "roleManager";
	}
	
	public String delRole(){
		String roleId = request.getParameter("roleId");
		try {
			securityService.deleteRole(new Long(roleId));
			
			
			response.getWriter().write("succ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.systemLog, curAdminName+"删除了角色");
		return null;
	}
	public String checkRoleUser(){
		String roleName = request.getParameter("roleName");
		String msg = "";
		try {
			List<Admin> list = securityService.getUserListByRoleName(roleName);
			if(list.size()>0){
				msg = "no";
			}else{
				msg = "ok";
			}
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String isExitRole(){
		String roleName = request.getParameter("roleName");
		String msg = "";
		Role r = securityService.getRoleByName(roleName);
		if(r==null){
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
	
	public String backUpPlanInPut(){
		backUpPlan = backUpPlanService.getBackUpPlan();
		return "backUp";
	}
	public String updateBackUpPlan(){
		backUpPlanService.saveOrUpdateBackUpPlan(backUpPlan);
		
		
		request.setAttribute("success", "1");
		return "backUp";
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	
	public void setRole(Role role) {
		this.role = role;
	}

	
	/*public String[] getActionIds() {
		return actionIds;
	}

	
	public void setActionIds(String[] actionIds) {
		this.actionIds = actionIds;
	}*/

	
	public String[] getRoleIds() {
		return roleIds;
	}

	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
