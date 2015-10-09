package com.wfsc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.wfsc.common.bo.bym.QuoteFabric;

public class QuoteFabricUtil {
	public static List<QuoteFabric> sort(Collection<QuoteFabric> qfs,String method,String sort){
		if(qfs==null){
			return new ArrayList<QuoteFabric>();
		}
		List<QuoteFabric> qfList = new ArrayList<QuoteFabric>(qfs);
		SortList<QuoteFabric> sl = new SortList<QuoteFabric>();
		sl.Sort(qfList, method, sort);
		return qfList;
	}
}
