package com.wfsc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.user.User;

/**
 * session监听器,主要用来监听用户的在线状态以及在线人数统计
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public class CupidSessionListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

	/**  
     * 创建session  
     */  
    public void sessionCreated(HttpSessionEvent se) { 
        OnlineStatistics.increase();
    }
  
    /**  
     * 服务器销毁session对象  
     */  
    public void sessionDestroyed(HttpSessionEvent se) {   
        OnlineStatistics.decrease();
    }
  
    /**  
     * 用户登录时  
     */  
    public void attributeAdded(HttpSessionBindingEvent se) {   
        if (CupidStrutsConstants.SESSION_USER.equals(se.getName())) {   
            User memberInfo = (User) se.getValue();   
            OnlineStatistics.addAttr(memberInfo);   
        }
    }   
  
    /**  
     * 用户退出登录(销毁session属性时)  
     */  
    public void attributeRemoved(HttpSessionBindingEvent se) {   
        if (CupidStrutsConstants.SESSION_USER.equals(se.getName())) {   
        	User memberInfo = (User) se.getValue();
            OnlineStatistics.removeAttr(memberInfo);
            //更新用户在线状态以及上次登录时间
          //  IUserService userService= (IUserService) ServerBeanFactory.getBean("userService");
//            userService.updateOnlineStatus(memberInfo.getEmail(), false);
            memberInfo = null;
        }
    }   
  
    /**  
     * 待实现  
     */  
    public void attributeReplaced(HttpSessionBindingEvent se) {
//to be extended   
    }

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
	}  

}
