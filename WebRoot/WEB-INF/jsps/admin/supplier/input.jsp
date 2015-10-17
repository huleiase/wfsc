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
		$("#saveButton").attr("disabled", "");
	}
	function toBack(){
		window.location.href = "<%=basePath %>/admin/supplier_manager.Q";
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
          <form action="admin/supplier_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="supplier.id" id="supplierId" value="${supplier.id }">
           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂名称</label>
	            <input name="supplier.vcFactoryName" id="vcFactoryName" type="text" class="span2"  value="${ supplier.vcFactoryName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂代码</label>
	            <input name="supplier.vcFactoryCode" id="vcFactoryCode" type="text" class="span2"  value="${supplier.vcFactoryCode }">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">状态</label>
	            <select name="supplier.vcDis" id="vcDis" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="停用" <s:if test="#request.supplier.vcDis=='停用'">selected</s:if> >停用</option>
                  	<option value="启用" <s:if test="#request.supplier.vcDis=='启用'">selected</s:if> >启用</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">产地</label>
	            <input name="supplier.vcPlaceProduce" id="vcPlaceProduce" type="text" class="span2"  value="${ supplier.vcPlaceProduce}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">产地编号</label>
	            <input name="supplier.vcFactoryNum" id="vcFactoryNum" type="text" class="span2"  value="${ supplier.vcFactoryNum}">
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="supplier.vcRemarks" id="vcRemarks" type="text" class="span2"  value="${supplier.vcRemarks }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">布料进货货币</label>
	            <input name="supplier.vcFabPriceCur" id="vcFabPriceCur" type="text" class="span2"  value="${ supplier.vcFabPriceCur}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">布料进货尺度</label>
	            <input name="supplier.vcFabMeasure" id="vcFabMeasure" type="text" class="span2"  value="${ supplier.vcFabMeasure}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">布料进货折扣</label>
	            <input name="supplier.vcFabPurDis" id="vcFabPurDis" type="text" class="span2"  value="${supplier.vcFabPurDis }">
	         </div>
	          <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">皮料进货货币</label>
	            <input name="supplier.vcLeaPriceCur" id="vcLeaPriceCur" type="text" class="span2"  value="${ supplier.vcLeaPriceCur}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">皮料进货尺度</label>
	            <input name="supplier.vcLeaMeasure" id="vcLeaMeasure" type="text" class="span2"  value="${ supplier.vcLeaMeasure}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">皮料进货折扣</label>
	            <input name="supplier.vcLeaPurDis" id="vcLeaPurDis" type="text" class="span2"  value="${supplier.vcLeaPurDis }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">布料最低运费</label>
	            <input name="supplier.vcFabMinFre" id="vcFabMinFre" type="text" class="span2"  value="${ supplier.vcFabMinFre}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">皮料最低费用</label>
	            <input name="supplier.vcLeaMinFre" id="vcLeaMinFre" type="text" class="span2"  value="${ supplier.vcLeaMinFre}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">地址</label>
	            <input name="supplier.vcFactoryAddr" id="vcFactoryAddr" type="text" class="span2"  value="${supplier.vcFactoryAddr }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">联系人</label>
	            <input name="supplier.vcLinkMan" id="vcLinkMan" type="text" class="span2"  value="${ supplier.vcLinkMan}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">联系电话</label>
	            <input name="supplier.vcTel" id="vcTel" type="text" class="span2"  value="${ supplier.vcTel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">传真</label>
	            <input name="supplier.vcFax" id="vcFax" type="text" class="span2"  value="${supplier.vcFax }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">电子邮件</label>
	            <input name="supplier.vcEmail" id="vcEmail" type="text" class="span2"  value="${ supplier.vcEmail}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">国内运费</label>
	            <input name="supplier.homeTransportCost" id="homeTransportCost" type="text" class="span2"  value="${ supplier.homeTransportCost}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">国内最低运费</label>
	            <input name="supplier.homeLowTransportCost" id="homeLowTransportCost" type="text" class="span2"  value="${supplier.homeLowTransportCost }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">香港运费</label>
	            <input name="supplier.hkTransportCost" id="hkTransportCost" type="text" class="span2"  value="${ supplier.hkTransportCost}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">香港最低运费</label>
	            <input name="supplier.hkLowTransportCost" id="hkLowTransportCost" type="text" class="span2"  value="${ supplier.hkLowTransportCost}">
	            <s:if test="#request.flag==1">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">零售系数</label>
	            <input name="supplier.retailCoefficient" id="retailCoefficient" type="text" class="span2"  value="${supplier.retailCoefficient }">
	            </s:if>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">品牌属性</label>
	            <input name="supplier.brandAttri" id="brandAttri" type="text" class="span2"  value="${ supplier.brandAttri}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">产品范围</label>
	            <input name="supplier.productRange" id="productRange" type="text" class="span2"  value="${ supplier.productRange}">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">订货折扣</label>
	            <input name="supplier.orderDis" id="orderDis" type="text" class="span2"  value="${supplier.orderDis }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">QQ</label>
	            <input name="supplier.qq" id="qq" type="text" class="span2"  value="${ supplier.qq}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">市场透明度</label>
	            <input name="supplier.marketClarity" id="marketClarity" type="text" class="span2"  value="${ supplier.marketClarity}">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂属性</label>
	            <input name="supplier.factoryPro" id="factoryPro" type="text" class="span2"  value="${supplier.factoryPro }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最小起订量</label>
	            <input name="supplier.MOQ" id="MOQ" type="text" class="span2"  value="${ supplier.MOQ}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最小增量</label>
	            <input name="supplier.leastIncrement" id="leastIncrement" type="text" class="span2"  value="${ supplier.leastIncrement}">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注二</label>
	            <input name="supplier.vcRemarks2" id="vcRemarks2" type="text" class="span8"  value="${ supplier.vcRemarks2}">
	         </div>
	         <div class="clear"></div><br/>
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
