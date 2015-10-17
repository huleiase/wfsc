<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
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
		$("#companyName").val("");
		$("#linkman").val("");
	}
	function addOrUpdate(id){
		var url = "<%=basePath%>admin/designCompany_input.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function deleteByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条记录删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的记录吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=basePath%>admin/designCompany_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/designCompany_manager.Q";
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
			    cancel: true 
			});
		}
	}
	function disableByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条记录停用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			 art.dialog({
			    content: '你确定要禁用选中的记录吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=basePath%>admin/designCompany_disableByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/designCompany_manager.Q";
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
			    cancel: true 
				});
		 }
		}
		function enableByIds(){
			var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条记录启用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要启用选中的记录吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=basePath%>admin/designCompany_enableByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								var url = "<%=basePath%>admin/designCompany_manager.Q";
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
			    cancel: true 
			});
		}
	}
	function importExcel(){
		var url = "<%=basePath%>admin/designCompany_toImport.Q";
		art.dialog.open(url, {title: '客户导入', lock: true, drag:true, width: getClientWidth()*0.8, height: getClientHeight()*0.85});
	}
	
	function exportExcel(){
		var param = $("#queryForm").serialize();
		var url =  "<%=basePath%>admin/designCompany_exportDesignCompanyData.Q";
		if(param){
			url+="?"+param;
		}
		window.location.href = url;
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/designCompany_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:56px;">公司名</label>
	            <input name="companyName" id="companyName" type="text" class="span2"  value="${ companyName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:56px;">联系人</label>
	            <input name="linkman" id="linkman" type="text" class="span2"  value="${linkman }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:56px;">状态</label>
	            <select name="dis" id="dis" style="width:156px">
	            	<option value="">请选择</option>
	             	<option value="停用" <s:if test="#request.dis=='停用'">selected</s:if> >停用</option>
                  	<option value="启用" <s:if test="#request.dis=='启用'">selected</s:if> >启用</option>
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
            <h5>供应商列表</h5>
             <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            </w:permission>
            <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT_ADD%>">
         	 <button class="label label-info btn btn-primary btn-mini" onclick="importExcel();">导入</button>
         	 </w:permission>
         	 <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="disableByIds();">禁用</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="enableByIds();">启用</button>
             </w:permission>
              <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT_DOWNLOAD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="exportExcel();">导出</button> 
            </w:permission>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="list.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
