<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.constants.CupidStrutsConstants"%>
<%
	String going_to = (String) session.getAttribute(CupidStrutsConstants.GOTO_URL_KEY);

	if (going_to != null) {
		response.sendRedirect(going_to);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录成功</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  
  <body>
  </body>
</html>
