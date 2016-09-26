<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【广州诚光进销存后台管理系统】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
		<script src="<%=request.getContextPath() %>/js/echarts/echarts.common.min.js"></script>
		<script src="/wfsc/js/dpicker/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			function showChart(){
				var sDate = $("#startTime").val();
				var eDate = $("#endTime").val();
				var displayName = $("#displayName").val();
				if(!sDate || !eDate || !displayName){
					alert("查询条件必填");
					return
				}
				var myChart = echarts.init(document.getElementById('chartDiv'));
				var url = basePath+"admin/report_getData.Q";
				$.get(url,{"sDate":sDate,"eDate":eDate,"displayName":displayName}, function(jsonData){
					myChart.setOption({
						title: {
							text: jsonData.text
						},
						tooltip: {},
						legend: {
							data:[jsonData.name]
						},
						xAxis: {
							data: jsonData.jax
						},
						yAxis: {},
						series: [{
							name: jsonData.name,
							type: 'bar',
							data: jsonData.jas
						}]
					});
				},"json");
	}
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#displayName").val("");
	}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="/wfsc/admin/admin_index.Q" title="Go to Home"
						class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-content">
						<form action="<%=request.getContextPath() %>/admin/report_financeReport.Q" method="post" id="reportForm">
							<div class="controls">
							 <label class="span1" for="inputSuccess" style="margin-top:5px;">型号</label>
	            			<input name="displayName" id="displayName" type="text" class="span2" placeholder="报价单页面中的型号" value="${ displayName}">
							<label class="span1" for="inputSuccess" style="margin-top:5px">统计时间</label>
	            
	            <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${startTime}" class="wdateinput" />
		                  	&nbsp;至&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="wdateinput" />
	            &nbsp; &nbsp;
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-success" onclick="showChart();">
									查询
								</button>
								 <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
							</div>
						</form>
					</div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-bar-chart"></i> </span>
						<h5>
							统计报表
						</h5>
					</div>
						<div id="chartDiv" style="width:80%;height:600px;"></div>
				</div>
						
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
