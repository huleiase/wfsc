package com.wfsc.daos.bym;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.bym.Attachment;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("attachmentDao")
public class AttachmentDao extends EnhancedHibernateDaoSupport<Attachment> {


	@Override
	protected String getEntityName() {
		return Attachment.class.getName();
	}
	
	
}