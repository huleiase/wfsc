<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<style type="text/css">
.clear{
clear:both;
}
</style>
<script type="text/javascript">
	function resetForm(){
		$("#username").val("");
		$("#rolename").val("");
	}
	function updateAdmin(username){
		var url = "/wfsc/admin/admin_inputAdmin.Q";
		if(username){
			url+="?username="+username;
		}
		window.location.href = url;
	}
	function delAdmin(){
		var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"/wfsc/admin/admin_delAdmin.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="/wfsc/admin/admin_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '删除失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
			});
		}
	}
	function disableAccount(){
		var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户禁用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			 art.dialog({
			    content: '你确定要禁用选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"/wfsc/admin/admin_disableAccount.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="/wfsc/admin/admin_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '禁用失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
				});
		 }
		}
		function enableAccount(){
			var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户启用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			art.dialog({
			    content: '你确定要启用选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"/wfsc/admin/admin_enableAccount.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								var url = "/wfsc/admin/admin_manager.Q";
								window.location.href=url;
							}else{
								art.dialog({
									time: 2,
									content: '启用失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
			});
		}
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub71"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="/wfsc/admin/admin_manager.Q" method="post" id="adminQueryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:53px;">用户名</label>
	            <input name="username" id="username" type="text" class="span2"  value="${ username}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:53px;">角色</label>
	            <input name="rolename" id="rolename" type="text" class="span2"  value="${rolename }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:53px;">地区</label>
	            <select name="area" id="area" style="width:156px">
	            	<option value="">请选择</option>
	             	<option value="GZ" <s:if test="#request.area=='GZ'">selected</s:if> >广州</option>
                  	<option value="SH" <s:if test="#request.area=='SH'">selected</s:if> >上海</option>
                  	<option value="SZ" <s:if test="#request.area=='SZ'">selected</s:if> >深圳</option>
                  	<option value="BJ" <s:if test="#request.area=='BJ'">selected</s:if> >北京</option>
                  	<option value="HK" <s:if test="#request.area=='HK'">selected</s:if> >香港</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	        <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>管理员列表</h5>
             <w:permission permissionId="<%=PermissionId.SYSTEM_ADMIN_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="updateAdmin();">新增</button>
            </w:permission>
             <w:permission permissionId="<%=PermissionId.SYSTEM_ADMIN_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="delAdmin();">删除</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="disableAccount();">禁用</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="enableAccount();">启用</button>
            </w:permission>
         <!--   <button class="label label-info btn btn-primary btn-mini">导出</button> -->
         </div>
         <div class="widget-content nopadding" id="adminTableId">
            <%@include file="adminList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
