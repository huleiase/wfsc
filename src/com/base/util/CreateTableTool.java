package com.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wfsc.common.bo.bym.QuoteFabricReport;

public class CreateTableTool {
	/**   
     * 根据表名称 和 实体对象 创建一张表 ,已排除id和使用 final 和 static修饰的 字段,其中id已做特殊处理，所以排除。
     * 对于String类型默认全部存储为varchar(255),所以需要存储为text类型的还是需要自己手动改一下
     * @param tableName   表名
     * @param obj 实体对象 
     * @param noCol 不需要生成的列   
     */    
    @SuppressWarnings("unchecked")  
    public static String createTable(String tableName,Object obj, List<String> noCol){  
    	List<String> foreignKey = new ArrayList<String>();
        StringBuffer sb = new StringBuffer("DROP TABLE IF EXISTS `"+tableName+"`;");    
        sb.append("\n");
        sb.append("CREATE TABLE `" + tableName + "` (");
        sb.append("\n");
        sb.append("`id` bigint(20) NOT NULL AUTO_INCREMENT,");  
        sb.append("\n");
        Class c = obj.getClass();  
        Field field[] = c.getDeclaredFields();  
        for (Field f : field) {  
            if("id".equals(f.getName()) || noCol.contains(f.getName()) || Modifier.isStatic(f.getModifiers())|| Modifier.isFinal(f.getModifiers())){  
               continue;
            }else{
            	 String type = f.getType().toString();  
                 if(type.equals("class java.lang.String")){// String  
                      sb.append("`" + f.getName() + "` varchar(255) DEFAULT NULL,");    
                      sb.append("\n");
                 }else if(type.equals("int") || type.equals("class java.lang.Integer")){// int  
                     sb.append("`" + f.getName() + "` int(10) ,");     
                     sb.append("\n");
                 }else if(type.equals("double") || type.equals("class java.lang.Double")){// double  
                     sb.append("`" + f.getName() + "` DECIMAL(10,3) DEFAULT 0,"); 
                     sb.append("\n");
                 }else if(type.equals("float") || type.equals("class java.lang.Float")){// float  
                     sb.append("`" + f.getName() + "` DECIMAL(10,3) DEFAULT 0,"); 
                     sb.append("\n");
                 }else if(type.equals("long") || type.equals("class java.lang.Long")){// long  
                     sb.append("`" + f.getName() + "` bigint(20) ,");     
                     sb.append("\n");
                 }else if(type.equals("boolean") || type.equals("class java.lang.Boolean")){// boolean  
                     sb.append("`" + f.getName() + "` bit,");     
                     sb.append("\n");
                 }else if(type.equals("class java.util.Date")){// Date  
                     sb.append("`" + f.getName() + "` datetime DEFAULT NULL,");     
                     sb.append("\n");
                 }else{	//否则就当外键处理
                	 sb.append("`" + f.getName() + "Id` bigint(20) ,");     //外键==字段名+Id
                     sb.append("\n");
                     foreignKey.add(f.getName());
                 }        
            }  
        }  
        sb.append(" PRIMARY KEY (`id`)");
        if(foreignKey.size()>0){
        	sb.append(",");
        	sb.append("\n");
        }
        for(int i=0;i<foreignKey.size();i++){
        	String fkName = "FK"+new Date().getTime();
        	 sb.append(" CONSTRAINT "+fkName+" FOREIGN KEY("+foreignKey.get(i)+"Id) REFERENCES bym_"+foreignKey.get(i)+"(id)");
        	 if(i!=foreignKey.size()-1){
         		sb.append(",");
         		sb.append("\n");
         	}
        	 
        }
        sb.append("\n");
        sb.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8;");     
        
        return sb.toString();     
    }   
    
    public static String insertSelect(String targertTable,String sourceTable,Class ct,List<String> sCol,String where){
    	//INSERT INTO temp(doId,vcOldPriceTotal,cbTotal) SELECT doId,SUM(vcOldPriceTotal),SUM(amountrmb) from bym_qf_report WHERE isReplaced='0' GROUP BY doId;
    	StringBuffer sb = new StringBuffer("INSERT INTO ");
    	sb.append(targertTable).append(" (");
        Field[] fieldT = ct.getDeclaredFields();  
        for (int i=0;i<fieldT.length;i++) { 
        	Field f = fieldT[i];
        	if("id".equals(f.getName()) || Modifier.isStatic(f.getModifiers())|| Modifier.isFinal(f.getModifiers())){  
                continue;
             }else{
            	 if(i%5==0){
            		 sb.append("\n");
            	 }
            	 if(i==fieldT.length-1){
            		 sb.append(f.getName()).append(")");
            	 }else{
            		 sb.append(f.getName()).append(",");
            	 }
             	
             }
        }
        sb.append("\n");
        sb.append(" select");
        for (int i=0;i<sCol.size();i++) { 
        	String f = sCol.get(i);
        	if("id".equals(f)){  
                continue;
             }else{
            	 if(i==sCol.size()-1){
            		 sb.append(f);
            	 }else{
            		 sb.append(f).append(",");
            	 }
             	
             }
        }
        sb.append(" from ").append(sourceTable).append("\n").append(where);
    	return sb.toString();
    }
    
    /**
     * 在控制台打印建表语句
     * @param args
     */
    public static void main(String[] args){
    	/*//不需要存储的字段，默认已排除使用 final 和 static修饰的 字段
    	List<String> noCol = new ArrayList<String>();
    	//需要为该实体对象生成建表语句
    	QuoteFabricReport obj = new QuoteFabricReport();
    	
    	//表名默认为wf_类名的小写
    	String className = obj.getClass().getName().toLowerCase();
    	String tableName = "bym_"+className.substring(className.lastIndexOf(".")+1);
    	String str = createTable(tableName,obj,noCol);*/
    	
    	
    	//QuoteFabric,QuoteFabricReport
    	String str = insertSelect("bym_qf_report","quote_fabric",QuoteFabricReport.class,new ArrayList(),"");
    	
    	System.out.println(str);
    	
    }

}
