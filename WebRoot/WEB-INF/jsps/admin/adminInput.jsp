<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="mm/js/select2.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('select').select2();
});
	function checkAdminForm(){
		var username = $("#username").val();
		var roleIds = $("#roleIds").val();
		var phone = $("#phone").val();
		if(!username){
			$("#loginNameSpan").show();
			return;
		}
		if(!roleIds){
			$("#roleSpan").show();
			return;
		}
		if(phone){
			if(phone.length>11){
				$("#phoneSpan").show();
				return;
			}
		}
		var adminId = $("#adminId").val();
		if(adminId){
			$("#saveButton").attr("disabled", "disabled");
			$("#adminForm").submit();
		}else{
			$.ajax({
				url:"/wfsc/admin/admin_isExitAdmin.Q",
				data:{"username":username},
				dataType:'text',
				success:function(data){
					if("ok"==data){
						$("#saveButton").attr("disabled", "disabled");
						$("#adminForm").submit();
					}else{
						$("#loginNameSpan").html("该用户名已存在！");
						$("#loginNameSpan").show();
						return;
					}
				}
			})
		}
	}
	function toAdminList(){
		window.location.href = "/wfsc/admin/admin_manager.Q";
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="/wfsc/admin/admin_saveAdmin.Q" method="post" class="form-horizontal" id="adminForm">
          	<input type="hidden" name="admin.id" id="adminId" value="${admin.id }">
            <div class="control-group">
              <label class="control-label">用户名&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="username" class="span5" name="admin.username" value="${admin.username }" <s:if test="#request.admin.id!=null && #request.admin.id>0">readonly=readonly</s:if> />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="loginNameSpan">用户名不能为空！</span>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">中文名&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="zhname" class="span5" name="admin.zhname" value="${admin.zhname }"  />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">地区&nbsp;&nbsp;</label>
              <div class="controls">
                <select id="area" class="span5" name="admin.area">
                	<option value="GZ" <s:if test="#request.admin.area=='GZ'">selected</s:if> >广州</option>
                	<option value="SH" <s:if test="#request.admin.area=='SH'">selected</s:if> >上海</option>
                	<option value="BJ" <s:if test="#request.admin.area=='BJ'">selected</s:if> >北京</option>
                	<option value="SZ" <s:if test="#request.admin.area=='SZ'">selected</s:if> >深圳</option>
                	<option value="HK" <s:if test="#request.admin.area=='HK'">selected</s:if> >香港</option>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="areaSpan">请选择一个地区！</span>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">角色&nbsp;&nbsp;</label>
              <div class="controls">
                <select multiple class="span5" id="roleIds" name="roleIds">
                	<s:iterator value="#request.roles" var="role">
                	<option value="${role.id}" <s:if test="#role.ck==1">selected</s:if> >${role.roleName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="roleSpan">请至少选择一个角色！</span>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">用户组&nbsp;&nbsp;</label>
              <div class="controls">
                <select multiple class="span5" id="groupIds" name="groupIds">
                	<s:iterator value="#request.groups" var="group">
                	<option value="${group.id}" <s:if test="#group.ck==1">selected</s:if> >${group.groupName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="groupSpan">请至少选择一个用户组！</span>
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">手机&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" class="span5" name="admin.phone" value="${admin.phone }" id="phone"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="phoneSpan">手机长度不能大于11位！</span>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">邮箱&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" class="span5" name="admin.email" value="${admin.email }"/>&nbsp;&nbsp;
              </div>
            </div>
            <s:if test="#request.admin.id!=null">
             <div class="control-group">
              <label class="control-label">密码&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" class="span5" name="admin.password" value="${admin.password }"/>&nbsp;&nbsp;
              </div>
            </div>
            </s:if>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkAdminForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toAdminList();">返回</button>
            </div>
          </form>
          <s:if test="#request.admin.id==null || #request.admin.id<1">
          <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="#">×</a>
              <h4 class="alert-heading">注意!</h4><br>
             保存后用户状态默认为启用状态，密码默认初始为11111111，需要改密码请前往修改密码。
          </div>
          </s:if> 
        </div>
      </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
