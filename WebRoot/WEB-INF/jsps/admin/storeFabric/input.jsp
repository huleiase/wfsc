<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function checkForm(){
		$("#inputForm").submit();
	}
	function toBack(){
		window.location.href = "<%=basePath %>/admin/storeFabric_manager.Q";
	}
</script>

</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub71"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="admin/storeFabric_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="storeFabric.id" id="storeFabricId" value="${storeFabric.id }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂编号</label>
	            <input name="storeFabric.vcFactoryCode" id="vcFactoryCode" type="text" class="span2"  value="${ storeFabric.vcFactoryCode}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目名称</label>
	            <input name="storeFabric.vcProject" id="vcProject" type="text" class="span2"  value="${ storeFabric.vcProject}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">原厂型号</label>
	            <input name="storeFabric.vcModelNum" id="vcModelNum" type="text" class="span2"  value="${storeFabric.vcModelNum }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">供应商</label>
	            <input name="storeFabric.supplie" id="supplie" type="text" class="span2"  value="${ storeFabric.supplie}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">色号</label>
	            <input name="storeFabric.vcColorNum" id="vcColorNum" type="text" class="span2"  value="${ storeFabric.vcColorNum}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">对照码</label>
	            <input name="storeFabric.htCode" id="htCode" type="text" class="span2"  value="${storeFabric.htCode }">
	         </div>
	         <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">产品编号</label>
	            <input name="storeFabric.fabricNo" id="fabricNo" type="text" class="span2"  value="${ storeFabric.fabricNo}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">支付方式</label>
	            <input name="storeFabric.payment" id="payment" type="text" class="span2"  value="${ storeFabric.payment}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">订单编号</label>
	            <input name="storeFabric.orderNo" id="orderNo" type="text" class="span2"  value="${storeFabric.orderNo }">
	         </div>
	         <div class="clear"></div>
	         <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">实定量</label>
	            <input name="storeFabric.vcQuoteNum" id="vcQuoteNum" type="text" class="span2"  value="${ storeFabric.vcQuoteNum}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">实际到货量</label>
	            <input name="storeFabric.vcRealityAog" id="vcRealityAog" type="text" class="span2"  value="${ storeFabric.vcRealityAog}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">剩余量</label>
	            <input name="storeFabric.surplus" id="surplus" type="text" class="span2"  value="${storeFabric.surplus }">
	         </div>
	         <div class="clear"></div>
	          <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="storeFabric.vcRmk" id="vcRmk" type="text" class="span11"  value="${ storeFabric.vcRmk}">
	         </div>
	         <div class="clear"></div>
            <div class="form-actions">
            <s:if test="#request.isView">
            <button type="button" class="btn btn-success" onclick="window.close();">关闭</button>
            </s:if>
            <s:else>
            <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toBack();">返回</button>
            </s:else>
              
            </div>
          </form>
        </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
