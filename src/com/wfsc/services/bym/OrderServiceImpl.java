package com.wfsc.services.bym;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Order;
import com.wfsc.daos.bym.OrderDao;
import com.wfsc.services.bym.service.IOrderService;

@Service("orderService")
@SuppressWarnings("unchecked")
public class OrderServiceImpl implements IOrderService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private OrderDao orderDao;

	public Page<Order> findForPage(Page<Order> page, Map<String,Object> paramap){
		return orderDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		orderDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Order entity){
		orderDao.saveOrUpdateEntity(entity);
	}
	public Order getOrderById(Long id){
		return orderDao.getEntityById(id);
	}
	
	@Override
	public List<Order> getAll() {
		return orderDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(List<Order> list) {
		orderDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Order> getOrderByPara(Map<String, Object> paramap) {
		return orderDao.getOrderByPara(paramap);
	}
	@Override
	public List<Order> getOrderByPurchaseId(Long purchaseId) {
		return orderDao.getEntitiesByOneProperty("purchase.id", purchaseId);
	}
	@Override
	public synchronized long getCurrentOrderNum(String area, String tbYearMonth) {
		int num = 0;
		List<Order> list = orderDao.getEntitiesByPropNames(new String[]{"area","tbYearMonth"}, new Object[]{area,tbYearMonth});
		if(list!=null){
			for(Order o : list){
				String orderNo = o.getOrderNo();
				int no = Integer.valueOf(StringUtils.substring(orderNo, -3));
				if(no>num){
					num = no;
				}
			}
		}
		return num;
	}
	public List<Order> getOrderByOrderNo(String orderNo) {
		return orderDao.getEntitiesByOneProperty("orderNo", orderNo);
	}
	@Override
	public void deleteByProperty(String name, Object value) {
		orderDao.deleteEntityByProperty(name, value);
		
	}
	@Override
	public List<Order> getOrderByQuoteId(Long quoteId) {
		return orderDao.getEntitiesByOneProperty("quoteId", quoteId);
	}
	@Override
	public void deleteByEntitys(Collection<Order> orders) {
		orderDao.deleteAllEntities(orders);
		
	}
}
