package com.wfsc.daos.bym;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.bym.QuoteFabricReport;

/**
 * 
 * 供应商
 * 
 */
@SuppressWarnings("unchecked")
@Repository("quoteFabricReportDao")
public class QuoteFabricReportDao extends EnhancedHibernateDaoSupport<QuoteFabricReport> {


	@Override
	protected String getEntityName() {
		return QuoteFabricReport.class.getName();
	}
	
}