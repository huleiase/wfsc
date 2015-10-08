package com.wfsc.services.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Permission;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.UserGroup;
import com.wfsc.daos.AdminDao;
import com.wfsc.daos.GroupDao;
import com.wfsc.daos.PermissionDao;
import com.wfsc.daos.RoleDao;

@Service("securityService")
public class SecurityServiceImpl implements ISecurityService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PermissionDao permissionDao;
	
	
	@Autowired
	private GroupDao groupDao;
	

	@Override
	public boolean addAdminUser(Admin admin) {
		try{
			adminDao.addAdmin(admin);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addRole(Role role) {
		try{
			roleDao.addRole(role);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAdminUser(Long adminId) {
		adminDao.deleteEntity(adminId);
		return false;
	}

	@Override
	public boolean deleteRole(Long roleId) {
		roleDao.deleteEntity(roleId);
		return false;
	}

	@Override
	public Admin getAdminInfo(Long adminId) {
		return adminDao.getEntityById(adminId);
	}

	@Override
	public List<Admin> getAllAdmin() {
		adminDao.getAllUsers();
		return null;
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllEntities();
	}

	@Override
	public boolean updateAdminUser(Admin admin) {
		try{
			adminDao.updateAdmin(admin);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRole(Role role) {
		roleDao.updateEntity(role);
		return false;
	}

	
	

	@Override
	public List<Permission> getAllPerms() {
		return permissionDao.getAllEntities();
	}

	@Override
	public Permission getPermissionByActionId(String aid) {
		return permissionDao.getUniqueEntityByOneProperty("actionId", aid);
	}

	@Override
	public Role getRoleByRoleId(String rid) {
		return roleDao.getEntityById(Long.valueOf(rid));
	}

	@Override
	public Admin getUserWithPermissionByName(String userName){
		return adminDao.getUserWithPermissionByName(userName);
	}
	
	@Override
	public List<Admin> getUserListByRoleName(String roleName) {
		Role role = roleDao.getRoleWithPermissionByName(roleName);
		List<Admin> users = new ArrayList<Admin>();
		if(role!=null){
			users.addAll(role.getUsers());
		}
		
		return users;
	}
	
	@Override
	public List<Role> getRoleListByUserName(String userName) {
		Admin userObj = adminDao.getUserWithPermissionByName(userName);
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(userObj.getRoles());
		return roles;
	}
	
	@Override
	public boolean isAblePermission(String userName, String actionId) {
		if (userName == null || actionId == null)
			return false;
		Admin user = adminDao.getUserWithPermissionByName(userName);
		if (null == user)
			return false;
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Permission> perms = role.getPerms();
			for (Permission perm : perms) {
				if (perm.getActionId().equals(actionId))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isAbleRole(String userName, String roleName) {
		if (userName == null || roleName == null)
			return false;
		Admin user = adminDao.getUserWithPermissionByName(userName);
		if (user == null)
			return false;
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			if (role.getRoleName().contains(roleName))
				return true;
		}
		return false;
	}
	
	@Override
	public void disableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		Admin user = adminDao.getEntityById(Long.valueOf(userId));
		user.setStatus(0);
		adminDao.updateAdmin(user);
	}

	@Override
	public void enableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		Admin user = adminDao.getEntityById(Long.valueOf(userId));
		user.setStatus(1);
		adminDao.updateAdmin(user);
	}
	
	@Override
	public void disableUsers(String[] stopUserId) {
		for (int i = 0; i < stopUserId.length; i++) {
			disableAccount(stopUserId[i]);
		}
	}

	@Override
	public void enableUsers(String[] startUserId) {
		for (int i = 0; i < startUserId.length; i++) {
			enableAccount(startUserId[i]);
		}
	}
	public Page<Admin> findPageForAdmin(final Page<Admin> page, Map<String,Object> paramap){
		return adminDao.findPageForAdmin(page, paramap);
	}
	
	public List<Permission> getAllSubPermission(){
		return this.permissionDao.getAllSubPermission();
	}
	
	public Role getRoleByName(String roleName){
		return roleDao.getUniqueEntityByOneProperty("roleName", roleName);
	}
	
	public boolean isExitRole(String roleName){
		Role r = getRoleByName(roleName);
		if(r==null){
			return false;
		}else{
			return true;
		}
	}
	
	public void delSelectAdmin(String[] adminIds){
		List<Long> ids = new ArrayList<Long>();
		for(String id : adminIds){
			Admin a = adminDao.getEntityById(Long.valueOf(id));
			a.setRoles(null);
			adminDao.deleteEntity(a);
			//ids.add(Long.valueOf(id));
		}
		//adminDao.deletAllEntities(ids);
	}
	public AdminDao getAdminDao() {
		return adminDao;
	}

	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}


	@Override
	public void addGroup(UserGroup group) {
		groupDao.saveOrUpdateEntity(group);
	}

	@Override
	public void deleteGroup(Long groupId) {
		groupDao.deleteEntity(groupId);
	}

	@Override
	public List<UserGroup> getAllGroups() {
		return groupDao.getAllEntities();
	}

	@Override
	public UserGroup getUserGroupByGroupName(String groupName) {
		return groupDao.getUniqueEntityByOneProperty("groupName", groupName);
	}

	@Override
	public UserGroup getUserGroupById(Long id) {
		return groupDao.getEntityById(id);
	}
	@Override
	public List<Admin> getUsersByRoleAndArea(String roleName, String area){
		String hql = "select u from Admin u left join u.roles r where r.roleName='"+roleName+"' and u.area='" + area + "' order by u.username";
		List<Admin> users = adminDao.getEntityByHql(hql);
		return  users;
	}

	@Override
	public List<Admin> getUserListByGroupId(Long groupId) {
		UserGroup ug = groupDao.getEntityById(groupId);
		List<Admin> users = new ArrayList<Admin>();
		if(ug!=null){
			users.addAll(ug.getUsers());
		}
		
		return users;
	}

	@Override
	public Permission getPermissionById(Long id) {
		return permissionDao.getEntityById(id);
	}
}
