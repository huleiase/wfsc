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
		window.location.href = "<%=basePath %>/admin/fabric_managerHT.Q";
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
          <form action="admin/fabric_saveHT.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="fabric.id" id="fabricId" value="${fabric.id }">
           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂代码</label>
	            <input name="fabric.vcFactoryCode" id="vcFactoryCode" type="text" class="span2"  value="${fabric.vcFactoryCode }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">原厂型号</label>
	            <input name="fabric.vcBefModel" id="vcBefModel" type="text" class="span2"  value="${ fabric.vcBefModel}">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">HT型号</label>
	            <input name="fabric.htCode" id="htCode" type="text" class="span2"  value="${ fabric.htCode}">
	            
	             
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">色号</label>
	            <input name="fabric.colorCode" id="colorCode" type="text" class="span2"  value="${ fabric.colorCode}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">书号</label>
	            <input name="fabric.bookNo" id="bookNo" type="text" class="span2"  value="${fabric.bookNo }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">状态</label>
	            <select name="fabric.vcDis" id="vcDis" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="停用" <s:if test="#request.fabric.vcDis=='停用'">selected</s:if> >停用</option>
                  	<option value="启用" <s:if test="#request.fabric.vcDis=='启用'">selected</s:if> >启用</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">品牌</label>
	            <input name="fabric.brand" id="brand" type="text" class="span2"  value="${ fabric.brand}">
	             <c:if test="${isAdmin||isPurManager}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">大陆加工系数</label>
	            <input name="fabric.inlandRefitRate" id="inlandRefitRate" type="text" class="span2"  value="${ fabric.inlandRefitRate}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">香港加工系数</label>
	            <input name="fabric.hkRefitRate" id="hkRefitRate" type="text" class="span2"  value="${fabric.hkRefitRate }">
	            </c:if>
	         </div>
	          <div class="clear"></div>
	           <c:if test="${isAdmin||isPurManager}">
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">大陆上调系数</label>
	            <input name="fabric.inlandRaiseRate" id="inlandRaiseRate" type="text" class="span2"  value="${ fabric.inlandRaiseRate}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">香港上调系数</label>
	            <input name="fabric.hkRaiseRate" id="hkRaiseRate" type="text" class="span2"  value="${ fabric.hkRaiseRate}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">大陆下调系数</label>
	            <input name="fabric.inlandDownRate" id="inlandDownRate" type="text" class="span2"  value="${fabric.inlandDownRate }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">香港下调系数</label>
	            <input name="fabric.hkDownRate" id="hkDownRate" type="text" class="span2"  value="${ fabric.hkDownRate}">
	         </div>
	         <div class="clear"></div><br/>
	         </c:if>
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
