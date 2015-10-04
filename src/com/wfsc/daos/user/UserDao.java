package com.wfsc.daos.user;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;

@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends EnhancedHibernateDaoSupport<User> {

	@Override
	protected String getEntityName() {
		return User.class.getName();
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public long saveUser(User user){
		getHibernateTemplate().saveOrUpdate(user);
		return user.getId();
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user){
		getHibernateTemplate().saveOrUpdate(user);
		return true;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user){
		getHibernateTemplate().delete(user);
		return true;
	}
	
	/**
	 * 判断用户是否存在，即判断该邮箱是否在系统中注册过
	 * @return
	 */
	public boolean isExistByEmail(String email){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where email = ?", email).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 判断该手机号码是否被绑定，系统中不允许出现两个相同的手机号码
	 * @param telphone
	 * @return
	 */
	public boolean isExistByTelphone(String telphone){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where telphone = ?", telphone).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 检查昵称是否已经被占用
	 * @param nickName
	 * @return
	 */
	public boolean isExistByNickName(String nickName){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where nickName = ?", nickName).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 获取所有的用户
	 * @return
	 */
	public List<User> getAllUsers(){
		return getAllEntities();
	}
	
	/**
	 * 根据邮箱地址查询用户信息
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where email = ?", email);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	
	
	/**
	 * 根据手机号码查询用户信息
	 * @param telphone
	 * @return
	 */
	public User getUserByTelphone(String telphone){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where telphone = ?", telphone);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	/**
	 * 根据昵称查询用户信息
	 * @param telphone
	 * @return
	 */
	public User getUserByNickName(String nickName){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where nickName = ?", nickName);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	
	/**
	 * 在线用户列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getOnlineUsers() {
		return getHibernateTemplate().find("from User where online = ?", true);
	}
	
	public void setUserOffLine(String nickName) {
		User user = this.getUserByNickName(nickName);
		if (user != null) {
			user.setOnline(false);
			getHibernateTemplate().update(user);
		}
	}
	
	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("select distinct obj from User as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				if ("nickName".equals(key)) {
					hql.append(" and obj.nickName = '" + paramap.get(key) + "'");
					continue;
				}
				if ("status".equals(key)) {
					hql.append(" and obj.status = " + paramap.get(key));
					continue;
				}
			}
			int totalCount = this.countByHql(hql.toString());
			
			List<User> list = this.findList4Page(hql.toString(), page
					.getFirst() - 1, page.getPageSize());
			page.setData(list);
			page.setTotalCount(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据email删除用户
	 * @param email 需要删除的用户的email
	 * @return 删除的用户个数
	 */
	public int deleteUserByEmail(final String email){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from User where email = ?";
				Query query = session.createQuery(hql);
				query.setString(0, email);
				return query.executeUpdate();
			}
			
		});
	}
	
	/**
	 * 根据注册时间统计用户注册数量
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int countUserByCreateTime(final Date startTime, final Date endTime){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from User where regDate > ? and regDate <= ?";
				Query query = session.createQuery(hql);
				query.setDate(0, startTime);
				query.setDate(1, endTime);
				return ((Number)query.iterate().next()).intValue();
			}
			
		});
	}

}
