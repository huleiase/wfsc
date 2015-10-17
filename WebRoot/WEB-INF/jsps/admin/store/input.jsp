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
		window.location.href = "<%=basePath %>/admin/store_manager.Q";
	}
</script>

</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="admin/store_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="store.id" id="storeId" value="${store.id }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">仓库名</label>
	            <input name="store.storeName" id="storeName" type="text" class="span2"  value="${ store.storeName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">地址</label>
	            <input name="store.storeAddr" id="storeAddr" type="text" class="span2"  value="${ store.storeAddr}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">所属地</label>
	            <select name="store.vcLocal" id="vcLocal" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="GZ" <c:if test="${store.vcLocal=='GZ' }">selected</c:if> >广州</option>
                  	<option value="SH" <c:if test="${store.vcLocal=='SH' }">selected</c:if> >上海</option>
                  	<option value="SZ" <c:if test="${store.vcLocal=='SZ' }">selected</c:if> >深圳</option>
                  	<option value="BJ" <c:if test="${store.vcLocal=='BJ' }">selected</c:if> >北京</option>
                  	<option value="HK" <c:if test="${store.vcLocal=='HK' }">selected</c:if> >香港</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">电话</label>
	            <input name="store.tel" id="tel" type="text" class="span2"  value="${ store.tel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">传真</label>
	            <input name="store.fax" id="fax" type="text" class="span2"  value="${ store.fax}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">属性</label>
	            <select name="store.isPublic" id="isPublic" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="1" <c:if test="${store.isPublic=='1' }">selected</c:if> >公仓</option>
                  	<option value="0" <c:if test="${store.isPublic=='0' }">selected</c:if> >私仓</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">联系方式</label>
	            <input name="store.linkMethod" id="linkMethod" type="text" class="span2"  value="${ store.linkMethod}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">所属人</label>
	            <input name="store.userName" id="userName" type="text" class="span2"  value="${ store.userName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	             <input name="store.remark" id="remark" type="text" class="span2"  value="${ store.remark}">
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
