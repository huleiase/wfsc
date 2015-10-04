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
		// $("#saveButton").attr("disabled", "disabled");
		$("#inputForm").submit();
	}
	function toBack(){
		window.location.href = "<%=basePath %>/admin/fabric_manager.Q";
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
          <form action="admin/fabric_save.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="fabric.id" id="fabricId" value="${fabric.id }">
           <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">工厂名称</label>
	            <input name="fabric.factoryName" id="factoryName" type="text" class="span2"  value="${ fabric.factoryName}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">工厂代码</label>
	            <input name="fabric.vcFactoryCode" id="vcFactoryCode" type="text" class="span2"  value="${fabric.vcFactoryCode }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">状态</label>
	            <select name="fabric.vcDis" id="vcDis" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="停用" <s:if test="#request.fabric.vcDis=='停用'">selected</s:if> >停用</option>
                  	<option value="启用" <s:if test="#request.fabric.vcDis=='启用'">selected</s:if> >启用</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">类型</label>
	            <input name="fabric.vcType" id="vcType" type="text" class="span2"  value="${ fabric.vcType}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">原厂型号</label>
	            <input name="fabric.vcBefModel" id="vcBefModel" type="text" class="span2"  value="${ fabric.vcBefModel}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">幅宽</label>
	            <input name="fabric.vcWidth" id="vcWidth" type="text" class="span2"  value="${fabric.vcWidth }">
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">卷长</label>
	            <input name="fabric.vcPieceLength" id="vcPieceLength" type="text" class="span2"  value="${ fabric.vcPieceLength}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">起订量</label>
	            <input name="fabric.vcMinNum" id="vcMinNum" type="text" class="span2"  value="${ fabric.vcMinNum}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">最小分格</label>
	            <input name="fabric.vcMinLattice" id="vcMinLattice" type="text" class="span2"  value="${fabric.vcMinLattice }">
	         </div>
	          <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">成分</label>
	            <input name="fabric.vcComposition" id="vcComposition" type="text" class="span2"  value="${ fabric.vcComposition}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">密度</label>
	            <input name="fabric.vcDensity" id="vcDensity" type="text" class="span2"  value="${ fabric.vcDensity}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">回位</label>
	            <input name="fabric.vcRepeat" id="vcRepeat" type="text" class="span2"  value="${fabric.vcRepeat }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">定高宽</label>
	            <input name="fabric.vcFixWidthHight" id="vcFixWidthHight" type="text" class="span2"  value="${ fabric.vcFixWidthHight}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">收缩</label>
	            <input name="fabric.vcShrink" id="vcShrink" type="text" class="span2"  value="${ fabric.vcShrink}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">耐磨度</label>
	            <input name="fabric.vcDr" id="vcDr" type="text" class="span2"  value="${fabric.vcDr }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">阻燃</label>
	            <input name="fabric.vcFr" id="vcFr" type="text" class="span2"  value="${ fabric.vcFr}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">环保</label>
	            <input name="fabric.vcEco" id="vcEco" type="text" class="span2"  value="${ fabric.vcEco}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">防污</label>
	            <input name="fabric.vcAntifouling" id="vcAntifouling" type="text" class="span2"  value="${fabric.vcAntifouling }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">色牢度</label>
	            <input name="fabric.vcLightFas" id="vcLightFas" type="text" class="span2"  value="${ fabric.vcLightFas}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">洗涤</label>
	            <input name="fabric.vcClean" id="vcClean" type="text" class="span2"  value="${ fabric.vcClean}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">用途</label>
	            <input name="fabric.vcApp" id="vcApp" type="text" class="span2"  value="${fabric.vcApp }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">进价价格</label>
	            <input name="fabric.vcOldPrice" id="vcOldPrice" type="text" class="span2"  value="${ fabric.vcOldPrice}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">进价单位</label>
	            <input name="fabric.vcMeasure" id="vcMeasure" type="text" class="span2"  value="${ fabric.vcMeasure}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">进价货币</label>
	            <input name="fabric.vcPriceCur" id="vcPriceCur" type="text" class="span2"  value="${fabric.vcPriceCur }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <c:if test="${isAdmin||isPurManager}">
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">工程系数</label>
	            <input name="fabric.vcProRatio" id="vcProRatio" type="text" class="span2"  value="${ fabric.vcProRatio}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">分销系数</label>
	            <input name="fabric.vcRetailRatio" id="vcRetailRatio" type="text" class="span2"  value="${ fabric.vcRetailRatio}">
	            </c:if>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">工程运费</label>
	            <input name="fabric.vcProFre" id="vcProFre" type="text" class="span2"  value="${ fabric.vcProFre}">
	            
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	         <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">零售运费</label>
	            <input name="fabric.vcRetFre" id="vcRetFre" type="text" class="span2"  value="${fabric.vcRetFre }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">采购折扣</label>
	            <input name="fabric.vcPurDis" id="vcPurDis" type="text" class="span2"  value="${ fabric.vcPurDis}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">备注一</label>
	            <input name="fabric.vcRemark1" id="vcRemark1" type="text" class="span2"  value="${ fabric.vcRemark1}">
	           
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">备注二</label>
	            <input name="fabric.vcRemark2" id="vcRemark2" type="text" class="span2"  value="${fabric.vcRemark2 }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">备注三</label>
	            <input name="fabric.vcRemark3" id="vcRemark3" type="text" class="span2"  value="${ fabric.vcRemark3}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价成本价</label>
	            <input name="fabric.dhjCost" id="dhjCost" type="text" class="span2"  value="${ fabric.dhjCost}">
	            
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <c:if test="${isAdmin||isPurManager}">
	         	<label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价内地系数</label>
	            <input name="fabric.dhjInlandRate" id="dhjInlandRate" type="text" class="span2"  value="${fabric.dhjInlandRate }">
	            </c:if>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价内地运费</label>
	            <input name="fabric.dhjInlandTransCost" id="dhjInlandTransCost" type="text" class="span2"  value="${ fabric.dhjInlandTransCost}">
	            <c:if test="${isAdmin||isPurManager}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价香港系数</label>
	            <input name="fabric.dhjHKRate" id="dhjHKRate" type="text" class="span2"  value="${ fabric.dhjHKRate}">
	            </c:if>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价香港运费</label>
	            <input name="fabric.dhjHKTransCost" id="dhjHKTransCost" type="text" class="span2"  value="${fabric.dhjHKTransCost }">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">大货价幅宽</label>
	            <input name="fabric.dhjWidth" id="dhjWidth" type="text" class="span2"  value="${ fabric.dhjWidth}">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:105px;">国产/进口</label>
	             <select name="fabric.importFactory" id="importFactory" style="width:170px">
	            	<option value="">请选择</option>
	             	<option value="0" <s:if test="#request.fabric.importFactory==0">selected</s:if> >国产</option>
                  	<option value="1" <s:if test="#request.fabric.importFactory==1">selected</s:if> >进口</option>
	             </select>
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
