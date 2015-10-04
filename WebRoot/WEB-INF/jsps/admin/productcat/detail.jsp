<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存分类详情】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function toProductCatList(){
		window.location.href = "/wfsc/admin/productcat_index.Q";
	}
</script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/wfsc/ueditor/themes/default/css/ueditor.css">
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub12"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品分类</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="/wfsc/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
          	<input type="hidden" name="products.id" id="productsId" value="${productCat.id }">
            <div class="control-group">
              <label class="control-label">分类名:</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="productCat.name" value="${productCat.name }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label"> 商品编码:</label>
              <div class="controls">
                <input type="text" class="span5" name="productCat.code" value="${productCat.code }" id="code" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">分类图片</label>
              <div class="controls">
                 <s:if test="productCat.picUrl==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='/wfsc/${productCat.picUrl}' title="点击查看图片" target="_blank">
					<img src='/wfsc/${productCat.picUrl}' width="24" height="16" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="toProductCatList();">返回</button>
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
