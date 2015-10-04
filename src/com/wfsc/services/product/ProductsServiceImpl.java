package com.wfsc.services.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.exception.CupidRuntimeException;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductRecommend;
import com.wfsc.common.bo.product.Products;
import com.wfsc.daos.product.ProductRecommendDao;
import com.wfsc.daos.product.ProductsDao;

@Service("productsService")
public class ProductsServiceImpl implements IProductsService {
	@Autowired
	private ProductsDao productDao;
	@Autowired
	private ProductRecommendDao productRecommendDao;

	@Override
	public void saveOrUpdateEntity(Products products) {
		productDao.saveOrUpdateEntity(products);
	}

	@Override
	public void deleteByIds(List<Long> ids) {
		productDao.deletAllEntities(ids);

	}

	@Override
	public void deleteById(Long productsId) {
		productDao.deleteEntity(productsId);
	}

	@Override
	public Page<Products> findForPage(Page<Products> page,
			Map<String, Object> paramap) {
		return productDao.findForPage(page, paramap);
	}
	
	@Override
	public Page<Products> findPage(Page<Products> page,
			Map<String, Object> paramap) {
		return productDao.findPage(page, paramap);
	}

	@Override
	public List<Products> getAll() {
		return productDao.getAllEntities();
	}

	@Override
	public Products getById(Long productsId) {
		return productDao.getEntityById(productsId);
	}

	@Override
	public List<Products> findByCatCode(String catCode) {
		return productDao.findByCatCode(catCode);
	}

	@Override
	public List<Products> findByRecommend() {
		return productDao.findByRecommend();
	}

	@Override
	public List<ProductRecommend> queryAllProductRecommend() {
		return productRecommendDao.queryAll();
	}
	
	public String getMaxCodeByCatCode(String prdcatCode){
		
		return productDao.getMaxCodeByCatCode(prdcatCode);
	}
	
	public Products getByPrdCode(String prdCode) {
		return productDao.getUniqueEntityByOneProperty("prdCode", prdCode);
	}

	@Override
	public Products findByCode(String code) {
		return productDao.findByCode(code);
	}

	@Override
	public List<String> findBySeachKey(String key) {
		return productDao.findBySeachKey(key);
	}

	@Override
	public Products getPrductById(Long prdId) {
		return productDao.getEntityById(prdId);
	}

	@Override
	public List<Products> findByCatCode(String catCode, int start, int limit, int sort) {
		String order = "createDate";
		String sorter = "desc";
		if(sort == 1){
			order = "prdPrice";
			sorter = "asc";
		}
		return productDao.findByCatCode(catCode, start, limit, order, sorter);
	}
	
	@Override
	public List<Products> findByCatCode(String catCode, int start, int limit, String sorter, String order){
		return productDao.findByCatCode(catCode, start, limit, order, sorter);
	}

	@Override
	public List<Products> getProductByKeyword(String keyword, int start, int limit) {
		return productDao.getProductByKeyword(keyword, start, limit);
	}

	@Override
	public List<ProductRecommend> queryRecommendByType(int type) {
		return productRecommendDao.queryRecommendByType(type);
	}

	@Override
	public void addProductRecommend(int type, String prdCode) {
		int count = productRecommendDao.countRecommendByType(type);
		if(type == 1){
			if(count >= 6){
				throw new CupidRuntimeException("最多设置6个新品推荐商品");
			}
		}else if(type == 2){
			if(count >= 8){
				throw new CupidRuntimeException("最多设置8个本周特惠商品");
			}
		}
		Products product = findByCode(prdCode);
		if(product == null){
			throw new CupidRuntimeException("商品不存在或已被删除");
		}
		ProductRecommend recommend = new ProductRecommend();
		recommend.setType(type);
		recommend.setProduct(product);
		
		productRecommendDao.saveEntity(recommend);
	}

	@Override
	public void deleteRecommendById(long id) {
		productRecommendDao.deleteEntity(id);
	}

	@Override
	public ProductRecommend getRecommendByTypeAndPrdCode(int type, String prdCode) {
		return productRecommendDao.getRecommendByTypeAndPrdCode(type, prdCode);
	}

	@Override
	public int getRecommendCount(Integer recommend, String prdCatCode) {
		String topCode = prdCatCode.substring(0, 3);
		String hql = "from Products where recommend="+recommend+" and prdCatCode like '"+topCode+"%'";
		List list = productDao.getHibernateTemplate().find(hql);
		return list==null?0:list.size();
	}

}
