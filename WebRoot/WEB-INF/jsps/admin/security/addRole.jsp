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
	function checkRoleForm(){
		var rolename = $("#roleName").val();
		if(!rolename){
			$("#errorDiv").show();
			return;
		}
		var selectCheckbox=$("input[type=checkbox][name=permissionIds]:checked");
		if(selectCheckbox.length<1){
			$("#errorMsg").html("至少选择一个权限");
			$("#errorDiv").show();
			return;
		}
		var roleid = $("#roleId").val();
		if(roleid){
			$("#roleForm").submit();
		}else{
			$.ajax({
				url:"/wfsc/admin/sec_isExitRole.Q",
				data:{"roleName":rolename},
				dataType:'text',
				success:function(data){
					if("ok"==data){
						$("#roleForm").submit();
					}else{
						$("#errorMsg").html("该角色名已存在！");
						$("#errorDiv").show();
						return;
					}
				}
			})
		}
		
		
		
	}
	function selectAll(cunrretObj){
		var selectCheckbox=$("input[type=checkbox][name=permissionIds]");
		selectCheckbox.each(function(){
			$(this).attr("checked",$(cunrretObj).checked);
		});
	}
	function goback(){
		window.history.go(-1);
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub72"/>

<div id="content">
	<div id="content-header">
	    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
	</div>
	<div class="container-fluid">
	    <div class="widget-box">
	      <div class="widget-title"> <span class="icon"> <i class="icon-move"></i> </span>
	      <s:if test="role.id==0">
            <h5>新增角色</h5>
           </s:if>
           <s:else>
            <h5>编辑角色</h5>
           </s:else>
          </div>
           <div class="widget-content nopadding">
            <div class="alert alert-error" style="display:none" id="errorDiv">
	              <button class="close" data-dismiss="alert">×</button>
	              <strong id="errorMsg">角色名称必填!</strong>  
	         </div>
          	 <form action="/wfsc/admin/sec_saveRole.Q" method="post" class="form-horizontal" id="roleForm">
          	 	<input id="roleId" type="hidden" name="role.id" value="${role.id }">
	            <div class="control-group">
	              <label class="control-label">角色名称 &nbsp;&nbsp;</label>
	              <div class="controls">
	                <input type="text" class="span6" name="role.roleName" id="roleName" value="${role.roleName }" <s:if test="#request.role.id!=null && #request.role.id>0">readonly=readonly</s:if> />
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">描述&nbsp;&nbsp;&nbsp;&nbsp;</label>
	              <div class="controls">
	                <textarea class="span6" name="role.roleDescription">${role.roleDescription }</textarea>
	              </div>
	            </div>
	            <div class="widget-title"> <span class="icon"><!-- <input type="checkbox" name="pemissionAll" id="pemissionAll" onclick="selectAll(this);"/> </span>
	            	<h5>全选</h5>
	            --></div>
	            <s:iterator value="#request.allPerms" var="perm">
	             <s:if test="#perm.parentPermission==null">
		          <div class="widget-content updates">
		            <div class="new-update clearfix">
		              <div class="update-done">
		              	<input type="checkbox" name="permissionIds" value='<s:property value="id"/>' <s:if test="#perm.ck==1">checked=true</s:if> />&nbsp;&nbsp;
		              	<strong><s:property value="permissionName"/> </strong> 
		              </div>
		            </div>
		             <s:iterator value="#perm.subPermissions" var="subperm">
		             <div class="new-update clearfix" style="margin-left: 25px">
		              <div class="update-done">
		              	<input type="checkbox" name="permissionIds" value='<s:property value="id"/>' <s:if test="#subperm.ck==1">checked=true</s:if> />&nbsp;&nbsp;
		              	<strong><s:property value="permissionName"/> </strong> 
		              </div>
		              
		               <s:iterator value="#subperm.subPermissions" var="subsubperm">
		               <div class="update-done">
		               &nbsp;&nbsp;	<input type="checkbox" name="permissionIds" value='<s:property value="id"/>' <s:if test="#subsubperm.ck==1">checked=true</s:if> />&nbsp;&nbsp;
		              	<strong><s:property value="permissionName"/> </strong> 
		               </div>
		               </s:iterator>
		              
		            </div>
		            </s:iterator>
		          </div>
		          </s:if> 
		      	</s:iterator>
	            
	            <div class="form-actions">
	              <button type="button" class="btn btn-success" onclick="checkRoleForm();">保存</button>&nbsp;
	              <button type="button" class="btn btn-success" onclick="goback();">返回</button>
	            </div>
             </form>
            </div>
            
	      	
	    </div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsps/admin//common/adminFooter.jsp" />
</body>
</html>
