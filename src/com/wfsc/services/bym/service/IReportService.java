package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

public interface IReportService {
	List<Map<String,Object>> getReportDatas(Map<String,String> paramMap);
	
	public List<Map<String, Object>> getQFRByAreaAndMounth(String mouth,String local,int limit,String vcModelNum);
}
