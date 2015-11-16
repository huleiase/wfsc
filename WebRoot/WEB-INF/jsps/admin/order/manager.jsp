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
		$("#startTime1").val("");
		$("#endTime1").val("");
		$("#startTime2").val("");
		$("#endTime2").val("");
		$("#orderNo").val("");
		$("#supplier").val("");
		$("#isOrderConfirm").val("");
		$("#vcfrom").val("");
		$("#isShipments").val("");
		$("#expressNumber").val("");
		$("#vcModelNum").val("");
		$("#area_zh").val("");
		$("#orderNo").val("");
		$("#orderStatus").val("");
		$("#htCode").val("");
		$("#comeCode").val("");
		
		
	}
	function operOrder(id,oper){
		var url = basePath+"admin/order_input.Q?id="+id+"&oper="+oper;
		window.open(url);
	//	window.location.href = url;
	}
	
	function printOrder(id){
		art.dialog({
		    id: 'printOrder',
		    content: '请选择你要打印的模板!',
		    button: [
		        {
		            name: '内地模板',
		            callback: function () {
		            	var url = basePath+"admin/order_input.Q?id="+id+"&oper=8";
		            	window.open(url);
		                return true;
		            },
		            focus: true
		        },
		        {
		            name: '香港模板',
		            callback: function () {
		            	var url = basePath+"admin/order_input.Q?id="+id+"&oper=9";
		            	window.open(url);
		                return true;
		            }
		        },
		        {
		            name: '关闭'
		        }
		    ]
		});
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
						url:basePath+"admin/order_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href=basePath+"admin/order_manager.Q";
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

	function downloadOrder(id){
		art.dialog({
		    id: 'downloadOrder',
		    content: '请选择你要下载的模板!',
		    button: [
		        {
		            name: '内地模板',
		            callback: function () {
		            	var url = basePath+"admin/order_downOrder.Q?id="+id;
		            	window.open(url);
		                return true;
		            },
		            focus: true
		        },
		        {
		            name: '香港模板',
		            callback: function () {
		            	var url = basePath+"admin/order_downOrder2.Q?id="+id;
		            	window.open(url);
		                return true;
		            }
		        },
		        {
		            name: '关闭'
		        }
		    ]
		});
	}
	
	
	function uploadFile(id){
		var url = basePath+"admin/order_toImport.Q?orderId="+id;
		art.dialog.open(url, {title: '上传附件', lock: true, drag:true, width: getClientWidth()*0.7, height: getClientHeight()*0.38});
	}
	function downloadFile(id){
		var url = basePath+"admin/order_downloadFile.Q?orderId="+id;
		window.location.href = url;
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/order_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">订单号</label>
	            <input name="orderNo" id="orderNo" type="text" class="span2"  value="${orderNo }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">订单状态</label>
	            <select name="orderStatus" id="orderStatus" style="width:170px;float:left;">
	            	<option value="">请选择</option>
	            	<option value="0" <s:if test="#request.orderStatus==0">selected</s:if> >未提交</option>
                  	<option value="1" <s:if test="#request.orderStatus==1">selected</s:if> >已提交</option>
                  	<option value="3" <s:if test="#request.orderStatus==3">selected</s:if> >已审核</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">创建日期</label>
	             <input type="text" id="startTime1" name="startTime1"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime1}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endTime1" name="endTime1"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime1\')}'})" value="${endTime1}" class="span2" />
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">供应商名称</label>
	            <input name="supplier" id="supplier" type="text" class="span2"  value="${supplier }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">订单确认</label>
	            <select name="isOrderConfirm" id="isOrderConfirm" style="width:170px;float:left;">
	            	<option value="">请选择</option>
                  	<option value="0" <s:if test="#request.isOrderConfirm==0">selected</s:if> >未确认</option>
                  	<option value="1" <s:if test="#request.isOrderConfirm==1">selected</s:if> >已确认</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">货期</label>
	             <input type="text" id="startTime2" name="startTime2"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime2}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endTime2" name="endTime2"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime2\')}'})" value="${endTime2}" class="span2" />
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">经手人</label>
	            <input name="vcfrom" id="vcfrom" type="text" class="span2"  value="${vcfrom }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">是否发货</label>
	            <select name="isShipments" id="isShipments" style="width:170px;float:left;">
	            	<option value="">请选择</option>
                  	<option value="0" <s:if test="#request.isShipments==0">selected</s:if> >未发货</option>
                  	<option value="1" <s:if test="#request.isShipments==1">selected</s:if> >已发货</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">快递单号</label>
	             <input name="expressNumber" id="expressNumber" type="text" class="span2"  value="${expressNumber }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">原厂型号</label>
	            <input name="vcModelNum" id="vcModelNum" type="text" class="span2"  value="${vcModelNum }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;float:left;">下单地区</label>
	            <select name="area_zh" id="area_zh" style="width:170px;;float:left;">
	            	<option value="">请选择</option>
                  	<option value="广州" <c:if test="${area_zh=='广州'}">selected</c:if> >广州</option>
                  	<option value="香港" <c:if test="${area_zh=='香港'}">selected</c:if> >香港</option>
                  	<option value="北京" <c:if test="${area_zh=='北京'}">selected</c:if> >北京</option>
                  	<option value="上海" <c:if test="${area_zh=='上海'}">selected</c:if> >上海</option>
                  	<option value="深圳" <c:if test="${area_zh=='深圳'}">selected</c:if> >深圳</option>
                  	<option value="广州分销" <c:if test="${area_zh=='广州分销'}">selected</c:if> >广州分销</option>
                  	<option value="北京分销" <c:if test="${area_zh=='北京分销'}">selected</c:if> >北京分销</option>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">是否完结</label>
	            <select name="isOver" id="isOver" style="width:170px;float:left;">
	            	<option value="">请选择</option>
                  	<option value="0" <s:if test="#request.isOver==0">selected</s:if> >未完结</option>
                  	<option value="1" <s:if test="#request.isOver==1">selected</s:if> >已完结</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">HT型号</label>
	            <input name="htCode" id="htCode" type="text" class="span2"  value="${htCode }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">到货型号</label>
	            <input name="comeCode" id="comeCode" type="text" class="span2"  value="${comeCode }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>订单列表</h5>
             <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_DELETE%>">
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
