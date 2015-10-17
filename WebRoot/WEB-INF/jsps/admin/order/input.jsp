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
		$("#orderStatus").val(status);
		$("#inputForm").submit();
		
	}
	function toBack(){
		window.location.href = basePath+"admin/order_manager.Q";
	}
	function setAddress(otheraddress){
		if(otheraddress=="其他地址"){
			$("#otherShipAddress").show();
		}else{
			$("#otherShipAddress").val("");
			$("#otherShipAddress").hide();
		}
	}
	$(function(){
		var otherShipAddress = $("#otherShipAddress").val();	
		if(otherShipAddress){
			$("#otherShipAddress").show();
		}else{
			$("#otherShipAddress").hide();
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
[class*="span"] {
    margin-left: 100px;
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
          <form action="admin/order_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="order.id" id="orderId" value="${order.id }">
          	<input type="hidden" name="order.orderStatus" id="orderStatus" value="${order.orderStatus }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">供应商</label>
	             <input name="order.supplier" id="supplier" type="text" class="span3"  value="${ order.supplier}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">订单日期</label>
	            <input id="orderDate" type="text" class="span3"  value="${ order.orderDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">联系人</label>
	             <input name="order.atten" id="atten" type="text" class="span3"  value="${ order.atten}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">经手人</label>
	            <input name="order.vcfrom" id="vcfrom" type="text" class="span3"  value="${ order.vcfrom}">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">电话</label>
	             <input name="order.attenTel" id="attenTel" type="text" class="span3"  value="${ order.attenTel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">电话</label>
	            <input name="order.vcfromTel" id="vcfromTel" type="text" class="span3"  value="${ order.vcfromTel}">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">传真</label>
	             <input name="order.attenFax" id="attenFax" type="text" class="span3"  value="${ order.attenFax}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">传真</label>
	            <input name="order.vcfromFax" id="vcfromFax" type="text" class="span3"  value="${ order.vcfromFax}">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">报价单号</label>
	             <input name="order.quantation" id="quantation" type="text" class="span3"  value="${ order.quantation}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">订单号</label>
	            <input name="order.orderNo" id="orderNo" type="text" class="span3"  value="${ order.orderNo}" readonly="readonly">
	         </div>
	         
	         <div class="clear"></div>
	          <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">下单地区</label>
	            <select name="order.areaZh" id="areaZh" style="width:202px;float: left;" class="span3">
	            	 <option value="广州" <c:if test="${order.areaZh=='广州'}">selected</c:if>>广州</option>
					 <option value="上海" <c:if test="${order.areaZh=='上海'}">selected</c:if>>上海</option>
					<option value="香港" <c:if test="${order.areaZh=='香港'}">selected</c:if>>香港</option>
					<option value="深圳" <c:if test="${order.areaZh=='深圳'}">selected</c:if>>深圳</option>
					<option value="北京" <c:if test="${order.areaZh=='北京'}">selected</c:if>>北京</option>
					<option value="广州分销" <c:if test="${order.areaZh=='广州分销'}">selected</c:if>>广州分销</option>
					<option value="北京分销" <c:if test="${order.areaZh=='北京分销'}">selected</c:if>>北京分销</option>	
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">收货人</label>
	            <input name="order.consignee" id="consignee" type="text" class="span3"  value="${ order.consignee}">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">物流方式1</label>
	            <input name="order.express1" id="express1" type="text" class="span3"  value="${order.express1 }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">快递单号1</label>
	            <input name="order.expressNumber1" id="expressNumber1" type="text" class="span3"  value="${ order.expressNumber1}">
	            <input name="order.expressMoney1" id="expressMoney1" type="text" class="span3"  value="${ order.expressMoney1}">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">物流方式2</label>
	            <input name="order.express2" id="express2" type="text" class="span3"  value="${ order.express2}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">快递单号2</label>
	            <input name="order.expressNumber2" id="expressNumber2" type="text" class="span3"  value="${ order.expressNumber2}">
	             <input name="order.expressMoney2" id="expressMoney2" type="text" class="span3"  value="${ order.expressMoney2}">
	           </div>
	             <div class="clear"></div>
	           <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">物流方式3</label>
	            <input name="order.express3" id="express3" type="text" class="span3"  value="${ order.express3}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">快递单号3</label>
	            <input name="order.expressNumber3" id="expressNumber3" type="text" class="span3"  value="${ order.expressNumber3}">
	             <input name="order.expressMoney3" id="expressMoney3" type="text" class="span3"  value="${ order.expressMoney3}">
	           </div>
	             <div class="clear"></div>
	             <div class="controls">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">码单</label>
	            <input name="order.madan" id="madan" type="text" class="span3"  value="${ order.madan}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">运费</label>
	            <input name="order.freight" id="freight" type="text" class="span3"  value="${ order.freight}">
	           </div>
	             <div class="clear"></div>
	            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">订单是否已确认</label>
	           	<select name="order.isOrderConfirm" id="isOrderConfirm" style="width:202px;float: left;" class="span3">
	           		<option value="0" <c:if test="${order.isOrderConfirm=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isOrderConfirm=='1'}">selected</c:if>>是</option>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已发货</label>
	           	<select name="order.isShipments" id="isShipments" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isShipments=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isShipments=='1'}">selected</c:if>>是</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已议价</label>
	           	<select name="order.isBargain" id="isBargain" style="width:202px;float: left;" class="span3">
	           		<option value="0" <c:if test="${order.isBargain=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isBargain=='1'}">selected</c:if>>是</option>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已付款</label>
	           	<select name="order.isPay" id="isPay" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isPay=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isPay=='1'}">selected</c:if>>是</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已裁剪</label>
	           	<select name="order.isCutting" id="isCutting" style="width:202px;float: left;" class="span3">
	           		<option value="0" <c:if test="${order.isCutting=='0'}">selected</c:if>>不需要裁剪</option>
	           		<option value="1" <c:if test="${order.isCutting=='1'}">selected</c:if>>否</option>		
					<option value="2" <c:if test="${order.isCutting=='2'}">selected</c:if>>是</option>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已有快递单号</label>
	           	<select name="order.isEMS" id="isEMS" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isEMS=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isEMS=='1'}">selected</c:if>>是</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">货物是否已上网</label>
	           	<select name="order.isOnline" id="isOnline" style="width:202px;float: left;" class="span3">
	           		<option value="0" <c:if test="${order.isOnline=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isOnline=='1'}">selected</c:if>>是</option>
	             </select>
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">货物是否已到HK</label>
	           	<select name="order.isToHK" id="isToHK" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isToHK=='0'}">selected</c:if>>货物不需要到HK</option>	
					<option value="1" <c:if test="${order.isToHK=='1'}">selected</c:if>>否</option>
					<option value="2" <c:if test="${order.isToHK=='2'}">selected</c:if>>是</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">货物是否已到JT</label>
	           	<select name="order.isToJT" id="isToJT" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isToJT=='0'}">selected</c:if>>货物不需要到JT</option>	
					<option value="1" <c:if test="${order.isToJT=='1'}">selected</c:if>>否</option>
					<option value="2" <c:if test="${order.isToJT=='2'}">selected</c:if>>是</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">货物QC是否完成</label>
	           	<select name="order.isQC" id="isQC" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isQC=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isQC=='1'}">selected</c:if>>是</option>
					<option value="2" <c:if test="${order.isQC=='2'}">selected</c:if>>QC异常</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">是否已交货</label>
	           	<select name="order.isStore" id="isStore" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isStore=='1'}">selected</c:if>>未交留仓</option>
					<option value="1" <c:if test="${order.isStore=='2'}">selected</c:if>>已交货给客户</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">订单是否完结</label>
	           	<select name="order.isOver" id="isOver" style="width:202px;float: left;" class="span3">
					<option value="0" <c:if test="${order.isOver=='0'}">selected</c:if>>否</option>	
					<option value="1" <c:if test="${order.isOver=='1'}">selected</c:if>>是</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          
	          
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">货期</label>
	            <input name="order.shipDateRemark" id="shipDateRemark" type="text" class="span3"  value="${ order.shipDateRemark}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">国内备注</label>
	             <input name="order.paqueteRemark" id="paqueteRemark" type="text" class="span3"  value="${ order.paqueteRemark}">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">付款方式</label>
	           	<select name="order.payment" id="payment" style="width:202px;float: left;" class="span3">
					<option value="预付款" <c:if test="${order.payment=='预付款'}">selected</c:if>>预付款</option>
					<option value="汇款" <c:if test="${order.payment=='汇款'}">selected</c:if>>汇款</option>
					<option value="月结" <c:if test="${order.payment=='月结'}">selected</c:if>>月结</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">收货地址</label>
	           	<select name="order.shipAddress" id="shipAddress" style="width:202px;float: left;" class="span3" onchange="setAddress(this.value)">
					<option value="广州市海珠区江泰路康泰街9号首层" <c:if test="${entity.shipAddress=='广州市海珠区江泰路康泰街9号首层'}">selected</c:if>>广州市海珠区江泰路康泰街9号首层</option>
					<option value="上海市闸北区中山北路470号3号2楼202-208室" <c:if test="${entity.shipAddress=='上海市闸北区中山北路470号3号2楼202-208室'}">selected</c:if>>上海市闸北区中山北路470号3号2楼202-208室</option>
					<option value="深圳市福田区泰然七路苍松大厦北座1501A" <c:if test="${entity.shipAddress=='深圳市福田区泰然七路苍松大厦北座1501A'}">selected</c:if>>深圳市福田区泰然七路苍松大厦北座1501A </option>
					<option value="北京市朝阳区广渠門外大街八號优士閣B座2901室" <c:if test="${entity.shipAddress=='北京市朝阳区广渠門外大街八號优士閣B座2901室'}">selected</c:if>>北京市朝阳区广渠門外大街八號优士閣B座2901室</option>
					<option value="香港北角英皇道338號華懋交易廣場二期1909室" <c:if test="${entity.shipAddress=='香港北角英皇道338號華懋交易廣場二期1909室'}">selected</c:if>>香港北角英皇道338號華懋交易廣場二期1909室</option>
					<option value="广州市越秀区东风中路268号广州交易广场1501-1502室" <c:if test="${entity.shipAddress=='广州市越秀区东风中路268号广州交易广场1501-1502室'}">selected</c:if>>广州市越秀区东风中路268号广州交易广场1501-1502室</option>
					<option value="其他地址">其他地址</option>
	             </select>
	              <input name="order.otherShipAddress" id="otherShipAddress" type="text" class="span3"  value="${order.otherShipAddress }">
	            </div>
	          <div class="clear"></div>
	            <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">包装材料</label>
	           	<select name="order.paquete2" id="paquete2" style="width:202px;float: left;" class="span3">
					<option value="1" <c:if test="${order.paquete2=='1'}">selected</c:if>>特硬纸卷筒,两编一塑</option>
					<option value="2" <c:if test="${order.paquete2=='2'}">selected</c:if>>特硬纸卷筒,纸箱(中间悬空)</option>
					<option value="3" <c:if test="${order.paquete2=='3'}">selected</c:if>>打木条</option>
					<option value="4" <c:if test="${order.paquete2=='4'}">selected</c:if>>打木架</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">注意事项</label>
	           	<select name="order.noteThing2" id="noteThing2" style="width:202px;float: left;" class="span3">
					<option value="1" <c:if test="${order.noteThing2=='1'}">selected</c:if>>请尽快出货！并提供运输单号！</option>
					<option value="2" <c:if test="${order.noteThing2=='2'}">selected</c:if>>急！请今天出货!并提供运输单号！</option>
					<option value="3" <c:if test="${order.noteThing2=='3'}">selected</c:if>>十万火急!请务必今天出货!并提供运输单号！</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	           <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">运输方式</label>
	           	<select name="order.transportMode" id="transportMode" style="width:202px;float: left;" class="span3">
					<option value="1" <c:if test="${order.transportMode=='1'}">selected</c:if>>顺丰</option>
					<option value="2" <c:if test="${order.transportMode=='2'}">selected</c:if>>顺丰四日件</option>
					<option value="3" <c:if test="${order.transportMode=='3'}">selected</c:if>>德邦精准卡航:广州芳村西朗站点</option>
					<option value="4" <c:if test="${order.transportMode=='4'}">selected</c:if>>德邦精准卡航:北京朝阳区百子湾站点</option>
					<option value="5" <c:if test="${order.transportMode=='5'}">selected</c:if>>和记(汽运-专线)</option>
					<option value="6" <c:if test="${order.transportMode=='6'}">selected</c:if>>海运快递(先垫付运费)</option>
					<option value="7" <c:if test="${order.transportMode=='7'}">selected</c:if>>普通海运(先垫付运费)</option>
					<option value="8" <c:if test="${order.transportMode=='8'}">selected</c:if>>待确认</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">shipping date</label>
	            <input name="order.shippingDate" id="shippingDate" type="text" class="span3"  value="${ order.shippingDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">Packing Remark</label>
	            <select name="order.packingRemark" id="packingRemark" style="width:202px;float: left;" class="span3">
					<option value="1" <c:if test="${order.packingRemark=='1'}">selected</c:if>>Pack it with roller and strenthen the packing</option>
					<option value="2" <c:if test="${order.packingRemark=='2'}">selected</c:if>>Pack it with Hard Paper Tube and double-layers Plastic Covering</option>
					<option value="3" <c:if test="${order.packingRemark=='3'}">selected</c:if>>Pack it with Hard Paper Tube and BOX</option>
					<option value="4" <c:if test="${order.packingRemark=='4'}">selected</c:if>>Pack it with Wooden Crate and keep it hanging</option>
					<option value="5" <c:if test="${order.packingRemark=='5'}">selected</c:if>>Pack and Fold it in Half If Available</option>
					<option value="6" <c:if test="${order.packingRemark=='6'}">selected</c:if>>Pack it with PAK</option>
					<option value="7" <c:if test="${order.packingRemark=='7'}">selected</c:if>>To be advised</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">PayMent</label>
	           	<select name="order.payment2" id="payment2" style="width:202px;float: left;" class="span3">
					<option value="1" <c:if test="${order.payment2=='1'}">selected</c:if>>Credit Card</option>
					<option value="2" <c:if test="${order.payment2=='2'}">selected</c:if>>Statement</option>
					<option value="3" <c:if test="${order.payment2=='3'}">selected</c:if>>Wire Transfer</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">shipping service</label>
	           	<select name="order.shippingService" id="shippingService" style="width:202px;float: left;" class="span3" >
					<option value="1" <c:if test="${order.shippingService=='1'}">selected</c:if>>Fedex Intl Priority on our A/C:2820 72386</option>
					<option value="2" <c:if test="${order.shippingService=='2'}">selected</c:if>>Fedex Intl Economy on our A/C:2820 72386</option>
					<option value="3" <c:if test="${order.shippingService=='3'}">selected</c:if>>UPS Saver on your A/C and invoice us</option>
					<option value="4" <c:if test="${order.shippingService=='4'}">selected</c:if>>UPS Expedited on your A/C and invoice us</option>
					<option value="5" <c:if test="${order.shippingService=='5'}">selected</c:if>>DHL on your A/C and invoice us</option>
					<option value="6" <c:if test="${order.shippingService=='6'}">selected</c:if>>TNT on your A/C and invoice us</option>
					<option value="7" <c:if test="${order.shippingService=='7'}">selected</c:if>>To be advised</option>
					<option value="8" <c:if test="${order.shippingService=='8'}">selected</c:if>>DHL on our A/C 967947655</option>
	             </select>
	            </div>
	          <div class="clear"></div>
	          <div class="widget-title"> 
	          </div>
	         <div class="widget-content nopadding" id="orderFabricDiv" style="overflow-x:auto;width:1124px;">
            	<%@include file="orderFabric.jsp"%>
      		</div>
      		 <div class="clear"></div>
      		<div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:120px;">总金额</label>
	             <input name="order.sumMoney" id="sumMoney" type="text" class="span3"  value="${ order.sumMoney}">
	        </div>
      		
      		 <div class="clear"></div>
            <div class="form-actions">
            <s:if test="#request.isView==0">
            <s:if test="#request.order.orderStatus!=3">
             <button type="button" class="btn btn-success" onclick="checkForm(1)" id="saveButton">提交</button>&nbsp;&nbsp;
            </s:if>
            <s:else>
            <button type="button" class="btn btn-success" onclick="checkForm(3)" id="saveButton">审核</button>&nbsp;&nbsp;
            </s:else>
            </s:if>
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
