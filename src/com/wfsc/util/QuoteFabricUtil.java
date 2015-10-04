package com.wfsc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wfsc.common.bo.bym.QuoteFabric;

public class QuoteFabricUtil {
	public static List<QuoteFabric> sort(Set<QuoteFabric> qfSet,String method,String sort){
		if(qfSet==null){
			return new ArrayList<QuoteFabric>();
		}
		List<QuoteFabric> qfList = new ArrayList<QuoteFabric>(qfSet);
		SortList<QuoteFabric> sl = new SortList<QuoteFabric>();
		sl.Sort(qfList, method, sort);
		return qfList;
	}
}
