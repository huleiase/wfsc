<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【广州诚光进销存后台管理系统】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
		<script src="<%=request.getContextPath() %>/js/echarts/echarts.js"></script>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			function showChart(){
				var quoteLocal = $("#quoteLocal").val();
				var reportYear = $("#reportYear").val();
				var limit = $("#limit").val();
				if(!reportYear || !quoteLocal){
					alert("查询条件必填");
					return
				}
				var url = basePath+"admin/report_getMostSellData.Q";
				var myChart = echarts.init(document.getElementById('chartDiv'));
				var monthArray = [];
				myChart.showLoading();
				$.get(url,{"quoteLocal":quoteLocal,"reportYear":reportYear,"limit":limit}, function(jsonData){
					myChart.hideLoading();
					monthArray = jsonData.jsonMounth;
					myChart.setOption({
				        baseOption: {
				            timeline: {
				            	axisType: 'category',
				            	autoPlay: true,
				            	playInterval: 10000,
				                data: jsonData.jsonMounth
				            },
				            tooltip: {},
				            calculable : true,
				            xAxis: [{'type':'category',data:[quoteLocal]}],
				            yAxis: [{ type: 'value',name: '数量'}],
				            
				        },
				        options: jsonData.jsa
				    });
				},"json");
				
				myChart.on('click', function (params) {
					if(params.seriesType=='bar'){
						var displayName = params.seriesName;
						var option = myChart.getOption();
						var curMonthIndex = option.timeline[0].currentIndex;
						var yearMonth = monthArray[curMonthIndex];
						$.ajax({
							url:basePath+"admin/report_getDetailSellData.Q",
							data:{"displayName":displayName,"yearMonth":yearMonth,"quoteLocal":quoteLocal},
							dataType:'html',
							success:function(detailData){
								art.dialog({
									title: '详细信息',
								    content: detailData,
								    okVal:'关闭',
								    ok: true
								});
						    }
						})
					}
				});
	}
	
	
	function resetForm(){
		$("#reportYear").val("");
		$("#quoteLocal").val("");
		$("#limit").val("");
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
							<c:if test="${isAdminb==true}">
						<label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">地区</label>
	            <select name="quoteLocal" id="quoteLocal" style="width:170px;float:left;">
	            	<option value="">请选择</option>
	            	<option value="GZ">广州</option>
                  	<option value="HK">香港</option>
                  	<option value="BJ">北京</option>
                  	<option value="SH">上海</option>
                  	<option value="SZ">深圳</option>
	             </select>
	             </c:if>
	             <c:if test="${isAdminb==false}">
	             <input name="quoteLocal" value="${quoteLocal}" id="quoteLocal" type="hidden">
	             </c:if>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">年份</label>
	            <select name="reportYear" id="reportYear" style="width:170px;float:left;">
	            	<option value="">请选择</option>
	            	<option value="2015">2015</option>
                  	<option value="2016">2016</option>
                  	<option value="2017">2017</option>
                  	<option value="2018">2018</option>
                  	<option value="2019">2019</option>
                  	<option value="2020">2020</option>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:75px;">数量</label>
	            <select name="limit" id="limit" style="width:170px;float:left;">
	            	<option value="10">10</option>
                  	<option value="20">20</option>
                  	<option value="30">30</option>
	             </select>
								<button type="button" class="btn btn-success" onclick="showChart();">
									查询
								</button>
							</div>
						</form>
					</div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-bar-chart"></i> </span>
						<h5>
							统计报表
						</h5>
					</div>
						<div id="chartDiv" style="width:90%;height:600px;"></div>
				</div>
						
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
