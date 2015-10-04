<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function checkProductsForm(){
		$("#saveButton").attr("disabled", "disabled");
		$("#productsForm").submit();
	}
	function toProductsList(){
		window.location.href = "/wfsc/admin/products_manager.Q";
	}
</script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/ueditor/themes/default/css/ueditor.css">
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
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="/wfsc/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
          	<input type="hidden" name="products.id" id="productsId" value="${products.id }">
            <div class="control-group">
              <label class="control-label">商品名 :</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="products.name" value="${products.name }" />&nbsp;&nbsp;
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">商品规格 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdStandard" value="${products.prdStandard }" id="prdStandard"/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品价格 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdPrice" value="${products.prdPrice }" id="prdPrice"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">折扣价 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdDisPrice" value="${products.prdDisPrice }" id="prdDisPrice"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">所属分类 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdCatName" value="${products.prdCatName }" id="prdDisPrice"/>&nbsp;&nbsp;
                <input type="hidden" class="span5" name="products.prdCatCode" value="${products.prdCatCode }" id="prdCatCode"/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品描述 ：</label>
              <div class="controls">
                <textarea name="prdDesc" id="prdDesc">商品描述</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdDesc');
				</script>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">品牌故事 ：</label>
              <div class="controls">
                <textarea name="prdStory" id="prdStory">品牌故事</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdStory');
				</script>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkProductsForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
