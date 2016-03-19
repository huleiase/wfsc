package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.DesignerOrder;
import com.wfsc.daos.bym.DesignerOrderDao;
import com.wfsc.services.bym.service.IDesignerOrderService;

@Service("designerOrderService")
@SuppressWarnings("unchecked")
public class DesignerOrderServiceImpl implements IDesignerOrderService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private DesignerOrderDao designerOrderDao;

	public Page<DesignerOrder> findForPage(Page<DesignerOrder> page, Map<String,Object> paramap){
		return designerOrderDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		designerOrderDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(DesignerOrder entity){
		designerOrderDao.saveOrUpdateEntity(entity);
	}
	public DesignerOrder getDesignerOrderById(Long id){
		return designerOrderDao.getEntityById(id);
	}
	
	@Override
	public List<DesignerOrder> getAll() {
		return designerOrderDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<DesignerOrder> list) {
		designerOrderDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<DesignerOrder> getDesignerOrderByPara(Map<String, Object> paramap) {
		return designerOrderDao.getDesignerOrderByPara(paramap);
	}
	@Override
	public DesignerOrder getDesignerOrderByOrderId(Long orderId) {
		List<DesignerOrder> dos =  designerOrderDao.getEntitiesByOneProperty("orderId", orderId);
		if(CollectionUtils.isNotEmpty(dos)){
			return dos.get(0);
		}
		return new DesignerOrder();
	}
	public List<DesignerOrder> getDesignerOrderByOrderNo(String orderNo){
		return designerOrderDao.getEntitiesByOneProperty("orderNo", orderNo);
	}
	@Override
	public List<DesignerOrder> getDesignerOrderByQuoteId(Long quoteId) {
		String hql = "from DesignerOrder where quoteId="+quoteId+" order by id desc";
		return designerOrderDao.getEntityByHql(hql);
	}
}
