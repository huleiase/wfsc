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
		$("#startTime").val("");
		$("#endTime").val("");
		$("#vcProject").val("");
		$("#vcModelNum").val("");
		$("#orderNo").val("");
		$("#qsurplus").val("");
	}
	function addOrUpdate(id){
		var url = "<%=basePath%>admin/storeFabric_input.Q";
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
						url:"<%=basePath%>admin/storeFabric_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/storeFabric_manager.Q";
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

	function exportExcel(){
		var param = $("#queryForm").serialize();
		var url =  "<%=basePath%>admin/storeFabric_exportStoreFabricData.Q";
		if(param){
			url+="?"+param;
		}
		window.location.href = url;
	}
	
	function transfer(id){
		var url =  "<%=basePath%>admin/storeFabric_transfer.Q?id="+id;
		window.open(url);
	}
	function shipments(id){
		var url =  "<%=basePath%>admin/storeFabric_transfer.Q?shipments=1&id="+id;
		 window.open(url);
	}
	
	function uploadFile(factoryCode,befModel){
		var url = basePath+"admin/order_toImport.Q?factoryCode="+factoryCode+"&befModel="+befModel;
		art.dialog.open(url, {title: '上传附件', lock: true, drag:true, width: getClientWidth()*0.7, height: getClientHeight()*0.38});
	}
	function downloadFile(factoryCode,befModel){
		var url = "<%=basePath%>admin/order_downloadFile.Q?factoryCode="+factoryCode+"&befModel="+befModel;
		window.location.href = url;
	}
	function record(id){
		window.open('${ctx}/security/system-log.jspx?sfid='+id);
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
          <form action="admin/storeFabric_manager.Q" method="post" id="queryForm">
          <input type="hidden" id="vcModelNum" value="${vcModelNum }" name="vcModelNum">
          <input type="hidden" id="vcFactoryCode" value="${vcFactoryCode }" name="vcFactoryCode">
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>库存产品列表</h5>
            <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            </w:permission>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="recordList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
