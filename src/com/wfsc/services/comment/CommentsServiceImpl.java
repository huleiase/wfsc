package com.wfsc.services.comment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.comment.Comments;
import com.wfsc.daos.comment.CommentsDao;

@Service("commentsService")
@SuppressWarnings("unchecked")
public class CommentsServiceImpl implements ICommentsService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private CommentsDao commentsDao;

	@Override
	public List<Comments> queryByPrdCode(String prdCode) {
		return commentsDao.queryByPrdCode(prdCode);
	}
	
	public Page<Comments> findForPage(Page<Comments> page, Map<String,Object> paramap){
		return commentsDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		commentsDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(Comments c){
		commentsDao.saveOrUpdateEntity(c);
	}
	public Comments getCommentsById(Long id){
		return commentsDao.getEntityById(id);
	}
	@Override
	public Page<Comments> findPage(Page<Comments> page, Map<String, Object> paramap) {
		return commentsDao.findPage(page, paramap);
	}

	@Override
	public int countByStarsAndPrdCode(Integer stars, String prdCode) {
		if (stars == 0) {
			return commentsDao.countEntitiesByPropNames("prdCode", prdCode);
		}
		String[] is = {"stars", "prdCode"};
		Object[] os = {stars, prdCode};
		return commentsDao.countEntitiesByPropNames(is, os);
	}

	@Override
	public List<Comments> getCommentsByProductId(String productCode, int start, int limit) {
		return commentsDao.getCommentsByProductId(productCode, start, limit);
	}

	@Override
	public List<Comments> getCommentsByUserId(long userId, int start, int limit) {
		return commentsDao.getCommentsByUserId(userId, start, limit);
	}

	@Override
	public Comments getCommentsByDetailId(Long detailId) {
		// TODO Auto-generated method stub
		return commentsDao.getUniqueEntityByOneProperty("orderDetailId", detailId);
	}
}
