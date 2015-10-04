/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.user.UserGroup;

/**
 * @since cupid
 * 
 * @author jacky.wang
 */
@Repository("groupDao")
public class GroupDao extends EnhancedHibernateDaoSupport<UserGroup> {

	public long addUserGroup(UserGroup group) {
		getHibernateTemplate().saveOrUpdate(group);
		return group.getId();
	}

	@Override
	protected String getEntityName() {
		return UserGroup.class.getName();
	}
	
	/**
	 * 将角色从表中删除
	 */
	public boolean deleteUserGroup(UserGroup groupObj) {
		getHibernateTemplate().delete(groupObj);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup>getUserGroupsByIds(final List<Long>groupIds){
		return (List<UserGroup>)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from UserGroup where id in (:ids)";
				Query query = session.createQuery(hql);
				query.setParameterList("ids",groupIds);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 根据groupName返回表中对应的UserGroup对象
	 */
	@SuppressWarnings("unchecked")
	public UserGroup getUserGroupWithPermissionByName(String groupName) {
		UserGroup groupObj = null;
		List<UserGroup> result = getHibernateTemplate().find("from UserGroup where groupName = ?", groupName);
		if (CollectionUtils.isNotEmpty(result)) {
			groupObj = result.get(0);
			Hibernate.initialize(groupObj.getUsers());
		}
		return groupObj;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserGroup>getUserGroupsByNames(final List<Long>groupNames){
		return (List<UserGroup>)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from UserGroup where groupName in (:groupNames)";
				Query query = session.createQuery(hql);
				query.setParameterList("groupName",groupNames);
				return query.list();
			}
			
		});
	}
	
}