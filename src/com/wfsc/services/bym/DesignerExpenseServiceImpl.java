package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerExpense;
import com.wfsc.daos.bym.DesignerExpenseDao;
import com.wfsc.services.bym.service.IDesignerExpenseService;

@Service("designerExpenseService")
@SuppressWarnings("unchecked")
public class DesignerExpenseServiceImpl implements IDesignerExpenseService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private DesignerExpenseDao designerExpenseDao;

	public Page<DesignerExpense> findForPage(Page<DesignerExpense> page, Map<String,Object> paramap){
		return designerExpenseDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		designerExpenseDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(DesignerExpense entity){
		designerExpenseDao.saveOrUpdateEntity(entity);
	}
	public DesignerExpense getDesignerExpenseById(Long id){
		return designerExpenseDao.getEntityById(id);
	}
	
	@Override
	public List<DesignerExpense> getAll() {
		return designerExpenseDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<DesignerExpense> list) {
		designerExpenseDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<DesignerExpense> getDesignerExpenseByPara(Map<String, Object> paramap) {
		return designerExpenseDao.getDesignerExpenseByPara(paramap);
	}
	@Override
	public List<DesignerExpense> getDesignerExpenseByQuoteId(Long quoteId) {
		String hql = "from DesignerExpense where quoteId="+quoteId+" order by id desc";
		return designerExpenseDao.getEntityByHql(hql);
	}
	@Override
	public DesignerExpense getDEByQuoteIdAndOperation(Long quoteId,
			String operation) {
		designerExpenseDao.getUniqueEntityByPropNames(new String[]{"quoteId","operation"}, new Object[]{quoteId,operation});
		return null;
	}
	
}
