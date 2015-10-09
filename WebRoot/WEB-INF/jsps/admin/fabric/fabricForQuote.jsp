<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<style type="text/css">

#content {
    margin-left: 0px;
    padding-bottom: 0px;
}
</style>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function resetForm(){
		$("#vcFactoryCode").val("");
		$("#htCode").val("");
		$("#vcBefModel").val("");
	}
	
	function checkForm(){
		ajaxPage(1);
	}
	function changeCode(isHtCode){
		if(1==isHtCode){
			$("#fabricQuery").hide();
			$("#htfabricQuery").show();
			$("#vcFactoryCode").val("");
			$("#vcBefModel").val("");
		}else{
			$("#fabricQuery").show();
			$("#htfabricQuery").hide();
			$("#htCode").val("");
		}
		$("#isHtCode").val(isHtCode);
	}
	
	function addFabric(){
		var $options = $("#allFabric option:selected");
	//	var vcDis = [];
	//	$options.each(function(){
	//	  var fbcode = $(this).text();
	//	  if(fbcode.indexOf("停用")>0){
	//	  	vcDis.push(fbcode);
	//	  }
	//	});
	//	if(vcDis.length>0){
	//		art.dialog({title:"温馨提示",content:vcDis.join(",")+"已停用，请重新选择",ok:true});
	//		return;
	//	}
		var selectedFbId = $("#selectedFabricHidden").val();
		var selectedFb = [];
		$options.each(function(){
		  var fbid = $(this).val();
		  var fbcode = $(this).text();
		  selectedFb.push(fbid+"="+fbcode);
		});
		
		 if(selectedFbId){
		  	selectedFbId += ";"+selectedFb.join(";");
		  }else{
		  	selectedFbId += selectedFb.join(";");
		  }
		$("#selectedFabricHidden").val(selectedFbId);
		$options.appendTo("#selectedFabric");
	}
	
	function delFabric(){
		var $options = $("#selectedFabric option:selected");
		var selectedFbId = $("#selectedFabricHidden").val();
		var selectedFbIdArray = selectedFbId.split(";");
		if(selectedFbIdArray.length==1){
			$("#selectedFabricHidden").val("");
		}else{
			$options.each(function(){
			  var fbid = $(this).val();
			  var fbcode = $(this).text();
			  var index = getIndexOfArray(fbid+"="+fbcode,selectedFbIdArray);
			  if(index!=-1){
			  	selectedFbIdArray = selectedFbIdArray.splice(index,1);
			  }
			});
			$("#selectedFabricHidden").val(selectedFbIdArray.join(";"));
		}
		
		$options.appendTo("#allFabric");
	}
	
	function getIndexOfArray(obj,array){
		for(var i=0;i<array.length;i++){
			if(array[i]==obj) return i;
		}
		return -1;
	}
	
	function submitSelected(){
		var $options = $("#selectedFabric option");
		var selectedFb = [];
		$options.each(function(){
		  var fbid = $(this).val();
		  selectedFb.push(fbid);
		});
		if(selectedFb.length==0){
			art.dialog({
					title:"温馨提示",
					content:"你还没选择产品，请先选择产品",
					okVal:"确定",
					ok:true
				});
		}else{
			var $pDocument = $(window.parent.document);
			var url = basePath+"admin/quote_ajaxQuoteFabric.Q";
			var quoteFormate = $pDocument.find("#quoteFormate").val();
			var ctax = $pDocument.find("#containTax").val();
			var isFreight = $pDocument.find("#isFreight").val();
			var trId = $pDocument.find("#quoteFabricTable tr").last().attr("id");
			var trNumber = 0;
			if(trId){
				trNumber = trId.substr(2);
			}
			//var trNumber = $pDocument.find("#quoteFabricTable tr").last().attr("id").substr(2);
			$.ajax({
				type:"POST",
				url:url,
				data:{"fbids":selectedFb.join(","),"quoteFormate":quoteFormate,"ctax":ctax,"isFreight":isFreight,"trNumber":trNumber},
				dataType:"html",
				success:function(result){
					$pDocument.find("#quoteFabricTable").append(result);
					art.dialog.close();
				},
				error:function(){
					art.dialog({
						title:"错误提示",
						content:"发生未知错误，选择产品失败",
						okVal:"确定",
						ok:true
					});
				}
			})
		}
		
	}
</script>
</head>
<body>
<div id="content">

<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/fabric_getFabricForQuote.Q" method="post" id="queryForm">
          <input type="hidden" name="selectedFabricHidden" value="" id="selectedFabricHidden">
           <input type="hidden" name="quoteFormate" value="${quoteFormate }" id="quoteFormate">
            <input type="hidden" name="isHtCode" value="${isHtCode }" id="isHtCode">
            <div class="controls">
            	<label class="span1" for="inputSuccess" style="margin-top:5px;width:56px;">编码</label>
	            <select name="isHtCodeSelect" id="isHtCodeSelect" style="width:156px;float:left;" onchange="changeCode(this.value);">
	             	<option value="0" <s:if test="#request.isHtCode==0">selected</s:if> >原厂型号</option>
                  	<option value="1" <s:if test="#request.isHtCode==1">selected</s:if> >HT型号</option>
	             </select>
	             <span id="fabricQuery" style="width:350px;">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">工厂代码</label>
	            <input name="vcFactoryCode" id="vcFactoryCode" type="text" class="span2"  value="${ vcFactoryCode}" style="float: left;">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">原厂型号</label>
	            <input name="vcBefModel" id="vcBefModel" type="text" class="span2"  value="${vcBefModel }">
	            </span>
	             <div id="htfabricQuery" style="display:none">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">HT型号</label>
	            <input name="htCode" id="htCode" type="text" class="span2"  value="${ htCode}">
	            </div>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="button" class="btn btn-success" style="margin-left:355px;" onclick="checkForm();">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>产品列表</h5>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="listForQuote.jsp"%>
      </div>
       <div class="clear"></div>
      <div class="controls">
	            <button type="button" class="btn btn-success" style="margin-left:355px;" onclick="submitSelected();">确定</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="art.dialog.close();">关闭</button>
	  </div>
</div>
</div>

</body>
</html>
