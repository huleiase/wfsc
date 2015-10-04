/******************************************************************************** 
 * Create Author   : Administrator
 * Create Date     : Sep 25, 2009
 * File Name       : Role.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.common.bo.user;

import java.util.HashSet;
import java.util.Set;


/**
 * @author allen.wang
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_usergroup" lazy="false"
 */
public class UserGroup implements java.io.Serializable {

	private static final long serialVersionUID = -6117441659416793198L;

	private long id;

	/** 用户组名称 */
	private String groupName;

	/** 用户组描述 */
	private String groupDescription;
	
	private String ck = "0";

	/** user与group之前双向多对多，因为需要知道用户组下拥有的用户，级联设为NONE */
	private Set<Admin> users = new HashSet<Admin>(0);

	public UserGroup() {
	}

	public UserGroup(String groupName, String groupDescription) {
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	public UserGroup(long id, String groupName, String groupDescription) {
		this.id = id;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	/**
	 * 用户名是唯一的，只要用户名相等的两个角色就认为是相等的
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		UserGroup group = (UserGroup) obj;
		if (this.groupName.equals(group.getGroupName()))
			return true;
		else
			return false;
	}

	/** hash code */
	public int hashCode() {
		int hash = 1;
		if (groupName != null)
			hash = groupName.length();
		hash = (int) (id ^ (id >>> 32)) + 3 * hash;
		return hash;
	}

	/**
	 * hibernate中的主键
	 * 
	 * @return Returns the id.
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @hibernate.property type="string"
	 * @hibernate.property unique="true"
	 */
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 
	 * @hibernate.property type="string"
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	/**
	 * @hibernate.collection-many-to-many class="com.wfsc.common.bo.user.Admin" column="adminId"
	 * @hibernate.collection-key column="groupId"
	 * @hibernate.set name="users" table="wf_admin_group" inverse="true" cascade="none" lazy="false"
	 * @return
	 */
	public Set<Admin> getUsers() {
		return users;
	}

	public void setUsers(Set<Admin> users) {
		this.users = users;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}

	


	
	
}
