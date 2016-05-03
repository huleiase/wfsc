<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name=vs_targetSchema content="http://schemas.microsoft.com/intellisense/ie5">
<title>打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.PrintArea.js"></script>
<!--<link href="../themes/com/style.css" rel="stylesheet" type="text/css" />
    <link href="../css/style.css" rel="stylesheet" type="text/css" />
media=print 这个属性可以在打印时有效-->
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>

<style>
br{
  line-height: 10px;
}
.tdp
{
    border-bottom: 1 solid #000000;
    border-left:  1 solid #000000;
    border-right:  0 solid #ffffff;
    border-top: 0 solid #ffffff;
}
.tabp
{
    border-color: #000000 #000000 #000000 #000000;
    border-style: solid;
    border-top-width: 2px;
    border-right-width: 2px;
    border-bottom-width: 1px;
    border-left-width: 1px;
}
.NOPRINT {
 font-family: "宋体";
 font-size: 9pt;
}
.reportTable{
	border-left: 2px solid;
	border-top: 2px solid;
	border-right: 1px solid;
	border-bottom: 1px solid;
}
.reportTable td {
	border-right: 1px solid;
	border-bottom: 1px solid;
}
</style>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
  function setImg(obj){
     var url=obj.src;
     if(url.indexOf("title3.jpg")>0){
     	if(confirm("是否更换模版?")){
     		 document.getElementById("img").src=basePath+"images/title1.jpg";
     	}
     }
      if(url.indexOf("title1.jpg")>0){
     	if(confirm("是否更换模版?")){
     		 document.getElementById("img").src=basePath+"images/title.jpg";
     	}
     }
     if(url.indexOf("title.jpg")>0){
     	if(confirm("是否更换模版?")){
     		 document.getElementById("img").src=basePath+"images/title3.jpg";
     	}
     }
     
  }
  
  	$(document).ready(function(){
		  $("input#biuuu_button").click(function(){  
		  	$("div#myPrintArea").printArea();  
		  });
	});
</script>

</head>

<body >
<center class="Noprint" >
  	<p>
  	<input id="biuuu_button" class="hrbt1" type="button" value="打印"></input>
    </p>
</center>
<div id="myPrintArea">
<table width="713" border="0" align="center">
  <tr>
    <td colspan="2"><img src="images/title3.jpg" width="713" height="125" onclick="setImg(this);" id="img"/></td>
  </tr>
  <tr>
    <td colspan="2"><table width="680" border="0" align="center">
      <tr>
        <td width="51">Attn:</td>
        <td width="232">${quote.vcAttn}</td>
        <td width="33">Tel:</td>
        <td width="179">${quote.vcAttnTel}</td>
        <td width="59">Fax:</td>
        <td width="100">${quote.vcAttnFax}</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>${quote.vcAttnName}</td>
        <td>Phone:</td>
        <td>${quote.vcAttnPhone}</td>
        <td>Email</td>
        <td>${quote.vcAttnEmail}</td>
      </tr>
      <tr>
        <td>From:</td>
        <td>${quote.vcFrom}</td>
        <td>Tel:</td>
        <td>${quote.vcFormTel}</td>
        <td>Fax:</td>
        <td>${quote.vcFormFax}</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>${quote.vcFormName}</td>
        <td>Phone:</td>
        <td>${quote.vcFormPhone}
        </td>
        <td>Date: </td>
        <td><s:date name="#request.quote.vcFormDate" format="yyyy-MM-dd" /></td>
      </tr>
       <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>Ref no:</td>
        <td>${quote.vcQuoteLocal}-${quote.vcQuoteNum}</td>
      </tr>
      <!--<tr>
        <td>尊敬的</td>
        <td>&nbsp;${quote.customer}</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      
      --><tr>
        <td colspan="6">${quote.desInfo}</td>
        </tr>
      <tr>
        <td colspan="6">
        	<table width="500" border="1" class="reportTable" align="center" cellspacing="0" bordercolor="#000000" bgcolor="#FFFFFF">
          		<tr height="35">
            		<td width="100"><div align="right">Project：</div></td>
            		<td colspan="3" ><div align="center">${quote.projectName}&nbsp;</div></td>
            		<c:if test="${local=='hk'}">
            			<td width="100"><div align="center">Trade terms:&nbsp;</div></td>
            		</c:if>
          		</tr>
          		<tr>
            		<td><div align="right">Designer：</div></td>
            		<td colspan="3" ><div align="center">${quote.projectDesignComp}&nbsp;</div></td>
            		<c:if test="${local=='hk'}">
            			<td><div align="center">ExWorks Hong Kong:&nbsp;</div></td>
            		</c:if>
          		</tr>
        	</table>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr bordercolor="#000000">
	<td colspan="2">
    	<table width="713" border="1" class="reportTable" cellspacing="0" bordercolor="#000000">
    		<caption style="margin:10 10 5 10;">
    			<font style="font-size:16;font-weight:bold;">
    				<c:if test="${quote.fabricTitle == 0}">Quotation 報價表</c:if>
    				<c:if test="${quote.fabricTitle == 1}">Confirmation 合 同</c:if>
    				<c:if test="${quote.fabricTitle == 2}">INVOICE</c:if>
    			</font>
    		</caption>
      		<tr height="35px">
        		<th width="79">
        			<font style="font-size:14;">Items</font>
        		</th>
        		<th width="77">
        			<font style="font-size:14;">Description</font>
        		</th>
        		<th width="137">
        			<font style="font-size:14;">Model number</font>
        		</th>
        		<th width="90">
        			<font style="font-size:14;">Colour</font>
        		</th>
        		<th width="113">
        			<font style="font-size:14;">Width</font>
        		</th>
        		<th width="98">
        			<font style="font-size:14;">Unit price</font>
        		</th>
        		<th width="89">
        			<font style="font-size:14;">Quantity</font>
        		</th>
        		<th width="90">
        			<font style="font-size:14;">Total</font>
        		</th>
      		</tr>
      <s:iterator value="%{#request.listQF}" status="column">
      <tr>
        <td height="38"><div align="center">&nbsp;<s:property value='vcProject'/></div></td>
        <td><div align="center">&nbsp;<s:property value='vcDes'/></div></td>
        <td><div align="center"><s:if test="#request.isHtCode==0"><s:property value='vcFactoryCode'/>&nbsp;<s:property value='vcModelNum'/></s:if><s:if test="#request.isHtCode==1"><s:property value='htCode'/></s:if></div></td>
        <td><div align="center"><s:if test="#request.isHtCode==0"><s:property value='vcColorNum'/></s:if></div></td>
        <td><div align="center">&nbsp;<s:property value='vcWidth'/>&nbsp;<s:property value='vcWidthUnit'/></div></td>
        <td><div align="center"><s:if test='vcMoney=="RMB"||vcMoney=="rmb"'>&yen;</s:if><s:if test='vcMoney=="HKD"||vcMoney=="hkd"'>HKD</s:if>&nbsp;<s:property value='vcPrice'/>&nbsp;/<s:property value='vcPriceUnit'/></div></td>
        <td><div align="center">&nbsp;<s:if test='vcQuantity!=0.0'><s:property value='vcQuantity'/>&nbsp;<s:property value='vcPriceUnit'/></s:if><s:else>-</s:else></div></td>
        <td><div align="center">&nbsp;<s:if test='vcTotal!=0'><s:if test='vcMoney=="RMB"||vcMoney=="rmb"'>&yen;</s:if><s:if test='vcMoney=="HKD"||vcMoney=="hkd"'>HKD</s:if>&nbsp;${vcTotalStr}</s:if><s:else>-</s:else></div></td>
      </tr>
      </s:iterator>
      <c:if test="${!empty quote.engineTotal && quote.engineTotal!=0}">
      	<tr>
      		<td height="31" colspan="7"><div align="right">电机合计: </div></td>
        	<td><div align="center"><s:if test='#request.quote.engineTotal>0'>&yen;&nbsp;${quote.engineTotal}</s:if><s:else>-</s:else></div></td>
      	</tr>
      </c:if>
      					
      <c:if test="${!empty quote.flameTotal && quote.flameTotal!=0}">
      	<tr>
      		<td height="31" colspan="7"><div align="right">阻燃: </div></td>
        	<td><div align="center"><s:if test='#request.quote.flameTotal>0'>&yen;&nbsp;${quote.flameTotal}</s:if><s:else>-</s:else></div></td>
      	</tr>
      </c:if>
      <c:if test="${!empty quote.arriveTransport && quote.arriveTransport!=0}">
      	<tr>
      		<td height="31" colspan="7"><div align="right">货到工地运费: </div></td>
        	<td><div align="center"><s:if test='#request.quote.arriveTransport>0'>&yen;&nbsp;${quote.arriveTransport}</s:if><s:else>-</s:else></div></td>
      	</tr>
      </c:if>
      <c:if test="${!empty quote.titleCol1}">
		<tr>
	      	<td height="31" colspan="7"><div align="right">${quote.titleCol1}: </div></td>
	        <td><div align="center"><s:if test='#request.quote.inputCol1>0'>&yen;&nbsp;${quote.inputCol1}</s:if><s:else>-</s:else></div></td>
		</tr>
      </c:if>
      <c:if test="${!empty quote.titleCol2}">
	    <tr>
	      	<td height="31" colspan="7"><div align="right">${quote.titleCol2}: </div></td>
	        <td><div align="center"><s:if test='#request.quote.inputCol2>0'>&yen;&nbsp;${quote.inputCol2}</s:if><s:else>-</s:else></div></td>
		</tr>
      </c:if>
      <c:if test="${!empty quote.titleCol3}">
		<tr>
			<td height="31" colspan="7"><div align="right">${quote.titleCol3}: </div></td>
	        <td><div align="center"><s:if test='#request.quote.inputCol3>0'>&yen;&nbsp;${quote.inputCol3}</s:if><s:else>-</s:else></div></td>
		</tr>
	  </c:if>
      <c:if test="${!empty quote.titleCol4}">
		<tr>
	      	<td height="31" colspan="7"><div align="right">${quote.titleCol4}: </div></td>
			<td><div align="center"><s:if test='#request.quote.inputCol4>0'>&yen;&nbsp;${quote.inputCol4}</s:if><s:else>-</s:else></div></td>
		</tr>
      </c:if>
      <c:if test="${!empty quote.titleCol5}">
		<tr>
	      	<td height="31" colspan="7"><div align="right">${quote.titleCol5}: </div></td>
	        <td><div align="center"><s:if test='#request.quote.inputCol5>0'>&yen;&nbsp;${quote.inputCol5}</s:if><s:else>-</s:else></div></td>
		</tr>
      </c:if>
      <c:if test="${quote.taxes!=0}">
      						<tr>
      							<td height="31" colspan="7"><div align="right">税金: </div></td>
        						<td><div align="center">&yen;&nbsp;${quote.taxes}</div></td>
      						</tr>
      </c:if>
      <!--
      <c:if test="${not empty requestScope.quote.lowestFreight}">
      <tr>
        <td height="31" colspan="7"><div align="right">${quote.lowestFreightRmk}最低运费: </div></td>
        <td><div align="center"><s:if test='#request.quote.lowestFreight>0'>&yen;&nbsp;${quote.lowestFreight}</s:if><s:else>-</s:else></div></td>
      </tr>
      </c:if>
      -->
      <c:if test="${!empty quote.urgentCost && quote.urgentCost!=0}">
      <tr>
        <td height="31" colspan="7"><div align="right">${quote.urgentCostRmk}Emergency fee: </div></td>
        <td><div align="center"><s:if test='#request.quote.urgentCost>0'>&yen;&nbsp;${quote.urgentCost}</s:if><s:else>-</s:else></div></td>
      </tr>
      </c:if>
      <tr>
		<td height="36" colspan="7">
        	<div align="right">Freight charge:</div>
		</td>
       <td><div align="center"><s:if test='#request.quote.lowestFreight>0'>&nbsp;${quote.lowestFreight}</s:if><s:else>-</s:else></div></td>
      </tr>
      <!--
      <s:if test='#request.quote.discountMoney>0' >
      <tr>
        <td height="36" colspan="6"><div align="center">&nbsp;</div></td>
        <td><div align="right">优惠金额:</div></td>
        <td><div align="center" id="discountMoney"><s:if test='#request.quote.discountMoney!=0&&#request.quote.discountMoney!=""'>&yen;&nbsp;${quote.discountMoney}</s:if><s:else>-</s:else></div></td>
      </tr>
      </s:if>
      -->
      <tr>
		<td height="36" colspan="7">
			<div align="right">Subtotal:</div>
		</td>
		<td><div align="center"><s:if test='#request.quote.subtotal!=0&&#request.quote.subtotal!=""'>&yen;&nbsp;${quote.subtotalStr}</s:if><s:else>-</s:else></div></td>
      </tr>
      <tr>
        <td height="36" colspan="6"><div align="center">&nbsp;</div></td>
        <td><div align="right">Total Amount:</div></td>
        <td><div align="center"><s:if test='#request.quote.sumMoney!=0&&#request.quote.sumMoney!=""'>&yen;&nbsp;${quote.sumMoneyStr}</s:if><s:else>-</s:else></div></td>
      </tr><!--
      <tr>
        <td height="31" colspan="9"><div align="left">备注: ${quote.remk}</div></td>
      </tr>
    --></table></td>
  </tr>
  
  <tr>
    <td colspan="2"><font size="4px"><b>Terms and Conditions:</b></font> </td>
  </tr>
  <tr>
    <td colspan="2">
    1.)   Payment: 50% deposit payment is needed to confirm this order and 50% balance payment before delivery.<br/>
    2.)   Delivery : approximate 2working weeks from the date receiving deposit payment subject to our written final confirmation.<br/>
    3.)   Buyer agrees and accepts 5-10% color variation between goods and samples.<br/>
    4.)   For leather hide and fabric ordered in roll, it may exist +/- 5% variation in quantity between this quotation and the actual arrived quantity. Buyer agrees to settle the balance payment according to the actual arrived quantitty.<br/>
    5.)   Goods are to be picked up in our office for the total goods value under HK$5,000. HKD500 local delivery charge is needed if buyer requests us to deliver the goods.   (applicable only to Hong Kong delivery address)<br/>
    6.)   Delivery of each order is limited to one delivery address, otherwise surcharge is needed.<br/>
    7.)   Buyer agrees to pay HKD200/day for our storage charge, if buyer fails to pick up the goods 5 days after our notice of goods arrival.<br/>
    8.)   All goods sold under this quotation are not returnable. <br/>
    9.)   Claims for replacements or refunds of defective goods must be made to us in writing within 3 days after the goods delivery. In case of dispute, our company has the right of final judgement. No claims can be made after the goods have been cut.<br/>
    10.) This quotation is valid within 30 days. 
    </td>
  </tr>
  <tr>
    <td width="338">For and On Behalf of</td>
    <td width="371">Accept by</td>
  </tr>
  <tr>
    <td>Harmontex Living Concept Co. Ltd.</td>
    <td>${quote.deputyName}</td>
  </tr>
</table>
</div>
<br/>
<br/>
<br/>
 

</body>
</html>


