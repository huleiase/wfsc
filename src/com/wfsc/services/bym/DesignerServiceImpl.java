package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Designer;
import com.wfsc.daos.bym.DesignerDao;
import com.wfsc.services.bym.service.IDesignerService;

@Service("designerService")
@SuppressWarnings("unchecked")
public class DesignerServiceImpl implements IDesignerService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private DesignerDao designerDao;

	public Page<Designer> findForPage(Page<Designer> page, Map<String,Object> paramap){
		return designerDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		designerDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Designer entity){
		designerDao.saveOrUpdateEntity(entity);
	}
	public Designer getDesignerById(Long id){
		return designerDao.getEntityById(id);
	}
	
	@Override
	public List<Designer> getAll() {
		return designerDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Designer> list) {
		designerDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Designer> getDesignerByPara(Map<String, Object> paramap) {
		return designerDao.getDesignerByPara(paramap);
	}
	@Override
	public Designer getDesigner(String codeName) {
		return designerDao.getUniqueEntityByOneProperty("codeName", codeName);
	}

	
}
