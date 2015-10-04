package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerExpense;


public interface IDesignerExpenseService {

	public Page<DesignerExpense> findForPage(Page<DesignerExpense> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(DesignerExpense entity); 
	
	public DesignerExpense getDesignerExpenseById(Long id);
	
	/*public void disableDesignerExpenses(String[] ids);
	
	public void enableDesignerExpenses(String[] ids);
	
	public void disableDesignerExpense(String id);
	
	public void enableDesignerExpense(String id);*/
	
	public List<DesignerExpense> getAll();
	
	public void saveOrUpdateAll(List<DesignerExpense> list);
	
	public List<DesignerExpense> getDesignerExpenseByPara(Map<String,Object> paramap);
	
	public List<DesignerExpense> getDesignerExpenseByQuoteId(Long quoteId);
	
}
