<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped" id="quoteFabricTable">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>项目</th>
                  <th>描述</th>
                  <th>型号</th>
                   <th>色号</th>
                  <th>幅宽</th>
                  <th>最终单价</th>
                  <th>面价</th>
                   <th>货币</th>
                  <th>成分</th>
                  <th>运费</th>
                  <th>最低运费</th>
                   <th>品牌属性</th>
                  <th>产品范围</th>
                  <th>产地</th>
                  <th>产地编号</th>
                  <th>特殊费用</th>
                   <th>數量</th>
                  <th>客户提供</th>
                  <th>换算后</th>
                  <th>最小起订量</th>
                  <th>最小增量</th>
                  <th>实际订货</th>
                   <th>起订量</th>
                  <th>折扣</th>
                  <th>金額</th>
                  <th>备注</th>
                   <th>备注2</th>
                  <th>报价员备注</th>
                  <th>注意</th>
                </tr>
              </thead>
              <tbody>
              
              <s:if test="#request.quoteFabricList.size>0">
                <s:iterator value="#request.quoteFabricList" var="obj" status="st">
                <c:set value='${obj.vcIndex}' var="vcIndex"/>
                 <c:set value='${obj.vcIndex+1 }' var="vcCount"/>
                  <tr id="tr${vcCount }">
                 <td><input disabled="disabled" type="text" value="${vcCount }" id="orderId${vcCount }" name="quoteFabricList[${vcIndex }].orderId" class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcProject"/>" id="vcProject${vcCount }" name="quoteFabricList[${vcIndex }].vcProject"/></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcDes"/>" id="vcDes${vcCount }" name="quoteFabricList[${vcIndex }].vcDes" /></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcModelNumDisplay"/>" id="vcModelNumDisplay${vcCount }" name="quoteFabricList[${vcIndex }].vcModelNumDisplay" readonly="readonly" /></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcColorNum"/>" id="vcColorNum${vcCount }" name="quoteFabricList[${vcIndex }].vcColorNum"  class="widthShort"/></td>
                 <td>
                 <div style="width:150px;">
                 	<input disabled="disabled" type="text" value="<s:property value="vcWidth"/>" id="vcWidth${vcCount }" name="quoteFabricList[${vcIndex }].vcWidth"  class="widthShort"/>
                 	<select disabled="disabled" id="vcWidthUnit${vcCount }" name="quoteFabricList[${vcIndex }].vcWidthUnit">
                 		<option value="cm">cm</option>
                 		<option value="sf">sf</option>
                 	</select>
                 	</div>
                 </td>
                  <td>
                  <div style="width:235px;">
                  	<input disabled="disabled" type="text" value="<s:property value="vcPrice"/>" id="vcPrice${vcCount }" name="quoteFabricList[${vcIndex }].vcPrice" readonly="readonly"  class="widthShort"/>&nbsp;
                  	<span id="vcPricehb${vcCount }"><s:property value="vcMoney"/></span>&nbsp;
                  	<select disabled="disabled" id="vcPriceUnit${vcCount }" name="quoteFabricList[${vcIndex }].vcPriceUnit">
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
                  </div>
                  </td>
                  <td><input disabled="disabled" type="text" value="<s:property value="vcOldPrice"/>" id="vcOldPrice${vcCount }" readonly="readonly" name="quoteFabricList[${vcIndex }].vcOldPrice"  class="widthShort"/></td>
     			  <td><div style="width:100px;">
     			  	<select disabled="disabled" id="vcMoney${vcCount }" name="quoteFabricList[${vcIndex }].vcMoney">
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
     			 <td><input disabled="disabled" type="text" value="<s:property value="vcComposition"/>" id="vcComposition${vcCount }" name="quoteFabricList[${vcIndex }].vcComposition"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="freight"/>" id="freight${vcCount }" name="quoteFabricList[${vcIndex }].freight"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="lowFreight"/>" id="lowFreight${vcCount }" name="quoteFabricList[${vcIndex }].lowFreight"  class="widthShort"/></td>
			     <td class="brandAttri"><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="brandAttri"/>" id="brandAttri${vcCount }" name="quoteFabricList[${vcIndex }].brandAttri"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="productRange"/>" id="productRange${vcCount }" name="quoteFabricList[${vcIndex }].productRange"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="vcProduceLocal"/>" id="vcProduceLocal${vcCount }" name="quoteFabricList[${vcIndex }].vcProduceLocal"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" readonly="readonly" value="<s:property value="vcFactoryNum"/>" id="vcFactoryNum${vcCount }" name="quoteFabricList[${vcIndex }].vcFactoryNum"  class="widthShort"/></td>
			     <td class="vcSpecialExp"><input disabled="disabled" type="text" value="<s:property value="vcSpecialExp"/>" id="vcSpecialExp${vcCount }" name="quoteFabricList[${vcIndex }].vcSpecialExp"  class="widthShort"/></td>
			     <td><div style="width:100px;"><input disabled="disabled" type="text" value="<s:property value="vcQuantity"/>" id="vcQuantity${vcCount }" name="quoteFabricList[${vcIndex }].vcQuantity"  class="widthShort"/>&nbsp;<span id="qUnit${vcCount }"><s:property value="vcPriceUnit"/></span></div></td>
			     <td>
			     <div style="width:160px;">
			     	<input disabled="disabled" type="text" value="<s:property value="customerQuantity"/>" id="customerQuantity${vcCount }" name="quoteFabricList[${vcIndex }].customerQuantity"  class="widthShort" style="float:left;"/>
			     	<select disabled="disabled" id="customerUnit${vcCount }" name="quoteFabricList[${vcIndex }].customerUnit">
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
			     <td><div style="width:100px;"><input disabled="disabled" type="text" readonly="readOnly"  value="<s:property value="conversionQuantity"/>" id="conversionQuantity${vcCount }" name="quoteFabricList[${vcIndex }].conversionQuantity"  class="widthShort"/>&nbsp;<span><s:property value="vcOldPriceUnit"/></span>&nbsp;</div></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="MOQ"/>" id="MOQ${vcCount }" name="quoteFabricList[${vcIndex }].MOQ" class="widthShort" /></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="leastIncrement"/>" id="leastIncrement${vcCount }" name="quoteFabricList[${vcIndex }].leastIncrement"  class="widthShort"/></td>
			     <td><div style="width:100px;"><input disabled="disabled" type="text" value="<s:property value="orderQuantity"/>" id="orderQuantity${vcCount }" name="quoteFabricList[${vcIndex }].orderQuantity"  class="widthShort"/>&nbsp;<span><s:property value="vcOldPriceUnit"/></span>&nbsp;</div></td>
			     <td><input type="text"  disabled="disabled" value="<s:property value="vcLeastNum"/>" id="vcLeastNum${vcCount }" name="quoteFabricList[${vcIndex }].vcLeastNum"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcDiscount"/>" id="vcDiscount${vcCount }" name="quoteFabricList[${vcIndex }].vcDiscount"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcTotal"/>" id="vcTotal${vcCount }" readonly="readonly" name="quoteFabricList[${vcIndex }].vcTotal"  class="widthShort"/></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="vcRmk"/>" id="vcRmk${vcCount }" name="quoteFabricList[${vcIndex }].vcRmk" /></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="remark2"/>" id="remark2${vcCount }" name="quoteFabricList[${vcIndex }].remark2" /></td>
			     <td><input disabled="disabled" type="text" value="<s:property value="quoteRemark"/>" id="quoteRemark${vcCount }" name="quoteFabricList[${vcIndex }].quoteRemark" /></td>
			     <td><input disabled="disabled" type="text" readonly="readOnly" value="<s:property value="replaceRemark"/>" id="replaceRemark${vcCount }" name="quoteFabricList[${vcIndex }].replaceRemark" /></td>
                </tr>
                <s:if test="addRmk!=''&&addRmk!=null">
                <tr><td colspan="30"><input style="width:100%" disabled="disabled" type="text" readonly="readOnly" value="<s:property value="addRmk"/>" id="addRmk${vcCount }" name="quoteFabricList[${vcIndex }].addRmk" /></td> </tr>
                </s:if>
               </s:iterator>
               </s:if>
               
              </tbody>
            </table>
