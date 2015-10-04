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
		var url = "<%=basePath%>admin/storeFabric_toImport.Q?factoryCode="+factoryCode+"&befModel="+befModel;
		art.dialog.open(url, {title: '上传附件', lock: true, drag:true, width: getClientWidth()*0.7, height: getClientHeight()*0.38});
	}
	function downloadFile(factoryCode,befModel){
		var url = "<%=basePath%>admin/storeFabric_downloadFile.Q?factoryCode="+factoryCode+"&befModel="+befModel;
		window.location.href = url;
	}
	function record(factoryCode,befModel){
		var url =  "<%=basePath%>admin/storeFabric_recordManager.Q?vcModelNum="+befModel+"&vcFactoryCode="+factoryCode;
		 window.open(url);
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
          <input type="hidden" id="storeId" value="${storeId }" name="storeId">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">订单号</label>
	            <input name="orderNo" id="orderNo" type="text" class="span2"  value="${orderNo }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">剩余数量</label>
	            <select name="qsurplus" id="qsurplus" style="width:170px;float:left;">
	            	<option value="">请选择</option>
                  	<option value="0" <s:if test="#request.qsurplus==0">selected</s:if> >为零</option>
                  	<option value="1" <s:if test="#request.qsurplus==1">selected</s:if> >不为零</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">入库日期</label>
	             <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="span2" />
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">项目名称</label>
	            <input name="vcProject" id="vcProject" type="text" class="span2"  value="${vcProject }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">型号</label>
	             <input name="vcModelNum" id="vcModelNum" type="text" class="span2"  value="${vcModelNum }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>库存产品列表</h5>
            <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            </w:permission>
            <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_DOWNLOAD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="exportExcel();">导出</button> 
            </w:permission>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
         <s:if test="#request.permission==0">
            <%@include file="lesslist.jsp"%>
            </s:if>
            <s:else>
             <%@include file="list.jsp"%>
            </s:else>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
