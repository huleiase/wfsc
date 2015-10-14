package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Email;
import com.wfsc.daos.bym.EmailDao;
import com.wfsc.services.bym.service.IEmailService;

@Service("emailService")
@SuppressWarnings("unchecked")
public class EmailServiceImpl implements IEmailService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private EmailDao emailDao;

	public Page<Email> findForPage(Page<Email> page, Map<String,Object> paramap){
		return emailDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		emailDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Email entity){
		emailDao.saveOrUpdateEntity(entity);
	}
	public Email getEmailById(Long id){
		return emailDao.getEntityById(id);
	}
	
	@Override
	public List<Email> getAll() {
		return emailDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Email> list) {
		emailDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Email> getEmailByPara(Map<String, Object> paramap) {
		return emailDao.getEmailByPara(paramap);
	}
	@Override
	public int getUnreadCount(String username) {
		return emailDao.getUnreadCount(username);
	}

	
}
