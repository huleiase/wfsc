package com.wfsc.services.security;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Permission;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.UserGroup;

/**
 * 系统权限相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface ISecurityService {

	/** 获取所有管理员 */
	public List<Admin> getAllAdmin();

	/** 获取某个管理员的信息 */
	public Admin getAdminInfo(Long adminId);

	/** 获取所有角色 */
	public List<Role> getAllRoles();

	/** 新增角色 */
	public boolean addRole(Role role);

	/** 删除角色 */
	public boolean deleteRole(Long roleId);

	/** 更新角色 */
	public boolean updateRole(Role role);

	/** 添加管理员 */
	public boolean addAdminUser(Admin admin);

	/** 更新管理员信息 */
	public boolean updateAdminUser(Admin admin);

	/** 删除管理员信息 */
	public boolean deleteAdminUser(Long adminId);
	
	/** 获得所有权限项  */
	public List<Permission>  getAllPerms();
	
	/** 根据actionId获取权限项  */
	public Permission getPermissionByActionId(String aid);

	/** 根据角色id查找角色对象 */
	public Role getRoleByRoleId(String rid);

	
	public Admin getUserWithPermissionByName(String userName);
	
	public List<Admin> getUserListByRoleName(String roleName);
	
	public List<Role> getRoleListByUserName(String userName) ;
	
	public boolean isAblePermission(String userName, String actionId); 
	
	public boolean isAbleRole(String userName, String roleName);
	
	public void disableAccount(String userId);
	public void enableAccount(String userId);
	public void disableUsers(String[] stopUserId);
	public void enableUsers(String[] startUserId);
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 
	 * @param paramap 查询参数.
	 * 
	 * @return 分页查询结果
	 */
	public Page<Admin> findPageForAdmin(final Page<Admin> page, Map<String,Object> paramap);
	
	
	public List<Permission> getAllSubPermission();
	
	public Role getRoleByName(String roleName);
	public boolean isExitRole(String roleName);
	public void delSelectAdmin(String[] adminIds);

	
	/** 获取所有用户组 */
	public List<UserGroup> getAllGroups();

	/** 新增用户组 */
	public void addGroup(UserGroup group);

	/** 删除用户组 */
	public void deleteGroup(Long groupId);

	public UserGroup getUserGroupById(Long id);
	
	public UserGroup getUserGroupByGroupName(String groupName);
	
	public List<Admin> getUsersByRoleAndArea(String roleName, String area);
	
	public List<Admin> getUserListByGroupId(Long groupId);
	public Permission getPermissionById(Long id);
	
}
