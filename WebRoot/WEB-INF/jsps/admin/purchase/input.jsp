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
	function checkForm(status){
		var error = "";
		$("[id^='vcAssignAutor']").each(function(i){
		   var value = $(this).val();
		   if(!value){
		   		error += "错误";
		   		
		   }
		 });
		 if(error.length>0&&status=='3'){
		 	art.dialog({title:'提示',content:'请选择采购员',ok:true});
		 }else{
		 	$("#orderStatus").val(status);
			$("#inputForm").submit();
		 	$("#saveButton").attr("disabled",true);
		 }
		
	}
	function toBack(){
		//window.location.href = basePath+"admin/purchase_manager.Q";
		history.go(-1);
	}
	function setExpressCom(otherExpressCom){
		if(otherExpressCom=="其他"){
			$("#otherExpressCom").show();
		}else{
			$("#otherExpressCom").val("");
			$("#otherExpressCom").hide();
		}
	}
	function setAddress(otheraddress){
		if(otheraddress=="其他地址"){
			$("#otheraddress").show();
		}else{
			$("#otheraddress").val("");
			$("#otheraddress").hide();
		}
	}
	$(function(){
		var otherExpressCom = $("#otherExpressCom").val();	
		var otheraddress = $("#otheraddress").val();	
		if(otherExpressCom){
			$("#otherExpressCom").show();
		}else{
			$("#otherExpressCom").hide();
		}
		if(otheraddress){
			$("#otheraddress").show();
		}else{
			$("#otheraddress").hide();
		}
	})
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
          <form action="admin/purchase_save.Q" method="post" class="form-horizontal" id="inputForm">
          <input type="hidden" name="orderStatus" value="${orderStatus }">
          <input type="hidden" name="currPageNo" value="${currPageNo }">
          <input type="hidden" name="pageSize" value="${pageSize }">
          <input type="hidden" name="oper" value="${oper }">
          
          	<input type="hidden" name="purchase.id" id="purchaseId" value="${purchase.id }">
          	<input type="hidden" name="purchase.orderStatus" id="orderStatus" value="${purchase.orderStatus }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Date</label>
	             <input name="purchase.contractDate" id="contractDate" type="text" class="span3"  value="${ purchase.contractDate}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价单号</label>
	            <input id="projectNum" type="text" class="span3"  value="${ purchase.quote.projectNum}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">货期要求</label>
	            <select name="purchase.deliveryRequirements" id="deliveryRequirements" style="width:202px;float: left;" class="span3">
	            	 <option value="">请选择</option>
	            	 <option value="正常" <c:if test="${purchase.deliveryRequirements=='正常'}">selected</c:if> >正常</option>
                	<option value="铁期" <c:if test="${purchase.deliveryRequirements=='铁期'}">selected</c:if> >铁期</option>
                	<option value="特急" <c:if test="${purchase.deliveryRequirements=='特急'}">selected</c:if> >特急</option>
                	<option value="急" <c:if test="${purchase.deliveryRequirements=='急'}">selected</c:if> >急</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">依凭单</label>
	            <select name="purchase.voucher" id="voucher" style="width:202px;float: left;" class="span3">
	            	 <option value="">请选择</option>
	            	 <option value="报价合同" <c:if test="${purchase.voucher=='报价合同'}">selected</c:if> >报价合同</option>
                	<option value="加工单" <c:if test="${purchase.voucher=='加工单'}">selected</c:if> >加工单</option>
                	<option value="定造合同" <c:if test="${purchase.voucher=='定造合同'}">selected</c:if> >定造合同</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">差额因素</label>
	            <input name="purchase.balanceFactor" id="balanceFactor" type="text" class="span3"  value="${ purchase.balanceFactor}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">单号</label>
	            <input name="purchase.contractNo" id="contractNo" type="text" class="span3"  value="${purchase.contractNo }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目</label>
	            <input id="projectName" type="text" class="span3"  value="${ purchase.quote.projectName}" readonly="readonly">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">发货方式</label>
	            <select name="purchase.paymentType" id="paymentType" style="width:202px;float: left;" class="span3">
                	<option value="到付" <c:if test="${purchase.paymentType=='到付'}">selected</c:if> >到付</option>
                	<option value="寄付" <c:if test="${purchase.paymentType=='寄付'}">selected</c:if> >寄付</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">付款方式</label>
	            <select name="purchase.expressCom" id="expressCom" style="width:202px;float: left;" class="span3" onchange="setExpressCom(this.value);">
                	<option value="顺丰" <c:if test="${purchase.expressCom=='顺丰'}">selected</c:if> >顺丰</option>
                	<option value="德邦" <c:if test="${purchase.expressCom=='德邦'}">selected</c:if> >德邦</option>
                	<option value="普通快递" <c:if test="${purchase.expressCom=='普通快递'}">selected</c:if> >普通快递</option>
                	<option value="其他" <c:if test="${purchase.expressCom=='其他'}">selected</c:if> >其他</option>
	             </select>
	             <input name="purchase.otherExpressCom" id="otherExpressCom" type="text" class="span3"  value="${purchase.otherExpressCom }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">差旅费</label>
	            <input name="purchase.travelExpenses" id="travelExpenses" type="text" class="span3"  value="${ purchase.travelExpenses}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加工费</label>
	            <input name="purchase.processFee" id="processFee" type="text" class="span3"  value="${ purchase.processFee}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">安装费</label>
	            <input name="purchase.installFee" id="installFee" type="text" class="span3"  value="${ purchase.installFee}">
	            </div>
	             <div class="clear"></div>
	             <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">材料合计</label>
	            <input name="purchase.clTotal" id="clTotal" type="text" class="span3"  value="${ purchase.clTotal}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">其他</label>
	            <input name="purchase.otherFre" id="otherFre" type="text" class="span3"  value="${ purchase.otherFre}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">订金确认</label>
	            <input name="purchase.depositConfirmation" id="depositConfirmation" type="text" class="span3"  value="${ purchase.depositConfirmation}">
	            </div>
	             <div class="clear"></div>
	              <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">定金确认人</label>
	            <input name="purchase.dcPerson" id="dcPerson" type="text" class="span3"  value="${ purchase.dcPerson}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">余额确认</label>
	            <input name="purchase.balanceConfirmation" id="balanceConfirmation" type="text" class="span3"  value="${ purchase.balanceConfirmation}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">余额确认人</label>
	            <input name="purchase.bcPerson" id="bcPerson" type="text" class="span3"  value="${ purchase.bcPerson}">
	            </div>
	             <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Expected Arrival Date</label>
	            <input name="purchase.remarks" id="remarks" type="text" class="span3"  value="${ purchase.remarks}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Shippment Address</label>
	            <select name="purchase.address" id="address" style="width:202px;float: left;" class="span3" onchange="setAddress(this.value);">
                	<option value="广州市越秀区东风中路268号广州交易广场1501-1502" <c:if test="${purchase.address=='广州市越秀区东风中路268号广州交易广场1501-1502'}">selected</c:if> >广州市越秀区东风中路268号广州交易广场1501-1502</option>
                	<option value="广州市海珠区江泰路康泰街9号首层" <c:if test="${purchase.address=='广州市海珠区江泰路康泰街9号首层'}">selected</c:if> >广州市海珠区江泰路康泰街9号首层</option>
                	<option value="上海市闸北区中山北路470号3号楼2楼202-208室" <c:if test="${purchase.address=='上海市闸北区中山北路470号3号楼2楼202-208室'}">selected</c:if> >上海市闸北区中山北路470号3号楼2楼202-208室</option>
                	<option value="深圳市福田区天安高尔夫花园三期珑园4栋6B" <c:if test="${purchase.address=='深圳市福田区天安高尔夫花园三期珑园4栋6B'}">selected</c:if> >深圳市福田区天安高尔夫花园三期珑园4栋6B</option>
                	<option value="北京市朝阳区东柏街9号天之骄子5号楼2单元202室" <c:if test="${purchase.address=='北京市朝阳区东柏街9号天之骄子5号楼2单元202室'}">selected</c:if> >北京市朝阳区东柏街9号天之骄子5号楼2单元202室</option>
                	<option value="香港北角英皇道338號華懋交易廣場1909室" <c:if test="${purchase.address=='香港北角英皇道338號華懋交易廣場1909室'}">selected</c:if> >香港北角英皇道338號華懋交易廣場1909室</option>
                	<option value="其他地址" <c:if test="${purchase.address=='其他地址'}">selected</c:if> >其他地址</option>
	             </select>
	             <input name="purchase.otheraddress" id="otheraddress" type="text" class="span3"  value="${purchase.otheraddress }">
	         </div>
	         <div class="clear"></div>
	          <div class="widget-title" style="width:996px;"> 
	          </div>
	         <div class="widget-content nopadding" id="purchaseFabricDiv" style="overflow-x:auto;width:996px;">
            	<%@include file="purchaseFabric.jsp"%>
      		</div>
      		 <div class="clear"></div>
            <div class="form-actions">
             <s:if test="#request.isView==0">
              <s:if test="#request.oper==2">
             <button type="button" class="btn btn-success" onclick="checkForm(3)" id="saveButton">审核</button>
             </s:if>
             <s:if test="#request.oper==9">
             <button type="button" class="btn btn-success" onclick="checkForm(9)" id="saveButton">提交</button>
             </s:if>
             &nbsp;&nbsp;
             <button type="button" class="btn btn-success" onclick="toBack();">返回</button>
             </s:if>
             <s:else>
              <button type="button" class="btn btn-success" onclick="window.close();">关闭</button>
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
