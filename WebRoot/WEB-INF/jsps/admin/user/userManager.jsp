<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function resetForm(){
		$("#nickName").val("");
		$("#status").val("");
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
						url:"/wfsc/admin/user_deleteByIds.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="/wfsc/admin/user_manager.Q";
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
						url:"/wfsc/admin/user_disableAccount.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="/wfsc/admin/user_manager.Q";
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
						url:"/wfsc/admin/user_enableAccount.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								var url = "/wfsc/admin/user_manager.Q";
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
<input type="hidden" id="tab" value="m6"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="/wfsc/admin/user_manager.Q" method="post" id="userQueryForm">
            <div class="controls controls-row">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">昵称:</label>
	            <input name="nickName" id="nickName" type="text" class="span2" style="margin-left:5px;margin-right:35px" value="${ nickName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">状态：</label>
	             <select name="status" id="status" style="margin-left:5px;margin-right:35px;width:156px">
	            	 <option value="">请选择</option>
	             	<option value="0" <s:if test="#request.status==0">selected</s:if> >禁用</option>
                  	<option value="1" <s:if test="#request.status==1">selected</s:if>>启用</option>
	             </select>
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>普通会员列表</h5>
            
            <button class="label label-info btn btn-primary btn-mini" onclick="delAdmin();">删除</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="disableAccount();">禁用</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="enableAccount();">启用</button>
         <!--   <button class="label label-info btn btn-primary btn-mini">导出</button> -->
         </div>
         <div class="widget-content nopadding" id="userTableId">
            <%@include file="userList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
