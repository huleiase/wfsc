package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignCompany;
import com.wfsc.daos.bym.DesignCompanyDao;
import com.wfsc.services.bym.service.IDesignCompanyService;

@Service("designCompanyService")
@SuppressWarnings("unchecked")
public class DesignCompanyServiceImpl implements IDesignCompanyService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private DesignCompanyDao designCompanyDao;

	public Page<DesignCompany> findForPage(Page<DesignCompany> page, Map<String,Object> paramap){
		return designCompanyDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		designCompanyDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(DesignCompany entity){
		designCompanyDao.saveOrUpdateEntity(entity);
	}
	public DesignCompany getDesignCompanyById(Long id){
		return designCompanyDao.getEntityById(id);
	}
	
	public void disableDesignCompany(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		DesignCompany s = designCompanyDao.getEntityById(Long.valueOf(id));
		s.setDis("停用");
		designCompanyDao.updateEntity(s);
	}
	
	public void enableDesignCompany(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		DesignCompany s = designCompanyDao.getEntityById(Long.valueOf(id));
		s.setDis("启用");
		designCompanyDao.updateEntity(s);
	}
	@Override
	public void disableDesignCompanys(String[] ids) {
		for(String id : ids){
			disableDesignCompany(id);
		}
		
	}
	@Override
	public void enableDesignCompanys(String[] ids) {
		for(String id : ids){
			enableDesignCompany(id);
		}
	}
	@Override
	public List<DesignCompany> getAll() {
		return designCompanyDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<DesignCompany> list) {
		designCompanyDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<DesignCompany> getDesignCompanyByPara(Map<String, Object> paramap) {
		return designCompanyDao.getDesignCompanyByPara(paramap);
	}

}
