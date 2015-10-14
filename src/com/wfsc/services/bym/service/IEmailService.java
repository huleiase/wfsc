package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Email;


public interface IEmailService {

	public Page<Email> findForPage(Page<Email> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Email entity); 
	
	public Email getEmailById(Long id);
	
	/*public void disableEmails(String[] ids);
	
	public void enableEmails(String[] ids);
	
	public void disableEmail(String id);
	
	public void enableEmail(String id);*/
	
	public List<Email> getAll();
	
	public void saveOrUpdateAll(List<Email> list);
	
	public List<Email> getEmailByPara(Map<String,Object> paramap);
	
	public int getUnreadCount(String username);
}
