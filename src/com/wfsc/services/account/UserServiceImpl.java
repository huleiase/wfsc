package com.wfsc.services.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ServerBeanFactory;
import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.mail.Email;
import com.base.mail.EmailDispatcher;
import com.base.util.Page;
import com.wfsc.common.bo.report.UserRegisterReport;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.report.UserRegisterReportDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.util.CipherUtil;
import com.wfsc.util.DateUtil;
import com.wfsc.util.SysUtil;

/**
 * 用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	protected static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRegisterReportDao userRegisterReportDao;

	@Override
	public void modifyPass(Long userId, String newPass) {
		String password = CipherUtil.generatePassword(newPass);
		String sql = "update User set password = '" + password + "' where id= " + userId;
		userDao.getHibernateTemplate().bulkUpdate(sql);
	}
	
	@Override
	public boolean deleteUserById(Long userId) {
		try{
			this.userDao.deleteEntity(Long.valueOf(userId));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(String email) {
		userDao.deleteUserByEmail(email);
		return true;
	}

	@Override
	public boolean emailExists(String email) {
		return userDao.isExistByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUniqueEntityByOneProperty("email", email);
	}

	@Override
	public User getUserById(Long userId) {
		return userDao.getEntityById(userId);
	}

	@Override
	public boolean nickNameExists(String nickName) {
		return userDao.isExistByNickName(nickName);
	}

	@Override
	public boolean saveUser(User user) {
		userDao.saveEntity(user);
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		userDao.updateEntity(user);
		return true;
	}

	@Override
	public User login(String userName, String password, HttpServletRequest request) throws CupidRuntimeException{
		User user = null;
		if(StringUtils.isNumeric(userName)){
			user = userDao.getUserByTelphone(userName);
		}else{
			user = userDao.getUserByEmail(userName);
		}
		//验证帐号是否存在
		if(user == null)
			throw new CupidRuntimeException("用户名不存在");
		//检查帐号状态
		int status = user.getStatus();
		if(status == 0){
			throw new CupidRuntimeException("该用户已被禁用");
		}else if(status == 9){
			throw new CupidRuntimeException("账号尚未激活");
		}
		//检查密码是否正确
		boolean valid = CipherUtil.validatePassword(user.getPassword(), password);
		if(!valid){
			throw new CupidRuntimeException("密码不正确");
		}
		//更新帐号状态
		user.setOnline(true);
		user.setLastLogin(new Date());
		String loginIp = request.getRemoteAddr();
		user.setLoginIp(loginIp);
		
		userDao.updateUser(user);
		
		return user;
	}
	
	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap){
		return userDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		userDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(User u){
		userDao.saveOrUpdateEntity(u);
	}
	
	@Override
	public void disableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		User user = userDao.getEntityById(Long.valueOf(userId));
		user.setStatus(0);
		userDao.updateEntity(user);
	}

	@Override
	public void enableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		User user = userDao.getEntityById(Long.valueOf(userId));
		user.setStatus(1);
		userDao.updateEntity(user);
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

	@Override
	public void registUser(User user) {
		boolean exists = emailExists(user.getEmail());
		if(exists)
			throw new CupidRuntimeException("emailExists");
		boolean nickExists = nickNameExists(user.getNickName());
		if(nickExists)
			throw new CupidRuntimeException("nickexists");
		String password = user.getPassword();
		password = CipherUtil.generatePassword(password);
		user.setPassword(password);
		Date registeDate = new Date();
		user.setRegDate(registeDate);
		user.setStatus(9);
		String activeCode = SysUtil.createVerifyCode();
		user.setActiveCode(activeCode);
		//保存入库
		userDao.saveEntity(user);
		
		if(user.getEmail() != null){
			//保存入库成功后，发送邮件通知用户激活
			List<String> addresses =new ArrayList<String>();
			addresses.add(user.getEmail());
			String param = user.getEmail() + "=" + user.getActiveCode();
			Email email = new Email("register", "帐号激活", addresses, param);
			EmailDispatcher dispatcher = (EmailDispatcher) ServerBeanFactory.getBean("mailDispather");
			dispatcher.dispatchMail(email);
		}
		
		//统计报表
		int year = DateUtil.getYear(registeDate);
		int month = DateUtil.getMonth(registeDate);
		int week = DateUtil.getWeek(registeDate);
		UserRegisterReport report = null;
		synchronized (userRegisterReportDao) {
			report = userRegisterReportDao.getReportByYMW(year, month, week);
			if(report == null){
				report = new UserRegisterReport();
				report.setYear(year);
				report.setMonth(month);
				report.setWeek(week);
				report.setRegCount(1);
			}else{
				report.setRegCount(report.getRegCount() + 1);
			}
			userRegisterReportDao.saveOrUpdateEntity(report);
		}
	}

	@Override
	public void activeUser(String email, String activeCode) {
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(activeCode)){
			throw new CupidRuntimeException("激活请求不合法");
		}
		
		User user = userDao.getUserByEmail(email);
		if(user == null){
			throw new CupidRuntimeException("对不起，您激活的帐号不存在");
		}
		if(user.getStatus() == 0){
			throw new CupidRuntimeException("您的帐号已激活，请勿重复激活");
		}
		if(!StringUtils.equals(user.getActiveCode(), activeCode)){
			throw new CupidRuntimeException("激活失败");
		}
		user.setActiveCode(null);
		user.setStatus(1);
		userDao.updateEntity(user);
	}

}
