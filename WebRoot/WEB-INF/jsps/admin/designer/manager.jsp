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
		$("#codeName").val("");
		$("#designerName").val("");
	}
	function addOrUpdate(id){
		var url = "<%=basePath%>admin/designer_input.Q";
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
						url:"<%=basePath%>admin/designer_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/designer_manager.Q";
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

	function importExcel(){
		var url = "<%=basePath%>admin/designer_toImport.Q";
		art.dialog.open(url, {title: '设计师导入', lock: true, drag:true, width: getClientWidth()*0.8, height: getClientHeight()*0.85});
	}
	
	function exportExcel(){
		var param = $("#queryForm").serialize();
		var url =  "<%=basePath%>admin/designer_exportDesignerData.Q";
		if(param){
			url+="?"+param;
		}
		window.location.href = url;
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
          <form action="admin/designer_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">编号</label>
	            <input name="codeName" id="codeName" type="text" class="span2"  value="${ codeName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">合作方</label>
	            <input name="designerName" id="designerName" type="text" class="span2"  value="${designerName }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>设计师列表</h5>
             <w:permission permissionId="<%=PermissionId.BASIC_DESIGNER_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            </w:permission>
             <w:permission permissionId="<%=PermissionId.BASIC_DESIGNER_MGT_ADD%>">
         	 <button class="label label-info btn btn-primary btn-mini" onclick="importExcel();">导入</button>
         	 </w:permission>
         	  <w:permission permissionId="<%=PermissionId.BASIC_DESIGNER_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            </w:permission>
             <w:permission permissionId="<%=PermissionId.BASIC_DESIGNER_MGT_DOWNLOAD%>">
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
