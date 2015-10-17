<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub71"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=basePath%>admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
    <a href="<%=basePath%>admin/email_manager.Q" target="_blank" title="查看邮件信息">
    <img id="emailicon" src="<%=basePath%>images/email.png" width="30" />
    <font id="email">收件箱(0)</font>
    </a>
    </div>
</div>
<script type="text/javascript">
$(function(){init();})
  function init() {
 	        	initMsg();
 	        	setInterval('initMsg()', 60000);
 	        }
 function initMsg() {
 	        	initEmail();
 	        }
function initEmail() {
 	        	$.ajax({
	 	           		url : '<%=basePath%>admin/email_getUnreadCount.Q',
	 	           		type : 'GET',
	 	           		dataType:'text',
	 	           		success : function(rs) {
	 	           			if(rs!="0") {
		 	           			$("#email").empty();
		 	           			$("#email").append("收件箱(" + rs + ")");
		 	           			$("#email").attr("color", "red");
		 	           			$("#emailicon").attr('src', '<%=basePath%>/images/emai2_GIF.gif');
	 	           			} else {
		 	           			$("#email").empty();
		 	           			$("#email").append("收件箱(0)");
	 	           				$("#email").attr("color", "white");
	 	           				$("#emailicon").attr('src', '<%=basePath%>/images/email.png');
	 	           			}
	 	           		}
	 	        });
 	        }
	 		
</script>

