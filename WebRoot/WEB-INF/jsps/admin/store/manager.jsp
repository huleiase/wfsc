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
	function addOrUpdate(id){
		var url = "<%=basePath%>admin/store_input.Q";
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
						url:"<%=basePath%>admin/store_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/store_manager.Q";
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

	function importExcel(id){
		var url = "<%=basePath%>admin/store_toImport.Q?storeId="+id;
		art.dialog.open(url, {title: '库存导入', lock: true, drag:true, width: getClientWidth()*0.8, height: getClientHeight()*0.85});
	}
	
	function showStoreFabric(id){
		var url = "<%=basePath%>admin/storeFabric_manager.Q?storeId="+id;
		window.open(url);
	}
	
	function transferAll(oldStoreId){
		var selectHtml = "";
		$.ajax({
			url:"<%=basePath%>admin/store_queryAllStore.Q",
			async:false,
			dataType:'html',
			success:function(data){
				selectHtml = data;
			}
		})
		art.dialog({
			title: '转移到仓库',
			content: selectHtml,
			ok: function () {
				var newStoreId = $('#storeSelect').val();
				$.ajax({
						url:"<%=basePath%>admin/store_transferAll.Q",
						dataType:"html",
						data:{'storeId':newStoreId,'oldStoreId':oldStoreId},
						success:function(data){
							if(data){
								art.dialog({title:"提示",content:"转移成功",ok:true});
							}else{
								art.dialog({title:"提示",content:"转移失败",ok:true});
							}
							
						}
				});
				return true;
			}
		});
		
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>仓库列表</h5>
            <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            </w:permission>
            <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
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
