<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<!--Header-part-->
<div id="header">
  <h1><a href=""></a></h1>
</div>
<!--close-Header-part--> 

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li class=""><a title="" href="<%=request.getContextPath() %>/admin/admin_modifyPwd.Q"><i class="icon icon-cog"></i> <span class="text">修改密码</span></a></li>
    <li class=""><a title="" href="<%=request.getContextPath() %>/admin/admin_logout.Q"><i class="icon icon-share-alt"></i> <span class="text">安全退出</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->
