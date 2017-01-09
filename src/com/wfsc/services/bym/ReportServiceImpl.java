package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wfsc.daos.bym.QuoteFabricReportDao;
import com.wfsc.services.bym.service.IReportService;
@Service("reportService")
public class ReportServiceImpl implements IReportService {
	@Resource
	private QuoteFabricReportDao quoteFabricReportDao;
	@Override
	public List<Map<String, Object>> getReportDatas(Map<String, String> paramMap) {
		return quoteFabricReportDao.getReportDatas(paramMap);
	}
	@Override
	public List<Map<String, Object>> getQFRByAreaAndMounth(String mouth,String local,int limit,String vcModelNum) {
		return quoteFabricReportDao.getQFRByAreaAndMounth(mouth, local, limit, vcModelNum);
	}
	
	

}
