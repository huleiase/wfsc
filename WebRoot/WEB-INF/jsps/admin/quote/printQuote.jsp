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
 font-size: 14px;
}
table, form {
font-size: 14px;
color: #333;
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
    			<td colspan="2">
    				<table width="680" border="0" align="center">
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
      					<tr>
					        <td>尊敬的</td>
					        <td>&nbsp;${quote.customer}</td>
					        <td>&nbsp;</td>
					        <td>&nbsp;</td>
					        <td>&nbsp;</td>
					        <td>&nbsp;</td>
      					</tr>
      					<tr>
        					<td colspan="6">${quote.desInfo}</td>
        				</tr>
      					<tr>
        					<td colspan="6">
        						<table width="560" border="1" class="reportTable" align="center" cellspacing="0" bordercolor="#000000" bgcolor="#FFFFFF">
          							<tr height="30">
            							<td width="80"><div align="right">项目：</div></td>
            							<td width="189"><div align="center">${quote.projectName}&nbsp;</div></td>
            							<td width="80"><div align="right">项目地负责人：</div></td>
            							<td width="120"><div align="center">${quote.projectLocalPreson}&nbsp;</div></td>
							            <c:if test="${local=='hk'}">
							            	<td width="100"><div align="center">Trade terms:&nbsp;</div></td>
							            </c:if>
          							</tr>
          							<tr height="30">
            							<td><div align="right">设计公司：</div></td>
							            <td><div align="center">${quote.projectDesignComp}&nbsp;</div></td>
							            <td><div align="right">设计地跟进人：</div></td>
							            <td><div align="center">${quote.designLocalPreson}&nbsp;</div></td>
							            <c:if test="${local=='hk'}">
							            	<td><div align="center"><b>ExWorks Hong Kong:</b>&nbsp;</div></td>
							            </c:if>
          							</tr>
        						</table>
        					</td>
      					</tr>
    				</table>
    			</td>
  			</tr>
  			<tr bordercolor="#000000">
    			<td colspan="2">
    				<table width="713" border="1" class="reportTable" cellspacing="0" bordercolor="#000000">
    					<caption style="margin:10 10 5 10;">
    						<font style="font-size:16;font-weight:bold;">
    						<c:if test="${quote.fabricTitle == 0}">Quotation 报价表</c:if>
    						<c:if test="${quote.fabricTitle == 1}">Confirmation 合 同</c:if>
    						<c:if test="${quote.fabricTitle == 2}">INVOICE</c:if>
    						</font>
    					</caption>
      					<tr>
        					<th width="79">项目<br>Items</th>
        					<th width="77">描述<br>Description</th>
        					<th>图片<br>Picture</th>
        					<th width="137">型号<br>Model number</th>
        					<th width="90">色号<br>Colour</th>
        					<th width="113">幅宽<br>width</th>
        					<th width="98">单价<br>Unit price</th>
        					<th width="89">数量<br>Quantity</th>
        					<th width="90">金额<br>Total</th>
      					</tr>
      					<s:iterator value="%{#request.listQF}" status="column" var="qf">
      					<s:if test="addRmk!=''&&addRmk!=null">
				                <tr><td colspan="10"><div align="center"><s:property value='addRmk'/></div></td> </tr>
				             </s:if>
      						<tr>
        						<td height="38"><div align="center">&nbsp;<s:property value='vcProject'/></div></td>
        						<td><div align="center">&nbsp;<s:property value='vcDes'/></div></td>
        						<td>
        							<div align="center">
        							<s:if test="filePath!=null">
	        						<a target="_blank" title="点击查看图片" href='<s:property value="filePath"/>' rel="attachment" class="tip-bottom" style="display: inline-block; width: 150px;">
							     		<img width="150px" height="80px" alt="暂无图片" src='<s:property value="filePath"/>' /> 
							     	</a>
							     	</s:if>
        							</div>
        						</td>
        						<td><div align="center"><s:if test="#request.isHtCode==0"><s:property value='vcFactoryCode'/>&nbsp;<s:property value='vcModelNum'/></s:if><s:if test="#request.isHtCode==1"><s:property value='htCode'/></s:if></div></td>
        						<td><div align="center"><s:if test="#request.isHtCode==0"><s:property value='vcColorNum'/></s:if></div></td>
        						<td><div align="center">&nbsp;<s:property value='vcWidth'/>&nbsp;<s:property value='vcWidthUnit'/></div></td>
        						<td><div align="center"><s:if test='vcMoney=="RMB"||vcMoney=="rmb"'>&yen;</s:if><s:if test='vcMoney=="HKD"||vcMoney=="hkd"'>HKD</s:if>&nbsp;<s:property value='vcPrice'/>&nbsp;/<s:property value='vcPriceUnit'/></div></td>
        						<td><div align="center">&nbsp;<s:if test='vcQuantity!=0.0'><s:property value='vcQuantity'/>&nbsp;<s:property value='vcPriceUnit'/></s:if><s:else>-</s:else></div></td>
        						<td><div align="center">&nbsp;<s:if test='vcTotal!=0'><s:if test='vcMoney=="RMB"||vcMoney=="rmb"'>&yen;</s:if><s:if test='vcMoney=="HKD"||vcMoney=="hkd"'>HKD</s:if>&nbsp;${vcTotalStr}</s:if><s:else>-</s:else></div></td>
      						</tr>
      						
      					</s:iterator>
      					<c:if test="${quote.vcProcessFre!='0'}">
      						<tr>
      							<td height="31" colspan="8"><div align="right">加工费: </div></td>
        						<td><div align="center">&yen;&nbsp;${quote.vcProcessFre}</div></td>
      						</tr>
      					</c:if>
      					<c:if test="${quote.vcInstallFre!='0'}">
      						<tr>
      							<td height="31" colspan="8"><div align="right">安装费: </div></td>
        						<td><div align="center">&yen;&nbsp;${quote.vcInstallFre}</div></td>
      						</tr>
      					</c:if>
      					<c:if test="${quote.vcAftertreatment!='0'}">
      						<tr>
      							<td height="31" colspan="8"><div align="right">后处理费: </div></td>
        						<td><div align="center">&yen;&nbsp;${quote.vcAftertreatment}</div></td>
      						</tr>
      					</c:if>
      					
      					
      					<c:if test="${quote.engineTotal!=0}">
      						<tr>
      							<td height="31" colspan="8"><div align="right">电机合计: </div></td>
        						<td><div align="center"><s:if test='#request.quote.engineTotal!=0'>&yen;&nbsp;${quote.engineTotal}</s:if><s:else>-</s:else></div></td>
      						</tr>
      					</c:if>
      					
      					<c:if test="${quote.flameTotal!=0}">
      					<tr>
      						<td height="31" colspan="8"><div align="right">阻燃: </div></td>
        					<td><div align="center"><s:if test='#request.quote.flameTotal!=0'>&yen;&nbsp;${quote.flameTotal}</s:if><s:else>-</s:else></div></td>
      					</tr>
      					</c:if>
      					<c:if test="${quote.arriveTransport!=0}">
      					<tr>
      						<td height="31" colspan="8"><div align="right">货到工地运费: </div></td>
        					<td><div align="center"><s:if test='#request.quote.arriveTransport!=0'>&yen;&nbsp;${quote.arriveTransport}</s:if><s:else>-</s:else></div></td>
      					</tr>
      					</c:if>
      					
      					<c:if test="${quote.lowestFreight!=0}">
      					<tr>
      						<td height="31" colspan="8">
      							<div align="right">${quote.lowestFreightRmk} </div>
	        					<div align="right">最低运费: </div>
      						</td>
        					<td><div align="center"><s:if test='#request.quote.lowestFreight!=0'>&yen;&nbsp;${quote.lowestFreight}</s:if><s:else>-</s:else></div></td>
      					</tr>
      					</c:if>
      					<c:if test="${quote.urgentCost!=0}">
      					<tr>
     	 					<td height="31" colspan="8">
     	 						<div align="right">${quote.urgentCostRmk}</div>
	        					<div align="right">加急费: </div>
     	 					</td>
        					<td><div align="center"><s:if test='#request.quote.urgentCost!=0'>&yen;&nbsp;${quote.urgentCost}</s:if><s:else>-</s:else></div></td>
      					</tr>
      					</c:if>
      					
      					<c:if test="${!empty quote.titleCol1}">
	      					<tr>
	      						<td height="31" colspan="8"><div align="right">${quote.titleCol1}: </div></td>
	        					<td><div align="center"><s:if test='#request.quote.inputCol1!=0'>&yen;&nbsp;${quote.inputCol1}</s:if><s:else>-</s:else></div></td>
	      					</tr>
      					</c:if>
      					<c:if test="${!empty quote.titleCol2}">
	      					<tr>
	      						<td height="31" colspan="8"><div align="right">${quote.titleCol2}: </div></td>
	        					<td><div align="center"><s:if test='#request.quote.inputCol2!=0'>&yen;&nbsp;${quote.inputCol2}</s:if><s:else>-</s:else></div></td>
	      					</tr>
      					</c:if>
      					<c:if test="${!empty quote.titleCol3}">
	      					<tr>
	      						<td height="31" colspan="8"><div align="right">${quote.titleCol3}: </div></td>
	        					<td><div align="center"><s:if test='#request.quote.inputCol3!=0'>&yen;&nbsp;${quote.inputCol3}</s:if><s:else>-</s:else></div></td>
	      					</tr>
      					</c:if>
      					<c:if test="${!empty quote.titleCol4}">
	      					<tr>
	      						<td height="31" colspan="8"><div align="right">${quote.titleCol4}: </div></td>
	        					<td><div align="center"><s:if test='#request.quote.inputCol4!=0'>&yen;&nbsp;${quote.inputCol4}</s:if><s:else>-</s:else></div></td>
	      					</tr>
      					</c:if>
      					<c:if test="${!empty quote.titleCol5}">
	      					<tr>
	      						<td height="31" colspan="8"><div align="right">${quote.titleCol5}: </div></td>
	        					<td><div align="center"><s:if test='#request.quote.inputCol5!=0'>&yen;&nbsp;${quote.inputCol5}</s:if><s:else>-</s:else></div></td>
	      					</tr>
      					</c:if>
      					<c:if test="${quote.taxes!=0}">
      						<tr>
      							<td height="31" colspan="8"><div align="right">税金: </div></td>
        						<td><div align="center">&yen;&nbsp;${quote.taxes}</div></td>
      						</tr>
      					</c:if>
      					<tr>
					        <td height="36" colspan="8">
					        	<div align="right">小计:</div>
					        </td>
					        <td><div align="center"><s:if test='#request.quote.subtotal!=0&&#request.quote.subtotal!=""'>&yen;&nbsp;${quote.subtotalStr} </s:if><s:else>-</s:else></div></td>
      					</tr>
      					<s:if test='#request.quote.discountMoney>0' >
      						<tr>
						        <td height="36" colspan="8">
						        	<div align="right">优惠金额:</div>
						        </td>
						        <td><div align="center" id="discountMoney"><s:if test='#request.quote.discountMoney!=0&&#request.quote.discountMoney!=""'>&yen;&nbsp;${quote.discountMoney}</s:if><s:else>-</s:else></div></td>
      						</tr>
      					</s:if>
      					<tr>
					        <td height="36" colspan="7"><div align="center">&nbsp;${quote.sumMoneyRemark}</div></td>
					        <td><div align="right">总金额:</div></td>
					        <td><div align="center"><s:if test='#request.quote.sumMoney!=0&&#request.quote.sumMoney!=""'>&yen;&nbsp;${quote.sumMoneyStr}</s:if><s:else>-</s:else></div></td>
      					</tr>
      					<tr>
        					<td height="31" colspan="10"><div align="left">备注: ${quote.remk}</div></td>
      					</tr>
    				</table>
    			</td>
  			</tr>
  			<tr>
    			<td colspan="2">Terms and Conditions合作条件和内容: </td>
  			</tr>
  			<tr>
    			<td colspan="2">${quote.item}</td>
  			</tr>
  			<tr>
    			<td width="338">For and On Behalf of</td>
    			<td width="371">Accept by</td>
  			</tr>
  			<tr>
    			<td>${quote.deputyCom}</td>
    			<td>${quote.deputyName}</td>
  			</tr>
		</table>
	</div>
	<br/>
	<br/>
	<br/>
</body>
</html>


