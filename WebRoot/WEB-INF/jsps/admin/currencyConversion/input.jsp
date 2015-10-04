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
		$("#saveButton").attr("disabled", "disabled");
		$("#inputForm").submit();
	}
	function toBack(){
		window.location.href = "<%=basePath %>/admin/currencyConversion_manager.Q";
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
          <form action="admin/currencyConversion_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="currencyConversion.id" id="currencyConversionId" value="${currencyConversion.id }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">币种</label>
	            <input name="currencyConversion.vcCurrency" id="vcCurrency" type="text" class="span2"  value="${ currencyConversion.vcCurrency}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">目标币种</label>
	            <input name="currencyConversion.vcObjCurrency" id="vcObjCurrency" type="text" class="span2"  value="${ currencyConversion.vcObjCurrency}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">汇率</label>
	            <input name="currencyConversion.vcExchangeRate" id="vcExchangeRate" type="text" class="span2"  value="${currencyConversion.vcExchangeRate }">
	         </div>
	         
	         <div class="clear"></div>
	         <br/>
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
