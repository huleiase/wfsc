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
          <form action="admin/storeFabric_save.Q" method="post" class="form-horizontal" id="inputForm">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">工厂编号</label>
	            ${ storeFabric.vcFactoryCode}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目名称</label>
	            ${ storeFabric.vcProject}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">原厂型号</label>
	            ${storeFabric.vcModelNum }
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	         <c:if test="${permission=='1'}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">供应商</label>
	            ${ storeFabric.supplie}
	            </c:if>
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">色号</label>
	          ${ storeFabric.vcColorNum}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">对照码</label>
	            ${storeFabric.htCode }
	         </div>
	         <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">产品编号</label>
	            ${ storeFabric.fabricNo}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">支付方式</label>
	            ${ storeFabric.payment}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">订单编号</label>
	            ${storeFabric.orderNo }
	         </div>
	         <div class="clear"></div>
	         <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">实定量</label>
	            ${ storeFabric.vcQuoteNum}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">实际到货量</label>
	            ${ storeFabric.vcRealityAog}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">剩余量</label>
	            ${storeFabric.surplus }
	         </div>
	         <div class="clear"></div>
	          <div class="clear"></div><div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            ${ storeFabric.vcPurchaseRmk}
	         </div>
	         <div class="clear"></div>
            <div class="form-actions">
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
