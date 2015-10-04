package com.wfsc.services.ad;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.daos.ad.AdDao;

@Service("adService")
@SuppressWarnings("unchecked")
public class AdServiceImpl implements IAdService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private AdDao adDao;

	@Override
	public List<AdvConfig> queryByType(int type) {
		List<AdvConfig> lists = adDao.getHibernateTemplate().find("from AdvConfig where adType = ? order by id", type);
		if (CollectionUtils.isEmpty(lists)) {
			return null;
		}
		return lists;
	}

	@Override
	public Page<AdvConfig> queryAllAds(Page<AdvConfig> page, int type) {
		return adDao.queryAdsForPage(page, type);
	}

	@Override
	public void deleteAdByIds(List<Long> ids) {
		adDao.deletAllEntities(ids);
	}

	@Override
	public AdvConfig getAdById(long id) {
		return adDao.getEntityById(id);
	}

	@Override
	public void saveOrUpdateAd(AdvConfig advConfig) {
		if(advConfig.getId() != null && advConfig.getId() != 0){
			AdvConfig ad = getAdById(advConfig.getId());
			if(ad == null)
				throw new CupidRuntimeException("广告不存在或者已被删除");
			else{
				//编辑不更新图片路径
				advConfig.setPicUrl(ad.getPicUrl());
			}
		}
		//普通广告最多允许存在三个
		int type = advConfig.getAdType();
		int count = adDao.countByHql("select count(*) from AdvConfig where adType = " + type);
		if(advConfig.getId() == null && type == 1 && count>= 3){
			throw new CupidRuntimeException("普通广告最多允许配置3个");
		}
		adDao.saveOrUpdateEntity(advConfig);
	}

}
