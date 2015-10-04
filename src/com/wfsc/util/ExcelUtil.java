package com.wfsc.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtil {
	public static final String FIELD_TYPE_STRING = "string";

	public static final String FIELD_TYPE_INTEGER = "integer";

	public static final String FIELD_TYPE_FLOAT = "float";

	public static final String FIELD_TYPE_DATE = "date";
	
	public static String getCellValueAsString(Cell cell,String type){
		if (cell == null) {
			return "";
		}
		//如果列是字符串类型 设置cell 为字符串类型  避免 类似 11 被 poi 作为 数字型 读取为 11.0
		if(StringUtils.equals(type, FIELD_TYPE_STRING)){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		String cellStr = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellStr = Boolean.toString(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			//判断是否是日期,是日期的话返回 yyyy-MM-dd hh:mm:ss格式的字符串
			if(StringUtils.equals(type, FIELD_TYPE_DATE)&& DateUtil.isCellDateFormatted(cell)){
				cellStr = com.wfsc.util.DateUtil.getLongDate(DateUtil.getJavaDate(cell.getNumericCellValue()));
			}else{
				cellStr = String.valueOf(Double.valueOf(cell.getNumericCellValue()));
			}
			break;
		case Cell.CELL_TYPE_STRING:
			cellStr = cell.getStringCellValue().replaceAll("'", "’").replaceAll("\"", "”").replaceAll("，", ",");
			break;
		}
		return StringUtils.trim(cellStr);
	}

}
