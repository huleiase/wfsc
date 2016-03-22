<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<table class="table table-bordered table-striped" id="quoteFabricTable">
	<thead>
		<tr>
			<th>
				序号
			</th>
			<th>
				报价型号
			</th>
			<th>
				色号
			</th>
			<c:if test="${!isLess||isMoreLess}">
			<th>
				原厂型号
			</th>
			</c:if>
			<th>
				幅宽
			</th>
			<th>
				分段铺量
			</th>
			<th>
				实际到货
			</th>
			<th>
				出货数明细
			</th>
			<th>
				类型
			</th>
			
			<th>
				出货数量
			</th>
			<th>
				出库日期
			</th>
			<th>
				出货经手人
			</th>
			<th>
				快递单号
			</th>
			<th>
				快递公司
			</th>
			<th>
				到货地址
			</th>
			
			<th>
				备注
			</th>
			<th>
				订货量
			</th>
			<th>
				实订量
			</th>
			<c:if test="${!isLess}">
			<th>
				单价
			</th>
			<th>
				实价
			</th>
			<th>
				金额
			</th>
			<th>
				折算RMB
			</th>
			</c:if>
		</tr>
	</thead>
	<tbody>

		<s:if test="#request.quoteFabricList.size>0">
			<s:iterator value="#request.quoteFabricList" var="obj" status="st">
				<c:set value='${st.index}' var="vcIndex" />
				<c:set value='${st.index+1 }' var="vcCount" />
				<tr id="tr${vcCount }">
					<input type="hidden" value="<s:property value="id"/>"
						id="id${vcCount }" name="quoteFabricList[${vcIndex }].id" />
					<input type="hidden" value="<s:property value="exchangeRate"/>"
						id="exchangeRate${vcCount }">
					<td>
						<s:property value="orderId" />
					</td>
					<td>
						<s:property value="vcModelNumDisplay" />
					</td>
					<td>
						<s:property value="vcColorNum" />
					</td>
					<c:if test="${!isLess||isMoreLess}">
					<td>
						<s:property value="vcFactoryCode"/>&nbsp;<s:property value="vcModelNum"/>
					</td>
					</c:if>
					<td>
						<div style="width: 90px;">
							<s:property value="vcWidth" />
							&nbsp;&nbsp;
							<s:property value="vcWidthUnit" />
						</div>
					</td>
					<td>
						<input type="text" value="<s:property value="vcSubLay"/>"
							id="vcSubLay${vcCount }"
							name="quoteFabricList[${vcIndex }].vcSubLay" class="widthShort" />
					</td>
					<td>
						<input type="text" value="<s:property value="vcRealityAog"/>"
							id="vcRealityAog${vcCount }"
							name="quoteFabricList[${vcIndex }].vcRealityAog"
							class="widthShort" />
					</td>
					<td>
						<input type="text" value="<s:property value="vcShipmentNum"/>"
							id="vcShipmentNum${vcCount }"
							name="quoteFabricList[${vcIndex }].vcShipmentNum"
							class="widthShort" />
					</td>
					<td>
						<input type="text" value="<s:property value="vcType"/>"
							id="vcType${vcCount }" name="quoteFabricList[${vcIndex }].vcType"
							class="widthShort" />
					</td>
					
					<td>
						<input type="text" value="<s:property value="outNum"/>"
							id="outNum${vcCount }" name="quoteFabricList[${vcIndex }].outNum"
							class="widthShort" />
					</td>
					<td>
						<input type="text" value="<s:date name="outStoreDate" format="yyyy-MM-dd" />"
							id="outStoreDate${vcCount }" name="quoteFabricList[${vcIndex }].outStoreDate"
							  class="widthMidd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<td>
						<input type="text" value="<s:property value="shipPerson"/>"
							id="shipPerson${vcCount }" name="quoteFabricList[${vcIndex }].shipPerson"
							class="widthShort" />
					</td>
					<td>
						<input type="text" value="<s:property value="expressNumber"/>" class="widthMidd"
							id="expressNumber${vcCount }" name="quoteFabricList[${vcIndex }].expressNumber" />
					</td>
					<td>
						<input type="text" value="<s:property value="expressCompany"/>" class="widthMidd"
							id="expressCompany${vcCount }" name="quoteFabricList[${vcIndex }].expressCompany"/>
					</td>
					<td>
						<input type="text" value="<s:property value="arrivalAddress"/>"
							id="arrivalAddress${vcCount }" name="quoteFabricList[${vcIndex }].arrivalAddress" />
					</td>
					
					<td>
						<input type="text" value="<s:property value="vcPurchaseRmk"/>"
							id="vcPurchaseRmk${vcCount }"
							name="quoteFabricList[${vcIndex }].vcPurchaseRmk" />
					</td>

					<td>
						<div style="width: 100px;">
							<s:property value="orderQuantity" />
							&nbsp;&nbsp;
							<s:property value="vcOldPriceUnit" />
						</div>
					</td>
					<td>
						<div style="width: 112px;">
							<input id="vcQuoteNum${vcCount }"
								name="quoteFabricList[${vcIndex }].vcQuoteNum"
								value="<s:property value="vcQuoteNum" />"
								onkeyup="setOrderAmount(${ vcCount})" class="widthShort" />
								&nbsp;&nbsp;
							<s:property value="vcOldPriceUnit" />
						</div>
					</td>
					<c:if test="${!isLess}">
					<td>
						<div style="width: 90px;">
							<s:property value="sigMoney" />
							&nbsp;&nbsp;
							<s:property value="priceCur" />
						</div>
					</td>
					<td>
						<div style="width: 120px;">
							<input id="shijia${vcCount }" class="widthShort"
								name="quoteFabricList[${vcIndex }].shijia"
								value="<s:property value="shijia"/>"
								onkeyup="setOrderAmount(${vcCount })">
								&nbsp;&nbsp;
							<s:property value="priceCur" />
						</div>
					</td>
					<td>
						<input type="text" name="quoteFabricList[${vcIndex }].realMonny" class="widthShort"
							id="realMonny${vcCount }" value="<s:property value="realMonny"/>" />
					</td>
					<td>
						<input type="text" name="quoteFabricList[${vcIndex }].amountrmb"
							id="amountrmb${vcCount }" value="<s:property value="amountrmb"/>" />
					</td>
					</c:if>
				</tr>
			</s:iterator>
		</s:if>

	</tbody>
</table>
<script type="text/javascript">
function setOrderAmount(vcCount){
	var rate = $("#exchangeRate"+vcCount).val();
	var shijia = $("#shijia"+vcCount).val();
	var vcQuoteNum = $("#vcQuoteNum"+vcCount).val();
	if(!shijia){
		shijia = 0;
	}
	if(!vcQuoteNum){
		vcQuoteNum = 0;
	}
	$("#realMonny"+vcCount).val((shijia*vcQuoteNum).toFixed(2));
	$("#amountrmb"+vcCount).val((shijia*vcQuoteNum*rate).toFixed(2));
	setSumMoney();
}
function setSumMoney(){
	var sumMoney = 0;
	$("[id^='realMonny']").each(function(i){
		var money = $(this).val();
		if(!money){
			money = 0;
		}
	   sumMoney+=Number(money);
	 });
	$("#sumMoney").val(Number(sumMoney).toFixed(2));
}
</script>
