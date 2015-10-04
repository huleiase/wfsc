<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="js/quote.js" type="text/javascript"></script>
<link rel="stylesheet" href="js/autocomplete/autocomplete.css" type="text/css">
<script src="js/autocomplete/autoComplete.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	function checkForm(){
		$("#inputForm").submit();
	}
	function toBack(){
		window.location.href = basePath+"admin/quote_manager.Q";
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
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
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
          <form action="admin/quote_saveDesignQuote.Q" method="post" class="form-horizontal" id="inputForm">
          	<input type="hidden" name="quote.id" id="quoteId" value="${quote.id }">
          	<input type="hidden" name="quote.curUserName" id="curUserName" value="${quote.curUserName }">
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司</label>
	            ${ quote.vcAttn}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司电话</label>
	            ${ quote.vcAttnTel}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价公司传真</label>
	            ${quote.vcAttnFax }
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">询价客户名字</label>
	            ${ quote.vcAttnName}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">客户手机号码</label>
	           ${ quote.vcAttnPhone}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">客户电子邮箱</label>
	            ${quote.vcAttnEmail }
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价公司</label>
	            ${quote.vcFrom }
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地电话</label>
	            ${ quote.vcFormTel}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地传真</label>
	            ${quote.vcFormFax }
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">跟进的销售</label>
	            ${ quote.vcFormName}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售的电话</label>
	            ${ quote.vcFormPhone}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Date</label>
	            ${ quote.vcFormDate}
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价地</label>
	             ${ quote.vcQuoteLocal}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售</label>
	             ${ quote.vcSalesman}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Ref no</label>
	             ${ quote.vcQuoteNum}
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售1</label>
	             ${ quote.vcSalesman1}
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售2</label>
	             ${ quote.vcSalesman2}
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售3</label>
	             ${ quote.vcSalesman3}
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售4</label>
	             ${ quote.vcSalesman4}
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售组</label>
	            ${ quote.groupName}
	              <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加工费</label>
	              ${ quote.vcProcessFre}
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">安装费</label>
	            ${ quote.vcInstallFre}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">后处理</label>
	           ${ quote.vcAftertreatment}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">其它</label>
	            ${quote.vcOther }
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">尊敬的</label>
	           ${ quote.customer}
	           <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">报价</label>
	           ${ quote.quoteMethod}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">立项编号</label>
	            ${quote.projectNum }
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">电机合计</label>
	            ${ quote.engineTotal}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">阻燃</label>
	            ${ quote.flameTotal}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">货到工地运费</label>
	            ${quote.arriveTransport }
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">量窗费</label>
	            ${ quote.lcFre}
	            <s:if test="#request.quote.titleCol1!=null">
	             <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">${ quote.titleCol1}</label>
	             ${ quote.inputCol1}
	            </s:if>
	           <s:if test="#request.quote.titleCol2!=null">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;"> ${ quote.titleCol2}</label>
	           ${ quote.inputCol2}
	           </s:if>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <s:if test="#request.quote.titleCol3!=null">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;"> ${ quote.titleCol3}</label>
	           ${ quote.inputCol3}
	           </s:if>
	           <s:if test="#request.quote.titleCol4!=null">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;"> ${ quote.titleCol4}</label>
	           ${ quote.inputCol4}
	           </s:if>
	            <s:if test="#request.quote.titleCol5!=null">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;"> ${ quote.titleCol5}</label>
	           ${ quote.inputCol5}
	           </s:if>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">描述</label>
	          ${ quote.desInfo}
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目名称</label>
	            ${ quote.projectName}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">项目地负责人</label>
	            ${ quote.projectLocalPreson}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售比例</label>
	            ${quote.projectLPSellInverse }
	         </div>
	         <div class="clear"></div>
	         <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计公司</label>
	            ${ quote.projectDesignComp}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">设计地跟进人</label>
	            ${ quote.designLocalPreson}
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">销售比例</label>
	            ${quote.designLPSelllnverse }
	         </div>
	         <div class="clear"></div>
	         <br>
	          <div class="widget-title"> 
	            <select name="quote.fabricTitle" class="selectQuoteFabric">
		            <option value="0" <c:if test="${quote.fabricTitle=='0' }">selected=selected</c:if>>Quotation 报价表</option>
		            <option value="1" <c:if test="${quote.fabricTitle=='1' }">selected=selected</c:if>>Confirmation 合 同</option>
		            <option value="2" <c:if test="${quote.fabricTitle=='2' }">selected=selected</c:if>>INVOICE</option>
		        </select>
	          </div>
	         <div class="widget-content nopadding" id="quoteFabricDiv" style="overflow-x:auto;width:1105px;">
            	<%@include file="detailQuoteFabric.jsp"%>
      		</div>
      		 <div class="clear"></div>
      		 <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加急费</label>
	            <input name="quote.urgentCost" id="urgentCost" type="text" class="span3"  value="${ quote.urgentCost}" onkeyup="setSumMoney();">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最低运费</label>
	            <input name="quote.lowestFreight" id="lowestFreight" type="text" class="span3"  value="${ quote.lowestFreight}" onkeyup="setSumMoney();">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">是否含运费</label>
	             <select name="quote.isFreight" id="isFreight" style="width:202px;float: left;" onchange="setPriceByFre2();" class="span3">
	             	<option value="1" <s:if test="#request.quote.isFreight==1">selected</s:if> >是</option>
                  	<option value="0" <s:if test="#request.quote.isFreight==0">selected</s:if> >否</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">加急费备注</label>
	            <input name="quote.urgentCostRmk" id="urgentCostRmk" type="text" class="span11"  value="${ quote.urgentCostRmk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">最低运费备注</label>
	            <input name="quote.lowestFreightRmk" id="lowestFreightRmk" type="text" class="span11"  value="${ quote.lowestFreightRmk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">所含税率</label>
	            <input name="quote.containTax" id="containTax" type="text" class="span3"  value="${ quote.containTax}" onkeyup="setPriceByFre2()">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">小计</label>
	            <input name="quote.subtotal" id="subtotal" type="text" class="span3"  value="${ quote.subtotal}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">优惠金额</label>
	            <input name="quote.discountMoney" id="discountMoney" type="text" class="span3"  value="${quote.discountMoney }"  onkeyup="setPriceByDisMoney(this.value);">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">总金额</label>
	            <input name="quote.sumMoney" id="sumMoney" type="text" class="span3"  value="${ quote.sumMoney}" readonly="readonly">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">总金额备注</label>
	            <input name="quote.sumMoneyRemark" id="sumMoneyRemark" type="text" class="span6"  value="${ quote.sumMoneyRemark}" >
	            
	         </div>
	         <div class="clear"></div>
	          
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">备注</label>
	            <input name="quote.remk" id="remk" type="text" class="span11"  value="${ quote.remk}" >
	          </div>
	         <div class="clear"></div>
	          <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">合作条件和内容</label>
	          <textarea name="quote.item" id="item" class="quoteItem">${ quote.item}</textarea>
	          </div>
	         <div class="clear"></div>
	         <div class="controls">
	          <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">For and On Behalf of</label>
	            <input name="quote.deputyCom" id="deputyCom" type="text" class="span3"  value="${ quote.deputyCom}" onclick="setDeputyCom2();">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:93px;">Accept by</label>
	            <input name="quote.deputyName" id="deputyName" type="text" class="span3"  value="${quote.deputyName }" onclick="setDeputyName2();">
	          </div>
	         <div class="clear"></div>
            <div class="form-actions">
           
            <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
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
