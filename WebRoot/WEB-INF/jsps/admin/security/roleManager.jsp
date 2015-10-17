<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function resetForm(){
		$("#username").val("");
		$("#rolename").val("");
	}
	//$(function(){
	//	$("#addRoleBut").bind("click", function(){
	//	  window.location.href="/wfsc/admin/sec_addRole.Q";
	//	});
	//})
	function addRole(){
		window.location.href="/wfsc/admin/sec_addRole.Q";
	}
	function delRole(roleId,roleName){
		$.ajax({
					url:"/wfsc/admin/sec_checkRoleUser.Q",
					data:{"roleName":roleName},
					dataType:'text',
					success:function(data1){
						if("ok"==data1){
							var dialog = art.dialog({
							    title: '提示',
							    content: '确定要删除该角色吗！',
							    okVal:'确定',
							    ok: function(){
									  $.ajax({
										url:"/wfsc/admin/sec_delRole.Q",
										data:{"roleId":roleId},
										dataType:'text',
										success:function(data2){
											if("succ"==data2){
												window.location.href="/wfsc/admin/sec_roleManager.Q";
											}else{
												art.dialog({
													time: 2,
												    content: '删除失败！'
												});
											}
										}
									 })
							   	  	return true;
							   },
							   cancelVal: '取消',
					    	   cancel: true
							});
						}else{
							art.dialog({
								time: 3,
							    content: '该角色下还有用户，不能删除！'
							});
						}
					}
		})
		
		
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
	<div class="container-fluid">
	      <div class="widget-box">
	      <div class="widget-title"> <span class="icon"> <i class="icon-move"></i> </span>
            <h5>角色列表</h5>
            <button id="addRoleBut" class="label label-info btn btn-primary btn-mini" onclick="addRole();">新增</button>
          </div>
	      	<s:iterator value="#request.allRoles" var="role">
	          <div class="widget-content nopadding updates">
	            <div class="new-update clearfix"><i class="icon-ok-sign"></i>
	              <div class="update-done">
	              	<a href="/wfsc/admin/sec_addRole.Q?roleId=<s:property value="id"/>" title="点击查看或编辑"><strong><s:property value="roleName"/> </strong></a> 
	              	<span><s:property value="roleDescription"/></span> 
	              </div>
	               <s:if test="#role.deletable==true"> <div class="update-date"><span class="update-day"><a class="label btn btn-danger btn-mini" onclick="delRole('<s:property value="id"/>','<s:property value="roleName"/>');">删除</a></span></div></s:if>
	            </div>
	          </div>
	      	</s:iterator>
	      
	      </div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
