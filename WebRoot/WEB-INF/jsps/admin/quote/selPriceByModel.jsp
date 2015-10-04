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
</script>
<style type="text/css">

#content {
    margin-left: 0px;
}
</style>
</head>
<body>

<div id="content">
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/quote_selPriceByModel.Q?parentJsp=1" method="post" id="queryForm">
          <input type="hidden" name="isHtCode" value="${isHtCode }">
          <input type="hidden" name="htCode" value="${htCode }">
          <input type="hidden" name="factoryCode" value="${factoryCode }">
          <input type="hidden" name="model" value="${model }">
	          <div class="controls">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">报价方式</label>
	            <select name="quoteFormate" id="quoteFormate" style="width:170px;">
	            	<option value="">请选择</option>
	             	<option value="1" <s:if test="#request.quoteFormate==1">selected</s:if> >内地报价</option>
                  	<option value="2" <s:if test="#request.quoteFormate==2">selected</s:if> >香港报价</option>
	             </select>
	             
	              <button type="submit" class="btn btn-success" style="margin-left:55px;margin-bottom: 10px;">查询</button>&nbsp;&nbsp;&nbsp;
	         </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>报价列表</h5>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="selPriceByModelList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
