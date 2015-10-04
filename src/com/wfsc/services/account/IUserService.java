package com.wfsc.services.account;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.base.exception.CupidRuntimeException;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;


/**
 * 用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IUserService {
	
	public boolean saveUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(String email);
	
	public boolean deleteUserById(Long userId);
	
	public User getUserByEmail(String email);

	public User getUserById(Long userId);

	/** 检查昵称是否已经存在 */
	public boolean nickNameExists(String nickName);

	/** 检查邮件是否已经存在 */
	public boolean emailExists(String email);

	public void modifyPass(Long id, String newPass);
	
	public User login(String userName, String password, HttpServletRequest request) throws CupidRuntimeException;
	
	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(User user); 
	
	public void disableAccount(String userId);
	public void enableAccount(String userId);
	public void disableUsers(String[] stopUserId);
	public void enableUsers(String[] startUserId);
	
	/**
	 * 注册新会员
	 * @param user
	 */
	public void registUser(User user);
	
	/**
	 * 会员激活
	 * @param email 待激活会员帐号
	 * @param activeCode 激活码
	 */
	public void activeUser(String email, String activeCode);
	
	

}
