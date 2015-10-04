<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name=vs_targetSchema content="http://schemas.microsoft.com/intellisense/ie5">
<title>打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.PrintArea.js"></script>
<script>
    $(document).ready(function(){
		  $("input#biuuu_button").click(function(){  
		  	$("div#myPrintArea").printArea();  
		  });
	});
</script>
</head>

<body>
<center class="Noprint" >
	<p>
  	<input id="biuuu_button" class="hrbt1" type="button" value="打印"></input>
    </p>
</center>
<div id="myPrintArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" colspan="4" background="../css/images/tab_05.gif">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"></td>
        <td></td>
        <td width="16"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="../css/images/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="1" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" >
          <tr>
            <td width="15%">Date：</td>
            <td width="24%"><fmt:formatDate value="${purchase.contractDate}" type="date" ></fmt:formatDate> </td>
            <td width="15%"><br /></td>
            <td width="21%"><label>${purchase.quote.projectNum} </label>&nbsp;</td>
            <td width="15%">货期要求：</td>
            <td width="15%"><label>
            	${purchase.deliveryRequirements }&nbsp;
            </label></td>
          </tr>
          <tr>
            <td>依凭单</td>
            <td width="24%">差额因素</td>
            <td width="15%">单号</td>
            <td width="21%"><c:if test="${purchase.orderStatus==1}">订金确认</c:if></td>
            <td width="15%"><c:if test="${purchase.orderStatus==1}">余额确认</c:if></td>
            <td width="15%">
               ${purchase.quote.projectName}&nbsp;
            </td>
          </tr>
          <tr>
            <td height="18">
            ${purchase.voucher}&nbsp;
             </td>
            <td width="24%"> ${purchase.balanceFactor} &nbsp;</td>
            <td width="15%" rowspan="2">${purchase.contractNo}<!-- ${purchase.quote.vcQuoteLocal}_${purchase.quote.vcQuoteNum} --></td>
            <td width="21%" ><label>
             <c:if test="${purchase.orderStatus==1}">
             	${purchase.depositConfirmation}
             </c:if> 
            </label>&nbsp;</td>
            <td width="15%" >
            <c:if test="${purchase.orderStatus==1}">
            	${purchase.balanceConfirmation}
            </c:if>&nbsp;
            </td>
            <td width="15%" >&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td width="15%"></td>
            <td width="21%"><label>
             <c:if test="${purchase.orderStatus==1}">
             	${purchase.dcPerson}
             </c:if> 
            </label>&nbsp;</td>
            <td width="15%">
              <c:if test="${purchase.orderStatus==1}">
              	${purchase.bcPerson}
              </c:if>&nbsp;
              </td>
            <td width="15%">&nbsp;</td>
          </tr>
          <tr>
               <td>发货方式及快递付款方式:</td>
               <td colspan="5">
               <input type="radio" name="paymentType" value="到付" id="paymentType" <c:if test="${purchase.paymentType=='到付'||purchase.paymentType==''||purchase.paymentType==null}">checked="checked"</c:if>/>到付
               <input type="radio" name="paymentType" value="寄付" id="paymentType" <c:if test="${purchase.paymentType=='寄付'}">checked="checked"</c:if>/>寄付
               </td>
          </tr>
          <tr>
               <td>是否指定批核人:</td>
               <td colspan="5">
               <input type="radio" name="isApproved" value="0" id="isApproved" <c:if test="${purchase.isApproved=='0'||purchase.isApproved==null}">checked="checked"</c:if> onclick="checkIsApproved(this)"/>否
               <input type="radio" name="isApproved" value="1" id="isApproved" <c:if test="${purchase.isApproved=='1'}">checked="checked"</c:if> onclick="checkIsApproved(this)"/>是
               </td>
          </tr>
          <tr style="display: none;" id="isDisApprovedPerson">
               <td>总经理批核:</td>
               <td colspan="5">
               <input type="checkbox" name="approvedPerson" value="Samuel" id="approvedPersonSamuel"/>Samuel
               <input type="checkbox" name="approvedPerson" value="Tim" id="approvedPersonTim"/>Tim
               <input type="checkbox" name="approvedPerson" value="Benson" id="approvedPersonBenson"/>Benson
               </td>
          </tr>
          <tr>
            <td colspan="6"><div align="center">PURCHASE ORDER </div></td>
            </tr>
          <tr>
            <td colspan="3">Shippment Address </td>
            <td colspan="3">
            <c:if test="${purchase.address != null}">
            	${purchase.address} 
            </c:if>
             <c:if test="${purchase.address == null}">
             ${purchase.otheraddress} 
            </c:if>&nbsp;
            </td>
            </tr>
          <tr>
            <td colspan="3">Expected Arrival Date </td>
            <td colspan="3"><label>
              ${purchase.remarks} 
            </label>&nbsp;
            </td>
            </tr>
          <tr>
            <td colspan="6"><div align="center">注意：凡是绒布或真丝请厂家一定要包装好!!!（卷装，不折叠）</div></td>
            </tr>
          <tr>
            <td colspan="6"><table width="100%" border="1" cellspacing="0" cellpadding="0">
              <tr>
                <td width="40" height="26">NO.</td>
                <td width="140">Model Number </td>
                <td width="88">色号</td>
                <td width="58">幅宽</td>
                <td width="100">订货数量</td>
                <td width="80">报价数量</td>
                <td width="80">分段铺量</td>
                <td width="80">实际到货</td>
                <td width="80">出货数明细</td>
                <td width="60">类型</td> 
                <td width="62">备注</td>
              </tr>
               <s:iterator value="%{#request.quoteFabricList}" status="column" >
              <tr>
                <td height="27">${vcProject}</td>
                <td>
                	<c:if test="${purStatus==3}">
                		${vcFactoryCode}&nbsp;${vcModelNum}
                		<!--<c:if test='${isHtCode==0}'>${vcModelNum}</c:if>
	                	<c:if test='${isHtCode==1}'>${htCode}</c:if>-->
                	</c:if>
                	<c:if test="${purStatus != 3}">
                		<c:if test='${isHtCode==1}'>${htCode}</c:if>
	                	<c:if test='${isHtCode==0}'>${vcFactoryCode}&nbsp;${vcModelNum }</c:if>
	                	<!--<c:if test='${isHtCode==1}'>${vcModelNum}</c:if>
	                	<c:if test='${isHtCode==0}'>${vcFactoryCode}&nbsp;${vcModelNum }</c:if>-->
                	</c:if>&nbsp;
                </td>
                <td>${vcColorNum }&nbsp;</td>
                <td>${vcWidth }&nbsp;&nbsp;${vcWidthUnit }</td>
                <td>
                	${orderQuantity }&nbsp;&nbsp; ${vcOldPriceUnit}
                 </td>
                <td>${vcQuantity }&nbsp;&nbsp;${vcPriceUnit}</td>
                <td>${vcSubLay}  &nbsp;</td>
                <td>${vcRealityAog}  &nbsp;</td>
                <td>${vcShipmentNum}  &nbsp;</td>
				<td>${vcType} &nbsp;</td>
                <td>${vcPurchaseRmk}&nbsp; </td>
                
              </tr>
              </s:iterator>
              <tr>
                <td height="26">制单</td>
                <td><label>
                  ${purchase.touching }
                </label>&nbsp;</td>
                <td>&nbsp;</td>
                <td><c:if test="${purchase.orderStatus==1}">核对员</c:if></td>
                <td><c:if test="${purchase.orderStatus==1}">
                ${purchase.checker }
                </c:if>&nbsp;
                </td>
                <td>采购员</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </table></td>
            </tr>
           
          <tr>
            <td colspan="6" height="80px">&nbsp;</td>
            </tr>
        </table></td>
        <td width="8" background="../css/images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</div>
<script>
	function checkIsApproved(obj){
	   var isApproved=obj.value;
	   var isDisApprovedPerson=document.getElementById("isDisApprovedPerson");
	   if(isApproved=="0"){
	      isDisApprovedPerson.style.display="none";
	   }else{
	      isDisApprovedPerson.style.display="";
	   }
	}

	function checkIsDisApprovedPerson(){
	   var isApproved="${purchase.isApproved}";
	   if(isApproved=="1"){
	     isDisApprovedPerson.style.display="";
	   }
	   var approvedPerson="${purchase.approvedPerson}";
	   if(approvedPerson.length>0){
	      if(approvedPerson.indexOf("|")>=0){
	         var persons=approvedPerson.split("|");
	         for(var i=0;i<persons.length;i++){
	             if(persons[i]=="Samuel"){
	                document.getElementById("approvedPersonSamuel").checked="checked";
	             }
	             if(persons[i]=="Tim"){
	                 document.getElementById("approvedPersonTim").checked="checked";
	             }
	             if(persons[i]=="Benson"){
	                document.getElementById("approvedPersonBenson").checked="checked";
	             }
	         }
	      }else{
	         if(approvedPerson=="Samuel"){
	                document.getElementById("approvedPersonSamuel").checked="checked";
	             }
	             if(approvedPerson=="Tim"){
	                 document.getElementById("approvedPersonTim").checked="checked";
	             }
	             if(approvedPerson=="Benson"){
	                document.getElementById("approvedPersonBenson").checked="checked";
	          }
	      }
	   }
	}
</script>
</body>
</html>

