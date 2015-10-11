<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
              <s:if test="#request.qfList.size>0">
                <s:iterator value="#request.qfList" var="obj" status="st">
                <c:set value='${obj.vcIndex }' var="vcIndex"/>
                 <c:set value='${obj.vcIndex+1 }' var="vcCount"/>
                  <tr id="tr${vcCount }">
                 <td>
                 	<input type="hidden" value="<s:property value="id"/>" id="quoteFabricId${vcCount }" name="quoteFabricList[${vcIndex }].id"/>
                 	<input type="hidden" value="<s:property value="dhjVcOldPrice"/>" id="dhjVcOldPrice${vcCount }" />
	                 <input type="hidden" value="<s:property value="dhjVcHKPrice"/>" id="dhjVcHKPrice${vcCount }" />
	                 <input type="hidden" value="<s:property value="isCgbj"/>" id="isCgbj${vcCount }" />
	                 <!-- <input type="hidden" value="<s:property value="qfId"/>" id="qfId${vcCount }" name="quoteFabricList[${vcIndex }].qfId" />
	                --><input type="hidden" value="<s:property value="vcIndex"/>" id="vcIndex${vcCount }" name="quoteFabricList[${vcIndex }].vcIndex" />
	                 <input type="hidden" value="<s:property value="iscopy"/>" id="iscopy${vcCount }" name="quoteFabricList[${vcIndex }].copy" />
	                 <input type="hidden" value="<s:property value="isHtCode"/>" id="isHtCode${vcCount }" name="quoteFabricList[${vcIndex }].isHtCode" />
	                 <input type="hidden" value="<s:property value="htCode"/>" id="htCode${vcCount }" name="quoteFabricList[${vcIndex }].htCode" />
	                 <input type="hidden" value="<s:property value="isHidden"/>" id="isHidden${vcCount }" name="quoteFabricList[${vcIndex }].isHidden" />
	                 <input type="hidden" value="<s:property value="isReplaced"/>" id="isReplaced${vcCount }" name="quoteFabricList[${vcIndex }].isReplaced" />
	                 <input type="hidden" value="<s:property value="isHiddenisReplaced"/>" id="isHiddenisReplaced${vcCount }" name="quoteFabricList[${vcIndex }].isHiddenisReplaced" />
	                 <input type="hidden" value="<s:property value="replaceId"/>" id="replaceId${vcCount }" name="quoteFabricList[${vcIndex }].replaceId" />
	                 <input type="hidden" value="<s:property value="priceCur"/>" id="priceCur${vcCount }" name="quoteFabricList[${vcIndex }].priceCur" />
	                 <input type="hidden" value="<s:property value="vcPurDis"/>" id="vcPurDis${vcCount }" name="quoteFabricList[${vcIndex }].vcPurDis" />
	                 <input type="hidden" value="<s:property value="singlePrice"/>" id="singlePrice${vcCount }" name="quoteFabricList[${vcIndex }].singlePrice" />
	                 <input type="hidden" value="<s:property value="shijia"/>" id="shijia${vcCount }" name="quoteFabricList[${vcIndex }].shijia" />
	                 <input type="hidden" value="<s:property value="vcQuoteNum"/>" id="vcQuoteNum${vcCount }" name="quoteFabricList[${vcIndex }].vcQuoteNum" />
	                 <input type="hidden" value="<s:property value="exchangeRate"/>" id="exchangeRate${vcCount }" name="quoteFabricList[${vcIndex }].exchangeRate" />
	                 <input type="hidden" value="<s:property value="vcFactoryCode"/>" id="vcFactoryCode${vcCount }" name="quoteFabricList[${vcIndex }].vcFactoryCode" />
	                 <input type="hidden" value="<s:property value="vcModelNum"/>" id="vcModelNum${vcCount }" name="quoteFabricList[${vcIndex }].vcModelNum" />
	                 <input type="hidden" value="<s:property value="vcProFre"/>" id="vcProFre${vcCount }" name="quoteFabricList[${vcIndex }].vcProFre" />
	                 <input type="hidden" value="<s:property value="vcRetFre"/>" id="vcRetFre${vcCount }" name="quoteFabricList[${vcIndex }].vcRetFre" />
	                 <input type="hidden" value="<s:property value="dhjHKTransCost"/>" id="dhjHKTransCost${vcCount }" name="quoteFabricList[${vcIndex }].dhjHKTransCost" />
	                 <input type="hidden" value="<s:property value="dhjInlandTransCost"/>" id="dhjInlandTransCost${vcCount }" name="quoteFabricList[${vcIndex }].dhjInlandTransCost" />
	                 <input type="hidden" value="<s:property value="dhjCost"/>" id="dhjCost${vcCount }" name="quoteFabricList[${vcIndex }].dhjCost" />
	                 <input type="hidden" value="<s:property value="dhjInlandRate"/>" id="dhjInlandRate${vcCount }" name="quoteFabricList[${vcIndex }].dhjInlandRate" />
	                 <input type="hidden" value="<s:property value="dhjHKRate"/>" id="dhjHKRate${vcCount }" name="quoteFabricList[${vcIndex }].dhjHKRate" />
	                 <input type="hidden" value="<s:property value="dhjWidth"/>" id="dhjWidth${vcCount }" name="quoteFabricList[${vcIndex }].dhjWidth" />
	                 <input type="hidden" value="<s:property value="vcOldPriceUnit"/>" id="vcOldPriceUnit${vcCount }" name="quoteFabricList[${vcIndex }].vcOldPriceUnit" />
	                 <input type="hidden" value="<s:property value="quoteFormate"/>" id="quoteFormate${vcCount }" name="quoteFabricList[${vcIndex }].quoteFormate" />
	                 <span class="label label-info btn btn-primary btn-mini" id="copy${vcCount }" onclick="copyFabric2(${vcCount });">复制</span>
                 </td>
                 <td><input type="text" value="${vcCount }" id="orderId${vcCount }" name="quoteFabricList[${vcIndex }].orderId" class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcProject"/>" id="vcProject${vcCount }" name="quoteFabricList[${vcIndex }].vcProject"/></td>
			     <td><input type="text" value="<s:property value="vcDes"/>" id="vcDes${vcCount }" name="quoteFabricList[${vcIndex }].vcDes" /></td>
			     <td><input type="text" value="<s:property value="vcDis"/>" id="vcDis${vcCount }" name="quoteFabricList[${vcIndex }].vcDis"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcModelNumDisplay"/>" id="vcModelNumDisplay${vcCount }" name="quoteFabricList[${vcIndex }].vcModelNumDisplay" readonly="readonly" /></td>
			     <td><input type="text" value="<s:property value="vcColorNum"/>" id="vcColorNum${vcCount }" name="quoteFabricList[${vcIndex }].vcColorNum"  class="widthShort"/></td>
                 <td>
                 <div style="width:150px;">
                 	<input type="text" value="<s:property value="vcWidth"/>" id="vcWidth${vcCount }" name="quoteFabricList[${vcIndex }].vcWidth"  class="widthShort"/>
                 	<select id="vcWidthUnit${vcCount }" name="quoteFabricList[${vcIndex }].vcWidthUnit">
                 		<option value="cm">cm</option>
                 		<option value="sf">sf</option>
                 	</select>
                 	</div>
                 </td>
                  <td>
                  <div style="width:235px;">
                  	<input type="text" value="<s:property value="vcPrice"/>" id="vcPrice${vcCount }" name="quoteFabricList[${vcIndex }].vcPrice" readonly="readonly"  class="widthShort"/>&nbsp;
                  	<span id="vcPricehb${vcCount }"><s:property value="vcMoney"/></span>&nbsp;
                  	<select onchange="changeCustomerUnit(${vcCount },1);setVcPrice(${vcCount });" id="vcPriceUnit${vcCount }" name="quoteFabricList[${vcIndex }].vcPriceUnit">
                  		<option value="㎡" <c:if test="${obj.vcPriceUnit=='㎡'}">selected</c:if> >㎡</option>
                  		<option value="m" <c:if test="${obj.vcPriceUnit=='m'}">selected</c:if> >m</option>
                  		<option value="yd" <c:if test="${obj.vcPriceUnit=='yd'}">selected</c:if> >yd</option>
                  		<option value="sf" <c:if test="${obj.vcPriceUnit=='sf'}">selected</c:if> >sf</option>
                  		<option value="pc" <c:if test="${obj.vcPriceUnit=='pc'}">selected</c:if> >pc</option>
                  		<option value="set" <c:if test="${obj.vcPriceUnit=='set'}">selected</c:if> >set</option>
                  		<option value="unit" <c:if test="${obj.vcPriceUnit=='unit'}">selected</c:if> >unit</option>
                  		<option value="pair" <c:if test="${obj.vcPriceUnit=='pair'}">selected</c:if> >pair</option>
                  		<option value="roll" <c:if test="${obj.vcPriceUnit=='roll'}">selected</c:if> >roll</option>
                  	</select>
                  	<input type="button" onclick="selPriceByModel(${vcCount })" value="查价" id="selPrice" />
                  </div>
                  </td>
                  <td><input type="text" value="<s:property value="vcOldPrice"/>" id="vcOldPrice${vcCount }" readonly="readonly" name="quoteFabricList[${vcIndex }].vcOldPrice"  class="widthShort"/></td>
     			  <td><div style="width:100px;">
     			  	<select id="vcMoney${vcCount }" name="quoteFabricList[${vcIndex }].vcMoney">
     			  		<option value="RMB" <c:if test="${obj.vcMoney=='RMB'}">selected</c:if> >RMB</option>
     			  		<option value="HKD" <c:if test="${obj.vcMoney=='HKD'}">selected</c:if> >HKD</option>
     			  		<option value="JPY" <c:if test="${obj.vcMoney=='JPY'}">selected</c:if> >JPY</option>
     			  		<option value="USD" <c:if test="${obj.vcMoney=='USD'}">selected</c:if> >USD</option>
     			  		<option value="EUR" <c:if test="${obj.vcMoney=='EUR'}">selected</c:if> >EUR</option>
     			  		<option value="GBP" <c:if test="${obj.vcMoney=='GBP'}">selected</c:if> >GBP</option>
     			  		<option value="AUD" <c:if test="${obj.vcMoney=='AUD'}">selected</c:if> >AUD</option>
     			  		<option value="CAD" <c:if test="${obj.vcMoney=='CAD'}">selected</c:if> >CAD</option>
     			  		<option value="CHF" <c:if test="${obj.vcMoney=='CHF'}">selected</c:if> >CHF</option>
     			  	</select>&nbsp;
     			  	<span><s:property value="vcOldPriceUnit"/></span>&nbsp;
     			  </div>
     			  </td>
     			 <td><input type="text" value="<s:property value="vcComposition"/>" id="vcComposition${vcCount }" name="quoteFabricList[${vcIndex }].vcComposition"  class="widthShort"/></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="freight"/>" id="freight${vcCount }" name="quoteFabricList[${vcIndex }].freight"  class="widthShort"/></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="lowFreight"/>" id="lowFreight${vcCount }" name="quoteFabricList[${vcIndex }].lowFreight"  class="widthShort"/></td>
			     <td class="brandAttri"><input type="text" readonly="readonly" value="<s:property value="brandAttri"/>" id="brandAttri${vcCount }" name="quoteFabricList[${vcIndex }].brandAttri"  class="widthShort"/></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="productRange"/>" id="productRange${vcCount }" name="quoteFabricList[${vcIndex }].productRange"  class="widthShort"/></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="vcProduceLocal"/>" id="vcProduceLocal${vcCount }" name="quoteFabricList[${vcIndex }].vcProduceLocal"  class="widthShort"/></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="vcFactoryNum"/>" id="vcFactoryNum${vcCount }" name="quoteFabricList[${vcIndex }].vcFactoryNum"  class="widthShort"/></td>
			     <td class="vcSpecialExp"><input type="text" onkeyup="setVcPrice(${vcCount });" value="<s:property value="vcSpecialExp"/>" id="vcSpecialExp${vcCount }" name="quoteFabricList[${vcIndex }].vcSpecialExp"  class="widthShort"/></td>
			     <td><div style="width:100px;"><input type="text" value="<s:property value="vcQuantity"/>" id="vcQuantity${vcCount }" name="quoteFabricList[${vcIndex }].vcQuantity"  class="widthShort"/>&nbsp;<span id="qUnit${vcCount }"><s:property value="vcPriceUnit"/></span></div></td>
			     <td>
			     <div style="width:160px;">
			     	<input type="text" onkeyup="changeCustomerUnit(${vcCount },2);" value="<s:property value="customerQuantity"/>" id="customerQuantity${vcCount }" name="quoteFabricList[${vcIndex }].customerQuantity"  class="widthShort" style="float:left;"/>
			     	<select onchange="changeCustomerUnit(${vcCount },2);" id="customerUnit${vcCount }" name="quoteFabricList[${vcIndex }].customerUnit">
			     		<option value="㎡" <c:if test="${obj.customerUnit=='㎡'}">selected</c:if> >㎡</option>
			     		<option value="m" <c:if test="${obj.customerUnit=='m'}">selected</c:if> >m</option>
			     		<option value="yd" <c:if test="${obj.customerUnit=='yd'}">selected</c:if> >yd</option>
			     		<option value="sf" <c:if test="${obj.customerUnit=='sf'}">selected</c:if> >sf</option>
			     		<option value="pc" <c:if test="${obj.customerUnit=='pc'}">selected</c:if> >pc</option>
			     		<option value="set" <c:if test="${obj.customerUnit=='set'}">selected</c:if> >set</option>
			     		<option value="unit" <c:if test="${obj.customerUnit=='unit'}">selected</c:if> >unit</option>
			     		<option value="pair" <c:if test="${obj.customerUnit=='pair'}">selected</c:if> >pair</option>
			     		<option value="roll" <c:if test="${obj.customerUnit=='roll'}">selected</c:if> >roll</option>
			     	</select>
			     	</div>
			     </td>
			     <td><div style="width:100px;"><input type="text" readonly="readOnly"  value="<s:property value="conversionQuantity"/>" id="conversionQuantity${vcCount }" name="quoteFabricList[${vcIndex }].conversionQuantity"  class="widthShort"/>&nbsp;<span><s:property value="vcOldPriceUnit"/></span>&nbsp;</div></td>
			     <td><input type="text" value="<s:property value="MOQ"/>" id="MOQ${vcCount }" name="quoteFabricList[${vcIndex }].MOQ" class="widthShort" /></td>
			     <td><input type="text" value="<s:property value="leastIncrement"/>" id="leastIncrement${vcCount }" name="quoteFabricList[${vcIndex }].leastIncrement"  class="widthShort"/></td>
			     <td><div style="width:100px;"><input type="text" onkeyup="changeCustomerUnit(${vcCount },1);" value="<s:property value="orderQuantity"/>" id="orderQuantity${vcCount }" name="quoteFabricList[${vcIndex }].orderQuantity"  class="widthShort"/>&nbsp;<span><s:property value="vcOldPriceUnit"/></span>&nbsp;</div></td>
			     <td><input type="text" readonly="readonly" value="<s:property value="vcLeastNum"/>" id="vcLeastNum${vcCount }" name="quoteFabricList[${vcIndex }].vcLeastNum"  class="widthShort"/></td>
			     <td><input type="text" onkeyup="setVcPrice(${vcCount });" value="<s:property value="vcDiscount"/>" id="vcDiscount${vcCount }" name="quoteFabricList[${vcIndex }].vcDiscount"  class="widthShort"/></td>
			     <td><input type="text" onclick="setTrVcTotal(${vcCount });" value="<s:property value="vcTotal"/>" id="vcTotal${vcCount }" readonly="readonly" name="quoteFabricList[${vcIndex }].vcTotal"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcRmk"/>" id="vcRmk${vcCount }" name="quoteFabricList[${vcIndex }].vcRmk" /></td>
			     <td><input type="text" value="<s:property value="remark2"/>" id="remark2${vcCount }" name="quoteFabricList[${vcIndex }].remark2" /></td>
			     <td><input type="text" value="<s:property value="quoteRemark"/>" id="quoteRemark${vcCount }" name="quoteFabricList[${vcIndex }].quoteRemark" /></td>
			     <td><input type="text" readonly="readOnly" value="<s:property value="replaceRemark"/>" id="replaceRemark${vcCount }" name="quoteFabricList[${vcIndex }].replaceRemark" /></td>
			     <td>
				     <div style="width:405px;">
				     	<span class="label label-info btn btn-primary btn-mini" id="remove${vcCount }" onclick="delFabric2(${vcCount });">删除</span>
				     	<span class="label label-info btn btn-primary btn-mini" id="uploadFabric${vcCount }" onclick="uploadIMGt(${vcCount });">上传图片</span>
				     	<a target="_blank" title="点击查看图片" href='<s:property value="filePath"/>' rel="attachment" class="tip-bottom" style="display: inline-block; width: 60px;">
				     		<img width="30" height="20" alt="暂无图片" src='<s:property value="filePath"/>' /> 
				     	</a>
				     	<span class="label label-info btn btn-primary btn-mini" id="replaceFabric${vcCount }" onclick="replaceFabric2tt(${vcCount });">替代</span>
				     	<span class="label label-info btn btn-primary btn-mini" id="delreplaceFabric${vcCount }" onclick="delreplaceFabric2tt(${vcCount });">解除替代</span>
				     	<span class="label label-info btn btn-primary btn-mini" id="dhjbj${vcCount }" onclick="dahuojia(${vcCount });">大货价</span>
				     	<span class="label label-info btn btn-primary btn-mini" id="cgbj${vcCount }" onclick="cgjia(${vcCount });">常规价</span>
			     	</div>
			     </td>
                </tr>
               </s:iterator>
               </s:if>
