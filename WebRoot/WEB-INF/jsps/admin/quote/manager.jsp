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
<script src="js/quote.js" type="text/javascript"></script>
<script type="text/javascript">
var basePath = "<%=basePath%>";
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#projectName").val("");
		$("#vcSalesman").val("");
		$("#projectNum").val("");
		$("#vcAttn").val("");
		$("#quoteFormate").val("");
		$("#projectDesignComp").val("");
		$("#vcBefModel").val("");
		$("#curUserName").val("");
	}
	function addQuote(){
		var url = basePath+"admin/quote_input.Q";
		window.location.href = url;
	}
	function updateOrCopy(id){
			art.dialog({
		    id: 'updateOrCopy',
		    content: '请选择覆盖还是新增!',
		    button: [
		        {
		            name: '覆盖',
		            callback: function () {
		            	var url = basePath+"admin/quote_input.Q?id="+id;
		            	window.location.href = url;
		                return true;
		            },
		            focus: true
		        },
		        {
		            name: '新增',
		            callback: function () {
		            	var url = basePath+"admin/quote_input.Q?id="+id+"&isCopy=1";
		            	window.location.href = url;
		                return true;
		            }
		        },
		        {
		            name: '关闭'
		        }
		    ]
		});
	}
	
	function auditQuote(id,quoteFormate){
		var isLocalManager = $("#isLocalManager").val();
		if(isLocalManager==1&&(quoteFormate!=3||quoteFormate!=4)){
			art.dialog({
			    content: '区域经理只能审核大货价',
			    okVal:'确定',
			    ok: true
			});
			return;
		}
		if(isLocalManager!=1&&(quoteFormate==3||quoteFormate==4)){
			art.dialog({
			    content: '大货价只能由区域经理或管理员审核',
			    okVal:'确定',
			    ok: true
			});
			return;
		}
		art.dialog({
			    content: '你确定要审核该报价单吗？',
			    okVal:'确定',
			    ok: function(){
			    	window.location.href=basePath+"admin/quote_detail.Q?operate=audit&id="+id;
			    },
			    cancelVal:'取消',
			    cancel:true
			});
		
	}
	function writPerm(id,quoteFormate){
		var isLocalManager = $("#isLocalManager").val();
		if(isLocalManager==1&&(quoteFormate!=3||quoteFormate!=4)){
			art.dialog({
			    content: '区域经理只能签单大货价',
			    okVal:'确定',
			    ok: true
			});
			return;
		}
		if(isLocalManager!=1&&(quoteFormate==3||quoteFormate==4)){
			art.dialog({
			    content: '大货价只能由区域经理或管理员签单',
			    okVal:'确定',
			    ok: true
			});
			return;
		}
		art.dialog({
			    content: '你确定要签单该报价单吗？',
			    okVal:'确定',
			    ok: function(){
			    	window.location.href=basePath+"admin/quote_detail.Q?operate=writPerm&id="+id;
			    },
			    cancelVal:'取消',
			    cancel:true
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
						url:basePath+"admin/quote_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href=basePath+"admin/quote_manager.Q";
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

	function printQuote(id){
		art.dialog({
		    id: 'printQuote',
		    content: '请选择你要打印的模板!',
		    button: [
		        {
		            name: '内地模板',
		            callback: function () {
		            	var url = basePath+"admin/quote_printQuote.Q?id="+id;
		            	window.open(url);
		                return true;
		            },
		            focus: true
		        },
		        {
		            name: '香港模板',
		            callback: function () {
		            	var url = basePath+"admin/quote_printQuote.Q?id="+id+"&local=hk";
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
	
	function downloadQuote(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length!=1){
			art.dialog({
			    content: '请选择一条记录下载',
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
			 	art.dialog({
				    id: 'downloadQuote',
				    content: '请选择报价单的表头!',
				    button: [
				        {
				            name: '瀚姆',
				            callback: function () {
				            	var url = basePath+"admin/quote_downloadQuote.Q?id="+ids+"&header=1";
				            	window.location.href = url;
				                return true;
				            },
				            focus: true
				        },
				        {
				            name: 'SONKON',
				            callback: function () {
				            	var url = basePath+"admin/quote_downloadQuote.Q?id="+ids+"&header=2";
				            	window.location.href = url;
				                return true;
				            }
				        },
				         {
				            name: '协诚',
				            callback: function () {
				            	var url = basePath+"admin/quote_downloadQuote.Q?id="+ids+"&header=3";
				            	window.location.href = url;
				                return true;
				            }
				        },
				        {
				            name: '关闭'
				        }
				    ]
				});
			 }
		
		}
	}
	
	function designQuote(id){
		var url = basePath+"admin/quote_designQuote.Q?id="+id;
		window.location.href = url;
	}
	
	function uploadFile(id){
		var url = basePath+"admin/quote_toImport.Q?quoteId="+id;
		art.dialog.open(url, {title: '上传附件', lock: true, drag:true, width: getClientWidth()*0.7, height: getClientHeight()*0.38});
	}
	function downloadFile(id){
		var url = basePath+"admin/quote_downloadFile.Q?quoteId="+id;
		window.location.href = url;
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/quote_manager.Q" method="post" id="queryForm">
          <input type="hidden" value="${isLocalManager }" id="isLocalManager">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">项目名称</label>
	            <input name="projectName" id="projectName" type="text" class="span2"  value="${ projectName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">销售姓名</label>
	            <input name="vcSalesman" id="vcSalesman" type="text" class="span2"  value="${vcSalesman }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">创建日期</label>
	             <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="span2" />
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">报价单号</label>
	            <input name="projectNum" id="projectNum" type="text" class="span2"  value="${ projectNum}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">询价方</label>
	            <input name="vcAttn" id="vcAttn" type="text" class="span2"  value="${vcAttn }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">设计方</label>
	            <input name="designerName" id="designerName" type="text" class="span2"  value="${designerName }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">型号</label>
	            <input name="vcBefModel" id="vcBefModel" type="text" class="span2"  value="${vcBefModel }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">创建人</label>
	            <input name="curUserName" id="curUserName" type="text" class="span2"  value="${curUserName }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">报价方式</label>
	            <select name="quoteFormate" id="quoteFormate" style="width:170px;">
	            	<option value="">请选择</option>
	             	<option value="1" <s:if test="#request.quoteFormate==1">selected</s:if> >内地报价</option>
                  	<option value="2" <s:if test="#request.quoteFormate==2">selected</s:if> >香港报价</option>
                  	<option value="3" <s:if test="#request.quoteFormate==3">selected</s:if> >大货价内地报价</option>
                  	<option value="4" <s:if test="#request.quoteFormate==4">selected</s:if> >大货价香港报价</option>
                  	<option value="5" <s:if test="#request.quoteFormate==5">selected</s:if> >零售报价</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         
	         <div class="controls">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:60px;">是否签单</label>
	            <select name="isWritPerm" id="isWritPerm" style="width:170px;">
	            	<option value="">请选择</option>
	             	<option value="0" <s:if test="#request.isWritPerm==0">selected</s:if> >未签单</option>
                  	<option value="1" <s:if test="#request.isWritPerm==1">selected</s:if> >已签单</option>
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
            <h5>报价单列表</h5>
            <w:permission permissionId="<%=PermissionId.QUOTE_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="addQuote();">新增</button>
            </w:permission>
            <w:permission permissionId="<%=PermissionId.QUOTE_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            </w:permission>
            <w:permission permissionId="<%=PermissionId.QUOTE_MGT_DOWNLOAD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="downloadQuote();">下载报价单</button> 
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
