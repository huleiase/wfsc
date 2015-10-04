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
		window.location.href = "<%=basePath %>/admin/customer_manager.Q";
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
          <form action="admin/customer_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="customer.id" id="customerId" value="${customer.id }">
           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">公司名称</label>
	            <input name="customer.companyName" id="companyName" type="text" class="span2"  value="${ customer.companyName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">电话</label>
	            <input name="customer.tel" id="tel" type="text" class="span2"  value="${customer.tel }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">状态</label>
	            <select name="customer.dis" id="dis" style="width:170px">
	            	<option value="">所有</option>
	             	<option value="停用" <s:if test="#request.customer.dis=='停用'">selected</s:if> >停用</option>
                  	<option value="启用" <s:if test="#request.customer.dis=='启用'">selected</s:if> >启用</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">传真</label>
	            <input name="customer.fax" id="fax" type="text" class="span2"  value="${ customer.fax}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">地址</label>
	            <input name="customer.addr" id="addr" type="text" class="span2"  value="${ customer.addr}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">联系人</label>
	            <input name="customer.linkman" id="linkman" type="text" class="span2"  value="${customer.linkman }">
	         </div>
	         
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">手机</label>
	            <input name="customer.phone" id="phone" type="text" class="span2"  value="${ customer.phone}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">邮箱</label>
	            <input name="customer.email" id="email" type="text" class="span2"  value="${ customer.email}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="customer.remk" id="remk" type="text" class="span2"  value="${customer.remk }">
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
