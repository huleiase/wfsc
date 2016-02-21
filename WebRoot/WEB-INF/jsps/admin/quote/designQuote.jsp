<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function checkForm(){
		$("#inputForm").submit();
	}
	function toBack(){
		window.location.href = basePath+"admin/quote_manager.Q";
	}
	function sethasNoApply(obj){
	  	var jObj =  $(obj);
		var objId = jObj.attr("id");
		var idAttr = objId.split("_");
		var suffixId  = idAttr[1];
		var designmoney = $("#designMony_"+suffixId).val();
		var hasApply = $("#hasApply_"+suffixId).val();
		if(!designmoney){
			designmoney=0;
		}
		if(!hasApply){
			hasApply=0;
		}
		var hasNoApply = Number(designmoney)-Number(hasApply);
		var fixhasNoApply = hasNoApply.toFixed(2);
		jObj.val(fixhasNoApply);
	  }
	  
		function getCounselorRate(obj){
		var jObj =  $(obj);
		var objId = jObj.attr("id");
		var objValue = jObj.val();
		var idAttr = objId.split("_");
		var suffixId  = idAttr[1];
				if(objValue==""){
					return ;
				}
			//	var myurl=window.encodeURI("${ctx}/quote/quote!ajaxGetCounselorRate.jspx?objValue="+objValue);
			//	myurl=window.encodeURI(myurl);
				$.ajax({
					type : "POST",
					contentType :"application/json",
					url :basePath+"admin/quote_getCounselorRate.Q?objValue="+objValue,
					dataType :'html',
					success:function(data){
						if(data){
							var counselorRate = data;
							var counselorRateAttr = data.split(",");
							var realTotel = Number($("#realTotel").val());
							var designMony = 0;
							if(counselorRateAttr.length>1){
							var counselorRateSelect = "<select id=\"counselorRate_"+suffixId+"\" onchange=\"setDesignMony(\'"+suffixId+"\',this.value);\" name=\"designerExpense.counselorRate"+suffixId+"\"  style=\"width:202px;float:left;\" class=\"span3\">";
								var counselorRateOption="";
								for(var i=0;i<counselorRateAttr.length;i++){
									if(counselorRateAttr[i].indexOf("%")>0){
										var c = counselorRateAttr[i].substring(0,counselorRateAttr[i].length-1);
										var cf = c/100;
										counselorRateOption += "<option value="+cf+">"+cf+"</option>";
									}else{
										counselorRateOption += "<option value="+counselorRateAttr[i]+">"+counselorRateAttr[i]+"</option>";
									}
									
								}
								counselorRateSelect += counselorRateOption;
								counselorRateSelect += "</select>"
								$("#counselorRate_"+suffixId).replaceWith(counselorRateSelect);
								if(counselorRateAttr[0].indexOf("%")>0){
									var c = counselorRateAttr[0].substring(0,counselorRateAttr[0].length-1)
									designMony = realTotel*Number(c)*0.01;
								}else{
									designMony = realTotel*Number(counselorRateAttr[0]);
								}
							}else{
								var counselorRatInput = " <input id=\"counselorRate_"+suffixId+"\" name=\"designerExpense.counselorRate"+suffixId+"\" type=\"text\" value=\"\" readonly=\"readonly\" class=\"span3\"/>"
								$("#counselorRate_"+suffixId).replaceWith(counselorRatInput);
								if(counselorRate.indexOf("%")>0){
									counselorRate = counselorRate.substring(0,counselorRate.length-1)*0.01
								}
								$("#counselorRate_"+suffixId).val(counselorRate);
								designMony = realTotel*Number(counselorRate);
							}
							$("#designMony_"+suffixId).val(designMony.toFixed(2));
						}else{
							art.dialog({content:"发生未知错误",okVal:"确定",ok:true});
						}
					}
				});
			}
		function setDesignMony(suffixId,counselorRate){
			var designMony=0;
			var realTotel = Number($("#realTotel").val());
			if(counselorRate.indexOf("%")>0){
				var c = counselorRate.substring(0,counselorRate.length-1)
				designMony = realTotel*Number(c)*0.01;
			}else{
				designMony = realTotel*Number(counselorRate);
			}
			$("#designMony_"+suffixId).val(designMony.toFixed(2));
		}
</script>
<style type="text/css">
input.span3 {
    float: left;
    width: 188px;
}
input.blankInput {
     float: left;
    margin-left: 9px;
    margin-right: 15px;
    width: 75px;
}
input.span11 {
    width: 820px;
}
input.span6 {
    width: 504px;
}
.quoteItem {
    height: 160px;
    width: 823px;
}
.selectQuoteFabric {
    float: left;
    height: 28px;
    margin-left: 500px;
    margin-right: 15px;
    margin-top: 5px;
    width: 166px;
}
.alert {
   margin-bottom: 0px;
}
table#quoteFabricTable input.widthShort{
width: 60px;
}
table#quoteFabricTable select{
width: 70px;
}
table#quoteFabricTable .label, .badge {
    line-height: 20px;
    margin-right: 5px;
}
</style>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="admin/quote_saveDE.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="quoteId" id="quoteId" value="${quote.id }">
          	<input type="hidden" name="designerExpense.id" id="designerExpenseId" value="${designerExpense.id }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司</label>
	            <input disabled="disabled" autocomplete="off" name="quote.vcAttn" id="vcAttn" type="text" class="span3"  value="${ quote.vcAttn}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司电话</label>
	            <input disabled="disabled" name="quote.vcAttnTel" id="vcAttnTel" type="text" class="span3"  value="${ quote.vcAttnTel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司传真</label>
	            <input disabled="disabled" name="quote.vcAttnFax" id="vcAttnFax" type="text" class="span3"  value="${quote.vcAttnFax }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价客户名字</label>
	            <input disabled="disabled" name="quote.vcAttnName" id="vcAttnName" type="text" class="span3"  value="${ quote.vcAttnName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">客户手机号码</label>
	            <input disabled="disabled" name="quote.vcAttnPhone" id="vcAttnPhone" type="text" class="span3"  value="${ quote.vcAttnPhone}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">客户电子邮箱</label>
	            <input disabled="disabled" name="quote.vcAttnEmail" id="vcAttnEmail" type="text" class="span3"  value="${quote.vcAttnEmail }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价公司</label>
	            <input name="quote.vcFrom" id="vcFrom" type="text" class="span3"  value="${quote.vcFrom }">
	            <!--
	            <select disabled="disabled" name="quote.vcFrom" id="vcFrom" style="width:202px;float: left;" class="span3">
	            	<option value="">请选择</option>
	             	<option value="北京帛韵鸿维装饰材料有限公司" <s:if test="#request.quote.vcFrom=='北京帛韵鸿维装饰材料有限公司'">selected</s:if> >北京帛韵鸿维装饰材料有限公司</option>
                  	<option value="北京元韵布艺文化发展有限公司" <s:if test="#request.quote.vcFrom=='北京元韵布艺文化发展有限公司'">selected</s:if> >北京元韵布艺文化发展有限公司</option>
                  	<option value="尊艺诚光（上海）装饰材料有限公司" <s:if test="#request.quote.vcFrom=='尊艺诚光（上海）装饰材料有限公司'">selected</s:if> >尊艺诚光（上海）装饰材料有限公司</option>
                  	<option value="深圳市诚光装饰材料有限公司" <s:if test="#request.quote.vcFrom=='深圳市诚光装饰材料有限公司'">selected</s:if> >深圳市诚光装饰材料有限公司</option>
                  	<option value="广州诚光装饰材料有限公司" <s:if test="#request.quote.vcFrom=='广州诚光装饰材料有限公司'">selected</s:if> >广州诚光装饰材料有限公司</option>
                  	<option value="广州元韵装饰材料有限公司" <s:if test="#request.quote.vcFrom=='广州元韵装饰材料有限公司'">selected</s:if> >广州元韵装饰材料有限公司</option>
                  	<option value="協誠洋行有限公司" <s:if test="#request.quote.vcFrom=='協誠洋行有限公司'">selected</s:if> >協誠洋行有限公司</option>
                  	<option value="广州道勤装饰材料有限公司" <s:if test="#request.quote.vcFrom=='广州道勤装饰材料有限公司'">selected</s:if> >广州道勤装饰材料有限公司</option>
	             </select>
	            --><label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地电话</label>
	            <input disabled="disabled" name="quote.vcFormTel" id="vcFormTel" type="text" class="span3"  value="${ quote.vcFormTel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地传真</label>
	            <input disabled="disabled" name="quote.vcFormFax" id="vcFormFax" type="text" class="span3"  value="${quote.vcFormFax }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">跟进的销售</label>
	            <input disabled="disabled" name="quote.vcFormName" id="vcFormName" type="text" class="span3"  value="${ quote.vcFormName}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售的电话</label>
	            <input disabled="disabled" name="quote.vcFormPhone" id="vcFormPhone" type="text" class="span3"  value="${ quote.vcFormPhone}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Date</label>
	             <input disabled="disabled" id="vcFormDate" type="text" class="span3"  value='<s:date name="quote.vcFormDate" format="yyyy-MM-dd" />' readonly="readonly">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地</label>
	            <select disabled="disabled" name="quote.vcQuoteLocal" id="vcQuoteLocal" style="width:202px;float: left;" class="span3">
	             	<option value="GZ" <s:if test="#request.quote.vcQuoteLocal=='GZ'">selected</s:if> >广州</option>
	             	<option value="SH" <s:if test="#request.quote.vcQuoteLocal=='SH'">selected</s:if> >上海</option>
	             	<option value="BJ" <s:if test="#request.quote.vcQuoteLocal=='BJ'">selected</s:if> >北京</option>
	             	<option value="SZ" <s:if test="#request.quote.vcQuoteLocal=='SZ'">selected</s:if> >深圳</option>
	             	<option value="HK" <s:if test="#request.quote.vcQuoteLocal=='HK'">selected</s:if> >香港</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售</label>
	            <select disabled="disabled" name="quote.vcSalesman" id="vcSalesman" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.sellmanList" var="ls">
	            		<option value="${ls.username }" <c:if test='${quote.vcSalesman==ls.username }'>selected=selected</c:if>>${ls.username }</option>
	            	</s:iterator>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Ref no</label>
	            <input disabled="disabled" name="quote.vcQuoteNum" id="vcQuoteNum" type="text" class="span3"  value="${quote.vcQuoteNum }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售1</label>
	            <select disabled="disabled" name="quote.vcSalesman1" id="vcSalesman1" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.sellmanList" var="s1">
	            		<option value="${s1.username }" <c:if test='${quote.vcSalesman1==s1.username }'>selected=selected</c:if>>${s1.username }</option>
	            	</s:iterator>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售2</label>
	            <select disabled="disabled" name="quote.vcSalesman2" id="vcSalesman2" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.sellmanList" var="s2">
	            		<option value="${s2.username }" <c:if test='${quote.vcSalesman2==s2.username }'>selected=selected</c:if>>${s2.username }</option>
	            	</s:iterator>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售3</label>
	            <select disabled="disabled" name="quote.vcSalesman3" id="vcSalesman1" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.sellmanList" var="s3">
	            		<option value="${s3.username }" <c:if test='${quote.vcSalesman3==s3.username }'>selected=selected</c:if>>${s3.username }</option>
	            	</s:iterator>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售4</label>
	            <select disabled="disabled" name="quote.vcSalesman4" id="vcSalesman4" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.sellmanList" var="s4">
	            		<option value="${s4.username }" <c:if test='${quote.vcSalesman4==s4.username }'>selected=selected</c:if>>${s4.username }</option>
	            	</s:iterator>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售组</label>
	            <select disabled="disabled" name="quote.groupId" id="salesmanGroup" style="width:202px;float: left;"  class="span3">
	            	<option value="">请选择</option>
	            	<s:iterator value="#request.salesmanGroup" var="sg">
	            		<option value="${sg.id }" <c:if test='${quote.groupId==sg.id }'>selected=selected</c:if>>${sg.groupName }</option>
	            	</s:iterator>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加工费</label>
	              <input disabled="disabled" name="quote.vcProcessFre" id="vcProcessFre" type="text" class="span3"  value="${ quote.vcProcessFre}" >
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">安装费</label>
	            <input disabled="disabled" name="quote.vcInstallFre" id="vcInstallFre" type="text" class="span3"  value="${ quote.vcInstallFre}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">后处理</label>
	            <input disabled="disabled" name="quote.vcAftertreatment" id="vcAftertreatment" type="text" class="span3"  value="${ quote.vcAftertreatment}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">其它</label>
	            <input disabled="disabled" name="quote.vcOther" id="vcOther" type="text" class="span3"  value="${quote.vcOther }" >
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">尊敬的</label>
	            <input disabled="disabled" name="quote.customer" id="customer" type="text" class="span3"  value="${ quote.customer}" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价</label>
	            <select disabled="disabled" name="quote.quoteFormate" id="quoteFormate" style="width:202px;float: left;" class="span3">
	             	<option value="">请选择</option>
	             	<option value="1" <s:if test="#request.quote.quoteFormate==1">selected</s:if> >内地报价</option>
                  	<option value="2" <s:if test="#request.quote.quoteFormate==2">selected</s:if> >香港报价</option>
                  	<option value="3" <s:if test="#request.quote.quoteFormate==3">selected</s:if> >大货价内地报价</option>
                  	<option value="4" <s:if test="#request.quote.quoteFormate==4">selected</s:if> >大货价香港报价</option>
                  	<option value="5" <s:if test="#request.quote.quoteFormate==5">selected</s:if> >零售报价</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">立项编号</label>
	            <input disabled="disabled" name="quote.projectNum" id="projectNum" type="text" class="span3"  value="${quote.projectNum }" >
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">电机合计</label>
	            <input disabled="disabled" name="quote.engineTotal" id="engineTotal" type="text" class="span3"  value="${ quote.engineTotal}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">阻燃</label>
	            <input disabled="disabled" name="quote.flameTotal" id="flameTotal" type="text" class="span3"  value="${ quote.flameTotal}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">货到工地运费</label>
	            <input disabled="disabled" name="quote.arriveTransport" id="arriveTransport" type="text" class="span3"  value="${quote.arriveTransport }" >
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">量窗费</label>
	            <input disabled="disabled" name="quote.lcFre" id="lcFre" type="text" class="span3"  value="${ quote.lcFre}" >
	            <input disabled="disabled" class="blankInput" for="inputSuccess" style="" name="quote.titleCol1" value="${ quote.titleCol1}" id="titleCol1" type="text">
	            <input disabled="disabled" name="quote.inputCol1" id="inputCol1" type="text" class="span3"  value="${ quote.inputCol1}" >
	             <input disabled="disabled" class="blankInput" for="inputSuccess" style="" name="quote.titleCol2" value="${ quote.titleCol2}" id="titleCol2" type="text">
	            <input disabled="disabled" name="quote.inputCol2" id="inputCol2" type="text" class="span3"  value="${ quote.inputCol2}" >
	            
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	           <input disabled="disabled" class="blankInput" for="inputSuccess" style="" name="quote.titleCol3" value="${ quote.titleCol3}" id="titleCol3" type="text">
	            <input disabled="disabled" name="quote.inputCol3" id="inputCol3" type="text" class="span3"  value="${ quote.inputCol3}" >
	            <input disabled="disabled" class="blankInput" for="inputSuccess" style="" name="quote.titleCol4" value="${ quote.titleCol4}" id="titleCol4" type="text">
	            <input disabled="disabled" name="quote.inputCol4" id="inputCol4" type="text" class="span3"  value="${ quote.inputCol4}" >
	             <input disabled="disabled" class="blankInput" for="inputSuccess" style="" name="quote.titleCol5" value="${ quote.titleCol5}" id="titleCol5" type="text">
	            <input disabled="disabled" name="quote.inputCol5" id="inputCol5" type="text" class="span3"  value="${ quote.inputCol5}" >
	            
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">描述</label>
	            <input disabled="disabled" name="quote.desInfo" id="desInfo" type="text" class="span11"  value="${ quote.desInfo}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目名称</label>
	            <input disabled="disabled" name="quote.projectName" id="projectName" type="text" class="span3"  value="${ quote.projectName}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目地负责人</label>
	            <input disabled="disabled" name="quote.projectLocalPreson" id="projectLocalPreson" type="text" class="span3"  value="${ quote.projectLocalPreson}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售比例</label>
	            <input disabled="disabled" name="quote.projectLPSellInverse" id="projectLPSellInverse" type="text" class="span3"  value="${quote.projectLPSellInverse }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计公司</label>
	            <input disabled="disabled" name="quote.projectDesignComp" id="projectDesignComp" type="text" class="span3"  value="${ quote.projectDesignComp}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计地跟进人</label>
	            <input disabled="disabled" name="quote.designLocalPreson" id="designLocalPreson" type="text" class="span3"  value="${ quote.designLocalPreson}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售比例</label>
	            <input disabled="disabled" name="quote.designLPSelllnverse" id="designLPSelllnverse" type="text" class="span3"  value="${quote.designLPSelllnverse }">
	         </div>
	         <div class="clear"></div>
	         <br>
	          <div class="widget-title" style="width:100%;"> 
	            <select disabled="disabled" name="quote.fabricTitle" class="selectQuoteFabric">
		            <option value="0" <c:if test="${quote.fabricTitle=='0' }">selected=selected</c:if>>Quotation 报价表</option>
		            <option value="1" <c:if test="${quote.fabricTitle=='1' }">selected=selected</c:if>>Confirmation 合 同</option>
		            <option value="2" <c:if test="${quote.fabricTitle=='2' }">selected=selected</c:if>>INVOICE</option>
		        </select>
	             <span class="label label-info btn btn-primary btn-mini" style="float:left">产品选择</span>
	          </div>
	         <div class="widget-content nopadding" id="quoteFabricDiv" style="overflow-x:auto;width:100%;">
            	<%@include file="detailQuoteFabric.jsp"%>
      		</div>
      		 <div class="clear"></div>
      		 <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加急费</label>
	            <input disabled="disabled" name="quote.urgentCost" id="urgentCost" type="text" class="span3"  value="${ quote.urgentCost}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最低运费</label>
	            <input disabled="disabled" name="quote.lowestFreight" id="lowestFreight" type="text" class="span3"  value="${ quote.lowestFreight}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否含运费</label>
	             <select disabled="disabled" name="quote.isFreight" id="isFreight" style="width:202px;float: left;" class="span3">
	             	<option value="1" <s:if test="#request.quote.isFreight==1">selected</s:if> >是</option>
                  	<option value="0" <s:if test="#request.quote.isFreight==0">selected</s:if> >否</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加急费备注</label>
	            <input disabled="disabled" name="quote.urgentCostRmk" id="urgentCostRmk" type="text" class="span11"  value="${ quote.urgentCostRmk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最低运费备注</label>
	            <input disabled="disabled" name="quote.lowestFreightRmk" id="lowestFreightRmk" type="text" class="span11"  value="${ quote.lowestFreightRmk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">所含税率</label>
	            <input disabled="disabled" name="quote.containTax" id="containTax" type="text" class="span3"  value="${ quote.containTax}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">小计</label>
	            <input disabled="disabled" name="quote.subtotal" id="subtotal" type="text" class="span3"  value="${ quote.subtotal}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">优惠金额</label>
	            <input disabled="disabled" name="quote.discountMoney" id="discountMoney" type="text" class="span3"  value="${quote.discountMoney }"  >
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">总金额</label>
	            <input disabled="disabled" name="quote.sumMoney" id="sumMoney" type="text" class="span3"  value="${ quote.sumMoney}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">总金额备注</label>
	            <input disabled="disabled" name="quote.sumMoneyRemark" id="sumMoneyRemark" type="text" class="span6"  value="${ quote.sumMoneyRemark}" >
	            
	         </div>
	         <div class="clear"></div>
	          
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input disabled="disabled" name="quote.remk" id="remk" type="text" class="span11"  value="${ quote.remk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">合作条件和内容</label>
	          <textarea disabled="disabled" name="quote.item" id="item" class="quoteItem">${ quote.item}</textarea>
	          </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">For and On Behalf of</label>
	            <input disabled="disabled" name="quote.deputyCom" id="deputyCom" type="text" class="span3"  value="${ quote.deputyCom}" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Accept by</label>
	            <input disabled="disabled" name="quote.deputyName" id="deputyName" type="text" class="span3"  value="${quote.deputyName }" >
	          </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">实际金额</label>
	            <input name="designerExpense.realTotel" id="realTotel" type="text" class="span3"  value="${designerExpense.realTotel}" readonly="readonly">
	          </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计师1</label>
	             <select name="designerExpense.designer1" id="designer_1" style="width:202px;float: left;" class="span3" onchange="getCounselorRate(this)">
	             	 <option value="">请选择设计师</option>
	             	 <c:forEach var="d1" items="${designs}">
		             	<option value="${d1.codeName }" <c:if test='${designerExpense.designer1==d1.codeName }'>selected=selected</c:if>>${d1.codeName }</option>
		             </c:forEach>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计师2</label>
	             <select name="designerExpense.designer2" id="designer_2" style="width:202px;float: left;" class="span3" onchange="getCounselorRate(this)">
	             	 <option value="">请选择设计师</option>
	             	 <c:forEach var="d2" items="${designs}">
		             	<option value="${d2.codeName }" <c:if test='${designerExpense.designer2==d2.codeName }'>selected=selected</c:if>>${d2.codeName }</option>
		             </c:forEach>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计师3</label>
	             <select name="designerExpense.designer3" id="designer_3" style="width:202px;float: left;" class="span3" onchange="getCounselorRate(this)">
	             	<option value="">请选择设计师</option>
	             	 <c:forEach var="d3" items="${designs}">
		             	<option value="${d3.codeName }" <c:if test='${designerExpense.designer3==d3.codeName }'>selected=selected</c:if>>${d3.codeName }</option>
		             </c:forEach>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">顾问费率</label>
	            <input name="designerExpense.counselorRate1" id="counselorRate_1" type="text" class="span3"  value="${ designerExpense.counselorRate1}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">顾问费率</label>
	            <input name="designerExpense.counselorRate2" id="counselorRate_2" type="text" class="span3"  value="${ designerExpense.counselorRate2}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">顾问费率</label>
	            <input name="designerExpense.counselorRate3" id="counselorRate_3" type="text" class="span3"  value="${ designerExpense.counselorRate3}" readonly="readonly">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计费金额</label>
	            <input name="designerExpense.designMony1" id="designMony_1" type="text" class="span3"  value="${ designerExpense.designMony1}" >
	         	<label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计费金额</label>
	            <input name="designerExpense.designMony2" id="designMony_2" type="text" class="span3"  value="${ designerExpense.designMony2}" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计费金额</label>
	            <input name="designerExpense.designMony3" id="designMony_3" type="text" class="span3"  value="${ designerExpense.designMony3}" >
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">已付金额</label>
	            <input name="designerExpense.hasApply1" id="hasApply_1" type="text" class="span3"  value="${ designerExpense.hasApply1}" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">已付金额</label>
	            <input name="designerExpense.hasApply2" id="hasApply_2" type="text" class="span3"  value="${ designerExpense.hasApply2}" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">已付金额</label>
	            <input name="designerExpense.hasApply3" id="hasApply_3" type="text" class="span3"  value="${ designerExpense.hasApply3}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">未付金额</label>
	            <input name="designerExpense.hasNoApply1" id="hasNoApply_1" type="text" class="span3"  value="${ designerExpense.hasNoApply1}" onclick="sethasNoApply(this);">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">未付金额</label>
	            <input name="designerExpense.hasNoApply2" id="hasNoApply_2" type="text" class="span3"  value="${ designerExpense.hasNoApply2}" onclick="sethasNoApply(this);">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">未付金额</label>
	            <input name="designerExpense.hasNoApply3" id="hasNoApply_3" type="text" class="span3"  value="${ designerExpense.hasNoApply3}" onclick="sethasNoApply(this);">
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否收齐款</label>
	             <select name="designerExpense.isGetAll1" id="isGetAll_1" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isGetAll1=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isGetAll1=='是' }">selected=selected</c:if>>是</option>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否收齐款</label>
	             <select name="designerExpense.isGetAll2" id="isGetAll_2" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isGetAll2=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isGetAll2=='是' }">selected=selected</c:if>>是</option>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否收齐款</label>
	             <select name="designerExpense.isGetAll3" id="isGetAll_3" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isGetAll3=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isGetAll3=='是' }">selected=selected</c:if>>是</option>
	             </select>
	            </div>
	        <div class="clear"></div>  
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否支付</label>
	             <select name="designerExpense.isApply1" id="isApply_1" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isApply1=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isApply1=='是' }">selected=selected</c:if>>是</option>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否支付</label>
	             <select name="designerExpense.isApply2" id="isApply_2" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isApply2=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isApply2=='是' }">selected=selected</c:if>>是</option>
	             </select>
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否支付</label>
	             <select name="designerExpense.isApply3" id="isApply_3" style="width:202px;float: left;" class="span3">
	             <option value="否"  <c:if test="${designerExpense.isApply3=='否' }">selected=selected</c:if>>否</option>
                <option value="是" <c:if test="${designerExpense.isApply3=='是' }">selected=selected</c:if>>是</option>
	             </select>
	             </div>
	           <div class="clear"></div> 
	            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">支付时间</label>
	            <input name="designerExpense.applyDate1" id="applyDate_1" type="text" class="span3"  value="${ designerExpense.applyDate1}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">支付时间</label>
	            <input name="designerExpense.applyDate2" id="applyDate_2" type="text" class="span3"  value="${ designerExpense.applyDate2}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">支付时间</label>
	            <input name="designerExpense.applyDate3" id="applyDate_3" type="text" class="span3"  value="${ designerExpense.applyDate3}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </div>
	            <div class="clear"></div> 
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="designerExpense.remark1" id="remark_1" type="text" class="span3"  value="${ designerExpense.remark1}" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="designerExpense.remark2" id="remark_2" type="text" class="span3"  value="${ designerExpense.remark2}" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="designerExpense.remark3" id="remark_3" type="text" class="span3"  value="${ designerExpense.remark3}" >
	            </div>
	             <div class="clear"></div> 
            <div class="form-actions">
           <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">提交</button>&nbsp;&nbsp;
             <button type="button" class="btn btn-success" onclick="toBack();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
