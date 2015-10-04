<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name=vs_targetSchema content="http://schemas.microsoft.com/intellisense/ie5">
<title>打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.PrintArea.js"></script>
<!--media=print 这个属性可以在打印时有效-->
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
     
     if(confirm("是否更换模版?")){
         if(url.indexOf("title1.jpg")>0){
            document.getElementById("img").src=basePath+"css/images/title.jpg";
	     }else{
	        document.getElementById("img").src=basePath+"css/images/title1.jpg";
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
    <td width="585">Harmontex Living Concept Co.,</td><td width="191" rowspan="7"><div align="right"><img src="images/ht.jpg" width="188" height="157" id="img"></div></td>
  </tr>
  <tr>
    <td>Rm 1006, 10/F, Tower A, #393-397 </td>
  </tr>
  <tr>
    <td>Sha Tsui Road,</td>
  </tr>
  <tr>
    <td>Tsuen Wan</td>
  </tr>
  <tr>
    <td>Hong Kong</td>
  </tr>
  <tr>
    <td>T:00 852 3741 2235;Fax:00 852 3741 2236</td>
  </tr>
  <tr>
    <td><div align="center">www.harmontex.com</div></td>
  </tr>
  <tr>
    <td colspan="2"><table width="780" border="0" align="center">
      <tr>
        <td><div align="center">Guangzhou - Beijing - Hongkong - Shanghai - Shenzhen</div></td>
        </tr>
      <tr>
        <td><div align="center">Fabric-Leather-Lighting-Wallconvering-Furniture-Carpet-Window System-Artwork</div></td>
        </tr>

      <tr>
        <td colspan="2"><table  class="reportTable" width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="11%">supplier</td>
            <td width="26%">${order.supplier }</td>
            <td width="27%" rowspan="5">For And On Behalf Of Harmontex Living COncept CO.,</td>
            <td width="12%">Order date</td>
            <td width="24%"><fmt:formatDate value="${order.orderDate}" type="date" /></td>
          </tr>
          <tr>
            <td>Contacts</td>
            <td>${order.atten }</td>
            <td>Handling</td>
            <td>${order.vcfrom }</td>
          </tr>
          <tr>
            <td>Telephone</td>
            <td>${order.attenTel }</td>
            <td>Telephone</td>
            <td>${order.vcfromTel }</td>
          </tr>
          <tr>
            <td>Fax</td>
            <td>${order.attenFax  }</td>
            <td>Fax</td>
            <td>${order.vcfromFax }</td>
          </tr>
          <tr>
            <td>quote No.</td>
            <td>${order.quantation}</td>
            <td>Order No.</td>
            <td>${order.orderNo}</td>
          </tr>
        </table></td>
        </tr>
      <tr>
        <td colspan="2"><table  class="reportTable" width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="14%">delivery time：</td>
            <td width="86%">${order.shipDateRemark }</td>
          </tr>
        </table></td>
      </tr>  
      <tr>
        <td><div align="center">Purchase order</div></td>
      </tr>
      <tr>
        <td><table class="reportTable" width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="11%">Delivery requirement</td>
            <td width="56%">${order.consignmentRequirements}</td>
            <td width="33%">&nbsp;</td>
          </tr>
          <tr>
            <td>Packing</td>
            <td>${order.paquete}</td>
            <td>remark：${order.paqueteRemark }</td>
          </tr>
          <tr>
            <td>notes</td>
            <td>${order.notice}</td>
            <td>remark：${order.noticeRemark }</td>
          </tr>
          <tr>
            <td>Shipping companies</td>
            <td>${order.consignmentCompany}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Consignee</td>
            <td>${order.consignee}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>payment</td>
            <td>${order.payment}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Delivery address</td>
            <td>${order.shipAddress}</td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr bordercolor="#000000">
    <td colspan="2"><table width="780" border="0" class="reportTable" cellspacing="0" bordercolor="#000000">
      <tr>
        <td width="83"><div align="center">id</div></td>
        <td width="66"><div align="center">Model Number</div></td>
        <td colspan="2"><div align="center">Width</div>          </td>
        <!-- 
        <td width="68"><div align="center">原数量</div></td>
        <td colspan="2"><div align="center">数量</div></td>
        -->
        <td width="68"><div align="center">quantity</div></td>
        
        <td width="72"><div align="center">unit</div></td>
        <td width="87"><div align="center">price</div></td>
        <td width="118"><div align="center">Amount</div></td>
      </tr>
      <s:iterator value="%{#request.quoteFabricList}" status="column">
      <tr>
        <td height="38">${orderId}</td>
        <td>${vcModelNum }&nbsp;</td>
        <td width="62">${vcWidth }</td>
        <td width="82">${vcWidthUnit}</td>
        <!-- <td>${vcQuantity}</td> -->
        <td width="58">${vcQuoteNum}&nbsp;</td>
        <td width="61">&nbsp;${vcOldPriceUnit}</td>
        <!-- 
        <td>${vcOldPrice}&nbsp;${vcPriceCur}</td>
        <td> ${vcPrice}&nbsp;${vcPriceUnit}</td>
        -->
        <td></td>
        <td><!--${vcQuantity*vcPrice }  --></td>
      </tr>
      </s:iterator>
      <tr>
        <td height="36" colspan="6">&nbsp;</td>
        <td><div align="right">Grand Total Amount:</div></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="31" colspan="10"><div align="left">Remark: ${order.purchase.quote.remk}</div></td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td colspan="2">This Order is issued on the following conditions: </td>
  </tr>
  <tr>
    <td colspan="2">1.This company only accepts goods delivered against a Purchaser Order signed by the authorized Managers. </td>
  </tr>
  <tr>
    <td colspan="2">2.Acceptance of this order holds the supplier responsible for any loss which should be claimed by this Company in any case the supplier should fail to complete this order. </td>
  </tr>
  <tr>
    <td colspan="2">3.All increases in mill’s prices or increases in the exchange rates of any charge included in the selling price shall be paid by supplier.</td>
  </tr>
  <tr>
    <td colspan="2">4.If delivery is not in the stipulated time for any reason whatsoever, this Company reserves the right to claim the loss caused by such failure. </td>
  </tr>
  <tr>
    <td colspan="2">5.If any changes for the order,please inform us immediately</td>
  </tr>
   <tr>
    <td colspan="2">6. Company Chop:           Manager Signature:  </td>
  </tr>
</table>
</div>
</body>
</html>


