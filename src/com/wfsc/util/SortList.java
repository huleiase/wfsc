package com.wfsc.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList<E>{
	@SuppressWarnings(value = "unchecked")
	public void Sort(List<E> list, final String method, final String sort){   
        Collections.sort(list, new Comparator() {              
            public int compare(Object a, Object b) {   
                int ret = 0;   
                try{   
                    Method m1 = ((E)a).getClass().getMethod(method, null);   
                    Method m2 = ((E)b).getClass().getMethod(method, null);   
                    if(sort != null && "desc".equals(sort)){//按升序或降序排序 
                    	if(m1.invoke(((E)a), null).getClass().equals(Integer.class) && m2.invoke(((E)b), null).getClass().equals(Integer.class)){
                    		if(Integer.parseInt(m1.invoke((E)a, null).toString())>Integer.parseInt(m2.invoke((E)b, null).toString())){
								ret = -1;
                    		}
                    		else{
								ret = 1;
                    		}
                    	}else{
                    		ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a), null).toString()); 
                    	}
                    }else{
                    	if(m1.invoke(((E)a), null).getClass().equals(Integer.class) && m2.invoke(((E)b), null).getClass().equals(Integer.class)){
                    		if(Integer.parseInt(m1.invoke((E)a, null).toString())>Integer.parseInt(m2.invoke((E)b, null).toString())){
								ret = 1;
                    		}
                    		else{
								ret = -1;
                    		}
                    	}else{
                    		ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString()); 
                    	}
                    }
                }catch(NoSuchMethodException ne){   
                	ne.printStackTrace();   
                }catch(IllegalAccessException ie){   
                    ie.printStackTrace();   
                }catch(InvocationTargetException it){   
                	it.printStackTrace();    
                }   
                return ret;   
            }   
         });   
    }   
}
