package com.wfsc.services.bym;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.bym.Fabric;
import com.wfsc.daos.bym.FabricDao;
import com.wfsc.services.bym.service.IFabricService;

@Service("fabricService")
@SuppressWarnings("unchecked")
public class FabricServiceImpl implements IFabricService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private FabricDao fabricDao;

	public Page<Fabric> findForPage(Page<Fabric> page, Map<String,Object> paramap){
		return fabricDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids,String isHtCode) {
		StringBuffer sql = new StringBuffer("delete from bym_fabric where ");
		for(int i=0;i<ids.size();i++){
			if(i==ids.size()-1){
				sql.append(" id="+ids.get(i));
			}else{
				sql.append(" id="+ids.get(i)+" or ");
			}
		}
		System.out.println("deleteByIds==="+sql);
		int count = this.deleteBySql(sql.toString());
		System.out.println("删除的原厂型号记录数==="+count);
		if("0".equals(isHtCode)){
			StringBuffer hbsql = new StringBuffer("update bym_fabric set refid=NULL where ");
			for(int i=0;i<ids.size();i++){
				if(i==ids.size()-1){
					hbsql.append(" refid="+ids.get(i));
				}else{
					hbsql.append(" refid="+ids.get(i)+" or ");
				}
			}
			System.out.println("deleteHbByIds==="+hbsql);
			int hbcount = this.deleteBySql(hbsql.toString());
			System.out.println("因为删除原厂型号而更新的HB型号记录数==="+hbcount);
		}
	}
	public void saveOrUpdateEntity(Fabric entity){
		fabricDao.saveOrUpdateEntity(entity);
	}
	public Fabric getFabricById(Long id){
		return fabricDao.getEntityById(id);
	}
	
	public void disableFabric(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Fabric s = fabricDao.getEntityById(Long.valueOf(id));
		s.setVcDis("停用");
		fabricDao.saveOrUpdateEntity(s);
		List<Fabric> htList = getHTFabricByRefid(Long.valueOf(id));
		if(htList!=null){
			for(Fabric ht : htList){
				ht.setVcDis("停用");
				fabricDao.saveOrUpdateEntity(ht);
			}
		}
		
	}
	
	public void enableFabric(String id) {
		if (StringUtils.isEmpty(id.trim()))
			return;
		Fabric s = fabricDao.getEntityById(Long.valueOf(id));
		s.setVcDis("启用");
		fabricDao.saveOrUpdateEntity(s);
		List<Fabric> htList = getHTFabricByRefid(Long.valueOf(id));
		if(htList!=null){
			for(Fabric ht : htList){
				ht.setVcDis("启用");
				fabricDao.saveOrUpdateEntity(ht);
			}
		}
	}
	@Override
	public void disableFabrics(String[] ids) {
		for(String id : ids){
			disableFabric(id);
		}
		
	}
	@Override
	public void enableFabrics(String[] ids) {
		for(String id : ids){
			enableFabric(id);
		}
	}
	@Override
	public List<Fabric> getAll() {
		return fabricDao.getAllEntities();
	}
	@Override
	public void saveOrUpdateAll(Collection<Fabric> list) {
		fabricDao.saveOrUpdateAllEntities(list);
		
	}
	@Override
	public List<Fabric> getFabricByPara(Map<String, Object> paramap) {
		return fabricDao.getFabricByPara(paramap);
	}
	@Override
	public Page<Fabric> findHTForPage(Page<Fabric> page,
			Map<String, Object> paramap) {
		return fabricDao.findHTForPage(page, paramap);
	}
	@Override
	public Fabric getFabricByCode(String vcFactoryCode, String vcBefModel) {
		//return fabricDao.getUniqueEntityByPropNames(new String[]{"vcFactoryCode","vcBefModel","isHtCode"}, new Object[]{vcFactoryCode,vcBefModel,"0"});
		return fabricDao.getFabricByCode(vcFactoryCode, vcBefModel);
	}
	@Override
	public Fabric getHTFabricByCode(String vcFactoryCode, String vcBefModel,String htCode) {
		//return fabricDao.getUniqueEntityByPropNames(new String[]{"vcFactoryCode","vcBefModel","htCode"}, new Object[]{vcFactoryCode,vcBefModel,htCode});
		return fabricDao.getHtFabricByCode(vcFactoryCode, vcBefModel, htCode);
	}
	@Override
	public List<Fabric> getHTFabricByRefid(Long refId) {
		return fabricDao.getEntitiesByOneProperty("refid", refId);
	}
	@Override
	public List<Fabric> getHTFabricByPara(Map<String, Object> paramap) {
		return fabricDao.getHTFabricByPara(paramap);
	}
	@Override
	public List<Fabric> getAllFabric() {
		return fabricDao.getEntitiesByOneProperty("isHtCode", "0");
	}
	@Override
	public List<Fabric> getAllHTFabric() {
		return fabricDao.getEntitiesByOneProperty("isHtCode", "1");
	}
	@Override
	public List<Fabric> getFabricByHql(String hql) {
		return fabricDao.getEntityByHql(hql);
	}
	@Override
	public void deleteFabrics(List<Fabric> fs,String isHtCode) {
		List<Long> ids = new ArrayList<Long>();
		if(CollectionUtils.isNotEmpty(fs)){
			for(Fabric f : fs){
				ids.add(f.getId());
			}
		}
		this.deleteByIds(ids, isHtCode);
	}
	@Override
	public Map<String, Long> getRefMap() {
		
		return fabricDao.getRefMap();
	}
	@Override
	public Long getRefIdByCode(String vcFactoryCode, String vcBefModel) {
		return fabricDao.getRefIdByCode(vcFactoryCode, vcBefModel);
	}
	@Override
	public Page<Fabric> findForQuotePage(Page<Fabric> page,
			 String vcFactoryCode ,String vcBefModel) {
		return fabricDao.findForQuotePage(page, vcFactoryCode,vcBefModel);
	}
	@Override
	public Page<Fabric> findHTForQuotePage(Page<Fabric> page,
			String htCode) {
		return fabricDao.findHTForQuotePage(page, htCode);
	}
	@Override
	public int updateVcdis() {
		return fabricDao.updateVcdis();
	}
	
	public int deleteBySql(String sql){
		return fabricDao.deleteBySql(sql);
	}
	
	public List<Long> getHtFabricIdByIds(List<Long> ids){
		StringBuffer sql = new StringBuffer("select id from bym_fabric where ");
		if(ids!=null&&ids.size()>0){
			for(int i=0;i<ids.size();i++){
				if(i==ids.size()-1){
					sql.append(" refid="+ids.get(i));
				}else{
					sql.append(" refid="+ids.get(i)+" or ");
				}
			}
			System.out.println("getHtFabricIdByIds==="+sql);
			return fabricDao.getHtFabricIdByIds(sql.toString());
		}else{
			return new ArrayList<Long>();
		}
		
	}
	
	public List<Long> getHbIdByCode(String vcFactoryCode,String vcBefModel){
		String sql = "select id from bym_fabric where vcFactoryCode='"+vcFactoryCode+"' and vcBefModel='"+vcBefModel+"' and isHtCode='1' ";
		return fabricDao.getHtFabricIdByIds(sql.toString());
	}
	
	public int updateRefIdByHtId(List<Long> ids,Long fbid){
		StringBuffer hbsql = new StringBuffer("update bym_fabric set refid=");
		hbsql.append(fbid).append(" where ");
		for(int i=0;i<ids.size();i++){
			if(i==ids.size()-1){
				hbsql.append(" id="+ids.get(i));
			}else{
				hbsql.append(" id="+ids.get(i)+" or ");
			}
		}
		System.out.println("aaaaaaaaa="+hbsql.toString());
		int hbcount = this.deleteBySql(hbsql.toString());
		return hbcount;
	}
	
}
