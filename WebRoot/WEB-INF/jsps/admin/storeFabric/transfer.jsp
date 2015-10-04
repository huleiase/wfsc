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
		var surplus = $("#surplus").val();
		if(surplus){
			$("#inputForm").submit();
		}
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
          <form action="admin/storeFabric_savetransfer.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="id" id="storeFabricId" value="${storeFabric.id }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">剩余数量</label>
	            <input readonly="readonly" id="surplus" type="text" class="span2"  value="${ surplus}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">选择分段</label>
	            <select name="fenduan" id="fenduan" style="float: left;width:170px">
	            	<option value="">请选择</option>
	             	 <c:forEach var="su" items="${surpluslist}">
			             <option value="${su }">${su }</option>
			          </c:forEach>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">转移数量</label>
	            <input name="transferNum" id="transferNum" type="text" class="span2"  value="">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">转移到仓库</label>
	            <select name="newStore" id="newStore" style="float: left;width:170px">
	            	<option value="">请选择</option>
	             	<c:forEach var="s" items="${liststore}">
		             <option value="${s.id }">${s.storeName }</option>
		             </c:forEach>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">转移原因</label>
	             <input name="vcReason" id="vcReason" type="text" class="span10"  value="">
	         </div>
	         <div class="clear"></div>
            <div class="form-actions">
            <button type="button" class="btn btn-success" onclick="window.close();">关闭</button>
            <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
            </div>
          </form>
          <s:if test="#request.success==1">
	         <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="#">×</a>
              <h4 class="alert-heading">转移成功!</h4><br>
          </div>
	         </s:if>
        </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
