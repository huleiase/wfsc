<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
var basePath = "<%=basePath%>";
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#contractNo").val("");
		$("#orderStatus").val("");
		$("#orderNo").val("");
		$("#deliveryRequirements").val("");
		
		
	}
	function operPurchase(id,oper){
		var orderStatus = $("#orderStatus").val();
		var pageNo = $(".h2_span_on i").html();
		var pageSize = $("#pageSize").val();
		var url = basePath+"admin/purchase_input.Q?id="+id+"&oper="+oper+"&currPageNo="+pageNo+"&pageSize="+pageSize+"&orderStatus="+orderStatus;
		if(oper==3 || oper==4){
			window.open(url,"打印");
		}else{
			window.location.href = url;
		}
		
		
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
						url:basePath+"admin/purchase_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href=basePath+"admin/purchase_manager.Q";
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

	function downloadPurchase(id){
		var url = basePath+"admin/purchase_downPurchase.Q?id="+id;
		window.location.href = url;
	}
	
	
	function uploadFile(id){
		var url = basePath+"admin/purchase_toImport.Q?purchaseId="+id;
		art.dialog.open(url, {title: '上传附件', lock: true, drag:true, width: getClientWidth()*0.7, height: getClientHeight()*0.38});
	}
	function downloadFile(id){
		var url = basePath+"admin/purchase_downloadFile.Q?purchaseId="+id;
		window.location.href = url;
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/purchase_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">合同编号</label>
	            <input name="contractNo" id="contractNo" type="text" class="span2"  value="${contractNo }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">创建日期</label>
	             <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="span2" />
	         <label class="span1" for="inputSuccess" style="margin-top:5px;width:30px;">状态</label>
	            <select name="orderStatus" id="orderStatus" style="width:170px;">
	            	<option value="">请选择</option>
                  	<option value="2" <s:if test="#request.orderStatus==2">selected</s:if> >未提交</option>
                  	<option value="9" <s:if test="#request.orderStatus==9">selected</s:if> >已提交</option>
                  	<option value="3" <s:if test="#request.orderStatus==3">selected</s:if> >已审核</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">单号</label>
	            <input name="orderNo" id="orderNo" type="text" class="span2"  value="${orderNo }">
	         <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">货期要求</label>
	            <select name="deliveryRequirements" id="deliveryRequirements" style="width:170px;">
	            	<option value="">请选择</option>
                  	<option value="正常" <c:if test="${deliveryRequirements=='正常'}">selected</c:if> >正常</option>
                  	<option value="铁期" <c:if test="${deliveryRequirements=='铁期'}">selected</c:if> >铁期</option>
                  	<option value="特急" <c:if test="${deliveryRequirements=='特急'}">selected</c:if> >特急</option>
                  	<option value="急" <c:if test="${deliveryRequirements=='急'}">selected</c:if> >急</option>
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
            <h5>采购单列表</h5>
            <w:permission permissionId="<%=PermissionId.PROCESS_PURCHASE_MGT_DELETE%>">
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
