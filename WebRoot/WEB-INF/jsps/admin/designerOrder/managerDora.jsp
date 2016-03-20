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
<script type="text/javascript">
	function resetForm(){
		$("#contractNo").val("");
		$("#beginDate").val("");
		$("#endDate").val("");
	}
	function submitForm(){
			$("#queryForm").submit();
		
	}
	function downloadSell(){
		var url = "<%=basePath%>admin/designerOrde_exportDesignerOrdeData.Q?isSell=1"
		var contractNo = $("#contractNo").val();
			if(contractNo){
				 url += "&contractNo="+contractNo;
			}
			var beginDate = $("#beginDate").val();
			if(beginDate){
				 url += "&beginDate="+beginDate;
			}
			var endDate = $("#endDate").val();
			if(endDate){
				 url += "&endDate="+endDate;
			}
			window.location.href = url;
			
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/designerOrde_managerDora.Q" method="post" id="queryForm">
          <input name="isSell" type="hidden" value="1">
            <div class="controls">
            	
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">合同号</label>
	            <input name="contractNo" id="contractNo" type="text" class="span2"  value="${contractNo }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">日期</label>
	             <input type="text" id="beginDate" name="beginDate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${beginDate}" class="span2" />
		                  	&nbsp;&nbsp;<span class="span1" style="width: 20px;margin-left: 10px;">至</span>&nbsp;&nbsp;
		         <input type="text" id="endDate" name="endDate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${endDate}" class="span2" />
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="button" class="btn btn-success" style="margin-left:355px;" onclick="submitForm();">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>销售收入(Dora)</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="javascript:void(0);">按条件导出</button>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="listDora.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
