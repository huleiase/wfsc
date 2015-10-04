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

<style type="text/css">
    <!--.tnt {Writing-mode:tb-rl;Text-align:left;font-size:12pt}-->
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
</script>
<script type="text/javascript">
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
        <td colspan="2">
        <table  class="reportTable" width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="11%">供应商</td>
            <td width="26%">${order.supplier }</td>
            <td width="27%" rowspan="5" align="left" class="tnt">For And On Behalf Of Harmontex Living COncept CO.,</td>
            <td width="12%">订货日期</td>
            <td width="24%"><fmt:formatDate value="${order.orderDate}" type="date" /></td>
          </tr>
          <tr>
            <td>联系人</td>
            <td>${order.atten }&nbsp;</td>
            <td>经手人</td>
            <td>${order.vcfrom }&nbsp;</td>
          </tr>
          <tr>
            <td>电话</td>
            <td>${order.attenTel }&nbsp;</td>
            <td>电话</td>
            <td>${order.vcfromTel }&nbsp;</td>
          </tr>
          <tr>
            <td>传真</td>
            <td>${order.attenFax }&nbsp;</td>
            <td>传真</td>
            <td>${order.vcfromFax }&nbsp;</td>
          </tr>
          <tr>
            <td>报价单号</td>
            <td>${order.quantation}&nbsp;</td>
            <td>订单号</td>
            <td>${order.orderNo}&nbsp;</td>
          </tr>
        </table></td>
        </tr>
      <tr>
        <td colspan="2"><table  class="reportTable" width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="14%">货期：</td>
            <td width="86%">${order.shipDateRemark }</td>
          </tr>
        </table></td>
      </tr>  
      <tr>
        <td><div align="center">采购单</div></td>
      </tr>
      <tr>
        <td><table class="reportTable" width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#000000" bgcolor="#FFFFFF">
          <tr>
            <td width="11%">发货要求</td>
            <td width="56%">${order.consignmentRequirements}</td>
            <td width="33%">&nbsp;</td>
          </tr>
          <tr>
            <td>包装材料</td>
            <td>${order.paquete}</td>
            <td>备注：${order.paqueteRemark }</td>
          </tr>
          <tr>
            <td>注意事项</td>
            <td>${order.notice}</td>
            <td>备注：${order.noticeRemark }</td>
          </tr>
          <tr>
            <td>发货公司</td>
            <td>${order.consignmentCompany}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>收货人</td>
            <td>${order.consignee}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>付款方式</td>
            <td>${order.payment}</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>收货地址</td>
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
        <td width="83"><div align="center">序号</div></td>
        <td width="66"><div align="center">型号</div></td>
        <td colspan="2"><div align="center">幅宽</div>          </td>
        <!-- 
        <td width="68"><div align="center">原数量</div></td>
        <td colspan="2"><div align="center">数量</div></td>
        -->
        <td width="68"><div align="center">实订量</div></td>
        
        <td width="72"><div align="center">单位</div></td>
        <td width="87"><div align="center">单价</div></td>
        <td width="118"><div align="center">金额</div></td>
      </tr>
      <s:iterator value="%{#request.quoteFabricList}" status="column">
      <tr>
        <td height="38">${orderId}</td>
        <td>${vcModelNum }&nbsp;</td>
        <td width="62">${vcWidth }</td>
        <td width="82">${vcWidthUnit}</td>
        <!-- <td>${vcQuantity}</td> -->
        <td width="58">${vcQuoteNum}&nbsp;</td>
        <td width="61">&nbsp;${priceCur}</td>
        <!-- 
        <td>${vcOldPrice}&nbsp;${vcPriceCur}</td>
        <td> <!-- ${vcPrice}&nbsp;${vcPriceUnit}</td>
        -->
        <td>${sigMoney}&nbsp;${priceCur } </td>
        
        <td>${realMonny }</td>
      </tr>
      </s:iterator>
      <tr>
        <td height="36" colspan="6">&nbsp;</td>
        <td><div align="right">总金额:</div></td>
        <td>${order.sumMoney }</td>
      </tr>
      <tr>
        <td height="31" colspan="10"><div align="left">备注: ${order.purchase.quote.remk}</div></td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td colspan="2">此订单以以下条款生效: </td>
  </tr>
  <tr>
    <td colspan="2">1.所有订单必须以瀚姆特斯或诚光的抬头为正式订单，其他任何订单无效</td>
  </tr>
  <tr>
    <td colspan="2">2.瀚姆特斯/诚光集团的采购人员为:阙丹丹 / 李峻诚 / 林艳 /陈子峰 / 史国栋，其他人员无效</td>
  </tr>
  <tr>
    <td colspan="2">3.如有任何非诚光/瀚姆特斯公司的人员以我公司名义订货而产生任何的损失,与我司概不相关</td>
  </tr>
  <tr>
    <td colspan="2">4.订货时根据我们所提出的要求进行安排发货,如达不到要求，导致货物损坏或不能使用，我司则要追究相应的责任</td>
  </tr>
  <tr>
    <td colspan="2">5.请在出货前进行产品质检，保证发货时的质量。如收到货物有任何问题，我司有权索赔</td>
  </tr>
</table>
</div>
</body>
</html>


