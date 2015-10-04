<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="/wfsc/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script type="text/javascript">
	function resetForm(){
		$("#name").val("");
		$("#recommend").val("");
		$("#prdCatCode").val("");
		$("#prdCatName").val("");
	}
	function viewStock(code){
		window.location.href="/wfsc/admin/products_stock.Q?code="+code;
	}
	
	function addOrUpdate(id){
		var url = "/wfsc/admin/products_productInput.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function detail(id){
		var url = "/wfsc/admin/products_detail.Q?id="+id;
		window.location.href = url;
	}
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一件商品删除',
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
			    content: '你确定要删除选中的商品吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"/wfsc/admin/products_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="/wfsc/admin/products_manager.Q";
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
	function openPrdCatTree(){
    	var url = "/wfsc/admin/products_openPrdCatTree.Q";
       art.dialog.open(url, {limit:false,lock:true,title:'<b>商品类型</b>',width:'260px',height:'400px'});
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub11"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="/wfsc/admin/products_manager.Q" method="post" id="productsQueryForm">
            <div class="controls controls-row">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品名:</label>
	            <input name="name" id="name" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ name}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品编号:</label>
	            <input name="prdCode" id="prdCode" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ prdCode}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品分类:</label>
	            <input name="prdCatName" id="prdCatName" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ prdCatName}" onclick="openPrdCatTree();">
	             <input name="prdCatCode" id="prdCatCode" type="hidden" value="${ prdCatCode}">
	             <label class="span1" for="inputSuccess" style="margin-top:5px">是否推荐:</label>
	             <select name="recommend" id="recommend" style="margin-left:5px;margin-right:35px;width:156px">
	            	 <option value="">请选择</option>
	             	<option value="0" <s:if test="#request.recommend==0">selected</s:if> >不推荐</option>
                  	<option value="1" <s:if test="#request.recommend==1">selected</s:if>>推荐</option>
                  	<option value="2" <s:if test="#request.recommend==2">selected</s:if>>大图推荐</option>
	             </select>
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>商品列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
         <!-- <button class="label label-info btn btn-primary btn-mini">导入</button> -->
            <button class="label label-info btn btn-primary btn-mini" onclick="delByIds();">删除</button>
         </div>
         <div class="widget-content nopadding" id="productTableId">
            <%@include file="productList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
