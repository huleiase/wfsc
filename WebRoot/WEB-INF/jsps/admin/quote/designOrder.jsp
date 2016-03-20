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
		//window.location.href = basePath+"admin/purchase_manager.Q";
		history.go(-1);
	}
	function setShareMoney(obj){
				var jObj =  $(obj);
				var objId = jObj.attr("id");
				var objValue = jObj.val();
				var idAttr = objId.split("_");
				var suffixId  = idAttr[1];
				if(objValue==""){
					return ;
				}
				var shareMonew = Number($("#sumMoney").val())*Number(objValue)/100;
				$("#shareMony_"+suffixId).val(shareMonew.toFixed(2));
				
			}
			
			function setShareRate(obj){
				var jObj =  $(obj);
				var objId = jObj.attr("id");
				var objValue = jObj.val();
				var idAttr = objId.split("_");
				var suffixId  = idAttr[1];
				if(objValue==""){
					return ;
				}
				var shareRate = Number(objValue)/Number($("#sumMoney").val())*100;
				$("#shareRate_"+suffixId).val(shareRate.toFixed(2));
				
			}
			
			function setSharetotle(){
				var shareMony1 = $("#shareMony_1").val();
				if(!shareMony1){
					shareMony1 = 0;
				}
				var shareMony2 = $("#shareMony_2").val();
				if(!shareMony2){
					shareMony2 = 0;
				}
				var shareMony3 = $("#shareMony_3").val();
				if(!shareMony3){
					shareMony3 = 0;
				}
				var shareMony4 = $("#shareMony_4").val();
				if(!shareMony4){
					shareMony4 = 0;
				}
				var shareMony5 = $("#shareMony_5").val();
				if(!shareMony5){
					shareMony5 = 0;
				}
				var sharetotle = Number(shareMony1)+Number(shareMony2)+Number(shareMony3)+Number(shareMony4)+Number(shareMony5);
				$("#sharetotle").val(sharetotle.toFixed(2));
				
			}
			
			function setHasApplyTotle(){
				var frontMoney = $("#frontMoney").val();
				if(!frontMoney){
					frontMoney = 0;
				}
				var planMoney1 = $("#planMoney1").val();
				if(!planMoney1){
					planMoney1 = 0;
				}
				var planMoney2 = $("#planMoney2").val();
				if(!planMoney2){
					planMoney2 = 0;
				}
				var planMoney3 = $("#planMoney3").val();
				if(!planMoney3){
					planMoney3 = 0;
				}
				var qualityMoney = $("#qualityMoney").val();
				if(!qualityMoney){
					qualityMoney = 0;
				}
				var hasApplyTotle = Number(frontMoney)+Number(planMoney1)+Number(planMoney2)+Number(planMoney3)+Number(qualityMoney);
				$("#hasApplyTotle").val(hasApplyTotle.toFixed(2));
			}
			
			function setNoApply(){
				var sumMony = Number($("#sumMoney").val()).toFixed(2);
				var hasApplyTotle = $("#hasApplyTotle").val();
				if(!hasApplyTotle){
					hasApplyTotle=0
				}
				$("#hasNoApplyTotle").val(sumMony-hasApplyTotle);
			}
			
			function setYuE(){
				var sumMony = Number($("#sumMoney").val()).toFixed(2);
				var mon1 = $("#mon1").val();
				if(!mon1){
					mon1=0;
				}
				var mon2 = $("#mon2").val();
				if(!mon2){
					mon2=0;
				}
				var mon3 = $("#mon3").val();
				if(!mon3){
					mon3=0;
				}
				var mon4 = $("#mon4").val();
				if(!mon4){
					mon4=0;
				}
				var mon5 = $("#mon5").val();
				if(!mon5){
					mon5=0;
				}
				var mon6 = $("#mon6").val();
				if(!mon6){
					mon6=0;
				}
				var mon7 = $("#mon7").val();
				if(!mon7){
					mon7=0;
				}
				var mon8 = $("#mon8").val();
				if(!mon8){
					mon8=0;
				}
				var mon9 = $("#mon9").val();
				if(!mon9){
					mon9=0;
				}
				var mon10 = $("#mon10").val();
				if(!mon10){
					mon10=0;
				}
				var mon11 = $("#mon11").val();
				if(!mon11){
					mon11=0;
				}
				var mon12 = $("#mon12").val();
				if(!mon12){
					mon12=0;
				}
				var yue = sumMony-mon1-mon2-mon3-mon4-mon5-mon6-mon7-mon8-mon9-mon10-mon11-mon12;
				$("#yue").val(yue);
			}
			$(function(){
				setNoApply();
				setYuE();
			});
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
    width: 85px;
}
input.span11 {
    width: 840px;
}
input.span6 {
    width: 514px;
}
.quoteItem {
    height: 160px;
    width: 840px;
}
.selectQuoteFabric {
    float: left;
    height: 28px;
    margin-left: 500px;
    margin-right: 15px;
    margin-top: 5px;
    width: 166px;
}
.form-horizontal .controls {
    margin-left: 50px;
    padding: 10px 0;
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
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="admin/quote_saveDO.Q" method="post" class="form-horizontal" id="inputForm">
          <input type="hidden" id="sumMoney" value="${designerOrder.sumMoney }">
          <input type="hidden" name="designerOrder.id" value="${designerOrder.id }">
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊地</label>
	            <input name="designerOrder.shareArea1" id="shareArea_1" type="text" class="span3" value="GZ" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊比例</label>
	            <input name="designerOrder.shareRate1" id="shareRate_1" type="text" class="span3" value="${designerOrder.shareRate1}" onblur="setShareMoney(this)" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊金额</label>
	            <input name="designerOrder.shareMony1" id="shareMony_1" type="text" class="span3" value="${designerOrder.shareMony1}" onblur="setShareRate(this)">
	            </div>
	             <div class="clear"></div>
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊地</label>
	            <input name="designerOrder.shareArea2" id="shareArea_2" type="text" class="span3" value="SH" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊比例</label>
	            <input name="designerOrder.shareRate2" id="shareRate_2" type="text" class="span3" value="${designerOrder.shareRate2}" onblur="setShareMoney(this)" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊金额</label>
	            <input name="designerOrder.shareMony2" id="shareMony_2" type="text" class="span3" value="${designerOrder.shareMony2}" onblur="setShareRate(this)">
	            </div>
	             <div class="clear"></div>
	              <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊地</label>
	            <input name="designerOrder.shareArea3" id="shareArea_3" type="text" class="span3" value="BJ" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊比例</label>
	            <input name="designerOrder.shareRate3" id="shareRate_3" type="text" class="span3" value="${designerOrder.shareRate3}" onblur="setShareMoney(this)" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊金额</label>
	            <input name="designerOrder.shareMony3" id="shareMony_3" type="text" class="span3" value="${designerOrder.shareMony3}" onblur="setShareRate(this)">
	            </div>
	             <div class="clear"></div>
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊地</label>
	            <input name="designerOrder.shareArea4" id="shareArea_4" type="text" class="span3" value="SZ" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊比例</label>
	            <input name="designerOrder.shareRate4" id="shareRate_4" type="text" class="span3" value="${designerOrder.shareRate4}" onblur="setShareMoney(this)" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊金额</label>
	            <input name="designerOrder.shareMony4" id="shareMony_4" type="text" class="span3" value="${designerOrder.shareMony4}" onblur="setShareRate(this)">
	            </div>
	             <div class="clear"></div>
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊地</label>
	            <input name="designerOrder.shareArea5" id="shareArea_5" type="text" class="span3" value="HK" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊比例</label>
	            <input name="designerOrder.shareRate5" id="shareRate_5" type="text" class="span3" value="${designerOrder.shareRate5}" onblur="setShareMoney(this)" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊金额</label>
	            <input name="designerOrder.shareMony5" id="shareMony_5" type="text" class="span3" value="${designerOrder.shareMony5}" onblur="setShareRate(this)">
	            </div>
	             <div class="clear"></div>
	             
	             
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">分摊合计</label>
	            <input name="designerOrder.sharetotle" id="sharetotle" type="text" value="${designerOrder.sharetotle}" readonly="readonly" onclick="setSharetotle();" class="span3">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">类型</label>
	            <input name="designerOrder.type" id="type" type="text" value="${designerOrder.type}" class="span3">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">发票</label>
	            <select id="isInvoice" name="designerOrder.isInvoice" style="width:202px;float: left;" class="span3">
	            	 <option value="是" <c:if test="${designerOrder.isInvoice=='是' }">selected</c:if> >是</option>
	            	 <option value="否" <c:if test="${designerOrder.isInvoice=='否' }">selected</c:if> >否</option>
	             </select>
	            </div>
	             <div class="clear"></div>
	              <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">收款时间</label>
	            <input id="gatheringDate" name="gatheringDate" value="${designerOrder.gatheringDate }" type="text" class="span3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">定金</label>
	            <input name="designerOrder.frontMoney" id="frontMoney" type="text" value="${designerOrder.frontMoney}" class="span3">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">进度款1</label>
	            <input name="designerOrder.planMoney1" id="planMoney1" type="text" value="${designerOrder.planMoney1}" class="span3" >
	            </div>
	             <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">进度款2</label>
	            <input name="designerOrder.planMoney2" id="planMoney2" type="text" value="${designerOrder.planMoney2}" class="span3" >
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">进度款3</label>
	            <input name="designerOrder.planMoney3" id="planMoney3" type="text" value="${designerOrder.planMoney3}" class="span3" >
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">保质金</label>
	            <input name="designerOrder.qualityMoney" id="qualityMoney" type="text" value="${designerOrder.qualityMoney}" class="span3">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">已付合计</label>
	            <input name="designerOrder.hasApplyTotle" id="hasApplyTotle" type="text" value="${designerOrder.hasApplyTotle}" readonly="readonly" onclick="setHasApplyTotle();" class="span3">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">收款地</label>
	            <input name="designerOrder.gatheringArea" id="gatheringArea" type="text" value="${designerOrder.gatheringArea}" class="span3">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否付清</label>
	            <select id="isPayTotle" name="designerOrder.isPayTotle" style="width:202px;float: left;" class="span3">
	            	 <option value="是" <c:if test="${designerOrder.isPayTotle=='是' }">selected</c:if> >是</option>
	            	 <option value="否" <c:if test="${designerOrder.isPayTotle=='否' }">selected</c:if> >否</option>
	             </select>
	            </div>
	             <div class="clear"></div>
	             
	           <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">一月收款</label>
	            <input name="designerOrder.mon1" id="mon1" type="text" value="${designerOrder.mon1}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">二月收款</label>
	            <input name="designerOrder.mon2" id="mon2" type="text" value="${designerOrder.mon2}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">三月收款</label>
	            <input name="designerOrder.mon3" id="mon3" type="text" value="${designerOrder.mon3}" class="span3" >
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">四月收款</label>
	            <input name="designerOrder.mon4" id="mon4" type="text" value="${designerOrder.mon4}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">五月收款</label>
	            <input name="designerOrder.mon5" id="mon5" type="text" value="${designerOrder.mon5}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">六月收款</label>
	            <input name="designerOrder.mon6" id="mon6" type="text" value="${designerOrder.mon6}" class="span3" >
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">七月收款</label>
	            <input name="designerOrder.mon7" id="mon7" type="text" value="${designerOrder.mon7}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">八月收款</label>
	            <input name="designerOrder.mon8" id="mon8" type="text" value="${designerOrder.mon8}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">九月收款</label>
	            <input name="designerOrder.mon9" id="mon9" type="text" value="${designerOrder.mon9}" class="span3" >
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">十月收款</label>
	            <input name="designerOrder.mon10" id="mon10" type="text" value="${designerOrder.mon10}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">十一月收款</label>
	            <input name="designerOrder.mon11" id="mon11" type="text" value="${designerOrder.mon11}" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">十二月收款</label>
	            <input name="designerOrder.mon12" id="mon12" type="text" value="${designerOrder.mon12}" class="span3" >
	         </div>
	         <div class="clear"></div>
	         
	            <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">未付余额</label>
	            <input type="text" id="hasNoApplyTotle" name="designerOrder.hasNoApplyTotle" value="${designerOrder.hasNoApplyTotle }" onclick="setNoApply();" class="span3" >
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">余额</label>
	            <input type="text" value="${designerOrder.yue }"  id="yue" onclick="setYuE();" class="span3" name="designerOrder.yue">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="designerOrder.remark" id="remark" type="text" value="${designerOrder.remark}" class="span3" >
	         </div>
	         <div class="clear"></div>
            <div class="form-actions">
             <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">提交</button>
             &nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="window.close();">关闭</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
